package main;

import java.sql.PreparedStatement;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.Connect;

public class LoginPage implements EventHandler<ActionEvent> {
	private Stage loginPageStage;
	private Scene loginPageScene;
	private Connect connect = Connect.getInstance();
	
	Scene scene;
	BorderPane bp;
	GridPane gp;
	ImageView logo;
	Image gambar; 
	Line garis;
	Text text;
	Font font;
	TextField email;
	PasswordField pass;
	Label temail, tpass, newHere;
	Button btnlogin;
	Hyperlink here;
	HBox registerHere,cont;
	VBox form;
	
	public void init() {
		//LAYOUT AND COMPONENT
		bp = new BorderPane();
		scene = new Scene(bp, 1280, 720);
		gp = new GridPane();
				
		//LOGO
		logo = new ImageView();
		gambar = new Image(getClass().getResourceAsStream("logoGOGO.png"));
			
		//HEADER
		String login = "Login";
		text = new Text(30.0, 80.0, login);
				
		//FORM FIELDS
		email = new TextField();
		pass = new PasswordField();
		temail = new Label("Email");
		tpass = new Label("Password");
		
		//FOOTER
		btnlogin = new Button("LOGIN");
		newHere = new Label("Are you new Here? Register"); 
		here = new Hyperlink("Here");		
	}
	
	public void addComponent() {
		//HEADER
		logo.setImage(gambar);
	    logo.setFitWidth(200);
	    logo.setFitHeight(100);
	    logo.setPreserveRatio(true);
	    font = Font.font("Inter", FontWeight.BOLD, 36);
		text.setFont(font);
		garis= new Line(30,40,150,40);	
		
		//FOOTER
		btnlogin.setMaxWidth(Double.MAX_VALUE);
		btnlogin.setStyle("-fx-background-color: orange; -fx-text-fill: white; -fx-font-weight: bold;");
		
		//REGISTER LINK
		registerHere = new HBox(newHere,here);
		registerHere.setAlignment(Pos.CENTER);
		
		//FORM LAYOUT
		cont = new HBox(btnlogin);
		cont.setAlignment(Pos.CENTER);
        cont.setHgrow(btnlogin, Priority.ALWAYS);
        
		form = new VBox(10, text, garis, temail, email, tpass, pass, cont, registerHere);
		form.setPadding(new Insets(10,20,10,20));
		form.setStyle("-fx-background-color: White");
		
		gp.add(logo, 0, 0);
		gp.add(form, 0, 1);
		gp.setColumnSpan(cont,2);
		cont.setAlignment(Pos.CENTER);
		bp.setCenter(gp);
		bp.setStyle("-fx-background-color: #2d2d38; -fx-padding: 20; -fx-border-radius: 10; -fx-border-color: transparent;-fx-alignment: center;");
	}
	
	public void styling() {
		gp.setAlignment(Pos.CENTER);
		gp.setVgap(10);
		BorderPane.setMargin(gp, new Insets(10,5,10,5));
	}
	
	public void setMouseEvent() {
		btnlogin.setOnAction(this);
		here.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				RegisterPage registerPage = new RegisterPage(loginPageStage);
				Scene registerPageScene = registerPage.getRegisterPageScene();
				loginPageStage.setScene(registerPageScene);
				
			}
		});
	}
	
	public void setKeyEvent() {
	    EventHandler<KeyEvent> loginHandler = event -> {
	        if (event.getCode() == KeyCode.ENTER) {
	            btnlogin.fire(); 
	        }
	    };
	    // Add the key event handler to both email and password fields
	    email.setOnKeyPressed(loginHandler);
	    pass.setOnKeyPressed(loginHandler);
	}
	
	@Override
	public void handle(ActionEvent event) {
		try {
			if (email.getText().isBlank() || pass.getText().isBlank()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Invalid Login");
				alert.setHeaderText("Log in failed");
				alert.setContentText("please fill out all fields");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					event.consume();
					return;
				}
			};
			
			String query = "SELECT * FROM msuser " + "WHERE UserEmail = ?";
			PreparedStatement pst = connect.getConnection().prepareStatement(query);
			pst.setString(1, email.getText());
			connect.rs = pst.executeQuery();
			
			if (connect.rs.next()) {
				if (email.getText().equals(connect.rs.getString("UserEmail")) && pass.getText().equals(connect.rs.getString("UserPassword"))) {
					int userId = connect.rs.getInt("UserID");
					
					if (connect.rs.getString("UserRole").equals("Shopper")) {
						ShopperPage shopperPage = new ShopperPage(loginPageStage, userId);
						Scene shopperPageScene = shopperPage.getShopperPageScene();
						loginPageStage.setScene(shopperPageScene);
					}else if (connect.rs.getString("UserRole").equals("Manager")) {
						ManagerPage managerPage = new ManagerPage(loginPageStage);
						Scene managerPageScene = managerPage.getManagerPageScene();
						loginPageStage.setScene(managerPageScene);
					}
				}else {
					Alert alertCredential = new Alert(AlertType.ERROR);
					alertCredential.setTitle("Invalid Login");
					alertCredential.setHeaderText("Wrong Credential");
					alertCredential.setContentText("You entered a wrong email or password");
					Optional<ButtonType> resultCredential = alertCredential.showAndWait();
					if (resultCredential.get() == ButtonType.OK) {
						event.consume();
					}
				}
			}else {
				// Alert for email not found
				Alert alertEmail = new Alert(AlertType.ERROR);
				alertEmail.setTitle("Invalid Login");
				alertEmail.setHeaderText("Wrong Credential");
				alertEmail.setContentText("You entered a wrong email or password");
				Optional<ButtonType> resultEmail = alertEmail.showAndWait();
				if (resultEmail.get() == ButtonType.OK) {
					event.consume();
				}
			}
		} catch (Exception e) {
			Alert alertError = new Alert(AlertType.ERROR);
			alertError.setContentText(e.getMessage());
			Optional<ButtonType> errorBtn = alertError.showAndWait();
			if (errorBtn.get() == ButtonType.OK) {
				event.consume();
			}
		}
	}	

	public LoginPage(Stage loginPageStage) {
		super();
		this.loginPageStage = loginPageStage;
		loginPageStage.setTitle("Login");
		
		init();
		addComponent();
		styling();
		setMouseEvent();
		setKeyEvent();
		loginPageScene = scene;
	}

	public Scene getLoginPageScene() {
		return loginPageScene;
	}

	public void setLoginPageScene(Scene loginPageScene) {
		this.loginPageScene = loginPageScene;
	}

	
}
