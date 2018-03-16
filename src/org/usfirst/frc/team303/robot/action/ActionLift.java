package org.usfirst.frc.team303.robot.action;

import org.usfirst.frc.team303.robot.Robot;

public class ActionLift implements Action {
	private int setpoint;
	boolean firstRun = true;

	public ActionLift(int setpoint) {	
		this.setpoint = setpoint;
	}

	public void run() {
		if(firstRun) {
			Robot.lift.setSetpoint(setpoint);
			firstRun = false;
		}
		Robot.lift.autoControl();
	}

	public boolean isFinished() {
		return true;
	}


}
