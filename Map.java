//https://bbcsulb.desire2learn.com/d2l/le/dropbox/600630/489567/DownloadAttachment?fid=21706897
public class Map
{
	private char[][] map;
	private boolean[][] revealed;

	public Map()
	{
		map = new char[5][5];
		revealed = new boolean[5][5];
	}

	public void loadMap(int mapNum)
	{
		//If the user reaches the finish, then they have passed the level. The hero’s hp is
		//replenished (if needed) and the hero moves to the next map. Re-load the first map
		//for level 4, second map for level 5, etc
    for (int i = 0; i < revealed.length; i++)
		{
			for (int j = 0; j < revealed[0].length; j++)
			{
				revealed[i][j] = false;
			}
		}

		switch (mapNum)
		{
			case 1:
				map = new char[][] { {'a','s','a','a','a'}, {'a','a','a','a','a'}, {'a','a','a','a','a'}, {'a','a','a','a','a'}, {'a','a','a','a','a'} };
			case 2:
				map = new char[][] { {'a','s','a','a','a'}, {'a','a','a','a','a'}, {'a','a','a','a','a'}, {'a','a','a','a','a'}, {'a','a','a','a','a'} };
			case 3:
				map = new char[][] { {'a','s','a','a','a'}, {'a','a','a','a','a'}, {'a','a','a','a','a'}, {'a','a','a','a','a'}, {'a','a','a','a','a'} };
			case 4:
				map = new char[][] { {'a','s','a','a','a'}, {'a','a','a','a','a'}, {'a','a','a','a','a'}, {'a','a','a','a','a'}, {'a','a','a','a','a'} };
			case 5:
				map = new char[][] { {'a','s','a','a','a'}, {'a','a','a','a','a'}, {'a','a','a','a','a'}, {'a','a','a','a','a'}, {'a','a','a','a','a'} };
		}
	}

	public char getCharAtLoc(Point p)
	{
		return map[p.getX()][p.getY()];
	}

	public void displayMap(Point p)
	{
		for (int i = 0; i < map.length; i++)
		{
			for (int j = 0; j < map[0].length; j++)
			{
				if (revealed[i][j])
				{
          if (i == p.getX() && j == p.getY())
          {
            System.out.println("s ");
          }
          else
          {
            System.out.print(map[i][j] + " ");
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

	public Point findStart()
	{
		for (int i = 0; i < map.length; i++)
		{
			for (int j = 0; j < map[0].length; j++)
			{
				if (map[i][j] == 's')
				{
					return new Point(i, j); 
				}
			}
		}
		return new Point();
	}

	public void reveal(Point p)
	{
		revealed[p.getX()][p.getY()] = true;
	}

	public void removeCharAtLoc(Point p)
	{
		//Not sure what to do here
		//map[p.getX()][p.getY()];
	}
}