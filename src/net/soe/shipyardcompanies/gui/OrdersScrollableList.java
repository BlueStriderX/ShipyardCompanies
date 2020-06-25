package net.soe.shipyardcompanies.gui;

import net.soe.shipyardcompanies.shipyards.ShipyardOrder;
import org.hsqldb.lib.StringComparator;
import org.schema.common.util.CompareTools;
import org.schema.schine.graphicsengine.core.MouseEvent;
import org.schema.schine.graphicsengine.forms.gui.GUICallback;
import org.schema.schine.graphicsengine.forms.gui.GUIElement;
import org.schema.schine.graphicsengine.forms.gui.GUIElementList;
import org.schema.schine.graphicsengine.forms.gui.newgui.ControllerElement;
import org.schema.schine.graphicsengine.forms.gui.newgui.GUITextOverlayTable;
import org.schema.schine.graphicsengine.forms.gui.newgui.ScrollableTableList;
import org.schema.schine.input.InputState;
import java.util.*;

public class OrdersScrollableList extends ScrollableTableList<ShipyardOrder> {

    private Set<ShipyardOrder> orders;
    private GUIElementList ordersElementList;
    private OrderListRow selectedRow;

    public OrdersScrollableList(InputState inputState, float v, float v1, GUIElement guiElement, Set<ShipyardOrder> orders) {
        super(inputState, v, v1, guiElement);
        this.orders = orders;
        selectedRow = null;
    }

    @Override
    public void initColumns() {
        new StringComparator();
        this.addColumn("STATUS", 1.5F, new Comparator<ShipyardOrder>() {
            public int compare(ShipyardOrder o1, ShipyardOrder o2) {
                return CompareTools.compare(o1.getCompletionPercent(), o2.getCompletionPercent());
            }
        });
        this.addColumn("DESIGNER", 6.0F, new Comparator<ShipyardOrder>() {
            public int compare(ShipyardOrder o1, ShipyardOrder o2) {
                return o1.getDesigner().getName().compareTo(o2.getDesigner().getName());
            }
        });
        this.addColumn("BLUEPRINT", 10.0F, new Comparator<ShipyardOrder>() {
            public int compare(ShipyardOrder o1, ShipyardOrder o2) {
                return o1.getBlueprint().getName().compareTo(o2.getBlueprint().getName());
            }
        });
        this.addColumn("MASS", 2.0F, new Comparator<ShipyardOrder>() {
            public int compare(ShipyardOrder o1, ShipyardOrder o2) {
                return CompareTools.compare(o1.getBlueprint().getMass(), o2.getBlueprint().getMass());
            }
        });
        this.addColumn("PRODUCTION COST", 4.0F, new Comparator<ShipyardOrder>() {
            public int compare(ShipyardOrder o1, ShipyardOrder o2) {
                return CompareTools.compare(o1.getPrice(), o2.getPrice());
            }
        });
        this.addButton(new GUICallback() {
            public void callback(GUIElement guiElement, MouseEvent mouseEvent) {
                if(mouseEvent.pressedLeftMouse()) {
                    cancelOrder();
                }
            }

            public boolean isOccluded() {
                return false;
            }
        }, "Cancel Order", ControllerElement.FilterRowStyle.RIGHT, ControllerElement.FilterPos.BOTTOM);

        this.activeSortColumnIndex = 0;
    }

    private void cancelOrder() {
        if(this.selectedRow != null) {
            orders.remove(selectedRow.getOrder());
            updateListEntries(ordersElementList, orders);
            //Todo:Cancel Order
        }
    }

    @Override
    protected Collection<ShipyardOrder> getElementList() {
        return orders;
    }

    @Override
    public void updateListEntries(GUIElementList guiElementList, Set<ShipyardOrder> set) {
        for(ShipyardOrder order : set) {

            GUITextOverlayTable statusTextElement;
            (statusTextElement = new GUITextOverlayTable(10, 10, this.getState())).setTextSimple(order.getCompletionPercent() + "%");
            GUIClippedRow statusRowElement;
            (statusRowElement = new GUIClippedRow(this.getState())).attach(statusTextElement);

            GUITextOverlayTable designerTextElement;
            (designerTextElement = new GUITextOverlayTable(10, 10, this.getState())).setTextSimple(order.getDesigner().getName());
            GUIClippedRow designerRowElement;
            (designerRowElement = new GUIClippedRow(this.getState())).attach(designerTextElement);

            GUITextOverlayTable blueprintTextElement;
            (blueprintTextElement = new GUITextOverlayTable(10, 10, this.getState())).setTextSimple(order.getBlueprint().getName());
            GUIClippedRow blueprintRowElement;
            (blueprintRowElement = new GUIClippedRow(this.getState())).attach(blueprintTextElement);

            GUITextOverlayTable massTextElement;
            (massTextElement = new GUITextOverlayTable(10, 10, this.getState())).setTextSimple((int) Math.ceil(order.getBlueprint().getMass()));
            GUIClippedRow massRowElement;
            (massRowElement = new GUIClippedRow(this.getState())).attach(massTextElement);

            GUITextOverlayTable costTextElement;
            (costTextElement = new GUITextOverlayTable(10, 10, this.getState())).setTextSimple((int) Math.ceil(order.getPrice()));
            GUIClippedRow costRowElement;
            (costRowElement = new GUIClippedRow(this.getState())).attach(costTextElement);

            OrderListRow orderListRow;
            (orderListRow = new OrderListRow(this.getState(), order, statusRowElement, designerRowElement, blueprintRowElement, massRowElement, costRowElement)).onInit();
            guiElementList.addWithoutUpdate(orderListRow);
        }
        orders = set;
        ordersElementList = guiElementList;
        ordersElementList.updateDim();
    }

    @Override
    public void cleanUp() {
        super.cleanUp();
    }

    @Override
    public void draw() {
        super.draw();
    }

    class OrderListRow extends ScrollableTableList<ShipyardOrder>.Row {
        private ShipyardOrder order;

        public OrderListRow(InputState inputState, ShipyardOrder shipyardOrder, GUIElement... guiElements) {
            super(inputState, shipyardOrder, guiElements);
            this.highlightSelect = true;
            this.highlightSelectSimple = true;
            order = shipyardOrder;
        }

        @Override
        public void clickedOnRow() {
            super.clickedOnRow();
            OrdersScrollableList.this.selectedRow = this;
        }

        public ShipyardOrder getOrder() {
            return order;
        }
    }
}
