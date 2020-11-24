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
