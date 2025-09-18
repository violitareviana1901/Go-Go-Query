package main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.CartDetail;
import model.Product;
import util.Connect;

public class CartPage {
	private Stage cartPageStage;
	private Scene cartPageScene;
	private Connect connect = Connect.getInstance();
	private int userId;
	BorderPane border;
	Scene scene;
	
	//Searchbar
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
	
	//LabelUserName
	Label name;
	Label cart;
	HBox namaCart;
	
	//emptyCartPage
	Label emptyCart;
	
	//display Cart
	Label showingLabel;
	Label qtyLabel;
	Label showingLabel2;
	HBox hMessage;
	
	Label billing;
	Label total;
	Label harga;
	Label footerTot;
	Line line2;
	Button checkOut;
	HBox priceMsg;
	VBox bilBox;
	
	//listCart
	ListView<CartDetail> cartList = new ListView<>();
	
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
		hNav = new HBox();
		
		//LabelUserName
		name = new Label("elgankenlie");
		cart = new Label("'s Cart");
		namaCart = new HBox();
		
		//emptyCartPage
		emptyCart= new Label("Your cart is empty!");
		
		//displayCart
		showingLabel =new Label("Showing ");
		qtyLabel = new Label("XX ");
		showingLabel2 = new Label(" products in cart");
		hMessage = new HBox();
		
		billing = new Label("Billing summary");
		total = new Label("Total : ");
		harga = new Label("$XX");
		footerTot = new Label("*Tax and deliverey cost included");
		line2 = new Line();
		checkOut = new Button("Checkout Items");
		priceMsg = new HBox();
		bilBox = new VBox();
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
		hNav = new HBox(30);
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
		
		name.setStyle("-fx-font-style: italic; -fx-text-fill: #FF9C24; -fx-font-size: 45");
		String username = getUsernameById(userId);
		name.setText(username);
		cart.setStyle("-fx-font-style: italic; -fx-text-fill: white; -fx-font-size: 45");
		namaCart.getChildren().addAll(name, cart);
		namaCart.setPadding(new Insets(30, 0, 30, 20));
		namaCart.setAlignment(Pos.CENTER_LEFT);
		vbox.getChildren().add(namaCart);
		border.setCenter(vbox);
	}
	
	public void emptyCart() {
		emptyCart.setStyle("-fx-font-style: italic; -fx-text-fill: #98A3b7; -fx-font-size: 30");
		emptyCart.setPadding(new Insets(0, 0, 0, 80));
		emptyCart.setAlignment(Pos.CENTER_LEFT);
		vbox.getChildren().add(emptyCart);
	}
	
	public void displayCart(List<CartDetail> cartItems) {
	    vbox.getChildren().clear();

	    // Clear and reset hMessage
	    hMessage.getChildren().clear();
	    if (!cartItems.isEmpty()) {
	    	showingLabel.setStyle("-fx-text-fill: #98A3b7; -fx-font-weight: bold");
	    	qtyLabel.setStyle("-fx-text-fill: #FF9C24; -fx-font-weight: bold");
	    	showingLabel2.setStyle("-fx-text-fill: #98A3b7; -fx-font-weight: bold");
	    	qtyLabel.setText(String.valueOf(cartItems.size())); // Update quantity dynamically
	    	hMessage.getChildren().addAll(showingLabel, qtyLabel, showingLabel2);
	    	hMessage.setPadding(new Insets(0, 0, 10, 0));
	    	hMessage.setAlignment(Pos.TOP_LEFT);
		}
	    
	    // Billing Section
	    billing.setStyle("-fx-text-fill: #C4C1CE; -fx-font-weight: bold");
	    billing.setPadding(new Insets(5, 0, 10, 0));
	    total.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 20");
	    harga.setStyle("-fx-text-fill: #FF9C24; -fx-font-weight: bold; -fx-font-size: 22");
	    priceMsg.getChildren().clear();
	    priceMsg.getChildren().addAll(total, harga);
	    priceMsg.setAlignment(Pos.CENTER_LEFT);

	    footerTot.setStyle("-fx-text-fill: #98A3b7; -fx-font-weight: bold; -fx-font-size: 10");
	    line2.setStartX(20);
	    line2.setStartY(35);
	    line2.setEndX(250);
	    line2.setEndY(35);
	    line2.setStroke(Color.LIGHTGREY);
	    checkOut.setStyle("-fx-background-color: #FF9C24; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14");
	    checkOut.setPrefWidth(232);
	    checkOut.setPadding(new Insets(5));
	    checkOut.setAlignment(Pos.CENTER);
	    checkOut.setOnAction(e -> {
	        // Show confirmation alert
	        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	        alert.setTitle("Checkout Confirmation");
	        alert.setHeaderText("Are you sure you want to checkout your cart?");
	        alert.setContentText("Please confirm your choice.");
	        
	        alert.showAndWait().ifPresent(response -> {
	            if (response == ButtonType.OK) {
	                processCheckout(cartItems);
	            }
	        });
	    });
	    VBox footerSection = new VBox(20);
	    footerSection.getChildren().addAll(footerTot, line2, checkOut);
	    bilBox.getChildren().clear();
	    bilBox.getChildren().addAll(billing, priceMsg, footerSection);
	    bilBox.setStyle("-fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 5;");
	    bilBox.setPadding(new Insets(10, 10, 15, 10));
	    bilBox.setSpacing(3);
	    bilBox.setMaxHeight(80);
	    
	    // ListView Section
	    cartList.getItems().setAll(cartItems);
	    cartList.setPrefHeight(900);
	    cartList.setPrefWidth(690);
	    cartList.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-control-inner-background: transparant;");
	    
	    cartList.setCellFactory(listview -> new ListCell<>() {
	        @Override
	        protected void updateItem(CartDetail cartDetail, boolean empty) {
	            super.updateItem(cartDetail, empty);

	            if (empty || cartDetail == null) {
	                setText(null);
	                setGraphic(null);
	            } else {
	                // Create Product Components
	                Product product = cartDetail.getProduct();

	                // Foto produk
	                Rectangle foto = new Rectangle(120, 120);
	                foto.setArcHeight(10);
	                foto.setArcWidth(10);
	                foto.setStyle("-fx-fill: #808080;");

	                // Nama produk
	                Label productName = new Label(product.getItemName());
	                productName.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16;");
	                productName.setWrapText(true);
	                productName.setMaxWidth(300);

	                // Harga produk
	                Label productPrice = new Label("$" + String.format("%.2f", product.getItemPrice()));
	                productPrice.setStyle("-fx-text-fill: #FF9C24; -fx-font-weight: bold; -fx-font-size: 18;");

	                // Quantity Spinner
	                Spinner<Integer> quantity = new Spinner<>(0, cartDetail.getItemStock(), cartDetail.getQuantity());
	                quantity.valueProperty().addListener((obs, oldValue, newValue) -> {
	                	if (newValue == 0) {
	                		quantity.getValueFactory().setValue(1);
	                		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	                		alert.setTitle("Item Removal Confirmation");
	                		alert.setHeaderText("Do you want to remove this item from your cart?");
	                		alert.setContentText("Please confirm your choice.");
	                		alert.showAndWait().ifPresent(response -> {
	                			if (response == ButtonType.OK) {
	                				cartDetail.setQuantity(newValue);
	                				updateItemQuantityInDatabase(cartDetail.getProduct().getItemID(), userId, newValue);
	                				updateGrandTotal(); 
	                				
	                				getListView().getItems().remove(cartDetail);
	                				removeItemFromDatabase(cartDetail.getProduct().getItemID(), userId);
	                				
	                				if (cartList.getItems().isEmpty()) {
	                                    clearCart(userId);
	                                    cartList.getItems().clear();
	                                    hMessage.getChildren().clear();
	                                    vbox.getChildren().remove(hMessage); 
	                                    vbox.getChildren().remove(cartList);
	                                    vbox.getChildren().remove(bilBox);
	                                    emptyCart();
	                                    updateGrandTotal();
	                                    
	                                    cartPageStage.setScene(new CartPage(cartPageStage, userId).getCartPageScene());
	                                }
	                			} else {
	                                quantity.getValueFactory().setValue(1);
	                            }
	                		});
	                	}else {
	                        // Update quantity jika nilai baru bukan 0
	                        cartDetail.setQuantity(newValue);
	                        updateItemQuantityInDatabase(cartDetail.getProduct().getItemID(), userId, newValue);
	                        updateGrandTotal();
	                    }
	                });
	                quantity.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-font-weight: bold;");
	                quantity.setPrefWidth(60);
	                
	                // Remove Button
	                Button remove = new Button("x");
	                remove.setPrefWidth(30);
	                remove.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white; -fx-font-weight: bold;");
	                remove.setOnAction(e -> {
	                	int originalQuantity = cartDetail.getQuantity();
	                	
	                    // Show confirmation alert
	                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	                    alert.setTitle("Item Removal Confirmation");
	                    alert.setHeaderText("Do you want to remove this item from your cart?");
	                    alert.setContentText("Please confirm your choice.");
	            		alert.showAndWait().ifPresent(response -> {
	                        if (response == ButtonType.OK) {
	                            getListView().getItems().remove(cartDetail);
	                            removeItemFromDatabase(cartDetail.getProduct().getItemID(), userId);
	                            updateGrandTotal(); 
	                            
	                            if (cartList.getItems().isEmpty()) {
	                            	clearCart(userId);
	            	                cartList.getItems().clear();
	            	                hMessage.getChildren().clear();
	            	                vbox.getChildren().remove(hMessage); 
	            	                vbox.getChildren().remove(cartList);
	            	                vbox.getChildren().remove(bilBox);
	            	                emptyCart();
	            	                updateGrandTotal(); 
	            	                
	                            	cartPageStage.setScene(new CartPage(cartPageStage, userId).getCartPageScene());

	                            }
	                        }else {
	                            // If canceled, revert the quantity display if needed
	                            quantity.getValueFactory().setValue(originalQuantity);
	                        }
	                    });
	                });
	                // Detail produk
	                VBox detailProduk = new VBox(10, productName, productPrice);
	                detailProduk.setAlignment(Pos.TOP_LEFT);
	                
	                //Layout sections
	                VBox kontrolProduk = new VBox(10, remove, quantity);
	                kontrolProduk.setAlignment(Pos.CENTER_RIGHT);
	                kontrolProduk.setPrefWidth(100); // Lebar tetap agar stabil di sisi kanan
	                kontrolProduk.setPadding(new Insets(0, 10, 0, 0)); // Padding kanan untuk mendorong ke kanan lebih jauh
	                kontrolProduk.setSpacing(60);
	                // Untuk geser remove button dan spinner ke kanan
	                Region spacer = new Region();
	                HBox.setHgrow(spacer, Priority.ALWAYS);

	                // Horizontal layout untuk produk
	                HBox produkContainer = new HBox(10, foto, detailProduk, spacer, kontrolProduk);
	                produkContainer.setAlignment(Pos.CENTER); // Center alignment agar rata secara vertikal
	                produkContainer.setStyle("-fx-background-color: #303243; -fx-padding: 10; -fx-border-radius: 5;");
	                produkContainer.setPadding(new Insets(10));

	                // Transparan table
	                setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
	                setGraphic(produkContainer);
	                setText(null);
	            }
	        }
	    });

	    VBox showProd = new VBox(10, hMessage, cartList);
	    showProd.setAlignment(Pos.CENTER_LEFT);
	    showProd.setPadding(new Insets(0, 0, 0, 80));

	    HBox cartBill = new HBox(60, showProd, bilBox);
	    cartBill.setAlignment(Pos.TOP_LEFT);
	    cartBill.setPadding(new Insets(5, 0, 0, 80));
	    
	    VBox cartpage = new VBox(namaCart, cartBill);
	    cartpage.setAlignment(Pos.CENTER_LEFT);
	    vbox.getChildren().add(cartpage);

	    double grandTotal = CartDetail.getGrandTotal(cartItems);
	    harga.setText("$" + String.format("%.2f", grandTotal));
	}
	
	private void updateGrandTotal() {
	    double grandTotal = CartDetail.getGrandTotal(cartList.getItems());
	    harga.setText("$" + String.format("%.2f", grandTotal));
	}
	
	private void updateItemQuantityInDatabase(int itemId, int userId, int newQuantity) {
	    String query = "UPDATE mscart SET Quantity = ? WHERE UserID = ? AND ItemID = ?";
	    try (PreparedStatement pst = connect.getConnection().prepareStatement(query)) {
	        pst.setInt(1, newQuantity);
	        pst.setInt(2, userId);
	        pst.setInt(3, itemId);
	        int rowsAffected = pst.executeUpdate();
	        if (rowsAffected > 0) {
	            System.out.println("Quantity updated in database successfully.");
	        } else {
	            System.out.println("No item found with the specified UserID and ItemID.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	private void removeItemFromDatabase(int itemId, int userId) {
	    String query = "DELETE FROM mscart WHERE UserID = ? AND ItemID = ?";
	    try (PreparedStatement pst = connect.getConnection().prepareStatement(query)) {
	        pst.setInt(1, userId);
	        pst.setInt(2, itemId);
	        int rowsAffected = pst.executeUpdate();
	        if (rowsAffected > 0) {
	            System.out.println("Item removed from database successfully.");
	        } else {
	            System.out.println("No item found with the specified UserID and ItemID.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public String getUsernameById(int userId) {
	    String email = "";
	    String query = "SELECT UserEmail FROM msuser WHERE UserID = ?";
	    try (PreparedStatement pst = connect.getConnection().prepareStatement(query)) {
	        pst.setInt(1, userId);
	        ResultSet rs = pst.executeQuery();
	        if (rs.next()) {
	            email = rs.getString("userEmail");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    if (email.contains("@")) {
	        return email.substring(0, email.indexOf("@")); // Ambil bagian sebelum '@'
	    }
	    return ""; // Kembalikan string kosong jika tidak ada '@'
	}
	
	public List<CartDetail> getCartItems() {
	    List<CartDetail> cartDetails = new ArrayList<>();
	    String query = "SELECT mc.ItemID, mi.ItemName, mi.ItemPrice, mc.Quantity, mi.ItemStock " +
	                   "FROM mscart mc JOIN msitem mi ON mc.ItemID = mi.ItemID " +
	                   "WHERE mc.UserID = ?"; 
	    try {
	        PreparedStatement pst = connect.getConnection().prepareStatement(query);
	        pst.setInt(1, userId);
	        ResultSet rs = pst.executeQuery();

	        while (rs.next()) {
	            int itemId = rs.getInt("ItemID");
	            String itemName = rs.getString("ItemName");
	            double itemPrice = rs.getDouble("ItemPrice");
	            int quantity = rs.getInt("Quantity");
	            int itemStock = rs.getInt("ItemStock");
	            
	            Product product = new Product(itemId, itemName, itemPrice, "", 0, ""); 
	            CartDetail cartDetail = new CartDetail(userId, product, quantity, itemStock);
	            cartDetails.add(cartDetail);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return cartDetails;
	}
	
	private void processCheckout(List<CartDetail> cartItems) {
	    int transactionId = createTransactionHeader(userId);
	    
	    if (transactionId != -1) {
	    	for (CartDetail item : cartItems) {
	    		updateItemStock(item.getProduct().getItemID(), item.getQuantity());
	    		insertTransactionDetail(transactionId, item.getProduct().getItemID(), item.getQuantity());
	    	}
	    	
	    	Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
	    	successAlert.setTitle("Transaction Information");
	    	successAlert.setHeaderText("Transaction success!!");
	    	successAlert.setContentText("Your order is now in queue.");
	    	successAlert.showAndWait().ifPresent(response -> {
	            if (response == ButtonType.OK) {
	                clearCart(userId);
	                cartList.getItems().clear();
	                hMessage.getChildren().clear();
	                vbox.getChildren().remove(hMessage); 
	                vbox.getChildren().remove(cartList);
	                vbox.getChildren().remove(bilBox);
	                emptyCart();
	                updateGrandTotal(); 
	                
	                cartPageStage.setScene(new CartPage(cartPageStage, userId).getCartPageScene());
	            }
	        });
	    }else {
	        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
	        errorAlert.setTitle("Transaction Error");
	        errorAlert.setHeaderText("Transaction could not be processed.");
	        errorAlert.setContentText("Please try again.");
	        errorAlert.showAndWait();
	    }
	}
	
	private void clearCart(int userId) {
	    String query = "DELETE FROM mscart WHERE UserID = ?";
	    try (PreparedStatement pst = connect.getConnection().prepareStatement(query)) {
	        pst.setInt(1, userId);
	        int rowsAffected = pst.executeUpdate();
	        if (rowsAffected > 0) {
	            System.out.println("Cart cleared from database successfully.");
	        } else {
	            System.out.println("No items found to clear from cart.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	private int createTransactionHeader(int userId) {
	    String query = "INSERT INTO transactionheader (UserID, DateCreated, Status) VALUES (?, CURRENT_DATE, 'In Queue')";
	    try (PreparedStatement pst = connect.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
	        pst.setInt(1, userId);
	        pst.executeUpdate();
	        
	        ResultSet generatedKeys = pst.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            return generatedKeys.getInt(1); // Return the generated TransactionID
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return -1; 
	}
	
	private void updateItemStock(int itemId, int quantitySold) {
	    String query = "UPDATE msitem SET ItemStock = ItemStock - ? WHERE ItemID = ?";
	    try (PreparedStatement pst = connect.getConnection().prepareStatement(query)) {
	        pst.setInt(1, quantitySold);
	        pst.setInt(2, itemId);
	        pst.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	private void insertTransactionDetail(int transactionId, int itemId, int quantity) {
	    String query = "INSERT INTO transactiondetail (TransactionID, ItemID, Quantity) VALUES (?, ?, ?)";
	    try (PreparedStatement pst = connect.getConnection().prepareStatement(query)) {
	        pst.setInt(1, transactionId);
	        pst.setInt(2, itemId);
	        pst.setInt(3, quantity);
	        pst.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void eventHandler() {
		view.setOnMouseClicked(e -> {
	        ShopperPage shopper = new ShopperPage(cartPageStage, userId);
			Scene shopperPageScene = shopper.getShopperPageScene();
			cartPageStage.setScene(shopperPageScene);
	    });
		
		logoutBtn.setOnAction(e -> {
			LoginPage loginPage = new LoginPage(cartPageStage); 
			Scene loginPagescene = loginPage.getLoginPageScene();
			cartPageStage.setScene(loginPagescene);
		});
		
	}
	
	public CartPage(Stage cartPageStage, int userId) {
		super();
		this.cartPageStage = cartPageStage;
		this.userId = userId;
		init();
		navBarShopper();
		
		List<CartDetail> cartItems = getCartItems();
	    if (cartItems.isEmpty()) {
	        emptyCart();
	    } else {
	        displayCart(cartItems);
	    }
		eventHandler();
		cartPageScene= scene;
	}
	
	public Scene getCartPageScene() {
		return cartPageScene;
	}
	
	public void setCartPageScene(Scene cartPageScene) {
		this.cartPageScene = cartPageScene;
	}
}
