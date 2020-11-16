package cliniccaresystem.datalayer;

import java.sql.Connection;
import java.sql.Date;
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
import cliniccaresystem.model.Doctor;
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
	
	public static ResultCode editAppointment(Appointment appointment) throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING); 
		con.setAutoCommit(false);
		
		PreparedStatement editAppointment = con.prepareStatement("UPDATE appointment SET appointmentTime = ?, reasonForVisit = ?, doctorId = ? "
																+ "WHERE appointmentId = ?");  
		editAppointment.setTimestamp(1, Timestamp.valueOf(appointment.getAppointmentDateTime()));
		editAppointment.setString(2, appointment.getReasonForVisit());
		editAppointment.setInt(3, appointment.getDoctor().getDoctorId());
		editAppointment.setInt(4, appointment.getAppointmentId());
		editAppointment.executeUpdate();
		
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
        	
        	Doctor doctor = DoctorDatabaseClient.getDoctor(queryRS.getInt(6));
        	appointments.add(new Appointment(queryRS.getInt(1), queryRS.getInt(2), queryRS.getTimestamp(3).toLocalDateTime(), queryRS.getString(4), doctor));
        }

		return appointments;
	}
	
	public static boolean checkForDifferentAppointmentForSamePatientAtDateTimeOf(Appointment appointment) throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING); 
		
		String selectAppointmentsCommand = "SELECT * FROM appointment WHERE appointmentTime = ? AND patientId = ?";	
        PreparedStatement selectAppointments =  con.prepareStatement(selectAppointmentsCommand, Statement.RETURN_GENERATED_KEYS);
        selectAppointments.setTimestamp(1, Timestamp.valueOf(appointment.getAppointmentDateTime()));
        selectAppointments.setInt(2, appointment.getPatientId());
        
        ResultSet rs = selectAppointments.executeQuery();
        
        if (rs.next()) {
        	
        	if (appointment.getAppointmentId() == rs.getInt(1)) {
        		return false;
        	}
        	
        	return true;
        }
		return false;
	}
	
	private static int insertAppointment(Connection con, Appointment appointment) throws SQLException {
		String insertNurse = "INSERT INTO appointment ( patientId, appointmentTime, reasonForVisit, doctorId ) VALUES (?,?,?,?)";
		
        PreparedStatement pStatement =  con.prepareStatement(insertNurse, Statement.RETURN_GENERATED_KEYS);  

		pStatement.setInt(1, appointment.getPatientId());
		pStatement.setTimestamp(2, Timestamp.valueOf(appointment.getAppointmentDateTime()));
		pStatement.setString(3, appointment.getReasonForVisit());
		pStatement.setInt(4, appointment.getDoctor().getDoctorId());
			
		pStatement.executeUpdate();
			
		var generatedUserKeyResult = pStatement.getGeneratedKeys();
		generatedUserKeyResult.next();
		return Integer.parseInt(generatedUserKeyResult.getString(1));
	}

	public static String getFinalDiagnosis(Appointment selectedAppointment) throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING); 
		
		String getFinalDiagnosisQuery = "SELECT finalDiagnosis FROM appointment WHERE appointmentId = ?";
		PreparedStatement selectFinalDiagnosis =  con.prepareStatement(getFinalDiagnosisQuery, Statement.RETURN_GENERATED_KEYS);		
		selectFinalDiagnosis.setInt(1, selectedAppointment.getAppointmentId());
		
		ResultSet queryRS = selectFinalDiagnosis.executeQuery();
		
		if (queryRS.next()) {
			return queryRS.getString(1);
		} 
		return null;
	}

	public static ResultCode makeFinalDiagnosis(Appointment selectedAppointment, String finalDiagnosis) throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING); 
		con.setAutoCommit(false);
		
		PreparedStatement editFinalDiagnosis = con.prepareStatement("UPDATE appointment SET finalDiagnosis = ? WHERE appointmentId = ?");  
		editFinalDiagnosis.setString(1, finalDiagnosis);
		editFinalDiagnosis.setInt(2, selectedAppointment.getAppointmentId());
		editFinalDiagnosis.executeUpdate();
		
		con.commit();
		return ResultCode.Success;
	}

	public static boolean checkForDoubleBookForDoctor(Appointment appointment) throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_STRING); 
		
		String selectAppointmentsCommand = "SELECT appointmentId FROM appointment WHERE appointmentTime = ? AND doctorId = ?";	
        PreparedStatement selectAppointments =  con.prepareStatement(selectAppointmentsCommand, Statement.RETURN_GENERATED_KEYS);
        selectAppointments.setTimestamp(1, Timestamp.valueOf(appointment.getAppointmentDateTime()));
        selectAppointments.setInt(2, appointment.getDoctor().getDoctorId());
        
        ResultSet rs = selectAppointments.executeQuery();
        
        if (rs.next()) {
        	if (appointment.getAppointmentId() == rs.getInt(1)) {
        		return false;
        	}
        	
        	return true;
        }
		return false;
	}
}
