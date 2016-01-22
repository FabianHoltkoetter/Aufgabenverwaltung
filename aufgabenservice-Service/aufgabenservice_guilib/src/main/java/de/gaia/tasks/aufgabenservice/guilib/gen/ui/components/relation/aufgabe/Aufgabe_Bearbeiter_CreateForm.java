package de.gaia.tasks.aufgabenservice.guilib.gen.ui.components.relation.aufgabe;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

import de.gaia.tasks.aufgabenservice.api.local.Mitarbeiter_;

import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;

import de.muenchen.eventbus.events.Association;
import de.muenchen.vaadin.demo.i18nservice.buttons.SimpleAction;
import de.muenchen.vaadin.guilib.components.BaseComponent;
import de.muenchen.vaadin.guilib.components.actions.NavigateActions;
import de.muenchen.vaadin.guilib.components.buttons.ActionButton;

import de.gaia.tasks.aufgabenservice.guilib.gen.ui.components.buttons.listener.aufgabe.Aufgabe_AssociationActions;
import de.gaia.tasks.aufgabenservice.guilib.gen.ui.components.buttons.listener.mitarbeiter.Mitarbeiter_SingleActions;
import de.gaia.tasks.aufgabenservice.guilib.gen.ui.components.entity.mitarbeiter.Mitarbeiter_CreateForm;

import java.util.Optional;

/**
 * Provides a simple Form for creating a new Mitarbeiter_ as an Association.
 *
 * @author claus.straube p.mueller
 * @version 2.0
 */
public class Aufgabe_Bearbeiter_CreateForm extends Mitarbeiter_CreateForm {

    /** The relation this CreateForm is for. */
    private final String relation;

    /**
     * Formular zum Erstellen eines {@link Mitarbeiter_}s. Über diesen Konstruktor kann zusätzlich eine Zielseite für die
     * 'abbrechen' Schaltfläche erstellt werden. Dies ist dann sinnvoll, wenn dieses Formular in einen Wizzard, bzw. in
     * eine definierte Abfolge von Formularen eingebettet wird.
     *
     * @param navigateTo Zielseite nach Druck der 'erstellen' Schaltfläche
     * @param relation Angabe einer Assoziation, für die der Mitarbeiter_ ist.
     */
    public Aufgabe_Bearbeiter_CreateForm(final String navigateTo, final String relation) {
        super(navigateTo);
        getSaveButton().addActionPerformer(new NavigateActions(navigateTo)::navigate);
        this.relation = relation;
    }
	
	@Override
	protected void configureSaveButton() {
		final Aufgabe_AssociationActions aufgabeAssociationActions = new Aufgabe_AssociationActions(
			() -> new Association<>(getMitarbeiter(), getRelationToCreate()));
		getSaveButton().addActionPerformer(aufgabeAssociationActions::addAssociation);
		
		getSaveButton().useNotification(true);
	}

    /**
     * Get the Relation this CreateForm is for.
     * @return The relation.
     */
    public String getRelationToCreate() {
        return relation;
    }
}
