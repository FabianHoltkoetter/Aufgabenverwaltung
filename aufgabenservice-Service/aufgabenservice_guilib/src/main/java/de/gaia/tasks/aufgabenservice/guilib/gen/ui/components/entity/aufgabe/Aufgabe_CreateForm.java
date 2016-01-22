package de.gaia.tasks.aufgabenservice.guilib.gen.ui.components.entity.aufgabe;

import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import java.util.stream.Collectors;

import de.gaia.tasks.aufgabenservice.api.local.Aufgabe_;
import de.gaia.tasks.aufgabenservice.api.local.Mitarbeiter_;
import de.gaia.tasks.aufgabenservice.guilib.gen.ui.components.buttons.listener.aufgabe.Aufgabe_SingleActions;
import de.muenchen.vaadin.demo.i18nservice.buttons.SimpleAction;
import de.muenchen.vaadin.guilib.BaseUI;
import de.muenchen.vaadin.guilib.components.GenericGrid;
import de.muenchen.vaadin.guilib.components.actions.NavigateActions;
import de.muenchen.vaadin.guilib.components.buttons.ActionButton;

/**
 * Provides a simple Form for creating a new Aufgabe_.
 *
 * @author claus.straube p.mueller
 * @version 2.0
 */
public class Aufgabe_CreateForm extends Aufgabe_Form {
	/** Indicates the mode of the form. */
    private static final boolean READ_ONLY = false;

	/** The layout for all Buttons. */
    private final HorizontalLayout buttonLayout = new HorizontalLayout();
    
    /** The navigation for the save aciton. */
    private final NavigateActions saveNavigation;
    
    /** The button for the save action. */
    private final ActionButton saveButton = new ActionButton(Aufgabe_.class, SimpleAction.save);
    
	/** 
	 * Grid to select a required relation.
	 * relation: Bearbeiter
	 * type: multiple Mitarbeiter_
	 */
	 private final GenericGrid<Mitarbeiter_> bearbeiter_Grid = new GenericGrid<>(Mitarbeiter_.class, Mitarbeiter_.Field.getProperties());
	 
	/**
	 * Create a new Aufgabe_CreateForm that navigates to the navigateTo View on save.
	 *
	 * @param navigateTo The String of the view to navigate to on save.
	 */
    public Aufgabe_CreateForm(final String navigateTo) {
        this.saveNavigation = new NavigateActions(navigateTo);
        init();
        setIds();
    }

    /**
     * Build the basic layout and insert the headline and all Buttons.
     */
    protected void init() {
		setReadOnly(READ_ONLY);

        getButtonLayout().setSpacing(true);
        getButtonLayout().addComponents(getSaveButton());

		
		configureBearbeiter_Grid();
		getFormLayout().addComponent(bearbeiter_Grid);
        configureSaveButton();

        getFormLayout().addComponent(getButtonLayout());
		getFields().stream().findFirst().ifPresent(Field::focus);
    }
    
    /**
	 * Set the IDs for important components.
	 */
	protected void setIds() {
		setId(getClass().getSimpleName());
		getFields().forEach(f -> f.setId(getId() + "#" + f.getId()));
		setId(getId() + "#form");
		getSaveButton().setId(getId() + "#save-button-" + getSaveNavigation().getNavigateTo());
	}
	
	/**
	 * Configures the action the save button performs after beeing clicked.
	 */
	protected void configureSaveButton() {
        Aufgabe_SingleActions aufgabeSingleActions = new Aufgabe_SingleActions(this::getAufgabe);
        getSaveButton().addActionPerformer(aufgabeSingleActions::create);
        getSaveButton().addActionPerformer(getSaveNavigation()::navigate);
        
        getSaveButton().useNotification(true);
    }
    
    /**
     * Configures the grid used to select the Bearbeiter of this Aufgabe
     */
    protected void configureBearbeiter_Grid(){
    	bearbeiter_Grid.setHeightByRows(5);
    	bearbeiter_Grid.setSelectionMode(Grid.SelectionMode.MULTI);
    	bearbeiter_Grid.setCaption(BaseUI.getCurrentI18nResolver().resolve("aufgabe.bearbeiter.label"));
    }

	// Getters
	@Override
	public Aufgabe_ getAufgabe(){
		Aufgabe_ aufgabe=super.getAufgabe();
		aufgabe.setBearbeiter(bearbeiter_Grid.getSelectedEntities().stream().map(o -> o.getId().getHref()).collect(Collectors.toList()));
		return aufgabe;
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
