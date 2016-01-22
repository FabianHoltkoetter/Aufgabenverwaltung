package de.gaia.tasks.aufgabenservice.guilib.gen.ui.components.relation.aufgabe;

import com.vaadin.ui.Component;
import de.muenchen.vaadin.guilib.components.GenericGrid;
import de.muenchen.vaadin.guilib.components.buttons.ActionButton;

import de.gaia.tasks.aufgabenservice.api.local.Mitarbeiter_;

import de.gaia.tasks.aufgabenservice.guilib.gen.ui.components.buttons.listener.aufgabe.Aufgabe_SingleActions;
import de.gaia.tasks.aufgabenservice.guilib.gen.ui.controller.Aufgabe_ViewController;
import de.gaia.tasks.aufgabenservice.guilib.gen.ui.controller.Mitarbeiter_ViewController;

/**
 * @author rene.zarwel
 */
public class Aufgabe_Bearbeiter_ReadGrid extends GenericGrid<Mitarbeiter_> {

	private final Aufgabe_ViewController controller;

    public Aufgabe_Bearbeiter_ReadGrid(final Aufgabe_ViewController controller, final String navigateToRead) {
    	super(controller.getModel().getSelectedAufgabeBearbeiter(),
                Mitarbeiter_.Field.getProperties());
        this.controller = controller;
        this.activateSearch().activateRead(navigateToRead).activateDoubleClickToRead(navigateToRead);
    }
    
    /**
	 * Aktualisiert die Relationen in dieser Grid vom Server. Sollte in der init-Methode der View aufgerufen werden um einen
	 * Konsistenten Datenstand zu gewährleisten.
	 */
	public void reload(){
	    final Aufgabe_SingleActions singleActions = new Aufgabe_SingleActions(controller.getModel().getSelectedAufgabe()::get);
	    singleActions.reRead(null);
	}

	public Component addButton(ActionButton button){
    	addComponent(button);
    	return this;
    }
}
