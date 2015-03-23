import javax.swing.*;

public class BugTrackerClient{
	private JFrame mainFrame;
	private ScreenManager screenManager;
	private LoginSession loginSession;
	
	private LoginScreen loginScreen;
	private RegisterScreen registerScreen;
	private BugOverviewScreen bugOverviewScreen;
	private AccountSettingsScreen accountSettingsScreen;

	public BugTrackerClient(){
		screenManager = new ClientScreenManager();
		loginSession = new LoginSession(screenManager);
	
		setupMainFrame();
		loginScreen = new LoginScreen(mainFrame, screenManager, loginSession);
		registerScreen = new RegisterScreen(mainFrame,screenManager);
		bugOverviewScreen = new BugOverviewScreen(mainFrame, screenManager, loginSession);
		accountSettingsScreen = new AccountSettingsScreen(mainFrame, screenManager, loginSession);
		
		loginScreen.loadScreen();
		//registerScreen.loadScreen();
		//bugOverviewScreen.loadScreen();
		//accountSettingsScreen.loadScreen();
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
				loginScreen.loadScreen();
			}
			else if(screen.equals("register")){
				registerScreen.loadScreen();
			}
			else if(screen.equals("overview")){
				bugOverviewScreen.loadScreen();
			}
			else if(screen.equals("account settings")){
				accountSettingsScreen.loadScreen();
			}
			else{
				System.out.println("Unknown screen: " + screen);
			}
			mainFrame.getContentPane().validate();
		}
	}
}