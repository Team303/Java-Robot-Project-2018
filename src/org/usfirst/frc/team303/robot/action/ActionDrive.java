package org.usfirst.frc.team303.robot.action;

import org.usfirst.frc.team303.robot.Robot;

public class ActionDrive implements Action{

	double left;
	double right;
	
	public ActionDrive(double left, double right) {
		this.left = left;
		this.right = right;
	}
	
	public ActionDrive() {
		left = -0.8;
		right = -0.8;
	}
	
	@Override
	public void run() {
		Robot.drivebase.drive(left, right);
	}

	@Override
	public boolean isFinished() {
		return true;
	}
	
	

}
