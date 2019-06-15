package trominoes;

/**
 * MVC's Controller. Contains the main method and uses
 * reflection to process button presses during the
 * runtime of the program. 
 * 
 * @author Charles Mercenit
 */

public class Controller
{
	private View myView;
	private Model myModel;
	
	public Controller()
	{
		myView = new View(this);
		myModel = new Model(myView);
	}
	
	public static void main(String args[])
	{
		new Controller();
	}
	
	/**
	 * When the start button is pressed, executes this
	 * method. Finds where the deficient square is and
	 * passes it to the Model to tile the board.
	 */
	
	public void start()
	{
		int[] coordinates = myView.findDeficientCoordinates();
		myModel.tile(myView.getSize()*2, coordinates[0], coordinates[1], 0, 0);
	}
	
	/**
	 * When the reset button is pressed, executes this
	 * method. Resets the board.
	 */
	
	public void reset()
	{
		myView.reset();
	}
	
	/**
	 * When the generate button is pressed, executes
	 * this method. Generates the grid of size equal
	 * to selected size.
	 */
	
	public void generateGrid()
	{
		myView.generateGrid(myView.getSize());
	}
	
	/**
	 * When any button is pressed in the grid, executes
	 * this method to mark it as selected.
	 * @param position of selected button
	 */
	
	public void selectTile(Integer pos)
	{
		myView.chooseTile(pos);
	}
}