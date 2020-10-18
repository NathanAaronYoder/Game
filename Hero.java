import java.util.Random;
import java.util.Scanner;

public class Hero extends Entity implements Magical
{
	private ArrayList<Item> items;
	private Map map;
	private Point location;

	public Hero(String n, Map m)
	{
		super(n, 25);
		map = new Map(m);
	}

	public String itemsToString()
	{
		String itemString;
		Item item;
		itemString += "Inventory:\n";
		for(int i = 0; i < getNumItems(); i++)
		{
			item = items.get(i);
			itemString += i + ". " + item.toString();
			itemString += "\n";
		}
		return itemString;
	}

	public int getNumItems()
	{
		return items.size();
	}

	public boolean pickUpItem(Item i)
	{
		if (getNumItems() < maxNumberOfItems)
		{
			items.add(i);
			return true;
		}
		Scanner scanner = new Scanner(System.in);
		System.out.println("Your inventory is too full for a " + e.getItem() + ".");
		System.out.println("1. Leave " + e.getItem());
		System.out.println("2. Replace an item in your inventory with " + e.getItem());
		int inventoryChoice;
		do{
			inventoryChoice = scanner.nextInt();
		}
		while(inventoryChoice == 1 | inventoryChoice == 2);
		if (inventoryChoice == 2)
		{
			System.out.println("Choose the item to replace");
			for (int i = 1; i <= 5; i++)
			{
				System.out.println(i + ". " + items.get(i-1));
			}
			int itemToReplace = scanner.nextInt();
			System.out.println(items.get(itemToReplace-1) + "replaced with " + i);
			items.set(itemToReplace-1, i);
		}
		scanner.close();
		return false;
	}

	public void drinkPotion()
	{
		if (hasPotion())
		{
			//Increase 25 HP
			if (hp + 25 > maxHp)
			{
				hp = maxHp;
			}
			else
			{
				hp += 25;
			}
		}
	}

	public void dropItem(int index)
	{
		items.remove(index);
	}

	public boolean hasPotion()
	{
		for (Item item: items)
		{
			if (item.getName().equals("Health Potion"))
			{
				return true;
			}
		}
		return false;
	}

	public Point getLocation()
	{
		return location;
	}

	public char goNorth()
	{
		location.translate(0, 1);
		return map.getCharAtLoc(location);
	}

	public char goSouth()
	{
		location.translate(0, -1);
		return map.getCharAtLoc(location);
	}

	public char goEast()
	{
		location.translate(1, 0);
		return map.getCharAtLoc(location);
	}

	public char goWest()
	{
		location.translate(-1, 0);
		return map.getCharAtLoc(location);
	}

	public String toString()
	{
		String str = super.toString() + "\n";
		str += itemsToString();
		return str;
	}

	public String attack(Entity e)
	{
		Random rand = new Random();
		Scanner scanner = new Scanner(System.in);
		System.out.println("1. Physical Attack");
		System.out.println("2. Magic Attack");
		int attackChoice;
		do{
			attackChoice = scanner.nextInt();
		}while(attackChoice == 1 || attackChoice == 2);
		switch (attackchoice)
		{
			String str = "";
			case 1:
				int maxDamage = e.getMaxHP() * 2;
				int randomDamage = rand.nextInt(maxDamage);
				randomDamage++;
				e.takeDamage(randomDamage);
				str += name + "attacks " + e.getName() + " for " + randomDamage + " damage.";
			case 2:
				System.out.println(Magical.MAGIC_MENU);
				int magicAttackChoice;//Needs Validation
				do{
					magicAttackChoice = scanner.nextInt()
				}while(magicAttackChoice == 1 || magicAttackChoice == 2 || magicAttackChoice == 3);
				switch(magicAttackChoice)
				{
					case 1:
						str += magicMissile(e);
					case 2:
						str += fireball(e);
					case 3:
						str += thunderclap(e);
				}
		}
		scanner.close();
		return str;
	}

	public String magicMissile(Entity e)
	{
		String str = "";
		int maxDamage = e.getMaxHP() * 2;
		Random rand = new Random();
		int damageToTake = rand.nextInt(maxDamage);
		damageToTake++;
		e.takeDamage(damageToTake);
		str += name + "hits " + e.getName() + "with a Magic Missile for " + damageToTake + " damage.";
		return str;
	}

  	public String fireball(Entity e)
  	{
  		String str = "";
		int maxDamage = e.getMaxHP() * 2;
		Random rand = new Random();
		int damageToTake = rand.nextInt(maxDamage);
		damageToTake++;
		e.takeDamage(damageToTake);
		str += name + "hits " + e.getName() + "with a Fireball for " + damageToTake + " damage.";
		return str;
  	}

  	public String thunderclap(Entity e)
  	{
  		String str = "";
		int maxDamage = e.getMaxHP() * 2;
		Random rand = new Random();
		int damageToTake = rand.nextInt(maxDamage);
		damageToTake++;
		e.takeDamage(damageToTake);
		str += name + "hits " + e.getName() + "with a Thunderclap for " + damageToTake + " damage.";
		return str;
  	}
}