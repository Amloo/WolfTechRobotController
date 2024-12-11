package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Basic: Mecanum Drive", group="Linear Opmode")

public class BasicMecanumDrive extends LinearOpMode {
    private DcMotor leftDriveFront = null;
    private DcMotor rightDriveFront = null;
    private DcMotor leftDriveBack = null;
    private DcMotor rightDriveBack = null;

    private DcMotorEx lift1 = null;
    private DcMotor lift2 = null;

    private CRServo notClaw = null;

    private DcMotorEx lift3 = null;

    private Servo claw = null;
    private Servo rotate = null;

    private boolean clawOpen = false;
    private ElapsedTime clawTime = new ElapsedTime();
    private boolean rotateOpen = false;
    private ElapsedTime rotateTime = new ElapsedTime();

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

        lift1.setDirection(DcMotor.Direction.REVERSE);
        lift2.setDirection(DcMotor.Direction.FORWARD);

        lift1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        notClaw = hardwareMap.get(CRServo.class, "notClaw");

        lift3 = hardwareMap.get(DcMotorEx.class, "lift3");

        lift3.setDirection(DcMotor.Direction.REVERSE);

        lift3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        claw = hardwareMap.get(Servo.class, "claw");
        rotate = hardwareMap.get(Servo.class, "rotate");

        clawTime.reset();
        rotateTime.reset();

        waitForStart();
        lift1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        while (opModeIsActive()) {
            drive();

            if(lift1.getMode() != DcMotor.RunMode.RUN_WITHOUT_ENCODER) {
                lift1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                lift2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }
            lift1.setPower(0);
            lift2.setPower(0);
            if(lift1.getCurrentPosition() < 16000) {
                lift1.setPower(gamepad1.left_trigger);
                lift2.setPower(gamepad1.left_trigger);
            }
            lift1.setPower(lift1.getPower() - gamepad1.right_trigger);
            lift2.setPower(lift2.getPower() - gamepad1.right_trigger);
            if(lift1.getPower() == 0 && lift1.getCurrentPosition() < 500) {
                lift1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                lift2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }

            if(lift3.getMode() != DcMotor.RunMode.RUN_WITHOUT_ENCODER) {
                lift3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }
            lift3.setPower(0);
            if(lift3.getCurrentPosition() < 16000 && gamepad1.left_bumper) {
                lift3.setPower(1);
            }
            if(gamepad1.right_bumper) {
                lift3.setPower(-1);
            }
            if(lift3.getPower() == 0 && lift3.getCurrentPosition() < 500) {
                lift3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }

            if(gamepad1.x) {
                notClaw.setPower(1);
            } else if(gamepad1.y) {
                notClaw.setPower(-1);
            } else {
                notClaw.setPower(0);
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

            if(gamepad1.b && rotateTime.milliseconds() >= 250) {
                rotateOpen = !rotateOpen;
                rotateTime.reset();
            }

            if(rotateOpen) {
                rotate.setPosition(0);
            } else {
                rotate.setPosition(1);
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