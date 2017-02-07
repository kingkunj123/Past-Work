package org.usfirst.frc.team188.robot.subsystems;

import org.usfirst.frc.team188.robot.OI;
import org.usfirst.frc.team188.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class BinGrabber extends Subsystem {
	
	public static Servo binLauncherLeft = RobotMap.binLauncherLeft;
	public static Servo binLauncherRight = RobotMap.binLauncherRight;
	
	public static CANTalon binWinch = RobotMap.binWinch;
	
	public static boolean isToggled = false;
	
    public void initDefaultCommand() {
    }
    
    public static void moveJoystick(){
    	
    	//for some reason a toggle works better
    	if(OI.releaseLauncher())
    		isToggled = false;
    	if(OI.grabLauncher())
    		isToggled = true;
    	if(isToggled){
    		binLauncherLeft.set(0.5);
    		binLauncherRight.set(0.25);
    	}
    	else{
    		binLauncherLeft.set(0.25);
    		binLauncherRight.set(0.5);
    	}
    	
    	if(OI.winchIn())
    		binWinch.set(1);
    	else if(OI.winchOut())
    		binWinch.set(-1);
    	else
    		binWinch.set(0);
    	
    }
    
    public static void stop(){
    	binWinch.set(0);
    }
    
}

