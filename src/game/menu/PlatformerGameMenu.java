package game.menu;

import static com.almasb.fxgl.dsl.FXGL.getDialogService;
import static com.almasb.fxgl.dsl.FXGL.getGameScene;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.getUIFactoryService;

import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.util.ResourceBundle.Control;

import com.almasb.fxgl.app.scene.FXGLLoadingScene;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.input.view.KeyView;
import com.almasb.fxgl.scene.Scene;

import game.data.Config;
import game.data.GestoreSalvataggio;
import game.menu.PlatformerMainMenu.*;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

/**
 * crea un menu all'interno del gioco apribile tramite tasto <b>ESC</b> durante la partita
 * @author montis
 *
 */
public class PlatformerGameMenu extends FXGLMenu {

	/**
	 * crea un menu di gioco
	 * @author montis
	 */
	public PlatformerGameMenu() {
		super(MenuType.GAME_MENU);
		
		Rectangle background = new Rectangle(getAppWidth(), getAppHeight());
		background.setOpacity(0.6);	
		
		Rectangle gameRect = new Rectangle(getAppWidth() / 7, getAppHeight() / 4);
		gameRect.setTranslateX(getAppWidth() / 2 - getAppWidth()/14);
		gameRect.setTranslateY(getAppHeight() / 2 - getAppHeight()/8);
		gameRect.setOpacity(0.8);
		
		getContentRoot().getChildren().addAll(background, gameRect);
		
		int dim = 25;
	
		
		var menuBox = new VBox(
                10,
                new MenuButton("Resume", dim, () -> fireResume() , false),
                new MenuButton("Salva", dim, () -> salva() , false),
                
                new MenuButton("Comandi", dim, () -> instructions(), false),
                new MenuButton("Torna al menu", dim, () -> exitToMainMenu(), false),
                new MenuButton("Esci", dim, () -> fireExit(), false)
        );
		
		menuBox.setAlignment(Pos.TOP_CENTER);
		menuBox.setTranslateX(getAppWidth() / 2 - getAppWidth() / 14 + 40);
		menuBox.setTranslateY(getAppHeight() / 2 - getAppHeight()/12);
		
		
		getContentRoot().getChildren().add(menuBox);
		
	}
	
	protected void salva() {
		
		FXGL.getDialogService().showConfirmationBox("vuoi salvare " + Config.nomePartita +" ?", yes -> {
    		if (yes) {
				
    			try {
					GestoreSalvataggio.SalvaSuFile(Config.nomePartita);
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    	});
		
	}
	
	
	/**
	 * torna al menu principale
	 * @author montis
	 */
	protected void exitToMainMenu() {
		getGameScene().clearUINodes();
		getGameScene().getContentRoot().getChildren().remove(getContentRoot().getChildren().size());
		getController().gotoMainMenu();
		
	}

	
	/**
	 * apre una finestra con i comandi del gioco
	 * @author montis
	 */
	protected void instructions() {
        GridPane pane = new GridPane();
        
        
        pane.setHgap(25);
        pane.setVgap(10);
        pane.addRow(1, getUIFactoryService().newText("Movement"), new HBox(2, new KeyView(Config.leftKey), new KeyView(Config.rightKey)));
        pane.addRow(2, getUIFactoryService().newText("Attack"), new KeyView(Config.fightKey));
        pane.addRow(3, getUIFactoryService().newText("Jump"), new KeyView(Config.jumpKey));
        
        getDialogService().showBox("Istruzzioni", pane, getUIFactoryService().newButton("Ok"));
    }
}

