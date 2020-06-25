package net.soe.shipyardcompanies.shipyards;

import api.faction.Faction;
import org.schema.game.server.data.blueprintnw.BlueprintEntry;

public class ShipyardOrder {

    private ShipyardCompany designer;
    private Faction customer;
    private BlueprintEntry blueprint;
    private int productionCost;
    private int price;
    private double completionPercent;
    private boolean completed;

    public ShipyardOrder(ShipyardCompany designer, Faction customer, BlueprintEntry blueprint, int productionCost) {
        this.designer = designer;
        this.customer = customer;
        this.blueprint = blueprint;
        this.productionCost = productionCost;
        price = (int) blueprint.getPrice() + productionCost;
        completionPercent = 0.0;
        completed = false;
    }

    public ShipyardCompany getDesigner() {
        return designer;
    }

    public Faction getCustomer() {
        return customer;
    }

    public BlueprintEntry getBlueprint() {
        return blueprint;
    }

    public int getProductionCost() {
        return productionCost;
    }

    public int getPrice() {
        return price;
    }

    public boolean isCompleted() {
        return completed;
    }

    public double getCompletionPercent() {
        return completionPercent;
    }

    public void tickCompletionPercent(double amount) {
        if(!completed) {
            if(completionPercent + amount > 100.0) {
                completionPercent = 100.0;
                completed = true;
            } else {
                completionPercent += amount;
            }
        }
    }
}
