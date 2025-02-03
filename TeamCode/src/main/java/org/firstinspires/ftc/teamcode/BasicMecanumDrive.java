package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.acmerobotics.dashboard.config.Config;

@TeleOp(name="Basic: Mecanum Drive", group="Linear Opmode")
@Config

public class BasicMecanumDrive extends LinearOpMode {
    private DcMotor leftDriveFront = null;
    private DcMotor rightDriveFront = null;
    private DcMotor leftDriveBack = null;
    private DcMotor rightDriveBack = null;

    private DcMotorEx lift1 = null;
    private DcMotorEx lift2 = null;
    private DcMotorEx lift3 = null;

    private Servo claw = null;
    private CRServo notClaw = null;

    private boolean clawOpen = false;
    private ElapsedTime clawTime = new ElapsedTime();

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

        lift1 = hardwareMap.get(DcMotorEx.class, "lift1");
        lift2 = hardwareMap.get(DcMotorEx.class, "lift2");
        lift3 = hardwareMap.get(DcMotorEx.class, "lift3");

        lift1.setDirection(DcMotor.Direction.REVERSE);
        lift2.setDirection(DcMotor.Direction.REVERSE);
        lift3.setDirection(DcMotor.Direction.FORWARD);

        lift1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        claw = hardwareMap.get(Servo.class, "claw");
        notClaw = hardwareMap.get(CRServo.class, "notClaw");

        notClaw.setDirection(DcMotor.Direction.FORWARD);

        clawTime.reset();

        waitForStart();
        while (opModeIsActive()) {
            drive();

            lift1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            lift2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            lift3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            lift1.setPower(gamepad1.right_trigger - gamepad1.left_trigger);
            lift2.setPower(gamepad1.right_trigger - gamepad1.left_trigger);

            if(gamepad1.right_bumper && (lift3.getCurrentPosition() < 780 || lift1.getCurrentPosition() > 100)) {
                lift3.setPower(1);
            } else if(gamepad1.left_bumper) {
                lift3.setPower(-1);
            } else {
                lift3.setPower(0);
            }

            if(gamepad1.start) {
                lift1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                lift2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                lift3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }

            if(gamepad1.a && clawTime.milliseconds() >= 250) {
                clawOpen = !clawOpen;
                clawTime.reset();
            }

            if(clawOpen) {
                claw.setPosition(0);
            } else {
                claw.setPosition(1);
            }

            if(gamepad1.y) {
                notClaw.setPower(1);
            } else if(gamepad1.b) {
                notClaw.setPower(-1);
            } else {
                notClaw.setPower(0);
            }

            telemetry.addData("lift1", lift1.getCurrentPosition());
            telemetry.addData("lift2", lift2.getCurrentPosition());
            telemetry.addData("lift3", lift3.getCurrentPosition());
            telemetry.update();
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