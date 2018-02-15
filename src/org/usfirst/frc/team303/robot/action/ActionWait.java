package org.usfirst.frc.team303.robot.action;

import edu.wpi.first.wpilibj.Timer;

public class ActionWait implements Action {
	
	private int time;
	Timer timer = new Timer();
	
	public ActionWait() {
		this(9999999);
	}
	
	public ActionWait(int time) {
		//Start timer
		this.time = time;
		timer.start();
	}

	public void run() {}	
	
	public boolean isFinished() {
		return timer.get() >= time;
	}
}
