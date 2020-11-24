package cliniccaresystem.datalayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import cliniccaresystem.model.Appointment;
import cliniccaresystem.model.ResultCode;
import cliniccaresystem.model.Test;

public class TestOrderDatabaseClient extends DatabaseClient{

	public static List<Test> getTestOrdersIfExists(int appointmentId) throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING); 
		
		String getAppointmentsQuery = "SELECT * FROM test_order WHERE appointmentId = ?";
		PreparedStatement selectTestOrders =  con.prepareStatement(getAppointmentsQuery, Statement.RETURN_GENERATED_KEYS);		
		selectTestOrders.setInt(1, appointmentId);
		
		ResultSet queryRS = selectTestOrders.executeQuery();
		
		List<Test> tests = new ArrayList<Test>();
        while (queryRS.next()) {
        	var testName = StandardTestsDatabaseClient.getTestName(queryRS.getInt(3));
        	var test = new Test(queryRS.getInt(1), appointmentId, testName);
        	test = TestResultDatabaseClient.updateTestIfResultsExists(test);
        	tests.add(test);
        }
        
		return tests;
	}

	public static ResultCode orderTest(Test test) throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING); 
		con.setAutoCommit(false);
		
		var generatedTestOrderKey = insertTestOrder(con, test);
		test.setTestId(generatedTestOrderKey);
		
		con.commit();
		return ResultCode.Success;
	}
	
	private static int insertTestOrder(Connection con, Test test) throws SQLException {
		String insertNurse = "INSERT INTO test_order ( appointmentId, testCode ) VALUES (?,?)";
		
        PreparedStatement pStatement =  con.prepareStatement(insertNurse, Statement.RETURN_GENERATED_KEYS);  

		pStatement.setInt(1, test.getAppointmentId());
		pStatement.setString(2, StandardTestsDatabaseClient.getTestCode(test.getName()));
			
		pStatement.executeUpdate();
			
		var generatedUserKeyResult = pStatement.getGeneratedKeys();
		generatedUserKeyResult.next();
		return Integer.parseInt(generatedUserKeyResult.getString(1));
	}
	
}
