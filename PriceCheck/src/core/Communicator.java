package core;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Communicator
{
	private static Communicator instance = new Communicator();
	private String[] userAgents = {
			"Mozilla/5.0 (Windows NT 8.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36",
			"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36 OPR/42.0.2393.94",
			"Mozilla/5.0 (Windows NT 7.0; WOW64; rv:50.0) Gecko/20100101 Firefox/50.0",
			"Mozilla/5.0 (Windows NT 8.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2704.79 Safari/537.36 Edge/14.14393",
			"Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko" };

	private Communicator()
	{
	};

	public static Communicator getInstance()
	{
		return instance;
	}

	public Document getDocument(String address)
	{
		Document document = null;
		try
		{
			Map<String, String> headers = new HashMap<>();
			headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			headers.put("Accept-Language", "de-DE,de;q=0.8,en-US;q=0.6,en;q=0.4");
			headers.put("Accept-Encoding", "gzip, deflate, sdch, br");

			Random r = new Random();
			int randomInt = r.nextInt(userAgents.length - 1);

			Response response = Jsoup.connect(address).userAgent(userAgents[randomInt])
					.referrer("http://www.sebastianthiem.de").timeout(12000).headers(headers).followRedirects(true)
					.execute();
			document = response.parse();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Communicator:getDocument: " + e.getMessage());
			return null;
		}

		return document;
	}

	public String getProductName(Document document)
	{
		String dealerName = getURL(document).getHost();
		String productName = null;
		if (dealerName.equals("www.amazon.de"))
		{
			Element attribute = getElement(document, "span", "id", "productTitle");
			if (attribute != null)
			{
				productName = attribute.text();
			}
		}
		if (dealerName.equals("www.saturn.de") || dealerName.equals("www.mediamarkt.de"))
		{
			Element attribute = getElement(document, "meta", "property", "og:title");
			if (attribute != null)
			{
				productName = attribute.attr("content");
			}
		}
		if (dealerName.equals("www.notebooksbilliger.de"))
		{
			Element attribute = getElement(document, "input", "id", "main_product");
			if (attribute != null)
			{
				productName = attribute.attr("data-name");
			}
		}
		return productName;
	}

	public double getProductPrice(Document document)
	{
		String dealerName = getURL(document).getHost();
		double productPrice = 0;

		if (dealerName.equalsIgnoreCase("www.amazon.de"))
		{
			Element attribute = getElement(document, "span", "id", "priceblock_ourprice");
			if (attribute != null)
			{
				productPrice = Double.parseDouble(attribute.text().substring(4).replace(',', '.'));
			}
		}
		if (dealerName.equals("www.saturn.de") || dealerName.equals("www.mediamarkt.de"))
		{
			Element attribute = getElement(document, "meta", "property", "product:price:amount");
			if (attribute != null)
			{
				productPrice = Double.parseDouble(attribute.attr("content"));
			}
		}
		if (dealerName.equals("www.notebooksbilliger.de"))
		{
			Element attribute = getElement(document, "input", "id", "main_product");
			if (attribute != null)
			{
				productPrice = Double.parseDouble(attribute.attr("data-price"));
			}
		}
		return productPrice;
	}

	private Element getElement(Document document, String tagName, String attributeName, String attributValue)
	{
		Element element = null;
		Elements elements = document.select(tagName);

		for (Element e : elements)
		{
			if (e.attr(attributeName).equals(attributValue))
			{
				element = e;
			}
		}
		return element;
	}

	public URL getURL(Document document)
	{
		try
		{
			return new URL(document.location());
		}
		catch (MalformedURLException e)
		{
			System.out.println("Communicator_getURL: " + e.getMessage());
			return null;
		}
	}
}
