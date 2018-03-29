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
				new Waypoint(9, -9, Pathfinder.d2r(0)),
		};
		Waypoint[] centerRightSwitch = new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(11, 8, Pathfinder.d2r(0)),
		};
		Waypoint[] rightRightScaleApproach = new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(15, 0, 0),
				new Waypoint(22, -2.5, Pathfinder.d2r(-35)),
	};
		Waypoint[] leftLeftScaleApproach = new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(15, 0, 0),
				new Waypoint(22.5, 3, Pathfinder.d2r(35)),
		};
		Waypoint[] leftRightScaleApproach = new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(13, 0, 0),
				new Waypoint(16.5, 10.5, Pathfinder.d2r(90)), //10
				new Waypoint(20.5, 16.5, 0) //16
		};
		Waypoint[] rightLeftScaleApproach = new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(13, 0, 0),
				new Waypoint(16.5, -10.5, Pathfinder.d2r(-90)), //10
				new Waypoint(20.5, -16.5, 0) //16
		};
		Waypoint[] leftLeftSwitch = new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(9, -0.5, Pathfinder.d2r(-10)),
				new Waypoint(15, 2.7, Pathfinder.d2r(90))
		};		
	
		wayMap.put("forward", forward);
		wayMap.put("leftLeftScaleApproach", leftLeftScaleApproach);
		wayMap.put("rightRightScaleApproach", rightRightScaleApproach);
		wayMap.put("centerLeftSwitch", centerLeftSwitch);
		wayMap.put("centerRightSwitch", centerRightSwitch);
		wayMap.put("leftRightScaleApproach", leftRightScaleApproach);
		wayMap.put("rightLeftScaleApproach", rightLeftScaleApproach);
		wayMap.put("leftLeftSwitch", leftLeftSwitch);
		
		pathfinderInputTable.putString("waypoints", Path.serializeWaypointMap(wayMap));
	}
	
	public void assembleTest() {
	//	arr.add(new ActionIntakeRotation(true));
	//	arr.add(makeSimpleParallelAction(new ActionWait(3), new ActionLift(75500)));
	//	arr.add(makeSimpleParallelAction(new ActionWait(5), new ActionLift(0)));
		arr.add(new ActionDriveStraightByEncoders(5000, -0.7, 15));
	}
	
	public void assembleForward() {
		arr.add(getTrajectory("forward", 0.01, false));
	}
	
	public void assembleLeftLeftSwitch() {
		arr.add(getTrajectory("leftLeftSwitch", 0.02, false));
		arr.add(makeSimpleParallelAction(new ActionTurnToAngle(90, false, 8), new ActionLift(25000)));
		
		ArrayList<Action> switchLiftNonCon = new ArrayList<>();
		switchLiftNonCon.add(new ActionDrive(-0.6, -0.6));
		switchLiftNonCon.add(new ActionLift(25000));
		ArrayList<Action> switchLiftCon = new ArrayList<>();
		switchLiftCon.add(new ActionWait(0.5));
		arr.add(new ActionParallelAction(switchLiftCon, switchLiftNonCon));
		arr.add(new ActionIntakeRotation(true));
		
		ArrayList<Action> switchLiftNonCon2 = new ArrayList<>();
		switchLiftNonCon2.add(new ActionIntake(0.7, -0.7));
		switchLiftNonCon2.add(new ActionLift(25000));
		ArrayList<Action> switchLiftCon2 = new ArrayList<>();
		switchLiftCon2.add(new ActionWait(1));
		arr.add(new ActionParallelAction(switchLiftCon2, switchLiftNonCon2));
		arr.add(new ActionIntake(0, 0));
		arr.add(new ActionLift(0));
		arr.add(makeSimpleParallelAction(new ActionWait(1), new ActionDrive(0.6, 0.6)));
		
	}
	
	public void assembleLeftLeftScale() {
		arr.add(new ActionIntakeRotation(true));
		arr.add(makeSimpleParallelAction(getTrajectory("leftLeftScaleApproach", 0.01, false), new ActionDelayedAction(1, new ActionLift(70000))));
		//arr.add(makeSimpleParallelAction(new ActionTurnToAngle(15, false, 8), new ActionLift(70000)));
		//arr.add(makeSimpleParallelAction(new ActionWait(1), new ActionIntake(0.4, -0.4)));
		arr.add(makeSimpleParallelAction(new ActionWait(0.1), new ActionIntake(0.5,-0.5)));
		arr.add(new ActionIntakeRotation(false));
		arr.add(new ActionIntakeGrip(true));
		
		backupFromScale();
		
		arr.add(new ActionIntakeRotation(true));
		arr.add(new ActionParallelAction(new ActionTurnToAngle(130, false, 2.5f), new ActionLift(0)));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(8000, -0.7, 15), new ActionIntake(-0.7,0.7), new ActionLift(0)));
		arr.add(new ActionIntakeGrip(false));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(6000, 0.9, 15), new ActionLift(0)));
		//arr.add(new ActionIntake(0,0));
		arr.add(new ActionParallelAction(new ActionTurnToAngle(40, false, 2.5f), new ActionDelayedAction(0.5, new ActionLift(70000))));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(5000, -0.8, 15), new ActionLift(70000)));
		arr.add(new ActionParallelAction(new ActionWait(0.5), new ActionLift(70000), new ActionIntake(0.7,-0.7)));
		arr.add(new ActionIntake(0,0));
		backupFromScale();
		
		arr.add(makeSimpleParallelAction(new ActionWait(15), new ActionLift(0)));
		
	}
	
	public void assembleLeftRightScale() {
		arr.add(new ActionIntakeRotation(true));
		arr.add(getTrajectory("leftRightScaleApproach", 0.02, false));
		arr.add(new ActionTurnToAngle(0, false, 5));
		arr.add(makeSimpleParallelAction(new ActionWait(0.5), new ActionDrive(0.75, 0.75)));
		arr.add(makeSimpleParallelAction(new ActionWait(2), new ActionLift(75500)));
		arr.add(makeSimpleParallelAction(new ActionWait(0.75), new ActionDrive(-0.65, -0.65)));
		arr.add(makeSimpleParallelAction(new ActionWait(1), new ActionIntakeGrip(true)));
		backupFromScale();
		arr.add(makeSimpleParallelAction(new ActionWait(15), new ActionLift(0)));
	}
	
	
	
	public void assembleRightLeftScale() {
		arr.add(new ActionIntakeRotation(true));
		arr.add(getTrajectory("rightLeftScaleApproach", 0.02, false));
		arr.add(new ActionTurnToAngle(0, false, 5));
		arr.add(makeSimpleParallelAction(new ActionWait(0.5), new ActionDrive(0.75, 0.75)));
		arr.add(makeSimpleParallelAction(new ActionWait(2), new ActionLift(75500)));
		arr.add(makeSimpleParallelAction(new ActionWait(0.55), new ActionDrive(-0.65, -0.65)));
		arr.add(makeSimpleParallelAction(new ActionWait(1), new ActionIntakeGrip(true)));
		backupFromScale();
		arr.add(makeSimpleParallelAction(new ActionWait(15), new ActionLift(0)));
	}
	
	public void backupFromScale() {	
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(500, 0.7, 15), new ActionIntake(0,0), new ActionLift(70000)));	
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(5500, 0.55, 15), new ActionIntake(0,0), new ActionLift(0)));	
	}
	
	public void assembleRightRightScale() {
		arr.add(new ActionIntakeRotation(true));
		arr.add(makeSimpleParallelAction(getTrajectory("rightRightScaleApproach", 0.01, false), new ActionDelayedAction(1, new ActionLift(70000))));
		//arr.add(makeSimpleParallelAction(new ActionTurnToAngle(-15, false, 8), new ActionLift(70000)));
		arr.add(makeSimpleParallelAction(new ActionWait(1), new ActionIntake(0.5, -0.5)));
		arr.add(new ActionIntakeRotation(false));
		backupFromScale();
		
		arr.add(new ActionParallelAction(new ActionWait(1), new ActionDrive(0.5, 0.5), new ActionLift(10000)));
	}
	
	public void assembleCenterSwitchLeft() {
		arr.add(new ActionIntakeRotation(true));
		arr.add(makeSimpleParallelAction(getTrajectory("centerLeftSwitch", 0.01, false), new ActionDelayedAction(1, new ActionLift(25000))));
		arr.add(makeSimpleParallelAction(new ActionTurnToAngle(0, false, 3), new ActionLift(25000)));
		arr.add(makeSimpleParallelAction(new ActionWait(0.5), new ActionDrive()));
		arr.add(makeSimpleParallelAction(new ActionWait(1), new ActionIntake(0.7, -0.7)));
		arr.add(new ActionIntake(0, 0));
	}
	
	public void assembleCenterSwitchRight() {
		arr.add(new ActionIntakeRotation(true));
		arr.add(makeSimpleParallelAction(getTrajectory("centerRightSwitch", 0.01, false), new ActionDelayedAction(1, new ActionLift(25000))));
		arr.add(makeSimpleParallelAction(new ActionTurnToAngle(0, false, 8), new ActionLift(25000)));
		arr.add(new ActionParallelAction(new ActionWait(0.25), new ActionIntake(0.7, -0.7), new ActionLift(25000)));
		arr.add(new ActionIntake(0, 0));
		
		arr.add(new ActionIntakeGrip(true));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(2100, 0.7, 15), new ActionLift(0)));
		arr.add(new ActionParallelAction(new ActionTurnToAngle(-90, false, 2.5f), new ActionLift(0)));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(9000, -0.7, 15), new ActionLift(0), new ActionIntake(-0.7, 0.7)));
		arr.add(new ActionIntakeGrip(false));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(6000, 0.7, 15), new ActionLift(0)));
		arr.add(new ActionParallelAction(new ActionTurnToAngle(0, false, 2.5f), new ActionLift(25000)));
		arr.add(new ActionParallelAction(new ActionWait(1), new ActionDrive(-0.8, -0.8), new ActionLift(25000)));
		arr.add(new ActionParallelAction(new ActionWait(0.25), new ActionIntake(0.7, -0.7), new ActionLift(25000)));

	}
	
	public ActionParallelAction makeSimpleParallelAction(Action con, Action nonCon) {
		ArrayList<Action> nonConAction = new ArrayList<Action>();
		nonConAction.add(nonCon);
		ArrayList<Action> conAction = new ArrayList<Action>();
		conAction.add(con);
		return new ActionParallelAction(conAction, nonConAction);

	}
}
