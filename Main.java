import java.util.Random;
import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);

		Map map = new Map();
		map.loadMap(1);

		System.out.println("What is your name, traveler?");
		String name = scanner.nextLine();
		Hero user = new Hero();//Not Done

		while (user.getHP() > 0)
		{

		}
		System.out.println(user);
		map.displayMap();

		System.out.println("1. Go North");
		System.out.println("2. Go South");
		System.out.println("3. Go East");
		System.out.println("4. Go West");
		System.out.println("5. Quit");
		int directionChoice = myObj.nextInt();//Needs Validation
		switch(directionChoice)
		{
			case 1:
				user.goNorth();
			case 2:
				user.goSouth();
			case 3:
				user.goEast();
			case 4:
				user.goWest();
			case 5:
				System.out.println("Game Over.");
				System.exit(0);
		}

		char room = map.getCharAtLoc(user.getLocation());
		map.reveal(user.getLocation());
		EnemyGenerator enemyGenerator = new EnemyGenerator();
		ItemGenerator itemGenerator = new ItemGenerator();
		switch(room)
		{
			case 'm':
				mosterRoom(user, map, enemyGenerator);
			case 'n':
				System.out.println("There was nothing here.");
			case 'i':
				itemRoom(user, map, itemGenerator);
			case 's':
				System.out.println("You're back at the start.");
			case 'f':
				//If the user reaches the finish, then they have passed the level. The hero’s hp is
				//replenished
		}

	}

	public static boolean monsterRoom(Hero h, Map m, EnemyGenerator eg, int level)//Why return Boolean
	{
		Enemy enemy = eg.generateEnemy(level);
		fight(h, enemy);

	}

	public boolean fight(Hero h, Enemy e)//Why return Boolean, issue with case 2 and main funcion
	{
		boolean notRunningAway = true;
		System.out.println("You've encountered a " + e.getName());
		while (h.getHP() > 0 && e.getHP() > 0 && notRunningAway)
		{
			System.out.println(e);
			System.out.println("1. Fight\n2. Run Away");
			if (h.hasPotion())
			{
				System.out.println("3. Drink Health Potion");
			}
			//Get User Input, check for 3
			switch(choice)
			{
				case 1:
				case 2:
					Random rand = new Random();
					int randNum = rand.nextInt(4);//4 directions, maybe static var
					switch(randNum)
					{
						case 0:
							h.goNorth();
						case 1:
							h.goEast();
						case 2:
							h.goSouth();
						case 3:
							h.goWest();
					}
					notRunningAway = false;
				case 3:
					h.drinkPotion();
			}
		}
	}

	public static void itemRoom(Hero h, Map m, ItemGenerator ig)
	{
		Item item = ig.generateItem();
		if (h.pickUpItem(item))
		{
			System.out.println("\nYou found a " + item);
			m.removeCharAtLoc(h.getLocation());
		}
		else
		{
			//if their inventory is already full, then the user has the choice of selecting
			//an item in their inventory to drop and replace with the new item, or just to drop
			//the new item
			System.out.println("\nYour inventory is too full");
		}
	}
}