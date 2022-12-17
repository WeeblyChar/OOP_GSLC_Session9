package OpModel;

import Interface.AttackFunction;

public class OpMixed extends MainOpModel implements AttackFunction {

	//Here, the Physical Attack and Magic Attack would be increased
	//while Physical Defense and Magic Defense would be reduced
	public OpMixed(String name, String type) {
		super(name, type);
		this.setAtk(this.getAtk() + (int) ((Math.random() * 7) + 1));
		this.setDef(this.getDef() - (int) ((Math.random() * 5) + 1));
		this.setMatk(this.getMatk() + (int) ((Math.random() * 7) + 1));
		this.setMdef(this.getMdef() - (int) ((Math.random() * 5) + 1));
	}

	@Override
	public void attack(MainOpModel targetName) {
		int baseDamage, totalDamage;
		
		// Basic Damage Counter but is differnt than the others since it takes both Physical and Magic
		baseDamage = (int) ((this.getAtk() * this.getMatk())
				/ ((int) (targetName.getAtk() + targetName.getDef() + targetName.getMatk() + targetName.getMdef())
						* 0.25));
		
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

	@Override
	public int getCriticalChance(int atk, int def, int matk, int mdef, int hp) {
		int sum;
		sum = (int) ((atk + def + matk + mdef + hp) * 1.5);
		return sum;
	}

}
