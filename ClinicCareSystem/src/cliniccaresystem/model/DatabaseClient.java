package cliniccaresystem.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseClient {

	private static final String CONNECTION_STRING = "jdbc:mysql://160.10.25.16:3306/cs3230f20g?user=jpefini2&password=/:1mT9lCu$&serverTimezone=EST";
	
	public static void createTables() {
		
		//var result = sendQueryToServer("SELECT * FROM address");
		
		String createAddressTableCommand = "CREATE TABLE IF NOT EXISTS address (" +
					   						"addressId INT NOT NULL AUTO_INCREMENT, " +
					   						"street varchar(100) NOT NULL, " +
					   						"city varchar(100) NOT NULL, " +
					   						"state varchar(2) NOT NULL, " +
					   						"zipcode INT(5) NOT NULL, " +
					   						"UNIQUE ( street, city, state, zipcode ), " +
					   						"PRIMARY KEY ( addressId )) ";
		
		String createUserTableCommand = "CREATE TABLE IF NOT EXISTS user " +
										"(id INT NOT NULL AUTO_INCREMENT, " +
										"fname varchar(30) NOT NULL, " +
										"lname varchar(30) NOT NULL, " +
										"dob DATE NOT NULL, " +
										"mAddress INT NOT NULL, " +
										"pNumber varchar(10) NOT NULL, " +
										"PRIMARY KEY ( id ), " +
										"FOREIGN KEY ( mAddress ) references address ( addressId )) ";
		
		String createCredentialsTableCommand = "CREATE TABLE IF NOT EXISTS credentials " +
												"(username varchar(20) NOT NULL UNIQUE, " +
												"password varchar(20) NOT NULL, " +
												"uid INTEGER NOT NULL, " +
												"PRIMARY KEY ( username, password ), " +
												"FOREIGN KEY ( uid ) references user ( id )) ";
		
		String createPatientTableCommand = "CREATE TABLE IF NOT EXISTS patient ( " +
											"userId INTEGER NOT NULL UNIQUE, " +
											"patientId INTEGER NOT NULL AUTO_INCREMENT, " +
											"gender varchar(6) NOT NULL, " +
											"PRIMARY KEY ( patientId ), " +
											"FOREIGN KEY ( userId ) references user ( id )) ";
		
		String createNurseTableCommand = "CREATE TABLE IF NOT EXISTS nurse ( " +
											"userId INTEGER NOT NULL UNIQUE, " +
											"nurseId INTEGER NOT NULL AUTO_INCREMENT, " +
											"PRIMARY KEY ( nurseId ), " +
											"FOREIGN KEY ( userId ) references user ( id )) ";
		
		sendCommandToServer(createAddressTableCommand);
		sendCommandToServer(createUserTableCommand);
		sendCommandToServer(createCredentialsTableCommand);
		sendCommandToServer(createPatientTableCommand);
		sendCommandToServer(createNurseTableCommand);
		
		//String insert = "INSERT INTO address ( addressId, street, city, state, zipcode ) VALUES (?,?,?,?,?)";
	}
	
	public static ResultCode AddNurse(Nurse nurse, Credentials credentials) {
		var result = isUsernameTaken(credentials.getUsername());
		
		if (result) {
			return ResultCode.UsernameUnavailable;
		}
		
		MailingAddress mAddress = nurse.getMailingAddress();
		var generatedAddressKey = insertMailingAddress(mAddress);
		
		var generatedUserKey = insertUser(nurse, generatedAddressKey);
		nurse.setUserId(generatedUserKey);
		
		insertCredentials(credentials, generatedUserKey);
		
		var generatedNurseKey = insertNurse(nurse);
		nurse.setNurseId(generatedNurseKey);
		
		return ResultCode.Success;
	}
	
	public static ResultCode AddPatient(Patient patient) {
		MailingAddress mAddress = patient.getMailingAddress();
		var generatedAddressKey = insertMailingAddress(mAddress);
		
		var generatedUserKey = insertUser(patient, generatedAddressKey);
		patient.setUserId(generatedUserKey);
		
		var generatedPatientKey = insertPatient(patient);
		patient.setPatientId(generatedPatientKey);
		
		return ResultCode.Success;
	}
	
	private static boolean isUsernameTaken(String username) {
		try ( Connection con = DriverManager.getConnection(CONNECTION_STRING); 
        		Statement stmt =  con.createStatement();  
        	)
		{ 
			ResultSet rs = stmt.executeQuery("SELECT username FROM credentials");
			
			while (rs.next()) {
				if (rs.getString(1).equalsIgnoreCase(username)) {
					return true;
				}
			}
        }
        catch (Exception e) 
        {
            System.out.println(e.toString());
        }
		return false;
	}

	private static int insertMailingAddress(MailingAddress mAddress) {
		String insertAddress = "INSERT INTO address ( addressId, street, city, state, zipcode ) VALUES (?,?,?,?,?)";
		
		try ( Connection con = DriverManager.getConnection(CONNECTION_STRING); 
        		PreparedStatement pStatement =  con.prepareStatement(insertAddress, Statement.RETURN_GENERATED_KEYS);  
        	)
		{ 
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
        catch (Exception e) 
        {
            System.out.println(e.toString());
        }
		
		return -1;
	}
	
	private static int insertUser(User user, int addressId) {
		String insertUser = "INSERT INTO user ( id, fname, lname, dob, mAddress, pNumber ) VALUES (?,?,?,?,?,?)";
		
		try ( Connection con = DriverManager.getConnection(CONNECTION_STRING); 
        		PreparedStatement pStatement =  con.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS);  
        	)
		{ 
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
        catch (Exception e) 
        {
            System.out.println(e.toString());
        }
		
		return -1;
	}
	
	private static void insertCredentials(Credentials credentials, int userId) {
		String insertCredentials = "INSERT INTO credentials ( username, password, uid ) VALUES (?,?,?)";
		
		try ( Connection con = DriverManager.getConnection(CONNECTION_STRING); 
        		PreparedStatement pStatement =  con.prepareStatement(insertCredentials, Statement.SUCCESS_NO_INFO);  
        	)
		{ 
			pStatement.setString(1, credentials.getUsername());
			pStatement.setString(2, credentials.getPassword());
			pStatement.setInt(3, userId);
			
			pStatement.executeUpdate();
        }
        catch (Exception e) 
        {
            System.out.println(e.toString());
        }
	}
	
	private static int insertNurse(User user) {
		String insertNurse = "INSERT INTO nurse ( userId, nurseId ) VALUES (?,?)";
		
		try ( Connection con = DriverManager.getConnection(CONNECTION_STRING); 
        		PreparedStatement pStatement =  con.prepareStatement(insertNurse, Statement.RETURN_GENERATED_KEYS);  
        	)
		{ 
			pStatement.setInt(1, user.getUserId());
			pStatement.setString(2, null);
			
			pStatement.executeUpdate();
			
			var generatedUserKeyResult = pStatement.getGeneratedKeys();
			generatedUserKeyResult.next();
			return Integer.parseInt(generatedUserKeyResult.getString(1));
        }
        catch (Exception e) 
        {
            System.out.println(e.toString());
        }
		
		return -1;
	}
	
	private static int insertPatient(Patient patient) {
		String insertNurse = "INSERT INTO patient ( userId, patientId, gender ) VALUES (?,?,?)";
		
		try ( Connection con = DriverManager.getConnection(CONNECTION_STRING); 
        		PreparedStatement pStatement =  con.prepareStatement(insertNurse, Statement.RETURN_GENERATED_KEYS);  
        	)
		{ 
			pStatement.setInt(1, patient.getUserId());
			pStatement.setString(2, null);
			pStatement.setString(3, patient.getGender().toString());
			
			pStatement.executeUpdate();
			
			var generatedUserKeyResult = pStatement.getGeneratedKeys();
			generatedUserKeyResult.next();
			return Integer.parseInt(generatedUserKeyResult.getString(1));
        }
        catch (Exception e) 
        {
            System.out.println(e.toString());
        }
		
		return -1;
	}
	
	public static List<Patient> getAllPatients() {
		var patients = new ArrayList<Patient>();
		
		try ( Connection con = DriverManager.getConnection(CONNECTION_STRING); 
        		Statement stmt =  con.createStatement();  
				Statement stmt1 =  con.createStatement();
				Statement stmt2 =  con.createStatement();  
        	)
		{ 
			ResultSet rs = stmt.executeQuery("SELECT * FROM patient");
			
			while (rs.next()) {
				ResultSet user = stmt1.executeQuery("SELECT * FROM user WHERE id=" + rs.getInt(1));
				user.next();
				ResultSet addressData = stmt2.executeQuery("SELECT * FROM address WHERE addressId=" + user.getString(5));
				addressData.next();
				
				MailingAddress address = new MailingAddress(addressData.getString(2), addressData.getString(3), USState.valueOf(addressData.getString(4)), addressData.getString(5));
				Patient patient = new Patient(rs.getInt(2), user.getString(2), user.getString(3), user.getDate(4).toLocalDate(), address, user.getString(6), Gender.valueOf(rs.getString(3)));
				patients.add(patient);
			}
			
			return patients;
        }
        catch (Exception e) 
        {
            System.out.println(e.toString());
        }
		
		return null;
	}
	
	private static boolean sendCommandToServer(String sqlCommand) {
		try ( Connection con = DriverManager.getConnection(CONNECTION_STRING); 
        		Statement stmt =  con.createStatement();  
        	)
		{ 
			boolean rs = stmt.execute(sqlCommand);
			return rs;
        }
        catch (Exception e) 
        {
            System.out.println(e.toString());
        }
		
		return false;
	}
	
	public static User ValidateCredentials(String username, String password) {
		try ( Connection con = DriverManager.getConnection(CONNECTION_STRING); 
        		Statement stmt =  con.createStatement();
				Statement stmt1 = con.createStatement();
				Statement stmt2 = con.createStatement();
        	)
		{ 
			ResultSet rs = stmt.executeQuery("SELECT * FROM credentials");
			
			while (rs.next()) {
				if (rs.getString(1).equalsIgnoreCase(username) && rs.getString(2).equals(password)) {
					
					ResultSet user = stmt1.executeQuery("SELECT * FROM user WHERE id=" + rs.getString(3));
					user.next();
					ResultSet addressData = stmt2.executeQuery("SELECT * FROM address WHERE addressId=" + user.getString(5));
					addressData.next();
					
					MailingAddress address = new MailingAddress(addressData.getString(2), addressData.getString(3), USState.valueOf(addressData.getString(4)), addressData.getString(5));
					User activeUser = new User(user.getInt(1), username, user.getString(2), user.getString(3), user.getDate(4).toLocalDate(), address, user.getString(6));
					return activeUser;
				}
			}
        }
        catch (Exception e) 
        {
            System.out.println(e.toString());
        }
		return null;
	}
	
	public static void dropTables() {
		String dropAddress = "DROP TABLE address";
		String dropNurse = "DROP TABLE nurse";
		String dropCredentials = "DROP TABLE credentials";
		String dropUser = "DROP TABLE user";
		String dropPatient = "DROP TABLE patient";
		
		sendCommandToServer(dropNurse);
		sendCommandToServer(dropPatient);
		sendCommandToServer(dropCredentials);
		sendCommandToServer(dropUser);
		sendCommandToServer(dropAddress);
	}
}
