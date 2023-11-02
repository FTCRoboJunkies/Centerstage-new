package org.firstinspires.ftc.teamcode.commands.vertical;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.RobotStateSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalSlideSubsystem;

public class StagedVerticalCommand extends CommandBase {

    private VerticalSlideSubsystem slideSubsystem;

    private RobotStateSubsystem state;

    public StagedVerticalCommand(VerticalSlideSubsystem slide, RobotStateSubsystem rState) {
        this.slideSubsystem = slide;
        this.state = rState;
    }

    @Override
    public void initialize() {

        if(this.state.middleArm == RobotStateSubsystem.MiddleArmState.UP && this.state.verticalHeight == RobotStateSubsystem.VerticalHeight.DOWN){

            this.slideSubsystem.Position1();

        }
        else if(this.state.middleArm == RobotStateSubsystem.MiddleArmState.UP && this.state.verticalHeight == RobotStateSubsystem.VerticalHeight.POS1){

            this.slideSubsystem.Position2();
        }
        else if(this.state.middleArm == RobotStateSubsystem.MiddleArmState.UP && this.state.verticalHeight == RobotStateSubsystem.VerticalHeight.POS2){

            this.slideSubsystem.Position1();
        }
    }

    @Override
    public boolean isFinished()
    {
        if(this.state.middleArm == RobotStateSubsystem.MiddleArmState.UP)
        {
            if(this.state.verticalHeight == RobotStateSubsystem.VerticalHeight.DOWN)
            {
                if (this.slideSubsystem.IsPosition1()) {
                    this.state.verticalHeight = RobotStateSubsystem.VerticalHeight.POS1;
                }

                return this.slideSubsystem.IsPosition1();
            }
            else if(this.state.verticalHeight == RobotStateSubsystem.VerticalHeight.POS1){

                if (this.slideSubsystem.IsPosition2()) {
                    this.state.verticalHeight = RobotStateSubsystem.VerticalHeight.POS2;
                }

                return this.slideSubsystem.IsPosition2();
            }
            else if(this.state.verticalHeight == RobotStateSubsystem.VerticalHeight.POS2){

                if (this.slideSubsystem.IsPosition1() && !this.slideSubsystem.IsPosition2()) {
                    this.state.verticalHeight = RobotStateSubsystem.VerticalHeight.POS1;
                }

                return this.slideSubsystem.IsPosition1() && !this.slideSubsystem.IsPosition2();
            }

        }
        //need to work out what value
        return true;
    }
}