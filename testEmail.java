import java.util.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class testEmail{
	public static void main(String args[]){

		String to = "jason.jiasheng.yu@ryerson.ca";
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
		 message.setSubject("This is the Subject Line!");

		 // Now set the actual message
		 message.setText("This is actual message");

		 // Send message
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