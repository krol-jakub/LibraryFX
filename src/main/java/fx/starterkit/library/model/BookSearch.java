package fx.starterkit.library.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class BookSearch {
	
	private final LongProperty id = new SimpleLongProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final ListProperty<Author> authors = new SimpleListProperty<>();
    private final ObjectProperty<Book> selected = new SimpleObjectProperty<>();
    private final ListProperty<Book> result = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));

    public final Long getId() {
        return id.get();
    }

    public final void setId(Long value) {
        id.set(value);
    }

    public LongProperty idProperty() {
        return id;
    }

    public final String getTitle() {
        return title.get();
    }

    public final void setTitle(String value) {
        title.set(value);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public final List<Author> getAuthors() {
        return authors.get();
    }

    public final void setAuthors(List<Author> value) {
        authors.setAll(value);
    }

    public ListProperty<Author> authorListProperty() {
        return authors;
    }

    public final List<Book> getResult() {
        return result.get();
    }

    public final void setResult(List<Book> value) {
        result.setAll(value);
    }

    public ListProperty<Book> resultProperty() {
        return result;
    }

    public final void setSelected(Book value) {
        selected.set(value);
    }

    public final Book getSelected() {
        return selected.get();
    }

    public ObjectProperty<Book> selectedProperty() {
        return selected;
    }

}
