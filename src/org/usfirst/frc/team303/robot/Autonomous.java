package org.usfirst.frc.team303.robot;

import java.util.ArrayList;
import java.util.HashMap;
import org.usfirst.frc.team303.robot.action.Action;
import org.usfirst.frc.team303.robot.action.ActionDelayedAction;
import org.usfirst.frc.team303.robot.action.ActionDrive;
import org.usfirst.frc.team303.robot.action.ActionDriveByTrajectory;
import org.usfirst.frc.team303.robot.action.ActionDriveStraightByEncoders;
import org.usfirst.frc.team303.robot.action.ActionIntake;
import org.usfirst.frc.team303.robot.action.ActionIntakeGrip;
import org.usfirst.frc.team303.robot.action.ActionIntakeRotation;
import org.usfirst.frc.team303.robot.action.ActionLift;
import org.usfirst.frc.team303.robot.action.ActionParallelAction;
import org.usfirst.frc.team303.robot.action.ActionTurnToAngle;
import org.usfirst.frc.team303.robot.action.ActionWait;
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

	public ActionDriveByTrajectory getTrajectory(String trajectoryName, double turningConstant, boolean isReversed) {
		if(trajectoryMap==null) {
			realizeTrajectories();
		}
		return new ActionDriveByTrajectory(trajectoryMap.get(trajectoryName), turningConstant, isReversed);
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
				new Waypoint(8.5, -9, Pathfinder.d2r(0)),
		};
		Waypoint[] centerRightSwitch = new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(8.5, 8, Pathfinder.d2r(0)),
		};
		Waypoint[] rightRightScaleApproach = new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(15, 0, 0),
				new Waypoint(22.5, -6, Pathfinder.d2r(-25)),
		};
		Waypoint[] leftLeftScaleApproach = new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(15, 0, 0),
				new Waypoint(22.5, 3, Pathfinder.d2r(25)),
		};
		Waypoint[] leftRightScaleApproach = new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(13.5, 0, 0),
				new Waypoint(18.5, 10, Pathfinder.d2r(90)),
				new Waypoint(22, 16.5, 0)
		};
	
	
		wayMap.put("forward", forward);
		wayMap.put("leftLeftScaleApproach", leftLeftScaleApproach);
		wayMap.put("rightRightScaleApproach", rightRightScaleApproach);
		wayMap.put("centerLeftSwitch", centerLeftSwitch);
		wayMap.put("centerRightSwitch", centerRightSwitch);
		wayMap.put("leftRightScaleApproach", leftRightScaleApproach);

		pathfinderInputTable.putString("waypoints", Path.serializeWaypointMap(wayMap));
	}
	
	public void assembleForward() {
		arr.add(getTrajectory("forward", 0.01, false));
	}
	
	public void assembleLeftLeftScale() {
		arr.add(new ActionIntakeRotation(true));
		arr.add(makeSimpleParallelAction(getTrajectory("leftLeftScaleApproach", 0.01, false), new ActionDelayedAction(1, new ActionLift(70000))));
		arr.add(makeSimpleParallelAction(new ActionTurnToAngle(15, false, 4), new ActionLift(70000)));
		arr.add(makeSimpleParallelAction(new ActionWait(1), new ActionIntake(0.7, -0.7)));
		arr.add(new ActionIntakeRotation(false));
		backupFromScale();
	}
	
	public void assembleLeftRightScale() {
		arr.add(new ActionIntakeRotation(true));
		arr.add(getTrajectory("leftRightScaleApproach", 0.03, false));
		arr.add(new ActionTurnToAngle(0, false, 5));
		arr.add(makeSimpleParallelAction(new ActionDriveStraightByEncoders(1000, 0.5), new ActionLift(70000)));
	}
	
	public void backupFromScale() {
		ArrayList<Action> backupNonCon1 = new ArrayList<>();
		backupNonCon1.add(new ActionDrive(0.6, 0.6));
		backupNonCon1.add(new ActionIntake(0, 0));
		backupNonCon1.add(new ActionLift(70000));
		ArrayList<Action> backupCon1 = new ArrayList<>();
		backupCon1.add(new ActionWait(0.5));
		arr.add(new ActionParallelAction(backupCon1, backupNonCon1));
	
		ArrayList<Action> backupNonCon = new ArrayList<>();
		backupNonCon.add(new ActionDrive(0.6, 0.6));
		backupNonCon.add(new ActionIntake(0, 0));
		backupNonCon.add(new ActionLift(0));
		ArrayList<Action> backupCon = new ArrayList<>();
		backupCon.add(new ActionWait(1));
		arr.add(new ActionParallelAction(backupCon, backupNonCon));
	}
	
	public void assembleRightRightScale() {
		arr.add(new ActionIntakeRotation(true));
		arr.add(makeSimpleParallelAction(getTrajectory("rightRightScaleApproach", 0.01, false), new ActionDelayedAction(1, new ActionLift(70000))));
		arr.add(makeSimpleParallelAction(new ActionTurnToAngle(-15, false, 4), new ActionLift(70000)));
		arr.add(makeSimpleParallelAction(new ActionWait(1), new ActionIntake(0.7, -0.7)));
		arr.add(new ActionIntakeRotation(false));
		backupFromScale();
	}
	
//	public void assembleRightRightSwitchRightScale() {
//		arr.add(getTrajectory("rightRightScaleApproach", false));
//			//score cube here
//		arr.add(new ActionTurnToAngle(-90, false, 2, true, 0.5, false));
//		arr.add(new ActionDriveStraightByEncoders(-3000, -0.75));
//		arr.add(new ActionZero());
//		arr.add(getTrajectory("rightRightSwitchRightScaleGrabCube1", false));
//			//score cube here
//		arr.add(new ActionTurnToAngle(90, false, 2, true, 0.5, true));
//		arr.add(new ActionDriveStraightByEncoders(-3000, 0.6));
//		arr.add(getTrajectory("rightRightSwitchRightScaleGrabCube2", false));
//			//score cube here
//	}
	
	public void assembleCenterSwitchLeft() {
		System.out.println("running center switch left");
		arr.add(getTrajectory("centerLeftSwitch", 0.01, false));
		arr.add(new ActionTurnToAngle(0, false, 8));
		arr.add(new ActionWait(0.25));
		arr.add(makeSimpleParallelAction(new ActionWait(0.4), new ActionDrive()));
		arr.add(makeSimpleParallelAction(new ActionWait(1), new ActionIntake(0.7, -0.7)));
		arr.add(new ActionIntake(0, 0));
	}
	
	public void assembleCenterSwitchRight() {
		arr.add(getTrajectory("centerRightSwitch", 0.01, false));
		arr.add(new ActionTurnToAngle(0, false, 8)); 
		arr.add(new ActionWait(0.25));
		arr.add(makeSimpleParallelAction(new ActionWait(0.4), new ActionDrive()));
		arr.add(makeSimpleParallelAction(new ActionWait(1), new ActionIntake(0.7, -0.7)));
		arr.add(new ActionIntake(0, 0));
	}
	
	public ActionParallelAction makeSimpleParallelAction(Action con, Action nonCon) {
		ArrayList<Action> nonConAction = new ArrayList<Action>();
		nonConAction.add(nonCon);
		ArrayList<Action> conAction = new ArrayList<Action>();
		conAction.add(con);
		return new ActionParallelAction(conAction, nonConAction);

	}
}
