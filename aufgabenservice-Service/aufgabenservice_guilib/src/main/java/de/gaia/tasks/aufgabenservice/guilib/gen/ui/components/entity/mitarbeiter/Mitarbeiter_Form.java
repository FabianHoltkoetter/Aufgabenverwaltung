package de.gaia.tasks.aufgabenservice.guilib.gen.ui.components.entity.mitarbeiter;

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

import de.gaia.tasks.aufgabenservice.api.local.Mitarbeiter_;
import de.muenchen.vaadin.guilib.util.FormUtil;
import de.muenchen.vaadin.guilib.components.BaseComponent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Provides a very simple and basic Form for a Mitarbeiter_.
 * <p/>
 * If no Mitarbeiter_ is set, a blank user without an ID will be used. It has no buttons or additional components but can be
 * used for any Mitarbeiter_ you set it to.
 *
 * @author p.mueller
 * @version 1.0
 */
public class Mitarbeiter_Form extends BaseComponent {

    /** The class of the Entity of this Form. */
    public static final Class<Mitarbeiter_> ENTITY_CLASS = Mitarbeiter_.class;
    
    /** The FormLayout that contains all the form fields. */
    private final FormLayout formLayout;
    
    /** Contains the current Mitarbeiter_ and handles the data binding. */
    private final BeanFieldGroup<Mitarbeiter_> binder = new BeanFieldGroup<>(ENTITY_CLASS);
    
    /** A list of all the Fields. */
    private final List<Field> fields;

    /**
     * Create a new Mitarbeiter_Form using the specified i18nResolver and the eventbus.
     * <p/>
     * This Form is only the plain fields for input, and has no additional components or buttons. You can use {@link
     * Mitarbeiter_Form#setReadOnly(boolean)} for a readonly mode.
     */
    public Mitarbeiter_Form() {
        binder.setItemDataSource(new Mitarbeiter_());
        fields = buildFields();

        final FormLayout formLayout = new FormLayout();
        fields.stream().forEach(formLayout::addComponent);

        this.formLayout = formLayout;
        setCompositionRoot(formLayout);
    }


    /**
     * Build all the (input) Fields used by this form.
     * <p/>
     * The Fields are data binded to the Mitarbeiter_.
     *
     * @return A List of all Components.
     */
    private List<Field> buildFields() {
        final FormUtil formUtil = new FormUtil(getBinder());

		final TextField name = formUtil.createTextField(Mitarbeiter_.Field.name.name());
		final TextField mail = formUtil.createTextField(Mitarbeiter_.Field.mail.name());
		final DateField geburtsdatum = formUtil.createDateField(Mitarbeiter_.Field.geburtsdatum.name());
		
        return Arrays.asList(name, mail, geburtsdatum);
    }

    /**
     * Get the Data-Binder of this Form.
     *
     * @return The binder.
     */
    private BeanFieldGroup<Mitarbeiter_> getBinder() {
        return binder;
    }

    /**
     * Get the Mitarbeiter_ object of this form.
     *
     * @return The Mitarbeiter_.
     */
    public Mitarbeiter_ getMitarbeiter() {
        try {
            getBinder().commit();
        } catch (FieldGroup.CommitException e) {
            throw new Validator.InvalidValueException("Cannot create Mitarbeiter.");
        }
        return getBinder().getItemDataSource().getBean();
    }

    /**
     * Set the Mitarbeiter_ of this Form.
     *
     * @param mitarbeiter The new Mitarbeiter_.
     */
    public void setMitarbeiter(Mitarbeiter_ mitarbeiter) {
        getBinder().setItemDataSource(mitarbeiter == null ? new Mitarbeiter_() : mitarbeiter);
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
