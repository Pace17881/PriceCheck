package datatypes;

import java.util.ArrayList;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import ui.controller.BaseViewController;

public class ProductInvalidationListener implements InvalidationListener
{

	ArrayList<BaseViewController> baseViewControllerList = new ArrayList<>();

	@Override
	public void invalidated(Observable observable)
	{
		for (BaseViewController bvc : baseViewControllerList)
		{
			bvc.update();
		}
	}

	public ProductInvalidationListener()
	{
		baseViewControllerList = new ArrayList<>();
	}

	public void addViewController(BaseViewController bvc)
	{
		baseViewControllerList.add(bvc);
		System.out.println(bvc + " added");
	}

	public void removeViewController(BaseViewController bvc)
	{
		baseViewControllerList.remove(bvc);
		System.out.println(bvc + " removed");
	}

	@Override
	public String toString()
	{
		return "ProductInvalidationListener: count: " + baseViewControllerList.size();
	}

}
