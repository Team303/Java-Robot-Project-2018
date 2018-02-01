package org.usfirst.frc.team303.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake {
	WPI_TalonSRX intake = new WPI_TalonSRX(RobotMap.INTAKE_ID);
	
	public Intake(){
		intake.setSafetyEnabled(true);
		intake.setInverted(RobotMap.INTAKE_INV);
	}
	
	
	public void control(){
		
		if(OI.xLeftBumper && OI.xRightBumper) {
			
		} else if(!OI.xBtnA && !OI.xBtnB) {
			set(0);
		} else if(OI.xBtnB){
			set(1);
		}else if(OI.xBtnA){
			set(-1);
		}
		
	}
	
	public void set(double percentVoltage){
		intake.set(percentVoltage);
		SmartDashboard.putNumber("intake pvoltage", percentVoltage);
	}
}
