package cliniccaresystem.model;

public class ActiveUser {

	private static User activeUser = null;

	public static User getActiveUser() {
		return activeUser;
	}

	public static void setActiveUser(User activeUser) {
		ActiveUser.activeUser = activeUser;
	}
}
