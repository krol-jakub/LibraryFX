package fx.starterkit.library.dataprovider;

import java.util.Collection;

import fx.starterkit.library.dataprovider.impl.DataProviderImpl;
import fx.starterkit.library.model.BookVO;

public interface DataProvider {
	
	DataProvider INSTANCE = new DataProviderImpl();

	Collection<BookVO> findBooks(String titlePrefix);
    
    void deleteBook(Long id);
    
    BookVO addBook(BookVO book);

}
