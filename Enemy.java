import java.util.Random;

public class Enemy extends Entity
{
	private Item item;

	/** * Constructor for Enemy Class
	* @param n name of the Enemy 
	* @param mHp maximum hp the Enemy can have
	* @param i The item the Enemy drops when defeated 
	*/
	public Enemy(String n, int mHp, Item i)
	{
		//Adds up to 3 extra hp to max hp
		super(n, new Random().nextInt(4) + mHp);
		String itemName = i.getName();
	    this.item = new Item(itemName);
	}

	/** * Get method for item
	* @return The item the Enemy drops when defeated  
	*/
	public Item getItem()
	{
		return this.item;
	}


	/** * Overrides attack method inherited from Entity and Calculates random damage to deal to Hero entity
	* @param e hero entity to take damage 
	* @return String displaying the attack  
	*/
	public String attack(Entity e)
	{
		Random rand = new Random();
		String str = "";
		final int MAX_DAMAGE = e.getMaxHP();
		int randomDamage = rand.nextInt(MAX_DAMAGE);
		randomDamage++;
		e.takeDamage(randomDamage);
		str += this.name + " attacks " + e.getName() + " for " + randomDamage + " damage.";
		return str;
	}
}