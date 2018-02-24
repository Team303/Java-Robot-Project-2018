package org.usfirst.frc.team303.robot.action;

import org.usfirst.frc.team303.robot.Robot;

public class ActionIntakeGrip implements Action {
	private boolean grip;

	public ActionIntakeGrip(boolean grip) {
		this.grip = grip;
	}

	public void run() {
		Robot.intake.setGripper(grip);
	}

	public boolean isFinished() {
		return true;
	}

}
