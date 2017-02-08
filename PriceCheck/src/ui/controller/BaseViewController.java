package ui.controller;

import ui.model.ModelBean;

public class BaseViewController
{
	protected ModelBean modelBean;

	public BaseViewController(ModelBean modelBean)
	{
		this.modelBean = modelBean;
	}

	public void show()
	{
	}

	public void update()
	{
	}

}
