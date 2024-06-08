package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class myTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();

        telemetry.addLine("hello world!");
        telemetry.update();

        while (opModeIsActive()) {
        }
    }
}


