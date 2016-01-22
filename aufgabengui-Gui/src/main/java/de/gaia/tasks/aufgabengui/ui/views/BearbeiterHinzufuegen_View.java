package de.gaia.tasks.aufgabengui.ui.views;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;
import java.util.ArrayList;
import java.util.List;



import de.gaia.tasks.aufgabengui.ui.MainUI;
import de.muenchen.vaadin.guilib.BaseUI;
import de.muenchen.vaadin.demo.i18nservice.buttons.SimpleAction;
import de.muenchen.vaadin.guilib.components.actions.NavigateActions;
import de.muenchen.vaadin.guilib.components.buttons.ActionButton;

import de.gaia.tasks.aufgabenservice	.guilib.gen.ui.components.relation.aufgabe.Aufgabe_Bearbeiter_AddGrid;
import de.gaia.tasks.aufgabenservice.guilib.gen.ui.controller.Aufgabe_ViewController;
import de.gaia.tasks.aufgabenservice.guilib.gen.ui.controller.Mitarbeiter_ViewController;

@SpringView(name = BearbeiterHinzufuegen_View.NAME)
@UIScope
public class BearbeiterHinzufuegen_View extends DefaultView{				
	public static final String NAME = "bearbeiterHinzufuegen";

	@Autowired
	private Aufgabe_ViewController aufgabeController;

	@Autowired
	private Mitarbeiter_ViewController mitarbeiterController;
	
	@Override
	protected void init(){
		Label pageTitle = new Label(BaseUI.getCurrentI18nResolver().resolve("view_.bearbeiterHinzufuegen.title"));
        pageTitle.addStyleName(ValoTheme.LABEL_H1);
        pageTitle.addStyleName(ValoTheme.LABEL_COLORED);
        addComponent(pageTitle);
		
		final Aufgabe_Bearbeiter_AddGrid component1 = new Aufgabe_Bearbeiter_AddGrid(mitarbeiterController, AufgabeBearbeiten_View.NAME);
		
		// Add components to the default layout
		final VerticalLayout layout = new VerticalLayout(component1);
		
		layout.setSpacing(true);
		addComponent(layout);
	}
}
