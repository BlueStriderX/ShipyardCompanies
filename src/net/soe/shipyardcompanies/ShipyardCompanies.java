package net.soe.shipyardcompanies;

import api.DebugFile;
import api.listener.Listener;
import api.listener.events.gui.ControlManagerActivateEvent;
import api.mod.StarLoader;
import api.mod.StarMod;
import net.soe.shipyardcompanies.gui.NewShopPanel;
import net.soe.shipyardcompanies.shipyards.ShipyardCompany;
import org.schema.game.client.data.GameClientState;
import org.schema.game.client.view.gui.PlayerPanel;
import org.schema.game.client.view.gui.shop.shopnew.ShopPanelNew;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class ShipyardCompanies extends StarMod {
    static ShipyardCompanies inst;
    public ShipyardCompanies() {
        inst = this;
    }
    private ArrayList<ShipyardCompany> companies = new ArrayList<ShipyardCompany>();
    private boolean debug;

    public static void main(String[] args) {

    }

    @Override
    public void onGameStart() {
        this.modName = "ShipyardCompanies";
        this.modAuthor = "DovTech";
        this.modVersion = "0.1.2";
        this.modDescription = "Adds new functionality for shops and shipyards.";
    }

    @Override
    public void onEnable() {
        super.onEnable();
        DebugFile.log("Enabled", this);
        debug = true;

        //GUI Modifiers
        StarLoader.registerListener(ControlManagerActivateEvent.class, new Listener<ControlManagerActivateEvent>() {
            @Override
            public void onEvent(ControlManagerActivateEvent event) {
                PlayerPanel playerPanel = GameClientState.instance.getWorldDrawer().getGuiDrawer().getPlayerPanel();
                if(event.isActive()) {
                    if (debug) DebugFile.log("[DEBUG]: ControlManagerActivateEvent Fired");
                    try {
                        /*
                        //Faction Panel Design Companies Tab
                        Field designCompaniesPanelField = PlayerPanel.class.getDeclaredField("factionPanelNew");
                        designCompaniesPanelField.setAccessible(true);
                        FactionPanelNew factionPanelNew = (FactionPanelNew) designCompaniesPanelField.get(playerPanel);
                        if (!(factionPanelNew instanceof NewFactionPanel)) {
                            GameClientState state = playerPanel.getState();
                            designCompaniesPanelField.set(playerPanel, new NewFactionPanel(state));
                            if (debug) DebugFile.log("[DEBUG]: Swapped out FactionPanelNew");
                        }
                         */

                        //Shop Panel Shipyards Tab
                        Field shipyardsPanelField = PlayerPanel.class.getDeclaredField("shopPanelNew");
                        shipyardsPanelField.setAccessible(true);
                        ShopPanelNew shopPanelNew = (ShopPanelNew) shipyardsPanelField.get(playerPanel);
                        if (!(shopPanelNew instanceof NewShopPanel)) {
                            GameClientState state = playerPanel.getState();
                            shipyardsPanelField.set(playerPanel, new NewShopPanel(state));
                            if (debug) DebugFile.log("[DEBUG]: Swapped out ShopPanelNew");

                        }
                    } catch (NoSuchFieldException ex) {
                        ex.printStackTrace();
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    public static ShipyardCompanies getInst() {
        return inst;
    }

    public ArrayList<ShipyardCompany> getCompanies() {
        return companies;
    }
}