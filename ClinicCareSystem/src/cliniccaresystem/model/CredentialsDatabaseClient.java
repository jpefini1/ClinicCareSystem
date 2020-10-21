package cliniccaresystem.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CredentialsDatabaseClient extends DatabaseClient{
	
	public static User ValidateCredentials(String username, String password) throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING); 
		
        PreparedStatement selectCredentials =  con.prepareStatement("SELECT * FROM credentials WHERE username = ? AND password = ?");
		selectCredentials.setString(1, username);
		selectCredentials.setString(2, password);
		ResultSet credentials = selectCredentials.executeQuery();
		
		if (credentials.next()) {
			PreparedStatement selectUser = con.prepareStatement("SELECT * FROM user WHERE id= ?");
			selectUser.setInt(1, credentials.getInt(3));
			ResultSet user = selectUser.executeQuery();
			user.next();
			
			PreparedStatement selectAddress = con.prepareStatement("SELECT * FROM address WHERE addressId= ?");
			selectAddress.setInt(1, user.getInt(5));
			ResultSet addressData = selectAddress.executeQuery();
			addressData.next();
			
			MailingAddress address = new MailingAddress(addressData.getString(2), addressData.getString(3), USState.valueOf(addressData.getString(4)), addressData.getString(5));
			User activeUser = new User(user.getInt(1), username, user.getString(2), user.getString(3), user.getDate(4).toLocalDate(), address, user.getString(6));
			return activeUser;
		}
       
		return null;
	}
	
	public static void insertCredentials(Connection con, Credentials credentials, int userId) throws SQLException {
		String insertCredentials = "INSERT INTO credentials ( username, password, uid ) VALUES (?,?,?)";
		
        PreparedStatement pStatement =  con.prepareStatement(insertCredentials, Statement.SUCCESS_NO_INFO);; 
        
		pStatement.setString(1, credentials.getUsername());
		pStatement.setString(2, credentials.getPassword());
		pStatement.setInt(3, userId);
		
		pStatement.executeUpdate();
	}
	
	public static boolean isUsernameTaken(Connection con, String username) throws SQLException {
        Statement stmt =  con.createStatement();  
        	
		ResultSet rs = stmt.executeQuery("SELECT username FROM credentials");
			
		while (rs.next()) {
			if (rs.getString(1).equalsIgnoreCase(username)) {
				return true;
			}
		}
        
		return false;
	}
}