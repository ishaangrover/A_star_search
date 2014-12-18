/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 

/**
 *
 * @author Jay Poddar and Ishaan Grover
 */
public class Vertex implements Comparable<Vertex>
{
    char _value;
    Vertex _parent;
    boolean _isWall=false;
    int _bestEstimatedDistance;
    int _totalEstimate;
    int _x;
    int _y;
    int heuristic;
    @Override
    public String toString()
    {
        return ""+_value;
    }

    public Vertex(char value,int x,int y)
    {
        _value = value;
        _bestEstimatedDistance=Integer.MAX_VALUE;
        _totalEstimate=Integer.MAX_VALUE;
        _x=x;
        _y=y;
    }
    public int compareTo(Vertex o)
    {
    	return _totalEstimate-o._totalEstimate;
        //return _bestEstimatedDistance-o._bestEstimatedDistance;
    }

}
