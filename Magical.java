public interface Magical 
{
	public static final String MAGIC_MENU = "1. Magic Missile\n2. Fireball\n3. Thunderclap";
  	
  	/** * Calculates random damage to deal to Entity e
	* @param e Entity to take damage
	* @return String displaying the attack
	*/
  	public String magicMissile(Entity e);

  	/** * Calculates random damage to deal to Entity e
	* @param e Entity to take damage
	* @return String displaying the attack
	*/
  	public String fireball(Entity e);

  	/** * Calculates random damage to deal to Entity e
	* @param e Entity to take damage
	* @return String displaying the attack
	*/
  	public String thunderclap(Entity e);
}