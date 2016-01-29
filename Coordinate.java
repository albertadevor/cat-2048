package Cat2048;

public class Coordinate {
	
	private int _row;
	private int _col;
	
	public Coordinate(int col, int row) {
		_col = col;
		_row = row;
	}
	
	public int getCol() {
		return _col;
	}
	
	public int getRow() {
		return _row;
	}
	public void setCol(int col) {
		_col = col;
	}
	public void setRow(int row) {
		_row = row;
	}

}