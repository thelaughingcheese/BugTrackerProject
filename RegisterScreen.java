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
		
		//configure components
		//add components
	}
	
	public void loadScreen(){
		username.setText("");
		password.setText("");
		retypePassword.setText("");
		parentFrame.add(registerPanel);
	}
}