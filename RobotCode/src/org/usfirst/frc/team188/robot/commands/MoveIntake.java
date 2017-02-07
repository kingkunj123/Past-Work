package org.usfirst.frc.team188.robot.commands;

import org.usfirst.frc.team188.robot.RobotMap;
import org.usfirst.frc.team188.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveIntake extends Command {

	double direction = 0; //positive inwards, negative outwards
	double time = 0;

	Timer t;

	public MoveIntake(double direction, double time) {
		this.direction = direction;
		this.time = time;
	}

	protected void initialize() {
		if(time != 0){
			t = new Timer();
			t.reset();
			t.start();
		}
	}

	protected void execute() {
		Intake.intakeLeft.set(direction);
		Intake.intakeRight.set(direction);
	}

	protected boolean isFinished() {
		if(time != 0){
			return t.get() >= time;
		}
		else 
			return RobotMap.light.get();
	}

	protected void end() {
		Intake.stop();
	}

	protected void interrupted() {
		Intake.stop();
	}
}
