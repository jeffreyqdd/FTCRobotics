package com.gogobot.botcore.trajectory;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

/**
 * 
 * Jerk Minimizing Trajectory using Polynomial Trajectory Generator
 *
 */
public class JerkMinTrajectory {
	final double[] start;
	final double[] end;
	final double T;
	final double[] trajectory;
	
	public JerkMinTrajectory(double[] start, double[] end, double t) {
		super();
		this.start = start;
		this.end = end;
		T = t;
		trajectory = solver();		
	}
	
	

	public double[] getStart() {
		return start;
	}



	public double[] getEnd() {
		return end;
	}



	public double getT() {
		return T;
	}


	// [a0, a1, a2, a3, a4, a5]
	// s(t) = a0 + a1*t + a2*t^2 + a3 * t^3 + a4* t^4 + a5 * t^5
	public double[] getTrajectory() {
		return trajectory;
	}

	// below are convenient functions
	// get position at time t
	// s(t) = a0 + a1*t + a2*t^2 + a3 * t^3 + a4* t^4 + a5 * t^5
	public double getPositionAt(double t) {
		return 	trajectory[0] 
				+ trajectory[1]*t 
				+ trajectory[2]*Math.pow(t,2) 
				+ trajectory[3]*Math.pow(t,3) 
				+ trajectory[4]*Math.pow(t,4) 
				+trajectory[5]*Math.pow(t,5);
	}

	// get velocity at time t
	//s_dot(t) = a1 + 2a2*t + 3a3*t^2 + 4a4*t^3 + 5*a5t^4
	public double getVelocityAt(double t) {
		return 	trajectory[1] 
				+ 2* trajectory[2]*t 
				+ 3* trajectory[3]*Math.pow(t,2) 
				+ 4* trajectory[4]*Math.pow(t,3) 
				+ 5* trajectory[5]*Math.pow(t,4); 
				
	}

	// get acceleration at time t
	// s_double_dot(t) = 2a2 + 6a3*t + 12a4t^2 + 20*a5t^3
	public double getAccelerationAt(double t) {
		return 	2*trajectory[2] 
				+ 6* trajectory[3]*t 
				+ 12* trajectory[4]*Math.pow(t,2) 
				+ 20* trajectory[5]*Math.pow(t,3); 
 
	}

	// get jerk at time t
	// s_triple_dot(t) = 6a3 + 24a4t + 60*a5t^2
	public double getJerkAt(double t) {
		return 	6*trajectory[3] 
				+ 24* trajectory[4]*t 
				+ 60* trajectory[5]*Math.pow(t,2); 
 
	}
	
	/**
	 * Calculate the Jerk Minimizing Trajectory that connects the initial state to
	 * the final state in time T.
	 *
	 * @param start - the vehicles start location given as a length three array
	 *              corresponding to initial values of [s, s_dot, s_double_dot]
	 * @param end   - the desired end state for vehicle. Like "start" this is a
	 *              length three array.
	 * @param T     - The duration, in seconds, over which this maneuver should
	 *              occur.
	 *
	 * @output an array of length 6, each value corresponding to a coefficent in the
	 *         polynomial: s(t) = a_0 + a_1 * t + a_2 * t**2 + a_3 * t**3 + a_4 *
	 *         t**4 + a_5 * t**5
	 *
	 *         EXAMPLE > JMT([0, 10, 0], [10, 10, 0], 1) [0.0, 10.0, 0.0, 0.0, 0.0,
	 *         0.0]
	 */

	private double[] solver() {

		RealMatrix A = new Array2DRowRealMatrix(new double[][] { 
			{ T * T * T, T * T * T * T, T * T * T * T * T },
			{ 3 * T * T, 4 * T * T * T, 5 * T * T * T * T }, 
			{ 6 * T, 12 * T * T, 20 * T * T * T } 
		});

		RealMatrix B = new Array2DRowRealMatrix(
				new double[][] { 
						{ end[0] - (start[0] + start[1] * T + .5 * start[2] * T * T) },
						{end[1] - (start[1] + start[2] * T)}, 
						{end[2] - start[2] } 
					});

		RealMatrix Ai = MatrixUtils.inverse(A);

		RealMatrix C = Ai.multiply(B);

		double[] result = { start[0], start[1], .5 * start[2], C.getEntry(0, 0), C.getEntry(1, 0), C.getEntry(2, 0) };

		return result;
	}

	//eps = 0.01 is good number
	public static boolean closeEnough(double[] poly, double[] target_poly, double eps) {

		if (poly.length != target_poly.length) {
			System.out.println("Your solution didn't have the correct number of terms");
			return false;
		}

		for (int i = 0; i < poly.length; ++i) {
			double diff = poly[i] - target_poly[i];
			if (Math.abs(diff) > eps) {
				System.out.println("At least one of your terms differed from target by more than  " + eps);
				return false;
			}
		}

		return true;
	}

}
