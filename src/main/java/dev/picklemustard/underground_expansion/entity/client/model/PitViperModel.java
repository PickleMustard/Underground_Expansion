package dev.picklemustard.underground_expansion.entity.client.model;

import dev.picklemustard.underground_expansion.UndergroundExpansion;
import dev.picklemustard.underground_expansion.entity.custom.PitViper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class PitViperModel extends GeoModel<PitViper> {
    private String namespace = UndergroundExpansion.MODID;

    @Override
    public ResourceLocation getModelResource(PitViper animatable) {
        return ResourceLocation.fromNamespaceAndPath(namespace, "geo/pit_viper.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PitViper animatable) {
        return ResourceLocation.fromNamespaceAndPath(namespace, "textures/entity/pit_viper.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PitViper animatable) {
        return ResourceLocation.fromNamespaceAndPath(namespace, "animations/pit_viper.animation.json");
    }

    @Override
    public void setCustomAnimations(PitViper animatable, long instanceID, AnimationState<PitViper> animationState){
        GeoBone head = getAnimationProcessor().getBone("face");

        if(head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }


}
