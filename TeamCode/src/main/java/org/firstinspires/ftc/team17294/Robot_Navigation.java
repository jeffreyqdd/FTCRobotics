package org.firstinspires.ftc.team17294;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.robot.Robot;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;



import java.util.ArrayList;
import java.util.List;

import static android.R.attr.angle;
import static android.R.attr.orientation;
import static android.R.attr.targetName;
import static android.view.View.X;

public class Robot_Navigation {

    VuforiaLocalizer vuforiaLocalizer;
    VuforiaLocalizer.Parameters parameters;
    VuforiaTrackables visionTargets;
    VuforiaTrackable target;
    VuforiaTrackableDefaultListener listener;

    OpenGLMatrix lastKnownLocation;
    OpenGLMatrix phoneLocation;

    LinearOpMode myOpMode;
    Robot_MecanumDrive myRobot;

    public static final String VUFORIA_KEY = "AZklCzv/////AAABmTV7jhYz8E2ZgjSFt8rqQUB/fxREWQkyjBEmljutiXICdiA/5wnsf4evcLyh0+kwMHzC11Egu0y+UgK3OqS+BXkZV7eRxl00YNAaB0yp5SFR2+bV5u5DpK8Mu5EkqOnfxRjkNVFcm0NEp+UViCwedzadKLoxBz7kZpWgjNccdVt0rNdAnSAZ/7h0/OM+lig2PT2UVcSOGiG3bbWqL9CE7ffwIg/0Hbl5e4c77Ad4lVXh39EBw46AdFYLLIL/iDBi27olgVEvH4ORLHPsKsEay4rXFoEeXt31y9fTsDoha/yOSWZYK8ycrhPyyHMcS9tlgCo3zYaSzzEjI+EQrq75rnHVJTq3DEomwt30AhnQ6rSf";

    Robot_Navigation() {} //default constructor

    public void targetsAreVisible() throws InterruptedException
    {
        OpenGLMatrix latestLocation = listener.getUpdatedRobotLocation();

        if(latestLocation != null)
        {
            lastKnownLocation = latestLocation;
        }

        myOpMode.telemetry.addData("Tracking: " + target.getName(), listener.isVisible());
        myOpMode.telemetry.addData("Last known location: ", formatMatrix(lastKnownLocation));

    }



    public void initVuforia(LinearOpMode opMode/*, Robot_MecanumDrive robot*/) {

        // Save reference to OpMode and Hardware map
        myOpMode = opMode;
        //myRobot = robot;

        parameters = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        vuforiaLocalizer = ClassFactory.getInstance().createVuforia(parameters);

        visionTargets = vuforiaLocalizer.loadTrackablesFromAsset("FTC_2019-20");

        target = visionTargets.get(0);
        target.setName("twoBots");
        target.setLocation(createMatrix(0,0,0,0,0,0));

        /*target = visionTargets.get(1);
        target.setName("robotAssemble");
        target.setLocation(createMatrix(0,0,0,0,0,0));

        target = visionTargets.get(2);
        target.setName("blueHelper");
        target.setLocation(createMatrix(0,0,0,0,0,0));

        target = visionTargets.get(3);
        target.setName("bb8Cooking");
        target.setLocation(createMatrix(0,0,0,0,0,0));*/

        phoneLocation = createMatrix(0,0,0,0,0,0);

        listener = (VuforiaTrackableDefaultListener) target.getListener();
        listener.setPhoneInformation(phoneLocation,parameters.cameraDirection);


        //do setup
        lastKnownLocation = createMatrix(0,0,0,0,0,0);
        visionTargets.activate();

    }

    public OpenGLMatrix createMatrix(float x, float y, float z, float u, float v, float w)
    {
        return OpenGLMatrix.translation(x,y,z).
                multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ , AngleUnit.DEGREES, u, v, w));
    }
    public String formatMatrix(OpenGLMatrix matrix)
    {
        return matrix.formatAsTransform();
    }

}

