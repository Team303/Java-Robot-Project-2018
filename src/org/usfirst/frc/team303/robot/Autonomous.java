package org.usfirst.frc.team303.robot;

import java.util.ArrayList;
import java.util.HashMap;
import org.usfirst.frc.team303.robot.action.Action;
import org.usfirst.frc.team303.robot.action.ActionDriveByTrajectory;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

@SuppressWarnings("deprecation")
public class Autonomous {

	NetworkTable pathfinderOutputTable = NetworkTable.getTable("pathfinderOutput");		
	private ArrayList<Action> actionList = new ArrayList<>();
	private int count = 0;
	public Trajectory[] trajectoryArray;
	NetworkTable pathfinderInputTable;
	HashMap<String, Waypoint[]> wayMap = new HashMap<String, Waypoint[]>();
	HashMap<String, Trajectory> trajectoryMap = new HashMap<String, Trajectory>();

	
	public Autonomous() {
		pathfinderInputTable = NetworkTable.getTable("pathfinderInput");
	}

	/**
	 * please
	 */
	public void realizeTrajectories() {
		trajectoryMap = Path.deserializeTrajectoryMap(pathfinderOutputTable.getString("path", null));
	}
	
	public void add(Action action) {
		actionList.add(action);
	}

	public void run() {
		if (actionList.size() > count) {
			Action selectedAction = actionList.get(count);
			selectedAction.run();
			if (selectedAction.isFinished()) {
				count++;
			}
		}
	}

	public void driveTrajectory(String trajectoryName){ //TODO change auto name to match trajectory goal
		Trajectory trajectory = (Trajectory) trajectoryMap.get(trajectoryName);
		add(new ActionDriveByTrajectory(trajectory));
	}
		
	//WAYPOINTS
	public void initWaypoints() {
		pathfinderInputTable.putNumber("timeStep", Path.timeStep);
		pathfinderInputTable.putNumber("maxVel", Path.maxVel);
		pathfinderInputTable.putNumber("maxAccel", Path.maxAccel);
		pathfinderInputTable.putNumber("maxJerk", Path.maxJerk);
			
		
		Waypoint[] waypointArr = {
				new Waypoint(0, 0, 0),
				new Waypoint(8.75, -3.75, Pathfinder.d2r(0))
		};
		
		Waypoint[] waypointArr2 =  {
				new Waypoint(0, 0, 0),
				new Waypoint(9.167, 0, 0),
				new Waypoint(12.5, 1.67, Pathfinder.d2r(90))
		};
		
		wayMap.put("2-3-1", waypointArr);
		wayMap.put("3-3-1", waypointArr2);
		
		pathfinderInputTable.putString("waypoints", Path.serializeWaypointMap(wayMap));
	}
	
	public String[][] getAutoList() {
		
		//CONFIG 1
		String[] config1 = {};
		
		String[] config2 =
				{"L-1-Exchange Zone", 
					"L-2-Exchange PCZone Exchange", 
					"L-3-Exchange PCZone Switch", 
					"L-4-Switch",
					"L-5-Switch Cube Switch",
					"L-6-Scale",
					"C-1-Exchange Portal",
					"C-2-Exchange PCZone Exchange",
					"C-3-Switch Autoline",
					"C-4-Switch PCZone Switch",
					"C-5-Autoline",
					"C-6-Scale",
					"C-7-Scale Switch Scale",
					"R-1-Scale",
					"R-2-Scale Cube"};
		
		String[] config3 = 
				{"L-1-Exchange Autoline",
					"L-2-Exchange PCZone Exchange",
					"L-3-Exchange PCZone Switch",
					"C-1-Switch",
					"C-2-Switch Go Around Switch",
					"C-3-Exchange Switch",
					"C-4-Exchange PCZone Exchange",
					"C-5-Switch Cube Scale",
					"R-1-Switch",
					"R-2-Switch Cube Switch",
					"R-3-Scale Cube Switch",
					"R-4-Scale",
					"R-5-Scale Cube Scale",
					"R-6-Switch PCZone Switch"};
		
		//FIX CONFIG 4
		String[] config4 = 
				{"L-1-Exchange",
					"L-2-Exchange PCZone Switch",
					"L-3-Autoline PCZone Switch FIX",
					"L-4-Autoline Switch Scale",
					"L-5-Switch FIX",
					"C-1-Switch",
					"C-2-Switch Toward-Scale",
					"C-3-Autoline"};
		
		
		return new String[][] {config1,config2,config3,config4};
	}
	
	
}
