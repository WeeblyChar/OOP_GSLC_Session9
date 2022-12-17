package Interface;

import OpModel.MainOpModel;

@FunctionalInterface
public interface AttackFunction {
	// This would be the attacking function that would have
	// different multipliers for each class
	
	public void attack(MainOpModel targetName);
}
