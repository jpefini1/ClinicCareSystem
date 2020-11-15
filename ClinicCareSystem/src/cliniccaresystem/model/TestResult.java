package cliniccaresystem.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestResult {
	
	private LocalDateTime timePerformed;
	private String result;
	private String isAbnormal;
	private String formattedTimePerformed;
	
	public TestResult(LocalDateTime timePerformed, String result, String isAbnormal) {
		this.timePerformed = timePerformed;
		this.result = result;
		this.isAbnormal = isAbnormal;
		
		this.updateFormattedTimePerfomed();
	}
	
	private void updateFormattedTimePerfomed() {
		this.formattedTimePerformed = this.timePerformed.format(DateTimeFormatter.ISO_LOCAL_DATE) + " " + 
				this.timePerformed.toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm a"));
	}

	public LocalDateTime getTimePerformed() {
		return this.timePerformed;
	}

	public void setTimePerformed(LocalDateTime timePerformed) {
		this.timePerformed = timePerformed;
		this.updateFormattedTimePerfomed();
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getIsAbnormal() {
		return this.isAbnormal;
	}

	public void setIsAbnormal(String isAbnormal) {
		this.isAbnormal = isAbnormal;
	}

	public String getFormattedTimePerformed() {
		return formattedTimePerformed;
	}
}
