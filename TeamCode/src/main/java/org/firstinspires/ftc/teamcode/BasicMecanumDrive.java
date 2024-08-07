package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name="Basic: Mecanum Drive", group="Linear Opmode")

public class BasicMecanumDrive extends LinearOpMode {
    private DcMotorEx leftDriveFront = null;
    private DcMotorEx rightDriveFront = null;
    private DcMotorEx leftDriveBack = null;
    private DcMotorEx rightDriveBack = null;

    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftDriveFront = hardwareMap.get(DcMotorEx.class, "left_drive_front");
        rightDriveFront = hardwareMap.get(DcMotorEx.class, "right_drive_front");
        leftDriveBack  = hardwareMap.get(DcMotorEx.class, "left_drive_back");
        rightDriveBack = hardwareMap.get(DcMotorEx.class, "right_drive_back");

        leftDriveFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDriveFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftDriveBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDriveBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftDriveFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightDriveFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftDriveBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightDriveBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftDriveFront.setDirection(DcMotor.Direction.REVERSE);
        rightDriveFront.setDirection(DcMotor.Direction.REVERSE);
        leftDriveBack.setDirection(DcMotor.Direction.FORWARD);
        rightDriveBack.setDirection(DcMotor.Direction.FORWARD);
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

        leftDriveFront.setVelocity(fl * 1000);
        rightDriveFront.setVelocity(fr * 1000);
        leftDriveBack.setVelocity(bl * 1000);
        rightDriveBack.setVelocity(br * 1000);
    }
}