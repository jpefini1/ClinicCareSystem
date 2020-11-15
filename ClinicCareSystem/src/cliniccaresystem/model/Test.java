package cliniccaresystem.model;

import java.time.LocalDateTime;

public class Test {

	int testId;
	String name;
	LocalDateTime timePerformed;
	String result;
	boolean isAbnormal;
	
	public Test(int testId, String name) {
		this.testId = testId;
		this.name = name;
	}
	
	public Test(int testId, String name, LocalDateTime timePerformed, String result, boolean isAbnormal) {
		this.testId = testId;
		this.name = name;
		this.timePerformed = timePerformed;
		this.result = result;
		this.isAbnormal = isAbnormal;
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

	public boolean isAbnormal() {
		return isAbnormal;
	}

	public void setAbnormal(boolean isAbnormal) {
		this.isAbnormal = isAbnormal;
	}
	
	public int getTestId() {
		return this.testId;
	}

	public void setTestId(int testId) {
		this.testId = testId;
	}
}
