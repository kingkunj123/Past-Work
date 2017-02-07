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
public class Intake extends Subsystem {
	
	 	public static CANTalon intakeLeft = RobotMap.intakeLeft;
	    public static CANTalon intakeRight = RobotMap.intakeRight;
	    
	    public static DoubleSolenoid intakePiston = RobotMap.intakePiston;
	    
	    public static DoubleSolenoid binPiston = RobotMap.binPiston;

    public void initDefaultCommand() {
    }
    
    public static void moveRoller(){
    	intakeLeft.set(OI.leftIntake());
    	intakeRight.set(OI.rightIntake());
    }
    
    public static void movePiston(){
    	if(OI.intakePistonOut()){
    		intakePiston.set(Value.kForward);
    	}
    	else if(OI.intakePistonIn()){
    		intakePiston.set(Value.kReverse);
    	}
    }
    
    public static void binPiston(){
    	if(OI.binPistonIn())
    		binPiston.set(Value.kForward);
    	else if(OI.binPistonOut())
    		binPiston.set(Value.kReverse);
    }
    
    public static void stop(){
    	intakeLeft.set(0);
    	intakeRight.set(0);
    }
 
}

