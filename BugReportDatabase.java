import java.util.*;
import java.io.*;

public class BugReportDatabase{
	private static FileInputStream fileInputStream;
	private static BufferedReader bufferedReader;

	public static ArrayList<Integer> getBugReportIds(){
		ArrayList<Integer> rtn = new ArrayList<Integer>();
		
		if(!openManifest()){
			System.out.println("Could not open manifest");
			return rtn;
		}
		
		//read manifest
		for(;;){
			try{
				String line = bufferedReader.readLine();
				if(line == null){
					break;
				}
				rtn.add(Integer.valueOf(line));
			}
			catch(IOException e){
				System.out.println("Unexpected manifest read error");
				break;
			}
		}
		
		closeManifest();
		
		return rtn;
	}
	
	public static BugReport getBugReport(int id){
		BugReport rtn = new BugReport(id);
		
		return rtn;
	}
	
	private static boolean openManifest(){
		try{
			fileInputStream = new FileInputStream("reports/manifest.txt");
			bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
			return true;
		}
		catch(IOException e){
			return false;
		}
	}
	
	private static void closeManifest(){
		try{
			bufferedReader.close();
			fileInputStream.close();
		}
		catch(IOException e){
			System.out.println("Unexpected manifest file close error");
		}
	}
}