package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Basic: Mecanum Drive", group="Linear Opmode")

public class BasicMecanumDrive extends LinearOpMode {
    private DcMotor leftDriveFront = null;
    private DcMotor rightDriveFront = null;
    private DcMotor leftDriveBack = null;
    private DcMotor rightDriveBack = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftDriveFront = hardwareMap.get(DcMotor.class, "frontLeft");
        rightDriveFront = hardwareMap.get(DcMotor.class, "frontRight");
        leftDriveBack  = hardwareMap.get(DcMotor.class, "backLeft");
        rightDriveBack = hardwareMap.get(DcMotor.class, "backRight");

        leftDriveFront.setDirection(DcMotor.Direction.FORWARD);
        rightDriveFront.setDirection(DcMotor.Direction.FORWARD);
        leftDriveBack.setDirection(DcMotor.Direction.REVERSE);
        rightDriveBack.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
        while (opModeIsActive()) {
            drive();
        }
    }
    void drive(){
        double fl = 0.0;
        double fr = 0.0;
        double bl = 0.0;
        double br = 0.0;

        fl += gamepad1.left_stick_y;
        fr += gamepad1.left_stick_y;
        bl += gamepad1.left_stick_y;
        br += gamepad1.left_stick_y;

        fl -= gamepad1.left_stick_x;
        fr += gamepad1.left_stick_x;
        bl += gamepad1.left_stick_x;
        br -= gamepad1.left_stick_x;

        fl -= gamepad1.right_stick_x;
        fr += gamepad1.right_stick_x;
        bl -= gamepad1.right_stick_x;
        br += gamepad1.right_stick_x;

        leftDriveFront.setPower(fl);
        rightDriveFront.setPower(fr);
        leftDriveBack.setPower(bl);
        rightDriveBack.setPower(br);
    }
}