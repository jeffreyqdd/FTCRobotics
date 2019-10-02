The class Mechanum does the necessary kinetic modeling.

Constructors:

 Mechanum(double length, double width);

    // the measure from center to furthest edge and from center to closest edge.


Method:

 double[] tick(wx, vy, bx);
    //wx is the rotational speed from [-1,1];
    //vy is the y axis of the controller stick (drive)[-1,1];
    //vx is the x axis of the controller stick (slide) [-1,1];

    //returns an array of doubles of size 4, array[i] indexes the ith wheel's power.
    //the value is normalized.