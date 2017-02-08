package ui.controller;

import java.net.URL;

import datatypes.Price;
import datatypes.Product;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import ui.model.ModelBean;
import ui.view.OutputView;

public class OutputVC extends BaseViewController
{
	private OutputView outputView;

	public OutputVC(ModelBean modelBean)
	{
		super(modelBean);
		outputView = new OutputView();
		outputView.getTreeView().setRoot(buildTree());

		outputView.getBack().setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent event)
			{
				modelBean.getProductInvalidationListener().removeViewController(OutputVC.this);
				InputVC inputVC = new InputVC(modelBean);
				inputVC.show();
			}
		});

		modelBean.getProducts().addListener(new InvalidationListener()
		{

			@Override
			public void invalidated(Observable observable)
			{
				update();
			}
		});
		modelBean.getProductInvalidationListener().addViewController(this);
	}

	private TreeItem<String> buildTree()
	{
		TreeItem<String> root = new TreeItem<String>("Products");
		// Productname
		for (Product product : modelBean.getProducts())
		{
			TreeItem<String> sub = new TreeItem<String>(product.getName());
			// Dealer
			for (URL url : product.getURLs())
			{
				TreeItem<String> subDealer = new TreeItem<String>(url.getHost());
				// Price
				for (Price price : product.getPricesByURL(url))
				{
					TreeItem<String> subPrice = new TreeItem<String>(String.format(
							"Price: %.2f € Date: %tH:%<tM:%<tS %<td.%<tm.%<tY", price.getPrice(), price.getCallDate()));
					subDealer.getChildren().add(subPrice);
				}
				sub.getChildren().add(subDealer);
			}
			root.getChildren().add(sub);
		}
		return root;
	}

	public void show()
	{
		outputView.show(modelBean.getPrimaryStage());
	}

	public void update()
	{
		Platform.runLater(new Runnable()
		{
			@Override
			public void run()
			{
				outputView.getTreeView().setRoot(buildTree());
			}
		});
	}
}
