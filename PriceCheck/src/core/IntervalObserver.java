package core;

import java.net.URL;

import datatypes.Product;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class IntervalObserver extends Thread
{
	private static IntervalObserver instance;

	ObservableList<Product> products;
	long timerHeartbeat = 5000;
	long timerDefault = 6000;
	long timer = 0;

	private IntervalObserver()
	{
	}

	public synchronized static IntervalObserver getInstance()
	{
		if (instance == null)
		{
			instance = new IntervalObserver();
		}
		return instance;
	}

	public void setObservableProducts(ObservableList<Product> products)
	{
		this.products = products;
		products.addListener(new ListChangeListener<Product>()
		{
			@Override
			public void onChanged(Change<? extends Product> c)
			{
				timer = 0;
			}
		});
	}

	@Override
	public void run()
	{
		while (!isInterrupted() && products != null && !products.isEmpty())
		{

			if (timer <= 0)
			{
				timer = timerDefault;

				System.out.println("Refresh all products...");
				for (Product product : products)
				{
					for (URL url : product.getURLs())
					{
						Thread t = new Thread(new Checker(url, product));
						t.start();
					}
				}

			}
			else
			{
				System.out.println("Timer: " + timer / 1000 + " seconds");
			}
			try
			{
				System.out.println("Sleeping " + timerHeartbeat / 1000 + " seconds");
				sleep(timerHeartbeat);
				timer -= timerHeartbeat;
			}
			catch (InterruptedException e)
			{
				interrupt();
			}
		}
		instance = null;
		System.out.println("Observation ends now...");
	}

	public void setTimerHeartbeat(long timerHeartbeat)
	{
		this.timerHeartbeat = timerHeartbeat;
	}

	public void setTimerDefault(long timerDefault)
	{
		this.timerDefault = timerDefault;
	}

	public long getTimerHeartbeat()
	{
		return timerHeartbeat;
	}

	public long getTimerDefault()
	{
		return timerDefault;
	}
}
