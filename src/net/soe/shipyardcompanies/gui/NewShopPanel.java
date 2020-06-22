package net.soe.shipyardcompanies.gui;

import org.schema.game.client.view.gui.shop.shopnew.ShopPanelNew;
import org.schema.schine.graphicsengine.forms.font.FontLibrary;
import org.schema.schine.graphicsengine.forms.gui.GUITextOverlay;
import org.schema.schine.graphicsengine.forms.gui.newgui.GUIContentPane;
import org.schema.schine.input.InputState;

public class NewShopPanel extends ShopPanelNew {

    private GUIContentPane shipyardsTab;

    public NewShopPanel(InputState inputState) {
        super(inputState);
    }

    @Override
    public void recreateTabs() {
        super.recreateTabs();
        shipyardsTab = this.shopPanel.addTab("SHIPYARDS");
        createShipyardsPane();
    }

    private void createShipyardsPane() {
        GUITextOverlay textOverlay;
        (textOverlay = new GUITextOverlay(10, 10, FontLibrary.getBlenderProMedium20(), this.getState())).setTextSimple("SHIPYARDS");

        this.shipyardsTab.setTextBoxHeightLast(110);
        this.shipyardsTab.addNewTextBox(10);
        textOverlay.setPos(4.0F, 4.0F, 0.0F);
        this.shipyardsTab.getContent(0).attach(textOverlay);
    }
}