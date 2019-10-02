
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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


@TeleOp(name="Mechanum", group="Iterative Opmode")
@Disabled


public class OmniWheels_Iterative extends OpMode
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

    Mechanum model = null;



    @Override
    public void init() {
        telemetry.addData("Status", "Initializing");


        //init our class
        model = new Mechanum(10,10);

        //four wheels.
        leftDriveTop  = hardwareMap.get(DcMotor.class, "top left drive");
        rightDriveTop = hardwareMap.get(DcMotor.class, "top right drive");
        rightDriveBot = hardwareMap.get(DcMotor.class, "bot right drive");
        leftDriveBot  = hardwareMap.get(DcMotor.class, "bot left drive");



        //orientation
        leftDriveBot.setDirection(DcMotor.Direction.FORWARD);
        leftDriveTop.setDirection(DcMotor.Direction.FORWARD);
        rightDriveBot.setDirection(DcMotor.Direction.REVERSE);
        rightDriveTop.setDirection(DcMotor.Direction.REVERSE);


        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }



    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {



        //get gamepad inputs here and store it in driveYaw, driveAxial, and driveLat.
        //exponential curve.
        driveAxial = Math.pow(-gamepad1.left_stick_y, 3);
        driveLateral = Math.pow(gamepad1.left_stick_x, 3) + (-gamepad1.left_trigger + gamepad1.right_trigger);
        driveYaw = Math.pow(gamepad1.right_stick_x,3);

        //clip the outputs
        driveAxial = Range.clip(driveAxial, -1, 1);
        driveLateral = Range.clip(driveLateral, -1, 1);
        driveYaw = Range.clip(driveYaw, -1 ,1);



        double[] driveVector = model.tick(driveYaw, driveAxial, driveLateral);


        leftDriveTop.setPower(driveVector[0]);
        rightDriveTop.setPower(driveVector[1]);
        rightDriveBot.setPower(driveVector[2]);
        leftDriveBot.setPower(driveVector[3]);


        telemetry.addData("Status", "Run Time: " + runtime.toString());
    }


    //Code to run ONCE after the driver hits STOP

    @Override
    public void stop() {
    }

}