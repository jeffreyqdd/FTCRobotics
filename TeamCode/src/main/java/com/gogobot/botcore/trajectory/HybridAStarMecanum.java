package com.gogobot.botcore.trajectory;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class HybridAStarMecanum extends HybridAStar{

	private final double speed ;

	
	public HybridAStarMecanum() {
		this(90, 60*Math.PI/180, 1);
	}
	/**
	 * 
	 * @param numThetaCells
	 * @param gridSpaceSpeed  - value of the grid travel through; not speed in cartesian space
	 * @param length
	 * @param grid_sz
	 */
	public HybridAStarMecanum(int numThetaCells, double max_turn_radian,  double gridSpaceSpeed) {
		super(numThetaCells, max_turn_radian);
		this.speed = gridSpaceSpeed;

	}



	public double getSpeed() {
		return speed;
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
			
			double omega = delta; // in this mecanum kinematic model
			double theta2 = theta + omega;
			if (theta2 < 0) {
				theta2 += 2 * Math.PI;
			}
			double x2 = x + speed*  Math.cos(theta);
			double y2 = y + speed*  Math.sin(theta);
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
