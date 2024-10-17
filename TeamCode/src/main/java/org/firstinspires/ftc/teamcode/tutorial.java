package org.firstinspires.ftc.teamcode;


import android.os.AsyncTask;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "drivetrain")
public class tutorial extends OpMode
{
    DcMotor motorfl;
    DcMotor motorfr;
    DcMotor motorbl;
    DcMotor motorbr;

    public void moveDriveTrain(){
        double vertical = 0;
        double horizontal = 0;
        double pivot = 0;
        vertical = -gamepad1.left_stick_y;
        horizontal = gamepad1.left_stick_x;
        pivot = gamepad1.right_stick_x;

        motorfl.setPower(pivot + (-vertical + horizontal));
        motorfr.setPower(pivot + (-vertical - horizontal));
        motorbl.setPower(pivot + (-vertical - horizontal));
        motorbr.setPower(pivot + (-vertical + horizontal));


    }
    @Override
    public void init() {

        motorfl = hardwareMap.get(DcMotor.class, "motor_front_left"); // 0
        motorfr = hardwareMap.get(DcMotor.class, "motor_front_right"); // 1
        motorbl = hardwareMap.get(DcMotor.class, "motor_back_left"); // 2
        motorbr = hardwareMap.get(DcMotor.class, "motor_back_right"); // 3

        motorfl.setDirection(DcMotorSimple.Direction.REVERSE);
        motorbr.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        moveDriveTrain();
    }
}
