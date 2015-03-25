import java.io.*;

public class Account{
	private String username;
	private FileInputStream userFile;
	private FileOutputStream userFileWrite;
	private BufferedReader reader;
	private BufferedWriter writer;
	

	public Account(){
	}
	
	/**
	*sets the username for the user
	*@param changes username to user
	*@return returns true if succuessfully changed. Returns false otherwise.
	*/
	public boolean setUsername(String user){
		if(!openUserFileRead(user)){
			return false;
		}

		closeUserFileRead();
		
		username = user;
		return true;
	}
	/**
	*Checks the to see if the users password is correct.
	*@param password to be compared with
	*@return returns true if the inputed password is the same as "pass". Otherwise return false.
	*/
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
		return "none";
	}

	/**
	*changes the password of the user
	*@param new desired password
	*@return returns false only if an error occurs. i.e file is not open for read or write. 
	*/
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

	/**
	*changes the email of the user.
	*@param new desired email.
	*@return returns false only if an error occurs. i.e file is not open for read or write. 
	*/
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
	/**
	*opens a file containing users information in the folder "user" for writing
	*@param name of file in "users" folder (username)
	*@return returns true if file is found and is open for writing. False otherwise.
	*/
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
	/**
	*closes the user file for writing
	*/
	private void closeUserFileWrite(){
		try{
			writer.close();
			userFileWrite.close();
		}
		catch(IOException e){
			System.out.println("Unexpected user file close error");
		}
	}

	/**
	*Opens a file containg users information in the folder "users" for reading
	*@param name of file in "users" folder (username)
	*@return returns true if file is found and is open for reading. False otherwise.
	*/
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
	
	/**
	*Closes the user file for reading.
	*/
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
