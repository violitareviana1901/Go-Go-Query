package main;

import java.sql.PreparedStatement;
import java.util.Vector;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import jfxtras.labs.scene.control.window.Window;
import util.Connect;

public class ManagerQueuePage implements EventHandler<ActionEvent> {
	private Stage managerQueuePageStage;
	private Scene managerQueuePageScene;
	private Connect connect = Connect.getInstance();
	
	BorderPane bp1;
	Scene scene1;
	
	//MENUBAR
	MenuBar menubar = new MenuBar();
	Menu menu = new Menu("Menu");
	MenuItem addItem;
	MenuItem mngQueue;
	MenuItem logout;
	
	//AddItemPage
	Scene scene;
	BorderPane bp;
	Label title, tid, cid, cemail, date, total, status;
	Button bt;
	TableView<managerDetail> tb;
	Window window;

	public void init() {
		bp1 = new BorderPane();
		scene1 = new Scene(bp1, 1280, 720);
		
		//MENUBAR
		menubar = new MenuBar();
		menu = new Menu("Menu");
		addItem = new MenuItem("Add Item");
		mngQueue = new MenuItem("Manage Queue");
		logout = new MenuItem("Log Out");

		//Add Item Page
		bp = new BorderPane();
		title = new Label("Queue Manager");
		tid  = new Label("Transaction ID");
		cid  = new Label("Customer ID");
		cemail  = new Label("Customer Email");
		date  = new Label("Date");
		total = new Label("Total");
		status  = new Label("Status");
		bt = new Button("Send Package");
		window = new Window("");

	}
	
	public void addComponent() {
		scene = new Scene(bp, 800, 700);	
		bp1.setTop(menubar);
		menubar.getMenus().add(menu);
		menu.getItems().addAll(addItem, mngQueue, logout);
		window.getContentPane().getChildren().add(bp);
		bp1.setCenter(window);
		
		title.setFont(Font.font("Inter",FontWeight.LIGHT, 26));
		bt.setStyle("-fx-background-color: #5CB85C; -fx-text-fill: white;");
		bt.setMinWidth(150);
		bt.setMinHeight(40);

		//Queue Manager Page
		bp.setTop(title);
		tb = new TableView<>();
	
		TableColumn<managerDetail, Integer> trid = new TableColumn<managerDetail, Integer>("Transaction ID");
		trid.setCellValueFactory(new PropertyValueFactory<>("transactionid"));
		TableColumn<managerDetail, Integer> cuid = new TableColumn<managerDetail, Integer>("Customer ID");
		cuid.setCellValueFactory(new PropertyValueFactory<>("customerid"));
		TableColumn<managerDetail, String> cuemail = new TableColumn<managerDetail, String>("Customer Email");
		cuemail.setCellValueFactory(new PropertyValueFactory<>("customeremail"));
		TableColumn<managerDetail, String> date1 = new TableColumn<managerDetail, String>("Date");
		date1.setCellValueFactory(new PropertyValueFactory<>("date"));
		TableColumn<managerDetail, Double> total = new TableColumn<managerDetail, Double>("Total");
		total.setCellValueFactory(new PropertyValueFactory<>("total"));
		TableColumn<managerDetail, String> status = new TableColumn<managerDetail, String>("Status");
		status.setCellValueFactory(new PropertyValueFactory<>("status"));
		
		trid.setPrefWidth(125); 
		cuid.setPrefWidth(125);
		cuemail.setPrefWidth(380); 
		date1.setPrefWidth(150);
		total.setPrefWidth(200); 
		status.setPrefWidth(150);
		
		tb.setStyle("-fx-font-size: 14");
		tb.getColumns().addAll(trid, cuid, cuemail, date1, total, status);
		tb.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		
		ScrollPane scrollPane = new ScrollPane(tb);
	    scrollPane.setFitToWidth(true); 
	    scrollPane.setFitToHeight(true);
		
		VBox vbox = new VBox(10, title, tb, bt); // Set spacing between elements
		vbox.setPadding(new Insets(20)); 
		bp.setCenter(vbox);
		
		Vector<managerDetail> transactionDetails = getTransactionDetails();
		tb.getItems().addAll(getTransactionDetails());
		bp.setBottom(bt);
	}
	
	public void setMouseEvent() {
		bt.setOnAction(this::handle);
		addItem.setOnAction(this::handleButtonActions);
		mngQueue.setOnAction(this::handleButtonActions);
		logout.setOnAction(this::handleButtonActions);
	}
	
	public void style() {
		bp.setMargin(tb, new Insets(20, 20, 20, 20)); 
		bp.setMargin(title, new Insets(20, 20, 0, 20)); 
		bp.setMargin(bt, new Insets(10, 20, 20, 20)); 
		bp.setStyle("-fx-background-color: #6D96AF ;  -fx-border-radius: 10; -fx-border-color: transparent;\r\n");
		bp1.setMargin(window, new Insets(50, 40, 60, 40));
	}

	private void handleButtonActions(ActionEvent event) {
		if (event.getSource() == addItem) {
			AddItemPage addItemPage = new AddItemPage(managerQueuePageStage); 
			Scene addItemPageScene = addItemPage.getAddItemPageScene();
			managerQueuePageStage.setScene(addItemPageScene);
		}else if (event.getSource() == logout) {
			LoginPage loginPage = new LoginPage(managerQueuePageStage); 
			Scene loginPagescene = loginPage.getLoginPageScene();
			managerQueuePageStage.setScene(loginPagescene);
		}
	}
	
	@Override
	public void handle(ActionEvent event) {
		managerDetail selectedTransaction = tb.getSelectionModel().getSelectedItem();
        
		if (selectedTransaction == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Reference Error");
            alert.setHeaderText("Reference error due to no transaction selected");
            alert.setContentText("Please select a transaction");
            alert.showAndWait();
        } else {
            // Update the status if a transaction is selected
        	String updateQuery = "UPDATE transactionheader SET Status = 'Sent' WHERE TransactionID = ?";
        	try (PreparedStatement ps = connect.getConnection().prepareStatement(updateQuery)){
                 ps.setInt(1, selectedTransaction.getTransactionid());
                 ps.executeUpdate();
                 
                 // Refresh the table view after updating the database
                 tb.getItems().clear(); // Clear existing items
                 tb.getItems().addAll(getTransactionDetails()); // Reload filtered transactions
                 
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
        }
	}
	
	public Vector<managerDetail> getTransactionDetails() {
	    Vector<managerDetail> transactionDetails = new Vector<>();
	    String query = "SELECT th.TransactionID, th.UserID, mu.UserEmail, th.DateCreated, th.Status AS Status, " +
                		"SUM(td.Quantity * mi.ItemPrice) AS Total " +
                		"FROM transactionheader th " +
                		"JOIN msuser mu ON th.UserID = mu.UserID " +
                		"JOIN transactiondetail td ON th.TransactionID = td.TransactionID " +
                		"JOIN msitem mi ON td.ItemID = mi.ItemID " +
                		"WHERE th.Status IN ('In Queue', 'Sent') " +
                		"GROUP BY th.TransactionID, th.UserID, mu.UserEmail, th.DateCreated, th.Status " +
                        "ORDER BY CASE WHEN th.Status = 'Sent' THEN 0 ELSE 1 END, th.DateCreated DESC";
	    try {
	    	connect.rs = connect.execQuery(query);
	    	while (connect.rs.next()) {
	    		int transactionId = connect.rs.getInt("TransactionID");
	    		int userId = connect.rs.getInt("UserID");
	    		String userEmail = connect.rs.getString("UserEmail");
	    		String dateCreated = connect.rs.getString("DateCreated");
	    		String status = connect.rs.getString("Status");
	    		double total = connect.rs.getDouble("Total");
	    		
	    		transactionDetails.add(new managerDetail(transactionId, userId, userEmail, dateCreated, total, status)); // Assuming total is set to 0.0
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return transactionDetails;
	}

	public ManagerQueuePage(Stage managerQueuePageStage) {
		super();
		this.managerQueuePageStage = managerQueuePageStage;
		
		init();
		addComponent();
		setMouseEvent();
		style();
		managerQueuePageScene = scene1;
	}
	
	public Scene getManagerQueuePageScene() {
		return managerQueuePageScene;
	}
	
	public void setManagerQueuePageScene(Scene managerQueuePageScene) {
		this.managerQueuePageScene = managerQueuePageScene;
	}

}
