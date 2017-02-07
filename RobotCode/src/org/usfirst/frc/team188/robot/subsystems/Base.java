package org.usfirst.frc.team188.robot.subsystems;

import org.usfirst.frc.team188.robot.OI;
import org.usfirst.frc.team188.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Base extends Subsystem {
    
	
	
	public static CANTalon frontLeft = RobotMap.frontLeft;
	public static CANTalon frontRight = RobotMap.frontRight;
	public static CANTalon backLeft = RobotMap.backLeft;
	public static CANTalon backRight = RobotMap.backRight;
	
	public static DoubleSolenoid antiTip = RobotMap.antiTip;
	
	public static boolean isTriggered = false;
	
    public void initDefaultCommand() {
    }
    
    public static void driveJoystick(){
	    if(OI.slowmo()){
	    	frontLeft.set(OI.leftFrontAngleDrive()*0.50);
	       	backLeft.set(OI.leftBackAngleDrive()*0.50);
	       	frontRight.set(OI.rightFrontAngleDrive()*0.50);
	       	backRight.set(OI.rightBackAngleDrive()*0.50);
	   	}
	   	else{
	    	frontLeft.set(OI.leftFrontAngleDrive()*1);
	    	backLeft.set(OI.leftBackAngleDrive()*1);
	    	frontRight.set(OI.rightFrontAngleDrive()*1);
	    	backRight.set(OI.rightBackAngleDrive()*1);
    	}
    }
    
    public static void pistonAntiTip(){
    	if(OI.lowerAntiTip())
    		antiTip.set(Value.kForward);
    	else if(OI.raiseAntiTip()){
    		antiTip.set(Value.kReverse);
    	}
    }
    
    public static void stop(){
    	frontLeft.set(0);
    	backLeft.set(0);
    	frontRight.set(0);
    	backRight.set(0);
    }
    
}

