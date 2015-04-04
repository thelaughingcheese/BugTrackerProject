import java.util.*;
import java.io.*;

public class BugReport{
	private int id;
	private String title;
	
	private FileInputStream inStream;
	private BufferedReader reader;
	
	private FileOutputStream outStream;
	private BufferedWriter writer;
	
	public BugReport(int id){
		this.id = id;
		
		//temp
		if(!openMetaDataRead()){
			title = "Error Read Title";
		}
	
		try{
			title = reader.readLine();
		}
		catch(IOException e){
			System.out.println("Unexpected title read error");
		}


		closeMetaDataRead();
	}
	
	public void submitNewVersion(String user,String desc,boolean resolution){
		ArrayList<Integer> curVersions = getVersions();
		int newVersionId = curVersions.get(curVersions.size() - 1) + 1;
		long time = System.currentTimeMillis() / 1000L;
		
		//------------write resolution
		String title = getTitle();
		String res;
		if(resolution){
			res = "resolved";
		}
		else{
			res = "unresolved";
		}
		ArrayList<String> subscribers = getSubscribedUsers();
		
		openMetaDataWrite();
		
		try{
			writer.write(title);
			writer.newLine();
			writer.write(res);
			for(int i=0;i<subscribers.size();i++){
				writer.newLine();
				writer.write(subscribers.get(i));
			}
		}
		catch(IOException e){
			System.out.println("Unexpected Metadata Write");
		}
		
		closeMetaDataWrite();
		
		openVersionWrite(newVersionId);
		
		try{
			writer.write(user);
			writer.newLine();
			writer.write("" + time);
			writer.newLine();
			writer.write(desc);
		}
		catch(IOException e){
			System.out.println("Unexpected Version Write");
		}
		
		closeVersionWrite();
	}
	
	public boolean isResolved(){
		if(!openMetaDataRead()){
			return false;
		}
	
		boolean rtn = false;
	
		try{
			reader.readLine();
			if(reader.readLine().equals("resolved")){
				rtn = true;
			}
		}
		catch(IOException e){
			System.out.println("Unexpected title read error");
		}

		closeMetaDataRead();
		
		return rtn;
	}
	
	public ArrayList<Integer> getVersions(){
		ArrayList<Integer> rtn = new ArrayList<Integer>();
		
		File dir = new File("reports/"+id);
		File[] list = dir.listFiles();
		
		for(int i=0;i<list.length;i++){
			if(!list[i].getName().equals("metadata")){
				rtn.add(Integer.parseInt(list[i].getName()));
			}
		}
		
		Collections.sort(rtn);
	
		return rtn;
	}
	
	public ArrayList<String> getSubscribedUsers(){
		ArrayList<String> rtn = new ArrayList<String>();

		if(!openMetaDataRead()){
			return rtn;
		}
		
		try{
			reader.readLine();
			reader.readLine();
			for(;;){
				String line = reader.readLine();
				if(line == null){
					break;
				}
				rtn.add(line);
			}
		}
		catch(IOException e){
			System.out.println("Unexpected subscription read error");
		}
	
		closeMetaDataRead();
		
		return rtn;
	}
	
	public boolean isSubscribed(String user){
		ArrayList<String> subs = getSubscribedUsers();
		
		for(int i=0;i<subs.size();i++){
			if(subs.get(i).equals(user)){
				return true;
			}
		}
		
		return false;
	}
	
	public void changeSubscription(String user, boolean isSub){
		String title = getTitle();
		String res = "";
		
		if(!openMetaDataRead()){
			return;
		}
		
		try{
			reader.readLine();
			res = reader.readLine();
		}
		catch(IOException e){
			System.out.println("Unexpected subscription read error");
		}
		
		closeMetaDataRead();
		
		ArrayList<String> subscribers = getSubscribedUsers();
		
		//add or remove subscriber
		if(isSub){
			subscribers.add(user);
		}
		else{
			int index = 0;
			for(int i=0;i<subscribers.size();i++){
				if(subscribers.get(i).equals(user)){
					index = i;
					break;
				}
			}
			subscribers.remove(index);
		}
		
		//write back to file
		openMetaDataWrite();
		
		try{
			writer.write(title);
			writer.newLine();
			writer.write(res);
			for(int i=0;i<subscribers.size();i++){
				writer.newLine();
				writer.write(subscribers.get(i));
			}
		}
		catch(IOException e){
			System.out.println("Unexpected Metadata Write error");
		}
		
		closeMetaDataWrite();
	}
	
	public int getLatestVersion(){
		ArrayList<Integer> ver = getVersions();
		return ver.get(ver.size()-1);
	}
	
	public int getFirstVersion(){
		return getVersions().get(0);
	}
	
	public int getId(){
		return id;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getDescription(int version, boolean html){
		if(!openVersionRead(version)){
			return "DESC ERROR";
		}
		
		String rtn = "";
		if(html){
			rtn += "<html>";
		}
		try{
			reader.readLine();
			reader.readLine();
			for(;;){
				String line = reader.readLine();
				if(line == null){
					break;
				}
				rtn += line;
				if(html){
					rtn += "<br>";
				}
			}
		}
		catch(IOException e){
			System.out.println("Unexpected description read error");
		}
	
		closeVersionRead();
	
		return rtn;
	}
	
	public String getAuthor(int version){
		if(!openVersionRead(version)){
			return "AUTHOR ERROR";
		}
		
		String rtn = "AUTHOR ERROR";
		try{
			rtn = reader.readLine();
		}
		catch(IOException e){
			System.out.println("Unexpected author read error");
		}
	
		closeVersionRead();
	
		return rtn;
	}
	
	public int getTime(int version){
		if(!openVersionRead(version)){
			return 0;
		}
		
		int rtn = 0;
		try{
			reader.readLine();
			rtn = Integer.parseInt(reader.readLine());
		}
		catch(IOException e){
			System.out.println("Unexpected time read error");
		}
	
		closeVersionRead();
	
		return rtn;
	}
	
	private boolean openMetaDataRead(){
		try{
			inStream = new FileInputStream("reports/"+id+"/metadata");
			reader = new BufferedReader(new InputStreamReader(inStream));
			return true;
		}
		catch(IOException e){
			System.out.println("unexpected error reading report metadata");
			return false;
		}
	}
	
	private void closeMetaDataRead(){
		try{
			reader.close();
			inStream.close();
		}
		catch(IOException e){
			System.out.println("Unexpected report metadata file close error");
		}
	}
	
	private boolean openMetaDataWrite(){
		try{
			outStream = new FileOutputStream("reports/"+id+"/metadata");
			writer = new BufferedWriter(new OutputStreamWriter(outStream));
			return true;
		}
		catch(IOException e){
			System.out.println("unexpected error writing report metadata");
			return false;
		}
	}
	
	private void closeMetaDataWrite(){
		try{
			writer.close();
			outStream.close();
		}
		catch(IOException e){
			System.out.println("Unexpected report metadata file close error");
		}
	}
	
	private boolean openVersionRead(int version){
		try{
			inStream = new FileInputStream("reports/"+id+"/"+version);
			reader = new BufferedReader(new InputStreamReader(inStream));
			return true;
		}
		catch(IOException e){
			System.out.println("unexpected error reading report version");
			return false;
		}
	}
	
	private void closeVersionRead(){
		try{
			reader.close();
			inStream.close();
		}
		catch(IOException e){
			System.out.println("Unexpected report version file close error");
		}
	}
	
	private boolean openVersionWrite(int version){
		try{
			outStream = new FileOutputStream("reports/"+id+"/"+version);
			writer = new BufferedWriter(new OutputStreamWriter(outStream));
			return true;
		}
		catch(IOException e){
			System.out.println("unexpected error writing report version");
			return false;
		}
	}
	
	private void closeVersionWrite(){
		try{
			writer.close();
			outStream.close();
		}
		catch(IOException e){
			System.out.println("Unexpected report version file close error");
		}
	}
}