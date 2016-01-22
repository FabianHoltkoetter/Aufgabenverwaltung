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
import de.gaia.tasks.aufgabenservice.api.local.Mitarbeiter_;



import de.gaia.tasks.aufgabengui.ui.MainUI;
import de.muenchen.vaadin.guilib.BaseUI;
import de.muenchen.vaadin.demo.i18nservice.buttons.SimpleAction;
import de.muenchen.vaadin.guilib.components.actions.NavigateActions;
import de.muenchen.vaadin.guilib.components.buttons.ActionButton;

import de.gaia.tasks.aufgabenservice.guilib.gen.ui.components.entity.mitarbeiter.Mitarbeiter_CreateForm;

@SpringView(name = MitarbeiterErstellen_View.NAME)
@UIScope
public class MitarbeiterErstellen_View extends DefaultView{				
	public static final String NAME = "mitarbeiterErstellen";
	
	@Override
	protected void init(){
		Label pageTitle = new Label(BaseUI.getCurrentI18nResolver().resolve("view_.mitarbeiterErstellen.title"));
        pageTitle.addStyleName(ValoTheme.LABEL_H1);
        pageTitle.addStyleName(ValoTheme.LABEL_COLORED);
        addComponent(pageTitle);
		
		final Mitarbeiter_CreateForm component1 = new Mitarbeiter_CreateForm(Mitarbeiter_View.NAME
		);
		
		// Add components to the default layout
		final VerticalLayout layout = new VerticalLayout(component1);
		
		layout.setSpacing(true);
		addComponent(layout);
	}
}
