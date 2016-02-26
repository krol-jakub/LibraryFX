package fx.starterkit.library.mapper;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import fx.starterkit.library.model.BookVO;

public class Mapper {

	private Gson gson = new Gson();
	
	public ArrayList<BookVO> json2BookVOList(String json) {
		ArrayList<BookVO> bookToList = new Gson().fromJson(json, new TypeToken<ArrayList<BookVO>>() {
		}.getType());
		return bookToList;
	}
	
	public BookVO json2BookVO(String json){
		BookVO book = gson.fromJson(json, BookVO.class);
		return book;
	}

}