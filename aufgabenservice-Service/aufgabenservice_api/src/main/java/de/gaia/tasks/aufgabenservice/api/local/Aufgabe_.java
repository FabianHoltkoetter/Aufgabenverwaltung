package de.gaia.tasks.aufgabenservice.api.local;

/*
 * This file will NOT be overwritten by GAIA.
 * This file was automatically generated by GAIA.
 */
import java.util.stream.Stream;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import de.gaia.tasks.aufgabenservice.api.domain.Prioritaet_;
import org.springframework.hateoas.ResourceSupport;
/*
 * This file will be overwritten on every change of the model!
 * This file was automatically generated by GAIA.
 */
public class Aufgabe_ extends ResourceSupport {
	
	@NotNull
	private String beschreibung;
	
	@NotNull
	private Prioritaet_ prioritaet;
	
	@NotNull
	@Future
	private java.util.Date faelligAm;
	
	private java.util.Collection<String> bearbeiter;
	
	public Aufgabe_(){}
	
	/**
     * Create a new Aufgabe_ with the  beschreibung, prioritaet, faelligAm.
     *
     * @param beschreibung the beschreibung of the Aufgabe_.
     * @param prioritaet the prioritaet of the Aufgabe_.
     * @param faelligAm the faelligAm of the Aufgabe_.
     */
    public Aufgabe_( String beschreibung, Prioritaet_ prioritaet, java.util.Date faelligAm) {
        this.setBeschreibung(beschreibung);
        this.setPrioritaet(prioritaet);
        this.setFaelligAm(faelligAm);
    }
	
	// Getters and Setters
	public String getBeschreibung(){
		return beschreibung;
	}
	
	public void setBeschreibung(String beschreibung){
		this.beschreibung = beschreibung;
	}
	
	public Prioritaet_ getPrioritaet(){
		return prioritaet;
	}
	
	public void setPrioritaet(Prioritaet_ prioritaet){
		this.prioritaet = prioritaet;
	}
	
	public java.util.Date getFaelligAm(){
		return faelligAm;
	}
	
	public void setFaelligAm(java.util.Date faelligAm){
		this.faelligAm = faelligAm;
	}
	
	public java.util.Collection<String> getBearbeiter(){
		return bearbeiter;
	}
	public void setBearbeiter(java.util.Collection<String> value){
		this.bearbeiter = value;
	}
	
	 /**
      * A simple Enum for all the Fields of this Aufgabe_.
      * <p>
      *     You can use {@link Field#name()} for the String.
      * </p>
      */
	public enum Field {
        beschreibung, prioritaet, faelligAm;

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
	  * A simple Enum for all the Relations ({@link Aufgabe_#getLink(String)} of the Aufgabe_.
	  * <p>
	  *     You can use {@link Rel#name()} for the String.
	  * </p>
	  */
	 public enum Rel {
	 	bearbeiter;
    }
	
	@Override
	public String toString(){
		String s = "";
		s += "String beschreibung: " + this.getBeschreibung();
		s += "Prioritaet_ prioritaet: " + this.getPrioritaet();
		s += "java.util.Date faelligAm: " + this.getFaelligAm();
		return s;
	}
}
