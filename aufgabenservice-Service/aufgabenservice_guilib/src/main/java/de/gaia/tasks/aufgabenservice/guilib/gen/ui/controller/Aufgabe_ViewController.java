package de.gaia.tasks.aufgabenservice.guilib.gen.ui.controller;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

import de.muenchen.eventbus.EventBus;
import de.muenchen.eventbus.events.Association;
import de.muenchen.eventbus.selector.entity.RequestEntityKey;
import de.muenchen.eventbus.selector.entity.RequestEvent;
import de.muenchen.eventbus.selector.entity.ResponseEntityKey;

import de.gaia.tasks.aufgabenservice.guilib.gen.services.Aufgabe_Service;
import de.gaia.tasks.aufgabenservice.api.local.Aufgabe_;
import de.gaia.tasks.aufgabenservice.guilib.gen.services.Mitarbeiter_Service;
import de.gaia.tasks.aufgabenservice.api.local.Mitarbeiter_;
import de.gaia.tasks.aufgabenservice.guilib.gen.services.model.Aufgabe_Datastore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Link;
import reactor.bus.Event;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import de.muenchen.vaadin.guilib.BaseUI;

/*
 * This file will be overwritten on every change of the model!
 * This file was automatically generated by GAIA.
 */
@SpringComponent
@UIScope
public class Aufgabe_ViewController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(Aufgabe_ViewController.class);
	
	/**
	 * Die Aufgabe_Service Klasse
	 */
	@Autowired
	Aufgabe_Service aufgabeService;
	
	/**
	 * Die Mitarbeiter_Service Klasse
	 */
	@Autowired
	Mitarbeiter_Service mitarbeiterService;
	
	private final Aufgabe_Datastore model = new Aufgabe_Datastore();
	

	@PostConstruct
	private void init() {
		initEventhandlers();
	}
	
	private EventBus getEventbus() {
        return BaseUI.getCurrentEventBus();
    }
	
	public Aufgabe_Datastore getModel() {
		return model;
	}

	////////////////////////
	// Service Operations //
	////////////////////////

	/**
	 * Speichert ein {@link Aufgabe_} Objekt in der Datenbank.
	 *
	 * @param aufgabe Aufgabe_ der gespeichert werden soll
	 */
	public Aufgabe_ save(Aufgabe_ aufgabe) {
		return aufgabeService.create(aufgabe);
	}

	/**
	 * Zerstört die Verbindung zwischen einem Aufgabe_ und seinem bearbeiter
	 *
	 * @param event
	 */
	private void releaseBearbeiter(Mitarbeiter_ event) {
		Link link = getModel().getSelectedAufgabe().get().getLink(Aufgabe_.Rel.bearbeiter.name());
		List<Link> bearbeiter = mitarbeiterService.findAll(link)
				.stream()
				.map(Mitarbeiter_::getId)
				.filter(id -> !id.equals(event.getId()))
				.collect(Collectors.toList());

		aufgabeService.setRelations(link, bearbeiter);
	}
	
	
	/**
	 * Speichert eine beziehung als bearbeiter zu einem {@link Mitarbeiter_} Objekt in der Datenbank.
	 *
	 * @param bearbeiterEntity Bearbeiter
	 * @return Mitarbeiter_
	 */
	public void addAufgabeBearbeiter(Mitarbeiter_ bearbeiterEntity) {
		Link link = getModel().getSelectedAufgabe().get().getLink(Aufgabe_.Rel.bearbeiter.name());
		List<Link> bearbeiterlist = Stream.concat(
				mitarbeiterService.findAll(link)
						.stream()
						.map(Mitarbeiter_::getId),
				Stream.of(bearbeiterEntity.getId()))
	
				.collect(Collectors.toList());
	
		aufgabeService.setRelations(link, bearbeiterlist);
	}
	
	public List<Mitarbeiter_> queryBearbeiter(Aufgabe_ entity) {
		return mitarbeiterService.findAll(entity.getLink(Aufgabe_.Rel.bearbeiter.name())).stream().collect(Collectors.toList());
	}
	/**
	 * Speichert die Änderungen an einem {@link Aufgabe_} Objekt in der Datenbank.
	 *
	 * @param entity Aufgabe_
	 * @return Aufgabe_
	 */
	public Aufgabe_ updateAufgabe(Aufgabe_ entity) {
		return aufgabeService.update(entity);
	}

	/**
	 * Löscht ein {@link Aufgabe_} Objekt.
	 *
	 * @param entity Aufgabe_
	 */
	public void deleteAufgabe(Aufgabe_ entity) {
		aufgabeService.delete(entity.getId());
	}

	public List<Aufgabe_> queryAufgabe() {
		return aufgabeService.findAll().stream().collect(Collectors.toList());
	}

	public List<Aufgabe_> queryAufgabe(String query) {
		return aufgabeService.queryAufgabe(query);
	}
	
	/////////////////////
	// Event Steuerung //
	/////////////////////

	/**
	 * Register all event handlers on the RequestEntityKey.
	 */
	private void initEventhandlers() {
		getEventbus().on(getRequestKey(RequestEvent.CREATE).toSelector(), this::create);
		getEventbus().on(getRequestKey(RequestEvent.DELETE).toSelector(), this::delete);	 
		getEventbus().on(getRequestKey(RequestEvent.UPDATE).toSelector(), this::update);
		getEventbus().on(getRequestKey(RequestEvent.ADD_ASSOCIATION).toSelector(), this::addAssociation);	 
		getEventbus().on(getRequestKey(RequestEvent.REMOVE_ASSOCIATION).toSelector(), this::removeAssociation);
		getEventbus().on(getRequestKey(RequestEvent.READ_LIST).toSelector(), this::readList);
		getEventbus().on(getRequestKey(RequestEvent.READ_SELECTED).toSelector(), this::readSelected);
	}

	/**
	 * Remove the specified Association from the specified Relation and update the DataStore.
	 * Update the Model and send it on the ResponseEntityKey if necessary.
	 *
	 * @param event The event with an {@link Association} as {@link Event#getData()}.
	 */
	void removeAssociation(Event<?> event) {
		final Object data = event.getData();
		if (data == null)
			throw new NullPointerException("Event data must not be null!");
		if (data.getClass() != Association.class)
			throw new IllegalArgumentException("The event must be of " + Association.class);

		final Association<?> association = (Association<?>) event.getData();
		final Aufgabe_.Rel rel = Aufgabe_.Rel.valueOf(association.getRel());
		
		if (Aufgabe_.Rel.bearbeiter == rel) {
			Mitarbeiter_ bearbeiter = (Mitarbeiter_) association.getAssociation();
			releaseBearbeiter(bearbeiter);
			getModel().getSelectedAufgabeBearbeiter().removeItem(bearbeiter);
		}
		notifyComponents();
	}

	/**
	 * Add the specified Association to the specified Relation and update the DataStore.
	 * <p>	 
	 * If the {@link Association#getAssociation()} has no {@link ResourceSupport#getId()} the Resouce will be created
	 * on the DataStore first.
	 * </p>
	 * Update the Model and send it on the ResponseEntityKey if necessary.
	 *
	 * @param event The event with an {@link Association} as {@link Event#getData()}.
	 */
	private void addAssociation(Event<?> event) {
		final Object data = event.getData();
		if (data == null) 
			throw new NullPointerException("Event data must not be null!");
		if (data.getClass() != Association.class)
			throw new IllegalArgumentException("The event must be of " + Association.class);

		final Association<?> association = (Association<?>) event.getData();

		final Aufgabe_.Rel rel = Aufgabe_.Rel.valueOf(association.getRel());
		if (Aufgabe_.Rel.bearbeiter == rel) {
			Mitarbeiter_ bearbeiter = (Mitarbeiter_) association.getAssociation();
			// If Bearbeiter has no ID he has to be created in the backend
			if (bearbeiter.getId() == null) {
				bearbeiter = mitarbeiterService.create(bearbeiter);
			}
			addAufgabeBearbeiter(bearbeiter);
			getModel().getSelectedAufgabeBearbeiter().addBean(bearbeiter);
		}
		refreshModelAssociations();
		notifyComponents();
	}

	/**	
	 * Create a new Buerger on the DataStore.
	 * Update the Model and send it on the ResponseEntityKey if necessary.
	 *
	 * @param event The event with an {@link Aufgabe_} as {@link Event#getData()}.
	 */
	private void create(Event<?> event) {
		final Object data = event.getData();
		if (data == null) 
			throw new NullPointerException("Event data must not be null!");
		if (data.getClass() != Aufgabe_.class)
			throw new IllegalArgumentException("The event must be of " + Aufgabe_.class);
		final Aufgabe_ aufgabe = (Aufgabe_) event.getData();
		final Aufgabe_ fromREST = aufgabeService.create(aufgabe);
		getModel().getAufgabes().addBean(fromREST);
		notifyComponents();
	}


	/**
	 * Delete the Aufgabe_ on the DataStore.
	 * Update the Model and send it on the ResponseEntityKey if necessary.
	 *
	 * @param event The event with an {@link Aufgabe_} as {@link Event#getData()}.
	 */
	private void delete(Event<?> event) {
		final Object data = event.getData();
		if (data == null) 
			throw new NullPointerException("Event data must not be null!");
		if (data.getClass() != Aufgabe_.class)
			throw new IllegalArgumentException("The event must be of " + Aufgabe_.class);
		final Aufgabe_ aufgabe = (Aufgabe_) event.getData();
		if (aufgabe.getId() == null)
			throw new IllegalArgumentException("The Aufgabe_ must have an ID.");
		aufgabeService.delete(aufgabe.getId());
		getModel().getSelectedAufgabe().ifPresent(selectedAufgabe -> {
			if (selectedAufgabe.equals(aufgabe)) {
				getModel().setSelectedAufgabe(null);
				// reset all selected relations
				getModel().getSelectedAufgabeBearbeiter().removeAllItems();
			}
		});
		getModel().getAufgabes().removeItem(aufgabe);
		notifyComponents();
	}

	/**
	 * Update the Aufgabe_ on the DataStore.
	 * Update the Model and send it on the ResponseEntityKey if necessary.
	 *
	 * @param event The event with an {@link Aufgabe_} as {@link Event#getData()}.
	 */
	private void update(Event<?> event) {
		final Object data = event.getData();	 
		if (data == null) 
			throw new NullPointerException("Event data must not be null!");
		if (data.getClass() != Aufgabe_.class)
			throw new IllegalArgumentException("The event must be of " + Aufgabe_.class);
		final Aufgabe_ aufgabe = (Aufgabe_) event.getData();
		if (aufgabe.getId() == null)
			throw new IllegalArgumentException("The Aufgabe_ must have an ID.");
		final Aufgabe_ fromREST = aufgabeService.update(aufgabe);
		refreshModelSelected();
		getModel().getAufgabes().addBean(fromREST);
		notifyComponents();
	}

	/**
	 * Refresh the {@link Aufgabe_Datastore#aufgabes} list from the DataStore.
	 * <p/>	
	 * <p>
	 * This method also filters by the query (ifPresent).
	 * </p>
	 */
	private void refreshModelList() {
		final Optional<String> query = getModel().getQuery();
		if (query.isPresent()) {
			getModel().getAufgabes().removeAllItems();
			getModel().getAufgabes().addAll(queryAufgabe(query.get()));
		} else {
			getModel().getAufgabes().removeAllItems();
			getModel().getAufgabes().addAll(queryAufgabe());
		}
	}

	/**
	 * Refresh *all* the associations of the selected Aufgabe_ in the model.
	 */
	void refreshModelAssociations() {
		getModel().getSelectedAufgabe().ifPresent(aufgabe -> {
			final List<Mitarbeiter_> bearbeiter = queryBearbeiter(aufgabe);
			getModel().getSelectedAufgabeBearbeiter().removeAllItems();
			getModel().getSelectedAufgabeBearbeiter().addAll(bearbeiter);
		});	
	}

	/**
	 * Refresh the current selected Aufgabe_, but *not* its associations.
	 */
	private void refreshModelSelected() {
		getModel().getSelectedAufgabe().ifPresent(aufgabe -> getModel().setSelectedAufgabe(aufgabeService.findOne(aufgabe.getId()).orElse(null)));
	}

	/**	
	 * Set the query based on the String sent in the Event.
	 * Update the Model and send it on the ResponseEntityKey if necessary.
	 *
	 * @param event The event with a {@link String} query as {@link Event#getData()}.
	 */
	private void readList(Event<?> event) {
		final Object data = event.getData();

		if (data instanceof String) {
			final String filter = (String) event.getData();
			getModel().setQuery(filter);
		} else {
			getModel().setQuery(null);
		}

		refreshModelList();
		notifyComponents();
	}

	/**
	 * Read the Aufgabe_ in the Event from the DataStore and set it as the current selected Aufgabe_.
	 * If called with null, the current selected Aufgabe_ will only be refreshed from the DataStore.
	 * Update the Model and send it on the ResponseEntityKey if necessary.
	 *
	 * @param event The event with an {@link Aufgabe_} or *null* as {@link Event#getData()}.
	 */
	private void readSelected(Event<?> event) {
		final Object data = event.getData();

		if (data instanceof Aufgabe_) {
			final Aufgabe_ aufgabe = (Aufgabe_) event.getData();
			getModel().setSelectedAufgabe(aufgabe);
			refreshModelSelected();
			refreshModelAssociations();
		} else if (data == null) {
			refreshModelSelected();
			refreshModelAssociations();
		} else {
			throw new IllegalArgumentException("The event cannot be of Class " + event.getData().getClass());
		}
		notifyComponents();
    }

	/**
	 * Notify all the Components.
	 */
	public void notifyComponents() {
		getEventbus().notify(getResponseKey(), Event.wrap(getModel()));
	}

	/**
	 * Get the RequestEntityKey for this Entity.
	 *
	 * @param event The disered event the Key will have.
	 * @return The RequestEntityKey with the chosen RequestEvent.
	 */
	public RequestEntityKey getRequestKey(RequestEvent event) {
		return new RequestEntityKey(event, Aufgabe_.class);
	}

	/**
	 * Get the ResponseEntityKey for this Entity.
	 *
	 * @return The ResponseEntityKey.
	 */
	public ResponseEntityKey getResponseKey() {
		return new ResponseEntityKey(Aufgabe_.class);
	}
}