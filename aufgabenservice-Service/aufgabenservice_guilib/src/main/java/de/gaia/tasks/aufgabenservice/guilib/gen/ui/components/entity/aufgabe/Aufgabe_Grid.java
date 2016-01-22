package de.gaia.tasks.aufgabenservice.guilib.gen.ui.components.entity.aufgabe;

import com.vaadin.ui.Component;
import de.muenchen.vaadin.guilib.components.GenericGrid;
import de.muenchen.vaadin.guilib.components.buttons.ActionButton;

import de.gaia.tasks.aufgabenservice.api.local.Aufgabe_;

import de.gaia.tasks.aufgabenservice.guilib.gen.ui.controller.Aufgabe_ViewController;

/**
 * @author rene.zarwel
 */
public class Aufgabe_Grid extends GenericGrid<Aufgabe_> {

    public Aufgabe_Grid(final Aufgabe_ViewController controller) {
        super(controller.getModel().getAufgabes(),
                Aufgabe_.Field.getProperties());
    }
    
    public Component addButton(ActionButton button){
    	addComponent(button);
    	return this;
    }

}
