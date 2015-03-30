import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.text.*;
import java.util.*;

public class BugReportGuiTile{
	private JPanel contentPanel;
	
	private JPanel basicInfo;
	private JLabel startedLabel;
	private JLabel lastEditedLabel;
	private JLabel lastEditedTimeLabel;
	
	private JLabel statusLabel;
	
	private JPanel summary;
	private JLabel titleLabel;
	private JLabel descriptionLabel;
	
	private JButton viewButton;
	
	private String startingUser;
	private String lastEditedUser;
	private String lastEditedTime;
	
	private String status;
	
	private String title;
	private String description;
	
	private LoginSession session;
	private ScreenManager manager;
	private BugReport bugReport;
	
	public BugReportGuiTile(BugReport report, LoginSession sess, ScreenManager man){	//accept account and action listener obj too
		bugReport = report;
		session = sess;
		manager = man;
		startingUser = "Started By: " + report.getAuthor(report.getFirstVersion());
		lastEditedUser = "Last Edited By: " + report.getAuthor(report.getLatestVersion());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
		lastEditedTime = "Last Edited On: " + sdf.format(new Date(1000L*report.getTime(report.getLatestVersion())));
	
		if(report.isResolved()){
			status = "Resolved";
		}
		else{
			status = "Not Resolved";
		}
	
		title = report.getTitle();
		description = report.getDescription(report.getLatestVersion(),true);
	
		setupGui();
	}
	
	private class buttonListener implements ActionListener{
		private BugReportGuiTile parent;

		public buttonListener(BugReportGuiTile p){
			super();
			parent = p;
		}	

		public void actionPerformed(ActionEvent e){
			parent.viewClicked();
		}
	}
	
	public void setupGui(){
		//create components
		contentPanel = new JPanel();
		basicInfo = new JPanel();
		startedLabel = new JLabel(startingUser);
		lastEditedLabel = new JLabel(lastEditedUser);
		lastEditedTimeLabel = new JLabel(lastEditedTime);
		
		statusLabel = new JLabel(status);
		
		summary = new JPanel();
		titleLabel = new JLabel(title);
		descriptionLabel = new JLabel(description);
	
		viewButton = new JButton("View Report");
		
		//init components
		contentPanel.setLayout(new FlowLayout());
		basicInfo.setLayout(new GridLayout(3,1));
		summary.setLayout(new GridLayout(2,1));
		
		//add event listeners
		viewButton.addActionListener(new buttonListener(this));
		
		//add components
		basicInfo.add(startedLabel);
		basicInfo.add(lastEditedLabel);
		basicInfo.add(lastEditedTimeLabel);
		
		summary.add(titleLabel);
		summary.add(descriptionLabel);
		
		contentPanel.add(basicInfo);
		contentPanel.add(new JLabel("     "));
		contentPanel.add(statusLabel);
		contentPanel.add(new JLabel("     "));
		contentPanel.add(summary);
		contentPanel.add(new JLabel("     "));
		contentPanel.add(viewButton);
	}
	
	public JPanel getContentPanel(){
		return contentPanel;
	}
	
	public void viewClicked(){
		session.activeReport = bugReport;
		manager.changeScreen("report editor");
	}
}