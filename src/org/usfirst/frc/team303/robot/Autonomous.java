package org.usfirst.frc.team303.robot;

import java.util.ArrayList;
import org.usfirst.frc.team303.robot.action.Action;
import org.usfirst.frc.team303.robot.action.ActionDriveByTrajectory;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import jaci.pathfinder.Trajectory;

public class Autonomous {
	
	NetworkTable pathfinderOutputTable = NetworkTable.getTable("pathfinderOutput");		
	private ArrayList<Action> actionList = new ArrayList<>();
	private int count = 0;
	public Trajectory[] trajectoryArray;
	public Trajectory forwardLeftTrajectory;
	public Trajectory forwardRightTrajectory;
	Trajectory forwardTrajectory;
	
	
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
	
	
	public void assembleDriveForwardFour(){ //TODO change auto name to match trajectory goal
		trajectoryArray = Path.deserializeTrajectoryArray(pathfinderOutputTable.getString("path", ""));
		forwardTrajectory = trajectoryArray[0];
		add(new ActionDriveByTrajectory(forwardTrajectory));
	}

	
}
