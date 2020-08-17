package net.soe.shipyardcompanies.shipyards;

import api.faction.Faction;
import net.soe.shipyardcompanies.ShipyardCompanies;
import org.schema.game.server.data.simulation.npc.NPCFaction;
import java.util.ArrayList;

public class ShipyardCompany {

    private Faction ownerFaction;
    private NPCFaction ownerNPCFaction;
    private boolean npcOwned;
    private String name;
    private String description;
    private CompanyTechFocus techFocus;
    private ArrayList<ShipyardOrder> orders;
    private ArrayList<Faction> clients;

    public ShipyardCompany(Faction ownerFaction, String name, String description, CompanyTechFocus techFocus) {
        this.ownerFaction = ownerFaction;
        this.name = name;
        this.description = description;
        this.techFocus = techFocus;
        ownerNPCFaction = null;
        npcOwned = false;
        orders = new ArrayList<ShipyardOrder>();
        clients = new ArrayList<Faction>();
    }

    public ShipyardCompany(NPCFaction ownerNPCFaction, String name, String description, CompanyTechFocus techFocus) {
        this.ownerNPCFaction = ownerNPCFaction;
        this.name = name;
        this.description = description;
        this.techFocus = techFocus;
        ownerFaction = null;
        npcOwned = true;
        orders = new ArrayList<ShipyardOrder>();
        clients = new ArrayList<Faction>();
    }

    public ArrayList<ShipyardOrder> getOrders() {
        return orders;
    }

    public void placeOrder(ShipyardOrder order, Faction client) {
        //Todo: Add Order
        this.orders.add(order);
        this.clients.add(client);
    }

    public void cancelOrder(ShipyardOrder order, Faction client) {
        //Todo: Remove Order
        this.orders.remove(order);
        this.orders.remove(client);
    }

    public ArrayList<Faction> getClients() {
        return clients;
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
