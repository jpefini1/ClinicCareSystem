package cliniccaresystem.datalayer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseClient {
	
	protected static final String CONNECTION_STRING = "jdbc:mysql://160.10.25.16:3306/cs3230f20g?user=jpefini2&password=/:1mT9lCu$&serverTimezone=EST";
	
	public static void createTables() {
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
	}
	
	public static void createRoutineCheckTable() {
		String dropAddress = "DROP TABLE routine_check";
		sendCommandToServer(dropAddress);
		
		String createRoutineCheckTableCommand = "CREATE TABLE IF NOT EXISTS routine_check ( " +
				"appointmentId INTEGER NOT NULL, " +
				"nurseId INTEGER NOT NULL, " +
				"systolicBP INTEGER NOT NULL, " +
				"diastolicBP INTEGER NOT NULL, " +
				"pulse INTEGER NOT NULL, " +
				"weight DECIMAL(5,2) NOT NULL, " +
				"bodyTemp DECIMAL(4,1) NOT NULL, " +
				"PRIMARY KEY ( appointmentId ), " +
				"FOREIGN KEY ( nurseId ) references nurse ( nurseId )) ";
		
		
		sendCommandToServer(createRoutineCheckTableCommand);
	}
	
	public static void createStandardTestsTable() {
		//String dropTable = "DROP TABLE standard_tests";
		//sendCommandToServer(dropTable);
		
		String createStandardTestsTableCommand = "CREATE TABLE IF NOT EXISTS standard_tests ( " +
				"testCode INTEGER NOT NULL, " +
				"name VARCHAR(20) NOT NULL, " +
				"PRIMARY KEY ( testCode ))";
		
		sendCommandToServer(createStandardTestsTableCommand);
	}
	
	public static void createTestOrderTable() {
		String dropTable = "DROP TABLE test_order";
		sendCommandToServer(dropTable);
		
		String createTestOrderTableCommand = "CREATE TABLE IF NOT EXISTS test_order ( " +
				"id INTEGER NOT NULL AUTO_INCREMENT, " +
				"appointmentId INTEGER NOT NULL, " +
				"testCode INTEGER NOT NULL, " +
				"PRIMARY KEY ( id ), " +
				"FOREIGN KEY ( appointmentId ) references appointment ( appointmentId ), " +
				"FOREIGN KEY ( testCode ) references standard_tests ( testCode )) ";
		
		sendCommandToServer(createTestOrderTableCommand);
	}
	
	public static void createTestResultTable() {
		//String dropTable = "DROP TABLE test_result";
		//sendCommandToServer(dropTable);
		
		String createTestResultTableCommand = "CREATE TABLE IF NOT EXISTS test_result ( " +
				"testOrderId INTEGER NOT NULL, " +
				"performed DATETIME NOT NULL, " +
				"result VARCHAR(25) NOT NULL, " +
				"isAbnormal BOOLEAN NOT NULL, " +
				"PRIMARY KEY ( testOrderId ), " +
				"FOREIGN KEY ( testOrderId ) references test_order ( id )) ";
		
		sendCommandToServer(createTestResultTableCommand);
	}
	
	public static void populateStandardTestsTable() {
		String insertTestWBCCommand = "INSERT INTO standard_tests VALUES (1, 'WBC')";
		String insertTestLDLCommand = "INSERT INTO standard_tests VALUES (2, 'LDL')";
		String insertTestHepACommand = "INSERT INTO standard_tests VALUES (3, 'Hepatitis A')";
		String insertTestHepBCommand = "INSERT INTO standard_tests VALUES (4, 'Hepatitis B')";
		String insertTestHepCCommand = "INSERT INTO standard_tests VALUES (5, 'Hepatitis C')";
		
		sendCommandToServer(insertTestWBCCommand);
		sendCommandToServer(insertTestLDLCommand);
		sendCommandToServer(insertTestHepACommand);
		sendCommandToServer(insertTestHepBCommand);
		sendCommandToServer(insertTestHepCCommand);
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
}
