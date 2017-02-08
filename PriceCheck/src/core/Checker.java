package core;

import java.net.URL;
import java.util.Date;

import org.jsoup.nodes.Document;

import datatypes.MailItem;
import datatypes.Price;
import datatypes.Product;

public class Checker implements Runnable
{
	URL url;
	Product product;

	public Checker(URL url, Product product)
	{
		this.url = url;
		this.product = product;
	}

	@Override
	public void run()
	{
		Communicator communicator = Communicator.getInstance();
		Document document = communicator.getDocument(url.toString());
		if (document != null)
		{
			if (product.getName() == null)
			{
				product.setName(communicator.getProductName(document));
			}
			double price = communicator.getProductPrice(document);
			Price priceDate = new Price(price, new Date());

			product.addNewPrice(url, priceDate);

			MailItem mailItem = product.priceWishAchieved();
			if (mailItem != null && Mail.getInstance().isValid())
			{
				Mail.getInstance().addMessage(mailItem);
			}
		}

	}
}
