import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

public class LoginScreen extends Screen{
	private JLabel title;
	
	private JPanel loginInfo;
	private JPanel loginLabels;
	private JPanel loginFields;
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JTextField username;
	private JPasswordField password;
	private JLabel errorLabel;
	
	private JButton loginButton;
	private JButton registerButton;
	
	public LoginScreen(JFrame parent, ScreenManager man, LoginSession sess){
		super(parent, man, sess);
	}
	
	protected void loadScreenPre(){
		username.setText("");
		password.setText("");
	}
	
	private class buttonListener implements ActionListener{
		private LoginScreen parent;
		
		public buttonListener(LoginScreen p){
			super();
			parent = p;
		}
		
		public void actionPerformed(ActionEvent e){
			String command = e.getActionCommand();
			if(command.equals("login")){
				parent.loginClicked();
			}
			if(command.equals("register")){
				parent.registerClicked();
			}
		}
	}
	
	protected void setupGUI(){
		loginInfo = new JPanel();
		loginLabels = new JPanel();
		loginFields = new JPanel();
		title = new JLabel("Login",JLabel.CENTER);
		usernameLabel = new JLabel("Username: ",JLabel.RIGHT);
		passwordLabel = new JLabel("Password: ",JLabel.RIGHT);
		username = new JTextField(20);
		password = new JPasswordField(20);
		errorLabel = new JLabel("",JLabel.CENTER);
		
		loginButton = new JButton("Login");
		registerButton = new JButton("Register");
		
		mainContentPanel.setLayout(new BoxLayout(mainContentPanel,BoxLayout.Y_AXIS));
		loginInfo.setLayout(new FlowLayout());
		loginLabels.setLayout(new GridLayout(0,1));
		loginFields.setLayout(new GridLayout(0,1));
		title.setFont(title.getFont().deriveFont(32f));
		errorLabel.setFont(errorLabel.getFont().deriveFont(16f));
		
		//events
		loginButton.setActionCommand("login");
		registerButton.setActionCommand("register");
		loginButton.addActionListener(new buttonListener(this));
		registerButton.addActionListener(new buttonListener(this));
		
		//add components
		mainContentPanel.add(Box.createGlue());
		mainContentPanel.add(title,BorderLayout.PAGE_START);
		
		loginLabels.add(usernameLabel);
		loginLabels.add(passwordLabel);
		loginFields.add(username);
		loginFields.add(password);
		
		loginInfo.add(loginLabels,BorderLayout.WEST);
		loginInfo.add(loginFields,BorderLayout.CENTER);
		loginInfo.add(loginButton);
		loginInfo.add(registerButton);
		
		mainContentPanel.add(loginInfo,BorderLayout.CENTER);
		mainContentPanel.add(errorLabel);
		mainContentPanel.add(Box.createGlue());
	}
	
	public void loginClicked(){
		String user = username.getText();
		String pass = password.getText();
		
		FileInputStream userFile;
		BufferedReader reader;
		//check username
		try{
			userFile = new FileInputStream("users/"+user);
			reader = new BufferedReader(new InputStreamReader(userFile));
		}
		catch(FileNotFoundException e){
			errorLabel.setText("Invalid Login");
			return;
		}
		
		//check password
		try{
			String line = reader.readLine();
			if(line != null && !line.equals(pass)){
				errorLabel.setText("Invalid Login");
				return;
			}
			reader.close();
			userFile.close();
		}
		catch(IOException e){
			System.out.println("Unexpected password read error");
		}
		
		//login!
		session.username = user;
		errorLabel.setText("");
		manager.changeScreen("overview");
	}
	
	public void registerClicked(){
		manager.changeScreen("register");
	}
}