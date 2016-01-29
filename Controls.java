package Cat2048;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * The controls class updates the score and best label and whether the 
 * game has been reset or not. The score itself is kept track of in the Game
 * class, but is also kept track of in this class by passing in scores from Game. 
 * The private class NewGameHandler is also inside of this class because
 * it keeps track of whether the new game button has been pressed.
 *
 * @author <Alberta Devor>
 *
 */

public class Controls{

	private Text _scoreLabel;
	private Text _bestLabel;
	private int _best;
	private int _score;
	private boolean _newGame;
	
	public Controls(VBox pane) {
		
		this.setUpScorePane(pane);
		this.setUpBestPane(pane);
		this.setUpNewGamePane(pane);
		
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(0, 0, 0, 10));
		//setting padding with different values for all sides
		_newGame = false;
	}
	
	private void setUpNewGamePane(Pane pane) {
		Pane newGamePane = new Pane();
		Rectangle newBox = 
				new Rectangle(Constants.TILE_LENGTH, Constants.TILE_LENGTH);
		newBox.setFill(Constants.PINK_THEME);
		newBox.setArcWidth(Constants.ROUNDING);
		newBox.setArcHeight(Constants.ROUNDING);
	
		double newY = newBox.getY();
		
		Text newTitle = new Text("New Game");
		newTitle.setFill(Color.BLACK);
		newTitle.setFont(Font.font("Helvetica", FontWeight.BOLD, Constants.FONT));
		newTitle.setWrappingWidth(Constants.TILE_LENGTH);
		newTitle.setTextAlignment(TextAlignment.CENTER);
		newTitle.setY(newY + (Constants.TILE_LENGTH/2));
		newGamePane.getChildren().addAll(newBox, newTitle);
		newGamePane.addEventHandler(MouseEvent.MOUSE_CLICKED, new NewGameHandler());
		//makes clicking on this pane activate a new game
	
		pane.getChildren().add(newGamePane);
		
	}
	
	private void updateScore(int points) {
		_scoreLabel.setText(Integer.toString(points));
		_score = points;
	}
	
	public void update(int points, boolean gameHasReset) {
		this.updateBest();
		this.updateScore(points);
		
		if(gameHasReset) {
			_newGame = false;
			//if Game resets its board, Controls should stop trying to reset Game
		}
	}
	
	private void updateBest() {
		if(_score>_best) {
			_bestLabel.setText(Integer.toString(_score));
			_best = _score;
		}
		//changes the best label only if current points are higher than best
	}
	
	
	private void setUpBestPane(Pane pane) {
		Pane bestPane = new Pane();
		Rectangle bestBox = 
				new Rectangle(Constants.TILE_LENGTH, Constants.TILE_LENGTH);
		bestBox.setFill(Constants.ORANGE_THEME);
		bestBox.setArcWidth(Constants.ROUNDING);
		bestBox.setArcHeight(Constants.ROUNDING);
		
		double bestY = bestBox.getY();
		
		Text bestTitle = new Text("Best:");
		bestTitle.setFill(Color.BLACK);
		bestTitle.setFont(Font.font("Helvetica", FontWeight.BOLD, Constants.FONT));
		bestTitle.setWrappingWidth(Constants.TILE_LENGTH);
		bestTitle.setTextAlignment(TextAlignment.CENTER);
		bestTitle.setY(bestY + Constants.SCORE_PADDING);
		
		_bestLabel = new Text("0");
		_bestLabel.setFill(Color.BLACK);
		_bestLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, Constants.FONT));
		_bestLabel.setY(bestY+(Constants.SCORE_PADDING*2));
		_bestLabel.setWrappingWidth(Constants.TILE_LENGTH);
		_bestLabel.setTextAlignment(TextAlignment.CENTER);
		bestPane.getChildren().addAll(bestBox, bestTitle, _bestLabel);
	
		pane.getChildren().add(bestPane);
	}
	
	private void setUpScorePane(Pane pane) {
		Pane scorePane = new Pane();
		Rectangle scoreBox = 
				new Rectangle(Constants.TILE_LENGTH, Constants.TILE_LENGTH);
		scoreBox.setFill(Constants.ORANGE_THEME);
		scoreBox.setArcWidth(Constants.ROUNDING);
		scoreBox.setArcHeight(Constants.ROUNDING);
		double scoreY = scoreBox.getY();
		
		Text scoreTitle = new Text("Score:");
		scoreTitle.setFill(Color.BLACK);
		scoreTitle.setFont(Font.font("Helvetica", FontWeight.BOLD, Constants.FONT));
		scoreTitle.setWrappingWidth(Constants.TILE_LENGTH);
		scoreTitle.setTextAlignment(TextAlignment.CENTER);
		scoreTitle.setY(scoreY + Constants.SCORE_PADDING);
		
		_scoreLabel = new Text("0");
		_scoreLabel.setFill(Color.BLACK);
		_scoreLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, Constants.FONT));
		_scoreLabel.setY(scoreY+(Constants.SCORE_PADDING*2));
		_scoreLabel.setWrappingWidth(Constants.TILE_LENGTH);
		_scoreLabel.setTextAlignment(TextAlignment.CENTER);

		scorePane.getChildren().addAll(scoreBox, scoreTitle, _scoreLabel);
		pane.getChildren().add(scorePane);
	}
	
	public boolean isNewGame() {
		return _newGame;
	}
	
	private class NewGameHandler implements EventHandler <MouseEvent> {

		@Override
		public void handle(MouseEvent e){
			_newGame = true;
			e.consume();
		}
	}
}