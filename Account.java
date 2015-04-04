import java.io.*;

public class Account{
	private String username;
	private FileInputStream userFile;
	private FileOutputStream userFileWrite;
	private BufferedReader reader;
	private BufferedWriter writer;
	
	public Account(){
	}
	
	public boolean setUsername(String user){
		if(!openUserFileRead(user)){
			return false;
		}

		closeUserFileRead();
		
		username = user;
		return true;
	}
	
	public boolean checkPassword(String pass){
		boolean passCorrect = false;
		if(!openUserFileRead(username)){
			System.out.println("Tried checking password of invalid username");
			return false;
		}
	
		//check password
		try{
			String line = reader.readLine();
			if(line != null && !line.equals(pass)){
				passCorrect = false;
			}
			else{
				passCorrect = true;
			}
		}
		catch(IOException e){
			System.out.println("Unexpected password read error");
			passCorrect = false;
		}
		finally{
			closeUserFileRead();
		}
		
		return passCorrect;
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getEmail(){
		String e = "";
		if(!openUserFileRead(username)){
			System.out.println("could not get email, error, could not open file");
			return "none";
		}
		
		try{
			reader.readLine();
			e = reader.readLine();
		}
		catch(IOException ex){
			System.out.println("Unexpected email read error");
			e = "none";
		}
		
		closeUserFileRead();
		
		return e;
	}
	
	public boolean changePassword(String newPass){
		if(!openUserFileRead(username)){
			System.out.println("Tried checking password of invalid username");
			return false;
		}
		
		String password;
		String email;
		
		try{
			password = reader.readLine();
			email = reader.readLine();
		}
		catch(IOException e){
			System.out.println("Unexpected user file read error for password change");
			closeUserFileRead();
			return false;
		}
		closeUserFileRead();
		
		//change and write back
		password = newPass;
		
		openUserFileWrite(username);
		
		try{
			writer.write(password);
			writer.newLine();
			writer.write(email);
		}
		catch(IOException e){
			System.out.println("Unexpected user file write error for password change");
			closeUserFileWrite();
			return false;
		}

		closeUserFileWrite();
	
		return true;
	}
	
	public boolean changeEmail(String newEmail){
		if(!openUserFileRead(username)){
			System.out.println("Tried checking password of invalid username");
			return false;
		}
		
		String password;
		String email;
		
		try{
			password = reader.readLine();
			email = reader.readLine();
		}
		catch(IOException e){
			System.out.println("Unexpected user file read error for password change");
			closeUserFileRead();
			return false;
		}
		closeUserFileRead();
		
		//change and write back
		email = newEmail;
		
		openUserFileWrite(username);
		try{
			writer.write(password);
			writer.newLine();
			writer.write(email);
		}
		catch(IOException e){
			System.out.println("Unexpected user file write error for email change");
			closeUserFileWrite();
			return false;
		}
		closeUserFileWrite();
	
		return false;
	}
	
	private boolean openUserFileWrite(String user){
		try{
			userFileWrite = new FileOutputStream("users/"+user);
			writer = new BufferedWriter(new OutputStreamWriter(userFileWrite));
			return true;
		}
		catch(FileNotFoundException e){
			return false;
		}
	}
	
	private void closeUserFileWrite(){
		try{
			writer.close();
			userFileWrite.close();
		}
		catch(IOException e){
			System.out.println("Unexpected user file close error");
		}
	}
	
	private boolean openUserFileRead(String user){
		try{
			userFile = new FileInputStream("users/"+user);
			reader = new BufferedReader(new InputStreamReader(userFile));
			return true;
		}
		catch(FileNotFoundException e){
			return false;
		}
	}
	
	private void closeUserFileRead(){
		try{
			reader.close();
			userFile.close();
		}
		catch(IOException e){
			System.out.println("Unexpected user file close error");
		}
	}
}