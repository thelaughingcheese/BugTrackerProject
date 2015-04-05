import org.jfree.chart.*;
import javax.swing.*;
import org.jfree.data.category.*;
import org.jfree.chart.plot.*;

public class chartTest{
	private static CategoryDataset createDataset( ){
		return null;
	}

	public static void main(String[] args){
		JFrame mainFrame;
		
		JFreeChart barChart = ChartFactory.createBarChart(
         "le title",           
         "Category",            
         "Score",            
         createDataset(),          
         PlotOrientation.VERTICAL,           
         true, true, false);
		 
		ChartPanel chartPanel = new ChartPanel( barChart );    
		
		mainFrame = new JFrame("Bug Tracker 9000");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(800,600);
		
		mainFrame.add(chartPanel);
		
		mainFrame.setVisible(true);
	}
}