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

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import edu.robot.ftc.team17294.*;


@TeleOp(name="Combined test op mode", group="Linear Opmode")
//@Disabled
public class CombinedOpModeTest extends LinearOpMode {

    //Robot_Claw rClaw;
    Robot_FrontHook rHook;
    //Robot_Lift rLift;
    Robot_MecanumDrive rDrive;
    //Robot_Navigation rNav;
    Robot_SensorDistance rDistance;

    @Override
    public void runOpMode() throws InterruptedException{

        //init bot and nav
        //rClaw   = new Robot_Claw(this);
        rHook   = new Robot_FrontHook(this);
        //rLift   = new Robot_Lift(this);
        rDrive  = new Robot_MecanumDrive(this);
        //rNav    = new Robot_Navigation(this);


        rDistance = new Robot_SensorDistance(this);

        waitForStart(); //wait until game starts

        //start vuforia.
        //rNav.start();
        while(opModeIsActive()) {

            /* ROBOT CLAW CONTROL */
            //rClaw.tick();

            /* ROBOT FRONT HOOK CONTROL */
            rHook.tick();

            /* ROBOT LIFT CONTROL */
            //rLift.tick();

            /* ROBOT MOVEMENT */
            rDrive.tick();

            /* ROBOT VISION */
            //rNav.tick();

            /* ROBOT DISTANCE */
            rDistance.tick();


            /* flush telemetry */
            telemetry.update();
        }

    }






}
