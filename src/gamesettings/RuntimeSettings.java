package gamesettings;

/**
 * This class contains and manages the runtime settings during game play.
 */
public class RuntimeSettings {
	//�`�N�G�a�ϥH���W�����y�Э��I(0,0)�A���k��x�b����V�A���U��y�b����V
    //gameFieldWidth, gameFieldHeight �M maxPlayerPositionX, maxPlayerPositionY �M maxBulletPositionX, maxBulletPositionY �����O�ۦP��
    //���N��a�Ϫ�x�y�г̤j�ȩM�a�Ϫ�y�y�г̤j�ȡA���u�O��K�����Ӥw
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
      //�Ҽ{�D���M�ĤH���e�שM����(�ثe�O30*30 png)
        maxPlayerPositionX = w - gamesettings.Settings.PLAYER_WIDTH;
        maxPlayerPositionY = h - gamesettings.Settings.PLAYER_HEIGHT;
        maxBulletPositionX = w; //�]�N�O�l�u���|�W�L�a�Ϫ����
        maxBulletPositionY = h;      
    }   
    
    public static int getWidth() {return gameFieldWidth;}
    public static int getHeight() {return gameFieldHeight;}
    public static int getMaxBulletPositionX() {return maxBulletPositionX;}
    public static int getMaxBulletPositionY() {return maxBulletPositionY;}
    public static int getMaxPlayerPositionX() {return maxPlayerPositionX;}
    public static int getMaxPlayerPositionY() {return maxPlayerPositionY;}
}
