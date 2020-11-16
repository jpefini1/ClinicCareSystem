package cliniccaresystem.datalayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cliniccaresystem.model.Doctor;
import cliniccaresystem.model.Gender;
import cliniccaresystem.model.MailingAddress;
import cliniccaresystem.model.Patient;
import cliniccaresystem.model.ResultCode;
import cliniccaresystem.model.USState;
import cliniccaresystem.model.User;

public class DoctorDatabaseClient extends DatabaseClient{

	public static ResultCode AddDoctor(Doctor doctor) throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING); 
		con.setAutoCommit(false);
		
		MailingAddress mAddress = doctor.getMailingAddress();
		var generatedAddressKey = UserDatabaseClient.insertMailingAddress(con, mAddress);
		
		var generatedUserKey = UserDatabaseClient.insertUser(con, doctor, generatedAddressKey);
		doctor.setUserId(generatedUserKey);
		
		var generatedNurseKey = insertDoctor(con, doctor);
		doctor.setDoctorId(generatedNurseKey);
		
		con.commit();
		return ResultCode.Success;
	}
	
	public static List<Doctor> getAllDoctors() throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING); 
		con.setAutoCommit(false);
		
        Statement stmt =  con.createStatement();  
		Statement stmt1 =  con.createStatement();
		Statement stmt2 =  con.createStatement();  

		ResultSet rs = stmt.executeQuery("SELECT * FROM doctor");
			
		var doctors = new ArrayList<Doctor>();
		while (rs.next()) {
			ResultSet user = stmt1.executeQuery("SELECT * FROM user WHERE id=" + rs.getInt(1));
			user.next();
			ResultSet addressData = stmt2.executeQuery("SELECT * FROM address WHERE addressId=" + user.getString(5));
			addressData.next();
				
			MailingAddress address = new MailingAddress(addressData.getString(2), addressData.getString(3), USState.valueOf(addressData.getString(4)), addressData.getString(5));
			Doctor doctor = new Doctor(rs.getInt(2), user.getString(2), user.getString(3), user.getDate(4).toLocalDate(), address, user.getString(6));
			doctor.setUserId(rs.getInt(1));
			doctors.add(doctor);
		}
			
		con.commit();
		return doctors;
	}
	
	public static Doctor getDoctor(int doctorId) throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING); 
		String getDoctor = "SELECT * FROM doctor WHERE doctorId = ?";
        PreparedStatement pStatement =  con.prepareStatement(getDoctor);  
        	
		pStatement.setInt(1, doctorId);
		ResultSet rs = pStatement.executeQuery();
		
		Statement stmt =  con.createStatement();
		Statement stmt1 =  con.createStatement();  
			
		if (rs.next()) {
			ResultSet user = stmt.executeQuery("SELECT * FROM user WHERE id=" + rs.getInt(1));
			user.next();
			ResultSet addressData = stmt1.executeQuery("SELECT * FROM address WHERE addressId=" + user.getString(5));
			addressData.next();
				
			MailingAddress address = new MailingAddress(addressData.getString(2), addressData.getString(3), USState.valueOf(addressData.getString(4)), addressData.getString(5));
			Doctor doctor = new Doctor(rs.getInt(2), user.getString(2), user.getString(3), user.getDate(4).toLocalDate(), address, user.getString(6));
			doctor.setUserId(rs.getInt(1));
			
			return doctor;
		}
		return null;
	}
	
	private static int insertDoctor(Connection con, User user) throws SQLException {
		String insertDoctor = "INSERT INTO doctor ( userId, doctorId ) VALUES (?,?)";
		
        PreparedStatement pStatement =  con.prepareStatement(insertDoctor, Statement.RETURN_GENERATED_KEYS);  
        	
		pStatement.setInt(1, user.getUserId());
		pStatement.setString(2, null);
			
		pStatement.executeUpdate();
			
		var generatedUserKeyResult = pStatement.getGeneratedKeys();
		generatedUserKeyResult.next();
		return Integer.parseInt(generatedUserKeyResult.getString(1));
	}
}
