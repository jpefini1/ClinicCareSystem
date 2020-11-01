package cliniccaresystem.datalayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import cliniccaresystem.model.Appointment;
import cliniccaresystem.model.ResultCode;
import cliniccaresystem.model.RoutineCheckResults;

public class RoutineCheckDatabaseClient extends DatabaseClient{

	public static ResultCode addRoutineCheck(RoutineCheckResults routineCheck) throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING); 
		con.setAutoCommit(false);
		
		insertRoutineCheck(con, routineCheck);
		
		con.commit();
		return ResultCode.Success;
	}
	
	public static RoutineCheckResults getRoutineCheckIfExists(Appointment appointment) throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING); 
		
		String selectRoutineCheckCommand = "SELECT * FROM routine_check WHERE appointmentId = ?";	
        PreparedStatement selectRoutineCheck =  con.prepareStatement(selectRoutineCheckCommand);
        selectRoutineCheck.setInt(1, appointment.getAppointmentId());
        
        ResultSet rs = selectRoutineCheck.executeQuery();
        if (rs.next()) {
        	return new RoutineCheckResults(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getDouble(6), rs.getDouble(7));
        } else {
        	return null;
        }
	}
	
	private static void insertRoutineCheck(Connection con, RoutineCheckResults routineCheck) throws SQLException {
		String insertRoutineCheck = "INSERT INTO routine_check ( appointmentId, nurseId, systolicBP, diastolicBP, pulse, weight, bodyTemp ) VALUES (?,?,?,?,?,?,?)";
		
        PreparedStatement pStatement =  con.prepareStatement(insertRoutineCheck);  

		pStatement.setInt(1, routineCheck.getAppointmentId());
		pStatement.setInt(2, routineCheck.getNurseId());
		pStatement.setInt(3, routineCheck.getSystolicBP());
		pStatement.setInt(4, routineCheck.getDiastolicBP());
		pStatement.setInt(5, routineCheck.getPulse());
		pStatement.setDouble(6, routineCheck.getWeight());
		pStatement.setDouble(7, routineCheck.getBodyTemp());
			
		pStatement.executeUpdate();
	}

}
