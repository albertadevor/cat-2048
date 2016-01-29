package Cat2048;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**
  *
  * Cat 2048 is essentially 2048 with many more cats!
  * Known bugs include allowing the user to shift the tiles 
  * in a new direction if the tile hasn't finished moving.
  *
  * @author <Alberta Devor>
  *
  */

public class App extends Application {

    @Override
	public void start(Stage stage) {
        PaneOrganizer organizer  = new PaneOrganizer();
        Scene scene = new Scene(organizer.getRoot());
        stage.setScene(scene);
        stage.setMinWidth(Constants.STAGE_WIDTH);
        stage.setMinHeight(Constants.STAGE_HEIGHT);
        stage.setMaxWidth(Constants.STAGE_WIDTH);
        stage.setMaxHeight(Constants.STAGE_HEIGHT);
        stage.setTitle("Cat 2048");
        stage.show();
	}

	public static void main(String[] argv) {
		launch(argv);
	}
}
