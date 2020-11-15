package cliniccaresystem.datalayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import cliniccaresystem.model.Test;

public class TestResultDatabaseClient extends DatabaseClient{

	public static Test updateTestIfResultsExists(Test test) throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING); 
		
		String getTestResultQuery = "SELECT * FROM test_result WHERE testOrderId = ?";
		PreparedStatement testResult =  con.prepareStatement(getTestResultQuery, Statement.RETURN_GENERATED_KEYS);		
		testResult.setInt(1, test.getTestId());
		
		ResultSet queryRS = testResult.executeQuery();
		
		if (queryRS.next()) {
			test.setTimePerformed(queryRS.getTimestamp(2).toLocalDateTime());
			test.setResult(queryRS.getString(3));
			test.setAbnormal(Boolean.toString(queryRS.getBoolean(4)));
		}

		return test;
	}
}
