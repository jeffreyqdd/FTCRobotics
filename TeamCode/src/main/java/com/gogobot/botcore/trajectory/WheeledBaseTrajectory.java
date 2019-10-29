package com.gogobot.botcore.trajectory;

import java.util.List;

import com.gogobot.botcore.kinematic.CartesianVelocity;

public interface WheeledBaseTrajectory {
	
	/**
	 * Get each wheels angular velocity at time t
	 * @param t
	 * @return
	 */
	public List<Double> getWheelVelocityAt(double t);

	
	/**
	 * Get (bot) Cartesian velocity at time t
	 * @param t
	 * @return
	 */
	public CartesianVelocity getCartesianVelocityAt(double t);

	/**
	 * Get the duration of the trajectory.
	 * @return
	 */
	public double getT();
}
