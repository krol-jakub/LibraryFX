package fx.starterkit.library.model;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Author {
	
	private SimpleLongProperty id;
	private SimpleStringProperty firstName;
	private SimpleStringProperty lastName;
	
	public Author() {
	}
	
	public Author(Long id, String fn, String ln) {
		this.id = new SimpleLongProperty(id);
		this.firstName = new SimpleStringProperty(fn);
		this.lastName = new SimpleStringProperty(ln);
	}

	public Long getId() {
		return id.get();
	}

	public void setId(Long idd) {
		id.set(idd);
	}

	public String getFirstName() {
		return firstName.get();
	}

	public void setFirstName(String firstNamee) {
		firstName.set(firstNamee);
	}

	public String getLastName() {
		return lastName.get();
	}

	public void setLastName(String lastNamee) {
		lastName.set(lastNamee);
	}

}
