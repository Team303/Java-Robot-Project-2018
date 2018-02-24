package org.usfirst.frc.team303.robot;

import java.util.ArrayList;
import java.util.HashMap;
import org.usfirst.frc.team303.robot.action.Action;
import org.usfirst.frc.team303.robot.action.ActionDriveByTrajectory;
import org.usfirst.frc.team303.robot.action.ActionIntakeGrip;
import org.usfirst.frc.team303.robot.action.ActionParallelAction;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

@SuppressWarnings("deprecation")
public class Autonomous {

	NetworkTable pathfinderOutputTable = NetworkTable.getTable("pathfinderOutput");		
	NetworkTable pathfinderInputTable = NetworkTable.getTable("pathfinderInput");
	private ArrayList<Action> arr = new ArrayList<>();
	private int taskNum = 0;
	public Trajectory[] trajectoryArray;
	HashMap<String, Waypoint[]> wayMap = new HashMap<String, Waypoint[]>();
	HashMap<String, Trajectory> trajectoryMap = new HashMap<String, Trajectory>();

	/**
	 * please
	 */
	public void realizeTrajectories() {
		trajectoryMap = Path.deserializeTrajectoryMap(pathfinderOutputTable.getString("path", null));
	}

	public void run() {
		if (arr.size() >= taskNum) {
			arr.get(taskNum).run();
			if (arr.get(taskNum).isFinished()) {
				taskNum++;
			}
		}

		SmartDashboard.putNumber("taskNum", taskNum);
	}

	public ActionDriveByTrajectory getTrajectory(String trajectoryName) {
		return new ActionDriveByTrajectory(trajectoryMap.get(trajectoryName));
	}
		
	//WAYPOINTS
	public void initWaypoints() {
		pathfinderInputTable.putNumber("timeStep", Path.timeStep);
		pathfinderInputTable.putNumber("maxVel", Path.maxVel);
		pathfinderInputTable.putNumber("maxAccel", Path.maxAccel);
		pathfinderInputTable.putNumber("maxJerk", Path.maxJerk);
		
		Waypoint[] forward = new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(20, 0, Pathfinder.d2r(0)),
		};
		Waypoint[] centerLeftSwitch = new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(9, -6, Pathfinder.d2r(0)),
		};
		Waypoint[] centerRightSwitch = new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(9, 4, Pathfinder.d2r(0)),

		};
		wayMap.put("forward", forward);
		wayMap.put("centerLeftSwitch", centerLeftSwitch);
		wayMap.put("centerRightSwitch", centerRightSwitch);
		
		pathfinderInputTable.putString("waypoints", Path.serializeWaypointMap(wayMap));
	}
	
	public void assembleForward() {
		arr.add(getTrajectory("forward"));
	}
	
	public void assembleCenterSwitchLeft() {
		//TODO
		arr.add(getTrajectory("centerLeftSwitch"));
		arr.add(new ActionIntakeGrip(false));
	}
	
	public void assembleCenterSwitchRight() {
		//TODO
	}
	
	public ActionParallelAction makeSimpleParallelAction(Action con, Action nonCon) {
		ArrayList<Action> nonConAction = new ArrayList<Action>();
		nonConAction.add(nonCon);
		ArrayList<Action> conAction = new ArrayList<Action>();
		conAction.add(con);
		return new ActionParallelAction(conAction, nonConAction);

	}
}
