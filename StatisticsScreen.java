import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.text.*;

import org.jfree.chart.*;
import org.jfree.data.category.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.*;

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
	
	private class buttonListener implements ActionListener{
		private StatisticsScreen parent;

		public buttonListener(StatisticsScreen p){
			super();
			parent = p;
		}	

		public void actionPerformed(ActionEvent e){
			String command = e.getActionCommand();
			if(command.equals("back")){
				parent.backClicked();
			}
		}
	}
	
	protected void setupGUI(){
		//create components
		header = new JPanel();
		backButton = new JButton("Back");
		title = new JLabel("Report Statistics");
		
		content = new JPanel();
		//update();
		
		footer = new JPanel();
		
		//configure components
		mainContentPanel.setLayout(new BorderLayout());
		header.setLayout(new BorderLayout());
		content.setLayout(new FlowLayout());
		footer.setLayout(new FlowLayout());
		
		title.setHorizontalAlignment(SwingConstants.CENTER);
		
		//configure events
		backButton.setActionCommand("back");
		backButton.addActionListener(new buttonListener(this));
		
		//organize components into containers
		header.add(backButton,BorderLayout.WEST);
		header.add(title,BorderLayout.CENTER);
		
		//content.add(chart);
		
		mainContentPanel.add(header,BorderLayout.NORTH);
		mainContentPanel.add(content,BorderLayout.CENTER);
		mainContentPanel.add(footer,BorderLayout.SOUTH);
	}
	
	public void update(){
		JFreeChart chart = ChartFactory.createStackedAreaChart(
         "le title",           
         "Date",            
         "Reports",            
         createDataset(0,System.currentTimeMillis()/1000L,43200),          
         PlotOrientation.VERTICAL,           
         true, true, false);
		
		this.chart = new ChartPanel( chart );
		content.removeAll();
		content.add(this.chart);
	}
	
	private class IntervalData{
		public long startTime;
		public int newBugs;
		public int existingBugs;
		public int resolvedBugs;
		
		public IntervalData(long s){
			startTime = s;
			newBugs = 0;
			existingBugs = 0;
			resolvedBugs = 0;
		}
	}
	
	private CategoryDataset createDataset(long startDate, long endDate,int interval){
		//get all reports
		ArrayList<Integer> reportIds = BugReportDatabase.getBugReportIds();
		if(reportIds.size() < 1){
			return null;
		}
		ArrayList<BugReport> reports = new ArrayList<BugReport>();
		for(int i=0;i<reportIds.size();i++){
			reports.add(BugReportDatabase.getBugReport(reportIds.get(i)));
		}
		
		//sort data by intervals
		long firstTime = reports.get(0).getTime(reports.get(0).getFirstVersion());
		firstTime = (firstTime/interval) * interval;
		ArrayList<IntervalData> sortedData = new ArrayList<IntervalData>();

		long lastInterval = (reports.get(reports.size()-1).getTime(reports.get(reports.size()-1).getLatestVersion())/interval)*interval + interval;
		for(long time = firstTime;time < lastInterval;time+=interval){
			IntervalData intervalData = new IntervalData(time);
			sortedData.add(intervalData);
		}
	
		for(int i=0;i<reports.size();i++){
			BugReport report = reports.get(i);
			long timeStart = report.getTime(report.getFirstVersion());
			long startInterval = (timeStart/interval) * interval;
			long timeStop;
			long timeResolved;
			
			if(report.isResolved()){
				timeStop = report.getTime(report.getLatestVersion());
				timeResolved = report.getTime(report.getLatestVersion());
				long stopInterval = (timeStop/interval) * interval - interval;
				long resolvedInterval = (timeResolved/interval) * interval;
				
				int startIndex = (int)((startInterval - firstTime)/interval);
				int stopIndex = (int)((stopInterval - firstTime)/interval);
				int resolvedIndex = (int)((resolvedInterval - firstTime)/interval);
				
				if(startIndex != resolvedIndex){
					sortedData.get(startIndex).newBugs++;
					for(int k=startIndex+1;k<=stopIndex;k++){
						sortedData.get(k).existingBugs++;
					}
				}	

				sortedData.get(resolvedIndex).resolvedBugs++;
			}
			else{
				timeStop = report.getTime(report.getLatestVersion());
				long stopInterval = (timeStop/interval) * interval;
				
				int startIndex = (int)((startInterval - firstTime)/interval);
				int stopIndex = (int)((stopInterval - firstTime)/interval);
				
				sortedData.get(startIndex).newBugs++;
				for(int k=startIndex+1;k<sortedData.size();k++){
					sortedData.get(k).existingBugs++;
				}
			}
		}	
		
		//put into dataset
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		for(int i=0;i<sortedData.size();i++){
			if(sortedData.get(i).startTime < startDate || sortedData.get(i).startTime > endDate){
				continue;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
			
			String time = sdf.format(new Date(1000L*sortedData.get(i).startTime));
			
			dataset.addValue(sortedData.get(i).newBugs, "New Bugs", time);
			dataset.addValue(sortedData.get(i).existingBugs, "Existing Bugs", time);
			dataset.addValue(sortedData.get(i).resolvedBugs, "Resolved Bugs", time);
		}
		
        return dataset;
	}

	
	public void backClicked(){
		manager.changeScreen("overview");
	}
}