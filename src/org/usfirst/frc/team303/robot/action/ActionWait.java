package org.usfirst.frc.team303.robot.action;

import edu.wpi.first.wpilibj.Timer;

public class ActionWait implements Action {
	
	private double time;
	Timer timer = new Timer();
	boolean firstRun = true;
	
	
	public ActionWait() {
		this(9999999);
	}
	
	/**
	 * @param time in seconds
	 */
	public ActionWait(double time) {
		this.time = time;
	}

	public void run() {
		if(firstRun) {
			timer.start();
			firstRun = false;
		}
	}	
	
	public boolean isFinished() {
		return timer.get() >= time;
	}
}
