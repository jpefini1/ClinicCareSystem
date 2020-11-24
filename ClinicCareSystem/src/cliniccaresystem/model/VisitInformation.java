package cliniccaresystem.model;

import java.util.ArrayList;

public class VisitInformation {

	private String visitDate;
	private String patientId;
	private String patientFirstName;
	private String patientLastName;
	private String doctorFirstName;
	private String doctorLastName;
	private String nurseFirstName;
	private String nurseLastName;
	private String finalDiagnosis;
	private int appointmentId;
	
	private ArrayList<TestSummary> tests = new ArrayList<TestSummary>();
	
	public ArrayList<TestSummary> getTests(){
		return this.tests;
	}
	public String getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getPatientFirstName() {
		return patientFirstName;
	}
	public void setPatientFirstName(String patientFirstName) {
		this.patientFirstName = patientFirstName;
	}
	public String getPatientLastName() {
		return patientLastName;
	}
	public void setPatientLastName(String patientLastName) {
		this.patientLastName = patientLastName;
	}
	public String getDoctorFirstName() {
		return doctorFirstName;
	}
	public void setDoctorFirstName(String doctorFirstName) {
		this.doctorFirstName = doctorFirstName;
	}
	public String getDoctorLastName() {
		return doctorLastName;
	}
	public void setDoctorLastName(String doctorLastName) {
		this.doctorLastName = doctorLastName;
	}
	public String getNurseFirstName() {
		return nurseFirstName;
	}
	public void setNurseFirstName(String nurseFirstName) {
		this.nurseFirstName = nurseFirstName;
	}
	public String getNurseLastName() {
		return nurseLastName;
	}
	public void setNurseLastName(String nurseLastName) {
		this.nurseLastName = nurseLastName;
	}
	public String getFinalDiagnosis() {
		return finalDiagnosis;
	}
	public void setFinalDiagnosis(String finalDiagnosis) {
		this.finalDiagnosis = finalDiagnosis;
	}
	public int getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}
	
	
}
