package org.usfirst.frc.team303.robot;

import java.util.ArrayList;
import java.util.HashMap;
import org.usfirst.frc.team303.robot.action.Action;
import org.usfirst.frc.team303.robot.action.ActionDriveByTrajectory;
import org.usfirst.frc.team303.robot.action.ActionDriveStraightByEncoders;
import org.usfirst.frc.team303.robot.action.ActionIntakeGrip;
import org.usfirst.frc.team303.robot.action.ActionParallelAction;
import org.usfirst.frc.team303.robot.action.ActionTurnToAngle;
import org.usfirst.frc.team303.robot.action.ActionZero;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

@SuppressWarnings("deprecation")
public class Autonomous {

	NetworkTable pathfinderOutputTable = NetworkTable.getTable("pathfinderOutput");		
	NetworkTable pathfinderInputTable = NetworkTable.getTable("pathfinderInput");
	public ArrayList<Action> arr = new ArrayList<>();
	private int taskNum = 0;
	public Trajectory[] trajectoryArray;
	HashMap<String, Waypoint[]> wayMap = new HashMap<String, Waypoint[]>();
	HashMap<String, Trajectory> trajectoryMap = null;

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

	public ActionDriveByTrajectory getTrajectory(String trajectoryName, boolean isReversed) {
		if(trajectoryMap==null) {
			realizeTrajectories();
		}
		return new ActionDriveByTrajectory(trajectoryMap.get(trajectoryName), isReversed);
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
				new Waypoint(9, 9, Pathfinder.d2r(0)),
		};
		Waypoint[] rightRightScaleApproach = new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(17, 0, 0),
				new Waypoint(24, -4, Pathfinder.d2r(0)),
		};
		Waypoint[] rightRightSwitchRightScaleGrabCube1 = new Waypoint[] {
				new Waypoint(0, 0, Pathfinder.d2r(0)),
				new Waypoint(4, -4, Pathfinder.d2r(-90)),
		};
		Waypoint[] rightRightSwitchRightScaleGrabCube2 = new Waypoint[] {
				new Waypoint(20, -8, Pathfinder.d2r(-90)),
				new Waypoint(17, -5.5, Pathfinder.d2r(170)),
		};
	
	
		wayMap.put("forward", forward);
		wayMap.put("rightRightScaleApproach", rightRightScaleApproach);
		wayMap.put("rightRightSwitchRightScaleGrabCube1", rightRightSwitchRightScaleGrabCube1);
		wayMap.put("rightRightSwitchRightScaleGrabCube2", rightRightSwitchRightScaleGrabCube2);
		wayMap.put("centerLeftSwitch", centerLeftSwitch);
		wayMap.put("centerRightSwitch", centerRightSwitch);
		
		pathfinderInputTable.putString("waypoints", Path.serializeWaypointMap(wayMap));
	}
	
	public void assembleForward() {
		arr.add(getTrajectory("forward", false));
	}
	
	public void assembleRightRightSwitchRightScale() {
		arr.add(getTrajectory("rightRightScaleApproach", false));
			//score cube here
		arr.add(new ActionTurnToAngle(-90, false, 2, true, 0.5, false));
		arr.add(new ActionDriveStraightByEncoders(-3000, -0.75));
		arr.add(new ActionZero());
		arr.add(getTrajectory("rightRightSwitchRightScaleGrabCube1", false));
			//score cube here
		arr.add(new ActionTurnToAngle(90, false, 2, true, 0.5, true));
		arr.add(new ActionDriveStraightByEncoders(-3000, 0.6));
		arr.add(getTrajectory("rightRightSwitchRightScaleGrabCube2", false));
			//score cube here
	}
	
	public void assembleCenterSwitchLeft() {
		//TODO
		arr.add(getTrajectory("centerLeftSwitch", false));
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
