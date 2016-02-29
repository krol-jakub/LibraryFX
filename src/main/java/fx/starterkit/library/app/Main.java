package fx.starterkit.library.app;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import fx.starterkit.library.dataprovider.DataProvider;
import fx.starterkit.library.model.Author;
import fx.starterkit.library.model.Book;
import fx.starterkit.library.rest.RestService;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class Main extends Application {
	
	private final DataProvider dataProvider = DataProvider.INSTANCE;
	private RestService rest = new RestService();
	
	private TableView<Book> table = new TableView<Book>();
	private final ObservableList<Author> authorsList = FXCollections.observableArrayList(
            new Author(1L, "Andrzej", "Duda"),
            new Author(2L, "Antoni", "Macierewicz"));
    private final ObservableList<Book> data =
            FXCollections.observableArrayList(
            		new Book(1L, "Narrenturm", "Andrzej Sapkowski", authorsList),
                    new Book(5L, "Mistrz i Malgorzata", "Mihail Bulhakov", authorsList),
                    new Book(3L, "Lux Perpetua", "Andrzej Sapkowski", authorsList),
                    new Book(2L, "Bozy Bojownicy", "Andrzej Sapkowski", authorsList),
                    new Book(6L, "Kolejna", "Imie Nazwisko", authorsList),
                    new Book(11L, "Bardzodlugitytulksiazkizebyprzedluzyckolumne", "A B", authorsList),
                    new Book(4L, "Lalka", "Boleslaw Prus", authorsList),
                    new Book(8L, "Ostatnia", "Autor Zapomniany", authorsList),
                    new Book(7L, "Nastepna", "Kolejny Autor", authorsList));
    private final ObservableList<Book> newData =
    		FXCollections.observableArrayList(
    				new Book(1L, "Hehe", "Hoho", authorsList),
    				new Book(2L, "Poland Stronk", "Ble", authorsList));
    final HBox hb = new HBox();
    
    Comparator<? super Book> compareByID = new Comparator<Book>() {
    	@Override
    	public int compare(Book b1, Book b2) {
    		return Math.toIntExact(b1.getId() - b2.getId());

    	}
    };
 
    public static void main(String[] args) {
        launch(args);
    }
 
    @SuppressWarnings({ "unchecked" })
	@Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Library");
        stage.setWidth(800);
        stage.setHeight(520);
        
        FXCollections.sort(data, compareByID);
 
        final Label label = new Label("Books Library");
        label.setFont(new Font("Arial", 20));
        label.setAlignment(Pos.BASELINE_LEFT);
        
        final TextField titlePrefix = new TextField();
        titlePrefix.setPromptText("Book search: Enter (part of a) title");
        titlePrefix.setPrefWidth(200);
 
        table.setEditable(true);
        table.setPrefWidth(TableView.USE_COMPUTED_SIZE);
 
        TableColumn<Book, Long> idCol = new TableColumn<Book, Long>("ID");
        idCol.setMinWidth(50);
        idCol.setMaxWidth(50);
        idCol.setCellValueFactory(new PropertyValueFactory<Book, Long>("id"));
        idCol.setOnEditCommit(
            new EventHandler<CellEditEvent<Book, Long>>() {
                @Override
                public void handle(CellEditEvent<Book, Long> t) {
                    ((Book) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setId(t.getNewValue());
                }
             }
        );
 
 
        TableColumn<Book, String> titleCol = new TableColumn<Book, String>("Title");
        titleCol.setMinWidth(150);
        titleCol.setCellValueFactory(
            new PropertyValueFactory<Book, String>("title"));
        titleCol.setCellFactory(TextFieldTableCell.forTableColumn());
        titleCol.setOnEditCommit(
            new EventHandler<CellEditEvent<Book, String>>() {
                @Override
                public void handle(CellEditEvent<Book, String> t) {
                    ((Book) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setTitle(t.getNewValue());
                }
            }
        );       
 
        table.setItems(data);
        table.getColumns().addAll(idCol, titleCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
 
        final TextField addId = new TextField();
        addId.setPromptText("ID");
        addId.setMaxWidth(idCol.getPrefWidth());
        final TextField addTitle = new TextField();
        addTitle.setMaxWidth(titleCol.getPrefWidth());
        addTitle.setPromptText("Title");
        final TextField addAuthor = new TextField();
        addAuthor.setPrefWidth(100);
        addAuthor.setPromptText("Author");
        
        final TextArea infoArea = new TextArea();
        infoArea.setPrefRowCount(3);
        infoArea.setEditable(false);
        infoArea.setPrefWidth(300);
        infoArea.setPrefHeight(400);
        infoArea.setFont(Font.font("Helvetica", 15));
 
        final Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	if(addId.getText().equals("")) {
            		long newId = new Long(data.get(data.size()-1).getId());
            		Book newBook = new Book(new Long(newId + 1), addTitle.getText(), Author.parseAuthors(addAuthor.getText()));
						//dataProvider.addBook(newBook);
            		data.add(newBook);
            		
            	}
            	else {
            		data.add(new Book(
            				Long.parseLong(addId.getText()),
            				addTitle.getText(),
            				Author.parseAuthors(addAuthor.getText())));    
					//	dataProvider.addBook(new Book(Long.parseLong(addId.getText()), addTitle.getText(), Author.parseAuthors(addAuthor.getText())));
					
            	}
                addId.clear();
                addTitle.clear();
                addAuthor.clear();
                FXCollections.sort(data, compareByID);
            }
        });
        
        final Button searchButton = new Button("Search");
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
        	private List<Book> resultList = new ArrayList<>();

			@Override
        	public void handle(ActionEvent e) {
        		data.clear();
        		//data.addAll(newData);
        		try {
					resultList = rest.sendGET(titlePrefix.getText());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		data.addAll(resultList);
        	}
		});
        
        final Button deleteButton = new Button("Delete selected");
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		Book toDelete = table.getSelectionModel().getSelectedItem();
        		data.remove(toDelete);
        	}
		});
        
        final Button someButton = new Button("Show book info");
        someButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		infoArea.clear();
        		if(table.getSelectionModel().getSelectedCells().isEmpty()) {
        			infoArea.appendText("No book selected!");
        		}
        		else {
        			infoArea.appendText("Book info:\n\nID: " + table.getSelectionModel().getSelectedItem().getId()
        					+ "\nTitle: " + table.getSelectionModel().getSelectedItem().getTitle()
        					+ "\nAuthor: " + table.getSelectionModel().getSelectedItem().getAuthor());
        			infoArea.appendText("\n\nList of Authors:");
        			for(Iterator<Author> it = table.getSelectionModel().getSelectedItem().getAuthors().iterator() ; it.hasNext() ; ) {
        				Author curr = it.next();
        				infoArea.appendText("\n" + curr.getFirstName() + " " + curr.getLastName());
        			}
        		}
        		table.getSelectionModel().clearSelection();
        	}
		});
 
        hb.getChildren().addAll(addId, addTitle, addAuthor, addButton, deleteButton);
        hb.setSpacing(3);
        
        final VBox infoVbox = new VBox();
        infoVbox.setSpacing(3);
        infoVbox.setPadding(new Insets(10, 0, 0, 10));
        infoVbox.getChildren().addAll(someButton, infoArea);
        
        final HBox header = new HBox();
        header.setSpacing(15);
        header.getChildren().addAll(label, titlePrefix, searchButton);
 
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(header, table, hb);
        
        final HBox mainHbox = new HBox();
        mainHbox.setSpacing(5);
        mainHbox.getChildren().addAll(vbox, infoVbox);
 
        ((Group) scene.getRoot()).getChildren().addAll(mainHbox);
 
        stage.setScene(scene);
        stage.show();
    }
    
	/*
	private static final String BUNDLEPATH = "fx/starterkit/library/bundle/bundle";
	private static final String FXMLPATH = "/fx/starterkit/library/view/Library.fxml";
	private static final String CSSPATH = "/fx/starterkit/library/css/application.css";
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource(FXMLPATH), ResourceBundle.getBundle(BUNDLEPATH));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource(CSSPATH).toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("Book library");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}*/
	
}

