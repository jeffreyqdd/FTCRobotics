/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.team17294;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="Testing Op Mode", group="Linear Opmode")
//@Disabled
public class TestingOP extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor motor1 = null;
    private DcMotor motor2 = null;
    private DcMotor motor3 = null;
    private DcMotor motor4 = null;

    private boolean toggle1 = false, toggle2 = false, toggle3 = false, toggle4 = false;;
    private double speed = 0;
    private double speedToggle = 0;

    @Override
    public void runOpMode() throws InterruptedException{
        //init
        setUp(Global.LEFT_TOP_MOTOR, Global.RIGHT_TOP_MOTOR,
                Global.RIGHT_BOT_MOTOR, Global.LEFT_BOT_MOTOR);

        // Wait for the game to start
        waitForStart();

        //start
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while(opModeIsActive())
        {

            //get inputs
            if(gamepad1.right_bumper) speedToggle = 0.75;
            if(gamepad1.left_bumper) speedToggle = -0.75;

            if(gamepad1.y) toggle1 = !toggle1;
            if(gamepad1.b) toggle2 = !toggle2;
            if(gamepad1.a) toggle3 = !toggle3;
            if(gamepad1.x) toggle4 = !toggle4;

            speed = - gamepad1.left_stick_y;

            if(gamepad1.dpad_down)
            {
                toggle1 = false;
                toggle2 = false;
                toggle3 = false;
                toggle4 = false;
            }
            if(gamepad1.dpad_up)
            {
                toggle1 = true;
                toggle2 = true;
                toggle3 = true;
                toggle4 = true;
            }

            //set speeds

            if(toggle1) motor1.setPower(speedToggle);
            else motor1.setPower(speed);

            if(toggle2) motor2.setPower(speedToggle);
            else motor2.setPower(speed);

            if(toggle3) motor3.setPower(speedToggle);
            else motor3.setPower(speed);

            if(toggle4) motor4.setPower(speedToggle);
            else motor4.setPower(speed);

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "toggle: (%.2f), controller: (%.2f)",
                    speedToggle, speed);
            telemetry.update();
        }

    }


    public void setUp(String m1,String m2, String m3, String m4)
    {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize
        motor1  = hardwareMap.get(DcMotor.class, m1);
        motor2  = hardwareMap.get(DcMotor.class, m2);
        motor3  = hardwareMap.get(DcMotor.class, m3);
        motor4  = hardwareMap.get(DcMotor.class, m4);


        motor1.setDirection(DcMotor.Direction.FORWARD);
        motor2.setDirection(DcMotor.Direction.REVERSE);
        motor3.setDirection(DcMotor.Direction.REVERSE);
        motor4.setDirection(DcMotor.Direction.FORWARD);

        motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor4.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }


}
