package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class Drivetrain extends LinearOpMode {

    //Initializes linearslide motor

    DcMotor linearSlideMotor;

    // Declare motor variables for the Mecanum drivetrain
    private DcMotor leftFront = null;
    private DcMotor rightFront = null;
    private DcMotor leftBack = null;
    private DcMotor rightBack = null;



    // Declare servo variables for the claw
    private Servo leftClaw = null;
    private Servo rightClaw = null;

    // Claw control variables
    double clawOffset = 0.0;  // Claw offset to control servo positions
    final double CLAW_SPEED = 0.02;  // Step size for modifying claw position
    final double MID_SERVO = 0.5;    // Middle position of the claw servos

    @Override
    public void runOpMode() {
        // Initialize the Mecanum drivetrain motors
        leftFront = hardwareMap.get(DcMotor.class, "left_front");
        rightFront = hardwareMap.get(DcMotor.class, "right_front");
        leftBack = hardwareMap.get(DcMotor.class, "left_back");
        rightBack = hardwareMap.get(DcMotor.class, "right_back");

        // Set the direction of the motors
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.REVERSE);

        // Initialize the claw servos
        leftClaw = hardwareMap.get(Servo.class, "left_claw");
        rightClaw = hardwareMap.get(Servo.class, "right_claw");

        // Set initial claw positions
        leftClaw.setPosition(MID_SERVO);
        rightClaw.setPosition(MID_SERVO);

        // Wait for the driver to press the start button
        telemetry.addData("Status", "Initialized. Waiting for Start...");
        telemetry.update();

        // Mapping and getting linearslide

        linearSlideMotor = hardwareMap.get(DcMotor.class, "linearSlideMotor");
        linearSlideMotor.setDirection(DcMotor.Direction.FORWARD);
        linearSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        // Run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Linearslide controls

            if(gamepad1.right_trigger > 0) {
                linearSlideMotor.setPower(gamepad1.right_trigger);
            }
            else if (gamepad1.left_trigger > 0) {
                linearSlideMotor.setPower(-gamepad1.left_trigger);
            }
            else {
                linearSlideMotor.setPower(0);
            }

            // Mecanum drive calculation using gamepad inputs
            double drive = -gamepad1.left_stick_y;  // Forward and backward movement
            double strafe = gamepad1.left_stick_x;  // Left and right movement
            double rotate = gamepad1.right_stick_x; // Rotational movement

            // Calculate the power for each wheel using Mecanum formulas
            double frontLeftPower = drive + strafe + rotate;
            double frontRightPower = drive - strafe - rotate;
            double backLeftPower = drive - strafe + rotate;
            double backRightPower = drive + strafe - rotate;

            // Set the calculated power to each motor
            leftFront.setPower(frontLeftPower);
            rightFront.setPower(frontRightPower);
            leftBack.setPower(backLeftPower);
            rightBack.setPower(backRightPower);

            // Control the claw servos using the bumpers on `gamepad2`
            if (gamepad2.right_bumper) {
                clawOffset += CLAW_SPEED;  // Open the claw
            } else if (gamepad2.left_bumper) {
                clawOffset -= CLAW_SPEED;  // Close the claw
            }

            // Clip the claw offset to prevent overextension
            clawOffset = Range.clip(clawOffset, -0.5, 0.5);

            // Set the servo positions based on the calculated offset
            leftClaw.setPosition(MID_SERVO + clawOffset);
            rightClaw.setPosition(MID_SERVO - clawOffset);

            // Send telemetry data to the driver station
            telemetry.addData("Claw", "Offset = %.2f", clawOffset);
            telemetry.addData("Drive", "LF: %.2f, RF: %.2f, LB: %.2f, RB: %.2f",
                    frontLeftPower, frontRightPower, backLeftPower, backRightPower);
            telemetry.update();
        }
    }
}
