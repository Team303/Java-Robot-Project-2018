package org.usfirst.frc.team303.robot.action;

import org.usfirst.frc.team303.robot.Robot;

public class ActionDrive implements Action{

	@Override
	public void run() {
		Robot.drivebase.drive(-0.8, -0.8);
	}

	@Override
	public boolean isFinished() {
		return true;
	}
	
	

}
