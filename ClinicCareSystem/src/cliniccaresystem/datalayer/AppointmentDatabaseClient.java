package cliniccaresystem.datalayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import cliniccaresystem.model.Appointment;
import cliniccaresystem.model.ResultCode;

public class AppointmentDatabaseClient extends DatabaseClient{

	public static ResultCode scheduleAppointment(Appointment appointment) throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING); 
		con.setAutoCommit(false);
		
		var generatedAppointmentKey = insertAppointment(con, appointment);
		appointment.setAppointmentId(generatedAppointmentKey);
		
		con.commit();
		return ResultCode.Success;
	}
	
	public static boolean checkForAppointmentAt(Appointment appointment) throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING); 
		
		String selectAppointmentsCommand = "SELECT * FROM appointment WHERE appointmentTime = ? AND patientId = ?";	
        PreparedStatement selectAppointments =  con.prepareStatement(selectAppointmentsCommand, Statement.RETURN_GENERATED_KEYS);
        selectAppointments.setTimestamp(1, Timestamp.valueOf(appointment.getAppointmentTime()));
        selectAppointments.setInt(2, appointment.getPatientId());
        
        ResultSet rs = selectAppointments.executeQuery();
        
        if (rs.next()) {
        	return true;
        }
		return false;
	}
	
	private static int insertAppointment(Connection con, Appointment appointment) throws SQLException {
		String insertNurse = "INSERT INTO appointment ( patientId, appointmentTime, reasonForVisit ) VALUES (?,?,?)";
		
        PreparedStatement pStatement =  con.prepareStatement(insertNurse, Statement.RETURN_GENERATED_KEYS);  

		pStatement.setInt(1, appointment.getPatientId());
		pStatement.setTimestamp(2, Timestamp.valueOf(appointment.getAppointmentTime()));
		pStatement.setString(3, appointment.getReasonForVisit());
			
		pStatement.executeUpdate();
			
		var generatedUserKeyResult = pStatement.getGeneratedKeys();
		generatedUserKeyResult.next();
		return Integer.parseInt(generatedUserKeyResult.getString(1));
	}
}
