package net.jwn.jwn_items.animation;

import net.minecraft.client.model.geom.ModelPart;

public class ModAnimations {
    // 몸통 기준
    // x: 오른쪽
    // y: 아래
    // z: 앞
    // xRot: 숙임
    // yRot: 항공샷 기준 반시계방향
    // zRot: 정면 기준 반시계방향
    public static void animateAhchoo(ModelPart pHead, ModelPart pBody, ModelPart pLeftArm, ModelPart pRightArm, int time) {
        if (time <= 10) {
            pBody.xRot = (float) Math.toRadians(-time);
            pBody.z = (float) (0.2 * time);

            pHead.z = (float) (0.2 * time);

            pLeftArm.y = 2;
            pRightArm.y = 2;
            pLeftArm.xRot = (float) Math.toRadians(-2 * (time));
            pRightArm.xRot = (float) Math.toRadians(-2 * (time));
        } else if (time <= 20) {
            pBody.xRot = (float) Math.toRadians(-10);
            pBody.z = 2;

            pHead.z = 2;
            pHead.xRot = (float) Math.toRadians(-2 * (time - 10));

            pLeftArm.y = 2;
            pRightArm.y = 2;
            pLeftArm.xRot = (float) Math.toRadians(-2 * (time));
            pRightArm.xRot = (float) Math.toRadians(-2 * (time));
        } else if (time <= 23) {
            pBody.xRot = (float) Math.toRadians(-10 + (40.0 / 3.0) * (time - 20));
            pBody.z = (float) (2 - (7.0 / 3.0) * (time - 20));
            pBody.y = (float) (1.5 / 3.0) * (time - 20);

            pHead.xRot = (float) Math.toRadians(-20 + (60.0 / 3.0) * (time - 20));
            pHead.z = (float) (2 - (7.0 / 3.0) * (time - 20));
            pHead.y = (float) ((1.0 / 3.0) * (time - 20));

            pLeftArm.y = 2;
            pRightArm.y = 2;
            pLeftArm.z = (float) (2 - (7.0 / 3.0) * (time - 20));
            pRightArm.z = (float) (2 - (7.0 / 3.0) * (time - 20));
            pRightArm.xRot = (float) Math.toRadians(30);
            pLeftArm.xRot = (float) Math.toRadians(30);
        } else if (time <= 26) {
            pBody.xRot = (float) Math.toRadians(30);
            pBody.z = -5;
            pBody.y = 1.5F;

            pHead.xRot = (float) Math.toRadians(40 + (10.0 / 3.0) * (time - 23));
            pHead.z = -5;
            pHead.y = 1;

            pLeftArm.y = 2;
            pRightArm.y = 2;
            pLeftArm.z = -5;
            pRightArm.z = -5;
            pRightArm.xRot = (float) Math.toRadians(30);
            pLeftArm.xRot = (float) Math.toRadians(30);
        } else if (time <= 30) {
            double xRot = Math.toRadians(30 - (30.0 / 4.0) * (time - 26));
            pBody.xRot = (float) xRot;
            pBody.z = (float) (-5 + (5.0 / 4.0) * (time - 26));
            pBody.y = (float) (1.5 - (1.5 / 4.0) * (time - 26));

            pHead.xRot = (float) (50 - (50.0 / 4.0) * (time - 26));
            pHead.z = (float) (-5 + (5.0 / 4.0) * (time - 26));
            pHead.y = (float) (1 - (1.0 / 4.0) * (time - 26));

            pLeftArm.y = 2;
            pRightArm.y = 2;
            pLeftArm.z = (float) (-5 + (5.0 / 4.0) * (time - 26));
            pRightArm.z = (float) (-5 + (5.0 / 4.0) * (time - 26));
            pRightArm.xRot = (float) xRot;
            pLeftArm.xRot = (float) xRot;
        } else {
            pRightArm.xRot = 0;
            pLeftArm.xRot = 0;
        }
    }

    public static void animateShocked(ModelPart pHead,ModelPart pLeftArm, ModelPart pRightArm, ModelPart pLeftLeg, ModelPart pRightLeg) {
        pHead.xRot = (float) Math.toRadians(-20);
        pHead.zRot = (float) Math.toRadians((Math.random() > 0.9) ? 10 : 20);

        pLeftArm.xRot = (float) Math.toRadians(150 + ((Math.random() > 0.9) ? 10 : 0));
        pRightArm.xRot = (float) Math.toRadians(210 - ((Math.random() > 0.9) ? 10 : 0));

        pLeftLeg.xRot = (float) Math.toRadians(-30 + ((Math.random() > 0.9) ? 10 : 0));
        pRightLeg.xRot = (float) Math.toRadians(30 - ((Math.random() > 0.9) ? 10 : 0));

        pLeftLeg.zRot = (float) Math.toRadians(-20);
        pRightLeg.zRot = (float) Math.toRadians(20);
    }
}
