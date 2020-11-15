package cliniccaresystem.datalayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cliniccaresystem.model.Appointment;
import cliniccaresystem.model.Test;

public class TestOrderDatabaseClient extends DatabaseClient{

	public static List<Test> getTestOrdersIfExists(Appointment appointment) throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING); 
		
		String getAppointmentsQuery = "SELECT * FROM test_order WHERE appointmentId = ?";
		PreparedStatement selectTestOrders =  con.prepareStatement(getAppointmentsQuery, Statement.RETURN_GENERATED_KEYS);		
		selectTestOrders.setInt(1, appointment.getAppointmentId());
		
		ResultSet queryRS = selectTestOrders.executeQuery();
		
		List<Test> tests = new ArrayList<Test>();
        while (queryRS.next()) {
        	var testName = StandardTestsDatabaseClient.getTestName(queryRS.getInt(3));
        	var test = new Test(queryRS.getInt(1), testName);
        	test = TestResultDatabaseClient.updateTestIfResultsExists(test);
        	tests.add(test);
        }
        
		return tests;
	}
	
}
