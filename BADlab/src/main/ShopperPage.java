package main;

import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import util.Connect;

public class ShopperPage {
	private Stage shopperPageStage;
	private Scene shopperPageScene;
	private Connect connect = Connect.getInstance();
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
	
	//HOMESHOPPER
	HBox hIsian;
	VBox vIsian;
	HBox hJudul;
	HBox hMessage;
	VBox filterSection;
	VBox productSection;
	HBox mainContent;
	Region spacer;
	
	Label welkamLabel;
	Label namaUserLabel;
	Label filterLabel;
	Label categoryLabel;
	Label showingLabel;
	Label qtyLabel;
	Label showingLabel2;
	
	Rectangle boxProductList;
	Button applyBtn;
	ComboBox<String> categList = new ComboBox<>();
	ListView<String> productList = new ListView<String>();

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
		line = new Line();
		kotakNav = new Rectangle();
		stackNav = new StackPane();
		hNav = new HBox(30);
		
		//HOMESHOPPER
		hIsian = new HBox(10);
		vIsian = new VBox();
		hJudul = new HBox();
		hMessage = new HBox();
		stack = new StackPane();
		filterSection = new VBox();
		productSection = new VBox();
		mainContent = new HBox();
		spacer = new Region();
		
		welkamLabel = new Label("Welcome, ");
		namaUserLabel = new Label("elgankenlie");
		filterLabel = new Label("Filter");
		categoryLabel = new Label("Category");
		
		showingLabel =new Label("Showing ");
		qtyLabel = new Label("XX ");
		showingLabel2 = new Label("products");
		boxProductList = new Rectangle();
		applyBtn = new Button("Apply");
	}
	
	private void addComponent() {
		border.setStyle("-fx-background-color: #2d2d38; -fx-padding: 20; -fx-border-radius: 20; -fx-border-color: transparent;-fx-alignment: center;");
		
		//GAMBAR
		view.setImage(gambar);
		view.setFitWidth(100);
		view.setFitHeight(70);
		view.setPreserveRatio(true);
		
		//NAVBAR SHOPPER
		kotakNav.setWidth(900); 
		kotakNav.setHeight(65); 
		kotakNav.setStyle("-fx-fill: #303243");
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
		HBox.setMargin(mycartBtn, new Insets(0, 10, 0, 20));
		logoutBtn.setStyle("-fx-background-color: red; -fx-text-fill: white;-fx-font-weight: bold;");
		
		//NAVBAR
		hNav.getChildren().addAll(view, search, line, mycartBtn, logoutBtn);
		hNav.setAlignment(Pos.CENTER);
		
		//STACKPANE FOR NAVBAR
		stackNav.getChildren().addAll(kotakNav, hNav);
		stackNav.setAlignment(Pos.TOP_CENTER);
		
		hbox.getChildren().add(stackNav);
		hbox.setAlignment(Pos.TOP_LEFT);
		border.setTop(hbox);
	}
	
	public void homeShopper() {
		mainContent.setAlignment(Pos.CENTER_LEFT);
		
		//JUDUL
		welkamLabel.setStyle("-fx-font-style: italic; -fx-text-fill: white; -fx-font-size: 50");
		namaUserLabel.setStyle("-fx-font-style: italic; -fx-text-fill: #FF9C24; -fx-font-size: 50");
		String username = getUsernameById(userId);
		namaUserLabel.setText(username);
		hJudul.getChildren().addAll(welkamLabel, namaUserLabel);
		hJudul.setPadding(new Insets(30, 0, 40, 0));
		
		vbox.getChildren().add(hJudul);
		border.setCenter(vbox);
		
		//FILTER SECTION
		filterLabel.setStyle("-fx-text-fill: #98A3b7; -fx-font-weight: bold");
		filterLabel.setPadding(new Insets(0, 0, 10, 0));

		//BOXFILL - CATEGORY
		categoryLabel.setStyle("-fx-text-fill: #98A3b7; -fx-font-weight: bold");
		categoryLabel.setAlignment(Pos.CENTER_LEFT);
		categoryLabel.setPadding(new Insets(0, 0, 10, 0));
		applyBtn.setStyle("-fx-background-color: #FF9C24; -fx-text-fill: white; -fx-font-weight: bold");
		categList.setPromptText("Select a category");
		categList.setStyle("-fx-background-color: #575A79 ; -fx-font-weight: bold");
		fetchCategories();
		
		hIsian.getChildren().addAll(categList, applyBtn);
		hIsian.setAlignment(Pos.CENTER_LEFT);
		
		vIsian.getChildren().addAll(categoryLabel, hIsian);
		vIsian.setAlignment(Pos.CENTER_LEFT);
		vIsian.setStyle("-fx-background-color: #303243; -fx-background-radius: 15;");
		vIsian.setPadding(new Insets(10, 15, 15, 15));
		
		filterSection.getChildren().addAll(filterLabel, vIsian);
		filterSection.setAlignment(Pos.TOP_LEFT);
		filterSection.setPadding(new Insets(0, 10, 0 ,70));
		vbox.getChildren().addAll(filterSection);
		vbox.setPadding(new Insets(10));
		
		//PRODUCT LIST SECTION
		showingLabel.setStyle("-fx-text-fill: #98A3b7; -fx-font-weight: bold");
		qtyLabel.setStyle("-fx-text-fill: #FF9C24; -fx-font-weight: bold");
		showingLabel2.setStyle("-fx-text-fill: #98A3b7; -fx-font-weight: bold");
		
		hMessage.getChildren().addAll(showingLabel, qtyLabel, showingLabel2);
		hMessage.setPadding(new Insets(0, 0, 10, 0));
		hMessage.setAlignment(Pos.CENTER_LEFT);

		productList.setPrefHeight(500);
		productList.setPrefWidth(600);
		productList.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-control-inner-background: transparant;");
	    
		productList.setCellFactory(listView -> new ListCell<>() {
			//buat component
			HBox hMainContent = new HBox();
			VBox vIsianList = new VBox();
			HBox hHargaStock = new HBox();
			StackPane stackStock = new StackPane();
			Rectangle stockBox = new Rectangle();
			Region spacerList = new Region();
			
			//FOTO
			Rectangle fotoHome = new Rectangle();
			
			//ISIAN
			Label productNameLbl = new Label();
			Label productPriceLbl = new Label();
			Label productStockLbl = new Label();
			
			{
				//FOTO
				fotoHome.setWidth(100);
				fotoHome.setHeight(100);
				fotoHome.setArcHeight(10);
				fotoHome.setArcWidth(10);
				fotoHome.setStyle("-fx-fill: #808080");
				
				//ISIAN
				productNameLbl.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 20");
				productNameLbl.setWrapText(true);
				productNameLbl.setMaxWidth(400);
				productPriceLbl.setStyle("-fx-text-fill: #FF9C24; -fx-font-weight: bold; -fx-font-size: 20");
				productStockLbl.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 10");
				
				stockBox.setWidth(50);
				stockBox.setHeight(30);
				stockBox.setArcHeight(5);
				stockBox.setArcWidth(5);
				stockBox.setStyle("-fx-fill: #FF0000");
				stackStock.getChildren().addAll(stockBox, productStockLbl);
				
				hHargaStock.setSpacing(10);
				hHargaStock.getChildren().addAll(productPriceLbl, stackStock);
				
				vIsianList.setAlignment(Pos.CENTER_LEFT);
				vIsianList.getChildren().addAll(productNameLbl, hHargaStock);
				
				spacerList.setPrefWidth(20);
				hMainContent.getChildren().addAll(fotoHome, spacerList, vIsianList);
				hMainContent.setStyle("-fx-background-color: #303243; -fx-padding: 20; -fx-border-radius: 5;");
				HBox.setMargin(hMainContent, new Insets(20));
			}
			
			@Override
			protected void updateItem(String item, boolean empty) {
			    super.updateItem(item, empty);
			    if (empty || item == null) {
			        setText(null);
			        setGraphic(null);
			    } else {
			        String[] parts = item.split("\\|\\|");
			        if (parts.length == 3) {
			            productNameLbl.setText(parts[0].trim()); // ItemName
			            productPriceLbl.setText("$" + parts[1].trim()); // ItemPrice
			            productStockLbl.setText(parts[2].trim() + " Left"); // ItemStock
			            
			            HBox.setMargin(hMainContent, new Insets(10, 0, 10, 0));
			            setGraphic(hMainContent);
			        } else {
			            setText("Invalid data format");
			            setGraphic(null);
			        }
			    }
			}
		});
		
		productSection.getChildren().addAll(hMessage, productList);
		productSection.setAlignment(Pos.CENTER);
		
		spacer.setPrefWidth(20);
		mainContent.getChildren().addAll(filterSection, spacer, productSection);
		vbox.getChildren().add(mainContent);
	}
	private void fetchCategories() {
	    String query = "SELECT DISTINCT ItemCategory FROM msitem";
	    
	    try {
	        PreparedStatement pst = connect.getConnection().prepareStatement(query);
	        ResultSet rs = pst.executeQuery();
	        
	        categList.getItems().clear();
	        
	        while (rs.next()) {
	            categList.getItems().add(rs.getString("ItemCategory"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void beautifying() {
		BorderPane.setMargin(hbox, new Insets(10));
		BorderPane.setMargin(vbox, new Insets(10));
		hbox.setSpacing(20);
		hNav.setSpacing(10);
	}
	
	public void addEventHandlers() {
		mycartBtn.setOnMouseClicked(event -> {
	        CartPage cart = new CartPage(shopperPageStage, userId);
	        shopperPageStage.setScene(cart.getCartPageScene());
	    });
		
		searchBtn.setOnAction(event -> {
 		    String searchText = searchBar.getText().trim();
 		    if (!searchText.isEmpty()) {
 		        getSearch_HomePage(searchText);
 		    } else {
 		        getProductList_HomePage();
 		    }
 		});
		
		logoutBtn.setOnAction(e -> {
			LoginPage loginPage = new LoginPage(shopperPageStage); 
			Scene loginPagescene = loginPage.getLoginPageScene();
			shopperPageStage.setScene(loginPagescene);
		});
	}
	
	public void getProductList_HomePage() {
		String query = "SELECT ItemName, ItemPrice, ItemStock FROM msitem";
	    productList.getItems().clear();
	    
	    try (PreparedStatement ps = connect.getConnection().prepareStatement(query);
	         ResultSet rs = ps.executeQuery()) {
	        int count = 0;
	        while (rs.next()) {
	            String namaProduk = rs.getString("ItemName");
	            String hargaProduk = rs.getString("ItemPrice");
	            String stokProduk = rs.getString("ItemStock");
	            
	            productList.getItems().add(namaProduk + " || " + hargaProduk + " || " + stokProduk);
	            count++;
	        }
	        updateShowingMessageSearchBar(count, ""); // Kosongkan searchText
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void updateShowingMessageSearchBar(int count, String searchText) {
	    showingLabel.setText("Showing ");
	    qtyLabel.setText(count + " ");

	    TextFlow textFlow = new TextFlow(); 
	    Text productsText = new Text("products ");
	    productsText.setFill(Color.web("#98A3b7")); 
	    textFlow.getChildren().add(productsText);
	    
	    if (searchText != null && !searchText.isEmpty()) {
	        Text searchTextNode = new Text("for ");
	        searchTextNode.setFill(Color.web("#98A3b7")); 
	        searchTextNode.setStyle("-fx-font-weight: bold");
	        textFlow.getChildren().add(searchTextNode);

	        Text petikNode1 = new Text("'");
	        petikNode1.setFill(Color.web("#FF9C24"));
	        petikNode1.setStyle("-fx-font-weight: bold");
	        textFlow.getChildren().add(petikNode1);
	        
	        Text highlightedText = new Text(searchText);
	        highlightedText.setFill(Color.web("#FF9C24"));
	        highlightedText.setStyle("-fx-font-weight: bold");
	        textFlow.getChildren().add(highlightedText);
	        
	        Text petikNode2 = new Text("'");
	        petikNode2.setFill(Color.web("#FF9C24"));
	        petikNode2.setStyle("-fx-font-weight: bold");
	        textFlow.getChildren().add(petikNode2);
	    }

	    showingLabel2.setGraphic(textFlow); 
	    showingLabel2.setText("");
	}
	
	public void getSearch_HomePage(String searchText) {
		String query = "SELECT ItemName, ItemPrice, ItemStock FROM msitem WHERE ItemName LIKE ?";
		
		try (PreparedStatement ps = connect.getConnection().prepareStatement(query)){
			ps.setString(1, "%" + searchText + "%");
			
			try (ResultSet rs = ps.executeQuery()){
				productList.getItems().clear();
				int count = 0;
				while (rs.next()) {
					String namaProduk = rs.getString("ItemName");
					String hargaProduk = rs.getString("ItemPrice");
					String stokProduk = rs.getString("ItemStock");
					
					// Add formatted items to the ListView
					productList.getItems().add(namaProduk + "||" + hargaProduk + "||" + stokProduk);
					count++;
				}
				updateShowingMessageSearchBar(count, searchText);
			} 
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void updateShowingMessageFilter(int count, String searchText) {
	    showingLabel.setText("Showing ");
	    qtyLabel.setText(count + " ");
	    
	    TextFlow textFlow = new TextFlow(); 
	    Text productsText = new Text("products ");
	    productsText.setFill(Color.web("#98A3b7"));
	    textFlow.getChildren().add(productsText);
	    
	    if (searchText == null || searchText.isEmpty()) {
	        textFlow.getChildren().add(new Text("products")); 
	    } else {
	        Text searchTextNode = new Text("for ");
	        searchTextNode.setFill(Color.web("#98A3b7"));
	        searchTextNode.setStyle("-fx-font-weight: bold");
	        textFlow.getChildren().add(searchTextNode);
	        
	        Text petikNode1 = new Text("'");
	        petikNode1.setFill(Color.web("#FF9C24"));
	        petikNode1.setStyle("-fx-font-weight: bold");
	        textFlow.getChildren().add(petikNode1);
	        
	        Text highlightedText = new Text(searchText);
	        highlightedText.setFill(Color.web("#FF9C24"));
	        highlightedText.setStyle("-fx-font-weight: bold");
	        textFlow.getChildren().add(highlightedText);
	        
	        Text petikNode2 = new Text("'");
	        petikNode2.setFill(Color.web("#FF9C24"));
	        petikNode2.setStyle("-fx-font-weight: bold");
	        textFlow.getChildren().add(petikNode2);
	        
	        Text categoryTextNode = new Text(" category ");
	        categoryTextNode.setFill(Color.web("#98A3b7"));
	        categoryTextNode.setStyle("-fx-font-weight: bold");
	        textFlow.getChildren().add(categoryTextNode);
	    }

	    showingLabel2.setGraphic(textFlow); 
	    showingLabel2.setText(""); 
	}
	
	public void getFilter_HomePage() {
	    applyBtn.setOnAction(new EventHandler<ActionEvent>() {

	        @Override
	        public void handle(ActionEvent event) {
	            String selectedCateg = categList.getValue();

	            if ("Select a category".equals(selectedCateg)) {
	                productList.getItems().clear();
	                System.out.println("Selected category: " + selectedCateg);
	                getProductList_HomePage(); // Show all products
	                return;
	            }

	            String query = "SELECT ItemName, ItemPrice, ItemStock FROM msitem WHERE ItemCategory = ?";
	            try {
	                PreparedStatement pst = connect.getConnection().prepareStatement(query);
	                pst.setString(1, selectedCateg);
	                connect.rs = pst.executeQuery();
	                productList.getItems().clear(); // Clear current product list

	                // Populate product list with filtered data
	                int count = 0;
	                while (connect.rs.next()) {
	                    String namaProduk = connect.rs.getString("ItemName");
	                    String hargaProduk = connect.rs.getString("ItemPrice");
	                    String stokProduk = connect.rs.getString("ItemStock");

	                    productList.getItems().add(namaProduk + " || " + hargaProduk + " || " + stokProduk);
	                    count++;
	                }
	                	updateShowingMessageFilter(count, selectedCateg);
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}
	
	public void toDetailPage(String itemName) {
		productList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				System.out.println("off to detail page");
				String selectedItem = productList.getSelectionModel().getSelectedItem();
				String[] selectedItemDetails = selectedItem.split("\\|\\|");
				String itemName = selectedItemDetails[0].trim();  // Get the product name
	            int itemId = getItemIdByName(itemName); 
				
				DetailProduct detail = new DetailProduct(shopperPageStage, itemId, userId);
				Scene detailProductScene = detail.getDetailProductPageScene();
				shopperPageStage.setScene(detailProductScene);
				
			}
		});
		
		String query = "SELECT ItemName, ItemPrice, ItemStock FROM msitem WHERE ItemName = ?";
		try (PreparedStatement preparedStatement = connect.getConnection().prepareStatement(query)) {
		    preparedStatement.setString(1, itemName); // Bind the value to the placeholder
		    ResultSet rs = preparedStatement.executeQuery();
		    int userId = 1;
		    
		    while (rs.next()) {
		        String namaProduk = rs.getString("ItemName");
		        String hargaProduk = rs.getString("ItemPrice");
		        String stokProduk = rs.getString("ItemStock");

		        productList.getItems().add(namaProduk + "||" + hargaProduk + "||" + stokProduk);
		    }
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	}

	public int getItemIdByName (String itemName) {
		int itemId = -1;
	    String query = "SELECT ItemID FROM msitem WHERE ItemName = ?";
	    try (PreparedStatement pst = connect.getConnection().prepareStatement(query)) {
	        pst.setString(1, itemName);
	        connect.rs = pst.executeQuery();
	        
	        if (connect.rs.next()) {
	            itemId = connect.rs.getInt("ItemID");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return itemId;
	}
	
	public String getUsernameById(int userId) {
	    String email = "";
	    String query = "SELECT UserEmail FROM msuser WHERE UserID = ?";
	    try (PreparedStatement pst = connect.getConnection().prepareStatement(query)) {
	        pst.setInt(1, userId);
	        ResultSet rs = pst.executeQuery();
	        if (rs.next()) {
	            email = rs.getString("userEmail"); // Ambil email pengguna
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    // Ekstrak username dari email
	    if (email.contains("@")) {
	        return email.substring(0, email.indexOf("@")); // Ambil bagian sebelum '@'
	    }
	    return ""; // Kembalikan string kosong jika tidak ada '@'
	}
	
	public ShopperPage(Stage shopperPageStage, int userId) {
		super();
		this.shopperPageStage = shopperPageStage;
		this.userId = userId;
		init();
		addComponent();
		beautifying();
		homeShopper();
		getItemIdByName("");
		getProductList_HomePage();
		getSearch_HomePage("");
		getFilter_HomePage();
		toDetailPage("");
		addEventHandlers();
		shopperPageScene = scene;
	}

	public Scene getShopperPageScene() {
		return shopperPageScene;
	}

	public void setShopperPageScene(Scene shopperPageScene) {
		this.shopperPageScene = shopperPageScene;
	}
}
