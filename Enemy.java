import java.util.Random;

public class Enemy extends Entity
{
	//State
	private Item item;

	//Constructor
	public Enemy(Str n, int mHp, Item i)
	{
		Random rand = new Random();
		int extraHP = rand.nextInt(4);//Adds up to 3 hp
		mHp += extraHP;
		super(n, mHp);
		//Construct item
		String itemName = i.getName();
	    item = new Item(itemName);
	}

	public Item getItem()
	{
		//Get Item
		return item;
	}

	public String attack(Entity e)
	{
		Random rand = new Random();
		String str = "";
		int maxDamage = 10;
		int randomDamage = rand.nextInt(maxDamage);
		randomDamage++;
		e.takeDamage(randomDamage);
		str += name + "attacks " + e.getName() + " for " + randomDamage + " damage.";
		return str;
	}
}