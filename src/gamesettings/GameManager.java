package gamesettings;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import players.Enemy;
import players.MainPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The GameManager class will manage the Major aspects of the game. These include
 * 產生Main Player(.spawnPlayer())、產生敵人(.spawnEnemies())、updating locations, removing dead bodies,
 * and handling game state changes.Game state changes include pause, resume, stop, and quit.
 */
public class GameManager {
    /** 
     * The main player in the game. The user will be controlling this player. 
     * See the MainPlayer and Player classes for more detail. 
     * 
     * @see players.MainPlayer
     * @see players.Player
     */
    public static MainPlayer mainPlayer;

    /** Contains the enemies that are alive. */
    public static List<Enemy> enemies;

    /** Contains the dead enemies. */
    public static List<Enemy> deadEnemies;

    /** Contains a value determining whether game play is active. */
    private static boolean gameActive = false;

    /** The Stage that holds the Scenes displayed to the user. */
    private static Stage primaryStage;

    /** 
     * Displays the current health of the main player and the amount of enemies 
     * killed.
     */
    private static Label gameStats;

    /** The Pane the main player and enemies are added to. */
    private static Pane playerField;

    /** The scene displayed when game play starts. */
    private static Scene gameplayScene;

    /** The Input class is the input handler for the main player. */
    private static gamesettings.Input input;
    

    /** 
     * The animation timer that will update main player and enemy locations. It
     * will also decide when to clear the dead bodies from the screen (every 
     * 10000 milliseconds) and when to move the enemies. 
     */
    private static AnimationTimer mainUpdateTimer;
    
    
    /** The animation timer that will control when enemies spawn(����). */
    private static AnimationTimer enemySpawnTimer;

    
    /** The amount of enemies killed. */
    public static int amountKilled = 0;

    
    /**
     * Private constructor so this class can't be instantiated.
     */
    private GameManager(){}


    /**
     * Sets the Stage to use during the application's runtime. The primaryStage
     * is used to display Scenes to the user.
     * 
     * @param stage     The primary stage.
     */
    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }


    /**
     * Sets the scene to the main game scene. It will also create the Animation 
     * Timer used for updating enemy and player locations, and will instantiate
     * the lists used to keep track of the currently, alive and dead, enemies.
     */
    public static void loadGame() {
        if(primaryStage == null)
            throw new NullPointerException("Primary Stage is Null.");

        RuntimeSettings.loadRuntimeSettings(1240,694); //根據 battleBackground.png 1240*694
        
        Object[] graphicalComponents = SceneCreator.createGamePlayScene();

        gameplayScene = (Scene)graphicalComponents[0];
        playerField = (Pane)graphicalComponents[1];
        gameStats = (Label)graphicalComponents[2];

        input = new gamesettings.Input(gameplayScene, playerField);

        enemies = new ArrayList<>();
        deadEnemies = new ArrayList<>();

        mainUpdateTimer = new AnimationTimer() {

            long previousTime = 0;  // Used for updating enemy location.
            long previousTime2 = 0; // Used for cleaning the dead enemy bodies.

            /*
                This will control all animations pertaining to(撅祆) the main player and
                to the enemies.
            */
            @Override
            public void handle(long now) {                
                long time = now / 1000000;  // Manually handle time.

                if(mainPlayer.getHealth() <= 0) {
                    stopGame();
                }

                // Update the enemies and the player stats.
                if(time - previousTime >= Settings.REFRESH_RATE) {
                    List<Enemy> temp = new ArrayList<>(enemies);
                    for (Enemy e : temp) {
                        e.attackPlayer(now);
                    }

                    gameStats.setText("Health:\t"+mainPlayer.getHealth()
                                        + "\tAmount Killed:\t"+amountKilled);
                    previousTime = time;
                }

                // Remove all dead enemies from the game play field.
                if(time - previousTime2 >= 10000) { //10000ms = 10s
                    List<Enemy> temp = new ArrayList<>(deadEnemies);
                    for (Enemy enemy : temp) {
                        playerField.getChildren().remove(enemy.getImageView());
                        deadEnemies.remove(enemy);
                    }
                    previousTime2 = time;
                }
                // Update the main player.
                mainPlayer.changeValues();
                mainPlayer.move();
                mainPlayer.updateUI();
            }
        };
        startNewGame();
    }

    
    /**
     * Used to setup a new game. This should always be called when using loadGame() 
     * is too much, like restarting the game after the main player died. 
     */
    private static void startNewGame() {
        primaryStage.setScene(gameplayScene);
        
        playerField.getChildren().clear();
        enemies.clear();
        
        spawnPlayer();
        spawnEnemies();

        input.resetSettings();
        input.addListeners();

        amountKilled = 0;
        gameActive = true;

        mainUpdateTimer.start();
    }


    /**
     * Prevents the game scene from being updated and sets the scene to the 
     * "Game Over" scene. 
     */
    public static void stopGame() {
        gameActive = false;
        input.removeListeners();
        mainUpdateTimer.stop();
        enemySpawnTimer.stop();       
        primaryStage.setScene(SceneCreator.createGameOverScene());
    }

    /**
     * Starts a new game.
     */
    public static void restartGame() {
        gameActive = true;
        input.addListeners();       
        startNewGame();
    }


    /**
     * Pauses the game.
     */
    public static void pauseGame() {
        gameActive = false;
        input.removeListeners();
        mainUpdateTimer.stop();
        enemySpawnTimer.stop();
        primaryStage.setScene(SceneCreator.createPauseMenu());
        System.out.println("GameManager:\tGame Paused.");
    }


    /**
     * Resumes the game.
     */
    public static void resumeGame() {
        gameActive = true;
        primaryStage.setScene(gameplayScene);
        input.addListeners();
        mainUpdateTimer.start();
        enemySpawnTimer.start();
        System.out.println("GameManager:\tGame Resumed.");
    }


    /**
     * Sets the Scene to the "Main Menu" scene.
     */
    public static void goToMainMenu() {
        primaryStage.setScene(SceneCreator.createLobbyScene());
    }
    
    /**
     * Sets the path to start scene
     */
    public static void goToStart() 
    {
    	primaryStage.setScene(SceneCreator.createStartScene());
    }
    
    /**
     * Sets the path to Lobby scene
     */
    public static void goToLobby() 
    {
    	primaryStage.setScene(SceneCreator.createLobbyScene());
    }
    
    /**
     * Sets the path to Rule scene
     */
    public static void goToRule()
    {
    	primaryStage.setScene(SceneCreator.createRuleScene());
    }
    
    /**
     * Sets the path to apply account scene
     */
    public static void goToApplyAccount()
    {
    	primaryStage.setScene(SceneCreator.createApplyAccountScene());
    }
    
    /**
     * Sets the player profile scene
     */
    public static void showPlayerProfile() 
    {
    	primaryStage.setScene(SceneCreator.createPlayerProfileScene());
    }


    /**
     * Spawns the main player at a random place in the game play scene. 
     */
    private static void spawnPlayer() {
        Random rand = new Random();
        mainPlayer = new MainPlayer(playerField,
                                    Settings.getMainPlayerImage(),
                                    rand.nextInt(RuntimeSettings.getMaxPlayerPositionX()),
                                    rand.nextInt(RuntimeSettings.getMaxPlayerPositionY()),
                                    0,0,0,0, input);
        playerField.getChildren().add(mainPlayer.getImageView());   
    }


    /**
     * Continuously spawns a random amount of enemies (between 1 and 20 inclusive)
     * at random places on the game play scene. There are only three types of 
     * enemy that can be spawned. See the Enemy class for more information on the
     * types. 
     * @see players.Enemy
     */
    private static void spawnEnemies() {
        Random rand = new Random();

        enemySpawnTimer = new AnimationTimer() {
            
            long previousTime = 0;

            @Override
            public void handle(long now) {
                long time = now / 1000000;
                
                if(time - previousTime >= 5000) {
                    for (int i = 0; i < rand.nextInt(10) + 5; i++) {
                        Enemy e = null;

                        switch(rand.nextInt(3)) {

                            case 0: //���鈭粹���1(bot1.png)嚗peed=2,damage=1
                                e = new Enemy(playerField,
                                        Settings.getEnemyImage(1),
                                        rand.nextInt(100)+600,
                                        rand.nextInt(100)+100,
                                        0, 0, 0, 0, 3, 4);
                                break;

                            case 1: //���鈭粹���2(bot2.png)嚗peed=3,damage=2
                                 e = new Enemy(playerField,
                                        Settings.getEnemyImage(2),
                                        rand.nextInt(100)+800,
                                        rand.nextInt(100)+300,
                                        0, 0, 0, 0, 8, 1);
                                break;

                            case 2: //���鈭粹���3(bot3.png)嚗peed=5,damage=4
                                e = new Enemy(playerField,
                                        Settings.getEnemyImage(3),
                                        rand.nextInt(100)+100,
                                        rand.nextInt(100)+150,
                                        0, 0, 0, 0, 4, 5);
                                break;
                                
                            default:
                            	 e = new Enemy(playerField,
                                         Settings.getEnemyImage(1),
                                         rand.nextInt(100)+600,
                                         rand.nextInt(100)+100,
                                         0, 0, 0, 0, 3, 4);
                                 break;

                        }
                        enemies.add(e);
                        e.changeLocation();
                        playerField.getChildren().add(e.getImageView());
                    }
                    previousTime = time;
                }
            }
        };

        enemySpawnTimer.start();
    }


    /**
     * Returns true when the game is being played; otherwise will return false.
     * 
     * @return  true when the game is being played; otherwise will return false.
     */
    public static boolean gameActive() {return gameActive;}

    
    /**
     * Will remove an enemy from the enemies list. 
     * 
     * @param enemyToRemove The enemy to remove.
     */
    public static void removePlayer(Enemy enemyToRemove) {
        List<Enemy> copyOfEnemies = new ArrayList<>(enemies);
        copyOfEnemies.remove(enemyToRemove);
        enemies = copyOfEnemies;
    }
}