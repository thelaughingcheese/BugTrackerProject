import java.util.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;

public class notificationManager{
	public static void notifySubscribers(BugReport report, String msg){
		ArrayList<String> subs = report.getSubscribedUsers();
		
		for(int i=0;i<subs.size();i++){
			Account acc = new Account();
			acc.setUsername(subs.get(i));
		
			//send email
			notificationManager.sendEmail(acc.getEmail(),msg,msg);
		}
	}
	
	public static void sendEmail(String to,String title,String msg){
		String from = "cps406@mail.com";
		String pass = "asdfasdf";
		String host = "smtp.mail.com";
		
		Properties properties = System.getProperties();
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.user", from);
		properties.put("mail.smtp.password", pass);
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(properties);

		try{
		 // Create a default MimeMessage object.
		 MimeMessage message = new MimeMessage(session);

		 // Set From: header field of the header.
		 message.setFrom(new InternetAddress(from));

		 // Set To: header field of the header.
		 message.addRecipient(Message.RecipientType.TO,
								  new InternetAddress(to));

		 // Set Subject: header field
		 message.setSubject(title);

		 // Now set the actual message
		 message.setText(msg);

		 // Send message
		 //Transport.send(message);
		 
		 Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		 
		 System.out.println("Sent message successfully....");
		}catch (MessagingException mex) {
		 mex.printStackTrace();
		}
	}
}