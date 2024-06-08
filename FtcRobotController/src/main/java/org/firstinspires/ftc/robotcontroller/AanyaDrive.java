package org.firstinspires.ftc.robotcontroller;


import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
//@TeleOp
@Autonomous
public class AanyaDrive extends LinearOpMode {
    private DistanceSensor sensorRange;
    private DcMotor fRight, fLeft, bRight, bLeft;
    private Rev2mDistanceSensor sensorTimeOfFlight;

    private void setInitialValues() {
        sensorRange = hardwareMap.get(DistanceSensor.class, "sensor_range");

        // you can also cast this to a Rev2mDistanceSensor if you want to use added
        // methods associated with the Rev2mDistanceSensor class.
        sensorTimeOfFlight = (Rev2mDistanceSensor) sensorRange;

        //initialize motors
        fRight = hardwareMap.dcMotor.get("fRight");
        fLeft = hardwareMap.dcMotor.get("fLeft");
        bRight = hardwareMap.dcMotor.get("bRight");
        bLeft = hardwareMap.dcMotor.get("bLeft");

        //set direction of motors
        fRight.setDirection(DcMotorSimple.Direction.REVERSE);
        bRight.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    private void setPowerBasedOnDistance(double distance) {
        if (distance < 40.0) {
            double y = distance/80.0 - 1.0/2.0;
            if (y<0){
                y = 0;
            }
            y *= y;
            setPowers(fRight, fLeft, bRight, bLeft, y, 0, 0);
            fLeft.setPower(0.25);
            bLeft.setPower(-0.25);
            fRight.setPower(-0.25);
            bRight.setPower(0.25);
        } else {
            setPowers(fRight, fLeft, bRight, bLeft,0.5 ,0 ,0);
        }
    } // end of setPowerBasedOnDistance

    public void setPowers(DcMotor fRight, DcMotor fLeft, DcMotor bRight, DcMotor bLeft, double forwardPow, double turnPow, double mecanumPow) {
        double fLPower = forwardPow + turnPow + mecanumPow;
        double fRPower = forwardPow - turnPow - mecanumPow;
        double bLPower = forwardPow + turnPow - mecanumPow;
        double bRPower = forwardPow - turnPow + mecanumPow;

        double scale = 1.0/Math.max(Math.abs(forwardPow) + Math.abs(turnPow) + Math.abs(mecanumPow), 1);
        //double scale = 1.0;//scaleDown(fLPower, fRPower, bLPower, bRPower);

        fLPower *= scale;
        fRPower *= scale;
        bLPower *= scale;
        bRPower *= scale;

        fLeft.setPower(fLPower);
        bLeft.setPower(bLPower);
        fRight.setPower(fRPower);
        bRight.setPower(bRPower);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        setInitialValues();
        waitForStart();
        while (opModeIsActive()) {
            double distance = sensorTimeOfFlight.getDistance(DistanceUnit.CM);
            telemetry.addData("range", String.format("%.2f cm", distance));
            telemetry.update();
            setPowerBasedOnDistance(distance);
        }
    }
} // end of class

