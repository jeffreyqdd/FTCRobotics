package com.gogobot.botcore.kinematic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.lang.Math;

public class FourMecanumKinematic implements WheeledBaseKinematic {
	


	FourMecanumKinematicConfiguration config;
	
	// angle position
//	List<Double> lastWheelPositions;
	

	// 
//	boolean lastWheelPositionInitialized;
	
//	CartesianPosition position;

	
	public FourMecanumKinematic(FourMecanumKinematicConfiguration config) {
		super();
		
		setConfig(config);
	//	setLastWheelPositionInitialized(false);
		
		
	}
	
	@Override
	public List<Double> cartesianVelocityToWheelVelocities(CartesianVelocity chassisVelocity) {
		
		List<Double> wheelVelocities = new LinkedList<Double>();
		
		double RadPerSec_FromX, RadPerSec_FromY, RadPerSec_FromTheta;
		
		if (config.wheelRadius == 0 || config.rotationRatio == 0 || config.slideRatio == 0) {
			throw new IllegalArgumentException("The wheelRadius, RotationRatio or the SlideRatio are not allowed to be zero");
		}
		
		
	    // RadPerSec_FromX = longitudinalVelocity / config.wheelRadius;
	    RadPerSec_FromX = chassisVelocity.longitudinalVelocity / config.wheelRadius;
	    RadPerSec_FromY = chassisVelocity.transversalVelocity / (config.wheelRadius * config.slideRatio) ;

	    // Calculate Rotation Component
	    RadPerSec_FromTheta = ((config.lengthBetweenFrontAndRearWheels + config.lengthBetweenFrontWheels) / (2.0 * config.wheelRadius)) * chassisVelocity.angularVelocity;;

	    wheelVelocities.add( RadPerSec_FromX + RadPerSec_FromY + RadPerSec_FromTheta );
	    wheelVelocities.add( RadPerSec_FromX - RadPerSec_FromY - RadPerSec_FromTheta );
	    wheelVelocities.add( RadPerSec_FromX + RadPerSec_FromY - RadPerSec_FromTheta );
	    wheelVelocities.add( RadPerSec_FromX - RadPerSec_FromY + RadPerSec_FromTheta );

		
		return wheelVelocities;
	}

	@Override
	public CartesianVelocity wheelVelocitiesToCartesianVelocity(List<Double> wheelVelocities) {
		
		 if (wheelVelocities.size() < 4)
		      throw new IllegalArgumentException("To less wheel velocities");
		 
		 if (config.lengthBetweenFrontAndRearWheels == 0 || config.lengthBetweenFrontWheels == 0) {
			 throw new IllegalArgumentException ("The lengthBetweenFrontAndRearWheels or the lengthBetweenFrontWheels are not allowed to be zero");
		    }
		 
		 CartesianVelocity chassisVelocity = new CartesianVelocity() ;
		 
		 double wheel_radius_per4 = config.wheelRadius / 4.0;
		 
		 double geom_factor = (config.lengthBetweenFrontAndRearWheels / 2.0) + (config.lengthBetweenFrontWheels / 2.0);
		 
		 //now convert this to a vx,vy,vtheta
		 chassisVelocity.longitudinalVelocity = (wheelVelocities.get(0) + wheelVelocities.get(1) + wheelVelocities.get(2) + wheelVelocities.get(3)) * wheel_radius_per4 ;
		 chassisVelocity.transversalVelocity = (wheelVelocities.get(0) - wheelVelocities.get(1) + wheelVelocities.get(2) - wheelVelocities.get(3)) * wheel_radius_per4;
		 chassisVelocity.angularVelocity = (wheelVelocities.get(0) - wheelVelocities.get(1) - wheelVelocities.get(2) + wheelVelocities.get(3)) * (wheel_radius_per4 / geom_factor);

		   
		
		return chassisVelocity;
	}

	/*
	@Override
	public void setLastKnownBotPositionAndWheelPosition(CartesianPosition botPosition, List<Double> wheelPositions) {
		lastWheelPositions = new LinkedList<Double>();
		lastWheelPositions.addAll(wheelPositions);
		
	    position = botPosition;
	    
	    lastWheelPositionInitialized = true;
		
	}
	*/
	
	@Override
	/**
	 * This is a sateless method. wheel position is the change since last update.
	 */
	public CartesianPosition wheelPositionsToCartesianPosition(List<Double> deltaWheelPositions) {
		
		CartesianPosition deltaPosition = new CartesianPosition();
		
		 double angle = 0.0;

		 if (deltaWheelPositions.size() < 4)
		      throw new IllegalArgumentException("To less wheel positions");
		 
		 if (config.lengthBetweenFrontAndRearWheels == 0 || config.lengthBetweenFrontWheels == 0) {
			 throw new IllegalArgumentException ("The lengthBetweenFrontAndRearWheels or the lengthBetweenFrontWheels are not allowed to be zero");
		    }
		 
		 
//		 if (lastWheelPositionInitialized == false) {
//			 
//			 List<Double> e = new LinkedList<Double>();
//			 for (int i=0; i<4; i++)
//				 e.add(Double.valueOf(0.0));
//			 setLastKnownBotPositionAndWheelPosition (new CartesianPosition(0.0, 0.0, 0.0), e);		        		        
//		 }
//		 
		   double deltaLongitudinalPos;
		   double deltaTransversalPos;

		   double wheel_radius_per4 = config.wheelRadius / 4.0;

		   double geom_factor = (config.lengthBetweenFrontAndRearWheels / 2.0) + (config.lengthBetweenFrontWheels / 2.0);

		    double deltaPositionW1 = (deltaWheelPositions.get(0) );
		    double deltaPositionW2 = (deltaWheelPositions.get(1) );
		    double deltaPositionW3 = (deltaWheelPositions.get(2) );
		    double deltaPositionW4 = (deltaWheelPositions.get(3) );
		    
		    //Collections.copy(dest, source);
		    //Collections.copy(lastWheelPositions, deltaWheelPositions);


		    deltaLongitudinalPos = (deltaPositionW1 + deltaPositionW2 + deltaPositionW3 + deltaPositionW4) * wheel_radius_per4;
		    deltaTransversalPos = (deltaPositionW1 - deltaPositionW2 + deltaPositionW3 - deltaPositionW4) * wheel_radius_per4;		    
		    angle += (deltaPositionW1 - deltaPositionW2 - deltaPositionW3 + deltaPositionW4) * (wheel_radius_per4 / geom_factor);
		    

		    deltaPosition.longitudinalPosition += deltaLongitudinalPos * Math.cos(angle) - deltaTransversalPos * Math.sin(angle);
		    deltaPosition.transversalPosition += deltaLongitudinalPos * Math.sin(angle) + deltaTransversalPos * Math.cos(angle);
		    deltaPosition.orientation = angle;


		return deltaPosition;
	}
	
	@Override
	public List<Double> cartesianPositionToWheelPositions(CartesianPosition botPosition) {
	    double Rad_FromX,  Rad_FromY, Rad_FromTheta;
	    List<Double>wheelPositions = new ArrayList<Double>();

	    if (config.wheelRadius == 0 || config.rotationRatio == 0 || config.slideRatio == 0) {
	    	throw new IllegalArgumentException ("The wheelRadius, RotationRatio or the SlideRatio are not allowed to be zero");
	    }

	    // RadPerSec_FromX = longitudinalVelocity / config.wheelRadius;
	    Rad_FromX = botPosition.longitudinalPosition / config.wheelRadius;
	    Rad_FromY = botPosition.transversalPosition / (config.wheelRadius * config.slideRatio) ;

	    // Calculate Rotation Component
	    Rad_FromTheta = ((config.lengthBetweenFrontAndRearWheels + config.lengthBetweenFrontWheels) / (2.0 * config.wheelRadius)) * botPosition.orientation;
	    
	    
	    // Wrong math 
	    // full spin displacement (position) for one wheel
	    // R is the spin radius - every wheel is spinning in the circle with radius R
	    //double R = Math.sqrt(Math.pow(config.lengthBetweenFrontAndRearWheels,2) +  Math.pow(config.lengthBetweenFrontWheels, 2 )) / 2;
	    // S (capital)  - displacement of one wheel for a full bot yaw (360 degree)
	    // S = 2* PI * R;  
	    // s = 2* PI * r;  // r is wheel radius 
	    // Rad_FromTheta = (S / s) * 2 PI * (orientation / 2PI) = (R / r) * orientation   
	    
	    
	    //Rad_FromTheta = 2* Math.PI *  R  * botPosition.orientation * Math.cos(botPosition.orientation)/  config.wheelRadius;   
	    

	    wheelPositions.add(  Rad_FromX + Rad_FromY + Rad_FromTheta );
	    wheelPositions.add(  Rad_FromX - Rad_FromY - Rad_FromTheta);
	    wheelPositions.add(  Rad_FromX + Rad_FromY - Rad_FromTheta);
	    wheelPositions.add(  Rad_FromX - Rad_FromY + Rad_FromTheta);

	    return wheelPositions;	
	}
	
	// used to compare wheel parameters within range 0.001 (m/s)
	public static boolean wheelVelocityMatches(List<Double> a, List<Double> b) {
		double D = 0.001;
		
		if (a.size() != b.size()) 
				return false;
		
		
		for (int i = 0; i < a.size(); i++ ) {
			double d = Math.abs(a.get(i)-b.get(i));
			
			if (d > D)
				return false;			
		}
		
		return true;		
	}

	// getter and setter
	
	public FourMecanumKinematicConfiguration getConfig() {
		return config;
	}

	public void setConfig(FourMecanumKinematicConfiguration config) {
		this.config = config;
	}

}
