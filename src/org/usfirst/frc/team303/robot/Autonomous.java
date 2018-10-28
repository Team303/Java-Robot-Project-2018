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
				new Waypoint(10, 5, Pathfinder.d2r(0)),
		};
		Waypoint[] centerLeftSwitch = new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(5, -9, 0),
				new Waypoint(5.5, -9, Pathfinder.d2r(0)), //8, -9, 0
		};
		Waypoint[] centerRightSwitch = new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(5, 8, 0),
				new Waypoint(5.5, 8, Pathfinder.d2r(0)),
		};
		Waypoint[] rightRightScaleApproach = new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(15, 0, 0),
				new Waypoint(22, -2.5, Pathfinder.d2r(-40)),
		};
		Waypoint[] leftLeftScaleApproach = new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(15, 0, 0),
				new Waypoint(22, 2.5, Pathfinder.d2r(35)), //22.5, 2.5, 35
		};

		Waypoint[] leftRightScaleApproach = new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(13, 0, 0),
				new Waypoint(16.5, 10.5, Pathfinder.d2r(90)), //10
				new Waypoint(20.5, 16.5, 0) //16
		};
		
		Waypoint[] newLeftRightScaleApproach = new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(13, 0, 0),
				new Waypoint(16.5, 10.5, Pathfinder.d2r(90)), //10
				new Waypoint(15.5, 14, Pathfinder.d2r(90)) //16
		};
		
		
		Waypoint[] rightLeftScaleApproach = new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(13, 0, 0),
				new Waypoint(16.5, -10.5, Pathfinder.d2r(-90)), //10
				new Waypoint(20.5, -16.5, 0) //16
		};
		
		Waypoint[] newRightLeftScaleApproach = new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(13, 0, 0),
				new Waypoint(16, -10.5, Pathfinder.d2r(-90)), //10
				new Waypoint(15.5, -13,  Pathfinder.d2r(-90)) //16
		};
		
		
		Waypoint[] leftLeftSwitch = new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(9, -0.5, Pathfinder.d2r(-10)),
				new Waypoint(15, 2.7, Pathfinder.d2r(90))
		};
		
		Waypoint[] rightRightSwitch = new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(9, 0.5, Pathfinder.d2r(10)),
				new Waypoint(15, -2.7, Pathfinder.d2r(-90))
		};
		
		Waypoint[] leftRightScaleHalf = new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(13, 0, 0),
				new Waypoint(16.5, 10.5, Pathfinder.d2r(90)), 
		};	
		
		Waypoint[] rightLeftScaleHalf = new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(13, 0.5, 0),
				new Waypoint(16.5, -10.5, Pathfinder.d2r(-90)), 
		};
		
		Waypoint[] rightRightQuickScale = new Waypoint[] {
				new Waypoint(0,0,0),
				new Waypoint(24, 1, Pathfinder.d2r(0)) //-20
		};
		Waypoint[] leftLeftQuickScale = new Waypoint[] {
				new Waypoint(0,0,0),
				new Waypoint(24,-1,Pathfinder.d2r(0))
		};
		
		wayMap.put("leftRightScaleHalf", leftRightScaleHalf);
		wayMap.put("rightLeftScaleHalf", rightLeftScaleHalf);
		wayMap.put("forward", forward);
		wayMap.put("leftLeftScaleApproach", leftLeftScaleApproach);
		wayMap.put("rightRightScaleApproach", rightRightScaleApproach);
		wayMap.put("centerLeftSwitch", centerLeftSwitch);
		wayMap.put("centerRightSwitch", centerRightSwitch);
		wayMap.put("leftRightScaleApproach", leftRightScaleApproach);
		wayMap.put("newLeftRightScaleApproach", newLeftRightScaleApproach);
		wayMap.put("newRightLeftScaleApproach", newRightLeftScaleApproach);
		wayMap.put("rightLeftScaleApproach", rightLeftScaleApproach);
		wayMap.put("leftLeftSwitch", leftLeftSwitch);
		wayMap.put("rightRightSwitch", rightRightSwitch);
		wayMap.put("rightRightQuickScale", rightRightQuickScale);
		wayMap.put("leftLeftQuickScale", leftLeftQuickScale);
		
		pathfinderInputTable.putString("waypoints", Path.serializeWaypointMap(wayMap));
	}
	
	public void assembleTest() {
	//	arr.add(new ActionIntakeRotation(true));
	//	arr.add(makeSimpleParallelAction(new ActionWait(3), new ActionLift(75500)));
	//	arr.add(makeSimpleParallelAction(new ActionWait(5), new ActionLift(0)));
		arr.add(new ActionDriveStraightByEncoders(5000, -0.7, 15));
		arr.add(new ActionDriveStraightByEncoders(5000, 0.7, 15));
		arr.add(new ActionDriveStraightByEncoders(5000, -0.7, 15));
		arr.add(new ActionDriveStraightByEncoders(5000, 0.7, 15));
		arr.add(new ActionDriveStraightByEncoders(5000, -0.7, 15));
		arr.add(new ActionDriveStraightByEncoders(5000, 0.7, 15));
	}
	
	public void assembleForward() {
		arr.add(getTrajectory("forward", 0.01, false));
	}
	
	public void assembleLeftLeftSwitch() {
		arr.add(new ActionIntakeRotation(true));
		arr.add(new ActionParallelAction(getTrajectory("leftLeftSwitch", 0.02, false), new ActionDelayedAction(1, new ActionLift(30000))));
		arr.add(new ActionParallelAction(new ActionWait(1), new ActionIntake(0.7,-0.7), new ActionLift(30000)));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(2000, 0.6, 1.5), new ActionLift(30000)));
		arr.add(new ActionIntake(0,0));
		arr.add(makeSimpleParallelAction(new ActionWait(15), new ActionLift(0)));

		//arr.add(makeSimpleParallelAction(new ActionTurnToAngle(90, false, 8), new ActionLift(25000)));	
	}
	
	public void assembleRightRightQuickScale() {
		arr.add(new ActionIntakeRotation(true));
		arr.add(new ActionParallelAction(getTrajectory("rightRightQuickScale", 0.01, false), new ActionDelayedAction(4, new ActionLift(30000))));
		arr.add(new ActionParallelAction(new ActionTurnToAngle(-90, false, 2.0f), new ActionLift(70000)));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(4200, -0.6, 2.5), new ActionLift(70000)));
		arr.add(new ActionIntakeGrip(true));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(3500, 0.6, 2.5), new ActionLift(70000)));
		arr.add(new ActionParallelAction(new ActionWait(15), new ActionLift(0)));
		//arr.add(new ActionParallelAction(new ActionIntakeGrip(true)))
	}
	
	public void assembleLeftLeftQuickScale() {
		arr.add(new ActionIntakeRotation(true));
		arr.add(new ActionParallelAction(getTrajectory("leftLeftQuickScale", 0.01, false), new ActionDelayedAction(4, new ActionLift(30000))));
		arr.add(new ActionParallelAction(new ActionTurnToAngle(90, false, 2.0f), new ActionLift(70000)));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(3500, -0.6, 2.5), new ActionLift(70000)));
		arr.add(new ActionIntakeGrip(true));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(3500, 0.6, 2.5), new ActionLift(70000)));
		arr.add(new ActionParallelAction(new ActionWait(15), new ActionLift(0)));
		//arr.add(new ActionParallelAction(new ActionIntakeGrip(true)))
	}
	
	public void newLeftRightScale() {
		arr.add(new ActionIntakeRotation(true));
		arr.add(new ActionParallelAction(getTrajectory("newLeftRightScaleApproach", 0.02, false), new ActionDelayedAction(6, new ActionLift(35500))));
		arr.add(new ActionParallelAction(new ActionTurnToAngle(-10, false, 5), new ActionLift(75500)));
		arr.add(new ActionParallelAction(new ActionWait(0.75), new ActionDrive(-0.65, -0.65), new ActionLift(75500)));
		arr.add(new ActionParallelAction(new ActionWait(1), new ActionIntake(0.35, -0.35), new ActionLift(75500)));
		arr.add(new ActionIntakeGrip(true));
		backupFromScale();
		arr.add(makeSimpleParallelAction(new ActionWait(15), new ActionLift(0)));
	}
	
	public void newRightLeftScale() {
		arr.add(new ActionIntakeRotation(true));
		arr.add(new ActionParallelAction(getTrajectory("newRightLeftScaleApproach", 0.02, false), new ActionDelayedAction(6, new ActionLift(35500))));
		arr.add(new ActionParallelAction(new ActionTurnToAngle(0, false, 5), new ActionLift(75500)));
		arr.add(new ActionParallelAction(new ActionWait(0.75), new ActionDrive(-0.65, -0.65), new ActionLift(75500)));
		arr.add(new ActionParallelAction(new ActionWait(1), new ActionIntake(0.35, -0.35), new ActionLift(75500)));
		arr.add(new ActionIntakeGrip(true));
		backupFromScale();
//		arr.add(new ActionIntakeGrip(true));
//		arr.add(new ActionIntakeRotation(true));
//		arr.add(new ActionParallelAction(new ActionTurnToAngle(145, false, 2.5f), new ActionLift(0)));
//		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(5800, -0.7, 1.5), new ActionIntake(-0.8,0.8), new ActionLift(0)));
//		arr.add(new ActionIntakeGrip(false));
//		arr.add(new ActionWait(0.25));
		
		arr.add(makeSimpleParallelAction(new ActionWait(15), new ActionLift(0)));
	}
	
	public void assembleLeftRightScaleHalf() {
		arr.add(getTrajectory("leftRightScaleHalf", 0.02, false));
	}
	
	public void assembleRightLeftScaleHalf() {
		arr.add(getTrajectory("rightLeftScaleHalf", 0.02, false));
	}	
	
	
	public void assembleRightLeftScaleHalfSwitch() {
		arr.add(new ActionIntakeRotation(true));
		arr.add(new ActionParallelAction(getTrajectory("rightRightSwitch", 0.02, false), new ActionDelayedAction(1, new ActionLift(27000))));
		arr.add(new ActionParallelAction(new ActionWait(0.5), new ActionIntake(0.7,-0.7), new ActionLift(27000)));
		arr.add(new ActionIntake(0,0));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(1000, 0.6, 1.5f), new ActionLift(0)));		
		arr.add(new ActionParallelAction(new ActionTurnToAngle(0, false, 3f), new ActionLift(0)));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(3000, -0.7, 1.5f), new ActionLift(0)));		
		arr.add(new ActionParallelAction(new ActionTurnToAngle(90, false, 3f), new ActionLift(0)));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(15000, -0.7, 1.5f), new ActionLift(0)));		
		arr.add(makeSimpleParallelAction(new ActionWait(15), new ActionLift(0)));
	}	
	
	public void assembleLeftRightScaleHalfSwitch() {
		arr.add(new ActionIntakeRotation(true));
		arr.add(new ActionParallelAction(getTrajectory("leftLeftSwitch", 0.02, false), new ActionDelayedAction(1, new ActionLift(30000))));
		arr.add(new ActionParallelAction(new ActionWait(1.5), new ActionIntake(0.7,-0.7), new ActionLift(30000)));
		arr.add(new ActionIntake(0,0));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(2000, 0.6, 3.5), new ActionLift(0)));		
		arr.add(new ActionParallelAction(new ActionTurnToAngle(0, false, 2.5f), new ActionLift(0)));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(7000, -0.7, 5.5), new ActionLift(0)));		
		arr.add(new ActionParallelAction(new ActionTurnToAngle(90, false, 2.5f), new ActionLift(0)));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(15000, -0.7, 10), new ActionLift(0)));		
		arr.add(makeSimpleParallelAction(new ActionWait(15), new ActionLift(0)));
	}	
	
	
	public void assembleRightRightSwitch() {
		arr.add(new ActionIntakeRotation(true));
		arr.add(new ActionParallelAction(getTrajectory("rightRightSwitch", 0.02, false), new ActionDelayedAction(1, new ActionLift(27000))));
		arr.add(new ActionParallelAction(new ActionWait(0.5), new ActionIntake(0.7,-0.7), new ActionLift(27000)));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(2000, 0.6, 1.5f), new ActionLift(0)));
		arr.add(new ActionIntake(0,0));
		arr.add(makeSimpleParallelAction(new ActionWait(15), new ActionLift(0)));

		//arr.add(makeSimpleParallelAction(new ActionTurnToAngle(90, false, 8), new ActionLift(25000)));
	}
	
	
	public void assembleLeftLeftSwitchScale() {
		arr.add(new ActionIntakeRotation(true));
		arr.add(makeSimpleParallelAction(getTrajectory("leftLeftScaleApproach", 0.01, false), new ActionDelayedAction(1, new ActionLift(70000))));
		//arr.add(makeSimpleParallelAction(new ActionTurnToAngle(15, false, 8), new ActionLift(70000)));
		//arr.add(makeSimpleParallelAction(new ActionWait(1), new ActionIntake(0.4, -0.4)));
		arr.add(makeSimpleParallelAction(new ActionWait(0.5), new ActionIntake(0.5,-0.5))); 
		arr.add(new ActionIntakeRotation(false));
		
		backupFromScale();
		arr.add(new ActionIntakeGrip(true));
		arr.add(new ActionIntakeRotation(true));
		arr.add(new ActionParallelAction(new ActionTurnToAngle(145, false, 2.5f), new ActionLift(0)));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(5800, -0.7, 1.5), new ActionIntake(-0.8,0.8), new ActionLift(0)));
		arr.add(new ActionIntakeGrip(false));
		arr.add(new ActionWait(0.25));
		
		//Puts cube in switch
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(3000, 0.8, 2.5), new ActionLift(30000)));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(5000, -0.7, 2.5), new ActionLift(30000)));
		arr.add(new ActionParallelAction(new ActionWait(1.5), new ActionLift(30000), new ActionDelayedAction(0.5, new ActionIntake(0.7,-0.7))));
		
		//backup
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(5000, 0.55, 15), new ActionIntake(0,0), new ActionLift(0)));	

		
		arr.add(makeSimpleParallelAction(new ActionWait(15), new ActionLift(0)));

	/*	//Turn to get third cube
		arr.add(new ActionIntakeRotation(true));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(2000, 0.8, 15), new ActionLift(0)));
		arr.add(new ActionParallelAction(new ActionTurnToAngle(90, false, 2.5f), new ActionLift(0)));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(5000, -0.7, 15), new ActionIntake(-0.7,0.7), new ActionLift(0)));
		arr.add(new ActionIntakeGrip(false));
		arr.add(makeSimpleParallelAction(new ActionWait(15), new ActionLift(0)));	*/	
	}
	
	public void assembleRightRightSwitchScale() {
		
		//Follow trajectory and raise the lift
		arr.add(new ActionIntakeRotation(true));
		arr.add(makeSimpleParallelAction(getTrajectory("rightRightScaleApproach", 0.01, false), new ActionDelayedAction(1, new ActionLift(76000))));
		//Outtake the first cube into the scale
		arr.add(new ActionParallelAction(new ActionWait(1), new ActionIntake(0.3, -0.3), new ActionLift(76000)));
		arr.add(new ActionIntakeGrip(true));
		arr.add(new ActionParallelAction(new ActionWait(0.1), new ActionIntakeRotation(false)));

		//Backup from the scale
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(500, 0.7, 4), new ActionIntake(0,0), new ActionLift(70000)));	
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(5500, 0.55, 4), new ActionIntake(0,0), new ActionLift(0)));	

		//Turn to face toward the second cube
		arr.add(new ActionIntakeRotation(true));
		arr.add(new ActionParallelAction(new ActionTurnToAngle(-147, false, 1.2f), new ActionLift(0)));
		
		//Grab the second cube
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(7000, -0.7, 1.5), new ActionIntake(-0.7,0.7), new ActionLift(0)));
		arr.add(new ActionIntakeGrip(false));
		
		//Puts cube in switch
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(3000, 0.8, 2.5), new ActionLift(30000)));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(5000, -0.7, 2.5), new ActionLift(30000)));
		arr.add(new ActionParallelAction(new ActionWait(1.5), new ActionLift(30000), new ActionDelayedAction(0.5, new ActionIntake(0.7,-0.7))));

		//backup
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(5000, 0.55, 15), new ActionIntake(0,0), new ActionLift(0)));
		arr.add(makeSimpleParallelAction(new ActionWait(15), new ActionLift(0)));
		
		/*//Turn to get third cube
		arr.add(new ActionIntakeRotation(true));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(2000, 0.8, 15), new ActionLift(0)));
		arr.add(new ActionParallelAction(new ActionTurnToAngle(-90, false, 2.5f), new ActionLift(0)));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(5000, -0.7, 15), new ActionIntake(-0.7,0.7), new ActionLift(0)));
		arr.add(new ActionIntakeGrip(false));*/


		//arr.add(makeSimpleParallelAction(new ActionWait(15), new ActionLift(0)));	
		//arr.add(makeSimpleParallelAction(new ActionTurnToAngle(-15, false, 8), new ActionLift(70000)));

	}
	
	public void assembleLeftLeftScale() {
		arr.add(new ActionIntakeRotation(true));
		arr.add(makeSimpleParallelAction(getTrajectory("leftLeftScaleApproach", 0.01, false), new ActionDelayedAction(1, new ActionLift(70000))));
		//arr.add(makeSimpleParallelAction(new ActionTurnToAngle(15, false, 8), new ActionLift(70000)));
		//arr.add(makeSimpleParallelAction(new ActionWait(1), new ActionIntake(0.4, -0.4)));
		arr.add(makeSimpleParallelAction(new ActionWait(0.5), new ActionIntake(0.35,-0.35)));
		arr.add(new ActionIntakeRotation(false));
		
		backupFromScale();
		arr.add(new ActionIntakeGrip(true));
		arr.add(new ActionIntakeRotation(true));
		arr.add(new ActionParallelAction(new ActionTurnToAngle(145, false, 2.5f), new ActionLift(0)));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(5800, -0.7, 1.5), new ActionIntake(-0.8,0.8), new ActionLift(0)));
		arr.add(new ActionIntakeGrip(false));
		arr.add(new ActionWait(0.25));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(5500, 0.9, 3), new ActionLift(30000), new ActionIntake(-0.7,0.7)));
		//arr.add(new ActionIntake(0,0));
		arr.add(new ActionParallelAction(new ActionTurnToAngle(35, false, 2.5f), new ActionLift(70000), new ActionIntake(-0.55,0.55)));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(3850, -0.7, 15), new ActionLift(70000), new ActionIntake(-0.55,0.55)));
		arr.add(new ActionParallelAction(new ActionWait(1), new ActionLift(70000), new ActionDelayedAction(0.5, new ActionIntake(0.55,-0.55))));
		arr.add(new ActionIntake(0,0));
		
		//backup from scale
		arr.add(new ActionIntakeRotation(false));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(1500, 0.7, 15), new ActionIntake(0,0), new ActionLift(70000)));	
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(4500, 0.55, 15), new ActionIntake(0,0), new ActionLift(0)));	
		
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
		arr.add(new ActionTurnToAngle(0, false, 2.5f));
		arr.add(makeSimpleParallelAction(new ActionWait(0.5), new ActionDrive(0.75, 0.75)));
		arr.add(new ActionTurnToAngle(0 ,false , 2.5f));
		arr.add(makeSimpleParallelAction(new ActionWait(2), new ActionLift(75500)));
		arr.add(makeSimpleParallelAction(new ActionWait(0.55), new ActionDrive(-0.65, -0.65)));
		arr.add(makeSimpleParallelAction(new ActionWait(1), new ActionIntakeGrip(true)));
		backupFromScale();
		arr.add(makeSimpleParallelAction(new ActionWait(15), new ActionLift(0)));
	}
	
	public void backupFromScale() {	
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(500, 0.7, 15), new ActionIntake(0,0), new ActionLift(70000)));	
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(6500, 0.55, 15), new ActionIntake(0,0), new ActionLift(0)));	
	}
	
	public void assembleRightRightScale() {
		
		//Follow trajectory and raise the lift
		arr.add(new ActionIntakeRotation(true));
		arr.add(makeSimpleParallelAction(getTrajectory("rightRightScaleApproach", 0.01, false), new ActionDelayedAction(1, new ActionLift(76000))));
		//Outtake the first cube into the scale
		arr.add(new ActionParallelAction(new ActionWait(1), new ActionIntake(0.5, -0.5), new ActionLift(76000)));
		arr.add(new ActionIntakeGrip(true));
		arr.add(new ActionParallelAction(new ActionWait(0.1), new ActionIntakeRotation(false)));
		
		//Backup from the scale
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(500, 0.7, 4), new ActionIntake(0,0), new ActionLift(70000)));	
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(5500, 0.55, 4), new ActionIntake(0,0), new ActionLift(0)));	
		
		//Turn to face toward the second cube
		arr.add(new ActionIntakeRotation(true));
		arr.add(new ActionParallelAction(new ActionTurnToAngle(-142, false, 2.5f), new ActionLift(0)));
		//Grab the second cube
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(7000, -0.7, 1.5), new ActionIntake(-0.7,0.7), new ActionLift(0)));
		arr.add(new ActionIntakeGrip(false));
		//Drive backward and start lifting the cube
		arr.add(new ActionParallelAction(new ActionWait(0.2), new ActionLift(0), new ActionIntake(-0.7,0.7)));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(3000, 0.8, 4), new ActionLift(0)));
		//Turn toward the scale, drive forward and outtake the second cube onto the scale
		arr.add(new ActionParallelAction(new ActionTurnToAngle(-30, false, 2.5f), new ActionLift(70000)));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(5500, -0.6, 4), new ActionLift(70000)));
		arr.add(new ActionParallelAction(new ActionWait(0.5), new ActionLift(70000), new ActionIntake(0.35,-0.35)));
		arr.add(new ActionIntake(0,0));
		
		//Backup from the scale
		arr.add(new ActionParallelAction(new ActionWait(0.1), new ActionIntakeRotation(false)));
		backupFromScale();

		//Drop the lift - code ends here
		arr.add(makeSimpleParallelAction(new ActionWait(15), new ActionLift(0)));		
	
		//arr.add(new ActionIntake(0,0))
		//arr.add(makeSimpleParallelAction(new ActionTurnToAngle(-15, false, 8), new ActionLift(70000)));
		//arr.add(new ActionParallelAction(new ActionWait(1), new ActionDrive(0.5, 0.5), new ActionLift(10000)));
	}
	
	public void assembleCenterSwitchLeft() {
		
		//Go to trajectory
		arr.add(new ActionIntakeRotation(true));
		arr.add(makeSimpleParallelAction(getTrajectory("centerLeftSwitch", 0.01, false), new ActionDelayedAction(1, new ActionLift(25000))));
		//LIft and outtake cubes
		arr.add(new ActionParallelAction(new ActionWait(0.4), new ActionDrive(-0.4,-0.4), new ActionLift(30000)));		
		//Outtake cubes
		arr.add(new ActionParallelAction(new ActionWait(0.35), new ActionIntake(0.5, -0.5), new ActionLift(30000)));
		arr.add(new ActionIntake(0, 0));
		
		//centerSwitchTwo(false);

		arr.add(new ActionIntakeGrip(true));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(1400, 0.7, 15), new ActionLift(0))); //1750
		arr.add(new ActionParallelAction(new ActionTurnToAngle(90, false, 3f), new ActionLift(0)));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(9500, -0.65, 15), new ActionLift(0), new ActionIntake(-0.5, 0.5)));
		arr.add(new ActionIntakeGrip(false));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(7500, 0.8, 15), new ActionLift(0)));
		arr.add(new ActionParallelAction(new ActionTurnToAngle(0, false, 3f), new ActionLift(30000)));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(1500, -0.85, 15), new ActionLift(30000)));
		arr.add(new ActionParallelAction(new ActionWait(0.3), new ActionDrive(-0.5, -0.5), new ActionLift(30000)));
		arr.add(new ActionParallelAction(new ActionWait(0.35), new ActionIntake(0.7, -0.7), new ActionLift(30000)));
		arr.add(new ActionIntake(0, 0));
		
		arr.add(new ActionIntakeGrip(true));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(700, 0.7, 15), new ActionLift(0)));
		arr.add(new ActionParallelAction(new ActionTurnToAngle(90, false, 3f), new ActionLift(0)));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(10000, -0.65, 15), new ActionLift(0), new ActionIntake(-0.7, 0.7)));
		arr.add(new ActionIntakeGrip(false));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(7500, 0.8, 15), new ActionLift(7500)));
		arr.add(new ActionParallelAction(new ActionTurnToAngle(0, false, 3f), new ActionLift(30000)));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(1000, -0.85, 15), new ActionLift(30000)));
		arr.add(new ActionParallelAction(new ActionWait(0.3), new ActionDrive(-0.5, -0.5), new ActionLift(30000)));
		arr.add(new ActionParallelAction(new ActionWait(0.35), new ActionIntake(0.7, -0.7), new ActionLift(30000)));
		arr.add(new ActionIntake(0, 0));
		arr.add(new ActionParallelAction(new ActionWait(15), new ActionLift(30000)));
		
		//arr.add(makeSimpleParallelAction(new ActionTurnToAngle(0, false, 8), new ActionLift(25000)));

		
	}

	public void assembleCenterSwitchRight() {
		
		arr.add(new ActionIntakeRotation(true));
		arr.add(makeSimpleParallelAction(getTrajectory("centerRightSwitch", 0.01, true), new ActionDelayedAction(1, new ActionLift(25000))));
		arr.add(new ActionParallelAction(new ActionWait(0.4), new ActionDrive(-0.4,-0.4), new ActionLift(25000)));		
		//arr.add(makeSimpleParallelAction(new ActionTurnToAngle(0, false, 8), new ActionLift(25000)));
		arr.add(new ActionParallelAction(new ActionWait(0.35), new ActionIntake(0.5, -0.5), new ActionLift(30000)));
		arr.add(new ActionIntake(0, 0));
		
		//centerSwitchTwo(true);
		
		
		arr.add(new ActionIntakeGrip(true));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(1600, 0.7, 15), new ActionLift(0)));
		arr.add(new ActionParallelAction(new ActionTurnToAngle(-90, false, 3f), new ActionLift(0)));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(9000, -0.65, 15), new ActionLift(0), new ActionIntake(-0.5, 0.5)));
		arr.add(new ActionIntakeGrip(false));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(4500, 0.8, 15), new ActionLift(0)));
		arr.add(new ActionParallelAction(new ActionTurnToAngle(0, false, 3f), new ActionLift(30000)));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(1500, -0.85, 15), new ActionLift(30000)));
		arr.add(new ActionParallelAction(new ActionWait(0.3), new ActionDrive(-0.5, -0.5), new ActionLift(30000)));
		arr.add(new ActionParallelAction(new ActionWait(0.35), new ActionIntake(0.7, -0.7), new ActionLift(30000)));
		arr.add(new ActionIntake(0, 0));
		
		arr.add(new ActionIntakeGrip(true));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(700, 0.7, 15), new ActionLift(0)));
		arr.add(new ActionParallelAction(new ActionTurnToAngle(-90, false, 3f), new ActionLift(0)));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(10000, -0.65, 15), new ActionLift(0), new ActionIntake(-0.7, 0.7)));
		arr.add(new ActionIntakeGrip(false));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(6000, 0.8, 15), new ActionLift(7500)));
		arr.add(new ActionParallelAction(new ActionTurnToAngle(0, false, 3f), new ActionLift(30000)));
		arr.add(new ActionParallelAction(new ActionDriveStraightByEncoders(1000, -0.85, 15), new ActionLift(30000)));
		arr.add(new ActionParallelAction(new ActionWait(0.3), new ActionDrive(-0.5, -0.5), new ActionLift(30000)));
		arr.add(new ActionParallelAction(new ActionWait(0.35), new ActionIntake(0.7, -0.7), new ActionLift(30000)));
		arr.add(new ActionIntake(0, 0));
		arr.add(new ActionParallelAction(new ActionWait(15), new ActionLift(30000)));
		
		
	}
	
	
	public ActionParallelAction makeSimpleParallelAction(Action con, Action nonCon) {
		ArrayList<Action> nonConAction = new ArrayList<Action>();
		nonConAction.add(nonCon);
		ArrayList<Action> conAction = new ArrayList<Action>();
		conAction.add(con);
		return new ActionParallelAction(conAction, nonConAction);
	}
}
