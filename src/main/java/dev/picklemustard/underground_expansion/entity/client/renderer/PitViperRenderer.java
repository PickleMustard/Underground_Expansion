package dev.picklemustard.underground_expansion.entity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import dev.picklemustard.underground_expansion.entity.client.model.PitViperModel;
import dev.picklemustard.underground_expansion.entity.custom.PitViper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class PitViperRenderer extends GeoEntityRenderer<PitViper> {

    public PitViperRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new PitViperModel());
    }

    @Override
    public void render(PitViper entity, float entityYaw, float partialTick, PoseStack poseStack,
            MultiBufferSource bufferSource, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }



}
