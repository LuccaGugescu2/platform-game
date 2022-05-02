package game;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import com.almasb.fxgl.physics.PhysicsComponent;

import game.collisionHandler.PlayerChekpointHandler;
import game.collisionHandler.PlayerSpikeHandler;
import game.collisionHandler.PlayerWallJumpHandler;
import game.entities.PlayerComponent;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

public class PlatformerApp extends GameApplication {
	//posizione dello spawn del player
	private Entity player, box, checkpoint;
	public static Point2D playerPosition;
	static {
		playerPosition = new Point2D(76, 76*70);
	}
    private static final int MAX_LEVEL = 5;
    private static final int STARTING_LEVEL = 0;
    //altezza del livello
    private static final int levelHeight = 80*70;
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1280);
        settings.setHeight(900);
    }

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
        }, KeyCode.SPACE, VirtualButton.A);

    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("level", STARTING_LEVEL);
    }

    @Override
    protected void onPreInit() {
        getSettings().setGlobalMusicVolume(0.25);
        loopBGM("BGM_dash_runner.wav");
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new PlatformerFactory());

        nextLevel();

        //il player viene spwnato in base alla sua posizione su tiled
        player = spawn("player", playerPosition.getX(), playerPosition.getY());
        set("player", player);
        spawn("background");

        Viewport viewport = getGameScene().getViewport();
        viewport.setBounds(0, 0, 70*70, 80*70);
        viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0, 760);
        getPhysicsWorld().addCollisionHandler(new PlayerWallJumpHandler());
        getPhysicsWorld().addCollisionHandler(new PlayerSpikeHandler());
        getPhysicsWorld().addCollisionHandler(new PlayerChekpointHandler());
      
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

        if (player.getY() > levelHeight || player.getComponent(PlayerComponent.class).getHealth() <= 0) {
        	player.getComponent(PlayerComponent.class).refillHealth();
            onPlayerDied();
        }
    }

    public void onPlayerDied() {
    	player.getComponent(PhysicsComponent.class).overwritePosition(playerPosition);
        
    }

    private void setLevel(int levelNum) {
        if (player != null) {
        	player.getComponent(PhysicsComponent.class).overwritePosition(playerPosition);
            player.setZIndex(Integer.MAX_VALUE);
        }

        setLevelFromMap("tmx/level" + levelNum  + ".tmx");
        }

    public static void main(String[] args) {
        launch(args);
    }
}
