import java.util.*;
import java.io.*;

public class BugReportDatabase{
	private static FileInputStream fileInputStream;
	private static BufferedReader bufferedReader;
	
	private static FileOutputStream fileOutputStream;
	private static BufferedWriter bufferedWriter;

	public static ArrayList<Integer> getBugReportIds(){
		ArrayList<Integer> rtn = new ArrayList<Integer>();
		
		if(!openManifestRead()){
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
		
		Collections.sort(rtn);
		
		closeManifestRead();
		
		return rtn;
	}
	
	public static BugReport getBugReport(int id){
		BugReport rtn = new BugReport(id);
		
		return rtn;
	}
	
	public static boolean createNewReportTime(String user, String title, String desc,long time){
		ArrayList<Integer> curReports = getBugReportIds();
		int id;
		if(curReports.size() == 0){
			id = 1;
		}
		else{
			id = curReports.get(curReports.size()-1) + 1;
		}
		
		//create directory
		boolean success = (new File("reports/"+id)).mkdirs();
		if(!success){
			System.out.println("could not create directory for report!");
			return false;
		}
		
		//create metadata
		if(!openMetadataWrite(id)){
			System.out.println("could not write metadata for report!");
			return false;
		}
		
		try{
			bufferedWriter.write(title);
			bufferedWriter.newLine();
			bufferedWriter.write("unresolved");
			bufferedWriter.newLine();
			bufferedWriter.write(user);
		}
		catch(IOException e){
			System.out.println("Unexpected metadata write error");
			return false;
		}
		
		closeVersionWrite();
		//create first version
		if(!openVersionWrite(id,1)){
			System.out.println("could not write version for report!");
			return false;
		}
		
		try{
			bufferedWriter.write(user);
			bufferedWriter.newLine();
			bufferedWriter.write("" + time);
			bufferedWriter.newLine();
			bufferedWriter.write(desc);
		}
		catch(IOException e){
			System.out.println("Unexpected version write error");
			return false;
		}
		
		closeVersionWrite();
		//update manifest
		if(!openManifestWrite()){
			System.out.println("could not write version for report!");
			return false;
		}
		
		try{
			for(int i=0;i<curReports.size();i++){
				bufferedWriter.write(""+curReports.get(i));
				bufferedWriter.newLine();
			}
			bufferedWriter.write(""+id);
		}
		catch(IOException e){
			System.out.println("Unexpected manifest write error");
			return false;
		}
		
		closeManifestWrite();
		
		return true;
	}
	
	public static boolean createNewReport(String user, String title, String desc){
		long time = System.currentTimeMillis() / 1000L;
		
		return createNewReportTime(user,title,desc,time);
	}
	
	private static boolean openManifestRead(){
		try{
			fileInputStream = new FileInputStream("reports/manifest.txt");
			bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
			return true;
		}
		catch(IOException e){
			return false;
		}
	}
	
	private static void closeManifestRead(){
		try{
			bufferedReader.close();
			fileInputStream.close();
		}
		catch(IOException e){
			System.out.println("Unexpected manifest file close error");
		}
	}
	
	private static boolean openManifestWrite(){
		try{
			fileOutputStream = new FileOutputStream("reports/manifest.txt");
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
			return true;
		}
		catch(IOException e){
			return false;
		}
	}
	
	private static void closeManifestWrite(){
		try{
			bufferedWriter.close();
			fileOutputStream.close();
		}
		catch(IOException e){
			System.out.println("Unexpected manifest file close error");
		}
	}
	
	private static boolean openMetadataWrite(int reportId){
		try{
			fileOutputStream = new FileOutputStream("reports/"+reportId+"/metadata");
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
			return true;
		}
		catch(IOException e){
			return false;
		}
	}
	
	private static void closeMetadataWrite(){
		try{
			bufferedWriter.close();
			fileOutputStream.close();
		}
		catch(IOException e){
			System.out.println("Unexpected metadata file close error");
		}
	}
	
	private static boolean openVersionWrite(int reportId,int versionId){
		try{
			fileOutputStream = new FileOutputStream("reports/"+reportId+"/"+versionId);
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
			return true;
		}
		catch(IOException e){
			return false;
		}
	}
	
	private static void closeVersionWrite(){
		try{
			bufferedWriter.close();
			fileOutputStream.close();
		}
		catch(IOException e){
			System.out.println("Unexpected version file close error");
		}
	}
}