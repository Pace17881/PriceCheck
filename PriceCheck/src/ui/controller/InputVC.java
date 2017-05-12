package ui.controller;

import java.net.MalformedURLException;
import java.util.Optional;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import ui.model.ModelBean;
import ui.view.InputView;

public class InputVC extends BaseViewController
{
	private InputView inputView;

	public InputVC(ModelBean modelBean)
	{
		super(modelBean);
		this.inputView = new InputView();
		inputView.getChooseCB().getItems().addAll(modelBean.getProductNames());

		inputView.getAddBtn().setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				String urlTFText = inputView.getURLTF().getText();

				if (urlTFText != null && !urlTFText.isEmpty())
				{
					String chooseCBText = inputView.getChooseCB().getSelectionModel().getSelectedItem();
					String priceWishStr = inputView.getPriceWish().getText();
					double priceWish = 0;
					boolean withoutPrice = true;
					try
					{
						priceWish = Double.parseDouble(priceWishStr.replaceAll(",", "."));
					}
					catch (NumberFormatException e)
					{
						withoutPrice = confirmationDialog();
					}

					if (withoutPrice)
					{
						try
						{
							modelBean.addProduct(inputView.getURLTF().getText(), chooseCBText, priceWish);
						}
						catch (MalformedURLException e)
						{
							inputView.setInfo("Wrong URL format!");
						}
						inputView.getURLTF().clear();
						if (chooseCBText != null)
						{
							inputView.getAddBtn().setText("Add Product");
							inputView.getChooseCB().getSelectionModel().selectFirst();
						}
					}
				}
			}

		});

		inputView.getRemoveBtn().setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				modelBean.removeProduct(inputView.getChooseCB().getSelectionModel().getSelectedItem());
			}

		});

		inputView.getURLTF().setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				if (event.getButton() == MouseButton.PRIMARY)
				{
					inputView.getURLTF().selectAll();
				}
			}
		});

		inputView.getURLTF().setOnKeyReleased(new EventHandler<KeyEvent>()
		{

			@Override
			public void handle(KeyEvent event)
			{
				String cmb = inputView.getChooseCB().getSelectionModel().getSelectedItem();
				String tf = inputView.getURLTF().getText();
				if (cmb != null)
				{
					if (!cmb.isEmpty() && !tf.isEmpty())
					{
						inputView.getAddBtn().setText("Assign");
					}
					else
					{
						inputView.getAddBtn().setText("Add Product");
					}

				}
			}
		});

		inputView.getPriceWish().lengthProperty().addListener(new ChangeListener<Number>()
		{
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
			{
				if (newValue.intValue() > oldValue.intValue())
				{
					char ch = inputView.getPriceWish().getText().charAt(oldValue.intValue());
					// Check if the new character is the number or other's
					if (!(ch >= '0' && ch <= '9' || ch == ','))
					{
						// if it's not number then just setText to previous one
						inputView.getPriceWish().setText(inputView.getPriceWish().getText().substring(0,
								inputView.getPriceWish().getText().length() - 1));
					}
				}

			}
		});

		inputView.getChooseCB().setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent event)
			{
				String value = inputView.getChooseCB().getSelectionModel().getSelectedItem();
				if (value != null && !value.isEmpty())
				{
					if (!inputView.getURLTF().getText().isEmpty())
					{
						inputView.getAddBtn().setText("Assign");
					}
					inputView.getRemoveBtn().setDisable(false);
				}
				else
				{
					inputView.getAddBtn().setText("Add Product");
					inputView.getRemoveBtn().setDisable(true);
				}

			}
		});

		inputView.getDetails().setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent event)
			{
				modelBean.getProductInvalidationListener().removeViewController(InputVC.this);
				OutputVC outputVC = new OutputVC(modelBean);
				outputVC.show();
				// OutputVC2 out2 = new OutputVC2(modelBean);
				// out2.show();
			}
		});

		// inputView.getMailBtn().setOnAction(new EventHandlerInput());
		inputView.getMailBtn().setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent event)
			{
				modelBean.getProductInvalidationListener().removeViewController(InputVC.this);
				MailInputVC mailInputVC = new MailInputVC(modelBean);
				mailInputVC.show();
			}
		});

		inputView.getConfigBtn().setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent event)
			{
				modelBean.getProductInvalidationListener().removeViewController(InputVC.this);
				ConfigurationVC configurationVC = new ConfigurationVC(modelBean);
				configurationVC.show();
			}
		});

		modelBean.getProductInvalidationListener().addViewController(this);
	}

	private boolean confirmationDialog()
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("No Pricewish?");
		alert.setContentText("Are you ok with this?");

		Optional<ButtonType> result = alert.showAndWait();
		return result.get() == ButtonType.OK;
	}

	@Override
	public void show()
	{
		inputView.show(modelBean.getPrimaryStage());
	}

	@Override
	public void update()
	{
		Platform.runLater(new Runnable()
		{
			@Override
			public void run()
			{
				inputView.getChooseCB().getItems().clear();
				inputView.getChooseCB().getItems().addAll(modelBean.getProductNames());
			}
		});
	}

}
