package cliniccaresystem.datalayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cliniccaresystem.model.Test;

public class StandardTestsDatabaseClient extends DatabaseClient {

	public static String getTestName(int testCode) throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING); 
		
		String getTestNameQuery = "SELECT name FROM standard_tests WHERE testCode = ?";
		PreparedStatement selectName =  con.prepareStatement(getTestNameQuery, Statement.RETURN_GENERATED_KEYS);		
		selectName.setInt(1, testCode);
		
		ResultSet queryRS = selectName.executeQuery();
		
		if (queryRS.next()) {
			return queryRS.getString(1);
		}
		
		return null;
	}
	
	public static String getTestCode(String testName) throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING); 
		
		String getTestNameQuery = "SELECT testCode FROM standard_tests WHERE name = ?";
		PreparedStatement selectName =  con.prepareStatement(getTestNameQuery, Statement.RETURN_GENERATED_KEYS);		
		selectName.setString(1, testName);
		
		ResultSet queryRS = selectName.executeQuery();
		
		if (queryRS.next()) {
			return queryRS.getString(1);
		}
		
		return null;
	}
}
