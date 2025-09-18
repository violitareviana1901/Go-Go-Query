package main;

import java.sql.Date;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;
import java.util.Vector;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import util.Connect;

public class RegisterPage implements EventHandler<ActionEvent> {
	private Stage registerPageStage;
	private Scene registerPageScene;
	private Connect connect = Connect.getInstance();
	
	Scene scene;
	BorderPane bp ;
	GridPane gp;
	VBox vb;
	Label register;
	Line batas;
	Label lbemail;
	Label lnpass;
	Label lbconfirm;
	Label lbdob;
	TextField email;
	PasswordField pass;
	PasswordField confirmpass;
	TextField tfemail;
	DatePicker date;
	RadioButton rbmale;
	RadioButton rbfemale;
	ToggleGroup gender;
	FlowPane genderPane;
	Button btnregist;
	TilePane tile;
	Hyperlink terms;
	CheckBox cb;
	Label newHere;
	Hyperlink here;
	
	public void init() {
		bp = new BorderPane();
		gp = new GridPane();
		vb = new VBox();
		
		//HEADER
		register = new Label("Register");
		batas = new Line(30, 40, 200, 40);
		
		//FORM LAYOUT
		lbemail = new Label("Email");
		lnpass = new Label("Password");
		lbconfirm = new Label("Confirm Password");
		lbdob = new Label("Date of Birth");
		email = new TextField();
		pass = new PasswordField();
		confirmpass = new PasswordField();
		tfemail = new TextField();
		date = new DatePicker();
		
		//GENDER
		rbmale = new RadioButton("Male");
		rbfemale = new RadioButton("Female");
		gender = new ToggleGroup();
		genderPane = new FlowPane(Orientation.HORIZONTAL, 5, 5, rbmale, rbfemale);
		
		//FOOTER
		btnregist = new Button("Register");
		tile = new TilePane();
		terms = new Hyperlink("Terms and Conditions");
		cb = new CheckBox("I accept the");
		newHere = new Label("Already have an account? Sign in");
		here = new Hyperlink("Here");
	}

	public void addComponent() {
		scene = new Scene(bp, 1280, 720);
		//LOGO
		Image image = new Image(getClass().getResourceAsStream("logoGOGO.png"));
		ImageView img = new ImageView(image);
		img.setFitHeight(110);
		img.setFitWidth(200);
		
		//HEADER
		register.setFont(new Font("Inter", 30));
		register.setStyle("-fx-font-weight: bold");
		gp.add(register, 0, 0);
		gp.add(batas, 0, 1);
		
		//FORM LAYOUT
		gp.add(lbemail, 0, 2);
		gp.add(tfemail, 0, 3);
		gp.add(lnpass, 0, 4);
		gp.add(pass, 0, 5);
		gp.add(lbconfirm, 0, 6);
		gp.add(confirmpass, 0, 7);
		gp.add(lbdob, 0, 8);
		gp.add(tile, 0, 9);
		date.getEditor().setDisable(true);
		tile.getChildren().add(date);
		
		//GENDER
		gender.getToggles().addAll(rbmale, rbfemale);
		genderPane.setMaxWidth(Double.MAX_VALUE);
		genderPane.setHgap(5);
		gp.setHgrow(genderPane, Priority.ALWAYS);
		gp.add(genderPane, 0, 10);
		
		//CHECKBOX
		HBox cekbox = new HBox(cb, terms);
		cekbox.setAlignment(Pos.CENTER);
		gp.add(cekbox, 0, 11);
		
		//BUTTON REGIST
		btnregist.setStyle("-fx-background-color: orange; -fx-text-fill: white; -fx-font-weight: bold;");
		btnregist.setAlignment(Pos.CENTER);
		btnregist.setMaxWidth(Double.MAX_VALUE);
		gp.add(btnregist, 0, 12);
		
		//FOOTER
		HBox registerHere = new HBox(newHere, here);
		registerHere.setAlignment(Pos.CENTER);
		gp.add(registerHere, 0, 13);
		
		vb= new VBox(img,gp);
		vb.setAlignment(Pos.CENTER);
		bp.setCenter(vb);
		bp.setAlignment(vb, Pos.CENTER);
		
		scene.setOnKeyPressed(event -> {
	        if (event.getCode() == KeyCode.ENTER) {
	            btnregist.fire(); // Simulate button click
	        }
	    });
		
		here.setOnAction(event->{
			Main loginPage = new Main();
			Stage stage = (Stage)bp.getScene().getWindow();
			try {
				loginPage.start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	public void setMouseEvent() {
		btnregist.setOnAction(this);
		here.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				LoginPage loginPage = new LoginPage(registerPageStage); 
				Scene loginPageScene = loginPage.getLoginPageScene();
				registerPageStage.setScene(loginPageScene);
			}
		});
	}
	

	public void styling() {
		gp.setAlignment(Pos.CENTER);
		gp.setVgap(10);
		gp.setPadding(new Insets(10, 20, 10, 20));
		gp.setStyle("-fx-background-color: white");
		gp.setMaxWidth(250);
		gp.setMargin(gp, new Insets(10, 5, 10, 5));
		bp.setStyle("-fx-background-color: #2d2d38; -fx-padding: 20; -fx-border-radius: 10; -fx-border-color: transparent;\r\n");
		bp.setCenter(vb);

	}
	
	private boolean isEmailTaken(String emailInput) {
	    String query = "SELECT COUNT(*) FROM msuser WHERE UserEmail = ?";
	    try (PreparedStatement ps = connect.getConnection().prepareStatement(query)) {
	        ps.setString(1, emailInput);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next() && rs.getInt(1) > 0) {
	                return true; // Email already exists
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false; // Email does not exist
	}

	@Override
	public void handle(ActionEvent event) {
		boolean isValid = true;
		LocalDate dobInput = date.getValue();
		String emailInput = tfemail.getText();
		String passwordInput = pass.getText();
		String genderInput = rbmale.isSelected()? "Male" : "Female";
		
		//CHECK IF EMAIL IS EMPTY
		if (tfemail.getText().isEmpty()) {
			isValid = false;
			Alert message = new Alert(AlertType.ERROR);
			message.setTitle("Register Failed");
			message.setHeaderText("Register Error");
			message.setContentText("Email must be filled");
			Optional<ButtonType> error = message.showAndWait();
			if (error.isPresent() &&error.get() == ButtonType.OK) {
				event.consume();
				return;
			}
		}
			
		//CHECK IF EMAIL ENDS WITH @gomail.com
		else if (!(tfemail.getText().endsWith("@gomail.com"))) {
			isValid = false;
			Alert message1 = new Alert(AlertType.ERROR);
			message1.setTitle("Register Failed");
			message1.setHeaderText("Register Error");
			message1.setContentText("Email must ends with @gomail.com");
			Optional<ButtonType> error1 = message1.showAndWait();
			if (error1.isPresent() &&error1.get() == ButtonType.OK) {
				event.consume();
				return;
			}
		}
			
		//CHECK EMAIL CONTAINS special characters, except for ‘@’, ‘_’, or ‘.’.
		String sym = "@_.";
		for (int i = 0; i < tfemail.getText().length(); i++) {
			char c = tfemail.getText().charAt(i);
			
			// Jika karakter bukan huruf, angka, atau simbol yang diperbolehkan
			if (!(Character.isLetterOrDigit(c) || Character.isDigit(c) || sym.indexOf(c) != -1)) {
				isValid = false;
				
				// Tampilkan pesan error
				Alert message1 = new Alert(Alert.AlertType.ERROR);
				message1.setTitle("Register Failed");
				message1.setHeaderText("Register Error");
				message1.setContentText("no special character is allowed other than '@', '_', or '.'");
				message1.showAndWait();
				event.consume(); // Mencegah aksi lebih lanjut
				return; // Balik loop jika karakter tidak valid ditemukan
			}
		}
		
		//CHECK EMAIL ALREADY TAKEN 
		if (isEmailTaken(emailInput)) {
			isValid = false;
			Alert message = new Alert(AlertType.ERROR);
			message.setTitle("Register Failed");
			message.setHeaderText("Register Error");
			message.setContentText("Email is already taken. Please use another address.");
			Optional<ButtonType> error = message.showAndWait();
			if (error.isPresent() && error.get() == ButtonType.OK) {
				event.consume();
				return;
			}
		}

		//CHECK IF PASSWORD IS EMPTY
		if (pass.getText().isEmpty()) {
			isValid = false;
			Alert message = new Alert(AlertType.ERROR);
			message.setTitle("Register Failed");
			message.setHeaderText("Register Error");
			message.setContentText("Password must be filled");
			Optional<ButtonType> error = message.showAndWait();
			if (error.isPresent() && error.get() == ButtonType.OK) {
				event.consume();
				return;
			}
		}
		
		
		//CHECK PASSWORD IS ALPHANUMERIC
		boolean letter = false;
		boolean digit = false;
		boolean symbol = false;
		
		for (char ch : pass.getText().toCharArray()) {
			
			if (Character.isLetter(ch)) {
				letter = true;
			} else if (Character.isDigit(ch)) {
				digit = true;
			} else if (ch == ' ') {
				letter = true;
			} else {
				symbol = true;
			}
		}
		if (!(letter == true && digit == true && symbol == false)) {
			isValid = false;
			Alert message1 = new Alert(AlertType.ERROR);
			message1.setTitle("Register Failed");
			message1.setHeaderText("Register Error");
			message1.setContentText("Password must be alphanumberic");
			Optional<ButtonType> error1 = message1.showAndWait();
			if (error1.isPresent() &&error1.get() == ButtonType.OK) {
				event.consume();
				return;
			}
			
		}
			
		//CHECK IF CONFIRM PASSWORD IS EMPTY
		if (confirmpass.getText().isEmpty()) {
			isValid = false;
			Alert message = new Alert(AlertType.ERROR);
			message.setTitle("Register Failed");
			message.setHeaderText("Register Error");
			message.setContentText("Confirm password must be filled");
			Optional<ButtonType> error = message.showAndWait();
			if (error.isPresent() && error.get() == ButtonType.OK) {
				event.consume();
				return;
			}
		}
			
		//CHECK IF PASSWORDS MATCH
		if (!(pass.getText().equals(confirmpass.getText()))) {
			isValid = false;
			Alert message1 = new Alert(AlertType.ERROR);
			message1.setTitle("Register Failed");
			message1.setHeaderText("Register Error");
			message1.setContentText("Password don't match");
			Optional<ButtonType> error1 = message1.showAndWait();
			if (error1.isPresent() && error1.get() == ButtonType.OK) {
				event.consume();
				return;
			}
		}
			
		//CHECK IF DATE OF BIRTH IS EMPTY
		if (date.getValue() == null) {
			isValid = false;
			Alert message = new Alert(AlertType.ERROR);
			message.setTitle("Register Failed");
			message.setHeaderText("Register Error");
			message.setContentText("Please select date of birth");
			Optional<ButtonType> error = message.showAndWait();
			if (error.isPresent() && error.get() == ButtonType.OK) {
				event.consume();
				return;
			}
		}
			
		//CHECK IF AGE IS ABOVE 17
		LocalDate selectedDate = date.getValue();
		LocalDate today = LocalDate.now();
		int age = Period.between(selectedDate, today).getYears();
		if (age <= 17)  {
			isValid=false;
			Alert message = new Alert(AlertType.ERROR);
			message.setTitle("Register Failed");
			message.setHeaderText("Register Error");
			message.setContentText("User must be at least 17 years old");
			Optional<ButtonType> error = message.showAndWait();
			if (error.isPresent() && error.get() == ButtonType.OK) {
				event.consume();
				return;
			}
		}
			
		//CHECK IF GENDER IS SELECTED
		if (gender.getSelectedToggle() == null) {
			isValid = false;
			Alert message = new Alert(AlertType.ERROR);
			message.setTitle("Register Failed");
			message.setContentText("Select your gender");
			Optional<ButtonType> error = message.showAndWait();
			if (error.isPresent() && error.get() == ButtonType.OK) {
				event.consume();
				return;
			}
		}
			
		//CHECK IF TERMS AND CONDITIONS CHECKBOX IS SELECTED
		if (!cb.isSelected()) {
			isValid = false;
			Alert message = new Alert(AlertType.ERROR);
			message.setTitle("Register Failed");
			message.setHeaderText("Register Error");
			message.setContentText("You must agree to terms and conditions");
			Optional<ButtonType> error = message.showAndWait();
			if (error.isPresent() && error.get() == ButtonType.OK) {
				event.consume();
				return;
			}
		}
			
		if (isValid) {
			insertData(dobInput, emailInput, passwordInput, genderInput);
		}
	}
	
	private void insertData(LocalDate dobInput, String emailInput, String passwordInput, String genderInput) {
		String query = "INSERT INTO msuser (UserDOB, UserEmail, UserPassword, UserRole, UserGender) VALUES (?, ?, ?, ?, ?)";
	    
	    try (PreparedStatement ps = connect.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
	        ps.setDate(1, Date.valueOf(dobInput));
	        ps.setString(2, emailInput);
	        ps.setString(3, passwordInput);
	        ps.setString(4, "user");
	        ps.setString(5, genderInput);
	        
	        ps.executeUpdate();
	        
	        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                int userID = generatedKeys.getInt(1);
	                
	                // Navigate to the next page
	                SelectRolePage selectRolePage = new SelectRolePage(registerPageStage, userID);
	                Scene selectRolePageScene = selectRolePage.getSelectRolePageScene();
	                registerPageStage.setScene(selectRolePageScene);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public RegisterPage(Stage registerPageStage) {
		super();
		this.registerPageStage = registerPageStage;
		registerPageStage.setTitle("Register");
		
		init();
		addComponent();
		styling();
		setMouseEvent();
		registerPageScene = scene;
	}

	public Scene getRegisterPageScene() {
		return registerPageScene;
	}

	public void setRegisterPageScene(Scene registerPageScene) {
		this.registerPageScene = registerPageScene;
	}
}
