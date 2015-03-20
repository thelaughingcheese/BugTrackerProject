import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

public class BugOverviewScreen extends Screen{
	private JLabel title;
	
	public BugOverviewScreen(JFrame parent, ScreenManager man, LoginSession sess){
		super(parent,man,sess);
	}
	
	protected void loadScreenPre(){
	}
	
	protected void setupGUI(){
		//create components
		title = new JLabel("Overview",JLabel.CENTER);
		
		
		//configure components
		//configure events
		//organize components into containers
	}
}