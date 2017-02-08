package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import datatypes.MailItem;

public class Mail
{
	private static Mail instance = new Mail();
	private String user = "thiemsebastian@hotmail.de";
	private String pass = "Pacemaker17881";
	private String recipient = "thiemseb@gmail.com";
	private List<MailItem> messages = new ArrayList<>();
	private byte sendMessagesByCount = 1;

	public String getUser()
	{
		return user;
	}

	public String getRecipient()
	{
		return recipient;
	}

	private Mail()
	{
	}

	public void addMessage(MailItem mailItem)
	{
		if (mailItem != null)
		{
			messages.add(mailItem);
		}
		if (messages.size() >= sendMessagesByCount)
		{
			sendMail();
		}
	}

	public void setUser(String user)
	{
		this.user = user;
	}

	public void setPass(String pass)
	{
		this.pass = pass;
	}

	public void setRecipient(String recipient)
	{
		this.recipient = recipient;
	}

	public void setSendMessagesByCount(byte sendMessagesByCount)
	{
		this.sendMessagesByCount = sendMessagesByCount;
	}

	public static Mail getInstance()
	{
		return instance;
	}

	private Session getMailSession()
	{
		final Properties props = new Properties();
		// Zum Empfangen
		props.setProperty("mail.pop3.host", "pop-mail.outlook.com");
		props.setProperty("mail.pop3.user", user);
		props.setProperty("mail.pop3.password", pass);
		props.setProperty("mail.pop3.port", "995");
		props.setProperty("mail.pop3.auth", "true");
		props.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		// Zum Senden
		props.setProperty("mail.smtp.port", "587");
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.host", "smtp.live.com");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		return Session.getInstance(props, new javax.mail.Authenticator()
		{
			@Override
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(props.getProperty("mail.pop3.user"),
						props.getProperty("mail.pop3.password"));
			}
		});
	}

	private void sendMail()
	{

		String message = "";
		String subject = "PriceCheck";

		for (MailItem item : messages)
		{
			message += item.getName(true);
			message += item.getUrl(true);
			message += item.getPrice(true);
			System.out.println(item);
		}
		Session session = getMailSession();

		Message msg = new MimeMessage(session);
		InternetAddress addressTo;
		try
		{
			addressTo = new InternetAddress(recipient);
			msg.setRecipient(Message.RecipientType.TO, addressTo);
			msg.setSubject(subject);
			msg.setContent(message, "text/html");
			Transport.send(msg);
			System.out.println("Mail sent");
		}
		catch (AddressException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (MessagingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public String toString()
	{
		return "Mail is ok";
	}

	public boolean isValid()
	{
		return user != null && !user.isEmpty() && pass != null && !pass.isEmpty() && recipient != null
				&& !recipient.isEmpty();
	}

	// public static void main(String[] args)
	// {
	// getInstance().sendMail();
	// }
}
