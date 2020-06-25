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
        GUIAncor companyList = new GUIAncor(inputState, 80.0F, 200.0F);
        ArrayList<ShipyardCompany> companies = ShipyardCompanies.getInst().getCompanies();

        //Debug Testing
        companies.add(testCompany);

        for(ShipyardCompany company : companies) {
            GUIHorizontalArea.HButtonType buttonStyle = GUIHorizontalArea.HButtonType.BUTTON_NEUTRAL_NORMAL; //Neutral to Faction (Can order from them, Default Style)
            /*
            //Todo:Need to check if player is actually in a faction for the below to work, will implement later
            if(company.isNpcOwned() && company.getOwnerNPCFaction().getEnemies() != null && company.getOwnerNPCFaction().getEnemies().contains(this.getOwnFaction())) {
                buttonStyle = GUIHorizontalArea.HButtonType.BUTTON_RED_LIGHT; //Enemy to NPC Faction (Cant order from them)
            } else if(!(company.isNpcOwned() && company.getOwnerFaction().getEnemies() == null) && company.getOwnerFaction().getEnemies().contains(new Faction(this.getOwnFaction()))) {
                buttonStyle = GUIHorizontalArea.HButtonType.BUTTON_RED_MEDIUM; //Enemy to Player Faction (Cant order from them)
            } else if(!(company.isNpcOwned()) && company.getOwnerFaction().getAllies() != null && company.getOwnerFaction().getAllies().contains(new Faction(this.getOwnFaction()))) {
                buttonStyle = GUIHorizontalArea.HButtonType.BUTTON_BLUE_MEDIUM; //Ally to Player Faction (Can order from them)
            }
             */

            String factionName = "";
            if(company.isNpcOwned()) {
                factionName = " - [" + company.getOwnerNPCFaction().getName() + "]";
            } else {
                factionName = " - [" + company.getOwnerFaction().getName() + "]";
            }
            String companyText = company.getName() + factionName + "\n" + company.getDescription() + "\n" + company.getTechFocus().getDisplayName() + "\n" + company.getTechFocus().getDescription();
            GUITextOverlay companyDescription;
            (companyDescription = new GUITextOverlay(10, 10, FontLibrary.getBlenderProBook16(), this.getState())).setTextSimple(companyText);
            companyDescription.setPos(4.0F, 4.0F, 0.0F);

            companyList.attach(companyDescription);
        }

        GUIScrollablePanel companyPanel = new GUIScrollablePanel(80.0F, 200.0F, inputState);
        companyPanel.setContent(companyList);
        this.shipyardsTab.getContent(1).attach(companyPanel);
    }
}