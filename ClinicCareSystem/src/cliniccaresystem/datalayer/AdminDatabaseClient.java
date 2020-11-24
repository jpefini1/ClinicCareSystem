package cliniccaresystem.datalayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import cliniccaresystem.model.Admin;
import cliniccaresystem.model.Credentials;
import cliniccaresystem.model.MailingAddress;
import cliniccaresystem.model.Nurse;
import cliniccaresystem.model.ResultCode;
import cliniccaresystem.model.User;

public class AdminDatabaseClient extends DatabaseClient {

	public static ResultCode AddAdmin(Admin admin, Credentials credentials) throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING); 
		con.setAutoCommit(false);
		
		var result = CredentialsDatabaseClient.isUsernameTaken(con, credentials.getUsername());
		
		if (result) {
			return ResultCode.UsernameUnavailable;
		}
		
		MailingAddress mAddress = admin.getMailingAddress();
		var generatedAddressKey = UserDatabaseClient.insertMailingAddress(con, mAddress);
		
		var generatedUserKey = UserDatabaseClient.insertUser(con, admin, generatedAddressKey);
		admin.setUserId(generatedUserKey);
		
		CredentialsDatabaseClient.insertCredentials(con, credentials, generatedUserKey);
		
		var generatedNurseKey = insertAdmin(con, admin);
		admin.setAdminId(generatedNurseKey);
		
		con.commit();
		return ResultCode.Success;
	}
	
	private static int insertAdmin(Connection con, User user) throws SQLException {
		String insertAdmin = "INSERT INTO admin ( userId, adminId ) VALUES (?,?)";
		
        PreparedStatement pStatement =  con.prepareStatement(insertAdmin, Statement.RETURN_GENERATED_KEYS);  
        	
		pStatement.setInt(1, user.getUserId());
		pStatement.setString(2, null);
			
		pStatement.executeUpdate();
			
		var generatedUserKeyResult = pStatement.getGeneratedKeys();
		generatedUserKeyResult.next();
		return Integer.parseInt(generatedUserKeyResult.getString(1));
	}
}
