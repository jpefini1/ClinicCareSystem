package cliniccaresystem.model;

import java.util.ArrayList;
import java.util.List;

public class ActiveUser {

	private static User activeUser = null;
	private static List<Patient> patients = new ArrayList<Patient>();

	public static User getActiveUser() {
		return activeUser;
	}

	public static void setActiveUser(User activeUser) {
		ActiveUser.activeUser = activeUser;
	}
	
	public static List<Patient> getPatients() {
		return patients;
	}

	public static void setPatients(List<Patient> patients) {
		ActiveUser.patients = patients;
	}
	
	public static boolean addPatient(Patient patient) {
		return patients.add(patient);
	}
}
