package game.data;

import static javafx.scene.input.KeyCode.*;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

public class Config {
    public static Point2D playerPosition = new Point2D(76, 76*70);
    
    public static KeyCode leftKey = A;
    public static KeyCode rightKey = D;
    public static KeyCode jumpKey = SPACE;

}
