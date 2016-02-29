package fx.starterkit.library.dataprovider;

import java.util.Collection;

import fx.starterkit.library.dataprovider.impl.DataProviderImpl;
import fx.starterkit.library.model.Book;

public interface DataProvider {
	
	DataProvider INSTANCE = new DataProviderImpl();

	Collection<Book> findBooks(String titlePrefix);
    
    void deleteBook(Long id);
    
    Book addBook(Book book);

}
