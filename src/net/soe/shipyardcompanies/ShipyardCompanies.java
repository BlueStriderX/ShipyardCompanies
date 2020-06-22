package net.soe.shipyardcompanies;

import api.DebugFile;
import api.listener.Listener;
import api.listener.events.Event;
import api.listener.events.gui.ControlManagerActivateEvent;
import api.mod.StarLoader;
import api.mod.StarMod;
import net.soe.shipyardcompanies.gui.NewFactionPanel;
import net.soe.shipyardcompanies.gui.NewShopPanel;
import org.schema.game.client.data.GameClientState;
import org.schema.game.client.view.gui.PlayerPanel;
import org.schema.game.client.view.gui.faction.newfaction.FactionPanelNew;
import org.schema.game.client.view.gui.shop.shopnew.ShopPanelNew;
import java.lang.reflect.Field;

public class ShipyardCompanies extends StarMod {
    static ShipyardCompanies inst;
    public ShipyardCompanies() {
        inst = this;
    }

    public static void main(String[] args) {

    }

    @Override
    public void onGameStart() {
        this.modName = "ShipyardCompanies";
        this.modAuthor = "DovTech";
        this.modVersion = "0.1.0";
        this.modDescription = "Adds new functionality for shops and shipyards.";
    }

    @Override
    public void onEnable() {
        super.onEnable();
        DebugFile.log("Enabled", this);
        registerListeners();
    }

    private void registerListeners() {

        //GUI Modifiers
        StarLoader.registerListener(ControlManagerActivateEvent.class, new Listener() {
            @Override
            public void onEvent(Event e) {
                PlayerPanel playerPanel = GameClientState.instance.getWorldDrawer().getGuiDrawer().getPlayerPanel();
                playerPanel.onInit();
                GameClientState state = playerPanel.getState();
                try {
                    //Faction Panel Design Companies Tab
                    Field designCompaniesPanelField = PlayerPanel.class.getDeclaredField("factionPanelNew");
                    designCompaniesPanelField.setAccessible(true);
                    FactionPanelNew factionPanelNew = (FactionPanelNew) designCompaniesPanelField.get(playerPanel);
                    if(!(factionPanelNew instanceof NewFactionPanel)) {
                        designCompaniesPanelField.set(playerPanel, new NewFactionPanel(state));
                    }

                    //Shop Panel Shipyards Tab
                    Field shipyardsPanelField = PlayerPanel.class.getDeclaredField("shopPanelNew");
                    shipyardsPanelField.setAccessible(true);
                    ShopPanelNew shopPanelNew = (ShopPanelNew) shipyardsPanelField.get(playerPanel);
                    if(!(shopPanelNew instanceof NewShopPanel)) {
                        shipyardsPanelField.set(playerPanel, new NewShopPanel(state));
                    }
                } catch (NoSuchFieldException ex) {
                    ex.printStackTrace();
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}