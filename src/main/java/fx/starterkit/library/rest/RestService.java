package fx.starterkit.library.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import fx.starterkit.library.mapper.Mapper;
import fx.starterkit.library.model.Book;

public class RestService {
	
	private Mapper mapper = new Mapper();

	public Book sendPOST(String jsonString) throws ClientProtocolException, IOException {
		String url = "http://localhost:9721/workshop/services/books/book";

		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		post.setHeader("Content-Type", "Application/JSON");
		post.setEntity(new StringEntity(jsonString));
		HttpResponse response = client.execute(post);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = bufferedReader.readLine()) != null) {
			result.append(line);
		}
		Book book = mapper.json2Book(result.toString());
		System.out.println(result.toString());
		return book;
	}

	public ArrayList<Book> sendGET(String titlePrefix) throws IOException {
		String url = "http://localhost:9721/workshop/services/books/books-by-title?titlePrefix=" + titlePrefix;

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);

		request.setHeader("Content-Type", "Application/JSON");
		HttpResponse response = client.execute(request);

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = bufferedReader.readLine()) != null) {
			result.append(line);
		}

		ArrayList<Book> bookToList = mapper.json2BookList(result.toString());
		System.out.println(result.toString());
		return bookToList;
	}
	
	public void sendDELETE(Long id) throws IOException {
		String url = "http://localhost:9721/workshop/services/books/book/" + id;

		HttpClient client = HttpClientBuilder.create().build();
		HttpDelete request = new HttpDelete(url);

		request.setHeader("Content-Type", "Application/JSON");
		HttpResponse response = client.execute(request);

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = bufferedReader.readLine()) != null) {
			result.append(line);
		}
	}

}

