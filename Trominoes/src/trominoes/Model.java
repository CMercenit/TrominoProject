package trominoes;

import java.awt.Color;
import java.util.Random;
import javax.swing.JButton;

/**
 * MVC's Model. Uses recursion to tile a board of size
 * n (selected by the user) with trominoes based on a 
 * deficient square selected by the user.
 * 
 * @author Charles Mercenit
 */
public class Model
{
	private View myView;
	private JButton[][] myButtonGrid;
	
	public Model(View view)
	{
		myView = view;
	}
	
	/**
	 * Recursively calculates the positions of each
	 * tromino in relation to the deficient square
	 * and colors the appropriate buttons as trominoes
	 * until the board is filled.
	 * 
	 * @param size of the button grid
	 * @param loction of x
	 * @param location of y
	 * @param position of x
	 * @param position of y
	 */
	
	public void tile(int boardSize, int locX, int locY, int x, int y)
	{
		myButtonGrid = myView.getButtonGrid();
		Color myRandColor = randomColor();
		if(boardSize == 2)
		{
			if(locX == x)
			{
				if(locY == y)
				{
					if(!myButtonGrid[x][y].getBackground().equals(Color.BLACK))
					{
						myButtonGrid[x][y].setBackground(Color.PINK);
					}
					myButtonGrid[x+1][y].setBackground(myRandColor);
					myButtonGrid[x][y+1].setBackground(myRandColor);
					myButtonGrid[x+1][y+1].setBackground(myRandColor);
				}
				else
				{
					if(!myButtonGrid[x][y+1].getBackground().equals(Color.BLACK))
					{
						myButtonGrid[x][y+1].setBackground(Color.PINK);
					}
					myButtonGrid[x][y].setBackground(myRandColor);
					myButtonGrid[x+1][y].setBackground(myRandColor);
					myButtonGrid[x+1][y+1].setBackground(myRandColor);
				}
			}
			else
			{
				if(locY == y)
				{
					if(!myButtonGrid[x+1][y].getBackground().equals(Color.BLACK))
					{
						myButtonGrid[x+1][y].setBackground(Color.PINK);
					}
					myButtonGrid[x][y].setBackground(myRandColor);
					myButtonGrid[x][y+1].setBackground(myRandColor);
					myButtonGrid[x+1][y+1].setBackground(myRandColor);
				}
				else
				{
					if(!myButtonGrid[x+1][y+1].getBackground().equals(Color.BLACK))
					{
						myButtonGrid[x+1][y+1].setBackground(Color.PINK);
					}
					myButtonGrid[x][y].setBackground(myRandColor);
					myButtonGrid[x+1][y].setBackground(myRandColor);
					myButtonGrid[x][y+1].setBackground(myRandColor);
				}
			}
		}
		else
		{
			int compX, compY, topRX, topRY, topLX, 
				topLY, botRX, botRY, botLX, botLY;
			compX = (boardSize/2) + x;
			compY = (boardSize/2) + y;
			
			if(locX < compX)
			{
				botRY = compY - 1;
				topRX = compX;
				botRX = compX;
				topRY = compY;
				if(locY < compY)
				{
					topLX = compX - 1;
					topLY = compY;
					botLX = locX;
					botLY = locY;
				}
				else
				{
					botLX = compX - 1;
					botLY = compY - 1;
					topLX = locX;
					topLY = locY;
				}
			}
			else
			{
				topLX = compX - 1;
				botLX = compX - 1;
				botLY = compY - 1;
				topLY = compY;
				if(locY < compY)
				{
					topRX = compX;
					topRY = compY;
					botRX = locX;
					botRY = locY;
				}
				else
				{
					botRY = compY - 1;
					topRX = locX;
					topRY = locY;
					botRX = compX;
				}
			}
			
			tile(boardSize/2, topLX, topLY, x, y+(boardSize/2));
			tile(boardSize/2, topRX, topRY, x+(boardSize/2), y+(boardSize/2));
			tile(boardSize/2, botRX, botRY, x+(boardSize/2), y);
			tile(boardSize/2, botLX, botLY, x, y);
		}
	}
	
	/**
	 * Generates a random color to use as a tromino
	 * tile.
	 * 
	 * @return random color
	 */
	
	private Color randomColor()
	{
		Random rand = new Random();
		switch(rand.nextInt(8) + 1)
		{
			case 1:
				return Color.RED;
			case 2:
				return Color.ORANGE;
			case 3:
				return Color.YELLOW;
			case 4:
				return Color.GREEN;
			case 5:
				return Color.BLUE;
			case 6:
				return Color.CYAN;
			case 7:
				return Color.MAGENTA;
			case 8:
				return Color.PINK;
			default:
				return Color.GRAY;
		}
	}
}