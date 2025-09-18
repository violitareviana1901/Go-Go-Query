package main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jfxtras.labs.scene.control.window.Window;
import util.Connect;

public class AddItemPage implements EventHandler<ActionEvent> {
	private Stage addItemPageStage;
	private Scene addItemPageScene;
	private Connect connect = Connect.getInstance();
	
	BorderPane bp1;
	Scene mainScene;
	
	//MENUBAR 
	MenuBar menubar = new MenuBar();
	Menu menu = new Menu("Menu");
	MenuItem addItem;
	MenuItem mngQueue;
	MenuItem logout;
	Window window;
	
	BorderPane bp;
	Scene scene;
	Label lbadd, itemname, desc, category, price, quantity;
	TextField tfname, tfcategory, tfprice;
	TextArea tfdes;
	Spinner<Integer> quantityspin;
	GridPane gp;
	Button bt;
	
	public void init() {
		bp1 = new BorderPane();
		mainScene = new Scene(bp1, 1280, 720);
		
		//MENUBAR
		menubar = new MenuBar();
		menu = new Menu("Menu");
		addItem = new MenuItem("Add Item");
		mngQueue = new MenuItem("Manage Queue");
		logout = new MenuItem("Log Out");
		
		//Add Item Page
		bp = new BorderPane();
		gp = new GridPane();
		scene = new Scene(bp, 400, 200 );
		lbadd = new Label("Add Item: ");
		itemname = new Label("Item Name: ");
		desc = new Label("Item Desc: ");
		category = new Label("Item Category: ");
		price = new Label("Item Price: ");
		quantity = new Label("Quantity: ");
		tfname = new TextField();
		tfdes = new TextArea();
		tfcategory = new TextField();
		tfprice = new TextField();
		quantityspin = new Spinner<>(1, 300, 1);
		bt = new Button("Add Item");
		window = new Window("");
	}
	
	public void tablestyle() {
		lbadd.setStyle("-fx-font-size: 30px");
		lbadd.setAlignment(Pos.BASELINE_LEFT);
		tfname.setMinHeight(40);
		tfdes.setMaxHeight(200);
		tfcategory.setMinHeight(40);
		tfprice.setMinHeight(40);
		tfname.setMaxWidth(250);
		tfdes.setMaxWidth(250);
		tfcategory.setMaxWidth(250);
		tfprice.setMaxWidth(250);
		bt.setMinHeight(40);
		bt.setMaxWidth(100);
		quantityspin.setMinHeight(40);
		quantityspin.setMaxWidth(250);
	}
	
	public void addComponent() {
		bp1.setTop(menubar);
		menubar.getMenus().add(menu);
		menu.getItems().addAll(addItem, mngQueue, logout);
		window.getContentPane().getChildren().add(bp);
		bp1.setCenter(window);
		
		//ADD ITEM PAGE
		gp.setAlignment(Pos.CENTER);
		gp.add(lbadd, 0, 0);
		gp.add(itemname, 0, 1);
		gp.add(desc, 0, 2);
		gp.add(category, 0, 3);
		gp.add(price, 0, 4);
		gp.add(quantity, 0, 5);
		gp.add(tfname, 1, 1);
		gp.add(tfdes, 1, 2);
		gp.add(tfcategory, 1, 3);
		gp.add(tfprice, 1, 4);
		gp.add(quantityspin, 1, 5);
		gp.add(bt, 1, 6);
		bt.setStyle("-fx-background-color: green; -fx-text-fill: white;");
		bp.setLeft(gp);
	}

	public void styling() {
		bp.setMargin(gp, new Insets(5, 10, 10, 25));
		gp.setHgap(10);
		gp.setVgap(10);
		bp.setStyle("-fx-background-color: #6D96AF; -fx-padding: 20; -fx-border-radius: 10; -fx-border-color: transparent;");
		bp1.setMargin(window, new Insets(50, 50, 100, 50));
	}
	
	public void setMouseEvent() {
		bt.setOnAction(this:: handle);
		addItem.setOnAction(this::handleButtonActions);
		mngQueue.setOnAction(this::handleButtonActions);
		logout.setOnAction(this::handleButtonActions);
		
	}
	private void handleButtonActions(ActionEvent event) {
		if (event.getSource() == addItem) {
			
		}else if (event.getSource() == mngQueue) {
			ManagerQueuePage managerqueue = new ManagerQueuePage(addItemPageStage); 
			Scene queueManagerScene = managerqueue.getManagerQueuePageScene();
			addItemPageStage.setScene(queueManagerScene);
		}else if (event.getSource() == logout) {
			LoginPage loginPage = new LoginPage(addItemPageStage); 
			Scene loginPagescene = loginPage.getLoginPageScene();
			addItemPageStage.setScene(loginPagescene);
		}
	}
	
	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		boolean isValid = true;
		double mainWindowX = addItemPageStage.getX();
	    double mainWindowY = addItemPageStage.getY();
	    
	    double gpWidth = gp.getWidth();
	    double gpHeight = gp.getHeight();

		String iNameInput = tfname.getText();
		String iDescInput = tfdes.getText();
		String iCatInput = tfcategory.getText();
		double priceValue = 0;
		int iQuantity = Integer.valueOf(quantityspin.getValue());
		
		if (tfname.getText().isEmpty() || tfdes.getText().isEmpty() || tfcategory.getText().isEmpty()
				|| tfprice.getText().isEmpty()) {
			isValid = false;
			Alert message = new Alert(AlertType.ERROR);
			message.setHeaderText("Insert error!");
			message.setContentText("All fields must be filled out.");
			setAlertPosition(message, mainWindowX, mainWindowY, gpWidth, gpHeight);
			Optional<ButtonType> error = message.showAndWait();
			if (error.isPresent() && error.get() == ButtonType.OK) {
				event.consume();
				return;
			}
		}
		
		if (!(tfname.getText().length() > 5 && tfname.getText().length() < 70)) {
			isValid = false;
			Alert message = new Alert(AlertType.ERROR);
			message.setHeaderText("Insert error!");
			message.setContentText("Item name must be between 5 and 70 characters!");
			setAlertPosition(message, mainWindowX, mainWindowY, gpWidth, gpHeight);
			Optional<ButtonType> error = message.showAndWait();
			if (error.isPresent() && error.get() == ButtonType.OK) {
				event.consume();
				return;
			}
		}

		if (!(tfdes.getText().length() > 10 && tfdes.getText().length() < 255)) {
			isValid = false;
			Alert message = new Alert(AlertType.ERROR);
			message.setHeaderText("Insert error!");
			message.setContentText("Item description be between 10 and 255 characters!!");
			setAlertPosition(message, mainWindowX, mainWindowY, gpWidth, gpHeight);
			Optional<ButtonType> error = message.showAndWait();
			if (error.isPresent() && error.get() == ButtonType.OK) {
				event.consume();
				return;
			}
		}
	
		try {
			priceValue = Double.valueOf(tfprice.getText());
		    // Fix the condition to use || (OR) instead of && (AND)
		    if (priceValue < 0.50 || priceValue > 900000) {
		        isValid = false;
		        Alert message = new Alert(Alert.AlertType.ERROR);
				message.setHeaderText("Insert error!");
		        message.setContentText("Item price must be between $0.50 and $900,000.");
		        setAlertPosition(message, mainWindowX, mainWindowY, gpWidth, gpHeight);
		        Optional<ButtonType> error = message.showAndWait();
		        if (error.isPresent() && error.isPresent() && error.get() == ButtonType.OK) {
		            event.consume(); // Prevent further processing
		            return;
		        }
		    }
		} catch (NumberFormatException e) {
		    isValid = false;
		    Alert message = new Alert(Alert.AlertType.ERROR);
			message.setHeaderText("Insert error!");
		    message.setContentText("Item price must be a valid number.");
		    setAlertPosition(message, mainWindowX, mainWindowY, gpWidth, gpHeight);
		    Optional<ButtonType> error = message.showAndWait();
		    if (error.isPresent() && error.get() == ButtonType.OK) {
		    	event.consume(); // Prevent further processing
		        return;
		    }
		}
		
		
		if (isValid) {
            Alert message = new Alert(AlertType.INFORMATION);
            message.setTitle("Information");
            message.setHeaderText("Insert Success!");
            message.setContentText("Item added to product catalog");
            setAlertPosition(message, mainWindowX, mainWindowY, gpWidth, gpHeight);
		    Optional<ButtonType> error = message.showAndWait();
		    if (error.isPresent() && error.get() == ButtonType.OK) {
		    	System.out.println("Data berhasil ditambah");
		    	insertData(iNameInput, priceValue, iDescInput, iCatInput, iQuantity);
		    	
		    	tfname.clear();
		    	tfdes.clear();
		    	tfcategory.clear();
		    	tfprice.clear();
		    	quantityspin.getValueFactory().setValue(1);
		    	
		    	event.consume(); // Prevent further processing
		        return;
		    }
        }
	}

	private void setAlertPosition(Alert message, double mainWindowX, double mainWindowY, double gpWidth, double gpHeight) {
	    Stage alertStage = (Stage) message.getDialogPane().getScene().getWindow();
	    alertStage.setX(mainWindowX + addItemPageStage.getWidth() - gpWidth - 200);  
	    alertStage.setY(mainWindowY + gpHeight - 250); 
	}
	
	private void insertData(String iNameInput, double priceValue, String iDescInput, String iCatInput, int iQuantity) {
		String query = "INSERT INTO msitem (ItemName, ItemPrice, ItemDesc, ItemCategory, ItemStock) VALUES (?, ?, ?, ?, ?)";
	    
	    try (PreparedStatement ps = connect.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
	    	ps.setString(1, iNameInput);
	        ps.setDouble(2, priceValue);
	        ps.setString(3, iDescInput);
	        ps.setString(4, iCatInput);
	        ps.setInt(5, iQuantity);
	        
	        ps.executeUpdate();
	        
	        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                int itemID = generatedKeys.getInt(1);
	                System.out.println("Generated ItemID: " + itemID);
	                
	            }
	        } catch (Exception e) {
				// TODO: handle exception
	        	e.printStackTrace();
			}
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
	}
	
	public AddItemPage(Stage addItemPageStage) {
		super();
		this.addItemPageStage = addItemPageStage;
		
		init();
		tablestyle();
		addComponent();
		styling();
		setMouseEvent();
		addItemPageScene = mainScene;
	}
	
	public Stage getAddItemPageStage() {
		return addItemPageStage;
	}
	
	public void setAddItemPageStage(Stage addItemPageStage) {
		this.addItemPageStage = addItemPageStage;
	}
	
	public Scene getAddItemPageScene() {
		return addItemPageScene;
	}
	
	public void setAddItemPageScene(Scene addItemPageScene) {
		this.addItemPageScene = addItemPageScene;
	}
}
