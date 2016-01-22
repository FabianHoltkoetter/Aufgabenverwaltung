package de.gaia.tasks.aufgabenservice.guilib.gen.ui.components.relation.aufgabe;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

import de.muenchen.eventbus.events.Association;
import de.muenchen.vaadin.demo.i18nservice.buttons.SimpleAction;
import de.muenchen.vaadin.guilib.components.GenericGrid;
import de.muenchen.vaadin.guilib.components.actions.NavigateActions;
import de.muenchen.vaadin.guilib.components.buttons.ActionButton;

import de.gaia.tasks.aufgabenservice.api.local.Aufgabe_;
import de.gaia.tasks.aufgabenservice.api.local.Mitarbeiter_;
import de.gaia.tasks.aufgabenservice.guilib.gen.ui.components.buttons.listener.aufgabe.Aufgabe_AssociationListActions;
import de.gaia.tasks.aufgabenservice.guilib.gen.ui.components.buttons.listener.aufgabe.Aufgabe_SingleActions;
import de.gaia.tasks.aufgabenservice.guilib.gen.ui.controller.Aufgabe_ViewController;

import java.util.stream.Collectors;

/**
 * @author claus
 */
public class Aufgabe_Bearbeiter_ReadEditGrid extends CustomComponent {

    private Aufgabe_ViewController controller;
    private GenericGrid<Mitarbeiter_> grid;

    public Aufgabe_Bearbeiter_ReadEditGrid(Aufgabe_ViewController controller, String navigateToRead, String navigateToCreate, String navigateToAdd) {

        this.controller = controller;

        grid = new GenericGrid<Mitarbeiter_>(controller.getModel().getSelectedAufgabeBearbeiter(), Mitarbeiter_.Field.getProperties());
        grid.activateSearch(false);
        grid.activateCreate(navigateToCreate);

        ActionButton addButton = new ActionButton(Aufgabe_.class, SimpleAction.add);
        NavigateActions navigateActions = new NavigateActions(navigateToAdd);
        addButton.addActionPerformer(navigateActions::navigate);
        grid.addComponent(addButton);
        grid.activateRead(navigateToRead);

        //Create Button to delete one or more associations
        ActionButton deleteButton = new ActionButton(Aufgabe_.class, SimpleAction.delete);
        Aufgabe_AssociationListActions listAction = new Aufgabe_AssociationListActions(
                () -> grid.getSelectedEntities().stream()
                        .map(mitarbeiter -> new Association<>(mitarbeiter, Aufgabe_.Rel.bearbeiter.name()))
                        .collect(Collectors.toList())
        );
        deleteButton.addActionPerformer(listAction::removeAssociations);
        deleteButton.useNotification(true);
        grid.addMultiSelectComponent(deleteButton);

        HorizontalLayout layout = new HorizontalLayout(grid);
        layout.setSizeFull();
        setCompositionRoot(layout);
	}
	
    public Component addButton(ActionButton button){
    	grid.addComponent(button);
    	return this;
    }
	
	/**
	 * Aktualisiert die Relationen in dieser Grid vom Server. Sollte in der init-Methode der View aufgerufen werden um einen
	 * Konsistenten Datenstand zu gewährleisten.
	 */
	public void reload(){
	    final Aufgabe_SingleActions singleActions = new Aufgabe_SingleActions(controller.getModel().getSelectedAufgabe()::get);
	    singleActions.reRead(null);
	}
}

