import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class BugOverviewScreen extends Screen{
	private JLabel title;
	private JPanel header;
	private JPanel titlebar;
	private JPanel toolbar;
	
	private JScrollPane bugViewer;
	private JPanel bugViewerContent;
	
	private JButton accountSettingsButton;
	private JButton logoutButton;
	
	private JButton newReportButton;
	
	private ArrayList<Integer> reportIds;
	private ArrayList<BugReport> reports;
	private ArrayList<BugReportGuiTile> reportTiles;
	
	public BugOverviewScreen(JFrame parent, ScreenManager man, LoginSession sess){
		super(parent,man,sess);
		
		reportTiles = new ArrayList<BugReportGuiTile>();
		reports = new ArrayList<BugReport>();
	}
	
	protected void loadScreenPre(){
		updateBugReportList();
	}
	
	private class buttonListener implements ActionListener{
		private BugOverviewScreen parent;

		public buttonListener(BugOverviewScreen p){
			super();
			parent = p;
		}	

		public void actionPerformed(ActionEvent e){
			String command = e.getActionCommand();
			if(command.equals("account settings")){
				parent.accountSettingsClicked();
			}
			if(command.equals("logout")){
				parent.logoutClicked();
			}
			if(command.equals("new report")){
				parent.newReportClicked();
			}
		}
	}
	
	protected void setupGUI(){
		//create components
		title = new JLabel("Overview",JLabel.CENTER);
		header = new JPanel();
		titlebar = new JPanel();
		toolbar = new JPanel();
		
		bugViewer = new JScrollPane();
		bugViewerContent = new JPanel();
		
		accountSettingsButton = new JButton("Account Settings");
		logoutButton = new JButton("Logout");
		
		newReportButton = new JButton("Create New Bug Report");
		
		//configure components
		mainContentPanel.setLayout(new BorderLayout());
		header.setLayout(new BoxLayout(header,BoxLayout.Y_AXIS));
		titlebar.setLayout(new BorderLayout());
		toolbar.setLayout(new FlowLayout());
		
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		bugViewerContent.setLayout(new GridLayout(0,1));
		
		//configure events
		accountSettingsButton.setActionCommand("account settings");
		accountSettingsButton.addActionListener(new buttonListener(this));
		logoutButton.setActionCommand("logout");
		logoutButton.addActionListener(new buttonListener(this));
		newReportButton.setActionCommand("new report");
		newReportButton.addActionListener(new buttonListener(this));
		
		//organize components into containers
		header.add(titlebar);
		header.add(toolbar);
		titlebar.add(title,BorderLayout.CENTER);
		titlebar.add(logoutButton,BorderLayout.EAST);
		titlebar.add(accountSettingsButton,BorderLayout.WEST);
		toolbar.add(newReportButton);
		
		bugViewer.getViewport().add(bugViewerContent);
		
		mainContentPanel.add(header,BorderLayout.PAGE_START);
		mainContentPanel.add(bugViewer,BorderLayout.CENTER);
	}
	
	public void updateBugReportList(){
		//clear current bug viewer
		bugViewerContent.removeAll();
		//retrieve bug reports
		reportIds = BugReportDatabase.getBugReportIds();
		
		//load bug reports
		for(int i=0;i<reportIds.size();i++){
			reports.add(BugReportDatabase.getBugReport(reportIds.get(i)));
		}
		
		//create gui tiles
		for(int i=0;i<reports.size();i++){
			BugReportGuiTile newTile = new BugReportGuiTile(reports.get(i));
			reportTiles.add(newTile);
			bugViewerContent.add(newTile.getContentPanel());
		}
	}
	
	public void accountSettingsClicked(){
		manager.changeScreen("account settings");
	}
	
	public void logoutClicked(){
		session.logout();
	}
	
	public void newReportClicked(){
		System.out.println("new report screen");
	}
}