package net.jwn.jwn_items.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    public static final String KEY_CATEGORY_MOD = "key.category.jwn_items.utils";

    public static final String KEY_ACTIVE_SKILL = "key.jwn_items.active";
    public static final KeyMapping ACTIVE_SKILL_KEY =
            new KeyMapping(KEY_ACTIVE_SKILL, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, KEY_CATEGORY_MOD);

    public static final String KEY_STATS = "key.jwn_items.stats";
    public static final KeyMapping STATS_KEY =
            new KeyMapping(KEY_STATS, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Z, KEY_CATEGORY_MOD);

    public static final String KEY_MY_STUFF = "key.jwn_items.my_stuff";
    public static final KeyMapping MY_STUFF_KEY =
            new KeyMapping(KEY_MY_STUFF, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_I, KEY_CATEGORY_MOD);

    public static final String KEY_ACTIVE_CHANGE = "key.jwn_items.active_change";
    public static final KeyMapping ACTIVE_CHANGE_KEY =
            new KeyMapping(KEY_ACTIVE_CHANGE, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_C, KEY_CATEGORY_MOD);

    public static final String KEY_FOUND_STUFF = "key.jwn_items.found_stuff";
    public static final KeyMapping FOUND_STUFF_KEY =
            new KeyMapping(KEY_FOUND_STUFF, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_X, KEY_CATEGORY_MOD);
}