package model;

public enum HardnessLevel {
    EASY(1.5, 80, 150, 20),
    AVERAGE(3, 160, 300, 15),
    HARD(4.5, 240, 450, 10);

    private final double tankSpeed;
    private final int migRadius, tankRadius, migComeTime;

    HardnessLevel(double tankSpeed, int migRadius, int tankRadius, int migComeTime) {
        this.migRadius = migRadius;
        this.tankRadius = tankRadius;
        this.tankSpeed = tankSpeed;
        this.migComeTime = migComeTime;
    }

    public static int getMigRadius(String level) {
        for (HardnessLevel hardnessLevel : HardnessLevel.values())
            if (hardnessLevel.name().equalsIgnoreCase(level))
                return hardnessLevel.migRadius;
        return 0;
    }

    public static int getTankRadius(String level) {
        for (HardnessLevel hardnessLevel : HardnessLevel.values())
            if (hardnessLevel.name().equalsIgnoreCase(level))
                return hardnessLevel.tankRadius;
        return 0;
    }

    public static int getMigComeTime(String level) {
        for (HardnessLevel hardnessLevel : HardnessLevel.values())
            if (hardnessLevel.name().equalsIgnoreCase(level))
                return hardnessLevel.migComeTime;
        return 0;
    }

    public static double getTankSpeed(String level) {
        for (HardnessLevel hardnessLevel : HardnessLevel.values())
            if (hardnessLevel.name().equalsIgnoreCase(level))
                return hardnessLevel.tankSpeed;
        return 0;
    }
}