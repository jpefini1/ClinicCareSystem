package cliniccaresystem.datalayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import cliniccaresystem.model.Appointment;
import cliniccaresystem.model.Gender;
import cliniccaresystem.model.MailingAddress;
import cliniccaresystem.model.Patient;
import cliniccaresystem.model.ResultCode;
import cliniccaresystem.model.USState;

public class AppointmentDatabaseClient extends DatabaseClient{

	public static ResultCode scheduleAppointment(Appointment appointment) throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING); 
		con.setAutoCommit(false);
		
		var generatedAppointmentKey = insertAppointment(con, appointment);
		appointment.setAppointmentId(generatedAppointmentKey);
		
		con.commit();
		return ResultCode.Success;
	}
	
	public static List<Appointment> getAppointmentsOfPatient(Patient patient) throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING); 
		
		String getAppointmentsQuery = "SELECT * FROM appointment WHERE patientId = ?";
		PreparedStatement selectAppointments =  con.prepareStatement(getAppointmentsQuery, Statement.RETURN_GENERATED_KEYS);		
		selectAppointments.setInt(1, patient.getPatientId());
		
		ResultSet queryRS = selectAppointments.executeQuery();
		
		List<Appointment> appointments = new ArrayList<Appointment>();
        while (queryRS.next()) {
        	appointments.add(new Appointment(queryRS.getInt(1), queryRS.getInt(2), queryRS.getTimestamp(3).toLocalDateTime(), queryRS.getString(4)));
        }

		return appointments;
	}
	
	public static boolean checkForAppointmentAt(Appointment appointment) throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING); 
		
		String selectAppointmentsCommand = "SELECT * FROM appointment WHERE appointmentTime = ? AND patientId = ?";	
        PreparedStatement selectAppointments =  con.prepareStatement(selectAppointmentsCommand, Statement.RETURN_GENERATED_KEYS);
        selectAppointments.setTimestamp(1, Timestamp.valueOf(appointment.getAppointmentDateTime()));
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
		pStatement.setTimestamp(2, Timestamp.valueOf(appointment.getAppointmentDateTime()));
		pStatement.setString(3, appointment.getReasonForVisit());
			
		pStatement.executeUpdate();
			
		var generatedUserKeyResult = pStatement.getGeneratedKeys();
		generatedUserKeyResult.next();
		return Integer.parseInt(generatedUserKeyResult.getString(1));
	}
}
