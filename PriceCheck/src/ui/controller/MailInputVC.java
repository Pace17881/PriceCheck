package ui.controller;

import core.Mail;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ui.model.ModelBean;
import ui.view.MailInputView;

public class MailInputVC extends BaseViewController
{
	private MailInputView mailInputView;

	public MailInputVC(ModelBean modelBean)
	{
		super(modelBean);
		this.mailInputView = new MailInputView();
		if (Mail.getInstance().isValid())
		{
			mailInputView.getUserTF().setText(Mail.getInstance().getUser());
			mailInputView.getRecipientTF().setText(Mail.getInstance().getRecipient());
		}

		mailInputView.getOkBtn().setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent event)
			{
				String user = mailInputView.getUserTF().getText();
				String pass = mailInputView.getPassTF().getText();
				String recipient = mailInputView.getRecipientTF().getText();
				modelBean.setMailCredentials(user, pass, recipient);
				openInputView();
			}

		});
		mailInputView.getCancelBtn().setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent event)
			{
				openInputView();
			}

		});
	}

	private void openInputView()
	{
		InputVC inputVC = new InputVC(modelBean);
		inputVC.show();
	}

	@Override
	public void show()
	{
		mailInputView.show(modelBean.getPrimaryStage());
	}

	@Override
	public void update()
	{

	}

}
