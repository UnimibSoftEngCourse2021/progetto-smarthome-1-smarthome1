package domainServiceLifeCycle;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import application.GenericFacade;
import application.HandlerFacade;
import domain.Alarm;
import domain.AutomaticControl;
import domain.AutomaticControl.ChoosenMatrix;
import domain.Config;
import domain.ConflictHandler;
import domain.DataDescription;
import domain.Door;
import domain.Heater;
import domain.Light;
import domain.Obj.ObjType;
import domain.Room;
import domain.Sensor;
import domain.Sensor.AirState;
import domain.Sensor.SensorCategory;
import domain.TimerOP.Type;
import domain.Window;

public class CommunicationTest {

	//dichiarazione variabili di setup per l'ambiente di testing
	private Room r1;
	private Room r2;
	private Sensor sR1;
	private Sensor sR2;
	private Heater h1R1;
	private Heater h2R2;
	private Heater h3R2;
	private Sensor aR1;
	private Window w1R1;
	private Window w2R1;
	private Window w1R2;
	private Light l1R1;
	private Light l1R2;
	private Sensor mR1;
	
	@Before
	public void init() {
		//inizializzazione gestori delle notifiche
		HandlerFacade hf = new HandlerFacade();
		ConflictHandler.getInstance().setHandlerFacade(hf);
		
		//creazione stanze
		r1 = new Room("room1", (short)1);
		r2 = new Room("room2", (short)1);
		r1.createTimer();
		r2.createTimer();
		Config.getInstance().setRooms(r1);
		Config.getInstance().setRooms(r2);
		
		//aggiunta oggetti:
		//creazione allarme
		Alarm.getInstance("alarm1");
		Alarm.getInstance().getSensor().attach(Alarm.getInstance());
		ConflictHandler.getInstance().addObj(Alarm.getInstance());
		
		//aggiunta porta alla prima stanza
		r1.instantiateObj(ObjType.DOOR, "door1");
		//aggiunta di due finestre alla stanza room1
		r1.instantiateObj(ObjType.WINDOW, "window1");
		r1.instantiateObj(ObjType.WINDOW, "window2");
		//aggiunta una finestra alla stanza room2
		r2.instantiateObj(ObjType.WINDOW, "window1");
		//aggiunta caloriferi alla prima stanza
		r1.instantiateObj(ObjType.HEATER, "heater1");
		//aggiunta di due caloriferi alla seconda stanza
		r2.instantiateObj(ObjType.HEATER, "heater2");
		r2.instantiateObj(ObjType.HEATER, "heater3");
		//aggiunta di una luce ad entrambe le stanze
		r1.instantiateObj(ObjType.LIGHT, "light1");
		r2.instantiateObj(ObjType.LIGHT, "light1");
		
		
		//setting numero oggetti delle stanze
		r1.setWindowsNum();
		r2.setWindowsNum();
		r1.setHeatersNum();
		r2.setHeatersNum();
		r1.setLightsNum();
		r2.setLightsNum();
		
		//aggiunta sensori:
		//aggiunta di un sensore dell'aria nella prima stanza
		r1.instantiateSensor(SensorCategory.AIR, "air1");
		//aggiunta di un sensore di movimento nella seconda stanza
		r2.instantiateSensor(SensorCategory.MOVEMENT, "mov1");
		//aggiunta di un sensore di temperatura a ciascuna stanza
		r1.instantiateSensor(SensorCategory.TEMPERATURE, "temp1");
		r2.instantiateSensor(SensorCategory.TEMPERATURE, "temp2");
		//aggiunta di un sensore di movimento nella prima stanza
		r1.instantiateSensor(SensorCategory.MOVEMENT, "mov1");
		r1.getSensors(SensorCategory.MOVEMENT).get(0).attach(Alarm.getInstance());
		
		//binding caloriferi con i rispettivi sensori di temperatura
		sR1 = r1.getSensors(SensorCategory.TEMPERATURE).get(0);
		sR2 = r2.getSensors(SensorCategory.TEMPERATURE).get(0);
		h1R1 = (Heater)r1.getObjs(ObjType.HEATER).get(0);
		h2R2 = (Heater)r2.getObjs(ObjType.HEATER).get(0);
		h3R2 = (Heater)r2.getObjs(ObjType.HEATER).get(1);
		sR1.attach(h1R1);
		sR1.attach(h2R2);
		sR2.attach(h3R2);
		h1R1.setTempSensor(sR1);
		h2R2.setTempSensor(sR1);
		h3R2.setTempSensor(sR2);
		
		//dichiarazione sensore aria
		aR1 = r1.getSensors(SensorCategory.AIR).get(0);
		
		//dichiarazione sensore movimento
		mR1 = r1.getSensors(SensorCategory.MOVEMENT).get(0);
		
		//dichiarazione finestre
		w1R1 = (Window)r1.getObjs(ObjType.WINDOW).get(0);
		w2R1 = (Window)r1.getObjs(ObjType.WINDOW).get(1);
		w1R2 = (Window)r2.getObjs(ObjType.WINDOW).get(0);
		
		//dichiarazione luci
		l1R1 = (Light)r1.getObjs(ObjType.LIGHT).get(0);
		l1R2 = (Light)r2.getObjs(ObjType.LIGHT).get(0);

		//binding sensore di movimento con le rispettive luci
		mR1.attach(l1R1);
	}
	
	@Test
	public void checkHeaterOnTest() {
		sR1.notifies(SensorCategory.TEMPERATURE, 15.00);
		assertEquals(true, h1R1.isActive());
		assertEquals(true, h2R2.isActive());
	}

	@Test
	public void checkHeaterOffTest() {
		sR1.notifies(SensorCategory.TEMPERATURE, 21.00);
		assertEquals(false, h1R1.isActive());
		assertEquals(false, h2R2.isActive());
	}
	
	@Test
	public void checkUserHeaterOnTest() {
		//config matrice temperature utente
		DataDescription dd = new DataDescription();
		GenericFacade gf = new GenericFacade(dd);
		Config.getInstance().setDataDescription(dd);
		double[] monday = {18.00, 21.00, 0.00, 0.00, 1.00, 1.00, 1.00, 0.00, 0.00, 1.00, 0.00, 1.00, 1.00, 1.00, 1.00, 0.00, 0.00, 0.00, 1.00, 1.00, 1.00, 1.00, 1.00, 0.00, 0.00, 0.00};
		double[] sunday = {18.00, 21.00, 0.00, 0.00, 1.00, 1.00, 1.00, 0.00, 0.00, 1.00, 0.00, 1.00, 1.00, 1.00, 1.00, 0.00, 0.00, 0.00, 1.00, 1.00, 1.00, 1.00, 1.00, 0.00, 0.00, 0.00};
		double[] friday = {18.00, 21.00, 0.00, 0.00, 1.00, 1.00, 1.00, 0.00, 0.00, 1.00, 0.00, 1.00, 1.00, 1.00, 1.00, 0.00, 0.00, 0.00, 1.00, 1.00, 1.00, 1.00, 1.00, 0.00, 0.00, 0.00};
		double[]  other = {18.00, 21.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00};
		gf.manageWriteOnHSCFile("monday", monday);
		gf.manageWriteOnHSCFile("Tuesday", other);
		gf.manageWriteOnHSCFile("wednesday", other);
		gf.manageWriteOnHSCFile("thursday", other);
		gf.manageWriteOnHSCFile("saturday", other);
		gf.manageWriteOnHSCFile("sunday", sunday);
		gf.manageWriteOnHSCFile("friday", friday);
		gf.manageWriteOnHSCFile("end", null);
		
		//check
		sR1.notifies(SensorCategory.TEMPERATURE, 15.00);
		assertEquals(true, h1R1.isActive());
		assertEquals(true, h2R2.isActive());
		
	}
	@Test
	public void checkUserHeatOffTest() {
		//config matrice temperature utente
				DataDescription dd = new DataDescription();
				GenericFacade gf = new GenericFacade(dd);
				Config.getInstance().setDataDescription(dd);
				double[] monday = {18.00, 21.00, 0.00, 0.00, 1.00, 1.00, 1.00, 0.00, 0.00, 1.00, 0.00, 1.00, 1.00, 1.00, 1.00, 0.00, 0.00, 0.00, 1.00, 1.00, 1.00, 1.00, 1.00, 0.00, 0.00, 0.00};
				double[] sunday = {18.00, 21.00, 0.00, 0.00, 1.00, 1.00, 1.00, 0.00, 0.00, 1.00, 0.00, 1.00, 1.00, 1.00, 1.00, 0.00, 0.00, 0.00, 1.00, 1.00, 1.00, 1.00, 1.00, 0.00, 0.00, 0.00};
				double[] friday = {18.00, 21.00, 0.00, 0.00, 1.00, 1.00, 1.00, 0.00, 0.00, 1.00, 0.00, 1.00, 1.00, 1.00, 1.00, 0.00, 0.00, 0.00, 1.00, 1.00, 1.00, 1.00, 1.00, 0.00, 0.00, 0.00};
				double[]  other = {18.00, 21.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00};
				gf.manageWriteOnHSCFile("monday", monday);
				gf.manageWriteOnHSCFile("Tuesday", other);
				gf.manageWriteOnHSCFile("wednesday", other);
				gf.manageWriteOnHSCFile("thursday", other);
				gf.manageWriteOnHSCFile("saturday", other);
				gf.manageWriteOnHSCFile("sunday", sunday);
				gf.manageWriteOnHSCFile("friday", friday);
				gf.manageWriteOnHSCFile("end", null);
				
				//check
				sR1.notifies(SensorCategory.TEMPERATURE, 22.00);
				assertEquals(false, h1R1.isActive());
				assertEquals(false, h2R2.isActive());
	}
	
	@Test
	public void checkAirPollutionIsOkTest() {
		aR1.setAirState(AirState.POLLUTION);
		aR1.notifies(SensorCategory.AIR, 22.00);
		assertEquals(false, w1R1.isActive());
		assertEquals(false, w2R1.isActive());
	}
	
	@Test
	public void checkTimerIsDoneAirPollutionTest() {
		//simulazione conclusione del timerThread
		w1R1.setActive(true);
		w2R1.setActive(true);
		r1.getTimer().startTimer(Type.AIR, 300);
		
		//check
		r1.getTimer().executeOperations(Type.AIR);
		assertEquals(false, w1R1.isActive());
		assertEquals(false, w2R1.isActive());
	}
	
	@Test
	public void checkAirPollutionIsNotOkTest() {
		aR1.setAirState(AirState.POLLUTION);
		aR1.notifies(SensorCategory.AIR, 55.00);
		assertEquals(true, w1R1.isActive());
		assertEquals(true, w2R1.isActive());
	}
	
	
	
	
	@Test
	public void checkGasPollutionIsNotOkTest() {
		aR1.setAirState(AirState.GAS);
		aR1.notifies(SensorCategory.AIR, 55.00);
		assertEquals(true, w1R1.isActive());
		assertEquals(true, w2R1.isActive());
	}
	
	
	@Test
	
	public void checkAlarmTest() {
		Alarm.getInstance().setArmed(true);
		mR1.notifies(SensorCategory.MOVEMENT, 1.00);
		assertEquals(true, Alarm.getInstance().isActive());
	}
	
	
	@Test
	public void checkNightLightOnTest() {
		//
		AutomaticControl.setStopDayMode("00:00");
		AutomaticControl.setStopDayMode("01:00");
		
		mR1.notifies(SensorCategory.MOVEMENT, 1.00);
		assertEquals(true, l1R1.isActive());
	}
	
	@Test
	public void checkDayLightOffTest() {
		//inizializzazione dayMode
		AutomaticControl.setStopDayMode("00:00");
		AutomaticControl.setStopDayMode("23:59");
		
		//caso in cui gli shader sono abbassati
		//la luce non si accende 
		mR1.notifies(SensorCategory.MOVEMENT, 1.00);
		assertEquals(false, l1R1.isActive());
		
		//shader non abbassati
		//la luce si accende 
		w1R1.getShader().setActive(true);
		w2R1.getShader().setActive(true);
		mR1.notifies(SensorCategory.MOVEMENT, 1.00);
		assertEquals(true, l1R1.isActive());
	}
	
	@Test 
	public void startTimerTest() {
		//inizializzazione dayMode + startTimer
		AutomaticControl.setStopDayMode("00:00");
		AutomaticControl.setStopDayMode("23:59");
		l1R1.setActive(true);
				
		//check attivazione timer
		//il timer aspetta che passino 5 minuti prima di spegnere le luci
		mR1.notifies(SensorCategory.MOVEMENT, 0.00);
		assertEquals(true, l1R1.isActive());
	}
	
	@Test
	public void switchOffLightTimerTest() {
		//simulazione esecuzione timerThread
		//inizializzazione dayMode + startTimer
		AutomaticControl.setStopDayMode("00:00");
		AutomaticControl.setStopDayMode("23:59");
		l1R1.setActive(true);
		r1.getTimer().startTimer(Type.LIGHT, 600);
		
		//check spegnimento/reset timer
		//dopo 5 minuti le luci si spengono
		r1.getTimer().executeOperations(Type.LIGHT);
		assertEquals(false, l1R1.isActive());
	}
	
	@Test
	public void resetTimerTest() {
		//simulazione esecuzione timerThread
		//inizializzazione dayMode + startTimer
		AutomaticControl.setStopDayMode("00:00");
		AutomaticControl.setStopDayMode("23:59");
		l1R1.setActive(true);
		r1.getTimer().startTimer(Type.LIGHT, 600);
		
		//reset timer perche il sensore di movimento rileva qualcosa prima dello scadere del timer
		//le luci rimangono accese
		mR1.notifies(SensorCategory.MOVEMENT, 1.00);
		assertEquals(true, l1R1.isActive());
	}
	
	@Test
	public void checkDayModeTest() {
		AutomaticControl.setStartDayMode("08:00");
		AutomaticControl.setStopDayMode("23.00");
		assertEquals(true, AutomaticControl.getInstance().isDayMode());
	}
	
	@Test 
	public void checkGasAndAlarmConflictTest() {
		//init locale al test
		Alarm.getInstance().setArmed(false);
		w1R1.getShader().setActive(true);
		l1R1.setActive(true);
		l1R2.setActive(true);
		
		//test
		aR1.setAirState(AirState.GAS);
		aR1.notifies(SensorCategory.AIR, 35.00);
		assertEquals(true, w1R1.isActive());
		assertEquals(true, w2R1.isActive());
		assertEquals(false, w1R2.isActive());
		assertEquals(false, l1R1.isActive());
		assertEquals(true, l1R2.isActive());
	}
	
	@After
	public void clean() {
		if(Alarm.isCreated())
			Alarm.getInstance().clean();
		AutomaticControl.getInstance().setChoosenMatrix(ChoosenMatrix.STANDARD);
		Config.getInstance().clean();
		AutomaticControl.getInstance().clean();
		ConflictHandler.getInstance().clean();
	}
}
