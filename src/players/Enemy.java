package players;

import gamesettings.GameManager;
import gamesettings.GameMath;
import gamesettings.Settings;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * This is the Enemy class. When shot, the bullet that collided with the enemy
 * will call the deductHealth() method to deduct this enemy's health.
 */
public class Enemy extends Player {
    
    // The health of this enemy. 
    private int currentHealth = 5;
    
    // The previous time this enemy attacked.
    private long previousTime = 0;
    private int scaledDistance = 0;
    private int damage = 0;    
    
   
    /**
     * Constructor for the Enemy class. class GameManager Creates a new instance of the Enemy class.
     * 
     * @see Player
     * 
     * @param speed     The speed of this enemy. 
     * @param damage    The damage this enemy causes. 
     */
    public Enemy(Pane pane, Image img, double x, double y, double r, double velX,
                    double velY, double velR, int speed, int damage) {
        
        super(pane, img, x, y, r, velX, velY, velR);        
        this.scaledDistance = speed;
        this.damage = damage;
    }

    
    /**
     * Moves this enemy and then attacks the main player if possible. 
     * @param now 
     */
    public void attackPlayer(long now) {
        
        long time = now / 1000000;

        if(previousTime == 0) previousTime = time;
        if(imageView.getBoundsInParent().intersects(GameManager.mainPlayer.getImageView().getBoundsInParent())
                && time - previousTime >= 500) {
            GameManager.mainPlayer.deductHealth(damage);
            System.out.println("Enemy:\tAttacking Main Player");
            previousTime = time;            
        } else {
            changeLocation();
        }       
    }
    
    
    /**
     * Updates the location this image view should be at then relocates it.
     */
    public void changeLocation() {
        updateLocation();
        this.imageView.relocate(x, y);
        this.imageView.setRotate(GameMath.calculateAngle(
                                    x, y, 
                                    GameManager.mainPlayer.getCenterX(), 
                                    GameManager.mainPlayer.getCenterY()));
    }
    
    
    /**
     * 此和class GameMath內的.calculateSlope()類似
     * Calculate the rise and run to the main player. This is individualized for
     * each Enemy to avoid threading issues. 
     *
     * @return 
     */
    private double[] calculateNextPoint() {
        double rise = GameManager.mainPlayer.getCenterY() - y; //rise為和Main player的y座標相距距離
        double run = GameManager.mainPlayer.getCenterX() - x; //run為和Main player的x座標相距距離
        
        boolean nRise = false, nRun = false; //nRise == negative Rise
        
        if (rise < 0) {
            rise *= -1;
            nRise = true;
        }
        
        if (run < 0) {
            run *= -1;
            nRun = true;
        }
        //scaledDistance的目的為縮放敵人和主角的距離(但不改變之間的斜率)，使敵人不會一看到主角，就馬上跑到主角旁邊
        if (Math.max(rise, run) == rise) {
            run = (scaledDistance * run) / rise;
            rise = scaledDistance;
        } else {
            rise = (scaledDistance * rise) / run;
            run = scaledDistance;
        }
        
        if (nRise) rise *= -1;
        if (nRun) run *= -1;
        
        return new double[]{rise,run};
    }
    
    
    /**
     * Updates this enemies location. 
     */
    private void updateLocation() {
        double[] temp = calculateNextPoint();
        x += temp[1]; //temp[1]為run(縮放比例後敵人和主角間的delta x)
        y += temp[0]; //temp[0]為run(縮放比例後敵人和主角間的delta x)
    }    
    
    
    /**
     * Deducts this enemy's health. If the health is at zero, it will remove this
     * enemy from the living enemies list and add it to the dead enemies list. 
     */
    public void deductHealth() {
        this.currentHealth--; //'--'表示被子彈攻擊到時，敵人的生命值減1
        if (this.currentHealth == 0) {
            this.imageView.setImage(Settings.getDeadEnemyImage()); //播放死掉敵人的圖像(一攤血)
            GameManager.amountKilled++;
            GameManager.removePlayer(this);
            GameManager.deadEnemies.add(this);
        }
    }            
}
