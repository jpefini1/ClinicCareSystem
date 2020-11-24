package cliniccaresystem.datalayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cliniccaresystem.model.Credentials;
import cliniccaresystem.model.MailingAddress;
import cliniccaresystem.model.ResultCode;
import cliniccaresystem.model.USState;
import cliniccaresystem.model.User;

public class CredentialsDatabaseClient extends DatabaseClient {
	public static User validateNurseLogin(String username, String password) throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING); 
		con.setAutoCommit(false);
		
		var userId = checkForUserIdForCredentials(con, username, password);
		var isUserANurse = checkIfUserIsNurse(con, userId);
		
		if (!isUserANurse) {
			return null;
		}
		
		var nurse = makeUserFromUserId(con, userId, username);
		return nurse;
	}
	
	public static User validateAdminLogin(String username, String password) throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING); 
		con.setAutoCommit(false);
		
		var userId = checkForUserIdForCredentials(con, username, password);
		var isUserAnAdmin = checkIfUserIsAdmin(con, userId);
		
		if (!isUserAnAdmin) {
			return null;
		}
		
		var nurse = makeUserFromUserId(con, userId, username);
		return nurse;
	}
	
	private static boolean checkIfUserIsNurse(Connection con, int userId) throws SQLException {
		String selectNurseIdCommand = "SELECT nurseId FROM nurse WHERE userId = ?";	
        PreparedStatement selectNurseId =  con.prepareStatement(selectNurseIdCommand, Statement.RETURN_GENERATED_KEYS);
        selectNurseId.setInt(1, userId);
        ResultSet rs = selectNurseId.executeQuery();
        
        if (rs.next()) {
        	return true;
        } else {
        	return false;
        }
	}
	
	private static boolean checkIfUserIsAdmin(Connection con, int userId) throws SQLException {
		String selectAdminIdCommand = "SELECT adminId FROM admin WHERE userId = ?";	
        PreparedStatement selectAdminId =  con.prepareStatement(selectAdminIdCommand, Statement.RETURN_GENERATED_KEYS);
        selectAdminId.setInt(1, userId);
        ResultSet rs = selectAdminId.executeQuery();
        
        if (rs.next()) {
        	return true;
        } else {
        	return false;
        }
	}
	
	private static User makeUserFromUserId(Connection con, int userId, String username) throws SQLException {
		PreparedStatement selectUser = con.prepareStatement("SELECT * FROM user WHERE id= ?");
		selectUser.setInt(1, userId);
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

	private static int checkForUserIdForCredentials(Connection con, String username, String password) throws SQLException {
		String callCredentialsValidateProcedure = "call credentials_validate(?,?);";
		
		PreparedStatement selectCredentials = con.prepareStatement(callCredentialsValidateProcedure);
		selectCredentials.setString(1, username);
		selectCredentials.setString(2, getHash(password));
		ResultSet userId = selectCredentials.executeQuery();
		
		if (userId.next()) {
			return userId.getInt(1);
		} else {
			return -1;
		}
	}
	
	public static void insertCredentials(Connection con, Credentials credentials, int userId) throws SQLException {
		String callCredentialsAddProcedure = "call credentials_add(?,?,?);";
		
		PreparedStatement pStatement = con.prepareStatement(callCredentialsAddProcedure, Statement.SUCCESS_NO_INFO);; 
		pStatement.setString(1, credentials.getUsername()); 
		pStatement.setString(2, getHash(credentials.getPassword())); 
		pStatement.setInt(3, userId);
		executePreparedStatement(pStatement);
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
	
	private static String getHash(String password) {
		return Integer.toString(password.hashCode());
	}
}
