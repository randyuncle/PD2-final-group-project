package players;

import gamesettings.GameManager;
import gamesettings.GameMath;
import gamesettings.RuntimeSettings;
import gamesettings.Settings;
import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * The bullet class will contain an animation timer that will move an image view
 * until it has hit an object or gone off the map.
 */
public class Bullet {
    /** The bullet's current x and y coordinates. */
    private double x, y;

    /** The angle this bullet should be rotated at. */
    private final double r;
    
    
    /**
     * The first index contains the rise(delta y) of the line; the second, run(delta x).
     * 也就是slope[0]=rise(delta y), slope[1]=run(delta x)
     * */
    private final double[] slope;
    
    
    /**
     * Contains an image of the bullet.
     * 注意：ImageView屬於Node，所以必須加入到layout pane內
     * 心得：ImageView除了可以放照片格式檔(.png)外，還可以放動畫特效檔(.gif)
     * 例如：Bullet的constructor內：this.bulletView = new ImageView(Settings.BULLET); 為.png檔;
     * 而Bullet class內的.start()內子彈碰到(攻擊)敵人時，bulletView.setImage(Settings.EXPLOSION); 為.gif檔
     * 因此：子彈從射擊(圖片檔)到攻擊到敵人(圖片檔消失，動畫檔出現)時，可以很靈活的呈現
     */
    private final ImageView bulletView;    
    
    
    /**
     * Constructs a new Bullet object that has a specified (x,y) coordinate,
     * a destination (x,y) coordinate, and an angle of rotation.
     * 
     * @param x     The starting x-coordinate of the bullet.
     * @param y     The starting y-coordinate of the bullet.
     * @param dX    The destination x coordinate of the bullet.
     * @param dY    The destination y coordinate of the bullet.
     * @param r     The angle the bullet should be at.
     */
    public Bullet(double x, double y, double dX, double dY, double r) {
        this.x = x;
        this.y = y;
        
        this.slope  = GameMath.calculateSlope(x, dX, y, dY);
        
        this.bulletView = new ImageView(Settings.BULLET); //摮������(bullet.png)
        
        this.r = r;
    }
    
    
    /**
     * Will animate the bullet.
     * 
     * @param pane      The pane to draw the bullet in.
     */
    public void start(Pane pane) {
        
        pane.getChildren().add(this.bulletView);

        // Rotate the bullet and move it to the starting position.
        this.bulletView.setRotate(r);
        this.bulletView.relocate(x,y);        
        
        /*
            Use the AnimationTimer to animate the bullet. Since multithreading
            would be too complicated, just manually manage your time. Also, 
            time is managed manually because you can't use Thread.sleep() or 
            wait() on a JavaFX thread.
        */
        AnimationTimer timer = new AnimationTimer() {

            long previousTime = 0;
            
            /*
                onDestroy is only true when this bullet has collided with
                something(子彈撞到敵人或者是牆壁).
            */
            boolean onDestroy = false;
            
            @Override
            public void handle(long now) {
                
                long time = now / 1000000;
                
                if (!onDestroy) {
                    if (time - previousTime >= Settings.BULLET_MOVEMENT_DELAY || previousTime == 0) {
                    	//因為slope[0]=rise(delta y), slope[1]=run(delta x)
                        //所以，此兩行代表移動x,y座標至相對應目標座標
                        x += slope[1];
                        y += slope[0];

                      //判斷式表示移動後的子彈x,y座標皆在地圖內
                        if (x > 0 && x < RuntimeSettings.getMaxBulletPositionX() && y > 0 && y < RuntimeSettings.getMaxBulletPositionY()) {
                            bulletView.relocate(x,y); //雿瘺ullet��mageView�隞亙�����ayout pane�蝘餃��璅漣璅�
                            previousTime = time;
                            for (Enemy enemy : GameManager.enemies) {
                            	//利用ImageView的getBoundsInParent()得到所在layout pane內的bounds
                                //再用Bounds class的.intersects()判斷子彈有沒有碰到(攻擊)到敵人
                                if (bulletView.getBoundsInParent().intersects(enemy.getImageView().getBoundsInParent())) {
                                    this.onDestroy = true;
                                    //此行是配合下一行的explosion.gif爆炸動畫要在敵人的哪一個位置施放，-15是實際遊玩後所調整的動畫最佳施放位置
                                    bulletView.relocate(enemy.getCenterX()-15, enemy.getCenterY()-15);
                                    bulletView.setImage(Settings.EXPLOSION); //ImageView播放爆炸的.gif檔
                                    enemy.deductHealth();//敵人生命值下降
                                }
                            }                            
                        } else { //表示碰到牆壁了(原本子彈會超出地圖)
                            this.onDestroy = true;
                          //子彈的x,y軸超出牆壁，故作修正(-30使子彈的ImageView會在預設的地圖大小內施放)
                            if(y >= RuntimeSettings.getMaxBulletPositionY()) {
                                bulletView.relocate(x, RuntimeSettings.getMaxBulletPositionY()-30);
                            } else if(x >= RuntimeSettings.getMaxBulletPositionX()) {
                                bulletView.relocate(RuntimeSettings.getMaxBulletPositionX()-30, y);
                            }
                            bulletView.setImage(Settings.EXPLOSION);//ImageView播放爆炸的.gif檔
                        }
                    }

                } else if (time - previousTime >= Settings.EXPLOSION_DURATION) { //表示OnDestroy為true(子彈攻擊到敵人或牆壁)
                    this.stop(); //this表示這個AnimationTimer，.stop()表示結束
                    pane.getChildren().remove(bulletView);
                }

            }
        };
        System.out.println("Bullet fired");
        //子彈射出＋爆炸動畫一系列動作結束後，AnimationTimer再度啟動
        //此.start()並非Bullet內的.start()，而是AnimationTimer內的.start()
        timer.start();
    }
    
}
