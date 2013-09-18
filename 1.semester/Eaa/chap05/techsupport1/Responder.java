package chap05.techsupport1;

import java.util.ArrayList;
import java.util.Random;

/**
 * The responder class represents a response generator object.
 * It is used to generate an automatic response to an input string.
 * 
 * @author     Michael Kölling and David J. Barnes
 * @version    0.1 (2011.07.31)
 */
public class Responder
{
	private ArrayList<String> list;
	private Random ran;
    /**
     * Construct a Responder - nothing to do
     */
    public Responder()
    {
    	ran = new Random();
    	list = new ArrayList<String>();
    }

    /**
     * Generate a response.
     * @return   A string that should be displayed as the response
     */
    public String generateResponse()
    {
        list.add("You mad!");
        list.add("moo");
        return list.get(0);
    }
}
