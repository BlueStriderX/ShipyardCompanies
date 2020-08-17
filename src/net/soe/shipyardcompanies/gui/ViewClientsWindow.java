package net.soe.shipyardcompanies.gui;

import api.faction.Faction;
import net.soe.shipyardcompanies.shipyards.ShipyardCompany;
import org.schema.game.client.view.gui.GUIInputPanel;
import org.schema.schine.graphicsengine.forms.gui.GUICallback;
import org.schema.schine.graphicsengine.forms.gui.newgui.*;
import org.schema.schine.input.InputState;
import java.util.ArrayList;

public class ViewClientsWindow extends GUIInputPanel {

    private InputState inputState;
    private ShipyardCompany company;
    private ArrayList<Faction> clients;
    private CompanyClientList clientList;

    public ViewClientsWindow(InputState inputState, GUICallback guiCallback, ShipyardCompany company) {
        super("COMPANY_CLIENTS_LIST", inputState, 500, 300, guiCallback, "VIEW CLIENTS", "");
        this.inputState = inputState;
        this.company = company;
        this.clients = company.getClients();
    }

    public void onInit() {
        super.onInit();
        clientList = new CompanyClientList(inputState, clients);
    }

    /*
    public ViewClientsWindow(final InputState inputState, ShipyardCompany company, final Faction currentFaction) {
        SimpleGUIBuilder guiBuilder = SimpleGUIBuilder.newBuilder("VIEW CLIENTS");
        clients = company.getClients();
        CompanyClientList clientList = new CompanyClientList(inputState, guiBuilder, clients);

        clientList.addSimpleColumn("Faction", 1.0F, new Comparator<Faction>() {
            @Override
            public int compare(Faction o1, Faction o2) {
                return o1.getName().compareTo(o2.getName());
            }
        }, new RowStringCreator<Faction>() {
            @Override
            public String update(Faction faction) {
                return faction.getName();
            }
        });

        clientList.addSimpleColumn("Active Orders", 0.5F, new Comparator<Faction>() {
            @Override
            public int compare(Faction o1, Faction o2) {
                return 0;
            }
        }, new RowStringCreator<Faction>() {
            @Override
            public String update(Faction faction) {
                int ordersCount = 0;
                for(Faction client : clients) {
                    if(client == faction) {
                        ordersCount ++;
                    }
                }
                return ordersCount + " Orders";
            }
        });

        clientList.addSimpleColumn("Relation to us", 1.0F, new Comparator<Faction>() {
            @Override
            public int compare(Faction o1, Faction o2) {
                String rType1 = o1.getRelationTo(currentFaction).toString().toUpperCase();
                String rType2 = o2.getRelationTo(currentFaction).toString().toUpperCase();
                return rType1.compareTo(rType2);
            }
        }, new RowStringCreator<Faction>() {
            @Override
            public String update(Faction faction) {
                return faction.getRelationTo(currentFaction).toString().toUpperCase();
            }
        });

        clientList.addDropdownFilter(new GUIListFilterDropdown<Faction, FactionRelation.RType>(FactionRelation.RType.values()) {
            public boolean isOk(FactionRelation.RType relation, Faction faction) {
                return faction.getRelationTo(currentFaction).equals(relation);
            }

        }, new CreateGUIElementInterface<FactionRelation.RType>() {
            public GUIElement create(FactionRelation.RType relation) {
                GUIAncor anchor = new GUIAncor(inputState, 10.0F, 24.0F);
                GUITextOverlayTableDropDown dropDown;
                (dropDown = new GUITextOverlayTableDropDown(10, 10, inputState)).setTextSimple(relation.toString());
                dropDown.setPos(4.0F, 4.0F, 0.0F);
                anchor.setUserPointer(relation);
                anchor.attach(dropDown);
                return anchor;
            }

            public GUIElement createNeutral() {
                GUIAncor anchor = new GUIAncor(inputState, 10.0F, 24.0F);
                GUITextOverlayTableDropDown dropDown;
                (dropDown = new GUITextOverlayTableDropDown(10, 10, inputState)).setTextSimple("All");
                dropDown.setPos(4.0F, 4.0F, 0.0F);
                anchor.attach(dropDown);
                return anchor;
            }
        }, ControllerElement.FilterRowStyle.RIGHT);

        clientList.addTextFilter(new GUIListFilterText<Faction>() {
            @Override
            public boolean isOk(String s, Faction faction) {
                return faction.getName().toLowerCase().contains(s.toLowerCase());
            }
        }, ControllerElement.FilterRowStyle.LEFT);

        guiBuilder.addSimpleGUIList(clientList);
        guiBuilder.onInit();
        guiBuilder.draw();
    }
     */
}
