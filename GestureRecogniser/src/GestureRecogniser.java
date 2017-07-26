
import output.BufferedFileOutput;
import output.OutputManager;
import analysis.AnalysisManager;

import events.event.AcceptingStateEvent;
import events.event.CoordinateEvent;
import events.event.LimitToGestureEvent;
import events.listeners.AcceptingStateListener;
import events.listeners.CoordinateListener;
import events.listeners.LimitTogestureListener;
import filters.CorrectingBufferedFilter;
import filters.Filter;
import filters.NoiseSimulatorFilter;
import filters.SimpleKalmanFilter;
import model.FiniteStateMachine;
import recogniser.FiniteStateMachineManager;
import ui.monitor.analysis.AnalysisDisplay;
import ui.monitor.input.InputMonitor;
import gestures.*;
import input.FileInput;
import input.GestureSimulator;
import input.GestureSimulator2;
import input.GyroResource;
import input.InputInterface;
import input.InputManager;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.core.network.EndpointManager;

/**
 * The main class of the gestureRecogniser project
 * @author Balazs Pete
 *
 */
public class GestureRecogniser {
	
	private CoapServer coapServer;
	/**
	 * Run the application
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			GestureRecogniser gr = new GestureRecogniser();
			gr.start();
		} catch (Exception ex) {
			Logger.getLogger(GestureRecogniser.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	private FiniteStateMachineManager fsmm = new FiniteStateMachineManager();
	private InputManager inputManager;
	private InputMonitor inputMonitor;
	private OutputManager outputManager;
	private AnalysisManager analysisManager;
	
	/**
	 * Create a new instance of GestureRecogniser
	 */
	public GestureRecogniser() throws Exception {
		coapServer = new CoapServer();
		for (InetAddress addr : EndpointManager.getEndpointManager().getNetworkInterfaces()) {
			if (!addr.isLinkLocalAddress()) {
                coapServer.addEndpoint(new CoapEndpoint(new InetSocketAddress(addr, CoAP.DEFAULT_COAP_PORT)));
			}
		}
		coapServer.add(new GyroResource());
		
	}
	
	/**
	 * Start the execution of the instance
	 */
	public void start() throws Exception {
		
		coapServer.start();
		// Add FiniteStateMachines to the FSM manager
		addGestures();
		
		// Change InputInterface depending on input requirements
		GestureSimulator2 gs = new GestureSimulator2();//
		InputInterface input = gs;
//		InputInterface input = new FileInput(null);
		
		// Change Filter to filter type required
		//Filter noiseFilter = new NoiseSimulatorFilter(1, 500000);
		
		Filter correctingFilter = new CorrectingBufferedFilter(7);
		Filter simpleKalmanFilter = new SimpleKalmanFilter(correctingFilter);
		
		// filter is passed into system components...
		Filter filter = simpleKalmanFilter;
		
		// Set up the input manager
		inputManager = new InputManager(input, filter);
		inputManager.addEventListener(new CoordinateListener() {
			@Override
			public void handleCoordinate(CoordinateEvent e) {
				fsmm.input(e.getCoordinate());
			}
		});
		
		// Set up the input monitor
		inputMonitor = new InputMonitor(inputManager);
		fsmm.addAcceptingStateListener(new AcceptingStateListener() {
			@Override
			public void handleAcceptingState(AcceptingStateEvent e) {
				inputMonitor.handleAcceptingState(e);
			}
		});
		gs.addListener(new LimitTogestureListener(){
			@Override
			public void handle(LimitToGestureEvent e) {
				// TODO Auto-generated method stub
				System.out.println("here");
				inputMonitor.setCurrentGesture(e.getGesture());
			}
		});
		
		
		// Set up the output
		BufferedFileOutput outputInterface = new BufferedFileOutput("output");
		outputManager = new OutputManager(outputInterface);
		fsmm.addAcceptingStateListener(new AcceptingStateListener() {
			@Override
			public void handleAcceptingState(AcceptingStateEvent e) {
				outputManager.handleAcceptingStateEvent(e);
			}
		});
		
		// Set up the Analysis Display, the interface where the ExerciseGesture statistics are seen
		AnalysisDisplay adp = new AnalysisDisplay();
		adp.setVisible(true);
		analysisManager = new AnalysisManager(adp);
		fsmm.addAcceptingStateListener(new AcceptingStateListener() {
			@Override
			public void handleAcceptingState(AcceptingStateEvent e) {
				analysisManager.handleAcceptingStateEvent(e);
			}
		});
		
		
		
	}
	
	private void addGestures() {
		// LeftToRight
		fsmm.add(new FiniteStateMachine(new Gesture_LeftToRight()));
		
		// RightToLeft
		fsmm.add(new FiniteStateMachine(new Gesture_RightToLeft()));
		
		// Upwards
		fsmm.add(new FiniteStateMachine(new Gesture_Upwards()));
		
		// Stop
		fsmm.add(new FiniteStateMachine(new Gesture_Stop()));

		// Start
		fsmm.add(new FiniteStateMachine(new Gesture_Start()));
				
//		// FAIL
//		// Wave
//		fsmm.add(new FiniteStateMachine(new Gesture_Wave()));
		
		// 1
		fsmm.add(new FiniteStateMachine(new Gesture_1()));
		
		// 2
		fsmm.add(new FiniteStateMachine(new Gesture_2()));
		
		// 3
		fsmm.add(new FiniteStateMachine(new Gesture_3()));
		
		// Circle
		fsmm.add(new FiniteStateMachine(new Gesture_Circle()));
		
		// Downwards
		fsmm.add(new FiniteStateMachine(new Gesture_Downwards()));
		
		// Vertical Jump
		fsmm.add(new FiniteStateMachine(new Gesture_VerticalJump()));
		
		// Squat
		fsmm.add(new FiniteStateMachine(new Gesture_Squat()));
		
		// Lateral Raise
		fsmm.add(new FiniteStateMachine(new Gesture_LateralRaise()));
		
		// Bicep Curl
		fsmm.add(new FiniteStateMachine(new Gesture_BicepCurl()));
		
		// Overhead Press
		fsmm.add(new FiniteStateMachine(new Gesture_OverheadPress()));
		
		// Punching
		fsmm.add(new FiniteStateMachine(new Gesture_Punching()));

		// Isometric Hold
		fsmm.add(new FiniteStateMachine(new Gesture_IsometricHold()));
		
		// Bench Press
		fsmm.add(new FiniteStateMachine(new Gesture_BenchPress()));
		
		// Deadlift
		fsmm.add(new FiniteStateMachine(new Gesture_Deadlift()));
		
		// Chest Press
		fsmm.add(new FiniteStateMachine(new Gesture_ChestPress()));
	}

}
