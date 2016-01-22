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

import de.gaia.tasks.aufgabenservice.guilib.gen.ui.components.entity.aufgabe.Aufgabe_Grid;
import de.gaia.tasks.aufgabenservice.guilib.gen.ui.controller.Aufgabe_ViewController;

@SpringView(name = Aufgaben_View.NAME)
@UIScope
public class Aufgaben_View extends DefaultView{				
	public static final String NAME = "aufgaben";

	@Autowired
	private Aufgabe_ViewController aufgabeController;
	
	@Override
	protected void init(){
		Label pageTitle = new Label(BaseUI.getCurrentI18nResolver().resolve("view_.aufgaben.title"));
        pageTitle.addStyleName(ValoTheme.LABEL_H1);
        pageTitle.addStyleName(ValoTheme.LABEL_COLORED);
        addComponent(pageTitle);
		
		final Aufgabe_Grid component1 = new Aufgabe_Grid(aufgabeController
		);
		component1.activateSearch().activateCopy().activateDelete();
		component1.activateCreate(AufgabeErstellen_View.NAME);
		component1.activateEdit(AufgabeBearbeiten_View.NAME);
		component1.activateRead(NAME).activateDoubleClickToRead(NAME);
		
		// Add components to the default layout
		final VerticalLayout layout = new VerticalLayout(component1);
		
		layout.setSpacing(true);
		addComponent(layout);
	}
}
