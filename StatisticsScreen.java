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
		update();
		
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
		
		content.add(chart);
		
		mainContentPanel.add(header,BorderLayout.NORTH);
		mainContentPanel.add(content,BorderLayout.CENTER);
		mainContentPanel.add(footer,BorderLayout.SOUTH);
	}
	
	public void update(){
		JFreeChart chart = ChartFactory.createStackedAreaChart(
         "le title",           
         "Date",            
         "Reports",            
         createDataset(0,System.currentTimeMillis()/1000L,100000),          
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

		int currentReportIndex = 0;
		for(long time = firstTime;currentReportIndex < reports.size();time+=interval){
			IntervalData intervalData = new IntervalData(time);
			if(currentReportIndex != 0){
				intervalData.existingBugs = sortedData.get(sortedData.size()-1).existingBugs;
				intervalData.existingBugs += sortedData.get(sortedData.size()-1).newBugs;
			}

			long nextInterval = time + interval;
			while(reports.get(currentReportIndex).getTime(reports.get(currentReportIndex).getFirstVersion()) < nextInterval){
				intervalData.newBugs++;
				currentReportIndex++;
				
				if(currentReportIndex == reports.size()){
					break;
				}
			}
			
			sortedData.add(intervalData);
		}
		
		currentReportIndex = 0;
		int index = 0;
		for(long time = firstTime;currentReportIndex < reports.size();time+=interval){
			IntervalData intervalData = sortedData.get(index);
		
			long nextInterval = time + interval;
			while(reports.get(currentReportIndex).getTime(reports.get(currentReportIndex).getLatestVersion()) < nextInterval){
				if(reports.get(currentReportIndex).isResolved()){
					long createTime = reports.get(currentReportIndex).getTime(reports.get(currentReportIndex).getFirstVersion());
					long resolveTime = reports.get(currentReportIndex).getTime(reports.get(currentReportIndex).getLatestVersion());

					if(createTime/interval == resolveTime/interval){
						intervalData.newBugs--;
					}
					else{
						intervalData.existingBugs--;
					}
					intervalData.resolvedBugs++;
				}
				
				currentReportIndex++;
				
				if(currentReportIndex == reports.size()){
					break;
				}
			}
			
			index++;
			if(index == sortedData.size()){
				break;
			}
		}
		
		//put into dataset
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		for(int i=0;i<sortedData.size();i++){
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