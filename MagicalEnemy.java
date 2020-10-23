import java.util.Random;

public class MagicalEnemy extends Enemy implements Magical
{
	/** * Constructor for MagicalEnemy
	* @param n name of MagicalEnemy
	* @param mHp maximum hp of MagicalEnemy
	* @param i The item the MagicalEnemy drops when defeated 
	*/
	public MagicalEnemy(String n, int mHp, Item i)
	{
		super(n, mHp, i);
	}

	/** * Override attack method inherited from Enemy and randomly chooses which attack method to use on Hero
	* @param e hero entity to take damage
	* @return String displaying the attack
	*/
	public String attack(Entity e)
	{
		Random rand = new Random();
		int NUM_ATTACK_CHOICES = 3;
		int attackChoice = rand.nextInt(NUM_ATTACK_CHOICES);
		attackChoice++;
		String str = "";
		switch(attackChoice)
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
		return str;
	}

	/** * Override magic missile method implemented from Magical and calculates random damage to deal to hero Entity e
	* @param e hero entity to take damage
	* @return String displaying the attack
	*/
	public String magicMissile(Entity e)
	{
		String str = "";
		Random rand = new Random();
		final int MAX_DAMAGE = e.getMaxHP();
		int damageToTake = rand.nextInt(MAX_DAMAGE);
		damageToTake++;
		e.takeDamage(damageToTake);
		str += this.name + " hits " + e.getName() + " with a Magic Missile for " + damageToTake + " damage.";
		return str;
	}

	/** * Override fireball method implemented from Magical and calculates random damage to deal to hero Entity e
	* @param e hero entity to take damage
	* @return String displaying the attack
	*/
  	public String fireball(Entity e)
  	{
  		String str = "";
		Random rand = new Random();
		final int MAX_DAMAGE = e.getMaxHP();
		int damageToTake = rand.nextInt(MAX_DAMAGE);
		damageToTake++;
		e.takeDamage(damageToTake);
		str += this.name + " hits " + e.getName() + " with a Fireball for " + damageToTake + " damage.";
		return str;
  	}

  	/** * Override thunderclap method implemented from Magical and calculates random damage to deal to hero Entity e
	* @param e hero entity to take damage
	* @return String displaying the attack
	*/
  	public String thunderclap(Entity e)
  	{
  		String str = "";
		Random rand = new Random();
		final int MAX_DAMAGE = e.getMaxHP();
		int damageToTake = rand.nextInt(MAX_DAMAGE);
		damageToTake++;
		e.takeDamage(damageToTake);
		str += this.name + " zaps " + e.getName() + " with a Thunderclap for " + damageToTake + " damage.";
		return str;
  	}
}