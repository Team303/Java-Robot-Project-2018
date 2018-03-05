package org.usfirst.frc.team303.robot.action;

import org.usfirst.frc.team303.robot.Robot;

import edu.wpi.first.wpilibj.Timer;

public class ActionIntake implements Action {
	private double left;
	private double right;
	
	public ActionIntake(double lPower, double rPower) {
		left = lPower;
		right = rPower;
	}

	public void run() {
		//Robot.intake.setWheels(left, right);
	}

	public boolean isFinished() {
		return true;
	}

}
