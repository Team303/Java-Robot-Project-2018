package org.usfirst.frc.team303.robot.action;

public interface Action {

	public void run();
	
	public boolean isFinished();
	
	public static double[] driveStraight(double powSetpoint, double angleDifference, double tuningConstant) {                                                                                                                      //memes
		return new double[] {(powSetpoint + (angleDifference*tuningConstant)), (powSetpoint - (angleDifference*tuningConstant))};
	}
	
}
