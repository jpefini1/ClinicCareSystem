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
import java.util.HashMap;
import java.util.List;

import cliniccaresystem.model.Test;
import cliniccaresystem.model.TestSummary;
import cliniccaresystem.model.VisitInformation;

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
	
	public static void addFinalDiagnosisColumnToAppointment() {
		String addFinalDiagnosisColumnToAppointment = "ALTER TABLE appointment ADD finalDiagnosis Varchar(255)";
		
		
		sendCommandToServer(addFinalDiagnosisColumnToAppointment);
	}
	
	public static void addDoctorColumnToAppointment() {
		String addDoctorColumnToAppointment = "ALTER TABLE appointment ADD doctorId INT";
		
		sendCommandToServer(addDoctorColumnToAppointment);
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
	
	public static void createDoctorTable() {
		String createDoctorTableCommand = "CREATE TABLE IF NOT EXISTS doctor ( " +
				"userId INTEGER NOT NULL UNIQUE, " +
				"doctorId INTEGER NOT NULL AUTO_INCREMENT, " +
				"PRIMARY KEY ( doctorId ), " +
				"FOREIGN KEY ( userId ) references user ( id )) ";
		
		sendCommandToServer(createDoctorTableCommand);
	}
	
	public static void createAdminTable() {
		String createDoctorTableCommand = "CREATE TABLE IF NOT EXISTS admin ( " +
				"userId INTEGER NOT NULL UNIQUE, " +
				"adminId INTEGER NOT NULL AUTO_INCREMENT, " +
				"PRIMARY KEY ( adminId ), " +
				"FOREIGN KEY ( userId ) references user ( id )) ";
		
		sendCommandToServer(createDoctorTableCommand);
	}
	
	public static void createStandardTestsTable() {
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
	
	protected static boolean sendCommandToServer(String sqlCommand) {
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
	
	protected static boolean executePreparedStatement(PreparedStatement pStatement) {
		try ( Connection con = DriverManager.getConnection(CONNECTION_STRING))
		{ 
			boolean rs = pStatement.execute();
			return rs;
        }
        catch (Exception e) 
        {
            System.out.println(e.toString());
        }
		return false;
	}
	
	public static void createAddCredentialsProcedure() throws SQLException {
		String dropProcedureIfExists = "drop procedure if exists credentials_add;";
		sendCommandToServer(dropProcedureIfExists);
		
		var setupInsertCredentialsProcedureCommand = "CREATE PROCEDURE credentials_add( IN username VARCHAR(20), IN password VARCHAR(20), IN uid INT) " +
		"BEGIN " +
		"INSERT INTO credentials " +
		"VALUES(username,password, uid); " +
		"END";
		
        sendCommandToServer(setupInsertCredentialsProcedureCommand);
	}
	
	public static void createValidateCredentialsProcedure() throws SQLException {
		String dropProcedureIfExists = "drop procedure if exists credentials_validate;";
		sendCommandToServer(dropProcedureIfExists);
		
		var setupValidateCredentialsProcedureCommand = "CREATE PROCEDURE credentials_validate( IN uName VARCHAR(20), IN pWord VARCHAR(20)) " +
		"BEGIN " +
		"SELECT uid FROM credentials WHERE username = uName AND password = pWord; " +
		"END";
		
        sendCommandToServer(setupValidateCredentialsProcedureCommand);
	}
	
	public static void clearAllTables() {
		sendCommandToServer("DELETE FROM credentials");
		sendCommandToServer("DELETE FROM test_result");
		sendCommandToServer("DELETE FROM test_order");
		sendCommandToServer("DELETE FROM routine_check");
		sendCommandToServer("DELETE FROM appointment");
		sendCommandToServer("DELETE FROM nurse");
		sendCommandToServer("DELETE FROM patient");
		sendCommandToServer("DELETE FROM doctor");
		sendCommandToServer("DELETE FROM user");
		sendCommandToServer("DELETE FROM address");
	}

	public static HashMap<String, ArrayList<String>> sendSearchQuery(String query) throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING); 
		con.setAutoCommit(false);
		Statement stmt =  con.createStatement();  
		
		ResultSet rs = stmt.executeQuery(query);
		
		return makeHashMapFromResultSet(rs);
	}

	private static HashMap<String, ArrayList<String>> makeHashMapFromResultSet(ResultSet rs) throws SQLException {
		HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		
		for(int i = 0 ; i < rs.getMetaData().getColumnCount(); i++) {
			final int j = i;                
			map.put(rs.getMetaData().getColumnName(i + 1), new ArrayList<String>());
		}

		while (rs.next()) {
			for(int i = 1 ; i <= rs.getMetaData().getColumnCount(); i++){
				map.get(rs.getMetaData().getColumnName(i)).add(rs.getString(i));
            }
		}
		
		for (String columnName : map.keySet()) {
			System.out.print(columnName + ": ");
			for (String data : map.get(columnName)) {
				System.out.print(data);
			}
			System.out.println();
		}
		
		return map;
	}
	
	public static ArrayList<VisitInformation> getAppointmentInformationBetweenDates(LocalDate startDate, LocalDate endDate) throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING); 
		con.setAutoCommit(false);
		Statement stmt =  con.createStatement();  
		
		ResultSet rs = stmt.executeQuery("SELECT a.appointmentId, a.appointmentTime, a.finalDiagnosis, u.fname, u.lname, du.fname, du.lname, nu.fname, nu.lname, p.patientId " + 
										"FROM appointment AS a " +
										"LEFT JOIN patient AS p " + 
										"ON a.patientId = p.patientId " +
										"LEFT JOIN user AS u " +
										"ON p.userId = u.id " +
										"LEFT JOIN doctor AS d " +
										"ON a.doctorId = d.doctorId " +
										"LEFT JOIN user AS du " +
										"ON d.userId = du.id " +
										"LEFT JOIN routine_check AS r " +
										"ON r.appointmentId = a.appointmentId " +
										"LEFT JOIN nurse AS n " +
										"ON r.nurseId = n.nurseId " +
										"LEFT JOIN user AS nu " +
										"ON n.userId = nu.id " +
										"WHERE appointmentTime between " + "'" + startDate + "'" + " AND " + "'" + endDate + "'" + ";");
		
		var visits = new ArrayList<VisitInformation>();
		while (rs.next()) {
			var visit = new VisitInformation();
			visit.setAppointmentId(rs.getInt(1));
			visit.setVisitDate(rs.getString(2));
			visit.setFinalDiagnosis(rs.getString(3));
			visit.setPatientFirstName(rs.getString(4));
			visit.setPatientLastName(rs.getString(5));
			visit.setDoctorFirstName(rs.getString(6));
			visit.setDoctorLastName(rs.getString(7));
			visit.setNurseFirstName(rs.getString(8));
			visit.setNurseLastName(rs.getString(9));
			visit.setPatientId(rs.getString(10));
			visits.add(visit);
		}
		
		for (VisitInformation visit : visits) {
			List<Test> tests = TestOrderDatabaseClient.getTestOrdersIfExists(visit.getAppointmentId());
			
			for (Test test : tests) {
				TestResultDatabaseClient.updateTestIfResultsExists(test);
				visit.getTests().add(new TestSummary(test));
			}
		}
		return visits;
	}
}
