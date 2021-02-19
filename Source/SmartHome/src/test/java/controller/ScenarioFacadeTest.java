package controller;

import static org.junit.Assert.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import application.ScenarioFacade;
import domain.Room;
import domain.Scenario;
import domain.ScenariosHandler;
import domain.Obj.ObjType;
import service.TimeScheduleThread;
import domain.Obj;

public class ScenarioFacadeTest {
	
	@Test
	public void activateScenarioTest() {
		ScenariosHandler sh = new ScenariosHandler();
		ScenarioFacade sf = new ScenarioFacade(sh);
		List<List<String[]>> rooms = new ArrayList<>();
		List<String[]> roomObjs = new ArrayList<String[]>();
		String[] roomName = {"room1", null};
		roomObjs.add(roomName);
		String[] obj1 = {"light1", "true"};
		roomObjs.add(obj1);
		String[] obj2 = {"window1", "false"};
		roomObjs.add(obj2);
		rooms.add(roomObjs);
		sf.manageCreateScenario("scenario1", "null", rooms, null);
		
		sf.manageActivateScenario("scenario1");
		assertEquals(true, sh.getScenarios().get(0).isActive());
	}
	
	@Test
	public void createScenarioTest() {
		ScenariosHandler sh = new ScenariosHandler();
		ScenarioFacade sf = new ScenarioFacade(sh);
		List<List<String[]>> rooms = new ArrayList<>();
		List<String[]> roomObjs = new ArrayList<String[]>();
		String[] roomName = {"room1", null};
		roomObjs.add(roomName);
		String[] obj1 = {"light1", "true"};
		roomObjs.add(obj1);
		String[] obj2 = {"window1", "false"};
		roomObjs.add(obj2);
		rooms.add(roomObjs);		
		sf.manageCreateScenario("scenario1", "null", rooms, null);
		
		List<String> days = new ArrayList<>();
		days.add("lunedi");
		days.add("venerdi");
		days.add("sabato");
		sf.setDateTime("scenario1", "18:30", days);
		Scenario scenario = sh.getScenarios().get(0);
		assertEquals(true, sh.getScenarios().contains(scenario));
	}	
	
	@Test
	public void deleteScenarioTest() {
		ScenariosHandler sh = new ScenariosHandler();
		ScenarioFacade sf = new ScenarioFacade(sh);
		List<List<String[]>> rooms = new ArrayList<>();
		List<String[]> roomObjs = new ArrayList<String[]>();
		String[] roomName = {"room1", null};
		roomObjs.add(roomName);
		String[] obj1 = {"light1", "true"};
		roomObjs.add(obj1);
		String[] obj2 = {"window1", "false"};
		roomObjs.add(obj2);
		rooms.add(roomObjs);
		sf.manageCreateScenario("scenario1", "null", rooms, null);
		Scenario scenario = sh.getScenarios().get(0);
		sf.manageDeleteScenario("scenario1");
		assertEquals(false, sh.getScenarios().contains(scenario));
	}
	
	@Test
	public void addRoomToScenario() {
		ScenariosHandler sh = new ScenariosHandler();
		ScenarioFacade sf = new ScenarioFacade(sh);
		List<List<String[]>> rooms = new ArrayList<>();
		List<String[]> roomObjs = new ArrayList<String[]>();
		String[] roomName = {"room1", null};
		roomObjs.add(roomName);
		String[] obj1 = {"light1", "true"};
		roomObjs.add(obj1);
		String[] obj2 = {"window1", "false"};
		roomObjs.add(obj2);
		rooms.add(roomObjs);
		sf.manageCreateScenario("scenario1", "null", rooms, null);
		Scenario scenario = sh.getScenarios().get(0);
		
		sf.manageAddRoomsToScenario("scenario1", rooms);
		assertEquals(true, sh.getScenarios().contains(scenario));
	}
	
	
	@Test
	public void setDateTimeTest() {
		ScenariosHandler sh = new ScenariosHandler();
		ScenarioFacade sf = new ScenarioFacade(sh);
		List<List<String[]>> rooms = new ArrayList<>();
		List<String[]> roomObjs = new ArrayList<String[]>();
		String[] roomName = {"room1", null};
		roomObjs.add(roomName);
		String[] obj1 = {"light1", "true"};
		roomObjs.add(obj1);
		String[] obj2 = {"window1", "false"};
		roomObjs.add(obj2);
		rooms.add(roomObjs);
		sf.manageCreateScenario("scenario1", "null", rooms, null);
		
		List<String> days = new ArrayList<>();
		days.add("lunedi");
		days.add("martedi");
		days.add("mercoledi");
		days.add("giovedi");
		days.add("venerdi");
		days.add("sabato");
		days.add("domenica");
		
		Scenario scenario = sh.getScenarios().get(0);
		sf.setDateTime("scenario1", "18:30", days);
		assertEquals("18:30", scenario.getStartTime().toString());
		assertEquals(DayOfWeek.MONDAY, scenario.getDays().get(0));		
	}
	
	@Test
	public void changeActionTest() {
		ScenariosHandler sh = new ScenariosHandler();
		ScenarioFacade sf = new ScenarioFacade(sh);
		List<List<String[]>> rooms = new ArrayList<>();
		List<String[]> roomObjs = new ArrayList<String[]>();
		String[] roomName = {"room1", null};
		roomObjs.add(roomName);
		String[] obj1 = {"light1", "true"};
		roomObjs.add(obj1);
		String[] obj2 = {"window1", "false"};
		roomObjs.add(obj2);
		rooms.add(roomObjs);
		sf.manageCreateScenario("scenario1", "null", rooms, null);
		
		sf.manageChangeAction("scenario1", "light1", "true");
		assertEquals(true, sh.getScenarios().get(0).getActions().get(0));
		
		sf.manageChangeAction("scenario1", "window1", "false");
		assertEquals(false, sh.getScenarios().get(0).getActions().get(1));
	}
	
	@Test
	public void deleteActionTest() {
		ScenariosHandler sh = new ScenariosHandler();
		ScenarioFacade sf = new ScenarioFacade(sh);
		List<List<String[]>> rooms = new ArrayList<>();
		List<String[]> roomObjs = new ArrayList<String[]>();
		String[] roomName = {"room1", null};
		roomObjs.add(roomName);
		String[] obj1 = {"light1", "true"};
		roomObjs.add(obj1);
		String[] obj2 = {"window1", "false"};
		roomObjs.add(obj2);
		rooms.add(roomObjs);
		sf.manageCreateScenario("scenario1", "null", rooms, null);
		
		sf.manageDeleteAction("scenario1", "light1");
		assertEquals(false, sh.getObjsInScenario("scenario1").contains("light1"));
		assertEquals(false, sh.getActionsInScenario("scenario1").get(0));
	}
}
