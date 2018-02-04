package org.usfirst.frc.team303.robot;

public class RobotMap {

	//================= CAN IDs =================
	
	//these don't do anything, but are here so we don't accidentally use their IDs
	public static final int PDP = 0;
	
	public static final int FRONT_LEFT = 1;  //encoder on this talon
	public static final int FRONT_RIGHT = 10; 
	public static final int REAR_LEFT = 2; 
	public static final int REAR_RIGHT = 11; //encoder on this talon
	public static final int MIDDLE_LEFT = 2; 
	public static final int MIDDLE_RIGHT = 11; //encoder on this talon
	
	public static final boolean FRONT_LEFT_INV = true;
	public static final boolean FRONT_RIGHT_INV = false;
	public static final boolean REAR_LEFT_INV = true;
	public static final boolean REAR_RIGHT_INV = true;
	public static final boolean MIDDLE_LEFT_INV = true;
	public static final boolean MIDDLE_RIGHT_INV = true;
	
	public static final int CLIMBER_PDP_CHANNEL = 13;
	public static final int CLIMBER_ID = 3; 
	public static final boolean CLIMBER_INV = false;
	public static final int CLIMBER_CLIMB_BUTTON = 2;
	
	public static final int LIFT_PDP_CHANNEL = 13;
	public static final int LIFT_ID = 3; 
	public static final boolean LIFT_INV = false;
	

	
	public static final int INTAKE_LEFT_ID = 0;
	public static final int INTAKE_RIGHT_ID = 1;
	public static final int INTAKE_MOVE_ID = 1;
	public static final boolean INTAKE_LEFT_INV = false;
	public static final boolean INTAKE_RIGHT_INV = false;
	
	public static final int SOLENOID_OPEN_ID = 0;
	public static final int SOLENOID_CLOSE_ID = 0;

	

	
	
	
	
	
	
	
	
	
}
