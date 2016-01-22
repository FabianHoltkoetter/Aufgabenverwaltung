package de.gaia.tasks.aufgabenservice.guilib.gen.ui.components.entity.mitarbeiter;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

import de.muenchen.vaadin.guilib.components.BaseComponent;
import de.muenchen.vaadin.guilib.components.actions.NavigateActions;
import de.muenchen.vaadin.guilib.components.buttons.ActionButton;

import de.muenchen.vaadin.demo.i18nservice.buttons.SimpleAction;

import de.gaia.tasks.aufgabenservice.api.local.Mitarbeiter_;
import de.gaia.tasks.aufgabenservice.guilib.gen.ui.components.buttons.listener.mitarbeiter.Mitarbeiter_SingleActions;

/**
 * @author claus
 */
public class Mitarbeiter_ReadForm extends BaseComponent {
	/** Indicates the mode of the form. */
	public static final boolean READ_ONLY = true;

    /** The underlying form. */
    private final Mitarbeiter_SelectedForm mitarbeiterForm;

	/** The layout for all Buttons. */
    private final HorizontalLayout buttonLayout = new HorizontalLayout();
    
    /** The button for the update action. */
    private final ActionButton updateButton = new ActionButton(Mitarbeiter_.class, SimpleAction.update);

	/** The navigation for the update aciton. */
    private final NavigateActions updateNavigation;

    public Mitarbeiter_ReadForm(final String navigateToUpdate) {
        mitarbeiterForm = new Mitarbeiter_SelectedForm();
        mitarbeiterForm.reLoad();

        updateNavigation = new NavigateActions(navigateToUpdate);
        init();
        setIds();
    }

	/**
	 * Build the basic layout and insert the headline and all Buttons.
	 */
    private void init() {
        getForm().setReadOnly(READ_ONLY);

        getButtonLayout().setSpacing(true);
        getButtonLayout().addComponents(getUpdateButton());

        configureUpdateButton();

        getForm().getFormLayout().addComponent(getButtonLayout());
        setCompositionRoot(getForm());
    }

    /**
	 * Set the IDs for important components.
	 */
	private void setIds() {
		setId(getClass().getSimpleName());
		getForm().getFields().forEach(f -> f.setId(getId() + "#" + f.getId()));
		getForm().setId(getId() + "#form");
		getUpdateButton().setId(getId() + "#update-button-" + getUpdateNavigation().getNavigateTo());
	}

    private void configureUpdateButton() {
        final Mitarbeiter_SingleActions singleActions = new Mitarbeiter_SingleActions(getForm()::getMitarbeiter);
        getUpdateButton().addActionPerformer(singleActions::read);
        getUpdateButton().addActionPerformer(getUpdateNavigation()::navigate);
    }
    
    // Getters
    public Mitarbeiter_SelectedForm getForm() {
        return mitarbeiterForm;
    }

    public HorizontalLayout getButtonLayout() {
        return buttonLayout;
    }

    public ActionButton getUpdateButton() {
        return updateButton;
    }

    public NavigateActions getUpdateNavigation() {
        return updateNavigation;
    }
    
    public Component addButton(ActionButton button){
    	buttonLayout.addComponent(button);
    	return this;
    }
}
