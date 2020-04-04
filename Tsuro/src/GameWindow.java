import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameWindow {

    private Tsuro tsuro;
    private int count = 1;

    public GameWindow(Tsuro myTsuro, Stage myStage) {

        tsuro = myTsuro;

        StackPane root = new StackPane();
        Canvas canvas = new Canvas(Board.SIZE, Board.SIZE);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Animation loop - runs at (about) 60fps
        new AnimationTimer() {
            public void handle(long nano) {

                tsuro.showBoard(gc);
                tsuro.showPlayers(gc);

                if (Tsuro.TESTING) {
                    if (count % 100 == 0) tsuro.testStuff();
                }

                count++;

            }
        }.start();

        myStage.setTitle("Tsuro");
        myStage.setScene(scene);
        myStage.show();

    }

}
