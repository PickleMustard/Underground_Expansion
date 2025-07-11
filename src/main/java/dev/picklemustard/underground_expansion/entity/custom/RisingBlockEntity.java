package dev.picklemustard.underground_expansion.entity.custom;


import javax.annotation.Nullable;

import com.mojang.logging.LogUtils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult;
import org.slf4j.Logger;
import dev.picklemustard.underground_expansion.blocks.Risable;
import dev.picklemustard.underground_expansion.entity.ModEntities;

/**
 * @author picklemustard
 * @version v1.0.0
 * @category Entity
 * {@summary A Rising Block Entity that behaves like a falling block entity but inverses the fall direction.}
 *
 */
public class RisingBlockEntity extends Entity {
    private static final Logger LOGGER = LogUtils.getLogger();
    private BlockState blockState = Blocks.SAND.defaultBlockState();
    public int time;
    public boolean dropItem = true;
    private boolean cancelDrop;
    private boolean hurtEntities;
    private int fallDamageMax = 40;
    private float fallDamagePerDistance;
    @Nullable
    public CompoundTag blockData;
    public boolean forceTickAfterTeleportToDuplicate;
    protected static EntityDataAccessor<BlockPos> DATA_START_POS = SynchedEntityData.defineId(RisingBlockEntity.class, EntityDataSerializers.BLOCK_POS);
    public RisingBlockEntity(EntityType<? extends RisingBlockEntity> entityType, Level level) {
        super(entityType, level);
    }

    private RisingBlockEntity(Level level, double x, double y, double z, BlockState state) {
        this(ModEntities.RISING_BLOCK.get(), level);
        this.blockState = state;
        this.blocksBuilding = true;
        this.setPos(x, y, z);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.setStartPos(this.blockPosition());
    }


    private void setStartPos(BlockPos blockPosition) {
        this.entityData.set(DATA_START_POS, blockPosition);
    }

    public BlockState getBlockState(){
        return this.blockState;
    }

    public BlockPos getStartPos() {
        return this.entityData.get(DATA_START_POS);
    }

    /**
     * {@summary Ran when the block calculates it should start falling. Replaces the block with a RisingBlockEntity}
     *
     *
     * @param level Level
     * @param pos {@linkplain BlockPos}
     * @param blockState {@linkplain BlockState}
     * @return {@linkplain RisingBlockEntity}
     */
    public static RisingBlockEntity rise(Level level, BlockPos pos, BlockState blockState) {
        RisingBlockEntity rbe = new RisingBlockEntity(level, (double)pos.getX() + 0.5, (double)pos.getY(), (double)pos.getZ() + 0.5, blockState.hasProperty(BlockStateProperties.WATERLOGGED) ? blockState.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(false)) : blockState);
        level.setBlock(pos, blockState.getFluidState().createLegacyBlock(), 3);
        level.addFreshEntity(rbe);
        return rbe;
    }

    @Override
    protected double getDefaultGravity(){
        return -0.04;
    }

    @Override
    public void tick() {
        if(this.blockState.isAir()) {
            this.discard();
        } else {
            Block block = this.blockState.getBlock();
            this.time++;
            this.applyGravity();
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.handlePortal();
            if (!this.level().isClientSide && (this.isAlive() || this.forceTickAfterTeleportToDuplicate)) {
                BlockPos blockpos = this.blockPosition();
                boolean flag = this.blockState.getBlock() instanceof ConcretePowderBlock;
                boolean flag1 = flag && this.blockState.canBeHydrated(this.level(), blockpos, this.level().getFluidState(blockpos), blockpos);
                double d0 = this.getDeltaMovement().lengthSqr();
                if (flag && d0 > 1.0) {
                    BlockHitResult blockhitresult = this.level()
                        .clip(
                            new ClipContext(
                                new Vec3(this.xo, this.yo, this.zo), this.position(), ClipContext.Block.COLLIDER, ClipContext.Fluid.SOURCE_ONLY, this
                            )
                        );
                    if (blockhitresult.getType() != HitResult.Type.MISS && this.blockState.canBeHydrated(this.level(), blockpos, this.level().getFluidState(blockhitresult.getBlockPos()), blockhitresult.getBlockPos())) {
                        blockpos = blockhitresult.getBlockPos();
                        flag1 = true;
                    }
                }

                if (this.onGround() || flag1) {
                    BlockState blockstate = this.level().getBlockState(blockpos);
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.7, -0.5, 0.7));
                    if (!blockstate.is(Blocks.MOVING_PISTON)) {
                        if (!this.cancelDrop) {
                            boolean flag2 = blockstate.canBeReplaced(
                                new DirectionalPlaceContext(this.level(), blockpos, Direction.DOWN, ItemStack.EMPTY, Direction.UP)
                            );
                            boolean flag3 = FallingBlock.isFree(this.level().getBlockState(blockpos.below())) && (!flag || !flag1);
                            boolean flag4 = this.blockState.canSurvive(this.level(), blockpos) && !flag3;
                            if (flag2 && flag4) {
                                if (this.blockState.hasProperty(BlockStateProperties.WATERLOGGED)
                                    && this.level().getFluidState(blockpos).getType() == Fluids.WATER) {
                                    this.blockState = this.blockState.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(true));
                                }

                                if (this.level().setBlock(blockpos, this.blockState, 3)) {
                                    ((ServerLevel)this.level())
                                        .getChunkSource()
                                        .chunkMap
                                        .broadcast(this, new ClientboundBlockUpdatePacket(blockpos, this.level().getBlockState(blockpos)));
                                    this.discard();
                                    if (block instanceof Risable) {
                                        ((Risable)block).onLand(this.level(), blockpos, this.blockState, blockstate, this);
                                    }

                                    if (this.blockData != null && this.blockState.hasBlockEntity()) {
                                        BlockEntity blockentity = this.level().getBlockEntity(blockpos);
                                        if (blockentity != null) {
                                            CompoundTag compoundtag = blockentity.saveWithoutMetadata(this.level().registryAccess());

                                            for (String s : this.blockData.getAllKeys()) {
                                                compoundtag.put(s, this.blockData.get(s).copy());
                                            }

                                            try {
                                                blockentity.loadWithComponents(compoundtag, this.level().registryAccess());
                                            } catch (Exception exception) {
                                                LOGGER.error("Failed to load block entity from falling block", (Throwable)exception);
                                            }

                                            blockentity.setChanged();
                                        }
                                    }
                                } else if (this.dropItem && this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                                    this.discard();
                                    this.callOnBrokenAfterFall(block, blockpos);
                                    this.spawnAtLocation(block);
                                }
                            } else {
                                this.discard();
                                if (this.dropItem && this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                                    this.callOnBrokenAfterFall(block, blockpos);
                                    this.spawnAtLocation(block);
                                }
                            }
                        } else {
                            this.discard();
                            this.callOnBrokenAfterFall(block, blockpos);
                        }
                    }
                } else if (!this.level().isClientSide
                    && (
                        this.time > 100 && (blockpos.getY() <= this.level().getMinBuildHeight() || blockpos.getY() > this.level().getMaxBuildHeight())
                            || this.time > 600
                    )) {
                    if (this.dropItem && this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                        this.spawnAtLocation(block);
                    }

                    this.discard();
                }
            }

            this.setDeltaMovement(this.getDeltaMovement().scale(0.98));
        }

    }

    private void callOnBrokenAfterFall(Block block, BlockPos blockpos) {
        if(block instanceof Risable) {
            ((Risable)block).onBrokenAfterFall(this.level(), blockpos, this);
        }
    }

    /**
     * [TODO:description]
     *
     * @param builder [TODO:parameter]
     * @throws throw new UnsupportedOperationException("Unimplemented method 'defineSynchedData'"); [TODO:throw]
     */
    @Override
    protected void defineSynchedData(Builder builder) {
        builder.define(DATA_START_POS, BlockPos.ZERO);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        compound.put("BlockState", NbtUtils.writeBlockState(this.blockState));
        compound.putInt("Time", this.time);
        compound.putBoolean("DropItem", this.dropItem);
        compound.putBoolean("HurtEntities", this.hurtEntities);
        compound.putFloat("FallHurtAmount", this.fallDamagePerDistance);
        compound.putInt("FallHurtMax", this.fallDamageMax);
        if (this.blockData != null) {
            compound.put("TileEntityData", this.blockData);
        }

        compound.putBoolean("CancelDrop", this.cancelDrop);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
this.blockState = NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK), compound.getCompound("BlockState"));
        this.time = compound.getInt("Time");
        if (compound.contains("HurtEntities", 99)) {
            this.hurtEntities = compound.getBoolean("HurtEntities");
            this.fallDamagePerDistance = compound.getFloat("FallHurtAmount");
            this.fallDamageMax = compound.getInt("FallHurtMax");
        } else if (this.blockState.is(BlockTags.ANVIL)) {
            this.hurtEntities = true;
        }

        if (compound.contains("DropItem", 99)) {
            this.dropItem = compound.getBoolean("DropItem");
        }

        if (compound.contains("TileEntityData", 10)) {
            this.blockData = compound.getCompound("TileEntityData").copy();
        }

        this.cancelDrop = compound.getBoolean("CancelDrop");
        if (this.blockState.isAir()) {
            this.blockState = Blocks.SAND.defaultBlockState();
        }
    }
}
