package scrum.client;

import ilarkesto.core.scope.Scope;
import scrum.client.admin.User;
import scrum.client.common.AScrumComponent;
import scrum.client.workspace.Ui;

public class Auth extends AScrumComponent implements ServerDataReceivedListener {

	private User user;

	public void onServerDataReceived(DataTransferObject data) {
		if (data.isUserSet()) {
			user = cm.getDao().getUser(data.getUserId());
			log.info("User logged in:", user);
			cm.getEventBus().fireLogin();
		}
	}

	public void logout() {
		if (cm.getProjectContext().isProjectOpen()) cm.getProjectContext().closeProject();
		log.info("Logging out");
		Scope.get().getComponent(Ui.class).lock("Logging out..."); // TODO dependency
		user = null;
		cm.getEventBus().fireLogout();
		cm.getApp().callLogout();
		ScrumScopeManager.destroyUserScope();
	}

	public boolean isUserLoggedIn() {
		return user != null;
	}

	public User getUser() {
		return user;
	}

}
