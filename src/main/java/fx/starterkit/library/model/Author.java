package fx.starterkit.library.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Author {
	
	private SimpleLongProperty id;
	private SimpleStringProperty firstName;
	private SimpleStringProperty lastName;
	
	public Author() {
	}
	
	public Author(String fn, String ln) {
		this.id = new SimpleLongProperty(Long.MAX_VALUE);
		this.firstName = new SimpleStringProperty(fn);
		this.lastName = new SimpleStringProperty(ln);
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

	public static List<Author> parseAuthorsVO(List<AuthorVO> authors) {
		List<Author> result = new ArrayList<>();
		result.clear();
		Author tmpAuthor = null;
		AuthorVO tmpVO = null;
		for(Iterator<AuthorVO> it = authors.iterator() ; it.hasNext() ; ) {
			tmpVO = it.next();
			tmpAuthor = new Author(tmpVO.getId(), tmpVO.getFirstName(), tmpVO.getLastName());
			result.add(tmpAuthor);
		}
		return result;
	}
	
	public static List<Author> parseAuthors(String authors) {
		String[] authorsList = authors.split("[,;]");
		List <Author> result = new ArrayList<>();
		result.clear();
		Author tmpAuth = null;
		for(int i = 0 ; i < authorsList.length ; ++i) {
			String[] authorData = authorsList[i].trim().split("[\\s]");
			tmpAuth = new Author(authorData[0], authorData[1]);
			result.add(tmpAuth);
		}
		return result;
	}
	

}
