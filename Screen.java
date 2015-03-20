import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

public class Screen{
	protected JFrame parentFrame;
	protected ScreenManager manager;
	protected LoginSession session;
	
	protected JPanel mainContentPanel;
	
	public Screen(JFrame parent, ScreenManager man, LoginSession sess){
		parentFrame = parent;
		manager = man;
		session = sess;
		
		mainContentPanel = new JPanel();
		setupGUI();
	}

	public Screen(JFrame parent, ScreenManager man){
		parentFrame = parent;
		manager = man;
		
		mainContentPanel = new JPanel();
		setupGUI();
	}
	
	protected void setupGUI(){System.out.println("screen setup");}
	protected void loadScreenPre(){}
	
	public void loadScreen(){
		loadScreenPre();
	
		parentFrame.add(mainContentPanel);
		parentFrame.getContentPane().validate();
	}
}