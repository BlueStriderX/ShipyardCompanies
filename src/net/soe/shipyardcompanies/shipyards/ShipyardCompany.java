package net.soe.shipyardcompanies.shipyards;

import api.faction.Faction;
import org.schema.game.server.data.simulation.npc.NPCFaction;

public class ShipyardCompany {

    private Faction ownerFaction;
    private NPCFaction ownerNPCFaction;
    private boolean npcOwned;
    private String name;
    private String description;
    private CompanyTechFocus techFocus;

    public ShipyardCompany(Faction ownerFaction, String name, String description, CompanyTechFocus techFocus) {
        this.ownerFaction = ownerFaction;
        this.name = name;
        this.description = description;
        this.techFocus = techFocus;
        ownerNPCFaction = null;
        npcOwned = false;
    }

    public ShipyardCompany(NPCFaction ownerNPCFaction, String name, String description, CompanyTechFocus techFocus) {
        this.ownerNPCFaction = ownerNPCFaction;
        this.name = name;
        this.description = description;
        this.techFocus = techFocus;
        ownerFaction = null;
        npcOwned = true;
    }

    public Faction getOwnerFaction() {
        if(!(npcOwned)) {
            return ownerFaction;
        }
        return null;
    }

    public NPCFaction getOwnerNPCFaction() {
        if(npcOwned) {
            return ownerNPCFaction;
        }
        return null;
    }

    public boolean isNpcOwned() {
        return npcOwned;
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
