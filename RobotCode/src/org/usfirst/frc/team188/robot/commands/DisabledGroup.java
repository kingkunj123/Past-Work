package org.usfirst.frc.team188.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DisabledGroup extends CommandGroup {
	
    public DisabledGroup() {
        addParallel(new UpdateSensors());
    }
}
