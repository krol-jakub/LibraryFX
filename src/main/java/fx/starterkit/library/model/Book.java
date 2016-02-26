package fx.starterkit.library.model;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Book {
	
	private final SimpleLongProperty id;
	private final SimpleStringProperty title;
	private final SimpleStringProperty author;
	
	public Book(Long id, String title, String author) {
		this.id = new SimpleLongProperty(id);
		this.title = new SimpleStringProperty(title);
		this.author = new SimpleStringProperty(author);
	}

	public Long getId() {
		return id.get();
	}
	
	public void setId(Long idd) {
		id.set(idd);
	}

	public String getTitle() {
		return title.get();
	}
	
	public void setTitle(String titlee) {
		title.set(titlee);
	}

	public String getAuthor() {
		return author.get();
	}
	
	public void setAuthor(String authorr) {
		author.set(authorr);
	}
	
	@Override
	public int hashCode() {
		return title.hashCode() + author.hashCode() + id.intValue();
	}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Book other = (Book) obj;
        if (author == null) {
            if (other.author != null)
                return false;
        } else if (!author.equals(other.author))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }
	
	

}
