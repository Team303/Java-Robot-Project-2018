/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team303.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
@SuppressWarnings("deprecation")
public class Robot extends IterativeRobot {
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	
	private int position;
	private int config;

	private String message;

	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();
	private String[][][] autoList;
	
	private SendableChooser<String> positionChooser = new SendableChooser<>();
	private SendableChooser<String> config1 = new SendableChooser<>();
	private SendableChooser<String> config2 = new SendableChooser<>();
	private SendableChooser<String> config3 = new SendableChooser<>();
	private SendableChooser<String> config4 = new SendableChooser<>();

	public static NavX navX;
	public static Lift lift;
	public static Climber climber;
	public static Drivebase drivebase;
	public static Intake intake;
	public static Autonomous auto;


	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
		
		//positionChooser.addDefault("Choose", "Choose");
		//positionChooser.addDefault("1", "1");
		//positionChooser.addDefault("2", "2");
		//positionChooser.addDefault("3", "3");
		//SmartDashboard.putData("Position", positionChooser);

		navX = new NavX();
		//lift = new Lift();
		//climber = new Climber();
		//intake = new Intake();
		drivebase = new Drivebase();
		auto = new Autonomous();
		auto.initWaypoints();
		
		autoList = auto.getAutoList();
		
		position = DriverStation.getInstance().getLocation();
		int configIndex = 1;
		
		config1.addDefault("Config 1", "Config 1");
		config2.addDefault("Config 2", "Config 2");
		config3.addDefault("Config 3", "Config 3");
		config4.addDefault("Config 4", "Config 4");
		
		for (String[][] config : autoList) {
			for (String autoName : config[position - 1]) {
				switch (configIndex) {
					case 1:
						config1.addObject(autoName, autoName);
						break;
					case 2:
						config2.addObject(autoName, autoName);
						break;
					case 3:
						config3.addObject(autoName, autoName);
						break;
					case 4:
						config4.addObject(autoName, autoName);
						break;
				}
			}
			configIndex++;
		}
		
		SmartDashboard.putData("Config 1", config1);
		SmartDashboard.putData("Config 2", config2);
		SmartDashboard.putData("Config 3", config3);
		SmartDashboard.putData("Config 4", config4);	
	}
	
	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	

	@Override
	public void autonomousInit() {
		message = DriverStation.getInstance().getGameSpecificMessage();
		
		String switchStr = message.substring(0,1);
		String scaleStr = message.substring(1,2);
		if (switchStr.equals("R") && scaleStr.equals("L")) {
			config = 1;
		} else if (switchStr.equals("L") && scaleStr.equals("R")) {
			config = 2;
		} else if (switchStr.equals("R") && scaleStr.equals("R")) {
			config = 3;
		} else if (switchStr.equals("L") && scaleStr.equals("L")) {
			config = 4;
		} 
		
		String selectedAuto = "";
		
		switch (config) {
			case 1:
				selectedAuto = config1.getSelected();
				break;
			case 2:
				selectedAuto = config2.getSelected();
				break;
			case 3:
				selectedAuto = config3.getSelected();
				break;
			case 4:
				selectedAuto = config4.getSelected();
				break;
			default:
				break;
		}
		
		String configStr = Integer.toString(config);
		String positionStr = Integer.toString(position);
		String autoNumStr = selectedAuto.substring(0, 1);
		
		String autoString = configStr + "-" + positionStr + "-" + autoNumStr;
		auto.driveTrajectory(autoString);
		
		
	}
	
	
	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		auto.run();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {

		OI.update();

		drivebase.drive(OI.lY, OI.rY);

		/*	if (OI.lBtn[0]) {
			intake.set(0.6);
		} else {
			intake.set(0);
		}*/

		//	intake.setPiston(OI.lBtn[1]);
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
