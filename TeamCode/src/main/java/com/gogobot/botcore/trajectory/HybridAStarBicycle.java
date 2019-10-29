package com.gogobot.botcore.trajectory;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class HybridAStarBicycle extends HybridAStar{

	private final double speed ;
	private final double elapsedTime ;
	private final double length ;

	
	public HybridAStarBicycle() {
		this(90, 35*Math.PI/180, 1.45, 1, 0.5);
	}
	/**
	 * 
	 * @param numThetaCells
	 * @param speed Only used for bicycle model
	 * @param length
	 * @param grid_sz
	 */
	public HybridAStarBicycle(int numThetaCells, double max_turn_radian,  double speed, double elapsed_time, double length) {
		super(numThetaCells, max_turn_radian);
		this.speed = speed;
		this.elapsedTime = elapsed_time;
		this.length = length;		
	}



	public double getSpeed() {
		return speed;
	}


	public double getLength() {
		return length;
	}


	List<MazeS> expand(MazeS state, List<Integer> goal) {
		int g = state.g;
		double x = state.x;
		double y = state.y;
		double theta = state.theta;

		int g2 = g + 1;

		List<MazeS> next_states = new ArrayList<MazeS>();
		
		double max_turn_degree = getMaxTurnRadian() * 180 / Math.PI;

		for (double delta_i = -max_turn_degree; delta_i <= max_turn_degree; delta_i += 5) {

			double delta = Math.PI / 180.0 * delta_i;
			// omega (is the rotation rate) theta_dot
			// length is the distance between front bicycle wheel and the rear wheel
			// theta_dot = omega = v tan (delta) / Length 
			
			double omega = speed / length * Math.tan(delta);
			double theta2 = theta + omega;
			if (theta2 < 0) {
				theta2 += 2 * Math.PI;
			}
			double x2 = x + speed* elapsedTime * Math.cos(theta);
			double y2 = y + speed* elapsedTime * Math.sin(theta);
			MazeS state2 = new MazeS();
			state2.f = g2 + (int) heuristic(x2, y2, goal);
			state2.g = g2;
			state2.x = x2;
			state2.y = y2;
			state2.theta = theta2;
			next_states.add(state2);
		}

		return next_states;

	}

}
