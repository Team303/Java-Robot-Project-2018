package org.usfirst.frc.team303.robot.action;

import org.usfirst.frc.team303.robot.Robot;

import edu.wpi.first.wpilibj.Timer;

public class ActionZero implements Action {
	
	boolean firstRun = true;
	Timer timer;
	
	public ActionZero() {
		timer = new Timer();
	}
	
	@Override
	public void run() {
		if(firstRun) {
			Robot.drivebase.zeroEncoder();
			Robot.navX.zeroYaw();
			timer.start();
			firstRun = false;
		}
	}

	@Override
	public boolean isFinished() {
		return timer.get()>1;
	}
	
}
