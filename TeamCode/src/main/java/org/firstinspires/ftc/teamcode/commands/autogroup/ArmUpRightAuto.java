package org.firstinspires.ftc.teamcode.commands.autogroup;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.commands.arm.MiddleArmUpCommand;
import org.firstinspires.ftc.teamcode.commands.arm.UnlockTransferCommand;
import org.firstinspires.ftc.teamcode.commands.vertical.StagedVerticalCommand;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.RobotStateSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalSlideSubsystem;

public class ArmUpRightAuto extends SequentialCommandGroup {

    public ArmUpRightAuto(ArmSubsystem arm, VerticalSlideSubsystem vertSlide, RobotStateSubsystem state){

        addCommands(

                new UnlockTransferCommand(arm),
                new MiddleArmUpCommand(arm),
                new InstantCommand(()->{
                    state.middleArm = RobotStateSubsystem.MiddleArmState.UP;
                }),
                new StagedVerticalCommand(vertSlide, state)
        );

        addRequirements(arm, state);
    }
}
