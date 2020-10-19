public class Item 
{	  
	public String name;

	public Item(String n)
	{
		this.name = name;
	}
	
	public String getName() {
	     return name;
	}
	
	public void setName(String name) {
	     this.name = name;
	}
	
	public String toString() {//vs getName()
	    return name;
	}
}