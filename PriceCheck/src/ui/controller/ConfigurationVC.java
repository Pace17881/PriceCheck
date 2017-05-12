package ui.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ui.model.ModelBean;
import ui.view.ConfigurationView;

public class ConfigurationVC extends BaseViewController
{
	private ConfigurationView configurationView;

	public ConfigurationVC(ModelBean modelBean)
	{
		super(modelBean);
		this.configurationView = new ConfigurationView();

		configurationView.getBack().setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent event)
			{
				modelBean.getProductInvalidationListener().removeViewController(ConfigurationVC.this);
				InputVC inputVC = new InputVC(modelBean);
				inputVC.show();
			}
		});
		modelBean.getProductInvalidationListener().addViewController(this);
	}

	@Override
	public void show()
	{
		configurationView.show(modelBean.getPrimaryStage());
	}

	@Override
	public void update()
	{
		Platform.runLater(new Runnable()
		{
			@Override
			public void run()
			{

			}
		});
	}

}
