package Cat2048;

import javafx.scene.paint.Color;

/**
 * This is where my constants live!
 *   @author <Alberta Devor>
 */

public class Constants{
	public static final double STAGE_HEIGHT = 645; //max height of stage
	public static final double STAGE_WIDTH = 700; //max width of stage
	public static final double DURATION = 0.002; //duration of timeline
	public static final double HEAD_HEIGHT = 69.52; //height of header rectangle
	public static final double HEAD_WIDTH = 245.79; //width of header rectangle
	public static final int ROUNDING = 10; //rounding constant for all boxes
	public static final double BOARD_LENGTH = 505; //length of game board
	public static final double TILE_PADDING = 13.0; //length of padding between tiles
	public static final double TILE_LENGTH = 110.0; //length of tiles
	public static final double SCORE_PADDING = 35.0; //padding inside control boxes
	public static final int ROWS = 4; //number of columns and rows in the board
	public static final double ACCELERATION = 1000; //acceleration in movement
	public static final double SHIFT = 1; //pixels shifted each update loop
	public static final double FONT = 24.0;
	
	//below are the important colors in the theme
	public static final Color ROSE_THEME = Color.rgb(241, 94, 96); 
	public static final Color PINK_THEME = Color.rgb(244, 127, 120); 
	public static final Color ORANGE_THEME = Color.rgb(246, 145, 104);
}