package fx.starterkit.library.app;

import com.sun.prism.paint.Color;

import fx.starterkit.library.model.Book;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class Main extends Application {
	
	private TableView<Book> table = new TableView<Book>();
    private final ObservableList<Book> data =
            FXCollections.observableArrayList(
            		new Book(1L, "Narrenturm", "Andrzej Sapkowski"),
                    new Book(2L, "Bozy Bojownicy", "Andrzej Sapkowski"),
                    new Book(3L, "Lux Perpetua", "Andrzej Sapkowski"),
                    new Book(4L, "Lalka", "Boleslaw Prus"),
                    new Book(5L, "Mistrz i Malgorzata", "Mihail Bulhakov"),
                    new Book(6L, "Kolejna", "Imie Nazwisko"),
                    new Book(7L, "Nastepna", "Kolejny Autor"),
                    new Book(8L, "Ostatnia", "Autor Zapomniany"));
    private final ObservableList<Book> newData =
    		FXCollections.observableArrayList(
    				new Book(1L, "Hehe", "Hoho"));
    final HBox hb = new HBox();
 
    public static void main(String[] args) {
        launch(args);
    }
 
    @SuppressWarnings({ "unchecked" })
	@Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Library");
        stage.setWidth(800);
        stage.setHeight(550);
 
        final Label label = new Label("Books Library");
        label.setFont(new Font("Arial", 20));
 
        table.setEditable(true);
 
        TableColumn<Book, Long> idCol = new TableColumn<Book, Long>("ID");
        idCol.setMinWidth(50);
        idCol.setCellValueFactory(new PropertyValueFactory<Book, Long>("id"));
        //idCol.setCellFactory(TextFieldTableCell.forTableColumn());
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
 
        TableColumn<Book, String> authorCol = new TableColumn<Book, String>("Author");
        authorCol.setMinWidth(200);
        authorCol.setCellValueFactory(
            new PropertyValueFactory<Book, String>("author"));
        authorCol.setCellFactory(TextFieldTableCell.forTableColumn());
        authorCol.setOnEditCommit(
            new EventHandler<CellEditEvent<Book, String>>() {
                @Override
                public void handle(CellEditEvent<Book, String> t) {
                    ((Book) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setAuthor(t.getNewValue());
                }
            }
        );
 
        table.setItems(data);
        table.getColumns().addAll(idCol, titleCol, authorCol);
 
        final TextField addId = new TextField();
        addId.setPromptText("ID");
        addId.setMaxWidth(idCol.getPrefWidth());
        final TextField addTitle = new TextField();
        addTitle.setMaxWidth(titleCol.getPrefWidth());
        addTitle.setPromptText("Title");
        final TextField addAuthor = new TextField();
        addAuthor.setMaxWidth(authorCol.getPrefWidth());
        addAuthor.setPromptText("Author");
        
        final TextArea infoArea = new TextArea();
        infoArea.setPrefRowCount(3);
        infoArea.setEditable(false);
        infoArea.setPrefWidth(300);
        infoArea.setPrefHeight(400);
        infoArea.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
 
        final Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                data.add(new Book(
                		Long.parseLong(addId.getText()),
                        addTitle.getText(),
                        addAuthor.getText()));
                addId.clear();
                addTitle.clear();
                addAuthor.clear();
            }
        });
        
        final Button searchButton = new Button("Search");
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		data.clear();
        		data.addAll(newData);
        	}
		});
        
        final Button deleteButton = new Button("Delete");
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
        		}
        		table.getSelectionModel().clearSelection();
        	}
		});
 
        hb.getChildren().addAll(addId, addTitle, addAuthor, addButton, searchButton, deleteButton);
        hb.setSpacing(3);
        
        final VBox infoVbox = new VBox();
        infoVbox.setSpacing(3);
        infoVbox.setPadding(new Insets(10, 0, 0, 10));
        infoVbox.getChildren().addAll(someButton, infoArea);
 
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb);
        
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

