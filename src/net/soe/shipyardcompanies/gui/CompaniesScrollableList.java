package net.soe.shipyardcompanies.gui;

import net.soe.shipyardcompanies.shipyards.CompanyTechFocus;
import net.soe.shipyardcompanies.shipyards.ShipyardCompany;
import org.hsqldb.lib.StringComparator;
import org.schema.schine.graphicsengine.core.MouseEvent;
import org.schema.schine.graphicsengine.forms.gui.GUIAncor;
import org.schema.schine.graphicsengine.forms.gui.GUICallback;
import org.schema.schine.graphicsengine.forms.gui.GUIElement;
import org.schema.schine.graphicsengine.forms.gui.GUIElementList;
import org.schema.schine.graphicsengine.forms.gui.newgui.*;
import org.schema.schine.input.InputState;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;

public class CompaniesScrollableList extends ScrollableTableList<ShipyardCompany> {

    private Set<ShipyardCompany> companies;
    private GUIElementList companiesElementList;
    private CompanyListRow selectedRow;

    public CompaniesScrollableList(InputState inputState, float v, float v1, GUIElement guiElement, Set<ShipyardCompany> companies) {
        super(inputState, v, v1, guiElement);
        this.companies = companies;
        selectedRow = null;
    }

    @Override
    public void initColumns() {
        new StringComparator();

        this.addColumn("NAME", 6.0F, new Comparator<ShipyardCompany>() {
            public int compare(ShipyardCompany o1, ShipyardCompany o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        this.addColumn("FACTION", 6.0F, new Comparator<ShipyardCompany>() {
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
        this.addColumn("FOCUS", 6.0F, new Comparator<ShipyardCompany>() {
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
        }, "View Clients", ControllerElement.FilterRowStyle.LEFT, ControllerElement.FilterPos.TOP);

        this.addButton(new GUICallback() {
            public void callback(GUIElement guiElement, MouseEvent mouseEvent) {
                if(mouseEvent.pressedLeftMouse()) {
                    placeOrder();
                }
            }

            public boolean isOccluded() {
                return false;
            }
        }, "Place Order", ControllerElement.FilterRowStyle.RIGHT, ControllerElement.FilterPos.TOP);

        this.addTextFilter(new GUIListFilterText<ShipyardCompany>() {
            public boolean isOk(String s, ShipyardCompany shipyardCompany) {
                return shipyardCompany.getName().toLowerCase().contains(s.toLowerCase());
            }
        }, ControllerElement.FilterRowStyle.LEFT);

        this.addDropdownFilter(new GUIListFilterDropdown<ShipyardCompany, CompanyTechFocus>(CompanyTechFocus.values()) {
            @Override
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
                (dropDown = new GUITextOverlayTableDropDown(10, 10, CompaniesScrollableList.this.getState())).setTextSimple("ALL");
                dropDown.setPos(4.0F, 4.0F, 0.0F);
                anchor.attach(dropDown);
                return anchor;
            }
        }, ControllerElement.FilterRowStyle.RIGHT);

        this.activeSortColumnIndex = 0;
    }

    private void viewClients() {
        if(this.selectedRow != null) {
            //Todo: View Clients
        }
    }

    private void placeOrder() {
        if(this.selectedRow != null) {
            //Todo:Place Order GUI
        }
    }

    @Override
    protected Collection<ShipyardCompany> getElementList() {
        return companies;
    }

    @Override
    public void updateListEntries(GUIElementList guiElementList, Set<ShipyardCompany> set) {
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


            CompanyListRow companyListRow;
            (companyListRow = new CompanyListRow(this.getState(), company, nameRowElement, factionRowElement, focusRowElement)).onInit();
            guiElementList.addWithoutUpdate(companyListRow);
        }
        companies = set;
        companiesElementList = guiElementList;
        companiesElementList.updateDim();
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
