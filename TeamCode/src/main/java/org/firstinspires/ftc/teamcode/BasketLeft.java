package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="Basket Left", group="Basket")

public class BasketLeft extends LinearOpMode {
    private DcMotorEx lift = null;
    private Servo notClaw = null;

    @Override
    public void runOpMode() {
        lift = hardwareMap.get(DcMotorEx.class, "lift1");
        notClaw = hardwareMap.get(Servo.class, "notClaw");

        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        TrajectoryActionBuilder trajectoryBuilder = drive.actionBuilder(new Pose2d(0, 0, 0))
                .splineToConstantHeading(new Vector2d(54, 18), 0)
                .waitSeconds(1)
                .strafeTo(new Vector2d(54, 0));
        Action trajectory = trajectoryBuilder.build();

        waitForStart();
        Actions.runBlocking(trajectory);
        while(opModeIsActive()) {
            if(lift.getCurrentPosition() <= -1000) {
                lift.setPower(0);
                notClaw.setPosition(-1);
            } else {
                lift.setPower(1);
                notClaw.setPosition(0);
            }
        }
    }
}