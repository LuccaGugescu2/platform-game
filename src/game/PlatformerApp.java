package game;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

public class PlatformerApp extends GameApplication {
	//posizione dello spawn del player
	private static final Point2D PLAYER_POSITION;
	static {
		PLAYER_POSITION = new Point2D(50, 1400);
	}
    private static final int MAX_LEVEL = 5;
    private static final int STARTING_LEVEL = 0;
    //altezza del livello
    private static final int levelHeight = 10000;
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1280);
        settings.setHeight(900);
    }
    private Entity player;

    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).left();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.A, VirtualButton.LEFT);

        getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).right();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.D, VirtualButton.RIGHT);

        getInput().addAction(new UserAction("Jump") {
            @Override
            protected void onActionBegin() {
                player.getComponent(PlayerComponent.class).jump();
            }
        }, KeyCode.W, VirtualButton.A);

    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("level", STARTING_LEVEL);
        vars.put("levelTime", 0.0);
        vars.put("score", 0);
    }

    @Override
    protected void onPreInit() {
        getSettings().setGlobalMusicVolume(0.25);
        loopBGM("BGM_dash_runner.wav");
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new PlatformerFactory());

        player = null;
        nextLevel();

        // player must be spawned after call to nextLevel, otherwise player gets removed
        // before the update tick _actually_ adds the player to game world
        player = spawn("player", PLAYER_POSITION.getX(), PLAYER_POSITION.getY());

        set("player", player);

        spawn("background");

        Viewport viewport = getGameScene().getViewport();
        viewport.setBounds(0, 0, 250 * 40, 2000);
        viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2 + 300);
        viewport.setLazy(true);
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0, 760);
      
    }

  
    private void nextLevel() {
        if (geti("level") == MAX_LEVEL) {
            return;
        }

        inc("level", +1);

        setLevel(geti("level"));
    }

    @Override
    protected void initUI() {
        if (isMobile()) {
            var dpadView = getInput().createVirtualDpadView();
            var buttonsView = getInput().createXboxVirtualControllerView();

            addUINode(dpadView, 0, getAppHeight() - 290);
            addUINode(buttonsView, getAppWidth() - 280, getAppHeight() - 290);
        }
    }

    @Override
    protected void onUpdate(double tpf) {
        inc("levelTime", tpf);

        if (player.getY() > levelHeight) {
            onPlayerDied();
        }
    }

    public void onPlayerDied() {
        setLevel(geti("level"));
    }

    private void setLevel(int levelNum) {
        if (player != null) {
            player.getComponent(PhysicsComponent.class).overwritePosition(PLAYER_POSITION);
            player.setZIndex(Integer.MAX_VALUE);
        }

        set("levelTime", 0.0);

        Level level = setLevelFromMap("tmx/level" + levelNum  + ".tmx");

        var shortestTime = level.getProperties().getDouble("star1time");

        var levelTimeData = new LevelEndScene.LevelTimeData(shortestTime * 2.4, shortestTime*1.3, shortestTime);

        set("levelTimeData", levelTimeData);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
