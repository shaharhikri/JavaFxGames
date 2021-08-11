import java.util.ArrayList;
import java.util.Random;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Snake {
	private ArrayList<Rectangle> body;
	private Rectangle food;
	private boolean gameOver;
	Dir direction=Dir.LEFT;

	public boolean isGameOver() {
		return gameOver;
	}

	public Snake() {
		this.body = new ArrayList<>();
		gameOver=false;
		body.add(createNode(0, 0));
		body.get(0).setFill(Color.RED);

		body.add(createNode(140, 150));

		Random r = new Random();
		food = createNode(r.nextInt(30) * 10, r.nextInt(30) * 10);
		food.setFill(Color.BEIGE);
		
	}


	public void Move(Pane pane) {
		Rectangle head = body.get(0);
		Rectangle newNode = createNode(0, 0);
		swap(newNode, body.get(body.size() - 1));

		for (int i = body.size() - 1; i > 0; i--) {
			swap(body.get(i), body.get(i - 1));
		}

		if (direction == Dir.RIGHT) {
			if (head.getX() < Main.SIZE)
				head.setX((head.getX() + 10));
			else
				head.setX(0);

		}

		else if (direction == Dir.LEFT) {
			if (head.getX() > 0)
				head.setX((head.getX() - 10));
			else
				head.setX(300);
		}

		else if (direction == Dir.DOWN) {
			if (head.getY() < Main.SIZE)
				head.setY((head.getY() + 10));
			else
				head.setY(0);
		}

		else if (direction == Dir.UP) {
			if (head.getY() > 0)
				head.setY((head.getY() - 10));
			else
				head.setY(300);
		}

		if (food.getX() == head.getX() && food.getY() == head.getY()) {
			body.add(newNode);
			pane.getChildren().add(newNode);
			Random r = new Random();
			food.setX(r.nextInt(30) * 10);
			food.setY(r.nextInt(30) * 10);
		}

		updateGameOver();	
	}

	
	
	public Dir getDirection() {
		return direction;
	}

	public boolean setDirection(Dir dir) {
		if(direction == dir)
			return false;
		if(direction==Dir.RIGHT && dir==Dir.LEFT)
			return false;
		if(direction==Dir.LEFT && dir==Dir.RIGHT)
			return false;
		if(direction==Dir.UP && dir==Dir.DOWN)
			return false;
		if(direction==Dir.DOWN && dir==Dir.UP)
			return false;
		this.direction = dir;
		System.out.print(dir+" ");
	    return true;
	}

	public ArrayList<Rectangle> getBody() {
		return body;
	}

	public void setBody(ArrayList<Rectangle> body) {
		this.body = body;
	}

	private void swap(Rectangle r2, Rectangle r1) { // r1 coordinate into r2
		double x1 = r1.getX();
		double y1 = r1.getY();

		r2.setX(x1);
		r2.setY(y1);

	}

	private Rectangle createNode(double x, double y) {
		Rectangle rec = new Rectangle(x, y, 10, 10);
		rec.setFill(Color.AQUAMARINE);
		rec.setStroke(Color.BLACK);
		return rec;
	}

	public void setOnPane(Pane movingPane) {
		ArrayList<Rectangle> body = this.getBody();
		for (int j = 0; j < body.size(); j++) {
			movingPane.getChildren().add(body.get(j));
		}
		movingPane.getChildren().add(food);
	}

	public void updateGameOver() {
		Rectangle head = body.get(0);
		for (int i = 1; i < body.size(); i++) {
			if ((body.get(i).getX() == head.getX()) && (body.get(i).getY() == head.getY())) {
				//printBody();
				gameOver=true;
			}
		}
	}

	public void printBody() {
		System.out.println("-------------------------------------------------------------------------");
		for (int i = 0; i < body.size(); i++) {
			System.out.println(((i!=0)? i+"" : "head")  + "  ->   " + body.get(i).getX() + " , " + body.get(i).getY());
		}
		System.out.println("-------------------------------------------------------------------------");
	}
}
