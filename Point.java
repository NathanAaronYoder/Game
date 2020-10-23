public class Point
{
	private int x;
	private int y;

	/** * Constructor for Point class
	* @param x x coordinate of point
	* @param y y coordinate of point
	*/
	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	/** * Get x coordinate
	* @return x coordinate
	*/
	public int getX()
	{
		return this.x;
	}

	/** * Get y coordinate
	* @return y coordinate
	*/
	public int getY()
	{
		return this.y;
	}

	/** * Change x by dx and y by dy
	* @param dx change in x
	* @param dy change in y
	*/
	public void translate(int dx, int dy)
	{
		this.x += dx;
		this.y += dy;
	}

	/** * Check to see if this.point is equal to point parameter
	* @param point point to check equivalency
	* @return true if point is equivalent to this.Point
	*/
	public boolean equals(Point point)
	{
		if (point.getX() == this.x && point.getY() == this.y)
		{
			return true;
		}
		return false;
	}
}