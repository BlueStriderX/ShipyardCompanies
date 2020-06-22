package net.soe.shipyardcompanies.gui;

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
        designCompaniesTab = this.factionPanel.addTab("DESIGN COMPANIES");
        createDesignCompaniesPane();
    }

    private void createDesignCompaniesPane() {
        GUITextOverlay textOverlay;
        (textOverlay = new GUITextOverlay(10, 10, FontLibrary.getBlenderProMedium20(), this.getState())).setTextSimple("DESIGN COMPANIES");

        this.designCompaniesTab.setTextBoxHeightLast(110);
        this.designCompaniesTab.addNewTextBox(10);
        textOverlay.setPos(4.0F, 4.0F, 0.0F);
        this.designCompaniesTab.getContent(0).attach(textOverlay);
    }
}
