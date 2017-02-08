package datatypes;

import java.net.URL;

public class MailItem
{
	String name;
	URL url;
	Double price;

	public MailItem(String name, URL url, Double price)
	{
		super();
		this.name = name;
		this.url = url;
		this.price = price;
	}

	public String getName(boolean asHTML)
	{
		if (asHTML)
		{
			return "<h1>" + name + "</h1>";
		}
		return name;
	}

	public String getUrl(boolean asHTML)
	{
		if (asHTML)
		{
			return "<a href=\"" + url.toString() + "\">" + url.toString() + "</a>";
		}
		return url.toString();
	}

	public String getPrice(boolean asHTML)
	{
		if (asHTML)
		{
			return "<p>" + price + " Euro</p>";
		}
		return price.toString();
	}

	public String toString()
	{
		return String.format("Name: %s%nURL: %s%nPrice: %s", getName(true), getUrl(true), getPrice(true));
	}
}
