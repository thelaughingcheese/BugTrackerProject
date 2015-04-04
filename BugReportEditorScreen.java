import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

public class BugReportEditorScreen extends Screen{
	private JPanel reportDetails;
	private JPanel footer;

	private JLabel title;
	
	private JLabel reportIdLabel;
	private JLabel reportId;
	private JLabel reportTitleLabel;
	private JTextField reportTitle;
	private JLabel descriptionLabel;
	private JTextArea description;
	
	private String[] resolutionOptions;
	private JComboBox resolution;
	
	private JButton cancelButton;
	private JButton submitButton;
	
	public BugReportEditorScreen(JFrame parent, ScreenManager man, LoginSession sess){
		super(parent,man,sess);
	}
	
	protected void loadScreenPre(){
		if(session.activeReport == null){
			reportId.setText("Available once submitted");
			reportTitle.setText("");
			description.setText("");
			resolution.setSelectedIndex(0);
			reportTitle.setEditable(true);
		}
		else{
			reportId.setText("" + session.activeReport.getId());
			reportTitle.setText(session.activeReport.getTitle());
			description.setText(session.activeReport.getDescription(session.activeReport.getLatestVersion(),false));
			if(session.activeReport.isResolved()){
				resolution.setSelectedIndex(1);
			}
			else{
				resolution.setSelectedIndex(0);
			}
			reportTitle.setEditable(false);
		}
	}
	
	private class buttonListener implements ActionListener{
		private BugReportEditorScreen parent;

		public buttonListener(BugReportEditorScreen p){
			super();
			parent = p;
		}	

		public void actionPerformed(ActionEvent e){
			String command = e.getActionCommand();
			if(command.equals("cancel")){
				parent.cancelClicked();
			}
			if(command.equals("submit")){
				parent.submitClicked();
			}
		}
	}
	
	protected void setupGUI(){
		//create components
		reportDetails = new JPanel();
		footer = new JPanel();
		
		title = new JLabel("Editor!");
		
		reportIdLabel = new JLabel("Report ID: ", JLabel.RIGHT);
		reportId = new JLabel("Not set");
		reportTitleLabel = new JLabel("Bug: ", JLabel.RIGHT);
		reportTitle = new JTextField("Not set");
		descriptionLabel = new JLabel("Description: ", JLabel.RIGHT);
		description = new JTextArea("");
	
		String[] resolutionOptions = {"Unresolved","Resolved"};
		resolution = new JComboBox(resolutionOptions);
	
		cancelButton = new JButton("Cancel");
		submitButton = new JButton("Submit");
		
		//configure components
		mainContentPanel.setLayout(new BorderLayout());
		reportDetails.setLayout(new GridLayout(3,2));
		footer.setLayout(new FlowLayout());
		
		resolution.setSelectedIndex(0);
		
		//configure events
		cancelButton.setActionCommand("cancel");
		cancelButton.addActionListener(new buttonListener(this));
		submitButton.setActionCommand("submit");
		submitButton.addActionListener(new buttonListener(this));
		
		//organize components into containers
		reportDetails.add(reportIdLabel);
		reportDetails.add(reportId);
		reportDetails.add(reportTitleLabel);
		reportDetails.add(reportTitle);
		reportDetails.add(descriptionLabel);
		reportDetails.add(description);
		reportDetails.add(resolution);
		
		footer.add(cancelButton);
		footer.add(submitButton);
		
		mainContentPanel.add(title,BorderLayout.NORTH);
		mainContentPanel.add(reportDetails,BorderLayout.CENTER);
		mainContentPanel.add(footer,BorderLayout.SOUTH);
	}
	
	public void cancelClicked(){
		manager.changeScreen("overview");
	}
	
	public void submitClicked(){
		if(session.activeReport == null){
			BugReportDatabase.createNewReport(session.account.getUsername(), reportTitle.getText(), description.getText());
		}
		else{
			if(session.activeReport.isResolved()){
				JOptionPane.showMessageDialog(parentFrame,
				"Bug Already Resolved! Cannot edit further.",
				"Bug Already Resolved",
				JOptionPane.WARNING_MESSAGE);
				return;
			}
			else{
				boolean isResolved;
				if(resolution.getSelectedItem().equals("Resolved")){
					isResolved = true;
				}
				else{
					isResolved = false;
				}
				session.activeReport.submitNewVersion(session.account.getUsername(),description.getText(),isResolved);
			}
		}
		manager.changeScreen("overview");
	}
}