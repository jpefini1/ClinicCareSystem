package cliniccaresystem.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PatientDatabaseClient extends DatabaseClient{
	
	public static ResultCode AddPatient(Patient patient) throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING); 
		con.setAutoCommit(false);
		
		MailingAddress mAddress = patient.getMailingAddress();
		var generatedAddressKey = UserDatabaseClient.insertMailingAddress(con, mAddress);
		
		var generatedUserKey = UserDatabaseClient.insertUser(con, patient, generatedAddressKey);
		patient.setUserId(generatedUserKey);
		
		var generatedPatientKey = insertPatient(con, patient);
		patient.setPatientId(generatedPatientKey);
		
		con.commit();
		return ResultCode.Success;
	}
	
	private static int insertPatient(Connection con, Patient patient) throws SQLException {
		String insertNurse = "INSERT INTO patient ( userId, patientId, gender ) VALUES (?,?,?)";
		
        PreparedStatement pStatement =  con.prepareStatement(insertNurse, Statement.RETURN_GENERATED_KEYS);  

		pStatement.setInt(1, patient.getUserId());
		pStatement.setString(2, null);
		pStatement.setString(3, patient.getGender().toString());
			
		pStatement.executeUpdate();
			
		var generatedUserKeyResult = pStatement.getGeneratedKeys();
		generatedUserKeyResult.next();
		return Integer.parseInt(generatedUserKeyResult.getString(1));
	}
	
	public static List<Patient> getAllPatients() throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING); 
		con.setAutoCommit(false);
		
        Statement stmt =  con.createStatement();  
		Statement stmt1 =  con.createStatement();
		Statement stmt2 =  con.createStatement();  

		ResultSet rs = stmt.executeQuery("SELECT * FROM patient");
			
		var patients = new ArrayList<Patient>();
		while (rs.next()) {
			ResultSet user = stmt1.executeQuery("SELECT * FROM user WHERE id=" + rs.getInt(1));
			user.next();
			ResultSet addressData = stmt2.executeQuery("SELECT * FROM address WHERE addressId=" + user.getString(5));
			addressData.next();
				
			MailingAddress address = new MailingAddress(addressData.getString(2), addressData.getString(3), USState.valueOf(addressData.getString(4)), addressData.getString(5));
			Patient patient = new Patient(rs.getInt(2), user.getString(2), user.getString(3), user.getDate(4).toLocalDate(), address, user.getString(6), Gender.valueOf(rs.getString(3)));
			patients.add(patient);
		}
			
		con.commit();
		return patients;
	}

}
