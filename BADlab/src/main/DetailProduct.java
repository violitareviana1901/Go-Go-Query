package main;

import java.sql.PreparedStatement;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import util.Connect;

public class DetailProduct {
	private Stage detailProductPageStage;
	private Scene detailProductPageScene;
	private Connect connect = Connect.getInstance();
	private int itemId; 
	private int userId;
	
	BorderPane border;
	Scene scene;
	
	//SEARCHBAR
	ImageView view;
	Image gambar;
	TextField searchBar;
	Button searchBtn;
	Button mycartBtn;
	Button logoutBtn;
	HBox hbox;
	VBox vbox;
	StackPane stack;
	Line line;
	Rectangle kotakNav;
	StackPane stackNav;
	HBox hNav;
	
	//MAIN CONTENT
	HBox mainContent;
	
	//FOTO SECTION
	Rectangle fotoDetail;
	VBox fotoSection;
	
	//PRODUCT DESC SECTION
	VBox productdescSection;
	HBox hCateg;
	Label prodNameLbl;
	Label prodPriceLbl;
	Label prodCategLbl;
	Label prodCategValueLbl;
	Label prodDetailLbl;
	Label prodSpeclbl;
	Label prodDescLb1;
	Line garis;
	
	//best seller
	Label bestSellerLbl = new Label("Best Seller!");
	
	//qty
	Label setItemLbl;
	HBox hIsianQty;
	VBox vIsianQty = new VBox();
	Spinner<Integer> qtySpinner = new Spinner<>(1, 99, 1);
	Label stockLbl;
	Label stockValueLbl;
	Button AddtoCartBtn;
	
	public void init() {
		border = new BorderPane();
		scene = new Scene(border,1280,720);
		
		//SEARCHBAR
		view = new ImageView();
		gambar = new Image(getClass().getResourceAsStream("logoGOGO.png"));
		searchBar = new TextField();
		searchBtn = new Button("Search");
		mycartBtn = new Button("My Cart");
		logoutBtn = new Button("Log Out");
		hbox = new HBox();
		vbox = new VBox();
		stack = new StackPane();
		line = new Line();
		kotakNav = new Rectangle();
		stackNav = new StackPane();
		hNav = new HBox(30);
		
		//DETAILPRODUCT
		mainContent = new HBox();
		fotoDetail = new Rectangle();
		fotoSection = new VBox();
		productdescSection = new VBox();
		hCateg = new HBox();
		
		prodNameLbl = new Label("Product Name");
		prodPriceLbl = new Label("$XXX.XX");
		prodCategLbl = new Label("Category: ");
		prodCategValueLbl = new Label("lorem ipsum");
		prodDetailLbl = new Label("Item Detail");
		prodSpeclbl = new Label("Specification:");
		prodDescLb1 = new Label("deskripsi specification");
		garis = new Line();
		
		bestSellerLbl = new Label("Best Seller!");
		setItemLbl = new Label("Set item quantity");
		hIsianQty = new HBox();
		vIsianQty = new VBox();
		stockLbl = new Label("Stock: ");
		stockValueLbl = new Label("xx");
		AddtoCartBtn = new Button("Add to Cart");
		
		
	}
	
	public void navBarShopper() {
		border.setStyle("-fx-background-color: #2d2d38; -fx-padding: 20; -fx-border-radius: 20; -fx-border-color: transparent;-fx-alignment: center;");
		
		//GAMBAR
		view.setImage(gambar);
		view.setFitWidth(100);
		view.setFitHeight(70);
		view.setPreserveRatio(true);
		
		//NAVBAR SHOPPER
		kotakNav.setWidth(900); 
		kotakNav.setHeight(65); 
		kotakNav.setStyle("-fx-fill: #303243");; 
		kotakNav.setArcWidth(70); 
		kotakNav.setArcHeight(70);
		
		//SEARCH BAR
		searchBar.setPromptText("Search Items in GoGoQuery Store");
		searchBar.setPrefWidth(400);
		searchBar.setStyle("-fx-background-color: #3C3F4F; -fx-text-fill: white;");
		searchBtn.setStyle("-fx-background-color: #7278B2; -fx-text-fill: white; -fx-font-weight: bold;");
		
		//SEARCH HBOX
		HBox search = new HBox();
		search.getChildren().addAll(searchBar,searchBtn);
		search.setAlignment(Pos.CENTER);
		search.setPadding(new Insets(0, 50, 0, 20));
		
		//GARIS 
		line.setStartX(10);
		line.setStartY(4);
		line.setEndX(10);
		line.setEndY(35);
		line.setStroke(Color.SLATEGRAY);
		
		mycartBtn.setStyle("-fx-background-color: #303243; -fx-text-fill: white;-fx-font-weight: bold;");
		mycartBtn.setPadding(new Insets(0, 10, 0 , 20));
		logoutBtn.setStyle("-fx-background-color: red; -fx-text-fill: white;-fx-font-weight: bold;");
		
		//NAVBAR
		hNav.getChildren().addAll(view, search, line, mycartBtn, logoutBtn);
		hNav.setAlignment(Pos.CENTER);
		hNav.setSpacing(10);
		
		//STACKPANE FOR NAVBAR
		stackNav.getChildren().addAll(kotakNav, hNav);
		stackNav.setAlignment(Pos.TOP_CENTER);
		
		hbox.getChildren().add(stackNav);
		hbox.setAlignment(Pos.TOP_LEFT);
		hbox.setPadding(new Insets(10));
		hbox.setSpacing(20);
		vbox.setPadding(new Insets(10));
		
		border.setTop(hbox);
	}
	
	public void detailPart() {
		//FOTO SECTION
		fotoDetail.setWidth(250);
		fotoDetail.setHeight(250);
		fotoDetail.setArcHeight(10);
		fotoDetail.setArcWidth(10);
		fotoDetail.setStyle("-fx-fill: #A9A9A9");
		fotoSection.getChildren().add(fotoDetail);
		fotoSection.setPadding(new Insets(30, 10, 0, 100));
		
		//PRODUCT DESC SECTION
		garis.setStartX(20);
		garis.setStartY(35);
		garis.setEndX(300);
		garis.setEndY(35);
		garis.setStroke(Color.ORANGE);
		
		prodNameLbl.setStyle("-fx-text-fill: white; -fx-font-size: 20px; ; -fx-font-weight: bold");
		prodNameLbl.setWrapText(true);
		prodNameLbl.setMaxWidth(280);
		prodPriceLbl.setStyle("-fx-text-fill: #ffa500; -fx-font-size: 25px; -fx-font-weight: bold;");
		
		prodCategLbl.setStyle("-fx-text-fill: #98A3b7; -fx-font-weight: bold");
		prodCategValueLbl.setStyle("-fx-text-fill: #ffa500; -fx-font-weight: bold;");
		hCateg.getChildren().addAll(prodCategLbl, prodCategValueLbl);
		
		prodDetailLbl.setStyle("-fx-text-fill: #ffa500; -fx-font-weight: bold;");
		prodDetailLbl.setPadding(new Insets(10, 0, 2, 0));
		prodSpeclbl.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");
		prodSpeclbl.setPadding(new Insets(2, 0, 10, 0));
		prodDescLb1.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");
		prodDescLb1.setWrapText(true);
		prodDescLb1.setMaxWidth(300);
		productdescSection.getChildren().addAll(prodNameLbl, prodPriceLbl, hCateg, prodDetailLbl, garis, prodSpeclbl, prodDescLb1);
		productdescSection.setPadding(new Insets(28, 20, 0, 0));
		
		//QTY SECTION
		//best seller box
		bestSellerLbl.setStyle("-fx-background-color: linear-gradient(#5D3FD3, #9B5FD3);"
                + "-fx-background-radius: 5; -fx-text-fill: white; -fx-font-weight: bold; "
                + "-fx-font-size: 15;");
		bestSellerLbl.setPadding(new Insets(10));
        bestSellerLbl.setPrefWidth(220);
        bestSellerLbl.setAlignment(Pos.CENTER_LEFT);
        
		setItemLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: white");
		stockLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: white");
		stockValueLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #FF9C24");
		AddtoCartBtn.setStyle("-fx-background-color: #FF9C24; -fx-text-fill: white; -fx-font-weight: bold");
		AddtoCartBtn.setPrefWidth(200);
		qtySpinner.setPrefWidth(80);
		
		HBox stockSection = new HBox();
		stockSection.getChildren().addAll(stockLbl, stockValueLbl);
		stockSection.setPadding(new Insets(8, 0, 0, 0));
		
		hIsianQty.getChildren().addAll(qtySpinner, stockSection);
		hIsianQty.setPadding(new Insets(5, 0, 20, 0));
		hIsianQty.setAlignment(Pos.CENTER_LEFT);
		
		vIsianQty.getChildren().addAll(setItemLbl, hIsianQty, AddtoCartBtn);
		vIsianQty.setAlignment(Pos.CENTER_LEFT);
		vIsianQty.setStyle("-fx-border-color: white; "
                + "-fx-border-width: 2; "
                + "-fx-background-radius: 10; "   
                + "-fx-border-radius: 5;"); 
		vIsianQty.setPadding(new Insets(10, 0, 15, 8));
		
		//MainLayoutQTYSection
		VBox quanSection = new VBox(5);
		quanSection.getChildren().addAll(bestSellerLbl, vIsianQty);
		quanSection.setAlignment(Pos.CENTER);
		quanSection.setPadding(new Insets(0, 0, 50, 0));
		
		//setSpacing
		hIsianQty.setSpacing(15);
		
		mainContent.getChildren().addAll(fotoSection, productdescSection, quanSection);
		mainContent.setAlignment(Pos.TOP_LEFT);
		mainContent.setSpacing(20);
		vbox.getChildren().add(mainContent);
		border.setCenter(vbox);
	}
	
	public void getDetailProduct(int itemId) {
		String query = "SELECT * FROM msitem WHERE ItemID = ?";
		
		try {
			PreparedStatement pst = connect.getConnection().prepareStatement(query);
		 	pst.setInt(1, itemId);
		 	connect.rs = pst.executeQuery();
			while(connect.rs.next()) {
				int id = connect.rs.getInt("ItemID");
				String name = connect.rs.getString("ItemName");
				double price = connect.rs.getDouble("ItemPrice");
				String description = connect.rs.getString("ItemDesc");
				String category = connect.rs.getString("ItemCategory");
				int stock = connect.rs.getInt("ItemStock");
				
				prodNameLbl.setText(name);
				prodPriceLbl.setText("$" + String.format("%.2f", price));  // Set the product price
		        prodCategValueLbl.setText(category);  // Set the product category
		        prodDescLb1.setText(description);  // Set the product description
		        stockValueLbl.setText(String.valueOf(stock));
		        
		        SpinnerValueFactory<Integer> valueFactory = 
		                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, stock, 1);
		        qtySpinner.setValueFactory(valueFactory);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void eventHandler() {
		view.setOnMouseClicked(e -> {
	        ShopperPage shopper = new ShopperPage(detailProductPageStage, userId);
			Scene shopperPageScene = shopper.getShopperPageScene();
			detailProductPageStage.setScene(shopperPageScene);
	    });
		
		mycartBtn.setOnMouseClicked(e -> {
	        CartPage cart = new CartPage(detailProductPageStage, userId);
	        detailProductPageStage.setScene(cart.getCartPageScene());
	    });
		
		logoutBtn.setOnAction(e -> {
			LoginPage loginPage = new LoginPage(detailProductPageStage); 
			Scene loginPagescene = loginPage.getLoginPageScene();
			detailProductPageStage.setScene(loginPagescene);
		});
		
	}
	public void addToCart() {
		AddtoCartBtn.setOnMouseClicked(e -> {
		    try {
		        int spinnerValue = qtySpinner.getValue(); // Get the desired quantity
		        int stock = Integer.parseInt(stockValueLbl.getText()); // Get the stock value

		        // Check if item already exists in the cart
		        String checkCartQuery = "SELECT Quantity FROM mscart WHERE UserID = ? AND ItemID = ?";
		        PreparedStatement checkStmt = connect.getConnection().prepareStatement(checkCartQuery);
		        checkStmt.setInt(1, userId);
		        checkStmt.setInt(2, itemId);
		        connect.rs = checkStmt.executeQuery();

		        if (connect.rs.next()) {
		            // Item exists, update quantity
		            int existingQuantity = connect.rs.getInt("Quantity");
		            int newQuantity = existingQuantity + spinnerValue;

		            // Retrieve item name
		            String itemNameQuery = "SELECT ItemName FROM msitem WHERE ItemID = ?";
		            PreparedStatement nameStmt = connect.getConnection().prepareStatement(itemNameQuery);
		            nameStmt.setInt(1, itemId);
		            connect.rs = nameStmt.executeQuery();
		            String itemName = "";
		            if (connect.rs.next()) {
		            	itemName = connect.rs.getString("ItemName");
		            }
		            
		            if (newQuantity > stock) {
		                showAlert(Alert.AlertType.INFORMATION, "Quantity Exceeds Stock","Not enough stock available",  "There are " + stock + " units left in stock for this item, and you already have " + existingQuantity + 
	                              " units in your cart. The quantity in your cart has been adjusted to the maximum available stock.");
		                newQuantity = stock;
		            }
		            
	                
		            String updateCartQuery = "UPDATE mscart SET Quantity = ? WHERE UserID = ? AND ItemID = ?";
		            PreparedStatement updateStmt = connect.getConnection().prepareStatement(updateCartQuery);
		            updateStmt.setInt(1, newQuantity);
		            updateStmt.setInt(2, userId);
		            updateStmt.setInt(3, itemId);
		            updateStmt.executeUpdate();

		            showAlert(Alert.AlertType.INFORMATION, "Item Already in Cart", "Quantity Updated", "'" + itemName + "' is already in your cart ["+existingQuantity+" unit(s)]. The quantity has been updated to " + newQuantity + " units.");
		        } else {
		            // Item does not exist, add to cart
		            String addToCartQuery = "INSERT INTO mscart (UserID, ItemID, Quantity) VALUES (?, ?, ?)";
		            PreparedStatement insertStmt = connect.getConnection().prepareStatement(addToCartQuery);
		            insertStmt.setInt(1, userId);
		            insertStmt.setInt(2, itemId);
		            insertStmt.setInt(3, spinnerValue);
		            insertStmt.executeUpdate();
		            
		            // Retrieve item name for the alert
	                String itemNameQuery = "SELECT ItemName FROM msitem WHERE ItemID = ?";
	                PreparedStatement nameStmt = connect.getConnection().prepareStatement(itemNameQuery);
	                nameStmt.setInt(1, itemId);
	                connect.rs = nameStmt.executeQuery();
	                String itemName = "";
	                if (connect.rs.next()) {
	                    itemName = connect.rs.getString("ItemName");
	                }
	                
		            showAlert(Alert.AlertType.INFORMATION, "Item Added to Cart","Item Added Successfully", "The item '" + itemName + "' has been added to your cart with a quantity of " + spinnerValue + " unit(s).");
		        }

		    } catch (Exception ex) {
		        ex.printStackTrace();
		        showAlert(Alert.AlertType.ERROR, "Error", "Error Message", "An error occurred while adding to cart.");
		    }
		});
	}
	
	private void showAlert(Alert.AlertType alertType, String title, String htext,String message) {
	    Alert alert = new Alert(alertType);
	    alert.setTitle(title);
	    alert.setHeaderText(htext);
	    
	    Label messageLabel = new Label(message);
	    messageLabel.setWrapText(true); 
	    messageLabel.setMaxWidth(300); 

	    alert.getDialogPane().setContent(messageLabel);
	    alert.getDialogPane().setPrefSize(400, Region.USE_COMPUTED_SIZE); 
	    alert.getDialogPane().setStyle("-fx-font-size: 14px; -fx-text-fill: black;");
	    alert.showAndWait();
	}
	
	
	public DetailProduct(Stage detailProductPageStage, int itemId, int userId) {
		super();
		this.detailProductPageStage = detailProductPageStage;
		this.itemId = itemId;
		this.userId = userId;
		init();
		navBarShopper();
		detailPart();
		getDetailProduct(itemId);
		eventHandler();
		addToCart();
		detailProductPageScene=scene;
		
	}
	
	public Scene getDetailProductPageScene() {
		return detailProductPageScene;
	}
	
	public void setDetailProductPageScene(Scene detailProductPageScene) {
		this.detailProductPageScene = detailProductPageScene;
	}
	
}
