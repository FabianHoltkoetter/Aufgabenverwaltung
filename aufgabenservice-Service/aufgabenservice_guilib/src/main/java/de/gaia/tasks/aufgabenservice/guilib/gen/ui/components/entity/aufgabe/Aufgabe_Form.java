package de.gaia.tasks.aufgabenservice.guilib.gen.ui.components.entity.aufgabe;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Field;
import org.vaadin.tokenfield.TokenField;
import com.vaadin.data.Validator;

import de.gaia.tasks.aufgabenservice.api.local.Aufgabe_;
import de.muenchen.vaadin.guilib.util.FormUtil;
import de.muenchen.vaadin.guilib.components.BaseComponent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Provides a very simple and basic Form for a Aufgabe_.
 * <p/>
 * If no Aufgabe_ is set, a blank user without an ID will be used. It has no buttons or additional components but can be
 * used for any Aufgabe_ you set it to.
 *
 * @author p.mueller
 * @version 1.0
 */
public class Aufgabe_Form extends BaseComponent {

    /** The class of the Entity of this Form. */
    public static final Class<Aufgabe_> ENTITY_CLASS = Aufgabe_.class;
    
    /** The FormLayout that contains all the form fields. */
    private final FormLayout formLayout;
    
    /** Contains the current Aufgabe_ and handles the data binding. */
    private final BeanFieldGroup<Aufgabe_> binder = new BeanFieldGroup<>(ENTITY_CLASS);
    
    /** A list of all the Fields. */
    private final List<Field> fields;

    /**
     * Create a new Aufgabe_Form using the specified i18nResolver and the eventbus.
     * <p/>
     * This Form is only the plain fields for input, and has no additional components or buttons. You can use {@link
     * Aufgabe_Form#setReadOnly(boolean)} for a readonly mode.
     */
    public Aufgabe_Form() {
        binder.setItemDataSource(new Aufgabe_());
        fields = buildFields();

        final FormLayout formLayout = new FormLayout();
        fields.stream().forEach(formLayout::addComponent);

        this.formLayout = formLayout;
        setCompositionRoot(formLayout);
    }


    /**
     * Build all the (input) Fields used by this form.
     * <p/>
     * The Fields are data binded to the Aufgabe_.
     *
     * @return A List of all Components.
     */
    private List<Field> buildFields() {
        final FormUtil formUtil = new FormUtil(getBinder());

		final TextField beschreibung = formUtil.createTextField(Aufgabe_.Field.beschreibung.name());
		final ComboBox prioritaet = formUtil.createComboBox(Aufgabe_.Field.prioritaet.name());
		final DateField faelligAm = formUtil.createDateField(Aufgabe_.Field.faelligAm.name());
		
        return Arrays.asList(beschreibung, prioritaet, faelligAm);
    }

    /**
     * Get the Data-Binder of this Form.
     *
     * @return The binder.
     */
    private BeanFieldGroup<Aufgabe_> getBinder() {
        return binder;
    }

    /**
     * Get the Aufgabe_ object of this form.
     *
     * @return The Aufgabe_.
     */
    public Aufgabe_ getAufgabe() {
        try {
            getBinder().commit();
        } catch (FieldGroup.CommitException e) {
            throw new Validator.InvalidValueException("Cannot create Aufgabe.");
        }
        return getBinder().getItemDataSource().getBean();
    }

    /**
     * Set the Aufgabe_ of this Form.
     *
     * @param aufgabe The new Aufgabe_.
     */
    public void setAufgabe(Aufgabe_ aufgabe) {
        getBinder().setItemDataSource(aufgabe == null ? new Aufgabe_() : aufgabe);
    }

    /**
     * Get the layout of this form, containing all the Fields.
     *
     * @return The base Layout.
     */
    public FormLayout getFormLayout() {
        return formLayout;
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        getBinder().setReadOnly(readOnly);
    }

    /**
     * Get all the (input) Fields of this form as a list.
     *
     * @return The list of components.
     */
    public List<Field> getFields() {
        return Collections.unmodifiableList(fields);
    }

}
