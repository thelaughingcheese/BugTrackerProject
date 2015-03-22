public class LoginSession{
	private ScreenManager screenManager;
	public Account account;
	
	public LoginSession(ScreenManager man){
		account = null;
		screenManager = man;
	}
	
	public void logout(){
		account = null;
		screenManager.changeScreen("login");
	}
}