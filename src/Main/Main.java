package Main;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;

import OpModel.MainOpModel;
import OpModel.OpAttack;
import OpModel.OpMixed;
import OpModel.OpMage;

public class Main {

	Scanner sc = new Scanner(System.in);
	ArrayList<MainOpModel> opModel = new ArrayList<>();

	// This is a method to put the Thread asleep for the inputted number in miliseconds.
	public static void wait(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	// This clears the screen just for convenience.
	public void clearSpace() {
		for (int i = 0; i < 20; i++)
			System.out.println("");
	}

	// This uses the previous wait method to create some simple waiting animation.
	public void waitDots(int ms) {
		for (int i = 0; i < 3; i++) {
			wait(ms);
			System.out.print(".");
		}
	}

	// This shows the main menu for users to choose.
	public void menu() {
		clearSpace();
		System.out.println("Operators Arena! v1.0");
		System.out.println("=====================");
		System.out.println("1. View Operators");
		System.out.println("2. Add Operator");
		System.out.println("3. Remove Operator");
		System.out.println("4. Test Operators");
		System.out.println("5. Game Info");
		System.out.println("6. Exit");
		System.out.print("Choice : ");
	}

	public void view() {
		clearSpace();
		System.out.println("Operator List");

		if (opModel.isEmpty()) {
			System.out.println("=============");
			System.out.println("There is no operator yet.");
			return;
		}

		// Here I used Iteration to loop the list and print them.
		// While it's not necessary, this is a way to practice it.
		System.out.println("===================================");
		ListIterator<MainOpModel> opListIterator = opModel.listIterator();
		while (opListIterator.hasNext()) {
			System.out.printf("Operator %d\n", opListIterator.nextIndex() + 1);
			opModel.get(opListIterator.nextIndex()).viewCharInfo();
			System.out.println("===================================");
			opListIterator.next();
		}

	}

	public void add() {
		clearSpace();
		String name, type;
		System.out.println("Operator Creation");
		System.out.println("=================");
		boolean nameFlag = false;

		// This will check if the Name is at least 1 character and isn't more than 10 characters while also
		// searching if there's already the same name existing in the ArrayList.
		do {
			System.out.print("Insert Operator Name [10] : ");
			name = sc.nextLine();
			for (int i = 0; i < opModel.size(); i++) {
				String check = opModel.get(i).getName();
				if (name.contains(check))
					nameFlag = true;
				else
					nameFlag = false;
			}
		} while (name.length() < 1 || name.length() > 10 || nameFlag);

		do {
			System.out.print("Insert Operator Class [Physical | Mixed | Magic] : ");
			type = sc.nextLine();
		} while (!(type.equals("Physical")) && !(type.equals("Mixed")) && !(type.equals("Magic")));

		System.out.println("Added " + type + " Operator named " + name + ".");
		System.out.println("Status :");
		
		// Creates the instance and adding them to the ArrayList
		if (type.equals("Physical")) {
			OpAttack newOp = new OpAttack(name, type);
			opModel.add(newOp);
			newOp.viewCharInfo();
		} else if (type.equals("Mixed")) {
			type = "Physical and Magic";
			OpMixed newOp = new OpMixed(name, type);
			opModel.add(newOp);
			newOp.viewCharInfo();
		} else if (type.equals("Magic")) {
			OpMage newOp = new OpMage(name, type);
			opModel.add(newOp);
			newOp.viewCharInfo();
		}
		System.out.print("Press any key to continue...");
		sc.nextLine();
	}

	public void fight() {
		if (opModel.isEmpty()) {
			view();
			sc.nextLine();
			return;
		}

		// Checks if there's less than 2 Operators available.
		if (opModel.size() < 2) {
			System.out.println("There's only one Operator, unable to test!");
			sc.nextLine();
			return;
		}
		view();
		int x = 0, y = 0;
		
		// Choosing Operators and preventing from choosing the same Operator 
		do {
			System.out.printf("Choose First Operator [1-%d]: ", opModel.size());
			x = sc.nextInt();
			sc.nextLine();
			System.out.printf("Choose Second Operator [1-%d]: ", opModel.size());
			y = sc.nextInt();
			sc.nextLine();
		} while ((x < 1 || x > opModel.size()) || (y < 1 || y > opModel.size()) || x == y);
		--x;
		--y;
		MainOpModel a, b;
		a = opModel.get(x);
		b = opModel.get(y);
		int turnCounter = 1, aAtk, aMatk, bAtk, bMatk;

		// Saves the current Operator's stats that would be modified during the fight.
		aAtk = a.getAtk();
		aMatk = a.getMatk();
		bAtk = b.getAtk();
		bMatk = b.getMatk();

		System.out.println("");

		boolean flag = true;
		do {

			// Checks the instance of A and use the attack of said instance
			System.out.printf("Turn %d:\n", turnCounter);
			if (a instanceof OpAttack) {
				((OpAttack) a).attack(b);
				a.setAtk(a.getAtk() + 1);
			} else if (a instanceof OpMixed) {
				((OpMixed) a).attack(b);
			} else if (a instanceof OpMage) {
				((OpMage) a).attack(b);
				a.setMatk(a.getMatk() + 1);
			}
			wait(1000);

			// Checks if A was eliminated from B's attack
			if (b.getHp() < 0) {
				flag = false;
				if (b.getHp() < 1)
					b.setHp(0);
				System.out.println(a.getName() + " won with " + a.getHp() + " hp left!");
				System.out.print("\n\nRemoving " + b.getName());
				waitDots(1500);
				System.out.print("\nRemoved " + b.getName() + "!");
				wait(1000);
				System.out.print("\nHealing " + a.getName() + " to full HP");
				waitDots(1500);
				System.out.println("\n" + a.getName() + " has been Healed!");
				
				// Return Operator's status back to normal.
				a.setAtk(aAtk);
				a.setMatk(aMatk);
				a.setHp(200);
				opModel.remove(y);
				break;
			}

			// ====================================================

			// Checks the instance of B and use the attack of said instance
			if (b instanceof OpAttack) {
				((OpAttack) b).attack(a);
				b.setAtk(b.getAtk() + 1);
			} else if (b instanceof OpMixed) {
				((OpMixed) b).attack(a);
			} else if (b instanceof OpMage) {
				((OpMage) b).attack(a);
				b.setMatk(b.getMatk() + 1);
			}
			wait(1000);

			// Checks if A was eliminated from B's attack
			if (a.getHp() < 0) {
				flag = false;
				if (a.getHp() < 1)
					a.setHp(0);
				System.out.println(b.getName() + " won with " + b.getHp() + " hp left!");
				System.out.print("\n\nRemoving " + a.getName());
				waitDots(2000);
				System.out.print("\nRemoved " + a.getName() + "!");
				wait(1000);
				System.out.print("\nHealing " + b.getName() + " to full HP");
				waitDots(2000);
				System.out.println("\n" + b.getName() + " has been Healed!");
				
				// Return Operator's status back to normal.
				b.setHp(200);
				b.setAtk(bAtk);
				b.setMatk(bMatk);
				opModel.remove(x);
				break;
			}

			turnCounter++;
			System.out.println("");
		} while (flag);

		wait(500);
		System.out.print("Press any key to continue...");
		sc.nextLine();
	}

	// Shows some info for the user about the game.
	public void gameInfo() {
		clearSpace();
		System.out.println("Hello, welcome to Operators Arena!");
		System.out.println(
				"This is a very simple game where you make and pick 2 Operators to\n" + "fight each other to death.\n"
						+ "\n" + "The penalty for each defeated Operator? They will be deleted.\n" + "\n"
						+ "How to create Operators:\n" + "You only need to insert the name and class of the Operator\n"
						+ "in the Operator Creation menu and let the system generate the\n"
						+ "randomized stats for you. Very simple, right?\n" + "Have Fun!");
		System.out.print("\n\nPress any key to continue...");
		sc.nextLine();
		return;
	}

	public void remove() {
		if (opModel.isEmpty()) {
			view();
			sc.nextLine();
			return;
		}
		view();
		int x;
		do {
			System.out.printf("Which Operator do wish to delete? [1-%d] : ", opModel.size());
			x = sc.nextInt();
			sc.nextLine();
		} while (x < 1 || x > opModel.size());
		--x;

		String y;
		
		// Confirms if the Operator's deletion
		do {
			System.out.println("Do you wish to delete " + opModel.get(x).getName() + "? [Y/n]");
			y = sc.nextLine();
		} while (!(y.equalsIgnoreCase("Y") || y.equalsIgnoreCase("N")));

		if (y.equalsIgnoreCase("Y")) {
			System.out.print("Removing " + opModel.get(x).getName());
			waitDots(350);
			System.out.println("\n" + opModel.get(x).getName() + " has been removed!");
			opModel.remove(x);
			System.out.print("Press any key to continue...");
			sc.nextLine();
		} else {
			System.out.print("Returning to main screen");
			waitDots(350);
			return;
		}
	}

	public Main() {
		int x;
		do {
			menu();
			x = sc.nextInt();
			sc.nextLine();
			switch (x) {
			case 1:
				view();
				System.out.print("Press any key to continue...");
				sc.nextLine();
				break;
			case 2:
				add();
				break;
			case 3:
				remove();
				break;
			case 4:
				fight();
				break;
			case 5:
				gameInfo();
				break;
			}
		} while (x != 6);
	}

	public static void main(String[] args) {
		new Main();
	}

}
