package fx.starterkit.library.mapper;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import fx.starterkit.library.model.Book;

public class Mapper {

	private Gson gson = new Gson();
	
	public ArrayList<Book> json2BookList(String json) {
		ArrayList<Book> bookList = new Gson().fromJson(json, new TypeToken<ArrayList<Book>>() {
		}.getType());
		return bookList;
	}
	
	public Book json2Book(String json){
		Book book = gson.fromJson(json, Book.class);
		return book;
	}

}