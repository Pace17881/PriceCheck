package core;

import java.util.ArrayList;

import datatypes.Product;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import ui.controller.InputVC;
import ui.model.ModelBean;

public class StartPriceCheck extends Application
{

	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		ModelBean.getInstance().setPrimaryStage(primaryStage);

		ArrayList<Product> products = new ArrayList<>();
		ModelBean.getInstance().setProducts(FXCollections.observableArrayList(products));

		InputVC inputVC = new InputVC(ModelBean.getInstance());
		inputVC.show();
	}

	@Override
	public void stop()
	{
		ModelBean.getInstance().stopObservation();
	}

}
