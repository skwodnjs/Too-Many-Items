package net.jwn.jwn_items.mixin;

import net.jwn.jwn_items.Timer;
import net.jwn.jwn_items.animation.ModAnimations;
import net.minecraft.client.model.AbstractZombieModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.monster.Monster;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractZombieModel.class)
public class MixinAbstractZombieModel<T extends Monster> extends HumanoidModel<T> {
    public MixinAbstractZombieModel(ModelPart pRoot) {
        super(pRoot);
    }

    @Inject(at = @At("TAIL"), method = "setupAnim(Lnet/minecraft/world/entity/monster/Monster;FFFFF)V")
    public void setupAnime(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, CallbackInfo ci) {
        if (pEntity instanceof Timer timer) {
            if (pEntity.getTags().contains("ahchoo")) {
                if (timer.getTime("ahchooClient") != -1) {
                    ModAnimations.animateAhchoo(this.head, this.body, this.leftArm, this.rightArm, timer.getTime("ahchooClient"));
                }
            }
            else if (pEntity.getTags().contains("shocked")) {
                if (timer.getTime("shockedClient") != -1) {
                    ModAnimations.animateShocked(this.head, this.leftArm, this.rightArm, this.leftLeg, this.rightLeg);
                }
            }
        }
    }
}
