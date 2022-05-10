package game;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import com.almasb.fxgl.physics.PhysicsComponent;

import game.menu.PlatformerGameMenu;
import game.menu.PlatformerMainMenu;
import game.collisionHandler.EnemyCollisionHandler;
import game.collisionHandler.PlayerAttackHandler;
import game.collisionHandler.PlayerChekpointHandler;
import game.collisionHandler.PlayerSpikeHandler;
import game.collisionHandler.PlayerWallJumpHandler;
import game.data.Config;
import game.entities.HealthComponent;
import game.entities.PlayerComponent;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

public class PlatformerApp extends GameApplication {
	// posizione dello spawn del player
	private Entity player;
	private Entity[] health = new Entity[3];
	private static final int MAX_LEVEL = 2;
	private static final int STARTING_LEVEL = 0;
	// altezza del livello
	private static final int levelHeight = 80 * 32;

	@Override
	protected void initSettings(GameSettings settings) {
		// finestra gioco
		settings.setWidth(1920);
		settings.setHeight(1080);
		settings.setTitle("platformer");
		settings.setMainMenuEnabled(true);
		settings.setGameMenuEnabled(true);
		settings.setDeveloperMenuEnabled(true);

		settings.setSceneFactory(new SceneFactory() {
			// menu inizio gioco
			@Override
			public FXGLMenu newMainMenu() {
				return new PlatformerMainMenu();
			}

			// menu interno gioco
			@Override
			public FXGLMenu newGameMenu() {
				return new PlatformerGameMenu();
			}
		});
	}

	@Override
	protected void initInput() {
		// input da tastiera per il movimento del player
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

		getInput().addAction(new UserAction("Attack") {
			@Override
			protected void onActionBegin() {
				player.getComponent(PlayerComponent.class).attack();
			}
		}, KeyCode.F);

	}

	@Override
	protected void initGameVars(Map<String, Object> vars) {
		vars.put("level", STARTING_LEVEL);
	}

	@Override
	protected void onPreInit() {
		Config.setDefaultSettings();
		getSettings().setGlobalMusicVolume(0.0);
		loopBGM("BGM_dash_runner.wav");
	}

	@Override
	protected void initGame() {
		getGameWorld().addEntityFactory(new PlatformerFactory());
		nextLevel();
		// creazione vita del player (quando il gioco parte)
		for (int i = 0; i < health.length; i++) {
			health[i] = spawn("health", Config.playerPosition.getX() + 50 * i, Config.playerPosition.getY());
			set("health", health[i]);
		}

		// il player viene spwnato in base alla posizione presente in Config
		player = spawn("player", Config.playerPosition.getX(), Config.playerPosition.getY());
		set("player", player);
		spawn("background");
		//spawn("castleBackground");
		Viewport viewport = getGameScene().getViewport();
		viewport.setZoom(1);
		viewport.setBounds(0, 0, 70 * 32, 80 * 32);
		viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
	}

	@Override
	protected void initPhysics() {
		// gestione collisioni tra entità
		getPhysicsWorld().setGravity(0, 800);
		getPhysicsWorld().addCollisionHandler(new PlayerWallJumpHandler());
		getPhysicsWorld().addCollisionHandler(new PlayerSpikeHandler());
		getPhysicsWorld().addCollisionHandler(new PlayerChekpointHandler());
		getPhysicsWorld().addCollisionHandler(new PlayerAttackHandler());
		getPhysicsWorld().addCollisionHandler(new EnemyCollisionHandler());
	}

	private void nextLevel() {
		if (geti("level") == MAX_LEVEL) {
			return;
		}

		inc("level", +1);

		setLevel(geti("level"));
	}

	@Override
	protected void onUpdate(double tpf) {

		if (player.getY() > levelHeight || player.getComponent(PlayerComponent.class).getHealth() <= 0) {
			player.getComponent(PlayerComponent.class).refillHealth();
			for (int i = 0; i < health.length; i++) {
				health[i].setOpacity(1);
			}
			onPlayerDied();
		}
		
		if(player.getComponent(PlayerComponent.class).isDead) {
			for (int i = 0; i < health.length; i++) {
				health[i].setOpacity(0);
			}
		}
		// la vita segue il player
		health[0].setPosition(new Point2D(player.getX() - 22, player.getY() - 30));
		health[1].setPosition(new Point2D(player.getX() + 2, player.getY() - 30));
		health[2].setPosition(new Point2D(player.getX() + 25, player.getY() - 30));
		// cambia la vita del player se prende danno
		switch (player.getComponent(PlayerComponent.class).getHealth()) {
		case 6:
			health[2].getComponent(HealthComponent.class).setFull();
			health[1].getComponent(HealthComponent.class).setFull();
			health[0].getComponent(HealthComponent.class).setFull();
			break;
		case 5:
			health[2].getComponent(HealthComponent.class).sethalf();
			break;
		case 4:
			health[2].getComponent(HealthComponent.class).setEmpty();
			break;
		case 3:
			health[1].getComponent(HealthComponent.class).sethalf();
			break;
		case 2:
			health[1].getComponent(HealthComponent.class).setEmpty();
			break;
		case 1:
			health[0].getComponent(HealthComponent.class).sethalf();
			break;
		case 0:
			health[0].getComponent(HealthComponent.class).setEmpty();
			break;
		default:
			throw new IllegalArgumentException(
					"Unexpected value: " + player.getComponent(PlayerComponent.class).getHealth());
		}
	}

	public void onPlayerDied() {
		player.getComponent(PhysicsComponent.class).overwritePosition(Config.playerPosition);

	}

	private void setLevel(int levelNum) {
		if (player != null) {
			player.getComponent(PhysicsComponent.class).overwritePosition(Config.playerPosition);
			player.setZIndex(Integer.MAX_VALUE);
		}

		setLevelFromMap("tmx/level" + levelNum + ".tmx");
	}

	public static void main(String[] args) {
		launch(args);
	}
}
