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
	
	public Autonomous() {
		pathfinderInputTable = NetworkTable.getTable("pathfinderInput");
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
		HashMap<String, Trajectory> map = (HashMap<String, Trajectory>) Path.deserializeTrajectoryMap(pathfinderOutputTable.getString("path", null));
		Trajectory trajectory = (Trajectory) map.get(trajectoryName);
		add(new ActionDriveByTrajectory(trajectory));
	}
		
	//WAYPOINTS
	public void initWaypoints() {
		pathfinderInputTable.putNumber("timeStep", Path.timeStep);
		pathfinderInputTable.putNumber("maxVel", Path.maxVel);
		pathfinderInputTable.putNumber("maxAccel", Path.maxAccel);
		pathfinderInputTable.putNumber("maxJerk", Path.maxJerk);
			
		Waypoint[] waypointArr = new Waypoint[2];
		waypointArr = new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(8.75, -3.75, Pathfinder.d2r(30))
		};
		
		Waypoint[] waypointArr2 = new Waypoint[3];
		waypointArr2 = new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(9.167, 0, 0),
				new Waypoint(12.5, 1.67, Pathfinder.d2r(90))
		};
		
		wayMap.put("2-1-1", waypointArr);
		wayMap.put("3-3-1 Switch", waypointArr2);
		
		pathfinderInputTable.putString("waypoints", Path.serializeWaypointMap(wayMap));
	}
	
	public String[][][] getAutoList() {
		
		//CONFIG 1
		String[][] config1 = {{}};
		
		String[][] config2 = {
				{"1-Exchange Zone", 
					"2-Exchange PCZone Exchange", 
					"3-Exchange PCZone Switch", 
					"4-Switch",
					"5-Switch Cube Switch",
					"6-Scale"},
				{"1-Exchange Portal",
					"2-Exchange PCZone Exchange",
					"3-Switch Autoline",
					"4-Switch PCZone Switch",
					"5-Autoline",
					"6-Scale",
					"7-Scale Switch Scale"},
				{"1-Scale",
					"2-Scale Cube"}
		};
		
		String[][] config3 = {
				{"1-Exchange Autoline",
					"2-Exchange PCZone Exchange",
					"3-Exchange PCZone Switch"},
				{"1-Switch",
					"2-Switch Go Around Switch",
					"3-Exchange Switch",
					"4-Exchange PCZone Exchange",
					"5-Switch Cube Scale"},
				{"1-Switch",
					"2-Switch Cube Switch",
					"3-Scale Cube Switch",
					"4-Scale",
					"5-Scale Cube Scale",
					"6-Switch PCZone Switch"}
		};
		
		//FIX CONFIG 4
		String[][] config4 = {
				{"1-Exchange",
					"2-Exchange PCZone Switch",
					"3-Autoline PCZone Switch FIX",
					"4-Autoline Switch Scale",
					"5-Switch FIX"},
				{"1-Switch",
					"2-Switch Toward-Scale",
					"3-Autoline"}
		};
		
		
		return new String[][][] {config1,config2,config3,config4};
	}
	
	
}
