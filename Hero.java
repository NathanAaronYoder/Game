import java.util.Random;

public class Hero extends Entity implements Magical
{
	private ArrayList<Item> items;
	private Map map;
	private Point location;

	public Hero(String n, Map m)
	{
		super(n);//Not In Entity Class Yet
		map = m;
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
		return false;
	}

	public void drinkPotion()
	{
		if (hasPotion())
		{
			//Increase HP
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

	}

	public char goSouth()
	{

	}

	public char goEast()
	{

	}

	public char goWest()
	{

	}

	public String toString()
	{
		String str = super.toString() + "\n";
		str += itemsToString();
		return str;
	}

	public abstract String attack(Entity e)
	{

	}

	public String magicMissile(Enemy e)//Can I change Entity to Enemy?
	{
		int maxNumberOfItems = 5;//https://www.geeksforgeeks.org/final-keyword-java/
		String str = "";
		int maxDamage = 10;
		Random rand = new Random();
		int damageToTake = rand.nextInt(maxDamage);
		e.takeDamage(damageToTake);
		str += name + "hits " + e.getName() + "with a Magic Missile for " + damageToTake + " damage.";
		if (e.getHP() == 0)
		{
			str += "\nYou defeated the " + e.getName() + "!";
			if (pickUpItem(e.getItem()))
			{
				str += "\nYou received a " + e.getItem() + " from its corpse.";
			}
			else//Not Sure What to Print
			{
				str += "\nYour inventory is too full";
			}
			removeCharAtLoc(location);
		}
		return str;
	}

  	public String fireball(Enemy e)//Can I change Entity to Enemy?
  	{
  		int maxDamage = 10;
  		Random rand = new Random();
  		int damageToTake = rand.nextInt(maxDamage);
  		e.takeDamage(damageToTake);
  	}

  	public String thunderclap(Enemy e)//Can I change Entity to Enemy?
  	{
  		int maxDamage = 10;
  		Random rand = new Random();
  		int damageToTake = rand.nextInt(maxDamage);
  		e.takeDamage(damageToTake);
  	}
}