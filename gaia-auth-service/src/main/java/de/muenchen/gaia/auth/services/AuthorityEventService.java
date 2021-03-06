package de.muenchen.gaia.auth.services;

/*
 * This file will be overwritten on every change of the model!
 * This file was automatically generated by GAIA.
 */

import de.muenchen.gaia.auth.entities.Authority;


/**
 * Provides methods to implement logic before and after Events.
 */
public interface AuthorityEventService {
    void onAfterCreate(Authority entity);

    void onBeforeCreate(Authority entity);

    void onBeforeSave(Authority entity);

    void onAfterSave(Authority entity);

    void onBeforeLinkSave(Authority parent, Object linked);

    void onAfterLinkSave(Authority parent, Object linked);

    void onBeforeLinkDelete(Authority parent, Object linked);

    void onBeforeDelete(Authority entity);

    void onAfterDelete(Authority entity);

    void onAfterLinkDelete(Authority parent, Object linked);
}
