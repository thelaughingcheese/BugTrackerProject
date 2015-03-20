import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.Arrays;

public class RegisterScreen extends Screen{
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
		super(parent, man);
	}

	private class buttonListener implements ActionListener{
		private RegisterScreen parent;

		public buttonListener(RegisterScreen p){
			super();
			parent = p;
		}	

		public void actionPerformed(ActionEvent e){
			String command = e.getActionCommand();
			if(command.equals("register")){
				parent.registerClicked();
			}
			if(command.equals("cancel")){
				parent.cancelClicked();
			}
		}
	}

	protected void setupGUI(){
		//create components
		title = new JLabel("Register",JLabel.CENTER);

		accountInfo = new JPanel();
		accountLabels = new JPanel();
		accountFields = new JPanel();
	
		title = new JLabel("Register",JLabel.CENTER);
		title.setFont(title.getFont().deriveFont(32f));
	
		usernameLabel = new JLabel("Username: ",JLabel.RIGHT);
		passwordLabel = new JLabel("Password: ",JLabel.RIGHT);
		retypePasswordLabel = new JLabel("Re-type Password: ",JLabel.RIGHT);
		emailLabel = new JLabel("E-mail: ",JLabel.RIGHT);
	
		username = new JTextField(20);
		password = new JPasswordField(20);
		retypePassword = new JPasswordField(20);
		email = new JTextField(20);
	
		registerButton = new JButton("Register");
		cancelButton = new JButton("Cancel");
		errorLabel = new JLabel("",JLabel.CENTER);
		
		registerButton = new JButton("register");
		cancelButton = new JButton("cancel");
		
		//configure components
		mainContentPanel.setLayout(new BoxLayout(mainContentPanel,BoxLayout.Y_AXIS));
		accountInfo.setLayout(new FlowLayout());
		accountLabels.setLayout(new GridLayout(6,1,0,10));
		accountFields.setLayout(new GridLayout(6,1));
		
		//configure events
		registerButton.setActionCommand("register");
		cancelButton.setActionCommand("cancel");
		registerButton.addActionListener(new buttonListener(this));
		cancelButton.addActionListener(new buttonListener(this));

		//add components
		mainContentPanel.add(Box.createGlue());
		mainContentPanel.add(title,BorderLayout.PAGE_START);

		accountLabels.add(usernameLabel);
		accountLabels.add(passwordLabel);
		accountLabels.add(retypePasswordLabel);
		accountLabels.add(emailLabel);
	
		accountFields.add(username);
		accountFields.add(password);
		accountFields.add(retypePassword);
		accountFields.add(email);
		accountFields.add(registerButton);
		accountFields.add(cancelButton);
	
		accountInfo.add(accountLabels,BorderLayout.WEST);
		accountInfo.add(accountFields,BorderLayout.CENTER);
		
		mainContentPanel.add(accountInfo);
		mainContentPanel.add(errorLabel);
		mainContentPanel.add(Box.createGlue());
	}
	
	protected void loadScreenPre(){
		username.setText("");
		password.setText("");
		retypePassword.setText("");
	}
	
	public void registerClicked(){
		String name = username.getText();
		//make sure username is valid
		if((name.contains("/")) || (name.contains("\\")) || (name.contains("<")) || (name.contains(">")) || 
				(name.contains(":")) || (name.contains("*")) || (name.contains("\"")) || (name.contains("|")))
		{
			errorLabel.setText("Name may not contain \\ / < > : * \" |");
			registerPanel.add(errorLabel);
			parentFrame.getContentPane().validate();
			return;
		}
		
		//error if name not entered
		if(name.equals(""))
		{
			errorLabel.setText("Name not entered.");
			registerPanel.add(errorLabel);
			parentFrame.getContentPane().validate();
			return;
		}
		
		//check if username is already taken
		String dir = System.getProperty("user.dir");
		if(new File(dir + "\\src\\" + name + ".txt").exists())
		{
			errorLabel.setText("Name already taken.");
			registerPanel.add(errorLabel);
			parentFrame.getContentPane().validate();
			return;
		}
		//check if password is empty
		char[] dud = "".toCharArray();
		if(Arrays.equals(dud,password.getPassword()))
		{
			errorLabel.setText("The password field is empty");
			registerPanel.add(errorLabel);
			parentFrame.getContentPane().validate();
			return;
		}
		//check if retypePassword is empty
		if(Arrays.equals(dud,retypePassword.getPassword()))
		{
		    errorLabel.setText("The retype password field is empty");
			registerPanel.add(errorLabel);
			parentFrame.getContentPane().validate();
			return;
		}
		
		//check if passwords match
		if(!Arrays.equals(password.getPassword(),retypePassword.getPassword()))
		{
			errorLabel.setText("Passwords don't match.");
			registerPanel.add(errorLabel);
			parentFrame.getContentPane().validate();
			return;
		}
		errorLabel.setText("");
		parentFrame.getContentPane().validate();
		System.out.println(new File(dir).getAbsolutePath() + "\\" + name);
		//check if email is okay
	}

	public void cancelClicked(){
		manager.changeScreen("login");
	}
}
