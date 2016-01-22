package de.gaia.tasks.aufgabenservice.guilib.gen.ui.components.relation.aufgabe;

import com.vaadin.ui.Grid;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import de.muenchen.eventbus.events.Association;

import de.muenchen.vaadin.demo.i18nservice.buttons.SimpleAction;
import de.muenchen.vaadin.guilib.components.BaseComponent;
import de.muenchen.vaadin.guilib.components.GenericGrid;
import de.muenchen.vaadin.guilib.components.actions.NavigateActions;
import de.muenchen.vaadin.guilib.components.buttons.ActionButton;

import de.gaia.tasks.aufgabenservice.api.local.Aufgabe_;
import de.gaia.tasks.aufgabenservice.api.local.Mitarbeiter_;
import de.gaia.tasks.aufgabenservice.guilib.gen.ui.components.buttons.listener.aufgabe.Aufgabe_AssociationActions;
import de.gaia.tasks.aufgabenservice.guilib.gen.ui.components.buttons.listener.aufgabe.Aufgabe_AssociationListActions;
import de.gaia.tasks.aufgabenservice.guilib.gen.ui.controller.Mitarbeiter_ViewController;
import de.gaia.tasks.aufgabenservice.guilib.gen.ui.components.entity.mitarbeiter.Mitarbeiter_Grid;

import java.util.stream.Collectors;

public class Aufgabe_Bearbeiter_AddGrid extends BaseComponent {
    private final String navigateOnAdd;
    private GenericGrid<Mitarbeiter_> grid;
    private final Mitarbeiter_ViewController controller;

    public Aufgabe_Bearbeiter_AddGrid(Mitarbeiter_ViewController controller, String navigateOnAdd){
        this.controller = controller;
        this.navigateOnAdd= navigateOnAdd;
        init();
    }

    protected void init(){
        final NavigateActions navigateActions = new NavigateActions(navigateOnAdd);

        grid = new Mitarbeiter_Grid(controller);

		grid.setSelectionMode(Grid.SelectionMode.MULTI).activateSearch();

        ActionButton addButton = new ActionButton(Mitarbeiter_.class, SimpleAction.add);
        
        Aufgabe_AssociationListActions actionMultiple = new Aufgabe_AssociationListActions(
                () -> grid.getSelectedEntities().stream().map(entity -> new Association<>(entity, Aufgabe_.Rel.bearbeiter.name())).collect(Collectors.toList()));
        addButton.addActionPerformer(actionMultiple::addAssociations);
        addButton.addActionPerformer(navigateActions::navigate);
        addButton.useNotification(true);
        grid.addMultiSelectComponent(addButton);

        setCompositionRoot(grid);
    }
    
    public Component addButton(ActionButton button){
    	grid.addComponent(button);
    	return this;
    }
}

