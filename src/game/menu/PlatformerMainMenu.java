package game.menu;


import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.input.view.KeyView;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.ui.FontType;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Parent;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;
import static javafx.scene.input.KeyCode.*;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.function.Consumer;

import game.PlatformerApp;
import game.data.Config;
import game.data.GestoreSalvataggio;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class PlatformerMainMenu extends FXGLMenu {

	
	protected double optionMenuDimensionX = getAppWidth() - getAppWidth() / 6;
	protected double optionMenuDimensionY = getAppHeight() - getAppHeight() / 6;
	protected double optionMenuPositionX = getAppWidth() / 12;
	protected double optionMenuPositionY = getAppHeight() / 12;
	
	
	
	/**
	 * Costruttore Main Menu
	 * @author montis
	 */
	public PlatformerMainMenu() {
        super(MenuType.MAIN_MENU);

        Rectangle background = new Rectangle(getAppWidth(), getAppHeight());
        Image img = new Image("/assets/textures/background/darkCastleBackground.jpg");
        
        background.setFill(new ImagePattern(img));
      
        getContentRoot().getChildren().setAll(background);
        
        
        var title = getUIFactoryService().newText(getSettings().getTitle(), Color.WHITE, FontType.MONO ,40);
        title.setStroke(Color.WHITESMOKE);
        title.setStrokeWidth(1.2);
        
        centerTextBind(title, getAppWidth() / 2.0, 150);

        var version = getUIFactoryService().newText(getSettings().getVersion(), Color.WHITE, 22.0);
        centerTextBind(version, getAppWidth() / 2.0, 170);

        getContentRoot().getChildren().addAll(title, version);

        float dim = 30.0f;
        
        var menuBox = new VBox(
                10,
                new MenuButton("Continua", dim, () -> {}),
                new MenuButton("Nuova Partita", dim, () -> fireNewGame()),
                new MenuButton("Opzioni", dim, () -> option()),
                new MenuButton("Credits", dim, () -> showCredits()),
                new MenuButton("Esci", dim, () -> fireExit()),
                new Text(""),
                new Separator(Orientation.HORIZONTAL)
        );
        menuBox.setAlignment(Pos.TOP_LEFT);

        menuBox.setTranslateX(getAppWidth() / 20);
        menuBox.setTranslateY(getAppHeight() / 2.0 + 100);
        
        
        getContentRoot().getChildren().addAll(menuBox);
    }

	/**
	 * crea file partita e lo carica
	 * @author montis
	 */
    private void newGame() {
		getDialogService().showInputBox("Metti nome partita", new Consumer<String>() {
			@Override
			public void accept(String t) {
				try {
					GestoreSalvataggio.SalvaSuFile(t);
					
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
			}
		});
		
	}

    /**
     * finestra credits
     * @author montis
     */
	private void showCredits() {
        getDialogService().showMessageBox("credits.....");
    }

    
    
    
    /**
     * Crea un menu Opzioni che consente di modificare le impostazioni
     * @author montis
     */
    private void option() {
    	
    	float dim = 25;
    	
    	VBox optionMenu = new VBox(
    			10,
    			new MenuButton("Audio", dim, () -> audio()),
    			new Text(""),
    			new MenuButton("Command", dim, () -> command()),
    			new Text(""),
    			new MenuButton("Restore", dim, () -> restore())
    			);
    	optionMenu.setTranslateX(getAppHeight() / 6);
    	optionMenu.setTranslateY(getAppWidth() / 6);
    	
    	Rectangle bgOpacity = new Rectangle(getAppWidth(), getAppHeight());
    	bgOpacity.setFill(Color.BLACK);
    	bgOpacity.setOpacity(0.5);
    	
    	Rectangle bgOption = new Rectangle( optionMenuDimensionX , optionMenuDimensionY);
    	bgOption.setFill(Color.BLACK);
    	bgOption.setTranslateX(optionMenuPositionX);
    	bgOption.setTranslateY(optionMenuPositionY);
    	bgOption.setOpacity(0.9);
    	
    	Separator separa = new Separator(Orientation.VERTICAL);
    	separa.setTranslateX(optionMenuPositionX + optionMenuDimensionX / 4);
    	separa.setTranslateY(optionMenuPositionY);
    	separa.setPrefHeight(optionMenuDimensionY);
    	separa.setOpacity(0.6);
    	
    	MenuButton esc = new MenuButton("ESC", dim,() -> comeBackToMainMenu());
    	esc.setTranslateX(getAppHeight() / 6);
    	esc.setTranslateY((getAppHeight() - getAppHeight() / 12) - 35);
    	
    	getContentRoot().getChildren().addAll(bgOpacity, bgOption, optionMenu, separa, esc);
    	
    }
    
    /**
     * riporta le impostazioni del menu in default
     * @author montis
     */
    private void restore() {
    	
    	FXGL.getDialogService().showConfirmationBox("vuoi resettare le impostazioni ?", yes -> {
    		if (yes) {
            Config.setDefaultConfig();
    		}
    	});
	}

     /**
     * crea il menu impostazioni audio
     * @author montis
     */
	protected void audio() {
		getContentRoot().getChildren().remove(9, getContentRoot().getChildren().size());
		
		Slider musicSlider = new Slider();
		musicSlider.setValue(getSettings().getGlobalMusicVolume());
		musicSlider.setOnMouseReleased(e -> getSettings().setGlobalMusicVolume(musicSlider.getValue()));
		
		Slider volumeSlider = new Slider();
		volumeSlider.setValue(getSettings().getGlobalMusicVolume());
		volumeSlider.setOnMouseReleased(e -> getSettings().setGlobalSoundVolume(volumeSlider.getValue()));
		
		var menuAudio = new GridPane();
		menuAudio.setHgap(optionMenuDimensionX / 3 - 160);
        menuAudio.setVgap(optionMenuDimensionY / 8);
        menuAudio.add(getUIFactoryService().newText("Impostazioni Audio"), 1, 0);
        menuAudio.setVgap(50);
        menuAudio.addRow(1, getUIFactoryService().newText("Musica"));
		menuAudio.add(musicSlider, 2, 1);
		menuAudio.addRow(2, getUIFactoryService().newText("Suono"));
		menuAudio.add(volumeSlider, 2, 2);
		menuAudio.setTranslateX(optionMenuPositionX + optionMenuDimensionX / 4 + 35);
		menuAudio.setTranslateY(optionMenuPositionY + 30);
		
		getContentRoot().getChildren().add(menuAudio);
	}

	private void command() {
		getContentRoot().getChildren().remove(9, getContentRoot().getChildren().size());
		
		Image imgDash = new Image(new File("player/_Run.png").toURI().toString());
		ImageView run = new ImageView(imgDash);
		
		
		GridPane pane = new GridPane();
		
		Image imgRun = new Image("/assets/textures/player/gif/run.gif");
		Image imgJump = new Image("/assets/textures/player/gif/jump.gif");
		Image imgAttack = new Image("/assets/textures/player/gif/attack.gif");		
		
		ImageView imgRightView = new ImageView(imgRun);
		ImageView imgLeftView = new ImageView(imgRun);
		imgLeftView.setRotationAxis(new Point3D(0, 1, 0));
		imgLeftView.setRotate(180);
		
		ImageView imgJumpView = new ImageView(imgJump);
		ImageView imgAttackView = new ImageView(imgAttack);
		
		pane.setHgap(optionMenuDimensionX / 3 - 100);
		pane.setVgap(optionMenuDimensionY / 3);
		pane.add(getUIFactoryService().newText("Comandi"), 1, 0);
		
		pane.addRow(2, 
					getUIFactoryService().newText("Right"),new CommandButton(Config.rightKey,() -> cambiaTasto(Config.rightKey)),imgRightView);
		pane.setVgap(50);
		
		pane.addRow(3, getUIFactoryService().newText("Left"),  new CommandButton(Config.leftKey,() -> cambiaTasto(Config.leftKey)), imgLeftView);
		
		pane.addRow(4, getUIFactoryService().newText("Jump"),  new CommandButton(Config.jumpKey,() -> cambiaTasto(Config.jumpKey)) , imgJumpView);
		pane.addRow(5, getUIFactoryService().newText("Attack"),  new CommandButton(Config.fightKey,() -> cambiaTasto(Config.jumpKey)) , imgAttackView);
		
		pane.setTranslateX(optionMenuPositionX + optionMenuDimensionX / 4 + 35);
		pane.setTranslateY(optionMenuPositionY + 30);
		
		getContentRoot().getChildren().addAll(pane);
    }

    /**
     * torna allo stato di start del menu pricipale
     * @author montis
     */
    private void comeBackToMainMenu () {		 	
    getContentRoot().getChildren().remove(4, getContentRoot().getChildren().size());	
    }
    
    
    /**
     * viene utilizzata nella sezione command dell'option menu e serve per cambire il tasto di una certa funzionalità
     * @param tasto
     * @author montis
     */
    private void cambiaTasto(KeyCode tasto) {
  
	}


	protected static class MenuButton extends Parent {
		MenuButton(String name, float dimentionText, Runnable action) {
			var text = getUIFactoryService().newText(name, Color.GRAY, FontType.MONO, dimentionText);
			text.setStrokeWidth(1);
			// text.strokeProperty().bind(text.fillProperty());

			text.fillProperty().bind(Bindings.when(hoverProperty()).then(Color.WHITE).otherwise(Color.GRAY));

			setOnMouseClicked(e -> action.run());

			setPickOnBounds(true);

			getChildren().add(text);
		}
	}

	private static class CommandButton extends Parent {
		CommandButton(KeyCode key, Runnable action) {

			var k = new KeyView(key);

			setOnMouseClicked(e -> action.run());

			setPickOnBounds(true);

			getChildren().add(k);
		}
	}
}

