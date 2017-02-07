package org.usfirst.frc.team188.robot.subsystems;

import org.usfirst.frc.team188.robot.OI;
import org.usfirst.frc.team188.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Elevator extends Subsystem {
    
    public static CANTalon elevatorLeft = RobotMap.elevatorLeft;
    public static CANTalon elevatorRight = RobotMap.elevatorRight;

    public void initDefaultCommand() {
    }
    
    public static void moveElevator(){
    	if(OI.leftElevator() < 0.1 && OI.leftElevator() > -0.1){
	    	elevatorLeft.set(-0.1);
	    	elevatorRight.set(0.1);
	    }
	    else if(OI.operator.getY() > 0){
	    		elevatorLeft.set(OI.leftElevator()*0.4);
	    		elevatorRight.set(OI.rightElevator()*0.4);
	    }
		else{
			elevatorLeft.set(OI.leftElevator()*0.75);
			elevatorRight.set(OI.rightElevator()*0.75);
		}
    }
    
    public static void stop(){
    	elevatorLeft.set(0);
    	elevatorRight.set(0);
    }
}

