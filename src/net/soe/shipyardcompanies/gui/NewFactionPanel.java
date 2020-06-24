package net.soe.shipyardcompanies.gui;

import api.faction.Faction;
import net.soe.shipyardcompanies.shipyards.CompanyTechFocus;
import net.soe.shipyardcompanies.shipyards.ShipyardCompany;
import org.schema.game.client.view.gui.faction.newfaction.FactionPanelNew;
import org.schema.schine.graphicsengine.forms.font.FontLibrary;
import org.schema.schine.graphicsengine.forms.gui.GUITextOverlay;
import org.schema.schine.graphicsengine.forms.gui.newgui.GUIContentPane;
import org.schema.schine.input.InputState;

public class NewFactionPanel extends FactionPanelNew {

    private GUIContentPane designCompaniesTab;

    public NewFactionPanel(InputState inputState) {
        super(inputState);
    }

    @Override
    public void recreateTabs() {
        super.recreateTabs();
        designCompaniesTab = this.factionPanel.addTab("SHIPYARDS");
        createDesignCompaniesPane();
    }

    private void createDesignCompaniesPane() {
        /*
        GUITextOverlay textOverlay;
        (textOverlay = new GUITextOverlay(10, 10, FontLibrary.getBlenderProMedium20(), this.getState())).setTextSimple("DESIGN COMPANIES");

        this.designCompaniesTab.setTextBoxHeightLast(110);
        this.designCompaniesTab.addNewTextBox(10);
        textOverlay.setPos(4.0F, 4.0F, 0.0F);
        this.designCompaniesTab.getContent(0).attach(textOverlay);
         */

        //Debug Testing
        String testDescription = "A Dovan design company focused on energy and electronics. They are well known for their computer terminals and DovOS.";
        ShipyardCompany testCompany = new ShipyardCompany(new Faction(this.getOwnFaction()), "DovTech", testDescription, CompanyTechFocus.ENERGY);
        String companyText = testCompany.getName() + "\n" + testCompany.getDescription() + "\n" + testCompany.getTechFocus().getDisplayName() + "\n" + testCompany.getTechFocus().getDescription();


        GUITextOverlay testCompanyOverlay;
        (testCompanyOverlay = new GUITextOverlay(10, 10, FontLibrary.getBlenderProMedium20(), this.getState())).setTextSimple(companyText);
        this.designCompaniesTab.addNewTextBox(85);
        testCompanyOverlay.setPos(4.0F, 4.0F, 0.0F);
        this.designCompaniesTab.getContent(0).attach(testCompanyOverlay);
    }
}
