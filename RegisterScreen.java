import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

public class RegisterScreen{
	private JFrame parentFrame;
	private ScreenManager manager;
	
	private JPanel registerPanel;
	
	private JLabel title;
	
	private JPanel accountInfo;
	private JPanel accountLabels;
	private JPanel accountFields;
	
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JLabel retypePasswordLabel;
	private JLabel emailLabel;
	private JTextField username;
	private JPasswordField password;
	private JPasswordField retypePassword;
	private JTextField email;
	private JLabel errorLabel;
	
	private JButton registerButton;
	private JButton cancelButton;
	
	public RegisterScreen(JFrame parent, ScreenManager man){
		parentFrame = parent;
		manager = man;
		
		setupGUI();
	}
	
	private void setupGUI(){
		//create components
		registerPanel = new JPanel();
	
		title = new JLabel("Register",JLabel.CENTER);
		
		accountInfo = new JPanel();
		accountLabels = new JPanel();
		accountFields = new JPanel();
		
		usernameLabel = new JLabel("Username: ",JLabel.RIGHT);
		passwordLabel = new JLabel("Password: ",JLabel.RIGHT);
		retypePasswordLabel = new JLabel("Re-type Password",JLabel.RIGHT);
		emailLabel = new JLabel("E-mail",JLabel.RIGHT);
		username = new JTextField(20);
		password = new JPasswordField(20);
		retypePassword = new JPasswordField(20);
		email = new JTextField(20);
		errorLabel = new JLabel("ERROR",JLabel.CENTER);
		
		registerButton = new JButton("Register");
		cancelButton = new JButton("Cancel");
		
		//configure components
		registerPanel.setLayout(new BoxLayout(registerPanel,BoxLayout.Y_AXIS));
		
		//configure events
		
		//add components
		registerPanel.add(title);
		
		accountLabels.add(usernameLabel);
		accountLabels.add(passwordLabel);
		accountLabels.add(retypePasswordLabel);
		accountLabels.add(emailLabel);
		
		accountInfo.add(accountLabels);
		accountInfo.add(accountFields);
		accountInfo.add(registerButton);
		accountInfo.add(cancelButton);
		
		registerPanel.add(accountInfo);
	}
	
	public void loadScreen(){
		username.setText("");
		password.setText("");
		retypePassword.setText("");
		
		parentFrame.add(registerPanel);
	}
}