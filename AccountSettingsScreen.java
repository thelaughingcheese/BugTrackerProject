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
	
	public AccountSettingsScreen(JFrame parent, ScreenManager man, LoginSession sess){
		super(parent,man,sess);
	}
	
	protected void loadScreenPre(){
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
		
		//configure components
		mainContentPanel.setLayout(new BorderLayout());
		header.setLayout(new BoxLayout(header,BoxLayout.Y_AXIS));
		titlebar.setLayout(new BorderLayout());
		toolbar.setLayout(new FlowLayout());
		
		//configure events
		cancelButton.setActionCommand("cancel");
		cancelButton.addActionListener(new buttonListener(this));
		logoutButton.setActionCommand("logout");
		logoutButton.addActionListener(new buttonListener(this));
		
		//organize components into containers
		header.add(titlebar);
		header.add(toolbar);
		titlebar.add(title,BorderLayout.CENTER);
		titlebar.add(logoutButton,BorderLayout.EAST);
		titlebar.add(cancelButton,BorderLayout.WEST);
		
		mainContentPanel.add(header,BorderLayout.PAGE_START);
	}
	
	public void cancelClicked(){
		manager.changeScreen("overview");
	}
	
	public void logoutClicked(){
		System.out.println("logout");
	}
}