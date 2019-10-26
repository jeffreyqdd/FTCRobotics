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

    VuforiaTrackableDefaultListener listener;


    List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
    OpenGLMatrix lastKnownLocation;
    OpenGLMatrix phoneLocation;

    LinearOpMode myOpMode;
    Robot_MecanumDrive myRobot;

    public static final String VUFORIA_KEY = "AZklCzv/////AAABmTV7jhYz8E2ZgjSFt8rqQUB/fxREWQkyjBEmljutiXICdiA/5wnsf4evcLyh0+kwMHzC11Egu0y+UgK3OqS+BXkZV7eRxl00YNAaB0yp5SFR2+bV5u5DpK8Mu5EkqOnfxRjkNVFcm0NEp+UViCwedzadKLoxBz7kZpWgjNccdVt0rNdAnSAZ/7h0/OM+lig2PT2UVcSOGiG3bbWqL9CE7ffwIg/0Hbl5e4c77Ad4lVXh39EBw46AdFYLLIL/iDBi27olgVEvH4ORLHPsKsEay4rXFoEeXt31y9fTsDoha/yOSWZYK8ycrhPyyHMcS9tlgCo3zYaSzzEjI+EQrq75rnHVJTq3DEomwt30AhnQ6rSf";

    Robot_Navigation() {} //default constructor

    public void targetsAreVisible() throws InterruptedException
    {

        for(VuforiaTrackable trackable : allTrackables) {
            //first get listener and cast it to vuforia trackable default listener

            listener = (VuforiaTrackableDefaultListener) trackable.getListener();

            OpenGLMatrix latestLocation = listener.getUpdatedRobotLocation();

            if (latestLocation != null) {
                lastKnownLocation = latestLocation;
            }

            myOpMode.telemetry.addData("Target: " + trackable.getName(), listener.isVisible());

        }
        myOpMode.telemetry.addData("Last known location: ", formatMatrix(lastKnownLocation));
    }



    public void initVuforia(LinearOpMode opMode/*, Robot_MecanumDrive robot*/) {

        // Save reference to OpMode and Hardware map
        myOpMode = opMode;
        //myRobot = robot;


        /*
         * create new a new vuforia localizer with these parameters
         */
        parameters = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        parameters.useExtendedTracking = false;

        vuforiaLocalizer = ClassFactory.getInstance().createVuforia(parameters);

        /*
         * Load data from the xml files
         */
        VuforiaTrackables visionTargets = this.vuforiaLocalizer.loadTrackablesFromAsset("FTC_2019-20");

        VuforiaTrackable t1 = visionTargets.get(0);
        t1.setName("two_bots");
        t1.setLocation(createMatrix(0,0,0,0,0,0));

        VuforiaTrackable t2 = visionTargets.get(1);
        t2.setName("robot_assemble");
        t2.setLocation(createMatrix(0,0,0,0,0,0));

        VuforiaTrackable t3 = visionTargets.get(2);
        t3.setName("blue_helper");
        t3.setLocation(createMatrix(0,0,0,0,0,0));

        VuforiaTrackable t4 = visionTargets.get(3);
        t4.setName("bb8_cooking");
        t4.setLocation(createMatrix(0,0,0,0,0,0));

        allTrackables.addAll(visionTargets);

        //create phone location
        phoneLocation = createMatrix(0,0,0,0,0,0);

        //init all listeners.
        ((VuforiaTrackableDefaultListener) t1.getListener()).setPhoneInformation(phoneLocation,parameters.cameraDirection);;
        ((VuforiaTrackableDefaultListener) t2.getListener()).setPhoneInformation(phoneLocation,parameters.cameraDirection);;
        ((VuforiaTrackableDefaultListener) t3.getListener()).setPhoneInformation(phoneLocation,parameters.cameraDirection);;
        ((VuforiaTrackableDefaultListener) t4.getListener()).setPhoneInformation(phoneLocation,parameters.cameraDirection);;

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

