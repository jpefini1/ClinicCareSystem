package cliniccaresystem.datalayer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cliniccaresystem.model.MailingAddress;
import cliniccaresystem.model.User;

public class UserDatabaseClient extends DatabaseClient {
	
	protected static int insertMailingAddress(Connection con, MailingAddress mAddress) throws SQLException {
		var existingAddressId = checkForExistingAddress(con, mAddress);
		if (existingAddressId != -1) {
			return existingAddressId;
		}
		
		String insertAddress = "INSERT INTO address ( addressId, street, city, state, zipcode ) VALUES (?,?,?,?,?)";
        PreparedStatement pStatement =  con.prepareStatement(insertAddress, Statement.RETURN_GENERATED_KEYS);
		pStatement.setString(1, null);
		pStatement.setString(2, mAddress.getStreet());
		pStatement.setString(3, mAddress.getCity());
		pStatement.setString(4, mAddress.getState().toString());
		pStatement.setString(5, mAddress.getZipcode());
			
		pStatement.executeUpdate();
			
		var generatedAddressKeyResult = pStatement.getGeneratedKeys();
		generatedAddressKeyResult.next();
		return Integer.parseInt(generatedAddressKeyResult.getString(1));
	}
	
	private static int checkForExistingAddress(Connection con, MailingAddress address) throws SQLException {
		String selectAddressId = "SELECT addressId FROM address WHERE street = ? AND city = ? AND state = ? AND zipcode = ?";	
        PreparedStatement select =  con.prepareStatement(selectAddressId, Statement.RETURN_GENERATED_KEYS);
        select.setString(1, address.getStreet());
        select.setString(2, address.getCity());
        select.setString(3, address.getState().toString());
        select.setString(4, address.getZipcode());
        ResultSet addressId = select.executeQuery();
        
        if (addressId.next()) {
        	return addressId.getInt(1);
        } else {
        	return -1;
        }
	}
	
	public static int insertUser(Connection con, User user, int addressId) throws SQLException {
		String insertUser = "INSERT INTO user ( id, fname, lname, dob, mAddress, pNumber ) VALUES (?,?,?,?,?,?)";
		
        PreparedStatement pStatement =  con.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS);
		pStatement.setString(1, null);
		pStatement.setString(2, user.getFirstName());
		pStatement.setString(3, user.getLastName());
		pStatement.setDate(4, Date.valueOf(user.getDateOfBirth()));
		pStatement.setInt(5, addressId);
		pStatement.setString(6, user.getPhoneNumber());
			
		pStatement.executeUpdate();
			
		var generatedUserKeyResult = pStatement.getGeneratedKeys();
		generatedUserKeyResult.next();
		return Integer.parseInt(generatedUserKeyResult.getString(1));
	}
}
