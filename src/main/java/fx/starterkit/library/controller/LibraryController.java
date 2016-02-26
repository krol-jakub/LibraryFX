package fx.starterkit.library.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;

import fx.starterkit.library.dataprovider.DataProvider;
import fx.starterkit.library.model.AuthorVO;
import fx.starterkit.library.model.BookSearch;
import fx.starterkit.library.model.BookVO;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class LibraryController {
	
	private final DataProvider dataProvider = DataProvider.INSTANCE;
    private final BookSearch model = new BookSearch();
	
	@FXML
	private ResourceBundle resources;

	@FXML
	private TextField titlePrefix;

	@FXML
	private Button searchButton;
	
	@FXML
	private Button deleteButton;
	
	@FXML
	private Button addButton;
	
	@FXML
	private TableView<BookVO> bookTable;

    @FXML
    private TableColumn<BookVO, Number> idColumn;

    @FXML
    private TableColumn<BookVO, String> titleColumn;

    @FXML
    private TableColumn<BookVO, String> authorsColumn;

    @FXML
    private AnchorPane mainPane;
    
    @FXML
    private GridPane gridPane;
    
    @FXML
    private BorderPane borderPane;
    
    @FXML
    private void initialize() {
        initializeResultTable();
        titlePrefix.textProperty().bindBidirectional(model.titleProperty());
        bookTable.itemsProperty().bind(model.resultProperty());
        deleteButton.disableProperty().bind(bookTable.getSelectionModel().selectedItemProperty().isNull());
    }

    private void initializeResultTable() {
        idColumn.setCellValueFactory(cellData -> new ReadOnlyLongWrapper(cellData.getValue().getId()));

        titleColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTitle()));

        authorsColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<BookVO, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<BookVO, String> param) {
                        Function<AuthorVO, String> mapAuthor = a -> "" + a.getFirstName() + " " + a.getLastName();
                        String authors = param.getValue().getAuthors().stream().map(mapAuthor)
                                .collect(Collectors.joining(", "));
                        return new ReadOnlyStringWrapper(authors);
                    }
                });

        bookTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BookVO>() {
            @Override
            public void changed(ObservableValue<? extends BookVO> observable, BookVO oldValue, BookVO newValue) {
                model.setSelected(newValue);
            }
        });
        
        bookTable.setPlaceholder(new Label(resources.getString("table.emptyText")));
    }

    @FXML
    private void searchButtonAction(ActionEvent event) {
        searchBooks();
    }

    private void searchBooks() {

        Task<Collection<BookVO>> searchTask = new Task<Collection<BookVO>>() {
            @Override
            protected Collection<BookVO> call() throws Exception {
                return dataProvider.findBooks(model.getTitle());
            }

            @Override
            protected void succeeded() {
                model.setResult(new ArrayList<BookVO>(getValue()));
                bookTable.getSortOrder().clear();
            }
        };
        new Thread(searchTask).start();
    }

    @FXML
    public void deleteSelectedAction() {
        if (ButtonType.OK.equals(confirmDeletionDialog()))
            deleteSelectedBook();
    }

    private ButtonType confirmDeletionDialog() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(resources.getString("delete.title"));
        alert.setHeaderText(resources.getString("delete.header") + model.getSelected().getTitle() + "\"");
        alert.setContentText(resources.getString("delete.content"));
        return alert.showAndWait().get();
    }

    private void deleteSelectedBook() {
        Task<Exception> deleteBookTask = new Task<Exception>() {
            @Override
            protected Exception call() {
                dataProvider.deleteBook(model.getSelected().getId());
                return null;
            }

            @Override
            protected void failed() {
                super.failed();
            }

            @Override
            protected void succeeded() {
                if (getValue() == null) {
                    searchBooks();
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Connection Error");
                    alert.setHeaderText("Failure to connect with server.");
                    alert.setContentText("Please check if server works.");
                    alert.showAndWait();
                }
            }
        };
        new Thread(deleteBookTask).start();
    }

    /*@FXML
    public void addButtonAction() throws IOException {
        String fxmlFile = "/fxml/bookAdd.fxml";
        String bundlePath = "bundle/bundle";
        
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile), ResourceBundle.getBundle(bundlePath));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/styles.css");
        Stage stage = new Stage();
        stage.initOwner(mainPane.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Adding a book");
        stage.setScene(scene);
        stage.showAndWait();
        searchBooks();
    }*/
	
	
	
	
}

