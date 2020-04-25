import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameWindow {

    private Tsuro tsuro;
    private int count;
    private boolean paused;

    public GameWindow(Tsuro myTsuro, Stage myStage) {

        tsuro = myTsuro;
        count = 1;
        paused = false;

        StackPane root = new StackPane();
        Canvas canvas = new Canvas(Board.SIZEX, Board.SIZEY);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        scene.setOnMouseMoved(e -> { tsuro.checkHandHover(e.getX(), e.getY()); });
        scene.setOnMouseClicked(e -> { tsuro.checkHandClick(e.getX(), e.getY()); });

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.R) tsuro.rotateHand();
            if (e.getCode() == KeyCode.P) paused = !paused;
            if (e.getCode() == KeyCode.Z) tsuro.start(myStage); // Careful with this
        });

        // Animation loop - runs at (about) 60fps
        new AnimationTimer() {
            public void handle(long nano) {

                tsuro.showBoard(gc);
                tsuro.showPlayers(gc);
                tsuro.showHand(gc);

                if (Tsuro.AI_ANIM) aiAnimation();
                if (Tsuro.PLAY_GAME) tsuro.updateGame(count);

                count++;

            }
        }.start();

        myStage.setTitle("Tsuro");
        myStage.setScene(scene);
        myStage.show();

    }

    private void aiAnimation() {
        if (count % 10 == 0 && !paused) tsuro.playNonLosingMoveFor(0);
    }

}
