package edu.robot.ftc.team17294;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot_Claw{
    private LinearOpMode myOpMode;
    private Servo clawServo;

    private boolean aButton;
    private boolean bButton;

    private double minPos = 0;
    private double maxPos = 0.75;


    public Robot_Claw(LinearOpMode opMode, DriverController dc){
        myOpMode = opMode;

        clawServo = dc.frontClawServo;

        aButton = false;
        bButton = false;
    }

    public void tick()
    {
        doControllerTick();

        updateTelemetry();
    }


    public void doControllerTick()
    {
        if(myOpMode.gamepad2.a && !aButton){
            moveToGrip();
            aButton = true;
        }
        if(myOpMode.gamepad2.b && !bButton){
            moveToRelease();
            bButton = true;
        }

        if(!myOpMode.gamepad2.a)
            aButton = false;
        if(!myOpMode.gamepad2.b)
            bButton = false;
    }


    public void moveToGrip() {
        clawServo.setPosition(maxPos);
    }

    public void moveToRelease() {
        clawServo.setPosition(minPos);
    }

    public void updateTelemetry()
    {
        myOpMode.telemetry.addData("Claw Servo Position: ",
                "s1: (%.2f)", clawServo.getPosition());
    }

}