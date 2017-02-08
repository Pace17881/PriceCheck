package datatypes;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

public class Product implements Observable
{
	private HashMap<URL, ArrayList<Price>> dealerWithPrices;
	private String name = null;
	private double priceWish;
	private InvalidationListener productListener = null;

	public double getPriceWish()
	{
		return priceWish;
	}

	public void setPriceWish(double price)
	{
		this.priceWish = price;
		informListener();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String newName)
	{
		if (newName != null && !newName.isEmpty())
		{
			this.name = newName;
			informListener();
		}
	}

	public Price getCurrentPrice(URL dealer)
	{
		ArrayList<Price> prices = getPricesByURL(dealer);
		if (prices != null && !prices.isEmpty())
		{
			return prices.get(prices.size() - 1);
		}
		return null;
	}

	public ArrayList<URL> getURLs()
	{
		ArrayList<URL> urls = new ArrayList<>();
		urls.addAll(dealerWithPrices.keySet());
		return urls;
	}

	public ArrayList<Price> getPricesByURL(URL url)
	{
		return dealerWithPrices.get(url);
	}

	/**
	 * add a new Price to existing URL, if URL not exists, new Entry will be
	 * added
	 * 
	 * @param url
	 * @param newPrice
	 */
	public void addNewPrice(URL url, Price newPrice)
	{
		ArrayList<Price> prices = dealerWithPrices.get(url);

		if (prices.isEmpty())
		{
			prices.add(newPrice);
			informListener();
		}
		else if (!getCurrentPrice(url).equals(newPrice))
		{
			prices.add(newPrice);
			informListener();
		}
	}

	public void addNewDealer(URL url)
	{
		if (!dealerWithPrices.containsKey(url))
		{
			dealerWithPrices.put(url, new ArrayList<>());
			informListener();
		}
	}

	public MailItem priceWishAchieved()
	{
		double lowestPrice = priceWish;
		URL dealer = null;

		for (Entry<URL, ArrayList<Price>> dp : dealerWithPrices.entrySet())
		{
			for (Price price : dp.getValue())
			{
				if (price.getPrice() < lowestPrice)
				{
					lowestPrice = price.getPrice();
					dealer = dp.getKey();

				}
			}
		}
		if (name != null && lowestPrice < priceWish)
		{
			return new MailItem(getName(), dealer, lowestPrice);
		}
		return null;
	}

	public Product(String name, URL url, Price price)
	{
		this.name = name;
		dealerWithPrices = new HashMap<>();
		ArrayList<Price> prices = new ArrayList<>();
		prices.add(price);
		dealerWithPrices.put(url, prices);
	}

	public Product(URL url)
	{
		ArrayList<Price> prices = new ArrayList<>();
		dealerWithPrices = new HashMap<>();
		dealerWithPrices.put(url, prices);
		name = null;
	}

	public Product()
	{
		dealerWithPrices = new HashMap<>();
	}

	@Override
	public String toString()
	{
		return String.format("%s %d Dealer", getName(), dealerWithPrices.size());
	}

	@Override
	public void addListener(InvalidationListener listener)
	{
		this.productListener = listener;
	}

	@Override
	public void removeListener(InvalidationListener listener)
	{
		if (this.productListener != null && this.productListener.equals(listener))
		{
			this.productListener = null;
		}
	}

	private void informListener()
	{
		if (productListener != null)
		{
			productListener.invalidated(this);
		}
	}
}
