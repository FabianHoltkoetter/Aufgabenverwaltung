package de.gaia.tasks.aufgabengui.ui;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import de.muenchen.eventbus.EventBus;
import de.muenchen.eventbus.selector.Key;
import de.muenchen.vaadin.guilib.services.SecurityService;
import de.muenchen.vaadin.demo.i18nservice.I18nResolverImpl;
import de.muenchen.vaadin.demo.i18nservice.MessageService;
import de.muenchen.vaadin.demo.i18nservice.buttons.SimpleAction;


import de.muenchen.vaadin.guilib.BaseUI;
import de.muenchen.vaadin.guilib.ValoMenuLayout;
import de.muenchen.vaadin.guilib.components.ConfirmationWindow;
import de.gaia.tasks.aufgabengui.ui.views.LoginView;
import de.gaia.tasks.aufgabengui.ui.views.Mainview_View;
import de.gaia.tasks.aufgabengui.ui.views.Mitarbeiter_View;
import de.gaia.tasks.aufgabengui.ui.views.Aufgaben_View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import static reactor.bus.Event.wrap;

@SpringUI
@Title("Vaadin Spring-Security Sample")
@Theme("valo")
@PreserveOnRefresh
//@Widgetset("de.muenchen.vaadin.Widgetset")
public class MainUI extends BaseUI {
	
    private static final Logger LOG = LoggerFactory.getLogger(MainUI.class);

    private static final long serialVersionUID = 5310014981075920878L;

    private final SpringViewProvider viewProvider;
    private final SecurityService security;
    private final MessageService i18n;
    private final boolean testMode = false;
    private final LinkedHashMap<String, String> menuItems = new LinkedHashMap<String, String>();
    protected ValoMenuLayout root = new ValoMenuLayout();
    protected ComponentContainer viewDisplay = root.getContentContainer();
    protected CssLayout menu = new CssLayout();
    protected CssLayout help = new CssLayout();
	private Label helpContent;
    protected CssLayout menuItemsLayout = new CssLayout();
    

    private MenuBar bar = new MenuBar();
    private MenuBar.MenuItem language;

    @Autowired
    public MainUI(EventBus eventBus, I18nResolverImpl i18nResolver, SpringViewProvider ViewProvider, SecurityService security, MessageService i18n) {
        super(eventBus, i18nResolver);
        LOG.info("starting UI");
        this.viewProvider = ViewProvider;
        this.security = security;
        this.i18n = i18n;
    }

    @Override
    protected void init(VaadinRequest request) {

        // Set Browser Locale
        setLocale(getPage().getWebBrowser().getLocale());
        i18n.setLocale(getPage().getWebBrowser().getLocale());

        // IE Support
        if (getPage().getWebBrowser().isIE()
                && getPage().getWebBrowser().getBrowserMajorVersion() == 9) {
            menu.setWidth("320px");
        }

        // mobile support
        if (!this.testMode) {
            Responsive.makeResponsive(this);
        }

        // build page
        getPage().setTitle(i18n.get("page.title"));
        setContent(root);
        root.setWidth("100%");
        root.addMenu(buildMenu());
        addStyleName(ValoTheme.UI_WITH_MENU);
        
        // Configure Help-Layout
		buildHelp();
		root.addComponent(help);
		root.setComponentAlignment(help, Alignment.TOP_RIGHT);

        // configure navigator
        VerticalLayout componentContainer = new VerticalLayout();
        viewDisplay.addComponent(buildMenuBar());
        viewDisplay.addComponent(componentContainer);
        final Navigator navigator = new Navigator(this, componentContainer);
        navigator.addProvider(viewProvider);
        setNavigator(navigator);

        // check security
        if (!this.security.isLoggedIn()) {
            getNavigator().navigateTo(LoginView.NAME);
            this.root.switchOffMenu();
        }
        // add navigator to security Service
//        this.security.setNavigator(this.navigator);


        navigator.addViewChangeListener(new ViewChangeListener() {
            @Override
            public boolean beforeViewChange(final ViewChangeEvent event) {

                LOG.debug("View change to: " + (event.getViewName()));

                // Check if a user has logged in
                boolean isLoggedIn = security.isLoggedIn();
                boolean isLoginView = event.getNewView() instanceof LoginView;

                if (!isLoggedIn && !isLoginView) {
                    // Redirect to login view always if a user has not yet
                    // logged in
                    security.logout();
                    getNavigator().navigateTo(LoginView.NAME);
                    LOG.info("not logged in");
                    return false;

                } else if (isLoggedIn && isLoginView) {
                    // If someone tries to access to login view while logged in,
                    // then cancel
                    LOG.warn("login view cannot be entered while logged in.");
                    return false;
                }
                return true;
            }

            @Override
            public void afterViewChange(final ViewChangeEvent event) {

                for (final Iterator<Component> it = menuItemsLayout.iterator(); it
                        .hasNext(); ) {
                    it.next().removeStyleName("selected");
                }
                for (final Entry<String, String> item : menuItems.entrySet()) {
                    if (event.getViewName().equals(item.getKey())) {
                        for (final Iterator<Component> it = menuItemsLayout
                                .iterator(); it.hasNext(); ) {
                            final Component c = it.next();
                            if (c.getCaption() != null
                                    && c.getCaption().startsWith(
                                    item.getValue())) {
                                c.addStyleName("selected");
                                break;
                            }
                        }
                        break;
                    }
                }
                menu.removeStyleName("valo-menu-visible");
            }
        });

        getCurrentEventBus().on(Key.LOGIN.toSelector(), this::loginEventHandler);
        getCurrentEventBus().on(Key.LOGOUT.toSelector(), this::logoutEventHandler);
    }

    public void loginEventHandler(reactor.bus.Event<?> event) {
        this.root.switchOnMenu();
        getNavigator().navigateTo(Mainview_View.NAME);
    }

    public void logoutEventHandler(reactor.bus.Event<?> event) {
        this.root.switchOffMenu();
        security.logout();

        // Close the VaadinServiceSession
        getUI().getSession().close();
        // Invalidate underlying session instead if login info is stored there
        VaadinService.getCurrentRequest().getWrappedSession().invalidate();
        LOG.info("logged out");

        // Redirect to avoid keeping the removed UI open in the browser
        getUI().getPage().setLocation("/");
    }

    private CssLayout buildMenu() {
        this.menu.addComponent(this.createTitle("Valo Theme"));
        this.menu.addComponent(this.createNavigationMenu());
        return menu;
    }
    
    private final String helpHtmlWrap = "<p style=\"margin: 6px; color: white\">%s</p>";
	
	private CssLayout buildHelp() {
		help.addComponent(createTitle("Help"));
		helpContent = new Label(String.format(helpHtmlWrap, "Not yet initialized..."), ContentMode.HTML);
		help.setStyleName(ValoTheme.LABEL_NO_MARGIN);
		help.addComponent(helpContent);
		help.setWidth(220, Unit.PIXELS);
		help.setVisible(false);
		help.addStyleName("valo-menu valo-menu-part v-csslayout-valo-menu-part");
		return help;
	}
	
	public void setHelpContent(){
		helpContent.setValue(getCurrentI18nResolver().resolve("view_."+(getNavigator().getState().equals("")?"mainview":getNavigator().getState())+".helptext"));
	}

    private Component createTitle(String titleText) {
        final HorizontalLayout top = new HorizontalLayout();
        top.setWidth("100%");
        top.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        top.addStyleName("valo-menu-title");
        final Label title = new Label(
                "<h3>Vaadin <strong>" + titleText + "</strong></h3>", ContentMode.HTML);
        title.setSizeUndefined();
        top.addComponent(title);
        top.setExpandRatio(title, 1);
        return top;
    }

    private MenuBar buildMenuBar() {
        bar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
        addLanguageSelector(bar);
        addHelpToggle(bar);
        return bar;
    }

	private MenuBar addHelpToggle(MenuBar bar) {
		bar.addItem("Help", FontAwesome.INFO, selectedItem -> toggleHelpBar());
		root.addShortcutListener(new ShortcutListener("togglehelp", ShortcutAction.KeyCode.F1, null) {
			@Override
			public void handleAction(Object sender, Object target) {
				toggleHelpBar();
			}
		});
		return bar;
	}
	
	private void toggleHelpBar() {
		help.setVisible(!help.isVisible());
	}

    private MenuBar addLanguageSelector(MenuBar bar) {
        language = bar.addItem(BaseUI.getCurrentI18nResolver().resolve("sprache.title"), FontAwesome.LANGUAGE, null);

        MenuBar.Command languageSelection = selectedItem -> i18n.getSupportedLocales().stream().forEach(locale -> {
            if (selectedItem.getText().equals(locale.getDisplayLanguage())) {
                i18n.setLocale(locale);
                getNavigator().navigateTo(getNavigator().getState());

                language.setText(BaseUI.getCurrentI18nResolver().resolve("sprache.title"));
				setHelpContent();
				
                removeMenuItems();
                createNavigationMenu();
            }
        });

        i18n.getSupportedLocales().stream().forEach(locale -> language.addItem(locale.getDisplayLanguage(), null, languageSelection));
        return bar;
    }
    

    private Component createSettings() {
        return null;
    }

    private void addMenuItems() {
        this.menuItems.put(Mainview_View.NAME, BaseUI.getCurrentI18nResolver().resolve("view_.mainview.title"));
        this.menuItems.put(Mitarbeiter_View.NAME, BaseUI.getCurrentI18nResolver().resolve("view_.mitarbeiter.title"));
        this.menuItems.put(Aufgaben_View.NAME, BaseUI.getCurrentI18nResolver().resolve("view_.aufgaben.title"));
    }

    private void removeMenuItems() {
        this.menuItems.clear();
        menuItemsLayout.removeAllComponents();
    }

    private Component createNavigationMenu() {

        addMenuItems();

        menuItemsLayout.setPrimaryStyleName("valo-menuitems");

        for (final Entry<String, String> item : menuItems.entrySet()) {
            final Button b = new Button(item.getValue(), event -> {
                getNavigator().navigateTo(item.getKey());
            });
            b.setHtmlContentAllowed(true);
            b.setPrimaryStyleName(ValoTheme.MENU_ITEM);
            b.setId(String.format("MENU_ITEM_BUTTON_%s", item.getKey()).toUpperCase());
//            b.setIcon(testIcon.get());
            menuItemsLayout.addComponent(b);
        }

        // creates and displays the logout button
        final Button logoutButton = new Button("Logout", event -> {
            ConfirmationWindow confirmationWindow =
                    new ConfirmationWindow(SimpleAction.logout);
							confirmationWindow.addActionPerformer(
                            e -> {
                                this.postEvent(Key.LOGOUT);
                                return true;
                            });
            getUI().addWindow(confirmationWindow);
            confirmationWindow.center();
            confirmationWindow.focus();
        });
        logoutButton.setHtmlContentAllowed(true);
        logoutButton.setPrimaryStyleName(ValoTheme.MENU_ITEM);
        logoutButton.setId("MENU_ITEM_BUTTON_LOGOUT");
        menuItemsLayout.addComponent(logoutButton);

        return menuItemsLayout;
    }

    public void postEvent(Object event) {
        getCurrentEventBus().notify(event, wrap(event));
    }
}

