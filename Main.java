import java.util.Random;
import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);

		Map map = new Map();
		int mapLevel = 1;
		map.loadMap(mapLevel);

		System.out.println("What is your name, traveler?");
		String name = scanner.nextLine();
		Hero user = new Hero(name, map);

		while(true)
		{
			System.out.println(user);
			map.displayMap(user.getLocation());

			char room = ' ';

			System.out.println("1. Go North");
			System.out.println("2. Go South");
			System.out.println("3. Go East");
			System.out.println("4. Go West");
			System.out.println("5. Quit");
			int directionChoice;
			do {
				directionChoice = scanner.nextInt();
			}while(directionChoice == 1 || directionChoice == 2 || directionChoice == 3 || directionChoice == 4 || directionChoice == 5);
			
			Point p = user.getLocation();
			int x = p.getX();
			int y = p.getY();
			switch(directionChoice)
			{
				case 1:
					y++;
				case 2:
					y--;
				case 3:
					x++;
				case 4:
					x--;
			}
			while(x < 0 || y < 0 || x > 4 || y > 4)
			{
				System.out.println("Out of Bounds. Choose a new direction.");
				directionChoice = scanner.nextInt();
				p = user.getLocation();
				x = p.getX();
				y = p.getY();
				switch(directionChoice)
				{
					case 1:
						y++;
					case 2:
						y--;
					case 3:
						x++;
					case 4:
						x--;
				}
			}
			switch(directionChoice)
			{
				case 1:
					room = user.goNorth();
				case 2:
					room = user.goSouth();
				case 3:
					room = user.goEast();
				case 4:
					room = user.goWest();
				case 5:
					System.out.println("Game Over.");
					scanner.close();
					System.exit(0);
			}
			map.reveal(user.getLocation());
			ItemGenerator itemGenerator = new ItemGenerator();
			EnemyGenerator enemyGenerator = new EnemyGenerator(itemGenerator);
			switch(room)
			{
				case 'm':
					if (!monsterRoom(user, map, enemyGenerator, mapLevel))
					{
						System.out.println("Game Over.");
						scanner.close();
						System.exit(0);
					}
				case 'n':
					System.out.println("There was nothing here.");
				case 'i':
					itemRoom(user, map, itemGenerator);
				case 's':
					System.out.println("You're back at the start.");
				case 'f':
					mapLevel++;
					mapLevel %= 3;
					map.loadMap(mapLevel);
					user.drinkPotion();
			}
		}
	}

	public static boolean monsterRoom(Hero h, Map m, EnemyGenerator eg, int mapLevel)//how to return map level to main
	{
		//Return True if Enemy Dies or no one dies, False if Hero Dies
		Enemy enemy = eg.generateEnemy();
		boolean fightOver = false;
		System.out.println("You've encountered a " + enemy.getName());
		Scanner scanner = new Scanner(System.in);

		while (!fightOver)
		{
			System.out.println(enemy);
			System.out.println("1. Fight");
			System.out.println("2. Run Away");
			if (h.hasPotion())
			{
				System.out.println("3. Drink Health Potion");
			}
			int choice;
			if (h.hasPotion())
			{
				do{
					choice = scanner.nextInt();
				}while(choice == 1 || choice == 2 | choice == 3);
			}
			else
			{
				do{
					choice = scanner.nextInt();
				}while(choice == 1 || choice == 2);
			}
			int randNum = 0;
			switch(choice)
			{
				case 1:
					if (!fight(h, enemy))
					{
						fightOver = true;
					}
				case 2:
					Random rand = new Random();
					char room = ' ';

					Point p;
					int x, y;
					do{
						p = h.getLocation();
						x = p.getX();
						y = p.getY();
						switch(randNum)
						{
							case 1:
								y++;
							case 2:
								y--;
							case 3:
								x++;
							case 4:
								x--;
						}
						randNum = rand.nextInt(4);//4 directions, maybe static var
					}while(x < 0 || y < 0 || x > 4 || y > 4);
					switch(randNum)
					{
						case 0:
							room = h.goNorth();
						case 1:
							room = h.goEast();
						case 2:
							room = h.goSouth();
						case 3:
							room = h.goWest();
					}
					m.reveal(h.getLocation());
					ItemGenerator itemGenerator = new ItemGenerator();
					EnemyGenerator enemyGenerator = new EnemyGenerator(itemGenerator);
					switch(room)
					{
						case 'm':
							if (!monsterRoom(h, m, enemyGenerator, mapLevel))
							{
								System.out.println("Game Over.");
								scanner.close();
								System.exit(0);
							}
						case 'n':
							System.out.println("There was nothing here.");
						case 'i':
							itemRoom(h, m, itemGenerator);
						case 's':
							System.out.println("You're back at the start.");
						case 'f':
							mapLevel++;
							mapLevel %= 3;
							m.loadMap(mapLevel);
							h.drinkPotion();
					}
					fightOver = true;
				case 3:
					h.drinkPotion();
			}
		}

		scanner.close();
		if (h.getHP() == 0)
		{
			System.out.println("You have died.");
			return false;
		}
		if (enemy.getHP() == 0)
		{
			System.out.println("You defeated the " + enemy.getName() + "!");
			if (h.pickUpItem(enemy.getItem()))
			{
				System.out.println("You received a " + enemy.getItem() + " from its corpse.");
			}
			m.removeCharAtLoc(h.getLocation());
			return true;
		}
		return true;
	}

	public static boolean fight(Hero h, Enemy e)
	{
		//If Hero or Enemy dies, return false
		h.attack(e);
		if (e.getHP() == 0)
		{
			return false;
		}
		e.attack(h);//Need to define
		if (h.getHP() == 0)
		{
			return false;
		}
		return true;
	}

	public static void itemRoom(Hero h, Map m, ItemGenerator ig)
	{
		Item item = ig.generateItem();
		System.out.println("You found a " + item);
		if (h.pickUpItem(item))
		{
			System.out.println("You received a " + item);
			m.removeCharAtLoc(h.getLocation());
		}
	}
}