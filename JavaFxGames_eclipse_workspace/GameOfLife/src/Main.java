import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
	private BorderPane mainPane;
	private Grid grid;

	public static int SIZE = 10;

	private TextField tfX;
	private TextField tfY;

	public void start(Stage primaryStage) throws Exception {
		grid = new Grid(SIZE, SIZE, 300, 300);

		mainPane = new BorderPane();
		mainPane.setStyle(
				"-fx-background-color: linear-gradient(from 13px 38px to 52px 52px, reflect,BLUEVIOLET , INDIGO 88%);");
		mainPane.getChildren().add(grid);
		
		Scene scene = new Scene(mainPane, 300-10, 400-10);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		
		new Cell(grid, 3, 3);
		new Cell(grid, 5, 3);
		new Cell(grid, 4, 4);
		new Cell(grid, 3, 5);
		new Cell(grid, 5, 5);
		inputCells();
		
		primaryStage.show();
		primaryStage.setTitle("Game Of Life");
	}

	public void inputCells() {
		GridPane grd = new GridPane();

		grd.setHgap(10);
		grd.setVgap(5);

		Button create = new Button("create");
		Button close = new Button("done!");
		Text tX=new Text("X coordinate: ");
		tX.setFill(Color.WHITE);
		Text tY=new Text("Y coordinate: ");
		tY.setFill(Color.WHITE);
		grd.add(tX ,1 ,1);
		grd.add(tY, 1, 2);

		tfX = new TextField();
		tfY = new TextField();
		create.setOnAction(new crHandler());

		grd.add(tfX, 2, 1);
		grd.add(tfY, 2, 2);
		grd.add(close, 3, 3);
		grd.add(create, 1, 3);

		mainPane.setBottom(grd);
		close.setOnAction(e -> {
			mainPane.getChildren().remove(mainPane.getChildren().size() - 1);
			move();
		});
	}

	public void move() {		
		Button mv = new Button("Move");
		mv.setOnAction(e -> grid.move());
		mainPane.setBottom(mv);
		mv.setAlignment(Pos.TOP_CENTER);
	}

	class crHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			//System.out.println("create button clicked");
			try {
				new Cell(grid, Integer.parseInt(tfX.getText()), Integer.parseInt(tfY.getText()));
			} catch (java.lang.NumberFormatException exp) {
				System.out.println("input mismatch");
			}
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
