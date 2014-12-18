/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
/**
 *
 * @author Jay Poddar and Ishaan Grover
 */
public class MazeSolver
{
    Vertex [][] _maze;
    
    int _noOfRows;
    int _noOfc;
    static int ctr;
    
    Vertex _source;
    
    Vertex _destination;
    public JFrame frame = new JFrame();
    
    PriorityQueue<Vertex> queue=new PriorityQueue<Vertex>();
    
    Set<Vertex> set=new HashSet<Vertex>();
    
    public MazeSolver() throws Exception
    {
        getInput();
        
        solveMaze();
        printSolution();

    }

    private void getInput() throws Exception
    {
    	FileReader fr = new FileReader(getChosenFile());
        
        BufferedReader br = new BufferedReader(fr);
        StringTokenizer st =new StringTokenizer(br.readLine());
        _noOfRows=Integer.parseInt(st.nextToken());
        _noOfc =Integer.parseInt(st.nextToken());
        _maze=new Vertex[_noOfRows][_noOfc];
        
        String s;
        char ch;
        for (int i = 0; i < _noOfRows; i++)
        {
            s=br.readLine();
            for(int j=0;j<_noOfc;j++)
            {
                ch=s.charAt(j);
                _maze[i][j]=new Vertex('.',j,i);
                
                if(ch=='x'||ch=='X' || ch=='|')
                {
                	_maze[i][j]._value='|';
                    _maze[i][j]._isWall=true;                    
                }
                else if(ch=='*')
                {
                    if(_source == null)
                    {
                    	_maze[i][j]._value='*';
                        _source=_maze[i][j];
                    }
                    else
                    {
                        _destination=_maze[i][j];
                        _maze[i][j]._value='*';
                    }
                }
            }            
        }
        for(int i=0;i<_noOfRows;i++)
        {
        	for(int j=0;j<_noOfc;j++)
        	{
        		heuristic(_maze[i][j]);
        	}
        }
        fr.close();
    }

    private ArrayList<Vertex> getNeighbour(Vertex u)
    {
        ArrayList<Vertex> neighbours=new ArrayList<Vertex>();

        if(!((u._x - 1)<0)&&!_maze[u._y][u._x-1]._isWall)
        {
            neighbours.add(_maze[u._y][u._x-1]);
        }

        if(((u._x + 1)<_noOfc)&&!_maze[u._y][u._x + 1]._isWall)
        {
            neighbours.add(_maze[u._y][u._x + 1]);
        }

        if(!((u._y-1)<0)&&!_maze[u._y-1][u._x]._isWall)
        {
            neighbours.add(_maze[u._y-1][u._x]);
        }

       if(((u._y + 1)<_noOfRows)&&!_maze[u._y+1][u._x ]._isWall)
        {
            neighbours.add(_maze[u._y+1][u._x]);
        }
        return neighbours;
    }
    private void printSolution()
    {
        if(_destination._parent==null)
        {
            System.out.println("NO PATH");
        }
        else
        {
            for (int i = 0; i < _noOfRows; i++)
            {
                for (int j = 0; j < _noOfc; j++)
                {
                    System.out.print(_maze[i][j]+ "");
                }
                System.out.println("");
            }
        }
    }               
    private JFileChooser chooser = new JFileChooser( new File(System.getProperty("user.dir")) );
    
    public static void main(String[] args) throws Exception
    {
    	
    	

        MazeSolver mazeSolver=new MazeSolver();
        System.exit(1);
       // System.out.println(ctr);
        
    }
    

    
    
    private File getChosenFile()
    {
        if ( chooser.showOpenDialog(frame) ==JFileChooser.APPROVE_OPTION)
        {
          return chooser.getSelectedFile();
        }
        return null;
    }

    
    private void relaxNeighbours(Vertex u)
    {
        ArrayList<Vertex> neighbours= getNeighbour(u);
        for(Vertex v:neighbours)
        {
            if(!set.contains(v))
            {
            	
                if((v._bestEstimatedDistance ) >(u._bestEstimatedDistance+1))
                {
                	ctr++;
                   v._bestEstimatedDistance=(u._bestEstimatedDistance+1);
                   v._totalEstimate=v._bestEstimatedDistance+v.heuristic;
                   v._parent=u;
                   queue.add(v);
                }
            }            
        }
    }

    private void solveMaze()
    {        
        queue.add(_source);
        _source._bestEstimatedDistance=0;
        Vertex u;

        while(!queue.isEmpty())
        {
            u=queue.remove();
            set.add(u);
            if(u==_destination)
            {
                break;
            }
            relaxNeighbours(u);
        }
        setPath();
    }
    private void setPath()
    {
        Vertex v=_destination;
        if(v._parent==null)
        {
            return;
        }
        while(v!=_source)
        {
            v._value='*';
            v=v._parent;
        }
        _source._value='*';
        _destination._value = '*';
    }
    
    public void heuristic(Vertex v)
    {
    	if(v._isWall)return;
    	v.heuristic = ((_destination._x - v._x)*(_destination._x - v._x)) + ((_destination._y -v._y)*(_destination._y -v._y));
    	v.heuristic = (int)Math.sqrt(v.heuristic);
    	v._totalEstimate=v.heuristic;
    }

}

