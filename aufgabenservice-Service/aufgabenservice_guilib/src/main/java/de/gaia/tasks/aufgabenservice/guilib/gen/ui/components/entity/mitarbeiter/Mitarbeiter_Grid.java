package de.gaia.tasks.aufgabenservice.guilib.gen.ui.components.entity.mitarbeiter;

import com.vaadin.ui.Component;
import de.muenchen.vaadin.guilib.components.GenericGrid;
import de.muenchen.vaadin.guilib.components.buttons.ActionButton;

import de.gaia.tasks.aufgabenservice.api.local.Mitarbeiter_;

import de.gaia.tasks.aufgabenservice.guilib.gen.ui.controller.Mitarbeiter_ViewController;

/**
 * @author rene.zarwel
 */
public class Mitarbeiter_Grid extends GenericGrid<Mitarbeiter_> {

    public Mitarbeiter_Grid(final Mitarbeiter_ViewController controller) {
        super(controller.getModel().getMitarbeiters(),
                Mitarbeiter_.Field.getProperties());
    }
    
    public Component addButton(ActionButton button){
    	addComponent(button);
    	return this;
    }

}
