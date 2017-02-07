package org.usfirst.frc.team188.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Delay extends Command {

	double seconds = 0;
	Timer t;
	
    public Delay(double seconds) {
        this.seconds = seconds;
    }

    protected void initialize() {
    	t = new Timer();
    	t.reset();
    	t.start();
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return t.get() >= seconds;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
