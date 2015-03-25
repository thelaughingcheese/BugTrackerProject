

public class BugReport{
	private int id;
	private String title;
	
	public BugReport(){
	}
	
	public boolean isResolved(){
		return false;
	}
	
	public int[] getVersions(){
		return new int[0];
	}
	
	public String[] getSubscribedUsers(){
		return new String[0];
	}
	
	public int getLatestVersion(){
		return 1;
	}
	
	public int getFirstVersion(){
		return 1;
	}
	
	public String getAuthor(int version){
		return "";
	}
	
	public String getReport(int version){
		return "";
	}
}