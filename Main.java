import java.util.Random;
import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);

		Map map = new Map();
		int mapLevel = 0;
		map.loadMap(mapLevel);

		System.out.println("What is your name, traveler?");
		String name = scanner.nextLine();
		Hero user = new Hero(name, map);

		map.reveal(user.getLocation());//Handles Edge Case because first location won't be revealed otherwise

		//Program will exit when the User dies or ends the game
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
			//Validate Input
			do 
			{
				directionChoice = scanner.nextInt();
			}
			while(directionChoice < 1 || directionChoice > 5);
			
			//Simulates the user moving to a direction to check if they are out of bounds
			Point p = user.getLocation();
			int x = p.getX();
			int y = p.getY();
			switch(directionChoice)
			{
				case 1://North
					x--;
					break;
				case 2://South
					x++;
					break;
				case 3://East
					y++;
					break;
				case 4://West
					y--;
					break;
			}
			//Check if Simulation has led user out of bounds
			while(x < 0 || y < 0 || x > 4 || y > 4)
			{
				System.out.println("Out of Bounds. Choose a new direction.");
				directionChoice = scanner.nextInt();
				p = user.getLocation();
				x = p.getX();
				y = p.getY();
				switch(directionChoice)
				{
					case 1://North
						x--;
						break;
					case 2://South
						x++;
						break;
					case 3://East
						y++;
						break;
					case 4://West
						y--;
						break;
				}
			}
			//Input has been validated, the user is now moving
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
					//User chooses to end the game
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
						//User has died
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
					System.out.println("You've reached the finish.");
					final int POTION_HEAL_AMOUNT = 25;
					final int NUM_MAPS = 3;
					mapLevel++;
					mapLevel %= NUM_MAPS;
					map.loadMap(mapLevel);
					user.heal(POTION_HEAL_AMOUNT);
					start = new Point(map.findStart().getX(), map.findStart().getY());
					map.reveal(start);
					break;
			}
			//This is necessary in case the user finds the finish when the user encounters an enemy but runs away
			if (!map.findStart().equals(start))
			{
				mapLevel++;
			}
		}
	}

	/** * The Hero has entered a monster Room and is given some options on how to continue
	* @param h hero that entered the monster room
	* @param m current map
	* @param eg generates the enemy in the room
	* @param mapLevel the current map level
	* @return True if Enemy Dies or no one dies, false if Hero Dies
	*/
	public static boolean monsterRoom(Hero h, Map m, EnemyGenerator eg, int mapLevel)
	{
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
			//Validate Input
			if (h.hasPotion())
			{
				do
				{
					choice = scanner.nextInt();
				}
				while(choice < 1 || choice > 3);
			}
			else
			{
				do
				{
					choice = scanner.nextInt();
				}
				while(choice < 1 || choice > 2);
			}
			int randNum = 0;
			switch(choice)
			{
				case 1:
					//User decides to fight the enemy
					if (!fight(h, enemy))
					{
						//Enemy or Hero has died
						//Loop of fighting is broken
						fightOver = true;
					}
					break;
				case 2:
					//User Runs Away in a random direction
					Random rand = new Random();
					char room = ' ';

					//Validate Input
					//Simulate the direction the Hero is running in to check if it is out of bounds
					Point p;
					int x, y;
					do
					{
						p = h.getLocation();
						x = p.getX();
						y = p.getY();
						randNum = rand.nextInt(4);//4 directions
						switch(randNum)
						{
							case 0://North
								x--;
								break;
							case 1://South
								y++;
								break;
							case 2://East
								x++;
								break;
							case 3://West
								y--;
								break;
						}
					}
					while(x < 0 || y < 0 || x > 4 || y > 4);
					//Input is valid, the Hero moves in a random direction
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
					/*
					Simulates the room the user randomly goes into
					The difference between this switch statement and the similar one in the main function is that this one occurs when
					the user runs away from a monster into a random direction whereas the one in the main function is when the user
					chooses which direction to move to
					*/
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
							System.out.println("You've reached the finish.");
							final int POTION_HEAL_AMOUNT = 25;
							final int NUM_MAPS = 3;
							mapLevel++;
							mapLevel %= NUM_MAPS;
							m.loadMap(mapLevel);
							h.heal(POTION_HEAL_AMOUNT);
							m.reveal(h.getLocation());
							break;
					}
					fightOver = true;
					break;
				case 3:
					//User Drinks a Health Potion
					h.drinkPotion();
					System.out.println(h);
					break;
			}
		}

		//Now that the fight is over, we don't know who died so we have to check
		if (h.getHP() == 0)
		{
			//User died, the game is over
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

	/** * The Hero fights the Enemy
	* @param h hero in the fight
	* @param e enemy in the fight
	* @return If Hero or Enemy dies, return false
	*/
	public static boolean fight(Hero h, Enemy e)
	{
		//Hero attacks the Enemy first
		System.out.println(h.attack(e));
		if (e.getHP() == 0)
		{
			return false;
		}
		//If Enemy survives the Hero's attack, the Enemy attacks the Hero
		System.out.println(e.attack(h));
		if (h.getHP() == 0)
		{
			return false;
		}
		return true;
	}

	/** * The Hero has entered a room with an item and interacts with it
	* @param h the hero that entered the item room
	* @param m the current map
	* @param ig generates the item in the room
	*/
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