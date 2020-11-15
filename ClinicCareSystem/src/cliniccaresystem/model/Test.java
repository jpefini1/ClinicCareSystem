package cliniccaresystem.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Test {

	private int testId;
	private int appointmentId;
	private String name;
	private LocalDateTime timePerformed;
	private String result;
	private String isAbnormal;
	
	private String formattedTimePerformed;
	
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
		
		this.updateFormattedTimePerfomed();
	}
	
	private void updateFormattedTimePerfomed() {
		this.formattedTimePerformed = this.timePerformed.format(DateTimeFormatter.ISO_LOCAL_DATE) + " " + 
				this.timePerformed.toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm a"));
	}
	
	public String getName() {
		return this.name;
	}

	public LocalDateTime getTimePerformed() {
		return timePerformed;
	}

	public void setTimePerformed(LocalDateTime timePerformed) {
		this.timePerformed = timePerformed;
		this.updateFormattedTimePerfomed();
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
	
	public String getFormattedTimePerformed() {
		return this.formattedTimePerformed;
	}
}
