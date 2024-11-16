package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Basic: Mecanum Drive", group="Linear Opmode")

public class BasicMecanumDrive extends LinearOpMode {
    private DcMotor leftDriveFront = null;
    private DcMotor rightDriveFront = null;
    private DcMotor leftDriveBack = null;
    private DcMotor rightDriveBack = null;

    private DcMotorEx lift = null;

    private Servo notClaw = null;

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

        lift = hardwareMap.get(DcMotorEx.class, "lift");

        lift.setDirection(DcMotor.Direction.REVERSE);

        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        notClaw = hardwareMap.get(Servo.class, "claw");

        waitForStart();
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        while (opModeIsActive()) {
            drive();

            if(lift.getMode() != DcMotor.RunMode.RUN_WITHOUT_ENCODER) {
                lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }
            lift.setPower(0);
            if(lift.getCurrentPosition() < 16000) {
                lift.setPower(gamepad1.left_trigger);
            }
            lift.setPower(lift.getPower() - gamepad1.right_trigger);
            if(lift.getPower() == 0 && lift.getCurrentPosition() < 500) {
                lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }

            if(gamepad1.right_bumper) {
                if(notClaw.getPosition() == 0) {
                    notClaw.setPosition(1);
                } else {
                    notClaw.setPosition(0);
                }
            }
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