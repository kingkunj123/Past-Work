package org.usfirst.frc.team188.robot.commands;

import org.usfirst.frc.team188.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousGroup extends CommandGroup {
	
	Command updateSensors;
	
    public  AutonomousGroup() {
    	
    	updateSensors = new UpdateSensors();
    	updateSensors.start();
    	
    	//zone auto
    	if(RobotMap.autoSelected == 1){
    		addParallel(new MoveAntiTip('d'));
	    	addSequential(new DriveForward(1, 0.75));
//    		addParallel(new PIDTurn(90, 15));
//    		addSequential(new PIDStraight(0, 5, 0, 0));
//    		addParallel(new PIDStraight(0, 215, 0.5, 0.5));
    	}
    	
    	//3 tote auto
    	else if(RobotMap.autoSelected == 2){
    		
    	}
    	
    	else if(RobotMap.autoSelected == 3){
    		addParallel(new MoveElevator(1, 0.5, 1.5));
    		addSequential(new DriveForward(-0.5, 0.5));
    		
    		addSequential(new DriveForward(-0.5, 1.5));
    		
    		addParallel(new MoveIntakePiston('i'));
    		addParallel(new DriveForward(-0.25, 1));
    		addSequential(new MoveIntake(-0.5, 1));
    		addSequential(new MoveIntake(-0.25, 0.5));
    		
    		addParallel(new MoveIntakePiston('o'));
    		addSequential(new MoveElevator(-1, 0.5, 0.75));
    		addSequential(new MoveElevator(-1, 0.25, 1));
    	}
    	
    	else if(RobotMap.autoSelected == 4){
    		addSequential(new BinLauncher('o'));
    		addSequential(new Delay(0.4)); //0.125, 0.0625, 0.09375, 0.109375, 0.25
    		
    		addParallel(new DriveForward(1, 1.25)); //0.75
//    		addParallel(new PIDStraight(0, 0.75, 1, 0));
    		addParallel(new BinLauncher('i'));
    		addSequential(new BinWinch('i', 1.3));
    		
//    		addParallel(new BinWinch('i', 0.25, 0.5));
//    		addSequential(new Delay(2.5));
    		
//    		addParallel(new DriveForward(-0.5, 2));
    		addParallel(new BinLauncher('i'));
    		addSequential(new BinWinch('i', 10, 0.75));
    	}
    	
    	updateSensors.cancel();
    	
    }
}
