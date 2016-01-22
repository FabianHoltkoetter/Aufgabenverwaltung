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
import de.gaia.tasks.aufgabenservice.api.local.Aufgabe_;



import de.gaia.tasks.aufgabengui.ui.MainUI;
import de.muenchen.vaadin.guilib.BaseUI;
import de.muenchen.vaadin.demo.i18nservice.buttons.SimpleAction;
import de.muenchen.vaadin.guilib.components.actions.NavigateActions;
import de.muenchen.vaadin.guilib.components.buttons.ActionButton;

import de.gaia.tasks.aufgabenservice.guilib.gen.ui.components.entity.aufgabe.Aufgabe_ReadWriteForm;
import de.gaia.tasks.aufgabenservice	.guilib.gen.ui.components.relation.aufgabe.Aufgabe_Bearbeiter_ReadEditGrid;
import de.gaia.tasks.aufgabenservice.guilib.gen.ui.controller.Aufgabe_ViewController;

@SpringView(name = AufgabeBearbeiten_View.NAME)
@UIScope
public class AufgabeBearbeiten_View extends DefaultView{				
	public static final String NAME = "aufgabeBearbeiten";

	@Autowired
	private Aufgabe_ViewController aufgabeController;
	
	@Override
	protected void init(){
		Label pageTitle = new Label(BaseUI.getCurrentI18nResolver().resolve("view_.aufgabeBearbeiten.title"));
        pageTitle.addStyleName(ValoTheme.LABEL_H1);
        pageTitle.addStyleName(ValoTheme.LABEL_COLORED);
        addComponent(pageTitle);
		
		final Aufgabe_Bearbeiter_ReadEditGrid component1 = new Aufgabe_Bearbeiter_ReadEditGrid(aufgabeController, NAME, NAME, BearbeiterHinzufuegen_View.NAME);
		
		final Aufgabe_ReadWriteForm component2 = new Aufgabe_ReadWriteForm();
		
		// Add components to the default layout
		final VerticalLayout layout = new VerticalLayout(component1, component2);
		
		layout.setSpacing(true);
		addComponent(layout);
	}
}
