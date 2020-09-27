package Items;

public class Item {
			  
			    public String name;


			    public String Item(String n){}

			    public Item(String n)
			    {
			    	this.name = name;
			    }
			 
			 	//Getter
			    public String getName() {
			         return name;
			    }
			    //Setter
			    public void setName(String name) {
			         this.name = name;
			    }
			    
			    @Override
			    public String toString() {
			        return name;
			    }
			}