package ui.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class InputView
{
	private Scene scene;

	private GridPane grid;

	private Label info;

	private TextField urlTF;
	private TextField priceWish;

	private ComboBox<String> chooseCB;

	private Button addBtn;
	private Button removeBtn;
	private Button details;
	private Button mailBtn;
	private Button configBtn;

	private HBox hBBtn;

	public InputView()
	{
		double width = 400;
		grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(5);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 20, 20));

		grid.add(new Label("URL:"), 0, 0);
		urlTF = new TextField();
		urlTF.setMaxWidth(width);
		grid.add(urlTF, 1, 0);

		grid.add(new Label("assign to:"), 0, 1);

		chooseCB = new ComboBox<>();
		chooseCB.setMinWidth(width);
		chooseCB.setMaxWidth(width);
		// chooseCB.setDisable(true);
		grid.add(chooseCB, 1, 1);

		priceWish = new TextField("0,00");
		priceWish.setMaxSize(60, Region.USE_PREF_SIZE);
		grid.add(new Label("PriceWish"), 0, 2);
		grid.add(priceWish, 1, 2);

		addBtn = new Button("Add Product");
		removeBtn = new Button("Remove Product");
		removeBtn.setDisable(true);
		details = new Button("Details...");
		mailBtn = new Button("Mail...");
		configBtn = new Button("Configuration");

		hBBtn = new HBox(10);
		hBBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hBBtn.getChildren().add(addBtn);
		hBBtn.getChildren().add(removeBtn);
		hBBtn.getChildren().add(mailBtn);
		hBBtn.getChildren().add(details);
		hBBtn.getChildren().add(configBtn);
		grid.add(hBBtn, 1, 3);

		info = new Label();
		grid.add(new Label("Info:"), 0, 4);
		grid.add(info, 1, 4);

		scene = new Scene(grid);
	}

	public void show(Stage stage)
	{
		stage.setTitle("PriceCheck");
		stage.setScene(scene);
		stage.show();
	}

	public TextField getURLTF()
	{
		return urlTF;
	}

	public Button getAddBtn()
	{
		return addBtn;
	}

	public Button getRemoveBtn()
	{
		return removeBtn;
	}

	public ComboBox<String> getChooseCB()
	{
		return chooseCB;
	}

	public Button getDetails()
	{
		return details;
	}

	public Button getMailBtn()
	{
		return mailBtn;
	}

	public void setInfo(String text)
	{
		info.setText(text);
	}

	public TextField getPriceWish()
	{
		return priceWish;
	}

	public Button getConfigBtn()
	{
		return configBtn;
	}
}
