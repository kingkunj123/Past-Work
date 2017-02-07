package org.usfirst.frc.team188.robot.commands;

import org.usfirst.frc.team188.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveIntakePiston extends Command {

	char direction = 'o';
	
    public MoveIntakePiston(char direction) { //'i', 'o'
    	this.direction = direction;
    }

    protected void initialize() {
    }

    protected void execute() {
    	if(direction == 'i')
    		Intake.intakePiston.set(Value.kReverse);
    	else
    		Intake.intakePiston.set(Value.kForward);
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
