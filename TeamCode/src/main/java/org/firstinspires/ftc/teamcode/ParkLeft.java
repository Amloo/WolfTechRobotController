package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.CompositePosePath;
import com.acmerobotics.roadrunner.PathBuilder;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PosePath;
import com.acmerobotics.roadrunner.ProfileParams;
import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TrajectoryBuilder;
import com.acmerobotics.roadrunner.TrajectoryBuilderParams;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.List;

@Autonomous(name="Park Left", group="Park")

public class ParkLeft extends LinearOpMode {

    @Override
    public void runOpMode() {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        TrajectoryActionBuilder trajectoryBuilder = drive.actionBuilder(new Pose2d(0, 0, 0))
                .strafeToSplineHeading(new Vector2d(-18, 54), 0)
                .strafeTo(new Vector2d(0, 54));
        Action trajectory = trajectoryBuilder.build();

        Actions.runBlocking(trajectory);
    }
}