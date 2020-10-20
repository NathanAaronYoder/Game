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
		String name = scanner.nextLine();//first time, reveal location
		Hero user = new Hero(name, map);
		map.reveal(user.getLocation());

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
			}while(directionChoice < 1 || directionChoice > 5);
			
			Point p = user.getLocation();
			int x = p.getX();
			int y = p.getY();
			switch(directionChoice)
			{
				case 1:
					y++;
					break;
				case 2:
					y--;
					break;
				case 3:
					x++;
					break;
				case 4:
					x--;
					break;
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
						break;
					case 2:
						y--;
						break;
					case 3:
						x++;
						break;
					case 4:
						x--;
						break;
				}
			}
			switch(directionChoice)
			{
				case 1:
					room = user.goNorth();
					break;
				case 2:
					room = user.goSouth();
					break;
				case 3:
					room = user.goEast();
					break;
				case 4:
					room = user.goWest();
					break;
				case 5:
					System.out.println("Game Over.");
					scanner.close();
					System.exit(0);
					break;
			}
			map.reveal(user.getLocation());
			ItemGenerator itemGenerator = new ItemGenerator();
			EnemyGenerator enemyGenerator = new EnemyGenerator(itemGenerator);
			Point start = new Point(map.findStart().getX(), map.findStart().getY());
			switch(room)
			{
				case 'm':
					if (!monsterRoom(user, map, enemyGenerator, mapLevel))
					{
						System.out.println("Game Over.");
						scanner.close();
						System.exit(0);
					}
					break;
				case 'n':
					System.out.println("There was nothing here.");
					break;
				case 'i':
					itemRoom(user, map, itemGenerator);
					break;
				case 's':
					System.out.println("You're back at the start.");
					break;
				case 'f':
					mapLevel++;
					mapLevel %= 3;
					map.loadMap(mapLevel);
					user = new Hero(name, map);
					start = new Point(map.findStart().getX(), map.findStart().getY());
					break;
					//user.drinkPotion();
			}
			if (!map.findStart().equals(start))
			{
				mapLevel++;
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
				}while(choice < 1 || choice > 3);
			}
			else
			{
				do{
					choice = scanner.nextInt();
				}while(choice < 1 || choice > 2);
			}
			int randNum = 0;
			switch(choice)
			{
				case 1:
					if (!fight(h, enemy))
					{
						fightOver = true;
					}
					break;
				case 2:
					Random rand = new Random();
					char room = ' ';

					Point p;
					int x, y;
					do{
						p = h.getLocation();
						x = p.getX();
						y = p.getY();
						randNum = rand.nextInt(4);//4 directions, maybe static var
						switch(randNum)
						{
							case 0:
								y++;
								break;
							case 1:
								y--;
								break;
							case 2:
								x++;
								break;
							case 3:
								x--;
								break;
						}
					}while(x < 0 || y < 0 || x > 4 || y > 4);
					switch(randNum)
					{
						case 0:
							room = h.goNorth();
							break;
						case 1:
							room = h.goEast();
							break;
						case 2:
							room = h.goSouth();
							break;
						case 3:
							room = h.goWest();
							break;
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
							break;
						case 'n':
							System.out.println("There was nothing here.");
							break;
						case 'i':
							itemRoom(h, m, itemGenerator);
							break;
						case 's':
							System.out.println("You're back at the start.");
							break;
						case 'f':
							mapLevel++;
							mapLevel %= 3;
							m.loadMap(mapLevel);
							h = new Hero(h.getName(), m);
							break;
							//h.drinkPotion();
					}
					fightOver = true;
					break;
				case 3:
					h.drinkPotion();
					break;
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
				System.out.println("You received a " + enemy.getItem().getName() + " from its corpse.");
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
		System.out.println("You found a " + item.getName());
		if (h.pickUpItem(item))
		{
			System.out.println("You received a " + item.getName());
			m.removeCharAtLoc(h.getLocation());
		}
	}
}