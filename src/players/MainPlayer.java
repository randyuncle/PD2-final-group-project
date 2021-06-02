package players;

import gamesettings.Input;
import gamesettings.RuntimeSettings;
import gamesettings.Settings;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


/**
 * The Main Player will be controlled by the user.
 */
public class MainPlayer extends Player {    
    
    
    /** The input listener for the main player. */
    private final Input input;
    
    
    private int health = 60;
    
    /*
        Constructs a new Player object (by class GameManager).
    */
    public MainPlayer(Pane pane, Image img, double x, double y, double r, double velX, double velY, double velR, Input input) {
        super(pane, img, x, y, r, velX, velY, velR); //MainPlayer extends Player
        this.input = input;
        this.imageView = new ImageView(Settings.getMainPlayerImage());
        this.imageView.relocate(x, y);
    }

    
    /**
     * Sets velocities on the x and y axis.
     */
    public void changeValues() {
        if(input.isMoveDown() && y < RuntimeSettings.getMaxPlayerPositionY()) { //往下且沒有超出地圖邊界
            velY = Settings.SPEED;
        } else if(input.isMoveUp() && y > 0) { //往下且沒有超出地圖邊界
            velY = -Settings.SPEED;
        } else {
            velY = 0;
        }
        
        if(input.isMoveLeft() && x > 0) { //往左且沒有超出地圖邊界
            velX = -Settings.SPEED;
        } else if(input.isMoveRight() && x < RuntimeSettings.getMaxPlayerPositionX()) { //往右且沒有超出地圖邊界
            velX = Settings.SPEED;
        } else {
            velX = 0;
        }
        r = input.getAngle();
    }        
    
    public void deductHealth(int amount) {
        health -= amount;
    }
    
    public int getHealth() {
        return health;
    }

}
