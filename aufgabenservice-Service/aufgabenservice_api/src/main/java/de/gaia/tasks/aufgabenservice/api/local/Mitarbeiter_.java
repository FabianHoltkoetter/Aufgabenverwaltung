package de.gaia.tasks.aufgabenservice.api.local;

/*
 * This file will NOT be overwritten by GAIA.
 * This file was automatically generated by GAIA.
 */
import java.util.stream.Stream;

import javax.validation.constraints.Size;
import javax.validation.constraints.Past;
import javax.validation.constraints.NotNull;
import org.springframework.hateoas.ResourceSupport;
/*
 * This file will be overwritten on every change of the model!
 * This file was automatically generated by GAIA.
 */
public class Mitarbeiter_ extends ResourceSupport {
	
	@NotNull
	private String name;
	
	@NotNull
	@Size(min=1, max=50)
	private String mail;
	
	@NotNull
	@Past
	private java.util.Date geburtsdatum;
	
	public Mitarbeiter_(){}
	
	/**
     * Create a new Mitarbeiter_ with the  name, mail, geburtsdatum.
     *
     * @param name the name of the Mitarbeiter_.
     * @param mail the mail of the Mitarbeiter_.
     * @param geburtsdatum the geburtsdatum of the Mitarbeiter_.
     */
    public Mitarbeiter_( String name, String mail, java.util.Date geburtsdatum) {
        this.setName(name);
        this.setMail(mail);
        this.setGeburtsdatum(geburtsdatum);
    }
	
	// Getters and Setters
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getMail(){
		return mail;
	}
	
	public void setMail(String mail){
		this.mail = mail;
	}
	
	public java.util.Date getGeburtsdatum(){
		return geburtsdatum;
	}
	
	public void setGeburtsdatum(java.util.Date geburtsdatum){
		this.geburtsdatum = geburtsdatum;
	}
	
	 /**
      * A simple Enum for all the Fields of this Mitarbeiter_.
      * <p>
      *     You can use {@link Field#name()} for the String.
      * </p>
      */
	public enum Field {
        name, mail, geburtsdatum;

        private final boolean field;

        Field() {
			this(true);
		}

		Field(boolean field) {
			this.field = field;
		}

		public boolean isField() {
			return field;
		}

		public static String[] getProperties() {
			return Stream.of(values()).filter(Field::isField).map(Field::name).toArray(String[]::new);
		}
	}
	
	 /**
	  * A simple Enum for all the Relations ({@link Mitarbeiter_#getLink(String)} of the Mitarbeiter_.
	  * <p>
	  *     You can use {@link Rel#name()} for the String.
	  * </p>
	  */
	 public enum Rel {
	 	;
    }
	
	@Override
	public String toString(){
		String s = "";
		s += "String name: " + this.getName();
		s += "String mail: " + this.getMail();
		s += "java.util.Date geburtsdatum: " + this.getGeburtsdatum();
		return s;
	}
}
