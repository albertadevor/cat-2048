package Cat2048;

import javafx.scene.layout.BorderPane;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.animation.Animation;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
* The pane organizer class primarily sets up panes, and the timeline. The
* private timehandler class is also within this class and updates the game
* and controls.
* 
* @author <Alberta Devor>
*
*/

public class PaneOrganizer{
	
	private BorderPane _root;
	private Game _game;
	private Controls _controls;
	
	public PaneOrganizer() {
		
		_root = new BorderPane();
		Pane headerPane = this.headerPane();
		Pane gamePane = new Pane();
		HBox mainPane = new HBox();
		
		VBox controlPane = new VBox();
		controlPane.setStyle("-fx-spacing: 10.0;");
		_controls = new Controls(controlPane);
		
		mainPane.getChildren().addAll(gamePane, controlPane); 
		mainPane.setStyle("-fx-padding:10px;");
		
		_root.setTop(headerPane);
		_root.setCenter(mainPane);
	
		this.setUpBoard(gamePane);
		_game = new Game(gamePane);
		this.setUpTimeline();
	}

	private Pane headerPane() {
		//this private method sets up the header pane and returns it
		StackPane headerPane = new StackPane();
		Text title = new Text("Cat 2048");
		title.setFill(Color.BLACK);
		title.setFont(Font.font("Helvetica", FontWeight.BOLD, 36.0));
		title.setWrappingWidth(Constants.HEAD_WIDTH);
		title.setTextAlignment(TextAlignment.CENTER);
				
		Rectangle headerRect = 
				new Rectangle(Constants.HEAD_WIDTH, Constants.HEAD_HEIGHT);
		headerRect.setFill(Constants.ROSE_THEME);
		headerRect.setArcWidth(Constants.ROUNDING);
		headerRect.setArcHeight(Constants.ROUNDING);
		
		headerPane.getChildren().addAll(headerRect, title);
		headerPane.setAlignment(Pos.CENTER_LEFT);
		headerPane.setStyle("-fx-padding:10px;");
		return headerPane;
	}
	
	private void setUpBoard(Pane pane) {
		Rectangle orangeMain = 
				new Rectangle(Constants.BOARD_LENGTH, Constants.BOARD_LENGTH);
		orangeMain.setFill(Constants.ORANGE_THEME);
		orangeMain.setArcWidth(Constants.ROUNDING);
		orangeMain.setArcHeight(Constants.ROUNDING);
		pane.getChildren().add(orangeMain);
		
		double startX = orangeMain.getX();
		double startY = orangeMain.getY();
		double xVal;
		double yVal;
		Rectangle tile;
		//these instance variables are declared outside of the loop to avoid redeclaring
		
		for(int i=0;i<4;i++) {
			for(int j=0; j<4; j++) {
				xVal = startX + 
					   (Constants.TILE_PADDING * (i+1)) +
					   (Constants.TILE_LENGTH * i);
				yVal = startY +
						(Constants.TILE_PADDING * (j+1)) +
						(Constants.TILE_LENGTH * j);
				//padding gets a "+1" because even the '0'th tile needs padding
				
				tile = new Rectangle(Constants.TILE_LENGTH, Constants.TILE_LENGTH);
				tile.setFill(Constants.PINK_THEME);
				tile.setArcWidth(Constants.ROUNDING);
				tile.setArcHeight(Constants.ROUNDING);
				tile.setX(xVal);
				tile.setY(yVal);
				pane.getChildren().add(tile);
				//changes the x and y values & leaves other attributes the same
			}
		}
	}

	public Pane getRoot() {
		return _root;
	}
	
	private void setUpTimeline() {
		KeyFrame kf = new KeyFrame(Duration.seconds(Constants.DURATION), new TimeHandler());
		Timeline timeline  = new Timeline(kf);
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}
	
	private class TimeHandler implements EventHandler <ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			_game.update();
			boolean gameReset = _game.resetGame(_controls.isNewGame());
			_controls.update(_game.getPoints(),gameReset);
		}
	}
}