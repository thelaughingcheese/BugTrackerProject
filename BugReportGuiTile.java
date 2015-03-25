import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

public class BugReportGuiTile{
	private JPanel contentPanel;
	
	private JPanel basicInfo;
	private JLabel startedLabel;
	private JLabel lastEditedLabel;
	private JLabel lastEditedTimeLabel;
	
	private JLabel status;
	
	private JPanel summary;
	private JLabel titleLabel;
	private JLabel descriptionLabel;
	
	private JButton viewButton;
	
	public BugReportGuiTile(){	//accept account and action listener obj too
		setupGui();
	}
	
	public setupGui(){
		//create components
		contentPanel = new JPanel();
		basicInfo = new JPanel();
		startedLabel;
		lastEditedLabel;
		lastEditedTimeLabel;
		
		status = new JLabel();
		
		summary = new JPanel();
		titleLabel;
		descriptionLabel;
	
		viewButton = new JButton();
		
		//init components
		contentPanel.setLayout(new FlowLayout());
		
		//add event listeners
		
		//add components
		contentPanel.add(status);
		contentPanel.add(basicInfo);
		contentPanel.add(summary);
		contentPanel.add(viewButton);
	}
	
	public JPanel getContentPanel(){
		return contentPanel;
	}
}