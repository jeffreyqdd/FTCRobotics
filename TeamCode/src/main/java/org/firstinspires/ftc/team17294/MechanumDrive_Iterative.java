
package org.firstinspires.ftc.team17294;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import java.util.Arrays; //for debugging.


/**
 * THIS IS AN OPMODE - player controlled
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel:  Top Left Drive Motor:        "top left drive"        Motor 1
 * Motor channel:  Top Right Drive Motor:       "top right drive"       Motor 2
 * Motor channel:  Bottom Right Drive Motor:    "bot right drive"       Motor 3
 * Motor channel:  Bottom Left Drive Motor:     "bot left drive"        Motor 4
 *
 *
 * These motors correspond to four drive locations spaced 90 degrees around a rectangular
 * Each motor is attached to an mechanum.
 *
 * Robot motion is defined in three different axis motions:
 * - Axial    Forward/Backwards      +ve = Forward
 * - Lateral  Side to Side strafing  +ve = Right
 * - Yaw      Rotating               +ve = Counter Clock Wise
 */


@TeleOp(name="Mecanum", group="Iterative Opmode")
//@Disabled
public class MechanumDrive_Iterative extends OpMode
{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor leftDriveTop = null;
    private DcMotor rightDriveTop = null;
    private DcMotor rightDriveBot = null;
    private DcMotor leftDriveBot = null;

    private double driveAxial = 0;
    private double driveLateral = 0;
    private double driveYaw = 0;

    private Mecanum model = null;



    @Override
    public void init() {
        telemetry.addData("Status", "Initializing");


        //init our class
        model = new Mecanum(10,10);


        //four wheels.
        /*leftDriveTop  = hardwareMap.get(DcMotor.class, "top left drive");
        rightDriveTop = hardwareMap.get(DcMotor.class, "top right drive");
        rightDriveBot = hardwareMap.get(DcMotor.class, "bot right drive");
        leftDriveBot  = hardwareMap.get(DcMotor.class, "bot left drive");*/



        //orientation
        /*leftDriveBot.setDirection(DcMotor.Direction.FORWARD);
        leftDriveTop.setDirection(DcMotor.Direction.FORWARD);
        rightDriveBot.setDirection(DcMotor.Direction.REVERSE);
        rightDriveTop.setDirection(DcMotor.Direction.REVERSE);*/

        //stop any motion if any;
        /*leftDriveTop.setPower(0.0);
        rightDriveTop.setPower(0.0);
        rightDriveBot.setPower(0.0);
        leftDriveBot.setPower(0.0);*/

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "initialized");
    }



    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {


        /*
         * get gamepad inputs here and store it in driveYaw, driveAxial, and driveLat.
         * exponential curve

         * left stick has exponential curve.
         * right stick exponential curve as well
         * triggers have linear relationships at half power
         * all powers are capped at 1;
         */

        driveAxial = Math.pow(-gamepad1.left_stick_y, 3); //need to invert

        driveLateral = Math.pow(gamepad1.left_stick_x, 3)
                + (-gamepad1.left_trigger*0.5 + gamepad1.right_trigger*0.5);

        driveYaw = Math.pow(gamepad1.right_stick_x,3);



        //clip the outputs
        //driveAxial = Range.clip(driveAxial, -1, 1);
        //driveLateral = Range.clip(driveLateral, -1, 1);
        //driveYaw = Range.clip(driveYaw, -1 ,1);



        double[] driveVector = model.tick(driveYaw, driveAxial, driveLateral);


       /* leftDriveTop.setPower(driveVector[0]);
        rightDriveTop.setPower(driveVector[1]);
        rightDriveBot.setPower(driveVector[2]);
        leftDriveBot.setPower(driveVector[3]);*/

        double[] temporary = {driveYaw,  driveAxial, driveLateral};

        for(int i = 0; i < driveVector.length; i++)
        {
            driveVector[i] = (double) Math.round(driveVector[i] * 1000) / 1000.0;
        }
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Wheel Debug", Arrays.toString(driveVector));
        telemetry.addData("Controller Debug", Arrays.toString(temporary));
    }

    //Code to run ONCE after the driver hits STOP

    @Override
    public void stop() {
    }

}