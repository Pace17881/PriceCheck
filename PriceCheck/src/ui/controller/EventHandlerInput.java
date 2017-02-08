package ui.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class EventHandlerInput implements EventHandler<ActionEvent>
{

	@Override
	public void handle(ActionEvent event)
	{
		System.out.println(event.getSource());

	}

}
