package org.usfirst.frc.team188.robot.commands;

import org.usfirst.frc.team188.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveIntakeDiversion extends Command {

	Timer t;
	double time;
	double power;
	
    public MoveIntakeDiversion(double power, double time) {
    	this.time = time;
    	this.power = power;
    }

    protected void initialize() {
    	t = new Timer();
    	t.reset();
    	t.start();
    }

    protected void execute() {
    	Intake.intakeLeft.set(-power);
    	Intake.intakeRight.set(power);
    }

    protected boolean isFinished() {
        return t.get() >= time;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
