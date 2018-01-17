package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.deeplearning4j.rl4j.space.ActionSpace;
import org.deeplearning4j.rl4j.space.DiscreteSpace;

import commons.Constants;

public class OrderActionSpace extends DiscreteSpace 
{
    //size of the space also defined as the number of different actions
    final static protected List<Integer> actions = new ArrayList<Integer>();
    static {
    	actions.add(Constants.ACTION_DO_NOTHING);
    	actions.add(Constants.ACTION_BUY);
    	actions.add(Constants.ACTION_SELL);
    }
    
    public OrderActionSpace() {
    	super(actions.size());
    	//this.actions= actions;
    }
                
    public Integer randomAction() {
    	//System.out.println("Choose random action from "+actions.stream().map(Object::toString).collect(Collectors.joining(", ")));
        return actions.get(rd.nextInt(actions.size()));
    }

    public Integer noOp() {
        return Constants.ACTION_DO_NOTHING;
    }
    
}