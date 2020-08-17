package net.soe.shipyardcompanies.gui;

import api.faction.Faction;
import api.main.GameClient;
import api.main.GameServer;
import net.soe.shipyardcompanies.ShipyardCompanies;
import net.soe.shipyardcompanies.shipyards.CompanyTechFocus;
import net.soe.shipyardcompanies.shipyards.ShipyardCompany;
import net.soe.shipyardcompanies.shipyards.ShipyardOrder;
import org.hsqldb.lib.StringComparator;
import org.schema.game.server.controller.BluePrintController;
import org.schema.schine.graphicsengine.core.MouseEvent;
import org.schema.schine.graphicsengine.forms.gui.*;
import org.schema.schine.graphicsengine.forms.gui.newgui.*;
import org.schema.schine.input.InputState;
import java.util.Collection;
import java.util.Comparator;
import java.util.Observable;
import java.util.Set;

public class CompaniesScrollableList extends ScrollableTableList<ShipyardCompany> implements GUIActiveInterface {

    private Set<ShipyardCompany> companies;
    private CompanyListRow selectedRow;

    public CompaniesScrollableList(InputState inputState, float v, float v1, GUIElement guiElement, Set<ShipyardCompany> companies) {
        super(inputState, v, v1, guiElement);
        this.companies = companies;
        selectedRow = null;
    }

    public void initColumns() {
        new StringComparator();

        this.addColumn("Name", 6.0F, new Comparator<ShipyardCompany>() {
            public int compare(ShipyardCompany o1, ShipyardCompany o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        this.addColumn("Faction", 6.0F, new Comparator<ShipyardCompany>() {
            public int compare(ShipyardCompany o1, ShipyardCompany o2) {
                String faction1Name = "";
                String faction2Name = "";

                if(o1.isNpcOwned()) {
                    faction1Name = o1.getOwnerNPCFaction().getName();
                } else {
                    faction1Name = o1.getOwnerFaction().getName();
                }

                if(o2.isNpcOwned()) {
                    faction2Name = o2.getOwnerNPCFaction().getName();
                } else {
                    faction2Name = o2.getOwnerFaction().getName();
                }

                return faction1Name.compareTo(faction2Name);
            }
        });
        this.addColumn("Focus", 3.0F, new Comparator<ShipyardCompany>() {
            public int compare(ShipyardCompany o1, ShipyardCompany o2) {
                return o1.getTechFocus().getDisplayName().compareTo(o2.getTechFocus().getDisplayName());
            }
        });

        this.addButton(new GUICallback() {
            public void callback(GUIElement guiElement, MouseEvent mouseEvent) {
                if(mouseEvent.pressedLeftMouse()) {
                    viewClients();
                }
            }

            public boolean isOccluded() {
                return false;
            }
        }, "VIEW CLIENTS", ControllerElement.FilterRowStyle.LEFT, ControllerElement.FilterPos.TOP);

        this.addButton(new GUICallback() {
            public void callback(GUIElement guiElement, MouseEvent mouseEvent) {
                if(mouseEvent.pressedLeftMouse()) {
                    placeOrder();
                }
            }

            public boolean isOccluded() {
                return false;
            }
        }, "PLACE ORDER", ControllerElement.FilterRowStyle.RIGHT, ControllerElement.FilterPos.TOP);

        this.addTextFilter(new GUIListFilterText<ShipyardCompany>() {
            public boolean isOk(String s, ShipyardCompany shipyardCompany) {
                return shipyardCompany.getName().toLowerCase().contains(s.toLowerCase());
            }
        }, ControllerElement.FilterRowStyle.LEFT);

        this.addDropdownFilter(new GUIListFilterDropdown<ShipyardCompany, CompanyTechFocus>(CompanyTechFocus.values()) {
            public boolean isOk(CompanyTechFocus techFocus, ShipyardCompany company) {
                return company.getTechFocus().equals(techFocus);
            }

        }, new CreateGUIElementInterface<CompanyTechFocus>() {
            public GUIElement create(CompanyTechFocus techFocus) {
                GUIAncor anchor = new GUIAncor(CompaniesScrollableList.this.getState(), 10.0F, 24.0F);
                GUITextOverlayTableDropDown dropDown;
                (dropDown = new GUITextOverlayTableDropDown(10, 10, CompaniesScrollableList.this.getState())).setTextSimple(techFocus.getDisplayName());
                dropDown.setPos(4.0F, 4.0F, 0.0F);
                anchor.setUserPointer(techFocus);
                anchor.attach(dropDown);
                return anchor;
            }

            public GUIElement createNeutral() {
                GUIAncor anchor = new GUIAncor(CompaniesScrollableList.this.getState(), 10.0F, 24.0F);
                GUITextOverlayTableDropDown dropDown;
                (dropDown = new GUITextOverlayTableDropDown(10, 10, CompaniesScrollableList.this.getState())).setTextSimple("All");
                dropDown.setPos(4.0F, 4.0F, 0.0F);
                anchor.attach(dropDown);
                return anchor;
            }
        }, ControllerElement.FilterRowStyle.RIGHT);

        this.activeSortColumnIndex = 0;
    }

    private void viewClients() {
        if(this.selectedRow != null) {
            Faction currentFaction = new Faction(GameServer.getServerState().getFactionManager().getFaction(GameClient.getClientPlayerState().getFactionId()));
            ViewClientsWindow clientsWindow = new ViewClientsWindow(this.getState(), selectedRow.getCompany(), currentFaction);

        }
    }

    private void placeOrder() {
        if(this.selectedRow != null) {
            //Todo:Place Order GUI

            //Debug Testing:
            ShipyardCompany testCompany = selectedRow.getCompany();
            Faction testFaction = new Faction(GameServer.getServerState().getFactionManager().getFaction(GameClient.getClientPlayerState().getFactionId()));
            ShipyardOrder testOrder = new ShipyardOrder(testCompany, testFaction, BluePrintController.stationsNeutral.readBluePrints().get(1),300000);
            testCompany.placeOrder(testOrder, testFaction);
            ShipyardCompanies.getInst().addOrder(testFaction, testOrder);
            this.flagDirty();
        }
    }

    @Override
    public void update(Observable var1, Object var2) {
        super.update(var1, var2);
        this.companies.clear();
        this.companies.addAll(ShipyardCompanies.getInst().getCompanies());
        this.flagDirty();
    }

    protected Collection<ShipyardCompany> getElementList() {
        return companies;
    }

    public void updateListEntries(GUIElementList guiElementList, Set<ShipyardCompany> set) {
        guiElementList.deleteObservers();
        guiElementList.addObserver(this);

        for(ShipyardCompany company : set) {

            GUITextOverlayTable nameTextElement;
            (nameTextElement = new GUITextOverlayTable(10, 10, this.getState())).setTextSimple(company.getName());
            GUIClippedRow nameRowElement;
            (nameRowElement = new GUIClippedRow(this.getState())).attach(nameTextElement);

            GUITextOverlayTable factionTextElement;
            String factionName = "";
            if(company.isNpcOwned()) {
                factionName = company.getOwnerNPCFaction().getName();
            } else {
                factionName = company.getOwnerFaction().getName();
            }
            (factionTextElement = new GUITextOverlayTable(10, 10, this.getState())).setTextSimple(factionName);
            GUIClippedRow factionRowElement;
            (factionRowElement = new GUIClippedRow(this.getState())).attach(factionTextElement);

            GUITextOverlayTable focusTextElement;
            (focusTextElement = new GUITextOverlayTable(10, 10, this.getState())).setTextSimple(company.getTechFocus().getDisplayName());
            GUIClippedRow focusRowElement;
            (focusRowElement = new GUIClippedRow(this.getState())).attach(focusTextElement);

            GUIAncor anchor = new GUIAncor(this.getState());
            anchor.onInit();
            anchor.setHeight(150);
            GUITextOverlayTableInnerDescription focusDescription;
            (focusDescription = new GUITextOverlayTableInnerDescription(50, 150, this.getState())).setTextSimple(company.getDescription() + "\n" + "Effects:" + "\n" + company.getTechFocus().getDescription());
            anchor.attach(focusDescription);
            CompanyListRow companyListRow = new CompanyListRow(this.getState(), company, nameRowElement, factionRowElement, focusRowElement);
            companyListRow.expanded = new GUIElementList(this.getState());
            companyListRow.expanded.height = 150;
            companyListRow.expanded.add(new GUIListElement(anchor, anchor, this.getState()));
            companyListRow.onInit();
            guiElementList.addWithoutUpdate(companyListRow);
        }
        guiElementList.updateDim();
    }

    class CompanyListRow extends ScrollableTableList<ShipyardCompany>.Row {

        private ShipyardCompany company;

        public CompanyListRow(InputState inputState, ShipyardCompany shipyardCompany, GUIElement... guiElements) {
            super(inputState, shipyardCompany, guiElements);
            this.highlightSelect = true;
            this.highlightSelectSimple = true;
            company = shipyardCompany;
        }

        @Override
        public void clickedOnRow() {
            super.clickedOnRow();
            CompaniesScrollableList.this.selectedRow = this;
        }

        public ShipyardCompany getCompany() {
            return company;
        }
    }
}
