package cliniccaresystem.model;

public class TestSummary {
	
	String testName;
	String isAbnormal;
	String result;
	
	public TestSummary(Test test) {
		this.testName = test.getName();
				
		if (test.getTestResults() != null) {
			this.isAbnormal = test.getTestResults().getIsAbnormal();
			this.result = test.getTestResults().getResult();
		}
	}
	
	public TestSummary(String testName, String isAbnormal, String result) {
		this.testName = testName;
		this.isAbnormal = isAbnormal;
		this.result = result;
	}
	
	public TestSummary(String testName, String isAbnormal) {
		this.testName = testName;
		this.isAbnormal = isAbnormal;
	}

	public String getTestName() {
		return this.testName;
	}

	public String getIsAbnormal() {
		return this.isAbnormal;
	}

	public String getResult() {
		return this.result;
	}
	
	public void setIsAbnormal(String isAbnormal) {
		this.isAbnormal = isAbnormal;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	
}
