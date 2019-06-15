package trominoes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.lang.reflect.Method;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * MVC's View. Displays the application window,
 * generates the button grid, and uses reflection
 * to associate listeners with each button in the
 * application. 
 * 
 * @author Charles Mercenit
 */

public class View
{
	private final Font FONT = new Font("f", Font.BOLD, 15);
	private final Color OPTION_BACKGROUND = Color.LIGHT_GRAY; 
	private final Color SELECTED = Color.BLACK;
	private final Color RESET = Color.WHITE;
	private final int DROPDOWN_OPTIONS = 7;
	private final int FRAME_WIDTH = 1000;
	private final int FRAME_HEIGHT = 900;
	
	private Controller myController;
	private JFrame myFrame = new JFrame("Trominoes");
	private Container myContentPane;
	private JPanel  myTrominoPanel,
					myOptionsPanel,
					myBackground;
	private JButton myStart, myReset, myGridButton;
	private JButton[][] myButtonGrid;
	private JComboBox<Integer> myGridSize;
	private JTextField myHeading, myInstructions;
	private ButtonListener  myStartListener,
							myResetListener,
							myGridListener;
	private ButtonListener[][] myButtonGridListener;
	
	private int mySize;
	private boolean myStarted = false;
	private int[] coordinates = new int[2];
	
	public View(Controller controller)
	{
		myController = controller;
		
		//Creates the frame
		myFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		myFrame.setLocation(500, 100);
		myFrame.setLayout(null);
		myFrame.setResizable(false);
		
		//Creates the content pane where everything is placed
		myContentPane = myFrame.getContentPane();
		myContentPane.setLayout(new BorderLayout());
		
		//Creates the panel that contains everything
		myTrominoPanel = new JPanel();
		myTrominoPanel.setBorder(BorderFactory.createEtchedBorder());
		myTrominoPanel.setLayout(null);
		
		//Creates the option panel with the buttons at the top
		myOptionsPanel = new JPanel();
		myOptionsPanel.setLayout(null);
		myOptionsPanel.setSize(FRAME_WIDTH, 50);
		myOptionsPanel.setLocation(0, 0);
		myOptionsPanel.setBackground(OPTION_BACKGROUND);
		
		//Creates the Start button
		myStart = new JButton("Start");
		myStart.setLayout(null);
		myStart.setSize(100, 25);
		myStart.setLocation(765, 10);
		myOptionsPanel.add(myStart);
		
		//Creates the Reset button
		myReset = new JButton("Reset");
		myReset.setLayout(null);
		myReset.setSize(100, 25);
		myReset.setLocation(875, 10);
		myOptionsPanel.add(myReset);
		
		//Creates the Generate button
		myGridButton = new JButton("Generate");
		myGridButton.setLayout(null);
		myGridButton.setSize(100, 25);
		myGridButton.setLocation(210, 10);
		myOptionsPanel.add(myGridButton);
		
		//Adds text describing the ComboBox
		myHeading = new JTextField("Choose a power of 2: ");
		myHeading.setLayout(null);
		myHeading.setSize(150, 25);
		myHeading.setBorder(BorderFactory.createEmptyBorder());
		myHeading.setEditable(false);
		myHeading.setLocation(1, 10);
		myHeading.setFont(FONT);
		myHeading.setBackground(OPTION_BACKGROUND);
		myOptionsPanel.add(myHeading);
		
		//Adds text updating the user on what to do
		myInstructions = new JTextField("Select a grid size and press generate!");
		myInstructions.setLayout(null);
		myInstructions.setSize(270, 25);
		myInstructions.setBorder(BorderFactory.createEmptyBorder());
		myInstructions.setEditable(false);
		myInstructions.setLocation(395, 5);
		myInstructions.setBackground(OPTION_BACKGROUND);
		myInstructions.setFont(FONT);
		myOptionsPanel.add(myInstructions);
		
		//Creates the combo box with options
		Integer[] ints = new Integer[DROPDOWN_OPTIONS];
		for(int i = 0; i < DROPDOWN_OPTIONS; i++)
		{
			ints[i] = (int)Math.pow(2, i+1);
		}
		myGridSize = new JComboBox<Integer>(ints);
		myGridSize.setSize(50, 25);
		myGridSize.setBackground(Color.WHITE);
		myGridSize.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		myGridSize.setEditable(false);
		myGridSize.setLocation(155, 10);
		myOptionsPanel.add(myGridSize);
		
		//Associates button listeners
		this.associateListeners(myController);
		
		//Condenses everything into the ContentPane
		myTrominoPanel.add(myOptionsPanel);
		myContentPane.add(myTrominoPanel, BorderLayout.CENTER);
		
		//Creates the background
		myBackground = new JPanel();
		myBackground.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		myBackground.setLocation(0, 0);
		myBackground.setBackground(Color.DARK_GRAY);
		myTrominoPanel.add(myBackground);
		
		//Displays the frame
		myFrame.setVisible(true);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Associates each button with a ButtonListener, and
	 * each ButtonListener with the appropriate method
	 * from the Controller class.
	 * 
	 * @param controller class that includes the methods
	 */
	
	private void associateListeners(Controller controller)
	{
		Class<? extends Controller> controllerClass;
		Method startMethod, resetMethod, generateMethod, gridMethod;
		Class<?>[] classArgs;
		Integer[] args;
		
		controllerClass = myController.getClass();
		
		startMethod = null;
		resetMethod = null;
		generateMethod = null;
		gridMethod = null;
		
		classArgs = new Class[1];
		
		try
		{
			classArgs[0] = Class.forName("java.lang.Integer");
		}
		catch(ClassNotFoundException e)
		{
			String error = e.toString();
			System.out.println(error);
		}
		
		try
		{
			startMethod = controllerClass.getMethod("start", (Class<?>[])null);
			resetMethod = controllerClass.getMethod("reset", (Class<?>[])null);
			generateMethod = controllerClass.getMethod("generateGrid", (Class<?>[])null);
			gridMethod = controllerClass.getMethod("selectTile", classArgs);
		}
		catch(SecurityException e)
		{
			String error = e.toString();
			System.out.println(error);
		}
		catch(NoSuchMethodException e)
		{
			String error = e.toString();
			System.out.println(error);
		}
		
		if(!myStarted)
		{		
			myStartListener = new ButtonListener(myController, startMethod, null);
			myStart.addMouseListener(myStartListener);
			myResetListener = new ButtonListener(myController, resetMethod, null);
			myReset.addMouseListener(myResetListener);
			myGridListener = new ButtonListener(myController, generateMethod, null);
			myGridButton.addMouseListener(myGridListener);
			myStarted = true;
		}
		
		if(myButtonGrid != null) {
			myButtonGridListener = new ButtonListener[myButtonGrid.length][myButtonGrid.length];
			for(int i = 0; i < myButtonGridListener.length; i++)
			{
				for(int j = 0; j < myButtonGridListener[i].length; j++)
				{
					args = new Integer[1];
					args[0] = new Integer(Integer.parseInt(myButtonGrid[i][j].getName()));
					myButtonGridListener[i][j] = new ButtonListener(myController, gridMethod, args);
					myButtonGrid[i][j].addMouseListener(myButtonGridListener[i][j]);
				}
			}
		}
	}
	
	/**
	 * Generates a grid of buttons based on size
	 * selected in the ComboBox.
	 * 
	 * @param size of grid to generate
	 */
	
	public void generateGrid(int size)
	{
		myBackground.removeAll();
		mySize = size*2;
		myButtonGrid = new JButton[mySize][mySize];
		int buttonWidth = (myFrame.getWidth()-100)/mySize;
		int buttonHeight = (myFrame.getHeight()-200)/mySize;
		for(int i = 0; i < mySize; i++)
		{
			for(int j = 0; j < mySize; j++)
			{
				myButtonGrid[i][j] = new JButton();
				myButtonGrid[i][j].setLayout(null);
				myButtonGrid[i][j].setBackground(RESET);
				myButtonGrid[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
				myButtonGrid[i][j].setSize(buttonWidth, buttonHeight);
				myButtonGrid[i][j].setLocation(50+(buttonWidth*j), 110+(buttonHeight*i));
				myButtonGrid[i][j].setName("0" + (i+1) + "5" + (j+1));
				myBackground.add(myButtonGrid[i][j]);
			}
		}
		myFrame.repaint();
		myInstructions.setLocation(395, 5);
		myInstructions.setSize(300, 25);
		myInstructions.setText("Choose a deficient square and press start!");
		this.associateListeners(myController);
	}
	
	/**
	 * Returns the size of the grid selected in the
	 * ComboBox.
	 * 
	 * @return selected size
	 */
	
	public int getSize()
	{
		return (int)Math.pow(2, myGridSize.getSelectedIndex());
	}
	
	/**
	 * Colors the selected tile differently from each
	 * other tile based on the passed in position. If
	 * there is already a selected tile, reverts it
	 * to the original color before selecting a new one.
	 * 
	 * @param position of selected tile
	 */
	
	public void chooseTile(int pos)
	{
		for(int i = 0; i < mySize; i++)
		{
			for(int j = 0; j < mySize; j++)
			{
				if(myButtonGrid[i][j].getBackground().equals(SELECTED))
				{
					myButtonGrid[i][j].setBackground(RESET);
				}
				if(Integer.parseInt(myButtonGrid[i][j].getName()) == pos)
				{
					myButtonGrid[i][j].setBackground(SELECTED);
				}
			}
		}
	}
	
	/**
	 * Finds the coordinates of the deficient square
	 * selected by the user and also updates the
	 * instructions TextBox.
	 * 
	 * @return coordinates of selected button
	 */
	
	public int[] findDeficientCoordinates()
	{
		myInstructions.setLocation(445, 5);
		myInstructions.setSize(205, 25);
		myInstructions.setText("Press reset to try again!");
		
		for(int i = 0; i < mySize; i++)
		{
			for(int j = 0; j < mySize; j++)
			{
				if(myButtonGrid[i][j].getBackground().equals(SELECTED))
				{
					coordinates[0] = i;
					coordinates[1] = j;
				}
			}
		}
		return coordinates;
	}
	
	/**
	 * Returns the grid of buttons.
	 * 
	 * @return button grid
	 */
	
	public JButton[][] getButtonGrid()
	{
		return myButtonGrid;
	}
	
	/**
	 * Resets the button grid to the original color and
	 * updates the instruction TextBox to inform the
	 * user of what to do next.
	 */
	
	public void reset()
	{
		myInstructions.setLocation(395, 5);
		myInstructions.setSize(300, 25);
		myInstructions.setText("Choose a deficient square and press start!");
		for(int i = 0; i < mySize; i++)
		{
			for(int j = 0; j < mySize; j++)
			{
				myButtonGrid[i][j].setBackground(RESET);
			}
		}
	}
}