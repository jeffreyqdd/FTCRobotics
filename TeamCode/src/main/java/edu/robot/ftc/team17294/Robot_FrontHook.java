package edu.robot.ftc.team17294;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import java.sql.Driver;

public class Robot_FrontHook {

    private double minValRight = 0;
    private double minValLeft = 0.75;

    private double maxValRight = 0.75;
    private double maxValLeft = 0;

    private Servo rightFrontServo;
    private Servo leftFrontServo;


    private LinearOpMode myOpMode;

    public Robot_FrontHook(LinearOpMode opMode, DriverController dc)
    {
        myOpMode = opMode;

        rightFrontServo = dc.rightFrontServo;
        leftFrontServo = dc.leftFrontServo;

        rightFrontServo.setPosition(minValRight);
        leftFrontServo.setPosition(minValLeft);
    }

    public void tick()
    {
        doControllerTick();
        updateTelemetry();
    }


    public void doControllerTick()
    {
        if(myOpMode.gamepad1.right_bumper) toggleOn();
        if(myOpMode.gamepad1.left_bumper) toggleOff();
    }


    public void toggleOn()
    {
        rightFrontServo.setPosition(maxValRight);
        leftFrontServo.setPosition(maxValLeft);
    }

    public void toggleOff()
    {
        rightFrontServo.setPosition(minValRight);
        leftFrontServo.setPosition(minValLeft);
    }

    public void updateTelemetry()
    {
        myOpMode.telemetry.addData("Front Hook Servo Positions: ",
                "s1: (%.2f), s2: (%.2f)",
                leftFrontServo.getPosition(),
                rightFrontServo.getPosition());
    }

}
