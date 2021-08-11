import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Cell extends Rectangle {
	private int Xc; // x coordinate on the grid
	private int Yc; // y coordinate on the grid
	private Grid grid;
	private Text coordinates;

	public Cell(Grid g) {
		super(0, 0, 10, 10);
		super.setFill(Color.RED);
		// TODO Auto-generated constructor stub
	}

	public Cell(Grid g, int x, int y) {
		super(0, 0, 0, 0);
		Xc = x;
		Yc = y;
		grid = g;
		this.setHeight(grid.YcToProperty(1));
		this.setWidth(grid.XcToProperty(1));
		this.setX(grid.XcToProperty(Xc));
		this.setY(grid.YcToProperty(Yc));
		this.setStyle("-fx-stroke-type:inside ;-fx-stroke: black;-fx-stroke-width:3;");
		coordinates = new Text();

		Random r = new Random();
		int num = r.nextInt(5 - 1) + 1;
		Color c1 = Color.WHITE;
		Color c2 = Color.DARKRED;
		switch (num) {
		case (1):
			c1 = Color.RED;
			c2 = Color.LIGHTPINK;
			break;
		case (2):
			c1 = Color.DARKRED;
			c2 = Color.PALEVIOLETRED;
			break;
		case (3):
			c1 = Color.PALEVIOLETRED;
			c2 = Color.DARKRED;
			break;
		case (4):
			c1 = Color.MEDIUMVIOLETRED;
			c2 = Color.LIGHTPINK;
			break;
		}
		super.setFill(c1);
		coordinates.setFill(c2);

		grid.add(this);
		coordinates.setText("(" + x + "," + y + ")");
		coordinates.xProperty().bind(this.xProperty().add(3));
		coordinates.yProperty().bind(this.yProperty().add(18));
		grid.getChildren().add(coordinates);

		mouseDrag(true);
	}

	public int getXc() {
		return Xc;
	}

	public int getYc() {
		return Yc;
	}

	public Grid getGrid() {
		return grid;
	}

	public void setXc(int xc) {
		Xc = xc;
		this.setX(grid.XcToProperty(Xc));
		coordinates.setText("(" + Xc + "," + Yc + ")");
	}

	public void setYc(int yc) {
		Yc = yc;
		this.setY(grid.YcToProperty(Yc));
		coordinates.setText("(" + Xc + "," + Yc + ")");
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public void Move(int x, int y) throws existException {
		grid.updateBoard(this, x, y);
	}

	public void suicide() {
		grid.suicideUpdate(this);
		grid.getChildren().remove(coordinates);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		else
			return super.equals(o);
	}

	public void mouseDrag(boolean on) {
		if (on) {
			this.setOnMouseDragged(e -> {
				try {
					int newXc = grid.PropertyToXc(e.getX());
					int newYc = grid.PropertyToYc(e.getY());
					Move(newXc, newYc);
				} catch (existException e1) {
					//System.out.println("exist!");
				} catch (java.lang.ArrayIndexOutOfBoundsException e2) {
					//System.out.println("out of bounds!");
				}
			});
			coordinates.setOnMouseDragged(e -> {
				try {
					int newXc = grid.PropertyToXc(e.getX());
					int newYc = grid.PropertyToYc(e.getY());
					Move(newXc, newYc);
				} catch (existException e1) {
					//System.out.println("exist!");
				} catch (java.lang.ArrayIndexOutOfBoundsException e2) {
					//System.out.println("out of bounds!");
				}
			});
		}
		else {
			this.setOnMouseDragged(e -> {});
			coordinates.setOnMouseDragged(e -> {});
		}
	}

}
