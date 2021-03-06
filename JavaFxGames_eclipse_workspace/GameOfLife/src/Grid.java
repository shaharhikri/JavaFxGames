import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Grid extends Pane {
	private int gridHeight;
	private int gridWidth;
	private Rectangle background;
	private Cell[][] board;

	public Grid(int gridHeight, int gridWidth, int x, int y) {
		super();
		this.gridHeight = gridHeight;
		this.gridWidth = gridWidth;
		this.setHeight(y);
		this.setWidth(x);
		board = new Cell[gridHeight][gridWidth];
		background = new Rectangle((double) x, (double) y);
		background.setStyle("-fx-stroke-type:inside ;-fx-stroke: black;-fx-stroke-width:2;"); background.setY(1);
		background.setFill(Color.INDIGO);
		this.getChildren().add(background);
	}

	public double XcToProperty(int Xc) {
		double num = (Xc * this.getWidth()) / gridWidth;
		return num;
	}

	public double YcToProperty(int Yc) {
		double num = (Yc * this.getHeight()) / gridHeight;
		return num;
	}
	
	public int PropertyToXc(double num) {
//		x = x/gridWidth;
//		x = x*gridWidth;
		int Xc = (int) ((num * gridWidth) / this.getWidth() );
		return Xc;
	}

	public int PropertyToYc(double num) {
		int Yc = (int) ((num * gridWidth) / this.getWidth() );
		return Yc;
	}

	public int getGridHeight() {
		return gridHeight;
	}

	public int getGridWidth() {
		return gridWidth;
	}

	public void add(Cell n) {
		try {
			if (board[n.getYc()][n.getXc()] != null)
				return;
			this.getChildren().add(n);
			board[n.getYc()][n.getXc()] = n;
			//System.out.println("created!");
		} catch (java.lang.ArrayIndexOutOfBoundsException w) {
			//System.out.println("Out Of Bounds");
			return;
		}
	}

	public void updateBoard(Cell c, int newX, int newY) throws existException {
		if(board[newY][newX]!=null)
			throw new existException();
		int x = c.getXc();
		int y = c.getYc();
		board[y][x] = null;
		board[newY][newX] = c;
		c.setXc(newX);
		c.setYc(newY);
	}

	public void suicideUpdate(Cell c) {
		this.getChildren().remove(c);
		int x = c.getXc();
		int y = c.getYc();
		board[y][x] = null;
	}

	public void move() {
		Cell[][] boardCopy = new Cell[gridHeight][gridWidth];
		//Cell[][] updatedBoard= new Cell[gridHeight][gridWidth];
		copyBoard(boardCopy , board);
		setBoard(boardCopy);
	}
	
	
	private boolean setBoard(Cell[][] arr){ 
        int neighborsNum; // neghbors number of each cell
         for(int i=0;i<arr.length;i++) {
		             for(int j=0;j<board[0].length;j++){
		       	           neighborsNum=getNeighbors(i,j,arr);
			               if(arr[i][j]!=null){ //if the cell is alive check if it stand to term to live and if not kills it
				                 if(!(neighborsNum>=2 && neighborsNum<=4) && arr[i][j]!=null) {
				                	 arr[i][j].suicide(); 	
				                 }
			                }		      
				            else{ //if the cell is dead check if it stand the terms to live and bring it to life
				                  if(neighborsNum==3 && arr[i][j]==null)
				                	  new Cell(this,j,i);
			                }
		              }
	          }
          return true; 	 
}
	
	
	
	

	private static int getNeighbors(int l, int c, Cell[][] b) { // the method het place and array and return the
		// number of the living neighbors
		int count = 0; // the number of the cell neighbors in place [l][c] , 'l' represent line 'c'
// represent column.
		if (b.length == 1)// for 1x1 array -in this case the cell die - doesnt need checking.
			return 0;

// for size more than 1x1 :
		int lBack = backPlace(l, b); // The line above
		int lFor = forwardPlace(l, b); // the line below
		int cBack = backPlace(c, b); // the column from left
		int cFor = forwardPlace(c, b); // the next column from right
// checking all 8 neghbors:
		if (b[lBack][cBack] != null)
			count++;
		if (b[lBack][c] != null)
			count++;
		if (b[lBack][cFor] != null)
			count++;
		if (b[l][cBack] != null)
			count++;
		if (b[l][cFor] != null)
			count++;
		if (b[lFor][cBack] != null)
			count++;
		if (b[lFor][c] != null)
			count++;
		if (b[lFor][cFor] != null)
			count++;

		return count;
	}

	private static int backPlace(int x, Cell[][] a) { // the method get a place (line or column) in symetricial array
// and return the place from behind (only for getNeighbors
// method use)
		int backP = x - 1; // Moves the variable backward.
		if (backP < 0) // If the variable exits from the array boundary, it take it to the end.
			backP = a.length - 1;
		return backP;
	}

	private static int forwardPlace(int x, Cell[][] a) { // the method get a place (line or column) in symetricial
// array and return the next place (only for getNeighbors
// method use)
		int forP = x + 1; // Moves the variable forward.
		if (forP > a.length - 1) // If the variable exits from the array boundary, it returns it to the
// beginning.
			forP = 0;
		return forP;
	}

	private static void copyBoard(Cell[][] b ,Cell[][] a) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++)
				b[i][j] = a[i][j];
		}
	}
}
