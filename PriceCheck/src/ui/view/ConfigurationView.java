package ui.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ConfigurationView
{
	Scene scene;
	GridPane grid;

	Button back;
	Spinner<Integer> timerHeartbeat;
	Spinner<Integer> timerDefault;

	HBox hBBtn;

	public ConfigurationView()
	{
		grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(5);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 20, 20));

		grid.add(new Label("Timer Hearbeat:"), 0, 0);
		grid.add(new Label("Timer Default:"), 0, 1);

		timerHeartbeat = new Spinner<>();
		timerDefault = new Spinner<>();
		grid.add(timerHeartbeat, 1, 0);
		grid.add(timerDefault, 1, 1);

		back = new Button("Back");

		hBBtn = new HBox(10);
		hBBtn.getChildren().add(back);
		grid.add(hBBtn, 0, 5);
		scene = new Scene(grid);
	}

	public void show(Stage stage)
	{
		stage.setTitle("Configuration");
		stage.setScene(scene);
		stage.show();
	}

	public Button getBack()
	{
		return back;
	}

	public Spinner<Integer> getTimerHeartbeat()
	{
		return timerHeartbeat;
	}

	public Spinner<Integer> getTimerDefault()
	{
		return timerDefault;
	}
}
