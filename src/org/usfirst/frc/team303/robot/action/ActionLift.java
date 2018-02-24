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

	/**
	 * <b>Stop!</b><br><br>Most of the time you should use lift as a non-conditional action. Do you know what you're doing?
	 */
	@Deprecated
	public boolean isFinished() {
		return Math.abs(setpoint-Robot.lift.getEncoder())<1000;
	}


}
