import java.util.ArrayList; 
import java.util.Random; 

public class ItemGenerator
{
	private ArrayList<Item> itemList;

	//Constructor
	public ItemGenerator()
	{
		//Use File.io to read from ItemList.txt
		//Add all of the items into the itemList
		itemList.add(new Item("gem"));
		itemList.add(new Item("gem"));
		itemList.add(new Item("Health Potion"));
		itemList.add(new Item("Shield"));
		itemList.add(new Item("Bag o' Gold"));
		itemList.add(new Item("Helm"));
		itemList.add(new Item("Ring"));
		itemList.add(new Item("Boots"));
	}

	public Item generateItem()
	{
		//Return a random Item
		
		//Generate a random number
		//return itemList.get(randomNum)
		Random rand = new Random(); 
        return itemList.get(rand.nextInt(itemList.size())); 
	}
}