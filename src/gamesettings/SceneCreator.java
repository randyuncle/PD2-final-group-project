package gamesettings;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import net.*;

/**
 * ��銝剖���4��嚗tartScene, GamePlayScene, PauseScene(press T), GameOverScene
 * ��隞亙��4�ethod
 * The SceneCreator creates scenes that will be used during the runtime of the
 * program. Think of scenes as different screens in the game. For example, the
 * scene of which we play the game in is called the "Main Game Scene" and the
 * scene where we click the start button is called the "Start Scene". This makes 
 * it much easier so there is less "Graphical" code in the GameManager and we can
 * better read the more "logical" code. In other words, there is less visual 
 * noise. 
 */
public class SceneCreator {


    /**
     * Creates the "Game Paused Scene". The game paused scene only occurs when 
     * the user presses the key "T". It will give the user the options to resume
     * game play or go to the main menu. 
     * 
     * @return  A scene containing two buttons and the text "Paused".
     */
    public static Scene createPauseMenu() {
        
        Text paused = new Text("Paused");
        paused.setFont(new Font(50));
        paused.setFill(Color.WHITE);
        
        // Resume button for when user wants to resume gameplay.
        Button resumeButton = new Button("Resume");
        resumeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                gamesettings.GameManager.resumeGame();
            }
        });

        // Main menu button for when user wants to go to the main menu.
        Button mainMenuButton = new Button("Main Menu");
        mainMenuButton.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent e) {
               gamesettings.GameManager.goToMainMenu();
           }
        });

        // Put the grid pane in the center of the scene and add the buttons.
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        
        gridPane.add(paused, 0, 0);
        GridPane.setHalignment(paused, HPos.CENTER);
        GridPane.setMargin(paused, new Insets(5, 7, 5, 7));
        gridPane.add(resumeButton, 0, 1);
        GridPane.setHalignment(resumeButton, HPos.CENTER);
        GridPane.setMargin(resumeButton, new Insets(5,7,5,7));
        gridPane.add(mainMenuButton, 0, 2);
        GridPane.setHalignment(mainMenuButton, HPos.CENTER);
        GridPane.setMargin(mainMenuButton, new Insets(5,7,5,7));
        
        gridPane.setStyle("-fx-background-image: url(\"/battleBackground.png\");");

        return new Scene(gridPane, RuntimeSettings.getWidth(), RuntimeSettings.getHeight());
    }
    
    /**
     * Creates a scene which is the Lobby of the game
     */
    public static Scene createLobbyScene() 
    {
    	 Text Lobby = new Text("Lobby\n");
         Lobby.setFont(new Font(50));
         Lobby.setFill(Color.BLACK);
         
         Button btn = new Button("Battle");
         btn.setFont(new Font(30));
         btn.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent event) {
                 gamesettings.GameManager.loadGame();
             }
         });
         
         Button btn1 = new Button("Player profile");
         btn1.setFont(new Font(30));
         btn1.setOnAction(new EventHandler<ActionEvent>()
         {
         	@Override
         	public void handle(ActionEvent event) 
         	{
         		gamesettings.GameManager.showPlayerProfile();
         	}
         });
         
         Button btn2 = new Button("Rule");
         btn2.setFont(new Font(30));
         btn2.setOnAction(new EventHandler<ActionEvent>()
         {
         	@Override
         	public void handle(ActionEvent event) 
         	{
         		gamesettings.GameManager.goToRule();
         	}
         });
         
         GridPane gridPane = new GridPane();
         gridPane.setAlignment(Pos.CENTER);
         
         gridPane.add(Lobby, 0, 0);
         GridPane.setHalignment(Lobby, HPos.CENTER);
         GridPane.setMargin(Lobby, new Insets(5,7,5,7));

         gridPane.add(btn1, 0, 1);
         GridPane.setHalignment(btn1, HPos.CENTER);
         GridPane.setMargin(btn1, new Insets(5,7,5,7));
         
         gridPane.add(btn, 0, 2);
         GridPane.setHalignment(btn, HPos.CENTER);
         GridPane.setMargin(btn, new Insets(5,7,5,7));
         
         gridPane.add(btn2, 0, 3);
         GridPane.setHalignment(btn2, HPos.CENTER);
         GridPane.setMargin(btn2, new Insets(5,7,5,7));
         
         gridPane.setStyle("-fx-background-image: url(\"/background.png\");");
         
         Scene scene = new Scene(gridPane, 1240, 586); 
         return scene;
    }
    
    /**
     * Creates a scene which tells the rules of play this game
     * @return
     */
    
    public static Scene createRuleScene() 
    {
    	Text rule = new Text("Rule");
        rule.setFont(new Font(50));
        rule.setFill(Color.BLACK);
        
        Text line1 = new Text("1、Battle : Press the 'Battle' button to start battle\n ");
        line1.setFont(new Font(30));
        line1.setFill(Color.BLACK);
        
        Text line2 = new Text("2、Keys to control characters : w (UP), s (DOWN), a (LEFT), d (RIGHT)\n");
        line2.setFont(new Font(30));
        line2.setFill(Color.BLACK);
        
        Text line3 = new Text("3、Attack : Click your mouse\n(p.s. the direction the character attack\ndepends on the direction of your mouse)\n");
        line3.setFont(new Font(30));
        line3.setTextAlignment(TextAlignment.CENTER);
        line3.setFill(Color.BLACK);
        
        Button back = new Button("back");
        back.setFont(new Font(30));
        back.setOnAction(new EventHandler<ActionEvent>()
        {
        	@Override
        	public void handle(ActionEvent event) 
        	{
        		gamesettings.GameManager.goToLobby();
        	}
        });
        
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        
        gridPane.add(rule, 0, 0);
        GridPane.setHalignment(rule, HPos.CENTER);
        GridPane.setMargin(rule, new Insets(5,7,5,7));
        
        gridPane.add(line1, 0, 1);
        GridPane.setHalignment(line1, HPos.CENTER);
        GridPane.setMargin(line1, new Insets(5,7,5,7));
        
        gridPane.add(line2, 0, 2);
        GridPane.setHalignment(line2, HPos.CENTER);
        GridPane.setMargin(line2, new Insets(5,7,5,7));
        
        gridPane.add(line3, 0, 3);
        GridPane.setHalignment(line3, HPos.CENTER);
        GridPane.setMargin(line3, new Insets(5,7,5,7));
        
        gridPane.add(back, 0, 4);
        GridPane.setHalignment(back, HPos.CENTER);
        GridPane.setMargin(rule, new Insets(5,7,5,7));
        
        gridPane.setStyle("-fx-background-image: url(\"/backgroundProRu.png\");");
        
        Scene scene = new Scene(gridPane, 1000, 600); 
		return scene;
    	
    }


    /**
     * Creates a scene with functions of Login and Apply Account 
     * @return 
     */
    public static Scene createStartScene() {

        Text Start = new Text("Rescue the World");
        Start.setFont(new Font(50));
        Start.setFill(Color.BLACK);
        
        TextField name = new TextField();
        name.setFont(new Font(24));
        name.setPromptText("name");
        
        TextField password = new TextField();
        password.setFont(new Font(24));
        password.setPromptText("password");
        
        Button btn = new Button("Login");
        btn.setFont(new Font(30));
        btn.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
            	ClientServer.NAME = name.getText();
            	ClientServer.PASSWORD = password.getText();
            	
            	MainClientServer mcs = new MainClientServer("Login");
            	mcs.run();
            	
                gamesettings.GameManager.goToLobby();
            }
        });
        
        Button btn1 = new Button("Apply");
        btn1.setFont(new Font(30));
        btn1.setOnAction(new EventHandler<ActionEvent>()
        {
        	@Override
        	public void handle(ActionEvent event) 
        	{
        		gamesettings.GameManager.goToApplyAccount();
        	}
        });
        
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        
        gridPane.add(Start, 0, 0);
        GridPane.setHalignment(Start, HPos.CENTER);
        GridPane.setMargin(Start, new Insets(5,7,5,7));

        gridPane.add(name, 0, 1);
        GridPane.setHalignment(name, HPos.CENTER);
        GridPane.setMargin(name, new Insets(5,7,5,7));   
        
        gridPane.add(password, 0, 2);
        GridPane.setHalignment(password, HPos.CENTER);
        GridPane.setMargin(password, new Insets(5,7,5,7));
        
        gridPane.add(btn, 0, 3);
        GridPane.setHalignment(btn, HPos.CENTER);
        GridPane.setMargin(btn, new Insets(5,7,5,7));
        
        gridPane.add(btn1, 0, 4);
        GridPane.setHalignment(btn1, HPos.CENTER);
        GridPane.setMargin(btn1, new Insets(5,7,5,7));
        
        gridPane.setStyle("-fx-background-image: url(\"/background.png\");");
        
        Scene scene = new Scene(gridPane, 1240, 586); 

        return scene;
    }
    
    /**
     * Creates a scene for applying a account
     */
    public static Scene createApplyAccountScene() {

        Text apply = new Text("Get an Account!");
        apply.setFont(new Font(50));
        apply.setFill(Color.BLACK);
        
        TextField name = new TextField();
        name.setFont(new Font(24));
        name.setPromptText("name");
        
        TextField password = new TextField();
        password.setFont(new Font(24));
        password.setPromptText("password");
        
        Button btn = new Button("OK");
        btn.setFont(new Font(30));
        btn.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
            	ClientServer.NAME = name.getText();
            	ClientServer.PASSWORD = password.getText();
            	
            	MainClientServer mcs = new MainClientServer("Set Account");
            	mcs.run();
            	
                gamesettings.GameManager.goToStart();
            }
        });
        
        Button btn1 = new Button("back");
        btn1.setFont(new Font(30));
        btn1.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                gamesettings.GameManager.goToStart();
            }
        });
        
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        
        gridPane.add(apply, 0, 0);
        GridPane.setHalignment(apply, HPos.CENTER);
        GridPane.setMargin(apply, new Insets(5,7,5,7));

        gridPane.add(name, 0, 1);
        GridPane.setHalignment(name, HPos.CENTER);
        GridPane.setMargin(name, new Insets(5,7,5,7));   
        
        gridPane.add(password, 0, 2);
        GridPane.setHalignment(password, HPos.CENTER);
        GridPane.setMargin(password, new Insets(5,7,5,7));
        
        gridPane.add(btn, 0, 3);
        GridPane.setHalignment(btn, HPos.CENTER);
        GridPane.setMargin(btn, new Insets(5,7,5,7));
        
        gridPane.add(btn1, 0, 4);
        GridPane.setHalignment(btn1, HPos.CENTER);
        GridPane.setMargin(btn1, new Insets(5,7,5,7));
        
        gridPane.setStyle("-fx-background-image: url(\"/background.png\");");
        
        Scene scene = new Scene(gridPane, 1240, 586); 

        return scene;
    }
    
    /**
     * Creates the main game play window (scene). It will return an object array
     * containing the following elements in the following order:
     * 1. The Scene that needs to be set to the primaryStage (Stage).
     * 2. The Pane that holds the players and enemies. (Pane��layout pane)
     * 3. The Label that contains the player's health.
     *
     * @return      An object array containing the Scene to be set, the Pane that
     *              holds the players and enemies, and the Label that displays
     *              the player's health.
     */
    public static Object[] createGamePlayScene() {

        HBox statusBox = new HBox();
        statusBox.setPadding(new Insets(15, 12, 15, 12));
        statusBox.setSpacing(10);
        statusBox.setStyle("-fx-background-color: #FFFFFF;");        

        Label stats = new Label("Health: ");
        stats.setPrefSize(400, 25);
        stats.setTextFill(Color.WHITE);
        statusBox.getChildren().add(stats);        

        Pane playerField = new Pane();

        BorderPane borderPane = new BorderPane();
        borderPane.setBottom(stats);
        borderPane.setCenter(playerField);
        borderPane.setStyle("-fx-background-image: url(\"/battleBackground.png\");");

        Scene scene = new Scene(borderPane, RuntimeSettings.getWidth(), RuntimeSettings.getHeight(), gamesettings.Settings.BACKGROUND);

        return new Object[]{scene, playerField, stats};
    }


    /**
     * Creates a scene that contains the text "Game Over", and two buttons labeled
     * restart and quit. The restart button will restart the game play. The quit
     * button will exit to the home screen.
     *
     * @return  A scene containing the text "Game Over", and two buttons labeled
     *          restart and quit.
     */
    public static Scene createGameOverScene() {
    	int killNumber = GameManager.amountKilled;
    	
        Text gameOver = new Text("Game Over");
        gameOver.setFont(new Font(50));
        gameOver.setFill(Color.BLACK);
        
        Text point = new Text("Congradulation ! You get " + killNumber + " points !");
        point.setFont(new Font(40));
        point.setFill(Color.BLACK);

        Button restartButton = new Button("Restart");
        restartButton.setFont(new Font(30));
        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	if(ClientServer.MAX_POINT < killNumber) 
            	{
            		ClientServer.MAX_POINT = killNumber;
            		
            		MainClientServer mcs = new MainClientServer("Upload Grades");
            		mcs.run();
            	}
            	
                gamesettings.GameManager.restartGame();
            }
        });

        Button mainMenuButton = new Button("Back to Lobby");
        mainMenuButton.setFont(new Font(30));
        mainMenuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	if(ClientServer.MAX_POINT < killNumber) 
            	{
            		ClientServer.MAX_POINT = killNumber;
            		
            		MainClientServer mcs = new MainClientServer("Upload Grades");
            		mcs.run();
            	}
            	
                gamesettings.GameManager.goToMainMenu();
            }
        });


        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(gameOver, 0, 0);
        GridPane.setHalignment(gameOver, HPos.CENTER);
        GridPane.setMargin(gameOver, new Insets(5,7,5,7));
        
        gridPane.add(point, 0, 1);
        GridPane.setHalignment(point, HPos.CENTER);
        GridPane.setMargin(point, new Insets(5,7,5,7));
        
        gridPane.add(restartButton, 0, 2);
        GridPane.setHalignment(restartButton, HPos.CENTER);
        GridPane.setMargin(restartButton, new Insets(5,7,5,7));
        
        gridPane.add(mainMenuButton, 0, 3);
        GridPane.setHalignment(mainMenuButton, HPos.CENTER);
        GridPane.setMargin(mainMenuButton, new Insets(5,7,5,7));

        gridPane.setStyle("-fx-background-image: url(\"/background.png\");");
        
        return new Scene(gridPane, 1240, 586);
    }
    
    /**
     * Creates a scene that contains player's profile, including history maximum points, name, and id
     */
    public static Scene createPlayerProfileScene() 
    {
    	String name = ClientServer.NAME;
    	int points = ClientServer.MAX_POINT;
    	int id = ClientServer.ID;
    	
    	Text player = new Text("Hello! " + name);
    	player.setFont(new Font(40));
    	player.setFill(Color.BLACK);
    	
    	Text showPoints = new Text("Your highest points in history : " + points);
    	showPoints.setFont(new Font(40));
    	player.setFill(Color.BLACK);
    	
    	Text showId = new Text("Your ID : " + id);
    	showId.setFont(new Font(40));
    	showId.setFill(Color.BLACK);
    	
    	Button mainLobbyButton = new Button("back");
    	mainLobbyButton.setFont(new Font(30));
    	mainLobbyButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent e) {
                 gamesettings.GameManager.goToMainMenu();
             }
         });
    	
    	GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
    	
    	gridPane.add(player, 0, 0);
        GridPane.setHalignment(player, HPos.CENTER);
        GridPane.setMargin(player, new Insets(5,7,5,7));
        
        gridPane.add(showId, 0, 1);
        GridPane.setHalignment(showId, HPos.CENTER);
        GridPane.setMargin(showId, new Insets(5,7,5,7));
        
        gridPane.add(showPoints, 0, 2);
        GridPane.setHalignment(showPoints, HPos.CENTER);
        GridPane.setMargin(showPoints, new Insets(5,7,5,7));
        
        gridPane.add(mainLobbyButton, 0, 3);
        GridPane.setHalignment(mainLobbyButton, HPos.CENTER);
        GridPane.setMargin(mainLobbyButton, new Insets(5,7,5,7));
    	
        gridPane.setStyle("-fx-background-image: url(\"/backgroundProRu.png\");");
        
        Scene scene = new Scene(gridPane, 1000, 600); 

        return scene;
    	
    }
}

