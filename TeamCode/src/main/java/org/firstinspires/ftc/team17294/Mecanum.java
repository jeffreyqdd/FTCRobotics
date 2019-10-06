package org.firstinspires.ftc.team17294;

class Mecanum {

    private double[][] MODEL = {
            {1.0, 1.0, 1.0},
            {-1.0, 1.0, -1.0},
            {-1.0, 1.0, 1.0},
            {1.0, 1.0, -1.0}
    };

    private double[] VECTOR;


    Mecanum(double length, double width)
    {
        VECTOR = new double[4];

        /*MODEL[0][0] = -length - width;
        MODEL[1][0] = length + width;
        MODEL[2][0] = length + width;
        MODEL[3][0] = -length - width;*/


    }

    double[] tick(double wx, double by, double bx)
    {
        double[] movement = {wx, by, bx};

        for(int i = 0; i < 4; i++) VECTOR[i] = 0.0; //reset

        /* keep track of count, and current movement vector */
        for(int k = 0, curr = 0; k < 4; k++) VECTOR[k] += movement[curr] * MODEL[k][curr];
        for(int k = 0, curr = 1; k < 4; k++) VECTOR[k] += movement[curr] * MODEL[k][curr];
        for(int k = 0, curr = 2; k < 4; k++) VECTOR[k] += movement[curr] * MODEL[k][curr];

        //normalize
        double max = 1;
        for(double d : VECTOR) max = Math.max(max, Math.abs(d));
        for(int i = 0; i < 4; i++) VECTOR[i] /= max;

        return VECTOR;
    }

}