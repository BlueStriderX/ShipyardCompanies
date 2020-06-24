package net.soe.shipyardcompanies.gui;

import api.faction.Faction;
import net.soe.shipyardcompanies.ShipyardCompanies;
import net.soe.shipyardcompanies.shipyards.CompanyTechFocus;
import net.soe.shipyardcompanies.shipyards.ShipyardCompany;
import org.schema.game.client.view.gui.shop.shopnew.ShopPanelNew;
import org.schema.schine.graphicsengine.core.MouseEvent;
import org.schema.schine.graphicsengine.forms.font.FontLibrary;
import org.schema.schine.graphicsengine.forms.gui.*;
import org.schema.schine.graphicsengine.forms.gui.newgui.GUIContentPane;
import org.schema.schine.graphicsengine.forms.gui.newgui.GUIHorizontalArea;
import org.schema.schine.graphicsengine.forms.gui.newgui.GUIHorizontalButtonExpandable;
import org.schema.schine.input.InputState;

import java.util.ArrayList;

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
        /*
        GUITextOverlay textOverlay;
        (textOverlay = new GUITextOverlay(10, 10, FontLibrary.getBlenderProMedium20(), this.getState())).setTextSimple("SHIPYARDS");

        this.shipyardsTab.setTextBoxHeightLast(110);
        this.shipyardsTab.addNewTextBox(10);
        textOverlay.setPos(4.0F, 4.0F, 0.0F);
        this.shipyardsTab.getContent(0).attach(textOverlay);
         */
        this.shipyardsTab.setTextBoxHeightLast(85);

        GUITextOverlay ordersOverlay;
        (ordersOverlay = new GUITextOverlay(10, 10, FontLibrary.getBlenderProBook16(), this.getState())).setTextSimple("Current Orders:");
        ordersOverlay.setPos(4.0F, 4.0F, 0.0F);
        this.shipyardsTab.getContent(0).attach(ordersOverlay);

        GUIElementList companyList = new GUIElementList(inputState);
        ArrayList<ShipyardCompany> companies = ShipyardCompanies.getInst().getCompanies();

        //Debug Testing
        String testDescription = "THE DOVAN PEOPLE ARE THE GREATEST IN ALL THE UNIVERSE";
        companies.add(new ShipyardCompany(new Faction(this.getOwnFaction()), "Dovan Lawncare Services", testDescription, CompanyTechFocus.MUNITIONS));

        for(ShipyardCompany company : companies) {
            GUIListElement companyElement = new GUIListElement(inputState);
            GUIHorizontalArea.HButtonType buttonStyle = GUIHorizontalArea.HButtonType.BUTTON_NEUTRAL_NORMAL; //Neutral to Faction (Can order from them, Default Style)
            if(company.isNpcOwned() && company.getOwnerNPCFaction().getEnemies().contains(this.getOwnFaction())) { //Enemy to NPC Faction (Cant order from them)
                buttonStyle = GUIHorizontalArea.HButtonType.BUTTON_RED_LIGHT;
            } else if(company.isNpcOwned() && !(company.getOwnerNPCFaction().getEnemies().contains(this.getOwnFaction()))) { //Neutral to NPC Faction (Can order from them)
                buttonStyle = GUIHorizontalArea.HButtonType.BUTTON_BLUE_SCNLINE_GREY;
            } else if(company.getOwnerFaction().getAllies().contains(new Faction(this.getOwnFaction())) && !(company.isNpcOwned())) { //Ally to Player Faction (Can order from them)
                buttonStyle = GUIHorizontalArea.HButtonType.BUTTON_BLUE_MEDIUM;
            } else if(company.getOwnerFaction().getEnemies().contains(new Faction(this.getOwnFaction()))&& !(company.isNpcOwned())) { //Enemy to Player Faction (Cant order from them)
                buttonStyle = GUIHorizontalArea.HButtonType.BUTTON_RED_MEDIUM;
            }

            GUIHorizontalButtonExpandable companyButton = new GUIHorizontalButtonExpandable(this.getState(), buttonStyle, company.getName(), this);
            String companyText = company.getName() + "\n" + company.getDescription() + "\n" + company.getTechFocus().getDisplayName() + "\n" + company.getTechFocus().getDescription();
            GUITextOverlay companyDescription;
            (companyDescription = new GUITextOverlay(10, 10, FontLibrary.getBlenderProBook16(), this.getState())).setTextSimple(companyText);
            companyDescription.setPos(4.0F, 4.0F, 0.0F);
            companyButton.attach(companyDescription);
            companyElement.getContent().attach(companyButton);
            companyList.add(companyElement);
        }

        this.shipyardsTab.getContent(1).attach(companyList);

        /* Debug Testing
        String testDescription = "THE DOVAN PEOPLE ARE THE GREATEST IN ALL THE UNIVERSE";
        ShipyardCompany testCompany = new ShipyardCompany(new Faction(this.getOwnFaction()), "Dovan Lawncare Services", testDescription, CompanyTechFocus.MUNITIONS);
        String companyText = testCompany.getName() + "\n" + testCompany.getDescription() + "\n" + testCompany.getTechFocus().getDisplayName() + "\n" + testCompany.getTechFocus().getDescription();

        GUITextOverlay testCompanyOverlay;
        (testCompanyOverlay = new GUITextOverlay(10, 10, FontLibrary.getBlenderProBook16(), this.getState())).setTextSimple(companyText);
        this.shipyardsTab.addNewTextBox(85);
        testCompanyOverlay.setPos(4.0F, 4.0F, 0.0F);
        this.shipyardsTab.getContent(1).attach(testCompanyOverlay);
         */
    }
}