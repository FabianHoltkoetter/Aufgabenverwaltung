package de.gaia.tasks.aufgabenservice.guilib.gen.ui.components.entity.mitarbeiter;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Field;

import de.muenchen.vaadin.demo.i18nservice.buttons.SimpleAction;
import de.muenchen.vaadin.guilib.components.BaseComponent;
import de.muenchen.vaadin.guilib.components.actions.NavigateActions;
import de.muenchen.vaadin.guilib.components.buttons.ActionButton;

import de.gaia.tasks.aufgabenservice.api.local.Mitarbeiter_;
import de.gaia.tasks.aufgabenservice.guilib.gen.ui.components.buttons.listener.mitarbeiter.Mitarbeiter_SingleActions;

/**
 * Provides a simple update form for the current selected Mitarbeiter_.
 *
 * @author p.mueller
 * @version 1.0
 */
public class Mitarbeiter_UpdateForm extends BaseComponent {
	/** Indicates the mode of the form. */
    private static final boolean READ_ONLY = false;

	/** The underlying form. */
    private final Mitarbeiter_SelectedForm mitarbeiterForm;

	/** The layout for all Buttons. */
    private final HorizontalLayout buttonLayout = new HorizontalLayout();
    
    /** The button for the update action. */
    private final NavigateActions saveNavigation;
    
    /** The button for the save action. */
    private final ActionButton saveButton = new ActionButton(Mitarbeiter_.class, SimpleAction.save);


    public Mitarbeiter_UpdateForm(final String navigateToSaved) {
        saveNavigation = new NavigateActions(navigateToSaved);
        mitarbeiterForm = new Mitarbeiter_SelectedForm();
        mitarbeiterForm.reLoad();

        init();
        setIds();
    }

	/**
	 * Initialize the ReadForm.
	 */
    private void init() {
        getForm().setReadOnly(READ_ONLY);

        getButtonLayout().setSpacing(true);
        getButtonLayout().addComponents(getSaveButton());

        configureSaveButton();

        getForm().getFormLayout().addComponent(getButtonLayout());
        setCompositionRoot(getForm());

        getForm().getFields().stream().findFirst().ifPresent(Field::focus);
    }
    
    /**
	 * Set the IDs for important components.
	 */
	private void setIds() {
		setId(getClass().getSimpleName());
		getForm().getFields().forEach(f -> f.setId(getId() + "#" + f.getId()));
		getForm().setId(getId() + "#form");
		getSaveButton().setId(getId() + "#save-button-" + getSaveNavigation().getNavigateTo());
	}

    private void configureSaveButton() {
        final Mitarbeiter_SingleActions singleActions = new Mitarbeiter_SingleActions(getForm()::getMitarbeiter);
        getSaveButton().addActionPerformer(singleActions::update);
        getSaveButton().addActionPerformer(getSaveNavigation()::navigate);
        
        getSaveButton().useNotification(true);
        getSaveButton().setNotifyAction(SimpleAction.update);
    }

	// Getters
    public Mitarbeiter_SelectedForm getForm() {
        return mitarbeiterForm;
    }

    public HorizontalLayout getButtonLayout() {
        return buttonLayout;
    }

    public ActionButton getSaveButton() {
        return saveButton;
    }

    public NavigateActions getSaveNavigation() {
        return saveNavigation;
    }
    
    public Component addButton(ActionButton button){
    	buttonLayout.addComponent(button);
    	return this;
    }
}
