package cliniccaresystem.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseClient {

	private static final String CONNECTION_STRING = "jdbc:mysql://160.10.25.16:3306/cs3230f20g?user=jpefini2&password=/:1mT9lCu$&serverTimezone=EST";
	
	public static void createTables() {
		String createAddressTableCommand = "CREATE TABLE IF NOT EXISTS address (" +
					   						"addressId INT NOT NULL AUTO_INCREMENT, " +
					   						"street varchar(100) NOT NULL, " +
					   						"city varchar(100) NOT NULL, " +
					   						"state varchar(2) NOT NULL, " +
					   						"zipcode INT(5) NOT NULL, " +
					   						"PRIMARY KEY ( addressId )) ";
		
		String createUserTableCommand = "CREATE TABLE IF NOT EXISTS user " +
										"(id INT NOT NULL AUTO_INCREMENT, " +
										"fname varchar(30) NOT NULL, " +
										"lname varchar(30) NOT NULL, " +
										"dob DATE NOT NULL, " +
										"mAddress INT NOT NULL, " +
										"pNumber INT(10) NOT NULL, " +
										"PRIMARY KEY ( id ), " +
										"FOREIGN KEY ( mAddress ) references address ( addressId )) ";
		
		String createCredentialsTableCommand = "CREATE TABLE IF NOT EXISTS credentials " +
												"(username varchar(20) NOT NULL, " +
												"password varchar(20) NOT NULL, " +
												"uid INTEGER NOT NULL, " +
												"PRIMARY KEY ( username, password ), " +
												"FOREIGN KEY ( uid ) references user ( id )) ";
		
		String createPatientTableCommand = "CREATE TABLE IF NOT EXISTS patient ( " +
											"userId INTEGER NOT NULL UNIQUE, " +
											"patientId INTEGER NOT NULL AUTO_INCREMENT, " +
											"PRIMARY KEY ( patientId ), " +
											"FOREIGN KEY ( userId ) references user ( id )) ";
		
		sendCommandToServer(createAddressTableCommand);
		sendCommandToServer(createUserTableCommand);
		sendCommandToServer(createCredentialsTableCommand);
		sendCommandToServer(createPatientTableCommand);
		
		//String insert = "INSERT INTO address ( addressId, street, city, state, zipcode ) VALUES (?,?,?,?,?)";
		
		var result = sendQueryToServer("SELECT * FROM address");
		
	}
	
	private static ResultSet sendQueryToServer(String sqlQuery) {
		try ( Connection con = DriverManager.getConnection(CONNECTION_STRING); 
        		Statement stmt =  con.createStatement();  
				
        	)
		{ 
			ResultSet rs = stmt.executeQuery(sqlQuery);
			return rs;
        }

		catch (SQLException ex) 
		{
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
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

		catch (SQLException ex) 
		{
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
        catch (Exception e) 
        {
            System.out.println(e.toString());
        }
		
		return false;
	}
}
