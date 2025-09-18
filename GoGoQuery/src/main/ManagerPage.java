package main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ManagerPage implements EventHandler<ActionEvent> {
	private Stage managerPageStage;
	private Scene managerPageScene;
	
	BorderPane bp;
	Scene scene;
	
	//MENUBAR
	MenuBar menubar;
	Menu menu;
	Label welcome;
	MenuItem addItem;
	MenuItem mngQueue;
	MenuItem logout;
	
	public void init() {
		bp = new BorderPane();
		scene = new Scene(bp, 1280, 720);
		menubar = new MenuBar();
		menu = new Menu("Menu");
		addItem = new MenuItem("Add Item");
		mngQueue = new MenuItem("Manage Queue");
		logout = new MenuItem("Log Out");
		welcome = new Label("Welcome to GoGoQuery Manager 2.0");
	}
	
	public void addComponent() {
		bp.setTop(menubar);
		menubar.getMenus().add(menu);
		menu.getItems().addAll(addItem, mngQueue, logout);
		welcome.setFont(Font.font("Inter",FontWeight.MEDIUM, 24));
		bp.setCenter(welcome);
	}
	
	public void setEventHandler() {
		addItem.setOnAction(this);
		mngQueue.setOnAction(this);
		logout.setOnAction(this);
	}


	@Override
	public void handle(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == addItem) {
			AddItemPage addItemPage = new AddItemPage(managerPageStage); 
			Scene addItemPageScene = addItemPage.getAddItemPageScene();
			managerPageStage.setScene(addItemPageScene);
		}else if (e.getSource() == mngQueue) {
			ManagerQueuePage managerqueue = new ManagerQueuePage(managerPageStage); 
			Scene queueManagerScene = managerqueue.getManagerQueuePageScene();
			managerPageStage.setScene(queueManagerScene);
		}else if (e.getSource() == logout) {
			LoginPage loginPage = new LoginPage(managerPageStage); 
			Scene loginPagescene = loginPage.getLoginPageScene();
			managerPageStage.setScene(loginPagescene);
		}
	}

	public ManagerPage(Stage managerPageStage) {
		super();
		this.managerPageStage = managerPageStage;
		managerPageStage.setTitle("GoGoQuery Manager 2.0");
		
		init();
		addComponent();
		setEventHandler();
		managerPageScene = scene;
	}
	public Scene getManagerPageScene() {
		return managerPageScene;
	}
	public void setManagerPageScene(Scene managerPageScene) {
		this.managerPageScene = managerPageScene;
	}
	
}
