package net.soe.shipyardcompanies.gui;

import api.faction.Faction;
import net.soe.shipyardcompanies.ShipyardCompanies;
import net.soe.shipyardcompanies.shipyards.CompanyTechFocus;
import net.soe.shipyardcompanies.shipyards.ShipyardCompany;
import net.soe.shipyardcompanies.shipyards.ShipyardOrder;
import org.schema.game.client.view.gui.shop.shopnew.ShopPanelNew;
import org.schema.game.server.controller.BluePrintController;
import org.schema.game.server.data.blueprintnw.BlueprintEntry;
import org.schema.schine.graphicsengine.forms.font.FontLibrary;
import org.schema.schine.graphicsengine.forms.gui.*;
import org.schema.schine.graphicsengine.forms.gui.newgui.*;
import org.schema.schine.input.InputState;
import java.util.ArrayList;
import java.util.HashSet;

public class NewShopPanel extends ShopPanelNew {

    private GUIContentPane shipyardsTab;
    private InputState inputState;

    public NewShopPanel(InputState inputState) {
        super(inputState);
        this.inputState = inputState;
    }

    @Override
    public void recreateTabs() {
        super.recreateTabs();
        shipyardsTab = this.shopPanel.addTab("SHIPYARDS");
        createShipyardsPane();
    }

    private void createShipyardsPane() {
        this.shipyardsTab.setTextBoxHeightLast(200);
        HashSet<ShipyardOrder> orders = new HashSet<ShipyardOrder>();

        //Debug Testing
        String testDescription = "THE DOVAN PEOPLE ARE THE GREATEST IN ALL THE UNIVERSE";
        ShipyardCompany testCompany = new ShipyardCompany(new Faction(this.getOwnFaction()), "Dovan Lawncare Services", testDescription, CompanyTechFocus.MUNITIONS);
        BlueprintEntry testBlueprint = BluePrintController.stationsTradingGuild.readBluePrints().get(0);
        orders.add(new ShipyardOrder(testCompany, new Faction(this.getOwnFaction()), testBlueprint, 300000));

        OrdersScrollableList ordersList;
        (ordersList = new OrdersScrollableList(inputState, 80.0F, 200.0F, this.shipyardsTab.getContent(0), orders)).onInit();
        GUIElementList orderElementList = new GUIElementList(inputState);
        ordersList.updateListEntries(orderElementList, orders);
        this.shipyardsTab.getContent(0).attach(ordersList);

        this.shipyardsTab.addNewTextBox(200);
        HashSet<ShipyardCompany> companies = new HashSet<ShipyardCompany>();
        companies.add(testCompany); //For Debug Testing
        companies.addAll(ShipyardCompanies.getInst().getCompanies());
        CompaniesScrollableList companiesList;
        (companiesList = new CompaniesScrollableList(inputState, 80.0F, 200.0F, this.shipyardsTab.getContent(1), companies)).onInit();
        this.shipyardsTab.getContent(1).attach(companiesList);
    }
}