package de.gaia.tasks.aufgabengui.ui.views;

import de.gaia.tasks.aufgabenservice.guilib.gen.services.BusinessActionsService;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;
import java.util.ArrayList;
import java.util.List;
import de.gaia.tasks.aufgabenservice.api.local.Mitarbeiter_;



import de.gaia.tasks.aufgabengui.ui.MainUI;
import de.muenchen.vaadin.guilib.BaseUI;
import de.muenchen.vaadin.demo.i18nservice.buttons.SimpleAction;
import de.muenchen.vaadin.guilib.components.actions.NavigateActions;
import de.muenchen.vaadin.guilib.components.buttons.ActionButton;

import de.gaia.tasks.aufgabenservice.guilib.gen.ui.components.entity.mitarbeiter.Mitarbeiter_Grid;
import de.gaia.tasks.aufgabenservice.guilib.gen.ui.controller.Mitarbeiter_ViewController;

@SpringView(name = Mitarbeiter_View.NAME)
@UIScope
public class Mitarbeiter_View extends DefaultView{				
	public static final String NAME = "mitarbeiter";

	@Autowired
	private Mitarbeiter_ViewController mitarbeiterController;

	@Autowired
	private BusinessActionsService businessActionsService;
	
	@Override
	protected void init(){
		Label pageTitle = new Label(BaseUI.getCurrentI18nResolver().resolve("view_.mitarbeiter.title"));
        pageTitle.addStyleName(ValoTheme.LABEL_H1);
        pageTitle.addStyleName(ValoTheme.LABEL_COLORED);
        addComponent(pageTitle);
		
		final ActionButton component1 = new ActionButton(MainUI.getCurrentI18nResolver().resolve("view_.mitarbeiter.button.informieren.label"), SimpleAction.none);
		component1.addActionPerformer(clickEvent -> {
			businessActionsService.mitarbeiterInformieren();
			return true;
		});
		component1.setId("informieren-");
		
		final Mitarbeiter_Grid component2 = new Mitarbeiter_Grid(mitarbeiterController
		);
		component2.activateSearch().activateCopy().activateDelete();
		component2.activateCreate(MitarbeiterErstellen_View.NAME);
		component2.activateEdit(NAME);
		component2.activateRead(NAME).activateDoubleClickToRead(NAME);
		
		// Add components to the default layout
		final VerticalLayout layout = new VerticalLayout(component1, component2);
		
		layout.setSpacing(true);
		addComponent(layout);
	}
}
