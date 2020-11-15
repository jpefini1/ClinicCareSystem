package cliniccaresystem.model;

import java.time.LocalDateTime;

public class Test {

	int testId;
	int appointmentId;
	String name;
	LocalDateTime timePerformed;
	String result;
	String isAbnormal;
	
	public Test(int appointmentId, String name) {
		this.appointmentId = appointmentId;
		this.name = name;
	}
	
	public Test(int testId, int appointmentId, String name) {
		this.testId = testId;
		this.appointmentId = appointmentId;
		this.name = name;
	}
	
	public Test(int testId, int appointmentId, String name, LocalDateTime timePerformed, String result, String isAbnormal) {
		this.testId = testId;
		this.appointmentId = appointmentId;
		this.name = name;
		this.timePerformed = timePerformed;
		this.result = result;
		this.isAbnormal = isAbnormal;
	}
	
	public String getName() {
		return this.name;
	}

	public LocalDateTime getTimePerformed() {
		return timePerformed;
	}

	public void setTimePerformed(LocalDateTime timePerformed) {
		this.timePerformed = timePerformed;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getIsAbnormal() {
		return isAbnormal;
	}

	public void setAbnormal(String isAbnormal) {
		this.isAbnormal = isAbnormal;
	}
	
	public int getTestId() {
		return this.testId;
	}

	public void setTestId(int testId) {
		this.testId = testId;
	}

	public int getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}
}
