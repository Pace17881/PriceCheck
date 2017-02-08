package ui.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import core.IntervalObserver;
import core.Mail;
import datatypes.Product;
import datatypes.ProductInvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class ModelBean
{
	private static ModelBean instance = new ModelBean();
	private Stage primaryStage;
	private ObservableList<Product> products;
	private ProductInvalidationListener productInvalidationListener = new ProductInvalidationListener();

	private ModelBean()
	{
	}

	public static ModelBean getInstance()
	{
		return instance;
	}

	public Stage getPrimaryStage()
	{
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage)
	{
		this.primaryStage = primaryStage;
	}

	public ObservableList<Product> getProducts()
	{
		return products;
	}

	public void setProducts(ObservableList<Product> products)
	{
		this.products = products;
	}

	public ObservableList<String> getProductNames()
	{
		ArrayList<String> productNames = new ArrayList<>();
		productNames.add("");
		for (Product product : products)
		{
			productNames.add(product.getName());
		}
		return FXCollections.observableArrayList(productNames);
	}

	public void startObservation()
	{
		if (!products.isEmpty())
		{
			IntervalObserver iObserver = IntervalObserver.getInstance();
			if (!iObserver.isAlive())
			{
				products.addListener(productInvalidationListener);
				iObserver.setObservableProducts(products);

				iObserver.setTimerDefault((int) TimeUnit.MINUTES.toMillis(30));
				iObserver.start();
			}
		}
		else
		{
			System.out.println("No products in list to observe");
		}
	}

	public void stopObservation()
	{
		IntervalObserver iObserver = IntervalObserver.getInstance();
		if (iObserver.isAlive())
		{
			iObserver.interrupt();
			products.removeListener(productInvalidationListener);
		}
	}

	public void addProduct(String address, String assignTo, double priceWish) throws MalformedURLException
	{
		URL url = new URL(address);

		if (assignTo == null || assignTo.isEmpty())
		{
			Product newProduct = new Product(url);
			newProduct.addListener(productInvalidationListener);
			newProduct.setPriceWish(priceWish);
			products.add(newProduct);
			startObservation();
		}
		else
		{
			for (Product product : products)
			{
				if (product.getName() != null && product.getName().equals(assignTo))
				{
					product.addNewDealer(url);
				}
			}
		}
	}

	public void removeProduct(String name)
	{
		if (name != null && !name.isEmpty())
		{
			for (Iterator<Product> iterProd = products.iterator(); iterProd.hasNext();)
			{
				Product product = iterProd.next();
				if (product.getName().equals(name))
				{
					iterProd.remove();
				}
			}
		}
	}

	public ProductInvalidationListener getProductInvalidationListener()
	{
		return this.productInvalidationListener;
	}

	public void setProductInvalidationListener(ProductInvalidationListener newInvalidationListener)
	{
		this.productInvalidationListener = newInvalidationListener;
	}

	public void setMailCredentials(String user, String pass, String recipient)
	{
		Mail.getInstance().setUser(user);
		Mail.getInstance().setPass(pass);
		Mail.getInstance().setRecipient(recipient);
	}

}
