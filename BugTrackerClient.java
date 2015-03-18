import javax.swing.*;

public class BugTrackerClient{
	private JFrame mainFrame;
	private ScreenManager screenManager;
	private LoginSession loginSession;
	
	private LoginScreen loginScreen;
	private RegisterScreen registerScreen;

	public BugTrackerClient(){
		screenManager = new ClientScreenManager();
		loginSession = new LoginSession();
	
		setupMainFrame();
		loginScreen = new LoginScreen(mainFrame, screenManager, loginSession);
		registerScreen = new RegisterScreen(mainFrame,screenManager);
		
		loginScreen.loadScreen();
		mainFrame.setVisible(true);
	}
	
	private void setupMainFrame(){
		mainFrame = new JFrame("Bug Tracker 9000");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(800,600);
	}
	
	private void clearScreen(){
		mainFrame.getContentPane().removeAll();
		mainFrame.getContentPane().repaint();
	}
	
	private class ClientScreenManager implements ScreenManager{
		public void changeScreen(String screen){
			clearScreen();
			if(screen.equals("login")){
				System.out.println("Login and go to bug overview");
				loginScreen.loadScreen();
			}
			else if(screen.equals("register")){
				System.out.println("go to registration screen");
				registerScreen.loadScreen();
			}
			else{
				System.out.println("Unknown screen: " + screen);
			}
		}
	}
}