package Cat2048;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.ImagePattern;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import java.util.ArrayList;

/**
 * 
 * This class contains the main game logic of Cat 2048, and allows for the 
 * combining, sliding, and creation of tiles. The 2d Array is in column-row
 * order.
 * 
 * @author <Alberta Devor>
 *
 */

public class Game {
	
	private Tile[][] _board;
	private Pane _gamePane;
	private ArrayList<Tile> _toDelete;
	private boolean _spawnTile;
	private boolean _isGameOver;
	private int _points;
	
	public Game(Pane pane) {
		_board = new Tile[Constants.ROWS][Constants.ROWS];
		_toDelete = new ArrayList<Tile>();
		/*
		 * toDelete is so that at the end of each turn i can delete 
		 * all of the tiles all at once, but until the end of each turn they
		 * need to be kept track of
		 */
		
		_gamePane = pane;
		this.generateNewTile(pane);
		this.generateNewTile(pane);
		
		_spawnTile = false;
		//this bool tracks whether a new tile needs to be spawned
		_isGameOver = false;
		_points = 0;
		
		pane.setOnKeyPressed(new KeyHandler());
		pane.setFocusTraversable(true);
		//associates pane and KeyHandler
	}
	
	public int getPoints() {
		return _points;	
	}

	public boolean resetGame(boolean isNewGame) {
		if(isNewGame) {
			_points = 0;
			for(int col=0;col<Constants.ROWS;col++) {
				for(int row=0;row<Constants.ROWS;row++) {
					if(_board[col][row]!=null){
						_gamePane.getChildren().remove(_board[col][row].getBody());
						_board[col][row] = null;
					}
				}
			}
			_toDelete.clear();
			_spawnTile = false;
			
			this.generateNewTile(_gamePane);
			this.generateNewTile(_gamePane);
			return true;
			//true means the game successfully reset 
		}else{
			return false;	
		}
	}
	
	public void update() {
		//this is the main update function for the Game class
		if(this.isDead()) {
			this.deadGraphic();
			_isGameOver = true;
		}
		if(!_isGameOver) {
			//only update if the game isn't over
			if(!this.isTurnOver()){
				for(int i=0;i<_toDelete.size();i++) {
					this.updateTilePos(_toDelete.get(i));
				}
				for(int col=0;col<Constants.ROWS;col++) {
					for(int row=0;row<Constants.ROWS;row++) {
						this.updateTilePos(_board[col][row]);
						if (_board[col][row]!=null) {
							if (_board[col][row].getID()==2048) {
								_isGameOver =true;
								this.winGraphic();
							}
						}
					}
				}
			}else {
				for(int i=0;i<_toDelete.size();i++) {
					_gamePane.getChildren().remove(_toDelete.get(i).getBody());
					_toDelete.remove(i);
					//visually and logically removes all tiles that need to be deleted
				}
				if(_spawnTile) {
					this.generateNewTile(_gamePane);
					_spawnTile =false;
					//generate a tile if the turn is over and something moved
				}
				for(int col=0;col<Constants.ROWS;col++) {
					for(int row=0;row<Constants.ROWS;row++) {
						if(_board[col][row]!=null){
							_board[col][row].setHasCombined(false);
							//resets tile's status as already combined 
						}
	
					}
				}
			}
		}
	}
	
	private void updateTilePos(Tile tile) {	
		//updates tile position in the update function
				if(tile!=null) {
					double currX = tile.getX();
					double currY = tile.getY();
					
					int slideRow = tile.getSlideTo().getRow();
					int slideCol = tile.getSlideTo().getCol();
					
					double slideX = this.getXFromCol(slideCol);
					double slideY = this.getYFromRow(slideRow);
	
					double distX = slideX -currX;
					double distY = slideY - currY;
					
					if(distX>0) {
						tile.setX(currX + Constants.SHIFT);
					}else if(distX<0) {
						tile.setX(currX - Constants.SHIFT);
					}else if(distY>0) {
						tile.setY(currY + Constants.SHIFT);
					}else if(distY<0) {
						tile.setY(currY - Constants.SHIFT);
					}
					
					if(distX==0 && distY==0) {
						//if the tile is done sliding or combining
						if(tile.isSliding()) {
							tile.setSliding(false);
						}
						if(tile.isCombining()) {
							tile.setCombining(false);
							 int id = _board[slideCol][slideRow].getID();
							 _board[slideCol][slideRow].setID(id*2);	
							 _points+=(id*2);
						}	
					}
		}
	}
	
	private boolean isDead() {
		//checks if the user is dead
		for(int col=0;col<Constants.ROWS;col++) {
			for(int row=0;row<Constants.ROWS;row++) {
				if (_board[col][row] == null) {
					return false;
					//if a single block is null the game is not over
				}
				Direction[] directions = new Direction[4];
				directions[0] = Direction.RIGHT;
				directions[1] = Direction.LEFT;
				directions[2] = Direction.UP;
				directions[3] = Direction.DOWN;
				
				for(int i = 0;i<directions.length;i++) {
					if(this.canCombine(col, row, directions[i])) {
						return false;
						//if the board is full but tiles can combine, the game isn't over
					}
				}
			}
		}
		return true;
		//return true if none of the conditions are met
	}
	
	private void deadGraphic() {
		Rectangle full = new Rectangle(Constants.BOARD_LENGTH, Constants.BOARD_LENGTH);
		full.setArcWidth(Constants.ROUNDING);
		full.setArcHeight(Constants.ROUNDING);
		Image dead = new Image("Cat2048/Images/Dead_Cat.png");
		full.setFill(new ImagePattern(dead, 0, 0, 1, 1, true));
		_gamePane.getChildren().add(full);
	}
	
	private void winGraphic() {
		Rectangle full = new Rectangle(Constants.BOARD_LENGTH, Constants.BOARD_LENGTH);
		full.setArcWidth(Constants.ROUNDING);
		full.setArcHeight(Constants.ROUNDING);
		Image win = new Image("Cat2048/Images/Win.png");
		full.setFill(new ImagePattern(win, 0, 0, 1, 1, true));
		_gamePane.getChildren().add(full);
	}

	public void moveTiles(Direction dir) {		
		/*
		 * The tiles are assumed to not be able to move until otherwise proven.
		 * anyCanMove is important to keep track of because if they can't move 
		 * a new tile should not be generated.
		 */
		boolean anyCanMove = false;
		/*
		 * The switch statements all go through the tiles in their own way 
		 * because each direction has to go through the tiles in the 
		 * opposite direction to their movement so that they don't recombine
		 * tiles twice
		 */
		switch(dir) {
			case LEFT:
				for(int row=0;row<Constants.ROWS;row++) {
					for(int col=0; col<Constants.ROWS;col++) {
						if(!anyCanMove) {
							anyCanMove = this.isMovable(col,row,dir);
						} 
						/*
						 * This isn't else-if but instead two if statements
						 * because if anyCanMove JUST changed to true, it 
						 * should be able to move into the next block.
						 */
						if(anyCanMove) {
							this.move(col, row, dir);
						}
					}
				}
				break;
			case RIGHT:
				for(int row=0;row<Constants.ROWS;row++) {
					for(int col=Constants.ROWS -1; col>=0;col--) {
						if(!anyCanMove) {
							anyCanMove = this.isMovable(col,row,dir);
						}
						if(anyCanMove) {
							this.move(col, row, dir);
						}
					}
				}
				break;
			case UP:
				for(int row=0;row<Constants.ROWS;row++) {
					for(int col=Constants.ROWS -1; col>=0;col--) {
						if(!anyCanMove) {
							anyCanMove = this.isMovable(col,row,dir);
						}
						if(anyCanMove) {
							this.move(col, row, dir);
						}
					}
				}
				break;
			default:
				for(int row=Constants.ROWS -1 ;row>=0;row--) {
					for(int col=Constants.ROWS -1; col>=0;col--) {
						if(!anyCanMove) {
							anyCanMove = this.isMovable(col,row,dir);
						}
						if(anyCanMove) {
							this.move(col, row, dir);
						}
					}
				}
				break;
		}
		if(anyCanMove) {
			_spawnTile = true;
			//generate a tile if the turn is over and something moved
		}
	}
	
	private void move(int col, int row, Direction dir) {
		/*
		 * This method doesn't actually move the tiles, it just sets where the 
		 * tile should slide to.
		 */
		boolean canMove = this.isMovable(col, row, dir);
		int newRow = row;
		int newCol = col;
		Tile original = _board[col][row];
		while(canMove) {
			newCol = this.getDirCoords(col, row, dir).getCol();
			newRow = this.getDirCoords(col, row, dir).getRow();
			
			if(this.canCombine(col, row, dir)) {
				Coordinate slideTo = new Coordinate(newCol, newRow);
				Tile toDelete = _board[newCol][newRow];
				_toDelete.add(toDelete);
				_board[newCol][newRow] = original;
				_board[col][row] = null;
				_board[newCol][newRow].setSlideTo(slideTo);
				_board[newCol][newRow].setCombining(true);
				_board[newCol][newRow].setHasCombined(true);
				//setHasCombined makes it so a tile can only combine once a turn
			}else if(this.canShift(col, row, dir)) {
				Coordinate slideTo = new Coordinate(newCol, newRow);
				_board[newCol][newRow] = original;
				_board[col][row] = null;
				_board[newCol][newRow].setSlideTo(slideTo);
				_board[newCol][newRow].setSliding(true);
			}else{
				canMove = false;
			}
			row = newRow;
			col = newCol;
			if(this.isInBounds(col, row)) {
				original = _board[col][row];
			}
			//row, col, & original are always one behind newRow and newCol
		}
	}
	
	private boolean isMovable(int col, int row, Direction dir) {
		//returns whether a tile can move given its spot and direction
		boolean canShift = false;
		boolean canCombine = false;
		Tile current = _board[col][row];
		if(current == null) {
			return false;
		}
		int newRow = this.getDirCoords(col, row, dir).getRow();
		int newCol = this.getDirCoords(col,row,dir).getCol();
		
		if(!this.isInBounds(newCol, newRow)){
			return false;
		}
		Tile newTile = _board[newCol][newRow];
		if(newTile ==null) {
			canShift = true;
		}else if(newTile.getID()==current.getID()) {
			canCombine = true;
		}
		if(canShift||canCombine) {
			return true;
			//true if the tile can shift one over or combine into the next tile
		}else{
			return false;
		}
	}
	
	private boolean canShift(int col, int row, Direction dir) {
		//returns whether a tile can shift given spot and direction
		Tile current = _board[col][row];
		if(!this.isInBounds(col, row)){
			return false;
			//double checking in case bad coordinates have been passed in
		}
		if(current == null) {
			return false;
		}
		int newCol = this.getDirCoords(col,row,dir).getCol();
		int newRow = this.getDirCoords(col,row,dir).getRow();
		if(!this.isSpotOk(newCol, newRow)){
			return false;
		}else{
			return true;
		}
	}
	
	private boolean isTurnOver() {
		for(int row=0;row<Constants.ROWS;row++) {
			for(int col=0; col<Constants.ROWS;col++) {
				Tile tile = _board[col][row];
				if(tile!=null){
					if(tile.isCombining()||tile.isSliding()) {
						return false;
					}
				}
			}
		}
		return true;
		//returns true only if no tiles are sliding or combining
	}
	
	private boolean canCombine(int col, int row, Direction dir) {
		/*
		 * checks if tile can combine with a new tile given the old tile's
		 * locus and the given direction
		 */

		int newRow = this.getDirCoords(col,row,dir).getRow();
		int newCol = this.getDirCoords(col,row,dir).getCol();
		
		if(!this.isInBounds(col, row) || !this.isInBounds(newCol, newRow)){
			return false;
		}else{
			Tile current = _board[col][row];
			Tile newTile = _board[newCol][newRow];
			
			if(newTile==null||current==null){
				return false;
			}else if(newTile.getID()==current.getID()) {
				if(!newTile.hasCombined() && !current.hasCombined()){
					return true;
				}else {
					return false;
				}
			}else {
				return false;
			}
		}
	}
	
	private Coordinate getDirCoords(int col, int row, Direction dir) {
		//gets the new coords given direction and previous coords
		int newRow = row;
		int newCol = col;
		switch(dir) {
			case LEFT:
				newCol = (col-1);
				break;
			case RIGHT:
				newCol = (col+1);
				break;
			case UP:
				newRow = (row-1);
				break;
			default:
				newRow = (row+1);
				break;
		}
		return new Coordinate(newCol,newRow);
	}

	private boolean isInBounds(int col, int row) {
		//checks if the tile is in the bounds of the board
		if(col>3||col<0) {
			return false;
		}else if(row>3||row<0) {
			return false;
		}else {
			return true;
		}
	}
	
	private void generateNewTile(Pane pane) {
		int id;
		int randID = (int) (Math.random() * 11);
		if(randID/10==1) {
			id = 4;
		}else{
			id = 2;
		}
		//each new tile has to be either a 4 or a 2
		
		int randCol = (int) (Math.random() * Constants.ROWS);
		int randRow = (int) (Math.random() * Constants.ROWS);
		
		while(_board[randCol][randRow] != null) {
			randCol = (int) (Math.random() * Constants.ROWS);
			randRow = (int) (Math.random() * Constants.ROWS);
			//keeps finding a new coordinate until it's valid
		}
		if(_board[randCol][randRow] == null) {
			//the if statement is just a fail-safe & shouldn't be necessary
			Tile newTile = new Tile(randCol, randRow, id);
			_board[randCol][randRow] = newTile;
			double xVal = this.getXFromCol(randCol);
			double yVal = this.getYFromRow(randRow);
			newTile.setX(xVal);
			newTile.setY(yVal);
			pane.getChildren().add(newTile.getBody());
		}
	}
	
	private double getXFromCol(int col) {
		double xVal = (Constants.TILE_PADDING * (col+1)) +
			          (Constants.TILE_LENGTH * col);
		return xVal;
	}
	private double getYFromRow(int row) {
		double yVal = (Constants.TILE_PADDING * (row+1)) +
		               (Constants.TILE_LENGTH * row);
		return yVal;
	}
	
	private boolean isSpotOk(int col, int row) {
		//checks if the spot is in bounds and empty
		if(!this.isInBounds(col,row)) {
			return false;
		}else if (_board[col][row] ==null){
			return true;
		}else{
			return false;
		}
	}
	
	private class KeyHandler implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent event) {
			KeyCode keyPressed = event.getCode();
			if (keyPressed == KeyCode.LEFT) {			
				Game.this.moveTiles(Direction.LEFT);
			}else if(keyPressed == KeyCode.RIGHT) {			
				Game.this.moveTiles(Direction.RIGHT);
			}else if(keyPressed == KeyCode.DOWN) {
				Game.this.moveTiles(Direction.DOWN);
			}else if(keyPressed ==KeyCode.UP) {
				Game.this.moveTiles(Direction.UP);
			}
			event.consume();
		}
	}
}