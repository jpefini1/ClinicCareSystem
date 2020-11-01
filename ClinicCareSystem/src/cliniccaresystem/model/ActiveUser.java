package cliniccaresystem.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
		sortPatients();
		return patients;
	}

	public static void setPatients(List<Patient> patients) {
		ActiveUser.patients = patients;
	}
	
	public static boolean addPatient(Patient patient) {
		return patients.add(patient);
	}
	
	private static void sortPatients() {
		
		Collections.sort(ActiveUser.patients, new Comparator<Patient>() {
			
			public int compare(Patient patient1, Patient patient2) {
				
				String patient1LastName = patient1.getLastName();
				String patient2LastName = patient2.getLastName();
				int lastNameComp = patient1LastName.compareTo(patient2LastName);
				
				if (lastNameComp != 0) {
					return lastNameComp;
				}
				
				String patient1FirstName = patient1.getFirstName();
				String patient2FirstName = patient2.getFirstName();
				return patient1FirstName.compareTo(patient2FirstName);
			}});
		}
}
