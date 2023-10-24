package net.jwn.jwn_items.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    public static final String KEY_CATEGORY_UTILS = "key.category.jwn_items.utils";

    public static final String KEY_ONE = "key.jwn_items.one";
    public static final KeyMapping ONE_KEY =
            new KeyMapping(KEY_ONE, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, KEY_CATEGORY_UTILS);

    public static final String KEY_TWO = "key.jwn_items.two";
    public static final KeyMapping TWO_KEY =
            new KeyMapping(KEY_TWO, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_F, KEY_CATEGORY_UTILS);

    public static final String KEY_THREE = "key.jwn_items.three";
    public static final KeyMapping THREE_KEY =
            new KeyMapping(KEY_THREE, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Z, KEY_CATEGORY_UTILS);
}