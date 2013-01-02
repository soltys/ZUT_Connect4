package pl.zut.edu.wsdi.connect4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import pl.zut.edu.wsdi.klesk.math.search.AlphaBetaSearcher;
import pl.zut.edu.wsdi.klesk.math.search.State;

public class Connect4Searcher extends AlphaBetaSearcher
{

    public Connect4Searcher(State aStartState, boolean aIsMaximizingPlayerFirst, double aMaximumDepth)
    {//konstruktor
        super(aStartState, aIsMaximizingPlayerFirst, aMaximumDepth);
    }

    @Override
    public void buildChildren(State aParent)
    {
        Connect4State parent = (Connect4State) aParent;
        List<State> children = new ArrayList<State>();

        for (int i = 0; i < parent.getColumns(); i++)
        {
            Connect4State child = new Connect4State(parent);

            if (child.makeMove(i))
            {
                child.setRootMove(Integer.toString(i));
                children.add(child);
            }
            child.playersSwitch();
        }
        parent.setChildren(children);
    }


}
