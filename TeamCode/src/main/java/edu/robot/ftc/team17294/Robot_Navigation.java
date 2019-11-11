package edu.robot.ftc.team17294;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.robot.Robot;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

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
import org.firstinspires.ftc.team17294.R;


import java.util.ArrayList;
import java.util.List;

import static android.R.attr.angle;
import static android.R.attr.orientation;
import static android.R.attr.targetName;
import static android.view.View.X;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

public class Robot_Navigation {

    VuforiaLocalizer vuforiaLocalizer;
    VuforiaTrackableDefaultListener listener;
    VuforiaTrackables visionTargets;

    OpenGLMatrix lastKnownLocation;
    OpenGLMatrix phoneLocation;

    LinearOpMode myOpMode;

    public static final String VUFORIA_KEY = "AZklCzv/////AAABmTV7jhYz8E2ZgjSFt8rqQUB/fxREWQkyjBEmljutiXICdiA/5wnsf4evcLyh0+kwMHzC11Egu0y+UgK3OqS+BXkZV7eRxl00YNAaB0yp5SFR2+bV5u5DpK8Mu5EkqOnfxRjkNVFcm0NEp+UViCwedzadKLoxBz7kZpWgjNccdVt0rNdAnSAZ/7h0/OM+lig2PT2UVcSOGiG3bbWqL9CE7ffwIg/0Hbl5e4c77Ad4lVXh39EBw46AdFYLLIL/iDBi27olgVEvH4ORLHPsKsEay4rXFoEeXt31y9fTsDoha/yOSWZYK8ycrhPyyHMcS9tlgCo3zYaSzzEjI+EQrq75rnHVJTq3DEomwt30AhnQ6rSf";


    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private static final boolean PHONE_IS_PORTRAIT = false;

    ArrayList<String> targetsSeen = new ArrayList<>();



    public Robot_Navigation(LinearOpMode opMode)
    {

        // Save reference to OpMode and Hardware map
        myOpMode = opMode;

        /*
         * create new a new vuforia localizer with these parameters
         */


        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        parameters.useExtendedTracking = false;
        parameters.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;
        //set a hint


        //create the localizer
        vuforiaLocalizer = ClassFactory.getInstance().createVuforia(parameters);

        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 8);
        /*
         * Load data from the xml files
         */

        visionTargets = this.vuforiaLocalizer.loadTrackablesFromAsset("Skystone");

        visionTargets.get(5).setName("cpo");
        visionTargets.get(5).setLocation(createMatrix(0,0,0,0,0,0));

        visionTargets.get(6).setName("yellow thing");
        visionTargets.get(6).setLocation(createMatrix(0,0,0,0,0,0));

        visionTargets.get(7).setName("blue thing");
        visionTargets.get(7).setLocation(createMatrix(0,0,0,0,0,0));

        visionTargets.get(8).setName("r2d2 and bb8");
        visionTargets.get(8).setLocation(createMatrix(0,0,0,0,0,0));

        visionTargets.get(9).setName("bb8");
        visionTargets.get(9).setLocation(createMatrix(0,0,0,0,0,0));

        visionTargets.get(10).setName("salute");
        visionTargets.get(10).setLocation(createMatrix(0,0,0,0,0,0));

        visionTargets.get(11).setName("dj");
        visionTargets.get(11).setLocation(createMatrix(0,0,0,0,0,0));

        visionTargets.get(12).setName("new r2d2");
        visionTargets.get(12).setLocation(createMatrix(0,0,0,0,0,0));



        //create phone location
        phoneLocation = createMatrix(0,0,0,0,0,0);


        //do setup
        lastKnownLocation = createMatrix(0,0,0,0,0,0);

    }

    public void tick()
    {
        targetsAreVisible();
        updateTelemetry();
    }
    public void targetsAreVisible()
    {
        targetsSeen.clear();

        for(VuforiaTrackable trackable : visionTargets) {
            //first get listener and cast it to vuforia trackable default listener

            listener = (VuforiaTrackableDefaultListener) trackable.getListener();

            if(listener.isVisible())
                targetsSeen.add(trackable.getName());


            OpenGLMatrix latestLocation = listener.getUpdatedRobotLocation();

            if (latestLocation != null) {
                lastKnownLocation = latestLocation;
            }



        }

    }


    public void start()
    {
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

    public void updateTelemetry()
    {
        myOpMode.telemetry.addData("Vuforia Last known location: ", formatMatrix(lastKnownLocation));
        myOpMode.telemetry.addData("Vuforia Targets Seen: ", targetsSeen.toString());

    }

}

