package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "ServoController")
public class TestingServos extends LinearOpMode {
    private Servo myServo;
    private static final double servo_min_pos = 0.0;
    private static double servo_max_pos = 1.0;
    private double currentPosition = 0.5;
    DcMotor motorfl;
    DcMotor motorfr;
    DcMotor motorbl;
    DcMotor motorbr;

    public void moveDriveTrain() {
        double vertical = 0;
        double horizontal = 0;
        double pivot= 0;
        vertical = -gamepad1.left_stick_y;
        horizontal = gamepad1.left_stick_x;
        pivot = gamepad1.right_stick_x;



        motorfl.setPower(pivot + (-vertical + horizontal));
        motorfr.setPower(pivot + (-vertical - horizontal));
        motorbl.setPower(pivot + (-vertical - horizontal));
        motorbr.setPower(pivot + (-vertical + horizontal));

    }


    @Override
    public void runOpMode() {
        myServo = hardwareMap.get(Servo.class,"servo1");

        motorfl = hardwareMap.get(DcMotor.class, "motor_front_left"); // 0
        motorfr = hardwareMap.get(DcMotor.class, "motor_front_right"); // 1
        motorbl = hardwareMap.get(DcMotor.class, "motor_back_left"); // 2
        motorbr = hardwareMap.get(DcMotor.class, "motor_back_right"); // 3

        motorfl.setDirection(DcMotorSimple.Direction.REVERSE);
        motorbr.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while(opModeIsActive()) {
            if(gamepad1.a) {
                currentPosition += 1;
            } else if(gamepad1.b) {
                currentPosition -= 0;
            }

            currentPosition = Math.max(servo_min_pos, Math.min(currentPosition, servo_max_pos));
            myServo.setPosition(currentPosition);

            telemetry.addData("Servo Position", currentPosition);
            telemetry.update();

            moveDriveTrain();
        }


    }
}
