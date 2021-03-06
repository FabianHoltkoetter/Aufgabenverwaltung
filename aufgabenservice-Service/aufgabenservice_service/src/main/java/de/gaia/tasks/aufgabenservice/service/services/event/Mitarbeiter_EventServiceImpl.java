package de.gaia.tasks.aufgabenservice.service.services.event;

import org.springframework.stereotype.Service;

import de.gaia.tasks.aufgabenservice.service.gen.domain.Mitarbeiter_;
import de.gaia.tasks.aufgabenservice.service.gen.services.event.Mitarbeiter_EventService;

/*
 * This file will NOT be overwritten by GAIA.
 * This file was automatically generated by GAIA.
 */
/**
 * Provides methods to implement logic before and after Events.
 * If used as generated by GAIA this service will be autowired and called by Mitarbeiter_EventListener.
 */
@Service
public class Mitarbeiter_EventServiceImpl implements Mitarbeiter_EventService{
	// If you need access to the database you can autowire a Repository.
	// Repositories are generated into the package: .gen.rest
	//
	// @Autowired
	// <EntityName>Repository repo;
	
	@Override
	public void onAfterCreate(Mitarbeiter_ entity) {
		// Add your logic here.
	}
	@Override
	public void onBeforeCreate(Mitarbeiter_ entity) {
		// Add your logic here.
	}
	@Override
	public void onBeforeSave(Mitarbeiter_ entity) {
		// Add your logic here.
	}
	@Override
	public void onAfterSave(Mitarbeiter_ entity) {
		// Add your logic here.
	}
	@Override
	public void onBeforeLinkSave(Mitarbeiter_ parent, Object linked) {
		// Add your logic here.
	}
	@Override
	public void onAfterLinkSave(Mitarbeiter_ parent, Object linked) {
		// Add your logic here.
	}
	@Override
	public void onBeforeLinkDelete(Mitarbeiter_ parent, Object linked) {
		// Add your logic here.
	}
	@Override
	public void onBeforeDelete(Mitarbeiter_ entity) {
		// Add your logic here.
	}
	@Override
	public void onAfterDelete(Mitarbeiter_ entity) {
		// Add your logic here.
	}
	@Override
	public void onAfterLinkDelete(Mitarbeiter_ parent, Object linked) {
		// Add your logic here.
	}
}
