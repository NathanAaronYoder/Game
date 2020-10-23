import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class Hero extends Entity implements Magical
{
	private ArrayList<Item> items;
	private Map map;
	private Point location;

	/** * Constructor for Hero Class
	* @param n name of Hero
	* @param m map the Hero is currently on
	*/
	public Hero(String n, Map m)
	{
		super(n, 25);//Hero's Max HP is 25
		this.map = m;
		this.items = new ArrayList<Item>();
		this.location = new Point(this.map.findStart().getX(), this.map.findStart().getY());
	}

	/** * Display the Hero's inventory
	* @return String of items in Hero's inventory
	*/
	public String itemsToString()
	{
		String itemString = "";
		Item item;
		itemString += "Inventory:";
		for(int i = 0; i < getNumItems(); i++)
		{
			item = this.items.get(i);
			itemString += "\n";
			itemString += (i+1) + ". " + item.getName();
		}
		return itemString;
	}

	/** * Get the number of items in Hero's inventory
	* @return number of items in Hero's inventory
	*/
	public int getNumItems()
	{
		return this.items.size();
	}

	/** * Add an item to the Hero's inventory
	* @param i item to be added to the inventory
	* @return true if Item i is added to Hero's inventory
	*/
	public boolean pickUpItem(Item i)
	{
		//If the inventory is too full, choose to replace an item in Hero's inventory with item i
		final int MAX_NUM_ITEMS = 5;
		if (getNumItems() < MAX_NUM_ITEMS)
		{
			this.items.add(i);
			return true;
		}
		//Ask User if they want to replace an item in their inventory
		Scanner scanner = new Scanner(System.in);
		System.out.println("Your inventory is too full for a " + i.getName() + ".");
		System.out.println("1. Leave " + i.getName());
		System.out.println("2. Replace an item in your inventory with " + i.getName());
		int inventoryChoice;
		//Validate Input
		do
		{
			inventoryChoice = scanner.nextInt();
		}
		while(inventoryChoice < 1 || inventoryChoice > 2);
		if (inventoryChoice == 2)
		{
			//Replace an item in Hero's inventory with item i
			System.out.println("Choose the item to replace");
			for (int index = 1; index <= MAX_NUM_ITEMS; index++)
			{
				System.out.println(index + ". " + this.items.get(index-1).getName());
			}
			//Ask user which item they want to replace
			int itemToReplace = scanner.nextInt();
			System.out.println(this.items.get(itemToReplace-1).getName() + "replaced with " + i.getName());
			//Replace item
			this.items.set(itemToReplace-1, i);
			return true;
		}
		return false;
	}

	/** * Hero drinks and removes a health potion from their inventory
	*/
	public void drinkPotion()
	{
		if (hasPotion())
		{
			final int POTION_HEAL_AMOUNT = 25;
			//Increase 25 HP
			heal(POTION_HEAL_AMOUNT);
		}

		//Take Health Potion out of inventory
		boolean dropped = false;
		for (int i = 0; i < getNumItems(); i++)
		{
			if (this.items.get(i).getName().equals("Health Potion") && !dropped)
			{
				dropItem(i);
				dropped = true;
			}
		}
	}

	/** * Remove an item in Hero's inventory at index
	* @param index the index of the item to be removed
	*/
	public void dropItem(int index)
	{
		this.items.remove(index);
	}

	//Returns true if the Hero has a potion
	/** * Check if the Hero has a potion in their inventory
	* @return true if the Hero has a health potion in inventory
	*/
	public boolean hasPotion()
	{
		for (Item item: this.items)
		{
			if (item.getName().equals("Health Potion"))
			{
				return true;
			}
		}
		return false;
	}

	/** * Get the point location of the Hero
	* @return the point location of the hero
	*/
	public Point getLocation()
	{
		return this.location;
	}

	/** * Move the location of the Hero one unit East
	* @return character at the new location
	*/
	public char goEast()
	{
		this.location.translate(0, 1);
		return this.map.getCharAtLoc(this.location);
	}

	/** * Move the location of the Hero one unit West
	* @return character at the new location
	*/
	public char goWest()
	{
		this.location.translate(0, -1);
		return this.map.getCharAtLoc(this.location);
	}

	/** * Move the location of the Hero one unit South
	* @return character at the new location
	*/
	public char goSouth()
	{
		this.location.translate(1, 0);
		return this.map.getCharAtLoc(this.location);
	}

	/** * Move the location of the Hero one unit North
	* @return character at the new location
	*/
	public char goNorth()
	{
		this.location.translate(-1, 0);
		return this.map.getCharAtLoc(this.location);
	}

	/** * Display the Hero including name, hp, and inventory
	* @return Hero displayed as a String
	*/
	public String toString()
	{
		//Add name and hp to toString()
		String str = super.toString() + "\n";
		//Add inventory to toString()
		str += itemsToString();
		return str;
	}

	/** * Overrides attack method inherited from Entity and Calculates random damage to deal to Enemy entity
	* @param e enemy entity the Hero attacks
	* @return display of the attack
	*/
	public String attack(Entity e)
	{
		//Use Random to determine the amount of damage being dealt, up to Enemies max hp * 2
		Random rand = new Random();
		//Ask user which attack they want to use
		Scanner scanner = new Scanner(System.in);
		System.out.println("1. Physical Attack");
		System.out.println("2. Magic Attack");
		int attackChoice;
		//Validate Input
		do
		{
			attackChoice = scanner.nextInt();
		}
		while(attackChoice < 1 || attackChoice > 2);
		String str = "";
		switch(attackChoice)
		{
			case 1://Physical Attack 
				final int MAX_DAMAGE = e.getMaxHP() * 2;
				int randomDamage = rand.nextInt(MAX_DAMAGE);
				randomDamage++;
				e.takeDamage(randomDamage);
				str += this.name + " attacks " + e.getName() + " for " + randomDamage + " damage.";
				break;
			case 2://Magical Attack
				System.out.println(Magical.MAGIC_MENU);
				int magicAttackChoice;
				//Validate Input
				do
				{
					magicAttackChoice = scanner.nextInt();
				}
				while(magicAttackChoice < 1 || magicAttackChoice > 3);
				switch(magicAttackChoice)
				{
					case 1://Magic Missile
						str += magicMissile(e);
						break;
					case 2://Fireball
						str += fireball(e);
						break;
					case 3://Thunderclap
						str += thunderclap(e);
						break;
				}
				break;
		}
		return str;
	}

	/** * Override magic missile method implemented from Magical and calculates random damage up to Enemies max hp * 2 to deal to hero Entity e
	* @param e enemy entity to take damage
	* @return String displaying the attack
	*/
	public String magicMissile(Entity e)
	{
		String str = "";
		final int MAX_DAMAGE = e.getMaxHP() * 2;
		Random rand = new Random();
		int damageToTake = rand.nextInt(MAX_DAMAGE);
		damageToTake++;
		e.takeDamage(damageToTake);
		str += this.name + " hits " + e.getName() + " with a Magic Missile for " + damageToTake + " damage.";
		return str;
	}

	/** * Override fireball method implemented from Magical and calculates random damage up to Enemies max hp * 2 to deal to hero Entity e
	* @param e enemy entity to take damage
	* @return String displaying the attack
	*/
  	public String fireball(Entity e)
  	{
  		String str = "";
		final int MAX_DAMAGE = e.getMaxHP() * 2;
		Random rand = new Random();
		int damageToTake = rand.nextInt(MAX_DAMAGE);
		damageToTake++;
		e.takeDamage(damageToTake);
		str += this.name + " hits " + e.getName() + " with a Fireball for " + damageToTake + " damage.";
		return str;
  	}

	/** * Override thunderclap method implemented from Magical and calculates random damage up to Enemies max hp * 2 to deal to hero Entity e
	* @param e enemy entity to take damage
	* @return String displaying the attack
	*/
  	public String thunderclap(Entity e)
  	{
  		String str = "";
		final int MAX_DAMAGE = e.getMaxHP() * 2;
		Random rand = new Random();
		int damageToTake = rand.nextInt(MAX_DAMAGE);
		damageToTake++;
		e.takeDamage(damageToTake);
		str += this.name + " zaps " + e.getName() + " with a Thunderclap for " + damageToTake + " damage.";
		return str;
  	}
}