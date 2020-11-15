package cliniccaresystem.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Test {

	private int testId;
	private int appointmentId;
	private String name;
	private TestResult testResults;
	
	public Test(int appointmentId, String name) {
		this.appointmentId = appointmentId;
		this.name = name;
	}
	
	public Test(int testId, int appointmentId, String name) {
		this.testId = testId;
		this.appointmentId = appointmentId;
		this.name = name;
	}
	
	public Test(int testId, int appointmentId, String name, TestResult testResults) {
		this.testId = testId;
		this.appointmentId = appointmentId;
		this.name = name;
		this.testResults = testResults;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getTestId() {
		return this.testId;
	}

	public void setTestId(int testId) {
		this.testId = testId;
	}

	public int getAppointmentId() {
		return this.appointmentId;
	}

	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}

	public TestResult getTestResults() {
		return this.testResults;
	}

	public void setResult(TestResult result) {
		this.testResults = result;
	}
	
	
	public String getTestResultsSummary() {
		if (this.testResults != null) {
			return this.testResults.getResult();
		}
		
		return "Unavailable";
	}
	
	public String getFormattedTimePerformed() {
		if (this.testResults != null) {
			return this.testResults.getFormattedTimePerformed();
		}
		
		return "Unavailable";
	}
	
	public String getIsAbnormal() {
		if (this.testResults != null) {
			return this.testResults.getIsAbnormal();
		}
		
		return "Unavailable";
	}
}
