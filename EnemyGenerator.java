import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class EnemyGenerator
{
	private ArrayList<Enemy> enemyList;
	private ItemGenerator ig;

	/** * Constructor for EnemyGenerator reading from enemyList.txt, adds every enemy to enemyList
	* @param ig used to generate an item for each Enemy 
	* @throws FileNotFoundException when enemyList.txt cannot be found
	*/
	public EnemyGenerator(ItemGenerator ig)
	{
		this.enemyList = new ArrayList<Enemy>();
		Scanner read = null;
		//Get enemy information from enemyList.txt and store in enemyList
	   	try 
	   	{
			read = new Scanner(new File("enemyList.txt"));
			while(read.hasNext()) {
				String line = read.nextLine();

				String name;
				int hp;
				char type;
				final int NAME_INDEX = 0;
				final int HP_INDEX = 1;
				final int TYPE_INDEX = 2;

				//Split line into tokens to get name, hp, and type data
				String[] tokens = line.split(",");
				name = tokens[NAME_INDEX];
				hp = Integer.parseInt(tokens[HP_INDEX]);
				type = tokens[TYPE_INDEX].charAt(0);

				switch(type)
				{
					case 'p':
						//Add a Physical Enemy to enemyList
						Enemy enemy = new Enemy(name, hp, ig.generateItem());
						this.enemyList.add(enemy);
						break;
					case 'm':
						//Add a Magical Enemy to enemyList
						MagicalEnemy magicalEnemy = new MagicalEnemy(name, hp, ig.generateItem());
						this.enemyList.add(magicalEnemy);
						break;
				}
			}
			read.close();
	    }
	    catch(FileNotFoundException e)
	    {
	     	System.out.println("File Not Found - place file in the project folder. ");
	   	}
	}

	/** * Generate a random Enemy from enemyList
	* @param i The item the Enemy drops when defeated
	*/
	public Enemy generateEnemy()
	{
		Random rand = new Random();
		final int NUM_ENEMIES = this.enemyList.size();
		int randNum = rand.nextInt(NUM_ENEMIES);
		return this.enemyList.get(randNum);
	}
}