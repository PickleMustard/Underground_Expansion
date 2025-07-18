package dev.picklemustard.underground_expansion.entity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;

import org.slf4j.Logger;

import dev.picklemustard.underground_expansion.entity.custom.RisingBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RisingBlockRenderer extends EntityRenderer<RisingBlockEntity> {

    private final BlockRenderDispatcher dispatcher;
    private final Logger LOGGER = LogUtils.getLogger();

    public RisingBlockRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.5f;
        this.dispatcher = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(RisingBlockEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        BlockState blockstate = entity.getBlockState();
        if (blockstate.getRenderShape() == RenderShape.MODEL) {
            Level level = entity.level();
            if (blockstate != level.getBlockState(entity.blockPosition()) && blockstate.getRenderShape() != RenderShape.INVISIBLE) {
                poseStack.pushPose();
                BlockPos blockpos = BlockPos.containing(entity.getX(), entity.getBoundingBox().maxY, entity.getZ());
                poseStack.translate(-0.5, 0.0, -0.5);
                var model = this.dispatcher.getBlockModel(blockstate);
                for (var renderType : model.getRenderTypes(blockstate, RandomSource.create(blockstate.getSeed(entity.getStartPos())), net.neoforged.neoforge.client.model.data.ModelData.EMPTY))
                this.dispatcher
                    .getModelRenderer()
                    .tesselateBlock(
                        level,
                        this.dispatcher.getBlockModel(blockstate),
                        blockstate,
                        blockpos,
                        poseStack,
                        buffer.getBuffer(net.neoforged.neoforge.client.RenderTypeHelper.getMovingBlockRenderType(renderType)),
                        false,
                        RandomSource.create(),
                        blockstate.getSeed(entity.getStartPos()),
                        OverlayTexture.NO_OVERLAY,
                        net.neoforged.neoforge.client.model.data.ModelData.EMPTY,
                        renderType
                    );
                poseStack.popPose();
                super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
            }
        }
    }

    @Override
    public ResourceLocation getTextureLocation(RisingBlockEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }

}
