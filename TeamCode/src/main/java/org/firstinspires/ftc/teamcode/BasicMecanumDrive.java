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

    private DcMotor lift = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftDriveFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightDriveFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftDriveBack  = hardwareMap.get(DcMotor.class, "leftBack");
        rightDriveBack = hardwareMap.get(DcMotor.class, "rightBack");

        leftDriveFront.setDirection(DcMotor.Direction.FORWARD);
        rightDriveFront.setDirection(DcMotor.Direction.FORWARD);
        leftDriveBack.setDirection(DcMotor.Direction.REVERSE);
        rightDriveBack.setDirection(DcMotor.Direction.REVERSE);

        lift = hardwareMap.get(DcMotor.class, "lift");

        waitForStart();
        while (opModeIsActive()) {
            drive();

            lift.setPower(gamepad1.right_trigger - gamepad1.left_trigger);
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

        leftDriveFront.setPower(fl * 0.5);
        rightDriveFront.setPower(fr * 0.5);
        leftDriveBack.setPower(bl * 0.5);
        rightDriveBack.setPower(br * 0.5);
    }
}