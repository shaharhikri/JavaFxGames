import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
	public static final int SIZE = 300;
	public static final int DELAYTIE = 80; //LOWER IS HARDER
	private Pane pane;
	private boolean movedInThisCircle=false;

	@Override
	public void start(Stage primaryStage) throws Exception {
		pane = new Pane();
		pane.setPrefSize(SIZE, SIZE);
		pane.setStyle("-fx-background-color: INDIGO;");
		Snake snake = new Snake();
		snake.setOnPane(pane);
		Rectangle rec = snake.getBody().get(0);

		// Create an animation for alternating text
		Timeline delay = new Timeline(new KeyFrame(Duration.millis(DELAYTIE), e -> {
			if (snake.isGameOver())	{				
				finishGame();
			}
			movedInThisCircle=false;
			snake.Move(pane);
			rec.requestFocus();
		}));		
		delay.setCycleCount(Timeline.INDEFINITE);
		delay.play(); // Start animation
		// ============================================
		Text score = new Text();
		
		score.setFont(new Font(18));
		score.setFill(Color.AQUA);
		score.setX(5);
		score.setY(SIZE - 5);

		rec.setOnKeyPressed(e -> {
			if(!movedInThisCircle) {
				switch (e.getCode()) {
				case DOWN:
					movedInThisCircle=snake.setDirection(Dir.DOWN);
					break;
				case UP:
					movedInThisCircle=snake.setDirection(Dir.UP);
					break;
				case LEFT:
					movedInThisCircle=snake.setDirection(Dir.LEFT);
					break;
				case RIGHT:
					movedInThisCircle=snake.setDirection(Dir.RIGHT);
					break;
				case F:	finishGame();
				default:
				}					
					
				score.setText("SCORE: " + (snake.getBody().size() - 2));
			
			}
		});
		rec.requestFocus();
		pane.getChildren().add(score);
		Text finishMessage = new Text("click 'F' to finish");
		finishMessage.setX(5);
		finishMessage.setY(15);
		finishMessage.setFill(Color.DEEPSKYBLUE);
		pane.getChildren().add(finishMessage);

		// =============================================

		Scene scene = new Scene(pane, SIZE, SIZE);
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.setResizable(false);
		primaryStage.show();
		primaryStage.setTitle("Snake");
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void finishGame() {
		Text GOtext = new Text("GAME OVER!");
		GOtext.setFont(new Font("Ariel", 30));
		GOtext.setFill(Color.LIGHTCYAN);
		GOtext.setStyle("-fx-font-weight: bold");
		GOtext.setX(SIZE /6);
		GOtext.setY(SIZE /2);
		pane.getChildren().clear();
		pane.getChildren().add(GOtext);
		System.out.println("game is finish!");
	}
	

}
