package cliniccaresystem.datalayer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import cliniccaresystem.model.Gender;
import cliniccaresystem.model.MailingAddress;
import cliniccaresystem.model.Patient;
import cliniccaresystem.model.ResultCode;
import cliniccaresystem.model.USState;

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
			patient.setUserId(rs.getInt(1));
			patients.add(patient);
		}
			
		con.commit();
		return patients;
	}
	
	public static ResultCode updatePatient(Patient patient) throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING); 
		con.setAutoCommit(false);
		
		PreparedStatement editUser = con.prepareStatement("UPDATE user SET fname = ?, lname = ?, dob = ?, mAddress = ?, pNumber = ? WHERE id = ?");  
		editUser.setString(1, patient.getFirstName());
		editUser.setString(2, patient.getLastName());
		editUser.setDate(3, Date.valueOf(patient.getDateOfBirth()));
		
		var addressId = UserDatabaseClient.insertMailingAddress(con, patient.getMailingAddress());
		editUser.setInt(4, addressId);
		editUser.setString(5, patient.getPhoneNumber());
		editUser.setInt(6, patient.getUserId());
		editUser.executeUpdate();
		
		con.commit();
		return ResultCode.Success;
	}

	public static List<Patient> searchForPatients(String fName, String lName, LocalDate dob) throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING);
		con.setAutoCommit(false);
		
		PreparedStatement filteredUsersStatement = getSearchStatement(con, fName, lName, dob);
		ResultSet filteredUsers = filteredUsersStatement.executeQuery();
		
		List<Patient> filteredPatients = new ArrayList<Patient>();
		
		while (filteredUsers.next()) {
			PreparedStatement selectedPatientId = con.prepareStatement("SELECT * FROM patient WHERE userId = ?");
			selectedPatientId.setInt(1, filteredUsers.getInt(1));
			
			ResultSet patientId = selectedPatientId.executeQuery();
			
			if (patientId.next()) {
				PreparedStatement selectedAddress = con.prepareStatement("SELECT * FROM address WHERE addressId = ?");
				selectedAddress.setInt(1, filteredUsers.getInt(5));
				
				ResultSet addressData = selectedAddress.executeQuery();
				addressData.next();
					
				MailingAddress address = new MailingAddress(addressData.getString(2), addressData.getString(3), USState.valueOf(addressData.getString(4)), addressData.getString(5));
				Patient patient = new Patient(patientId.getInt(2), filteredUsers.getString(2), filteredUsers.getString(3), filteredUsers.getDate(4).toLocalDate(), address, filteredUsers.getString(6), Gender.valueOf(patientId.getString(3)));
				patient.setUserId(filteredUsers.getInt(1));
				filteredPatients.add(patient);
			}
		}
		
		con.commit();
		return filteredPatients;
	}
	
	private static PreparedStatement getSearchStatement(Connection con, String fName, String lName, LocalDate dob) throws SQLException {
		
		PreparedStatement filteredUsersStatement;
		if (!fName.isBlank() && !lName.isBlank()) {
			if (dob != null) {
				filteredUsersStatement =  con.prepareStatement("SELECT * FROM user WHERE fname = ? AND lname = ? AND dob = ?");
				filteredUsersStatement.setString(1, fName);
				filteredUsersStatement.setString(2, lName);
				filteredUsersStatement.setDate(3, Date.valueOf(dob));
			} else {
				filteredUsersStatement =  con.prepareStatement("SELECT * FROM user WHERE fname = ? AND lname = ?");
				filteredUsersStatement.setString(1, fName);
				filteredUsersStatement.setString(2, lName);
			}
		} else {
			filteredUsersStatement =  con.prepareStatement("SELECT * FROM user WHERE dob = ?");
			filteredUsersStatement.setDate(1, Date.valueOf(dob));
		}
		return filteredUsersStatement;
	}
}
