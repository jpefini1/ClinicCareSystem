package cliniccaresystem.datalayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import cliniccaresystem.model.ResultCode;
import cliniccaresystem.model.RoutineCheckResults;
import cliniccaresystem.model.Test;
import cliniccaresystem.model.TestResult;

public class TestResultDatabaseClient extends DatabaseClient{

	public static Test updateTestIfResultsExists(Test test) throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING); 
		
		String getTestResultQuery = "SELECT * FROM test_result WHERE testOrderId = ?";
		PreparedStatement testResultStatement =  con.prepareStatement(getTestResultQuery, Statement.RETURN_GENERATED_KEYS);		
		testResultStatement.setInt(1, test.getTestId());
		
		ResultSet queryRS = testResultStatement.executeQuery();
		
		if (queryRS.next()) {
			var testResult = new TestResult(queryRS.getTimestamp(2).toLocalDateTime(), queryRS.getString(3), Boolean.toString(queryRS.getBoolean(4)));
			test.setResult(testResult);
		}

		return test;
	}
	
	public static ResultCode addResultsToTable(Test test) throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING); 
		con.setAutoCommit(false);
		
		insertResults(con, test);
		
		con.commit();
		return ResultCode.Success;
	}
	
	private static void insertResults(Connection con, Test test) throws SQLException {
		String insertRoutineCheck = "INSERT INTO test_result ( testOrderId, performed, result, isAbnormal ) VALUES (?,?,?,?)";
		
        PreparedStatement pStatement =  con.prepareStatement(insertRoutineCheck);  

		pStatement.setInt(1, test.getTestId());
		
		pStatement.setTimestamp(2, Timestamp.valueOf(test.getTestResults().getTimePerformed()));
		pStatement.setString(3, test.getTestResults().getResult());
		pStatement.setBoolean(4, Boolean.parseBoolean(test.getTestResults().getIsAbnormal()));
			
		pStatement.executeUpdate();
	}
}
