package net.soe.shipyardcompanies.shipyards;

public enum CompanyTechFocus {
    MOBILITY("Mobility Focus:",
            "+ 10% Speed\n" +
            "+ 12% Turn Rate"),
    DEFENSE("Defense Systems Focus:",
            "+ 15% Armor Protection\n" +
            "+ 10% Shield Regen"),
    MUNITIONS("Munitions Focus:",
            "+ 10% Cannon Speed\n" +
                    "+ 7% Cannon Penetration\n" +
                    "+ 7% Missile Explosion Size"),
    ENERGY("Energy Systems Focus:",
            "+ 10% Beam Range\n" +
                    "+ 7% Reactor HP\n" +
                    "+ 10% Reactor Regen"),
    AI("AI Systems Focus:",
            "+ 10% AI Range\n" +
                    "+ 15% AI Accuracy"),
    PHYSICS("Physics Focus:",
            "+ 10% Turret Turn Speed\n" +
                    "+ 10% Jump Drive Range\n" +
                    "+ 10% Jump Drive Charge Speed\n" +
                    "+ Jump Drive Autocharge"),
    INTERCEPTION("Interception Focus:",
            "+ 10% Scanner Range\n" +
                    "+ 15% Scanner Duration\n" +
                    "+ 1 Scanner Level\n" +
                    "+ 15% Jump Drive Inhibitor Range\n" +
                    "+ 1 Jump Drive Inhibitor Level");

    private String displayName;
    private String description;

    CompanyTechFocus(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }
}