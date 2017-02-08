package ui.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MailInputView
{
	private Scene scene;

	private GridPane grid;
	private TextField userTF;
	private PasswordField passTF;
	private TextField recipientTF;

	private Button okBtn;
	private Button cancelBtn;

	private HBox hBBtn;

	public MailInputView()
	{
		// Layout
		grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(5);
		grid.setVgap(5);
		grid.setPadding(new Insets(10, 10, 10, 10));

		grid.add(new Label("Mail:"), 0, 0);
		grid.add(new Label("Password:"), 0, 1);
		grid.add(new Label("Recipient:"), 0, 2);

		userTF = new TextField();
		grid.add(userTF, 1, 0);

		passTF = new PasswordField();
		grid.add(passTF, 1, 1);

		recipientTF = new TextField();
		grid.add(recipientTF, 1, 2);

		okBtn = new Button("OK");
		cancelBtn = new Button("Cancel");

		hBBtn = new HBox(10);
		hBBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hBBtn.getChildren().add(okBtn);
		hBBtn.getChildren().add(cancelBtn);
		grid.add(hBBtn, 1, 3);

		scene = new Scene(grid);
	}

	public void show(Stage stage)
	{
		stage.setTitle("PriceCheck");
		stage.setScene(scene);
		stage.show();
	}

	public TextField getUserTF()
	{
		return userTF;
	}

	public TextField getRecipientTF()
	{
		return recipientTF;
	}

	public PasswordField getPassTF()
	{
		return passTF;
	}

	public Button getOkBtn()
	{
		return okBtn;
	}

	public Button getCancelBtn()
	{
		return cancelBtn;
	}
}
