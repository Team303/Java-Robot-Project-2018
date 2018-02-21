package org.usfirst.frc.team303.robot.action;

import org.usfirst.frc.team303.robot.Robot;

public class ActionIntakeRotation implements Action {
	private boolean rotation;

	public ActionIntakeRotation() {
		this(true);
	}

	public ActionIntakeRotation(boolean grip) {
		this.rotation = rotation;
	}

	public void run() {
		Robot.intake.setGripper(rotation);
	}

	public boolean isFinished() {
		return true;
	}

}
