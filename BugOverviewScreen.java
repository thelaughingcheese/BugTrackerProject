import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

public class BugOverviewScreen extends Screen{
	private JLabel title;
	private JPanel header;
	private JPanel toolbar;
	
	private JScrollPane bugViewer;
	
	private JButton logoutButton;
	
	public BugOverviewScreen(JFrame parent, ScreenManager man, LoginSession sess){
		super(parent,man,sess);
	}
	
	protected void loadScreenPre(){
	}
	
	protected void setupGUI(){
		//create components
		title = new JLabel("Overview",JLabel.CENTER);
		header = new JPanel();
		toolbar = new JPanel();
		
		bugViewer = new JScrollPane();
		
		logoutButton = new JButton("Logout");
		
		//configure components
		mainContentPanel.setLayout(new BorderLayout());
		header.setLayout(new BoxLayout(header,BoxLayout.Y_AXIS));
		toolbar.setLayout(new FlowLayout());
		
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		//configure events
		
		//organize components into containers
		header.add(title);
		header.add(toolbar);
		toolbar.add(logoutButton);
		
		bugViewer.getViewport().add(new JLabel("DERRRRRRRRRRRRRRRRPPPPPPPRRRRRPPPPPPPRRRRRPPPPPPPRRRRRPPPPPPPRRRRRPPPPPPPRRRRRPPPPPPPRRRRRPPPPPPPRRRRRPPPPPPPRRRRRPPPPPPPRRRRRPPPPPPPRRRPPPPPPPPPPPPPPPP"));
		
		mainContentPanel.add(header,BorderLayout.PAGE_START);
		mainContentPanel.add(bugViewer,BorderLayout.CENTER);
	}
}