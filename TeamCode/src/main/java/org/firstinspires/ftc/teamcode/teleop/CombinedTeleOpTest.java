package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.ClawSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.Consts;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.DualSlider;
import org.firstinspires.ftc.teamcode.subsystems.Tilt;
import org.firstinspires.ftc.teamcode.subsystems.VertexSlider;

@TeleOp(name = "🟢CombinedTeleOpTest")
@Config()
//@Disabled
public class CombinedTeleOpTest extends LinearOpMode {

    // Define constants for motor power adjustment
    private static final double NORMAL_POWER = 1.0;
    private static final double SLOW_POWER = 0.5;

    // Track whether slow mode is active
    private boolean slowMode = false;

    Drivetrain drivetrain = new Drivetrain();
    VertexSlider vertexSlider = new VertexSlider();
    Tilt tilt = new Tilt();
    ClawSubsystem clawSubsystem = new ClawSubsystem(); // Initialize the ClawSubsystem

    @Override
    public void runOpMode() throws InterruptedException {
        Drivetrain.init(hardwareMap);
        DualSlider.init(hardwareMap);
        Tilt.init(hardwareMap);
        clawSubsystem.init(hardwareMap); // Initialize the ClawSubsystem

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        waitForStart();

        while (!isStopRequested()) {
            // Check for slow mode activation
            if (gamepad1.x) {
                slowMode = true;
            }

            // Check for returning to normal mode
            if (gamepad1.a) {
                slowMode = false;
            }

            // Adjust motor powers based on slow mode
            double powerMultiplier = slowMode ? SLOW_POWER : NORMAL_POWER;

            // Control Drivetrain
            double verticalLeft = -gamepad1.left_stick_x * powerMultiplier;
            double horizontalLeft = gamepad1.left_stick_y * powerMultiplier;

            double verticalRight = gamepad1.right_stick_x * powerMultiplier;
            double horizontalRight = -gamepad1.right_stick_y * powerMultiplier;

            // Call power() method with individual powers for each motor
            Drivetrain.power(verticalRight - horizontalRight,
                    verticalLeft + horizontalLeft,
                    verticalRight + horizontalRight,
                    verticalLeft - horizontalLeft);
            // Telemetry for drivetrain controls
            telemetry.addData("Drivetrain", "LeftY: %.2f, LeftX: %.2f, RightY: %.2f, RightX: %.2f",
                    verticalLeft, horizontalLeft, verticalRight, horizontalRight);

            // Example: Move both sliders to HIGH position when dpad_up is pressed
            // Moving lift
            if (gamepad2.dpad_up) {
                vertexSlider.move(Consts.Lift.HIGH);
            } else if (gamepad2.dpad_right) {
                vertexSlider.move(Consts.Lift.LOW);
            } else if (gamepad2.dpad_left) {
                vertexSlider.move(Consts.Lift.MEDIUM);
            } else if (gamepad2.dpad_down) {
                vertexSlider.move(Consts.Lift.ZERO);
            }
            // Telemetry for slider controls
            telemetry.addData("Lift Position", vertexSlider.getPosition());
            // Control claws
            if (gamepad1.left_bumper) {
                clawSubsystem.moveLeftClaw(Consts.LCLAW_OPEN_LIMIT);
                clawSubsystem.moveRightClaw(Consts.RCLAW_OPEN_LIMIT);
            } else if (gamepad1.right_bumper) {
                clawSubsystem.moveLeftClaw(Consts.LCLAW_CLOSE_LIMIT);
                clawSubsystem.moveRightClaw(Consts.RCLAW_CLOSE_LIMIT);
            }
            // Telemetry for claw controls
            telemetry.addData("ClawSubsystem", "LeftPos: %.2f, RightPos: %.2f", clawSubsystem.getLeftClawPosition(), clawSubsystem.getRightClawPosition());


            // Control tilt
            if (gamepad2.x) {
                Tilt.move(Consts.Tilt.ANGLETILT); //tilt
            }
            if (gamepad2.a) {
                Tilt.move(Consts.Tilt.PICKUPTILT); //pickup
            } else if (gamepad2.y) {
                Tilt.move(Consts.Tilt.SCORETILT); //score
            }
            telemetry.addData("Tilt", "Position: %s", Tilt.getPosition());
            // Update telemetry
            telemetry.update();
        }
    }
}
