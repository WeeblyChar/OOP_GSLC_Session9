package OpModel;

import Interface.AttackFunction;

public class OpAttack extends MainOpModel implements AttackFunction {

	//Here, the Physical Attack and Physical Defense would be increased
	//while Magic Attack and Magic Defense would be reduced
	public OpAttack(String name, String type) {
		super(name, type);
		this.setAtk(this.getAtk() + (int) ((Math.random() * 10) + 1));
		this.setDef(this.getDef() + (int) ((Math.random() * 5) + 1));
		this.setMatk(this.getMatk() - (int) ((Math.random() * 5) + 1));
		this.setMdef(this.getMdef() - (int) ((Math.random() * 5) + 1));
	}

	public int getCriticalChance(int atk, int def, int matk, int mdef, int hp) {
		int sum;
		sum = (int) ((atk + def + matk + mdef + hp) * 1.5);
		return sum;
	}

	@Override
	public void attack(MainOpModel targetName) {
		int baseDamage, totalDamage;
		
		// Basic Damage Counter
		baseDamage = (int) ((this.getAtk() * this.getAtk()) / (targetName.getAtk() + targetName.getDef()));
		
		// Checks if critical or not using (Math.random * total stats and hp)%2
		if (((int) (Math.random()
				* (getCriticalChance(this.getAtk(), this.getDef(), this.getMatk(), this.getMdef(), this.getHp())))
				% 2) == 1) {
			totalDamage = (int) getCriticalDamage(baseDamage);
			System.out.println(this.getName() + " attacked " + targetName.getName() + " and dealt " + totalDamage
					+ " critical damage!");
			targetName.setHp(targetName.getHp() - totalDamage);
			return;
		} else {
			System.out.println(
					this.getName() + " attacked " + targetName.getName() + " and dealt " + baseDamage + " damage!");
			targetName.setHp(targetName.getHp() - baseDamage);
			return;
		}
	}

	@Override
	public int getCriticalDamage(int baseDamage) {
		baseDamage = (int) (baseDamage + (baseDamage * 1.2));

		return baseDamage;
	}

}
