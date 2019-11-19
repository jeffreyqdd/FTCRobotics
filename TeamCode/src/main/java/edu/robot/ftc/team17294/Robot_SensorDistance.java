package edu.robot.ftc.team17294;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.sql.Driver;


public class Robot_SensorDistance {
    private DistanceSensor leftWaffleSensor;
    private DistanceSensor rightWaffleSensor;
    private LinearOpMode myOpMode;

    private double leftWaffleDist = 0.0;
    private double rightWaffleDist = 0.0;

    public Robot_SensorDistance(LinearOpMode opMode)
    {
        myOpMode = opMode;

        DriverController dc = new DriverController(opMode.hardwareMap);
        leftWaffleSensor = dc.leftWaffleSensor;
        rightWaffleSensor = dc.rightWaffleSensor;
    }

    public void tick()
    {
        leftWaffleDist = leftWaffleSensor.getDistance(DistanceUnit.MM);
        rightWaffleDist = rightWaffleSensor.getDistance(DistanceUnit.MM);

        addTelemetry();
    }


    public void addTelemetry()
    {
        myOpMode.telemetry.addData("left value:", leftWaffleDist);
        myOpMode.telemetry.addData("right value:", rightWaffleDist);
    }

}
