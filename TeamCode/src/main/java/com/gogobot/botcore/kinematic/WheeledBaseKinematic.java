package com.gogobot.botcore.kinematic;

import java.util.List;

public interface WheeledBaseKinematic {
	/**
	 * from vehicle base velocity to all individual wheels turning velocity
	 * @param  Plane3DoFVelocity chassis velocity with longitudal, transversal and angular
	 * @return a collection of angular velocity for all wheels
	 * 
 	 */
	
	public List<Double> cartesianVelocityToWheelVelocities(final CartesianVelocity chassisVelocity);
	
	
	/**
	 * from all individual wheels turning velocity to vehicle base velocity
	 * @param wheelVelocities Collection of angular velocity for all wheels
	 * @return chassis velocity
	 */
	public CartesianVelocity wheelVelocitiesToCartesianVelocity(final List<Double> wheelVelocities);

	
	/**
	 * This one go hand-in-hand with wheelPositionsToCartesianPosition. Without proper initiation	
	 * 	 last known bot position will be initiated as (0,0,0), which may not be the case; 
	 *   and last known wheel position will be set as (0,0,0,0), which may also not be right.
	 * @param wheelPositions
	 */
	
	//public void setLastKnownBotPositionAndWheelPosition(final CartesianPosition botPosition, final List<Double> wheelPositions );
	
	/**
	 * from individual wheels odometry reading (position) to vehicles new position
	 * @param wheelPositions collection of angular position of all wheels
	 * @return cartesian position that contains longitudinal, transversal and orientation
	 */
	public CartesianPosition wheelPositionsToCartesianPosition(final List<Double> wheelPositions);
	
	
	/**
	 * from bot position to wheel odometry
	 * @param botPosition - position that contains longitudinal, transversal and orientation
	 * @return all wheel positions
	 */
	public List<Double> cartesianPositionToWheelPositions(final CartesianPosition botPosition);

}
