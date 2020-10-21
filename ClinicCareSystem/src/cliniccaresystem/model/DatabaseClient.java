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
