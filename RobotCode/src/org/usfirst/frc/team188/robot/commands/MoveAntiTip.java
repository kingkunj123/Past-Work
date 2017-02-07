package org.usfirst.frc.team188.robot.commands;

import org.usfirst.frc.team188.robot.subsystems.Base;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveAntiTip extends Command {

	char dir = 'd';
	
    public MoveAntiTip(char dir) {
    	dir = this.dir;
    }

    protected void initialize() {
    }

    protected void execute() {
    	if(dir == 'd')
    		Base.antiTip.set(Value.kForward);
    	else
    		Base.antiTip.set(Value.kReverse);
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
