package net.jwn.jwn_items.stat;

public class Stat {
    private final StatType statType;
    private float value;

    public Stat(StatType statType, float value) {
        this.statType = statType;
        if (statType == StatType.HEALTH_BY_CONSUMABLES || statType == StatType.HEALTH_BY_ITEM) {
            this.value = Math.round(value * 2.0f) / 2.0f;
        } else if (statType == StatType.COIN) {
            this.value = Math.round(value);
        } else {
            this.value = Math.round(value * 10.0f) / 10.0f;
        }
    }

    public void setValue(float value) {
        float newValue;
        if (statType == StatType.HEALTH_BY_CONSUMABLES || statType == StatType.HEALTH_BY_ITEM) {
            newValue = Math.round(value * 2.0f) / 2.0f;
        } else if (statType == StatType.COIN) {
            newValue = Math.round(value);
        } else {
            newValue = Math.round(value * 10.0f) / 10.0f;
        }
        this.value = newValue;
    }

    public StatType getStatType() {
        return statType;
    }

    public float getValue() {
        return value;
    }
}
