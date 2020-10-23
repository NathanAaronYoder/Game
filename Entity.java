public abstract class Entity
{
	protected String name;
	protected int maxHp;
	protected int hp;

	/** * Constructor for Entity Class
	* @param n name of the Entity
	* @param mHp maximum hp the Enemy can have
	*/
	public Entity(String n, int mHp)
	{
		this.name = n;
		this.maxHp = mHp;
		//hp is initially set to max hp
		this.hp = mHp;
	}

	/** * Calculates random damage to deal to entity e
	* @param e Entity to take damage
	* @return String displaying the attack
	*/
	public abstract String attack(Entity e);

	/** * Get Name of Entity
	* @return name of Entity
	*/
	public String getName()
	{
		return this.name;
	}

	/** * Get HP of Entity
	* @return hp of Entity
	*/
	public int getHP()
	{
		return this.hp;
	}

	/** * Get Max HP of Entity
	* @return max hp of Entity
	*/
	public int getMaxHP()
	{
		return this.maxHp;
	}

	/** * Adds h hp to Entity up to maximum of max hp
	* @param h int to add to hp
	*/
	public void heal(int h)
	{
		int newHp = hp + h;
		//Make sure hp isn't higher than max hp
		if (newHp < maxHp)
		{
			this.hp = newHp;
		}
		else
		{
			this.hp = maxHp;
		}
	}

	/** * Removes hp by h from Entity down to a minimum of 0
	* @param h int to remove from hp
	*/
	public void takeDamage(int h)
	{
		int newHp = hp - h;
		//Make sure hp isn't lower than 0
		if (newHp < 0)
		{
			this.hp = 0;
		}
		else
		{
			this.hp = newHp;
		}
	}

	/** * Entity to String
	* @return String represenation of Entity's name and hp
	*/
	public String toString()
	{
		return this.name + "\nHP: " + this.hp + "/" + this.maxHp;
	}
}