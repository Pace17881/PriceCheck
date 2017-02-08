package ui.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class OutputView
{
	Scene scene;
	GridPane grid;
	TreeView<String> treeView;

	Button back;

	HBox hBBtn;

	public OutputView()
	{
		grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(5);
		grid.setVgap(5);
		grid.setPadding(new Insets(10, 10, 10, 10));

		treeView = new TreeView<>();
		treeView.setShowRoot(false);
		treeView.setPrefWidth(600);
		grid.add(treeView, 0, 0);

		back = new Button("Back");

		hBBtn = new HBox(10);
		hBBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hBBtn.getChildren().add(back);
		grid.add(hBBtn, 0, 1);

		scene = new Scene(grid);
	}

	public void show(Stage stage)
	{
		stage.setTitle("PriceCheck");
		stage.setScene(scene);
		stage.show();
	}

	public TreeView<String> getTreeView()
	{
		return treeView;
	}

	public Button getBack()
	{
		return back;
	}
}
