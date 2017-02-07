package org.usfirst.frc.team188.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TeleoperatedGroup extends CommandGroup {
	
    public TeleoperatedGroup() {
        addParallel(new MoveJoystick());
        addParallel(new UpdateSensors());
    }
}
