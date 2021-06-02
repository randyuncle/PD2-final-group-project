package gamesettings;

/**
 * This class contains and manages the runtime settings during game play.
 */
public class RuntimeSettings {
	//注意：地圖以左上角為座標原點(0,0)，往右為x軸正方向，往下為y軸正方向
    //gameFieldWidth, gameFieldHeight 和 maxPlayerPositionX, maxPlayerPositionY 和 maxBulletPositionX, maxBulletPositionY 概念是相同的
    //都代表地圖的x座標最大值和地圖的y座標最大值，但只是方便看懂而已
    private static int gameFieldWidth;
    private static int gameFieldHeight;
    
    private static int maxPlayerPositionX;
    private static int maxPlayerPositionY;
    private static int maxBulletPositionX;
    private static int maxBulletPositionY;
    
    private RuntimeSettings(){}
    
    public static void loadRuntimeSettings(int w, int h) {
        gameFieldWidth  = w;
        gameFieldHeight = h;
      //考慮主角和敵人的寬度和高度(目前是30*30 png)
        maxPlayerPositionX = w - gamesettings.Settings.PLAYER_WIDTH;
        maxPlayerPositionY = h - gamesettings.Settings.PLAYER_HEIGHT;
        maxBulletPositionX = w; //也就是子彈不會超過地圖的邊界
        maxBulletPositionY = h;      
    }   
    
    public static int getWidth() {return gameFieldWidth;}
    public static int getHeight() {return gameFieldHeight;}
    public static int getMaxBulletPositionX() {return maxBulletPositionX;}
    public static int getMaxBulletPositionY() {return maxBulletPositionY;}
    public static int getMaxPlayerPositionX() {return maxPlayerPositionX;}
    public static int getMaxPlayerPositionY() {return maxPlayerPositionY;}
}
