package fx.starterkit.library.dataprovider.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.http.HttpResponse;
import org.codehaus.jackson.map.ObjectMapper;

import fx.starterkit.library.dataprovider.DataProvider;
import fx.starterkit.library.model.Book;
import net.sf.corn.httpclient.HttpClient.HTTP_METHOD;
import net.sf.corn.httpclient.HttpForm;

public class DataProviderImpl implements DataProvider {
	
	private static String requestPath = "http://localhost:9721/workshop/services/books";
    private ObjectMapper objectMapper = new ObjectMapper();
    
	@SuppressWarnings("null")
	public Collection<Book> findBooks(String titlePrefix) {
		HttpForm client = null;
		Collection<Book> books = new ArrayList<Book>();
        client.putFieldValue("titlePrefix", titlePrefix);
        HttpResponse response = null;
        try {
            client = new HttpForm(new URI(requestPath + "/books-by-title"));
            response = (HttpResponse) client.doGet();
            books = Arrays.asList(objectMapper.readValue(((net.sf.corn.httpclient.HttpResponse) response).getData(), Book[].class));
        } catch (URISyntaxException e2) {
            e2.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return books;
	}
	
	public void deleteBook(Long id) {
		HttpForm client = null;
        try {
            client = new HttpForm(new URI(requestPath + "/book/" + id));
            client.sendData(HTTP_METHOD.DELETE);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
	@SuppressWarnings("null")
	public Book addBook(Book bookVO) {
		HttpForm client = null;
		client.setContentType("Application/JSON");
        HttpResponse response = null;
        String book = null;
        Book responseBook = null;
        try {
            client = new HttpForm(new URI(requestPath + "/book/"));
            book = objectMapper.writeValueAsString(bookVO);
            response = (HttpResponse) client.sendData(HTTP_METHOD.POST, book);
            responseBook = objectMapper.readValue(((net.sf.corn.httpclient.HttpResponse) response).getData(), Book.class);
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseBook;
	}

}
