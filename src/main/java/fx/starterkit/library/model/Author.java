package fx.starterkit.library.model;

import javafx.beans.property.SimpleStringProperty;

public class Author {
	
	private SimpleStringProperty firstName;
	private SimpleStringProperty lastName;
	
	public Author() {
	}
	
	public Author(String fn, String ln) {
		this.firstName = new SimpleStringProperty(fn);
		this.lastName = new SimpleStringProperty(ln);
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
