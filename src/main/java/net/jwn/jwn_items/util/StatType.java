package net.jwn.jwn_items.util;

public enum StatType {
    HEALTH_BY_CONSUMABLES,
    DAMAGE_BY_CONSUMABLES,
    ATTACK_SPEED_BY_CONSUMABLES,
    ATTACK_RANGE_BY_CONSUMABLES,
    MINING_SPEED_BY_CONSUMABLES,
    MOVEMENT_SPEED_BY_CONSUMABLES,
    LUCK_BY_CONSUMABLES,

    HEALTH_BY_ITEM,
    DAMAGE_BY_ITEM,
    ATTACK_SPEED_BY_ITEM,
    ATTACK_RANGE_BY_ITEM,
    MINING_SPEED_BY_ITEM,
    MOVEMENT_SPEED_BY_ITEM,
    LUCK_BY_ITEM,

    COIN;

    public static StatType getStatTypeById(int id) {
        for (StatType type: StatType.values()) {
            if (type.ordinal() == id) return type;
        } return null;
    }

    public String getName() {
        return switch (this) {
            case HEALTH_BY_CONSUMABLES, HEALTH_BY_ITEM -> "health";
            case DAMAGE_BY_CONSUMABLES, DAMAGE_BY_ITEM -> "damage";
            case ATTACK_SPEED_BY_CONSUMABLES, ATTACK_SPEED_BY_ITEM -> "attack_speed";
            case ATTACK_RANGE_BY_CONSUMABLES, ATTACK_RANGE_BY_ITEM -> "attack_range";
            case MINING_SPEED_BY_CONSUMABLES, MINING_SPEED_BY_ITEM -> "mining_speed";
            case MOVEMENT_SPEED_BY_CONSUMABLES, MOVEMENT_SPEED_BY_ITEM -> "movement_speed";
            case LUCK_BY_CONSUMABLES, LUCK_BY_ITEM -> "luck";
            case COIN -> "coin";
        };
    }
}
