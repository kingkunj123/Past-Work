package org.usfirst.frc.team188.robot.commands;

import org.usfirst.frc.team188.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveElevator extends Command {

	double direction = 0;
	double power = 0;
	double time = 0;
	Timer t;
	
    public MoveElevator(double direction, double power, double time) {
    	this.direction = direction;
    	this.power = power;
    	this.time = time;
    	t = new Timer();
    }

    protected void initialize() {
    	t.reset();
    	t.start();
    }

    protected void execute() {
    	Elevator.elevatorLeft.set(-direction * power);
    	Elevator.elevatorRight.set(direction * power);
    }

    protected boolean isFinished() {
//    	if(time == 0 && direction == -1)
//    		return RobotMap.deadTopElevator.get();
//    	if(time == 0 && direction == 1)
//    		return !RobotMap.deadBottomElevator.get();
    	return t.get() >= time;
    }

    protected void end() {
    	Elevator.elevatorLeft.set(-0.1);
    	Elevator.elevatorRight.set(0.1);
    }

    protected void interrupted() {
    	Elevator.stop();
    }
}
