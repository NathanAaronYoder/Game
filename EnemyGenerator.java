import java.util.Random;

public class EnemyGenerator
{
	private ArrayList<Enemy> enemyList;
	private ItemGenerator ig;

	public EnemyGenerator(ItemGenerator ig)
	{

	}

	public Enemy generateEnemy(int level)//How do I use level
	{
		Random rand = new Random();
		int randNum = rand.nextInt();//What is the parameter?
		return ;//Confused On How to Return an Enemy
	}
}