package cliniccaresystem.model;

public class RoutineCheckResults {

	private int appointmentId;
	private int nurseId;
	private int systolicBP;
	private int diastolicBP;
	private int pulse;
	private double weight;
	private double bodyTemp;
	
	public RoutineCheckResults(int appointmentId, int nurseId, int systolicBP, int diastolicBP, int pulse, double weight, double bodyTemp) {
		this.appointmentId = appointmentId;
		this.nurseId = nurseId;
		this.systolicBP = systolicBP;
		this.diastolicBP = diastolicBP;
		this.pulse = pulse;
		this.weight = weight;
		this.bodyTemp = bodyTemp;
	}

	public int getAppointmentId() {
		return this.appointmentId;
	}

	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}

	public int getNurseId() {
		return this.nurseId;
	}

	public void setNurseId(int nurseId) {
		this.nurseId = nurseId;
	}

	public int getSystolicBP() {
		return this.systolicBP;
	}

	public void setSystolicBP(int systolicBP) {
		this.systolicBP = systolicBP;
	}

	public int getDiastolicBP() {
		return this.diastolicBP;
	}

	public void setDiastolicBP(int diastolicBP) {
		this.diastolicBP = diastolicBP;
	}

	public int getPulse() {
		return this.pulse;
	}

	public void setPulse(int pulse) {
		this.pulse = pulse;
	}

	public double getWeight() {
		return this.weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getBodyTemp() {
		return this.bodyTemp;
	}

	public void setBodyTemp(double bodyTemp) {
		this.bodyTemp = bodyTemp;
	}
}
