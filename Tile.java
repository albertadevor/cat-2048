package Cat2048;

import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
*  The Tile class records many aspects of each tile on the board through 
*  private instance variables and their setters and getters, such as the id 
*  (e.g. 2, 4, 8, 16 etc.), the location the tile is sliding to, it's row, 
*  column, and whether it's currently sliding, or moving to combine with
*  other tiles. 
*
* @author <Alberta Devor>
*
*/

public class Tile {
	
	private int _id;
	private Rectangle _body;
	private Coordinate _slideTo;
	private int _row;
	private int _col;
	private boolean _isSliding;
	private boolean _isCombining;
	private boolean _hasCombined;
	
	public Tile(int col, int row, int id) {
		_body = new Rectangle(Constants.TILE_LENGTH, Constants.TILE_LENGTH);
		_body.setArcWidth(Constants.ROUNDING);
		_body.setArcHeight(Constants.ROUNDING);
		_id = id;
		this.updateVisualTile();
		
		_row = row;
		_col = col;
		_slideTo = new Coordinate(col,row);
		_isSliding = false;
		_isCombining = false;
		/*
		 * _isSliding and _isCombining record whether a tile is currently 
		 * sliding or combining which is important so that they can be 
		 * kept track of and appropriately updated throughout their sliding 
		 * journey!
		 */
		_hasCombined = false;
		//has combined keeps track of if a tile has already combined once in a turn
	}
	
	private void updateVisualTile() {
		
		Image catPic = new Image("Cat2048/Images/2_Cat.jpg");
		switch(_id) {
			case 2:
				catPic = new Image("Cat2048/Images/2_Cat.jpg");
				break;
			case 4:
				catPic = new Image("Cat2048/Images/4_Cat.jpg");
				break;
			case 8:
				catPic = new Image("Cat2048/Images/8_Cat.jpg");
				break;
			case 16:
				catPic = new Image("Cat2048/Images/16_Cat.jpg");
				break;
			case 32:
				catPic = new Image("Cat2048/Images/32_Cat.jpg");
				break;
			case 64:
				catPic = new Image("Cat2048/Images/64_Cat.jpg");
				break;
			case 128:
				catPic = new Image("Cat2048/Images/128_Cat.jpg");
				break;
			case 256:
				catPic = new Image("Cat2048/Images/256_Cat.jpg");
				break;
			case 512:
				catPic = new Image("Cat2048/Images/512_Cat.jpg");
				break;
			case 1024:
				catPic = new Image("Cat2048/Images/1024_Cat.jpg");
				break;
			case 2048:
				catPic = new Image("Cat2048/Images/2048_Cat.jpg");
				break;
		}
		_body.setFill(new ImagePattern(catPic, 0, 0, 1, 1, true));
	}
	
	public void setID(int num) {
		_id = num;
		this.updateVisualTile();
		//the visual is updated whenever the ID is changed
	}
	
	public void setX(double x) {
		_body.setX(x);
	}
	public void setY(double y) {
		_body.setY(y);
	}
	public Rectangle getBody() {
		return _body;
	}
	
	public int getID() {
		return _id;
	}
	
	public double getX() {
		return _body.getX();
	}
	
	public double getY() {
		return _body.getY();
	}

	public Coordinate getSlideTo() {
		return _slideTo;
	}

	public void setSlideTo(Coordinate slideTo) {
		_slideTo = slideTo;
	}
	
	public void setCurrCoord(Coordinate coord) {
		_row = coord.getRow();
		_col = coord.getCol();
	}
	
	public Coordinate getCurrCoord() {
		return new Coordinate(_col,_row);
	}
	
	public void setSliding(boolean bool) {
		_isSliding= bool;
	}
	
	public boolean isSliding() {
		return _isSliding;
	}
	public void setCombining(boolean bool) {
		_isCombining= bool;
	}
	
	public boolean isCombining() {
		return _isCombining;
	}
	
	public boolean hasCombined() {
		return _hasCombined;
	}
	public void setHasCombined(boolean bool) {
		_hasCombined = bool;
	}
	
}