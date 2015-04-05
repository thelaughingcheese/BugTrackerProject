import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

import org.jfree.chart.*;
import org.jfree.data.category.*;
import org.jfree.chart.plot.PlotOrientation;

public class StatisticsScreen extends Screen{
	private JPanel header;
	private JButton backButton;
	private JLabel title;
	
	private JPanel content;
	ChartPanel chart;
	
	private JPanel footer;
	
	public StatisticsScreen(JFrame parent, ScreenManager man, LoginSession sess){
		super(parent,man,sess);
	}
	
	protected void loadScreenPre(){
		update();
	}
	
	protected void setupGUI(){
		//create components
		header = new JPanel();
		backButton = new JButton("Back");
		title = new JLabel("Report Statistics");
		
		content = new JPanel();
		update();
		
		footer = new JPanel();
		
		//configure components
		mainContentPanel.setLayout(new BorderLayout());
		header.setLayout(new BorderLayout());
		content.setLayout(new FlowLayout());
		footer.setLayout(new FlowLayout());
		
		title.setHorizontalAlignment(SwingConstants.CENTER);
		
		//configure events
		
		//organize components into containers
		header.add(backButton,BorderLayout.WEST);
		header.add(title,BorderLayout.CENTER);
		
		content.add(chart);
		
		mainContentPanel.add(header,BorderLayout.NORTH);
		mainContentPanel.add(content,BorderLayout.CENTER);
		mainContentPanel.add(footer,BorderLayout.SOUTH);
	}
	
	public void update(){
		JFreeChart chart = ChartFactory.createBarChart(
         "le title",           
         "Category",            
         "Score",            
         null,          
         PlotOrientation.VERTICAL,           
         true, true, false);
		
		this.chart = new ChartPanel( chart ); 
	}
}