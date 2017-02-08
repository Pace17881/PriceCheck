package ui.view;

import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.stage.Stage;

public class OutputView2
{
	Scene scene;

	Accordion accordion;

	public OutputView2()
	{
		accordion = new Accordion();
		scene = new Scene(accordion);
	}

	public void show(Stage stage)
	{
		stage.setTitle("PriceCheck - Output");
		stage.setScene(scene);
		stage.show();
	}

	public Accordion getAccordion()
	{
		return accordion;
	}

}
