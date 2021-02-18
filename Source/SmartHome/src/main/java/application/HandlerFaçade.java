package main.java.application;

import main.java.domain.Alarm;
import main.java.domain.ConflictHandler;
import main.java.view.IsAtHomeView;
import main.java.view.NotifiesView;

public class HandlerFaçade {
	
	private IsAtHomeView isAtHomeView;
	private NotifiesView notifies;
	
	public HandlerFaçade() {
		notifies = new NotifiesView();
	}
	public void manageHomeFlagSettings(String state, String code, String doorID) {
		if(state.equals("home"))
			ConflictHandler.getInstance().isHome(doorID, code);
		else
			ConflictHandler.getInstance().isAway(doorID);		
	}

	public void manageManualAction(String objectID) {
		ConflictHandler.getInstance().doManualAction(objectID);
	}
	
	public void manageWrongCode() {
		isAtHomeView.wrongCode();
	}
	
	public boolean userNotifies(String message) { 
		return notifies.notification(message);
	}

	public void setIsAtHomeView(IsAtHomeView isAtHomeView) {
		this.isAtHomeView = isAtHomeView;		
	}
}
