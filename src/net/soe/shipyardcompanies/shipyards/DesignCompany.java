package net.soe.shipyardcompanies.shipyards;

import api.faction.Faction;

public class DesignCompany {

    private Faction ownerFaction;
    private String name;
    private String description;
    private CompanyTechFocus techFocus;

    public DesignCompany(Faction ownerFaction, String name, String description, CompanyTechFocus techFocus) {
        this.ownerFaction = ownerFaction;
        this.name = name;
        this.description = description;
        this.techFocus = techFocus;
    }

    public Faction getOwnerFaction() {
        return ownerFaction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CompanyTechFocus getTechFocus() {
        return techFocus;
    }

    public void setTechFocus(CompanyTechFocus techFocus) {
        this.techFocus = techFocus;
    }
}
