package trominoes;
/**
 * A basic buttonlistener that has as its primary responsibility
 * that of invoking the appropriate method of some other class
 * when the mouse pointer is over the button and is released.
 *
 * @author Daniel Plante
 * @version 1.0 (28 January 2002)
 */
import java.lang.reflect.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.lang.IllegalAccessException;

public class ButtonListener extends MouseAdapter
{
    //////////////////////////
    //     Properties       //
    //////////////////////////
    
    private Object myController;
    private Method myMethod;
    private Object[] myArguments;
    
    //////////////////////////
    //       Methods        //
    //////////////////////////

    /**
     * Only constructor, giving the method that should be invoked
     * when the mouse is released over the button; has the 
     * responsibility of notifying controller when mouse released.
     *
     * <pre>
     * pre:  controller and method passed in are valid
     * post: myController and myMethod are set
     * </pre>
     *
     * @param controller the controller this listener has a
     *        responsible to
     * @param method the method to invoke when button is pushed
     * @param args the array of objects that are the parameters
     *        for the method that will be invoked by the controller
     */
    public ButtonListener(Object controller, Method method, Object[] args)
    {
        myController = controller;
        myMethod = method;
        myArguments = args;
    }

    /**
     * Invokes the appropriate method when the mouse
     * button is released.
     *
     * <pre>
     * pre:  a valid MouseEvent has taken place, and the controller
     *       and method this listener is responsible to and how, are
     *       set to value values
     * post: the associated method is invoked
     * </pre>
     *
     * @param event a mouse event
     */
    public void mouseReleased(MouseEvent event)
    {
        Method method;
        Object controller;
        Object[] arguments;
    
        method = this.getMethod();
        controller = this.getController();
        arguments = this.getArguments();
        
        try
        {
            method.invoke(controller, arguments);
        }
        catch(InvocationTargetException exception)
        {
        	String error;
        	error = exception.toString();
        	//System.out.println(error);
        }
        catch(IllegalAccessException exception)
        {
            System.out.println("IllegalAccessException");
        }
        catch(IllegalArgumentException exception)
        {
            System.out.println("IllegalArgumentException");
        }
    }
  
    //////////////////////////
    //   Accessor Methods   //
    //////////////////////////

    protected Method getMethod()
    {
        return myMethod;
    }

    protected void setMethod(Method method)
    {
        myMethod = method;
    }

    protected Object getController()
    {
        return myController;
    }
      
    protected void setController(Object controller)
    {
        myController = controller;
    }
    
    protected Object[] getArguments()
    {
        return myArguments;
    }
    
    protected void setArguments(Object[] arguments)
    {
        myArguments = arguments;
    }
}