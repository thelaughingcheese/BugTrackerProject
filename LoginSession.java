public class LoginSession{
	private ScreenManager screenManager;
	public Account account;
	public BugReport activeReport;
	
	public LoginSession(ScreenManager man){
		account = null;
		screenManager = man;
		activeReport = null;
	}
	
	public void logout(){
		account = null;
		screenManager.changeScreen("login");
	}
}