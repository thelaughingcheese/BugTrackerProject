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
			System.out.println("send email to "+acc.getEmail());
			
			  String to = "abcd@gmail.com";
			  String from = "BugTracker@gmail.com";
			  String host = "localhost";

			  Properties properties = System.getProperties();
			  properties.setProperty("mail.smtp.host", host);
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
				 message.setSubject("This is the Subject Line!");

				 // Now set the actual message
				 message.setText("This is actual message");

				 // Send message
				 Transport.send(message);
				 System.out.println("Sent message successfully....");
			  }catch (MessagingException mex) {
				 mex.printStackTrace();
			  }
		}
	}
}