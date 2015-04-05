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
import org.jdatepicker.impl.*;
import org.jdatepicker.*;
import javax.swing.JFormattedTextField.AbstractFormatter;

public class StatisticsScreen extends Screen{
	private JPanel header;
	private JButton backButton;
	private JLabel title;
	
	private JPanel content;
	ChartPanel chart;
	
	private JPanel footer;
	private JLabel startLabel;
	private JLabel endLabel;
	private JDatePickerImpl startDatePicker;
	private JDatePickerImpl endDatePicker;
	private JButton updateButton;
	
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
			else if(command.equals("update")){
				parent.updateClicked();
			}
		}
	}
	
	private class DateLabelFormatter extends AbstractFormatter {
		private String datePattern = "yyyy-MM-dd";
		private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

		@Override
		public Object stringToValue(String text) throws ParseException {
			return dateFormatter.parseObject(text);
		}

		@Override
		public String valueToString(Object value) throws ParseException {
			if (value != null) {
				Calendar cal = (Calendar) value;
				return dateFormatter.format(cal.getTime());
			}

			return "";
		}
	}
	
	protected void setupGUI(){
		//create components
		header = new JPanel();
		backButton = new JButton("Back");
		title = new JLabel("Report Statistics");
		
		content = new JPanel();
		
		footer = new JPanel();
		startLabel = new JLabel("From: ");
		endLabel = new JLabel("To: ");
		UtilDateModel startModel = new UtilDateModel();
		UtilDateModel endModel = new UtilDateModel();
		startModel.setValue(new Date("January 1 2000"));
		endModel.setValue(new Date(System.currentTimeMillis()));
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl startDatePanel = new JDatePanelImpl(startModel,p);
		JDatePanelImpl endDatePanel = new JDatePanelImpl(endModel,p);
		startDatePicker = new JDatePickerImpl(startDatePanel, new DateLabelFormatter());
		endDatePicker = new JDatePickerImpl(endDatePanel, new DateLabelFormatter());
		
		updateButton = new JButton("Update");
		
		//configure components
		mainContentPanel.setLayout(new BorderLayout());
		header.setLayout(new BorderLayout());
		content.setLayout(new FlowLayout());
		footer.setLayout(new FlowLayout());
		
		title.setHorizontalAlignment(SwingConstants.CENTER);
		
		//configure events
		backButton.setActionCommand("back");
		backButton.addActionListener(new buttonListener(this));
		updateButton.setActionCommand("update");
		updateButton.addActionListener(new buttonListener(this));
		
		//organize components into containers
		header.add(backButton,BorderLayout.WEST);
		header.add(title,BorderLayout.CENTER);
		
		footer.add(startLabel);
		footer.add(startDatePicker);
		footer.add(endLabel);
		footer.add(endDatePicker);
		footer.add(updateButton);
		
		mainContentPanel.add(header,BorderLayout.NORTH);
		mainContentPanel.add(content,BorderLayout.CENTER);
		mainContentPanel.add(footer,BorderLayout.SOUTH);
	}
	
	public void update(){
		JFreeChart chart = ChartFactory.createStackedAreaChart(
         "",           
         "Date",            
         "Reports",            
         createDataset(((Date)startDatePicker.getModel().getValue()).getTime()/1000,((Date)endDatePicker.getModel().getValue()).getTime()/1000,43200),          
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
	
	public void updateClicked(){
		update();
		manager.changeScreen("statistics");
	}
}