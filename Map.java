import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Map
{
	private char[][] map;
	private boolean[][] revealed;

	//Constructor
	/** * Constructor for Map, rows and columns of 2d arrays are set to 5
	*/
	public Map()
	{
		final int NUM_ROWS = 5;
		final int NUM_COLS = 5;
		this.map = new char[NUM_ROWS][NUM_COLS];
		this.revealed = new boolean[NUM_ROWS][NUM_COLS];
	}

	/** * Change the Map to the Map of the next level
	* @param mapNum map level to load
	* @throws FileNotFoundException when map(mapNum).txt cannot be found
	*/
	public void loadMap(int mapNum)
	{
		mapNum++;

		//Set every element in revealed to false
    	for (int i = 0; i < this.revealed.length; i++)
		{
			for (int j = 0; j < this.revealed[0].length; j++)
			{
				this.revealed[i][j] = false;
			}
		}

		//Get and set the new map information from a txt file
		try 
		{
    	  	File myObj = new File("Map" + mapNum + ".txt");
    	  	Scanner myReader = new Scanner(myObj);
    	  	int row = 0;
    	  	while (myReader.hasNextLine()) {
    	  	  	String data = myReader.nextLine();
    	  	  	String[] tokens = data.split(" ");
 
 				char ch;
				for (int i = 0; i < tokens.length; i++)
				{
					ch = tokens[i].charAt(0);
					this.map[row][i] = ch;
				}
    	  	  	row++;
    	  	}
    	  	myReader.close();
    	} 
    	catch (FileNotFoundException e) 
    	{
    	  	System.out.println("File Not Found - place file in the project folder.");
    	}
	}

	/** * Get the character at Point p
	* @param p point to get the character from
	* @return character at p
	*/
	public char getCharAtLoc(Point p)
	{
		return this.map[p.getX()][p.getY()];
	}

	/** *  Print out the map, Any area not revealed yet will print an 'x'
	* @param p the hero's location which prints a '*'
	*/
	public void displayMap(Point p)
	{
		for (int i = 0; i < this.map.length; i++)
		{
			for (int j = 0; j < this.map[0].length; j++)
			{
				if (this.revealed[i][j])
				{
          			if (i == p.getX() && j == p.getY())
          			{
          			  System.out.print("* ");
          			}
          			else
          			{
          			  System.out.print(this.map[i][j] + " ");
          			}
				}
        		else
        		{
        		  System.out.print("x ");
        		}
			}
			System.out.println();
		}
	}

	//Return the Starting point by checking for character 's'
	/** * Get the Starting point of map
	* @return point of the start
	*/
	public Point findStart()
	{
		//check for character 's'
		for (int i = 0; i < this.map.length; i++)
		{
			for (int j = 0; j < this.map[0].length; j++)
			{
				if (this.map[i][j] == 's')
				{
					return new Point(i, j); 
				}
			}
		}
		//This point should never be reached
		return new Point(0,0);
	}

	/** * Set point p to true in revealed
	* @param p Point to be revealed
	*/
	public void reveal(Point p)
	{
		this.revealed[p.getX()][p.getY()] = true;
	}

	/** * Change the character at point p to 'n' representing nothing
	* @param p Point where character is removed
	*/
	public void removeCharAtLoc(Point p)
	{
		this.map[p.getX()][p.getY()] = 'n';
	}
}