import java.util.ArrayList; 
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class ItemGenerator
{
	private ArrayList<Item> itemList;

	/** * Constructor for ItemGenerator using itemList.txt, add every item to itemList
	* @throws FileNotFoundException when itemList.txt cannot be found
	*/
	public ItemGenerator()
	{
		Scanner read = null;
		this.itemList = new ArrayList<Item>();
		//Get item information from itemList.txt and store in itemList
	   	try 
	   	{
			read = new Scanner(new File("itemList.txt"));
			while(read.hasNext()) {
				String line = read.nextLine();

				Item item = new Item(line);
				this.itemList.add(item);
			}
			read.close();
	   	}
	   	catch(FileNotFoundException e)
	   	{
	     	System.out.println("File Not Found - place file in the project folder. ");
	   	}
	}

	/** * Return a random Item from itemList
	* @return random Item from itemList
	*/
	public Item generateItem()
	{
		Random rand = new Random();
		final int NUM_ITEMS = this.itemList.size();
        return this.itemList.get(rand.nextInt(NUM_ITEMS)); 
	}
}