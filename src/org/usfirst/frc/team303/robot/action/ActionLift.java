package org.usfirst.frc.team303.robot.action;

import org.usfirst.frc.team303.robot.Robot;

public class ActionLift implements Action {
	private int setpoint;

	public ActionLift() {
		this(1000);
	}

	public ActionLift(int setpoint) {	
		this.setpoint = setpoint;
	}

	public void run() {
		Robot.lift.setSetpoint(setpoint);
	}

	public boolean isFinished() {
		return Robot.lift.autoControl();
	}


}
