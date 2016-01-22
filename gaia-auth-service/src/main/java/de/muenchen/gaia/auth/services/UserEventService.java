package de.muenchen.gaia.auth.services;


/*
 * This file will be overwritten on every change of the model!
 * This file was automatically generated by GAIA.
 */


import de.muenchen.gaia.auth.entities.User;

/**
 * Provides methods to implement logic before and after Events.
 */
public interface UserEventService {
    void onAfterCreate(User entity);

    void onBeforeCreate(User entity);

    void onBeforeSave(User entity);

    void onAfterSave(User entity);

    void onBeforeLinkSave(User parent, Object linked);

    void onAfterLinkSave(User parent, Object linked);

    void onBeforeLinkDelete(User parent, Object linked);

    void onBeforeDelete(User entity);

    void onAfterDelete(User entity);

    void onAfterLinkDelete(User parent, Object linked);
}
