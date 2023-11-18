package net.jwn.jwn_items.item;

public enum Quality {
    COMMON(0),
    UNCOMMON(1),
    RARE(2),
    EPIC(3),
    LEGENDARY(4);

    public final int level;
    private Quality(int level) {
        this.level = level;
    }
    public Quality add(int value) {
        if (level + value >= 4) return LEGENDARY;
        else if (level + value == 3) return EPIC;
        else if (level + value == 2) return RARE;
        else if (level + value == 1) return UNCOMMON;
        else return COMMON;
    }
}
