package cliniccaresystem.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class NurseDatabaseClient extends DatabaseClient{
	
	public static ResultCode AddNurse(Nurse nurse, Credentials credentials) throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING); 
		con.setAutoCommit(false);
		
		var result = CredentialsDatabaseClient.isUsernameTaken(con, credentials.getUsername());
		
		if (result) {
			return ResultCode.UsernameUnavailable;
		}
		
		MailingAddress mAddress = nurse.getMailingAddress();
		var generatedAddressKey = UserDatabaseClient.insertMailingAddress(con, mAddress);
		
		var generatedUserKey = UserDatabaseClient.insertUser(con, nurse, generatedAddressKey);
		nurse.setUserId(generatedUserKey);
		
		CredentialsDatabaseClient.insertCredentials(con, credentials, generatedUserKey);
		
		var generatedNurseKey = insertNurse(con, nurse);
		nurse.setNurseId(generatedNurseKey);
		
		con.commit();
		return ResultCode.Success;
	}
	
	private static int insertNurse(Connection con, User user) throws SQLException {
		String insertNurse = "INSERT INTO nurse ( userId, nurseId ) VALUES (?,?)";
		
        PreparedStatement pStatement =  con.prepareStatement(insertNurse, Statement.RETURN_GENERATED_KEYS);  
        	
		pStatement.setInt(1, user.getUserId());
		pStatement.setString(2, null);
			
		pStatement.executeUpdate();
			
		var generatedUserKeyResult = pStatement.getGeneratedKeys();
		generatedUserKeyResult.next();
		return Integer.parseInt(generatedUserKeyResult.getString(1));
	}

}
