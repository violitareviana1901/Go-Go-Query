package main;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.Connect;

public class SelectRolePage implements EventHandler<ActionEvent> {
	private Stage selectRolePageStage;
	private Scene selectRolePageScene;
	private Connect connect = Connect.getInstance();
	
	Scene scene;
	BorderPane bp;
	ImageView view;
	ImageView logoM;
	ImageView logoS;
	Image gambar;
	Image gbrManager;
	Image gbrShopper;
	Label mLabel;
	Label sLabel;
	Line garis1;
	Line garis2;
	Text descManager;
	Text descShopper;
	Button mButton;
	Button sButton;
	private int userID;
	
	public void init() {
		bp = new BorderPane();
		scene = new Scene(bp, 1280, 720);
		
		//GAMBAR
		view = new ImageView();
		logoM = new ImageView();
		logoS = new ImageView();
		gambar = new Image(getClass().getResourceAsStream("logoGOGO.png"));
		gbrManager = new Image(getClass().getResourceAsStream("logoM.png"));
		gbrShopper = new Image(getClass().getResourceAsStream("logoS.png"));
		
		//LABEL
		mLabel = new Label("Manager");
		sLabel = new Label("Shopper");
		
		//LINE
		garis1= new Line(30,40,430,40);
		garis2= new Line(30,40,430,40);
		
		//DESCRIPTION
		descManager = new Text("Manage products and deliveries, be the ruler!");
		descShopper = new Text("Search products, manage your cart, go shopping!");
		
		//BUTTON
		mButton= new Button("Register as Manager");
		sButton= new Button("Register as Shopper");
	}
	
	public void addComponent() {
		//LOGO
		view.setImage(gambar);
		view.setFitWidth(300);
		view.setFitHeight(180);
		view.setPreserveRatio(true);
		
		//STYLING
		mLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD,22));
		sLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD,22));
		descManager.setFont(Font.font("Times New Roman",FontWeight.THIN,14));
		descShopper.setFont(Font.font("Times New Roman",FontWeight.THIN,14));
		
		mButton.setPrefSize(400, 30);
		sButton.setPrefSize(400, 30);
		mButton.setStyle("-fx-background-color: orange; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-border-radius: 5; -fx-padding: 10px;");
		sButton.setStyle("-fx-background-color: orange; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-border-radius: 5; -fx-padding: 10px;");

		logoM.setImage(gbrManager);
		logoM.setFitWidth(180);
		logoM.setFitHeight(120);
		logoM.setPreserveRatio(true);
		
		logoS.setImage(gbrShopper);
		logoS.setFitWidth(180);
		logoS.setFitHeight(120);
		logoS.setPreserveRatio(true);
		
		// MANAGER BOX
        VBox ManagerBox = new VBox(15, logoM,mLabel, garis1, descManager, mButton);
        ManagerBox.setStyle("-fx-background-color: white; -fx-alignment: center;");
        ManagerBox.setPadding(new Insets(20,60,35,60));
        
        // SHOPPER BOX
        VBox ShopperBox = new VBox(15, logoS,sLabel, garis2, descShopper, sButton);
        ShopperBox.setStyle("-fx-background-color: white; -fx-alignment: center;");
        ShopperBox.setPadding(new Insets(20,60,35,60));
        
        // HBOX 
        HBox layoutRoles = new HBox(60, ManagerBox, ShopperBox);
        layoutRoles.setAlignment(Pos.CENTER);
        
        // VBOX ROLE PAGE
        VBox rolePage = new VBox(30, view, layoutRoles);
        rolePage.setAlignment(Pos.CENTER);
        
        // ALL LAYOUT
        bp.setCenter(rolePage);
        bp.setStyle("-fx-background-color: #2d2d38; -fx-padding: 20; -fx-border-radius: 10; -fx-border-color: transparent;-fx-alignment: center;" + 
        		" ");
	}
	
	public void setMouseEvent() {
		mButton.setOnAction(this);
		sButton.setOnAction(this);
		
	}
	
	public SelectRolePage(Stage selectRolePageStage, int userID) {
		super();
		this.selectRolePageStage = selectRolePageStage;
		this.userID = userID;
		selectRolePageStage.setTitle("Role Page");
		
		init();
		addComponent();
		setMouseEvent();
		selectRolePageScene=scene;
	}

	public Scene getSelectRolePageScene() {
		return selectRolePageScene;
	}

	public void setSelectRolePageScene(Scene selectRolePageScene) {
		this.selectRolePageScene = selectRolePageScene;
	}

	@Override
	public void handle(ActionEvent e) {
		String query = "UPDATE msuser SET UserRole = ?\n WHERE UserID = ?";
		String role ="";
		
		if (e.getSource() == mButton) {
	        role = "Manager";
	    } else if (e.getSource() == sButton) {
	        role = "Shopper";
	    }
		
		if (!role.isEmpty()) {
	        try (PreparedStatement ps = connect.getConnection().prepareStatement(query)) {
	            ps.setString(1, role);
	            ps.setInt(2, userID); 
	            ps.executeUpdate(); 
	            
	            showConfirmation("Register Information", "Register success!", 
	                             "Please log in with your newly created account", selectRolePageStage);
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	    }
	} 
	
	public void showConfirmation(String title, String header, String content, Stage stage) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Register Information");
		alert.setHeaderText("Register success!");
		alert.setContentText("Please log in with your newly created account.");
		alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
            	LoginPage loginPage = new LoginPage(selectRolePageStage); 
				Scene loginPageScene = loginPage.getLoginPageScene();
				selectRolePageStage.setScene(loginPageScene);
            }
        });
	}
	
}
