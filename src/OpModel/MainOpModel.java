package OpModel;

public abstract class MainOpModel {
	private String name, type;
	private int atk, def, matk, mdef, hp;

	//This would be the base stats every Operator would have
	public MainOpModel(String name, String type) {
		super();
		this.name = name;
		this.type = type;
		this.hp = 200;
		this.atk = 10;
		this.def = 10;
		this.matk = 10;
		this.mdef = 10;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	//This shows the Operator's information
	public void viewCharInfo() {
		System.out.println("Name		: " + this.getName());
		System.out.println("Class		: " + this.getType());
		System.out.println("Hp		: " + this.getHp());
		System.out.println("Attack		: " + this.getAtk());
		System.out.println("Defense		: " + this.getDef());
		System.out.println("Magic Attack	: " + this.getMatk());
		System.out.println("Magic Defense	: " + this.getMdef());
		if (this.getType().equals("Physical"))
			System.out.println("Speciality: Attack Increases by 1 each turn.");
		else if (this.getType().equals("Physical and Magic"))
			System.out.println("Speciality: Piercing towards Physical and Magic type.");
		else if (this.getType().equals("Magic"))
			System.out.println("Speciality: Magic Attack Increases by 1 each turn.");
	}

	//This would be the method that each class will use to calculate the critical chance
	public abstract int getCriticalChance(int atk, int def, int matk, int mdef, int hp);

	//This would be the method that each class will use to calculate the critical damage
	public abstract int getCriticalDamage(int baseDamage);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getAtk() {
		return atk;
	}

	public void setAtk(int atk) {
		this.atk = atk;
	}

	public int getDef() {
		return def;
	}

	public void setDef(int def) {
		this.def = def;
	}

	public int getMatk() {
		return matk;
	}

	public void setMatk(int matk) {
		this.matk = matk;
	}

	public int getMdef() {
		return mdef;
	}

	public void setMdef(int mdef) {
		this.mdef = mdef;
	}

}
