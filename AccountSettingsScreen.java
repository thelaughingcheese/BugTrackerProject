import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

public class AccountSettingsScreen extends Screen{
	private JLabel title;
	private JPanel header;
	private JPanel titlebar;
	private JPanel toolbar;
	
	private JButton cancelButton;
	private JButton logoutButton;
	
	private JPanel accountInfo;
	private JPanel accountInfoHPadding;
	private JPanel changePassword;
	private JPanel changeEmail;
	
	private JLabel currentPasswordLabel;
	private JLabel newPasswordLabel;
	private JLabel retypeNewPasswordLabel;
	private JLabel newEmailLabel;
	
	private JPasswordField currentPassword;
	private JPasswordField newPassword;
	private JPasswordField retypeNewPassword;
	private JTextField newEmail;
	
	private JButton changePasswordButton;
	private JButton changeEmailButton;
	
	public AccountSettingsScreen(JFrame parent, ScreenManager man, LoginSession sess){
		super(parent,man,sess);
	}
	
	protected void loadScreenPre(){
		currentPassword.setText("");
		newPassword.setText("");
		retypeNewPassword.setText("");
		newEmail.setText("");
	}
	
	private class buttonListener implements ActionListener{
		private AccountSettingsScreen parent;

		public buttonListener(AccountSettingsScreen p){
			super();
			parent = p;
		}	

		public void actionPerformed(ActionEvent e){
			String command = e.getActionCommand();
			if(command.equals("cancel")){
				parent.cancelClicked();
			}
			if(command.equals("logout")){
				parent.logoutClicked();
			}
			if(command.equals("change password")){
				parent.changePasswordClicked();
			}
			if(command.equals("change email")){
				parent.changeEmailClicked();
			}
		}
	}

	
	protected void setupGUI(){
		//create components
		title = new JLabel("Account Settings",JLabel.CENTER);
		header = new JPanel();
		titlebar = new JPanel();
		toolbar = new JPanel();
		
		cancelButton = new JButton("Cancel");
		logoutButton = new JButton("Logout");
		
		accountInfo = new JPanel();
		accountInfoHPadding = new JPanel();
		changePassword = new JPanel();
		changeEmail = new JPanel();
		
		currentPasswordLabel = new JLabel("Current Password: ",JLabel.RIGHT);
		newPasswordLabel = new JLabel("New Password: ",JLabel.RIGHT);
		retypeNewPasswordLabel = new JLabel("Re-Type New Password: ",JLabel.RIGHT);
		newEmailLabel = new JLabel("New Email: ",JLabel.RIGHT);
		
		currentPassword = new JPasswordField(32);
		newPassword = new JPasswordField(32);
		retypeNewPassword = new JPasswordField(32);
		newEmail = new JTextField(32);
		
		changePasswordButton = new JButton("Change Password");
		changeEmailButton = new JButton("Change Email");
		
		//configure components
		mainContentPanel.setLayout(new BorderLayout());
		header.setLayout(new BoxLayout(header,BoxLayout.Y_AXIS));
		titlebar.setLayout(new BorderLayout());
		toolbar.setLayout(new FlowLayout());
		
		accountInfo.setLayout(new BoxLayout(accountInfo,BoxLayout.Y_AXIS));
		accountInfoHPadding.setLayout(new BoxLayout(accountInfoHPadding,BoxLayout.X_AXIS));
		changePassword.setLayout(new GridLayout(4,2));
		changeEmail.setLayout(new GridLayout(2,2));
		
		//configure events
		cancelButton.setActionCommand("cancel");
		cancelButton.addActionListener(new buttonListener(this));
		logoutButton.setActionCommand("logout");
		logoutButton.addActionListener(new buttonListener(this));
		changePasswordButton.setActionCommand("change password");
		changePasswordButton.addActionListener(new buttonListener(this));
		changeEmailButton.setActionCommand("change email");
		changeEmailButton.addActionListener(new buttonListener(this));
		
		//organize components into containers
		header.add(titlebar);
		header.add(toolbar);
		titlebar.add(title,BorderLayout.CENTER);
		titlebar.add(logoutButton,BorderLayout.EAST);
		titlebar.add(cancelButton,BorderLayout.WEST);
		
		changePassword.add(currentPasswordLabel);
		changePassword.add(currentPassword);
		changePassword.add(newPasswordLabel);
		changePassword.add(newPassword);
		changePassword.add(retypeNewPasswordLabel);
		changePassword.add(retypeNewPassword);
		changePassword.add(changePasswordButton);
		
		changeEmail.add(newEmailLabel);
		changeEmail.add(newEmail);
		changeEmail.add(changeEmailButton);

		accountInfo.add(Box.createGlue());
		accountInfo.add(changePassword);
		accountInfo.add(changeEmail);
		accountInfo.add(Box.createGlue());
		
		accountInfoHPadding.add(Box.createGlue());
		accountInfoHPadding.add(accountInfo);
		accountInfoHPadding.add(Box.createGlue());

		mainContentPanel.add(header,BorderLayout.PAGE_START);
		mainContentPanel.add(accountInfoHPadding,BorderLayout.CENTER);
	}

	/**
	*Confirms that you clicked the cancel button and moves the client back to the "overview" menu.
	*/
	public void cancelClicked(){
		manager.changeScreen("overview");
	}
	
	/**
	*Confirms that you clicked the log out button and logs out of the current session.
	*/
	public void logoutClicked(){
		session.logout();
	}
	
	/**
	*This function first checks to see you inputed your current password. It gets confirmation that 
	*this is your current password in another input field. It then changes the users password at the last 
	*input field.
	*/
	public void changePasswordClicked(){
		//check current password
		if(!session.account.checkPassword(currentPassword.getText())){
			JOptionPane.showMessageDialog(parentFrame,
				"Current Password Incorrect",
				"Current Password Incorrect",
				JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		//check if retype matches
		if(!newPassword.getText().equals(retypeNewPassword.getText())){
			JOptionPane.showMessageDialog(parentFrame,
				"Passwords Do Not Match",
				"Passwords Do Not Match",
				JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		//verify valid password
		
		//change!
		session.account.changePassword(newPassword.getText());
		JOptionPane.showMessageDialog(parentFrame,
				"Password Changed!",
				"Password Changed!",
				JOptionPane.WARNING_MESSAGE);
	}
	/**
	*This function gives a confirmation/warning message that the email was changed.
	*/
	public void changeEmailClicked(){
		session.account.changeEmail(newEmail.getText());
		JOptionPane.showMessageDialog(parentFrame,
				"Email Changed!",
				"Email Changed!",
				JOptionPane.WARNING_MESSAGE);
	}
}
