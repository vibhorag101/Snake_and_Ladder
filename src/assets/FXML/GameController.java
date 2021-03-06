package assets.FXML;
import Menu_Items.Menu;
import application.Main;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import Game.dice;
import Game.gameButton;
import Winning_Menu.WinningMenuStart;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URISyntaxException;

public class GameController {
    playerControl pc;
    gameBoardMaker gb;
    dice gameDice;
    int diceNum;
    gameButton turnInfo;
    gameButton exit;
    ImageView back;
    /* Initialising the components and injecting them into the controller */
    @FXML
    private VBox leftPane;
    @FXML
    private Pane gamePane;
    @FXML
    private Circle P1;
    @FXML
    private Circle P2;

    @FXML
    private void initialize() {
        this.turnInfo = new gameButton("Player 1\n" + " Turn", "Turn Info");
        initialiseGamePane();
        initialiseLeftPane();
    }

    private void initialiseGamePane() {
        this.gb = new gameBoardMaker(gamePane);
        gb.LabelMaker();
        styleGamePane();
        createLadderPaths();
        this.pc = new playerControl(P1, P2, turnInfo, gb);
    }

    private void initialiseLeftPane() {
        ImageView indicator = new ImageView(new Image(Main.rl.getPath("logo/arrow.png"), 60, 60, true, true));
        back = new ImageView(new Image(Main.rl.getPath("logo/previous.png"), 45, 45, true, true));
        exit = new gameButton("Exit", "Exit");
        createDice();
        leftPane.getChildren().addAll(turnInfo, gameDice.getDiceImage(), indicator,back,exit);
        styleLeftPane();
        initialiseMouseHandler();
        animateIndicator(indicator);
    }
    private void animateIndicator(ImageView indicator){
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                TranslateTransition tt = new TranslateTransition();
                tt.setDuration(javafx.util.Duration.millis(1000));
                tt.setNode(indicator);
                tt.setToY(indicator.getLayoutY() - 25);
                tt.setCycleCount(Animation.INDEFINITE);
                tt.setAutoReverse(true);
                tt.play();

            }
        });
        t.start();
    }

    private void createDice() {
        gameDice = new dice(pc);
    }

    private void styleLeftPane() {
        leftPane.setStyle("-fx-background-color: rgba(24,66,34,0.88);");
    }

    private void styleGamePane() {
        gamePane.setStyle("-fx-background-color: rgba(2,252,19,0.88);");
    }

    private void createLadderPaths() {
        gb.ladderMaker(4, 25);
        gb.ladderMaker(8, 31);
        gb.ladderMaker(28, 46);
        gb.ladderMaker(32, 48);
        gb.ladderMaker(42, 80);
        gb.ladderMaker(58, 77);
        gb.ladderMaker(52, 68);
        gb.ladderMaker(69, 93);
        gb.ladderMaker(84, 98);
    }

    private void initialiseMouseHandler() {
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        back.setOnMouseClicked(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent mouseEvent) {
                Platform.runLater(new Runnable(){

                    @Override
                    public void run() {
                        Menu menu = new Menu(850, 600);
                        Main.changeScene(menu.getScene());
                    }
                });
            }
        });
        back.setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                back.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 0, 0);");
            }
        });
        back.setOnMouseExited(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                back.setStyle("-fx-effect:none;");
            }
        });
    }
}

