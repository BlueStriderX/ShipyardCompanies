package net.soe.shipyardcompanies.gui;

import api.faction.Faction;
import org.schema.schine.graphicsengine.forms.gui.GUIElement;
import org.schema.schine.graphicsengine.forms.gui.GUIElementList;
import org.schema.schine.graphicsengine.forms.gui.newgui.ScrollableTableList;
import org.schema.schine.input.InputState;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class CompanyClientList extends ScrollableTableList<Faction> {

    private InputState inputState;
    private ArrayList<Faction> clients;

    public CompanyClientList(InputState inputState, GUIElement guiElement, ArrayList<Faction> clients) {
        super(inputState, 500, 300, guiElement);
        this.inputState = inputState;
        this.clients = clients;
    }


    @Override
    public void initColumns() {
    }

    @Override
    protected Collection<Faction> getElementList() {
        return clients;
    }

    @Override
    public void updateListEntries(GUIElementList guiElementList, Set<Faction> set) {

    }
}
