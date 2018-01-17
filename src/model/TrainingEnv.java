package model;

import org.deeplearning4j.rl4j.mdp.MDP;
import org.deeplearning4j.rl4j.space.ArrayObservationSpace;
import org.deeplearning4j.rl4j.space.DiscreteSpace;
import org.deeplearning4j.rl4j.space.ObservationSpace;
import org.json.JSONObject;

import commons.Constants;
import commons.FakeHistoryInfoProvider;
import commons.FakeHistoryInfoProviderMaker;
import lombok.Getter;
import trading.Wallet;

import org.deeplearning4j.gym.StepReply;

public class TrainingEnv implements MDP<ModelStateObservation, Integer, DiscreteSpace> {

	FakeHistoryInfoProviderMaker provider_maker;
	private Wallet wallet;
	private FakeHistoryInfoProvider states_provider;
    private boolean done = false;
    @Getter
    private ObservationSpace<ModelStateObservation> observationSpace = new ArrayObservationSpace(new int[] {ModelStateObservation.getInfosSize()});
    private OrderActionSpace actionsSpace = new OrderActionSpace();
        
    
    public TrainingEnv(FakeHistoryInfoProviderMaker provider) {
    	this.provider_maker = provider;
    	this.wallet = new Wallet();
    	this.states_provider = provider.makeNewInfoProvider();
    }

    /*
    public BrokerStateObservationSpace getObservationSpace() {
    	return new BrokerStateObservationSpace(states_provider.getCurentObservationSpace());
    }
    */
    
    public DiscreteSpace getActionSpace() {
    	//System.out.println("Request action space");
        return actionsSpace; //wallet.getCurrentActionsSpace();
    }

    public StepReply<ModelStateObservation> step(Integer action) 
    {
    	//System.out.println("Step with action "+action);
    	PriceStateObservation next_price_state = states_provider.step(); //inutile de passer action en argument vu qu'action n'a en fait aucun effet sur state...
    	double reward = 0;
    	if (action==Constants.ACTION_BUY || action==Constants.ACTION_SELL)
    	{
    		reward = wallet.passNewOrder(next_price_state.getLowest_price(), next_price_state.getHighest_price(), action==Constants.ACTION_BUY);
    	}
    	done = states_provider.isDone();
    	return new StepReply<>(getCurrentObservation(next_price_state, wallet), reward, done, new JSONObject("{}"));
    }

    public boolean isDone() {
        return done;
    }
    
    public ModelStateObservation reset() {
        done = false;
        wallet.reset();
        return getCurrentObservation(states_provider.reset(), wallet);
    }   
    
    private ModelStateObservation getCurrentObservation(PriceStateObservation priceinfos, Wallet wallet){
    	return new ModelStateObservation(priceinfos, wallet);
    }
  
    public Wallet getWallet() {
    	return this.wallet;
    }
    
    public void close() {
    }

    public TrainingEnv newInstance() {
        return new TrainingEnv(provider_maker); 
    }

	
}