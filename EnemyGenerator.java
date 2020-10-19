import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class EnemyGenerator
{
	private ArrayList<Enemy> enemyList;
	private ItemGenerator ig;

	public EnemyGenerator(ItemGenerator ig)
	{
		//Goblin,2,m
		//name,hp,type
		Scanner read = null;
	   try {
			read = new Scanner(new File("enemyList.txt"));
			while(read.hasNext()) {
				String line = read.nextLine();

				String name;
				int hp;
				char type;


				String[] tokens = line.split(",");//["Goblin", "2", "m"]
				name = tokens[0];//Goblin
				hp = Integer.parseInt(tokens[1]);//2
				type = tokens[2].charAt(0);//m


				switch(type)
				{
					case 'p':
						Enemy enemy = new Enemy(name, hp, ig.generateItem());//Maybe wrong
						enemyList.add(enemy);
					case 'm':
						MagicalEnemy magicalEnemy = new MagicalEnemy(name, hp, ig.generateItem());//Maybe wrong
						enemyList.add(magicalEnemy);
				}

			}
			read.close();
	   }catch(FileNotFoundException e){
	     System.out.println("File Not Found - place file in the project folder. ");
	   }
	}

	public Enemy generateEnemy()
	{
		Random rand = new Random();
		int randNum = rand.nextInt(8);
		randNum++;
		return enemyList.get(randNum);
	}
}