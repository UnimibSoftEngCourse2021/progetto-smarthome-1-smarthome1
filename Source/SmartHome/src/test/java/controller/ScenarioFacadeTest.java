package controller;

import static org.junit.Assert.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import application.ScenarioFacade;
import domain.Scenario;
import domain.ScenariosHandler;
import domain.Window;
import domain.AutomaticControl;
import domain.Config;
import domain.ConflictHandler;
import domain.Light;
import domain.Obj.ObjType;
import domain.Room;

public class ScenarioFacadeTest {
	
	private ScenariosHandler sh;
	private ScenarioFacade sf;
	private List<List<String[]>> rooms;
	private List<String[]> roomObjs;
	private String[] roomName = {"room1", null};
	private String[] obj1 = {"LIGHT_ROOM1_1_0", "true"};
	private String[] obj2 = {"WINDOW_ROOM1_1_0", "false"};
	private Scenario scenario;
	private Room r1;
	private Light l1;
	private Window w1;
	
	@Before
	public void init() {
		//inizializzazione container e facade
		sh = new ScenariosHandler();
		sf = new ScenarioFacade(sh);
		
		//creazione strutture dati da passare al costruttore di scenario
		rooms = new ArrayList<>();
		roomObjs = new ArrayList<String[]>();
		
		//creazione oggetti su cui eseguire le azioni dello scenario
		r1 = new Room("room1", (short)1);
		r1.instantiateObj(ObjType.LIGHT, "light1");
		r1.instantiateObj(ObjType.WINDOW, "window1");
		
		//oggetti luce e finestra
		l1 = (Light)r1.getObjs(ObjType.LIGHT).get(0);
		w1 = (Window)r1.getObjs(ObjType.WINDOW).get(0);
		
		//inizializzazione strutture dati
		roomObjs.add(roomName);
		roomObjs.add(obj1);
		roomObjs.add(obj2);
		rooms.add(roomObjs);
		
		//creazione scenario
		sf.manageCreateScenario("scenario1", "null", rooms, null);
		scenario = sh.getScenarios().get(0);
	}
	
	@Test
	public void createAutomaticScenarioTest() {
		//inizializzazione strutture dati da passare a scenariosHandler per creare uno scenario
		List<String> days = new ArrayList<>();
		days.add("lunedi");
		days.add("martedi");
		days.add("mercoledi");
		days.add("giovedi");
		days.add("venerdi");
		days.add("sabato");
		days.add("domenica");
		String hour = "00";
		String minute = "00";
		
		//
		sf.manageCreateScenario("scenario2", hour + ":" + minute, rooms, days);
		Scenario s2 = sh.getScenarios().get(1);
		assertEquals(true, sh.getScenarios().contains(s2));
	}
	
	@Test 
	public void activateAutomaticScenarioTest() {
		//inizializzazione dell'orario di attivazione dello scenario
		
		
		/*
		//inizializzazione dayMode 
		AutomaticControl.getInstance();
		AutomaticControl.setStartDayMode("23:59");
		AutomaticControl.setStopDayMode("00:00");
		
		/*settando orario e giorni lo scenario viene gestito dal thread specifico
		 *che lo attiva al'orario specificato 
		 */
		//scenario.getActions().set(0, true);
		
		//attivazione manuale della chiamata del thread
		scenario.activateScenario();
		assertEquals(false, w1.isActive());
		assertEquals(true, l1.isActive());
	}
	
	@Test
	public void activateScenarioTest() {
		sf.manageActivateScenario("scenario1");
		//assertEquals(true, scenario.isActive());
	}
	
	@Test
	public void createScenarioTest() {		
		List<String> days = new ArrayList<>();
		days.add("lunedi");
		days.add("venerdi");
		days.add("sabato");
		sf.setDateTime("scenario1", "18:30", days);
		assertEquals(true, sh.getScenarios().contains(scenario));
	}	
	
	@Test
	public void deleteScenarioTest() {
		sf.manageDeleteScenario("scenario1");
		assertEquals(false, sh.getScenarios().contains(scenario));
	}
	
	@Test
	public void addRoomToScenario() {
		sf.manageAddRoomsToScenario("scenario1", rooms);
		assertEquals(true, sh.getScenarios().contains(scenario));
	}
	
	
	@Test
	public void setDateTimeTest() {		
		List<String> days = new ArrayList<>();
		days.add("lunedi");
		days.add("martedi");
		days.add("mercoledi");
		days.add("giovedi");
		days.add("venerdi");
		days.add("sabato");
		days.add("domenica");
		sf.setDateTime("scenario1", "18:30", days);
		assertEquals("18:30", scenario.getStartTime().toString());
		assertEquals(DayOfWeek.MONDAY, scenario.getDays().get(0));		
	}
	
	@Test
	public void changeActionTest() {
		sf.manageChangeAction("scenario1", "light1", "true");
		assertEquals(true, scenario.getActions().get(0));
		
		sf.manageChangeAction("scenario1", "window1", "false");
		assertEquals(false, scenario.getActions().get(1));
	}
	
	@Test
	public void deleteActionTest() {		
		sf.manageDeleteAction("scenario1", "LIGHT_ROOM1_1_0");
		assertEquals(false, sh.getObjsInScenario("scenario1").contains("LIGHT_ROOM1_1_0"));
		assertEquals(true, sh.getObjsInScenario("scenario1").contains("WINDOW_ROOM1_1_0"));
		assertEquals(false, sh.getActionsInScenario("scenario1").get(0));
	}
	
	@After
	public void clean() {
		Config.clean();
		AutomaticControl.clean();
		ConflictHandler.clean();
	}
}
