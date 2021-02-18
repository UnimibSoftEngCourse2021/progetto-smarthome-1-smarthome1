package application;

import domain.Alarm;
import domain.ConflictHandler;
import view.IsAtHomeView;
import view.NotifiesView;

public class HandlerFaçade {
	
	private IsAtHomeView isAtHomeView;
	private NotifiesView notifies;
	
	public HandlerFaçade() {
		notifies = new NotifiesView();
	}
	public void manageHomeFlagSettings(String state, String code, String doorID) {
		if(state.equals("home"))
			ConflictHandler.getInstance().isHome(Alarm.getInstance().getObjectID(), doorID, code);
		else
			ConflictHandler.getInstance().isAway(Alarm.getInstance().getObjectID(), doorID);		
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
