package fx.starterkit.library.mapper;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import fx.starterkit.library.model.Book;
import fx.starterkit.library.model.BookVO;

public class Mapper {

	private Gson gson = new GsonBuilder().create();
	
	public ArrayList<BookVO> json2BookList(String json) {
		ArrayList<BookVO> bookList = new Gson().fromJson(json, new TypeToken<ArrayList<BookVO>>(){}.getType());
		return bookList;
	}
	
	public Book json2Book(String json){
		Book book = gson.fromJson(json, Book.class);
		return book;
	}

}