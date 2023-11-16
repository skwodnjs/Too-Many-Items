package net.jwn.jwn_items.mixin;

import net.jwn.jwn_items.util.Timer;
import net.jwn.jwn_items.animation.ModAnimations;
import net.minecraft.client.model.DrownedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(DrownedModel.class)
public class MixinDrownedModel<T extends LivingEntity> extends HumanoidModel<T> {

    public MixinDrownedModel(ModelPart pRoot) {
        super(pRoot);
    }

    @Inject(at = @At("TAIL"), method = "setupAnim(Lnet/minecraft/world/entity/monster/Zombie;FFFFF)V")
    public void setupAnime(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, CallbackInfo ci) {
        if (pEntity instanceof Timer timer) {
            if (pEntity.getTags().contains("ahchoo")) {
                if (timer.getTime("ahchooClient" + pEntity.getId()) != -1) {
                    ModAnimations.animateAhchoo(this.head, this.body, this.leftArm, this.rightArm, timer.getTime("ahchooClient" + pEntity.getId()));
                }
            }
            else if (pEntity.getTags().contains("shocked")) {
                if (timer.getTime("shockedClient" + pEntity.getId()) != -1) {
                    ModAnimations.animateShocked(this.head, this.leftArm, this.rightArm, this.leftLeg, this.rightLeg);
                }
            }
        }
    }
}
