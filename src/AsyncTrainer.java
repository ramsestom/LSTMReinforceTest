

import java.io.IOException;
import org.deeplearning4j.rl4j.learning.async.a3c.discrete.A3CDiscrete;
import org.deeplearning4j.rl4j.learning.async.a3c.discrete.A3CDiscreteDense;
import org.deeplearning4j.rl4j.network.ac.ActorCriticFactorySeparateStdDense;
import org.deeplearning4j.rl4j.policy.ACPolicy;
import org.deeplearning4j.rl4j.util.DataManager;

import commons.FakeHistoryInfoProviderMaker;
import model.ModelStateObservation;
import model.TrainingEnv;


public class AsyncTrainer {

	
	private static A3CDiscrete.A3CConfiguration MODEL_A3C =
            new A3CDiscrete.A3CConfiguration(
                    123,   		//seed: Random seed
                    60*24*30,   //maxEpochStep: Max step By epoch (Epoch d'un mois)
                    15000000, 	//maxStep: Max step global -> nombre de steps dans notre fichier d'history. TODO: ne pas le passer en conf mais le calculer depuis fichier de brokerhistory
                    2,          //Number of threads: nombre d'acteurs entrainés en parallèle
                    10,   		//t_max: tous les combien de step threads updatent les paramètres du reseau global?
                    0,     		//updateStart: num step noop warmup. Lié a epsilon: semble etre le nombre de steps pendant lesquels epsilon sera égal à 1 -> nombre de step ou toujours action random (ou noop?) choisie plutôt que celle donnée par le neural network
                    1.0,  		//rewardFactor: reward scaling
                    0.99,  		//gamma: gamma: reward discount factor: facteur par lequel sera multiplié future reward a chaque step -> permet de donner poids un peu plus important a reward immediates que futures
                    1.0//10.0,  //errorClamp: td-error clipping
              );


    private static final ActorCriticFactorySeparateStdDense.Configuration MODEL_NET = ActorCriticFactorySeparateStdDense.Configuration.builder().l2(0.01).learningRate(1e-2).numLayer(3).numHiddenNodes(50).useLSTM(true).build();

    
    public static void AsyncTrainer() throws IOException {

        //record the training data in rl4j-data in a new folder
        DataManager manager = new DataManager(true);

        //HistoryInfoProviderMaker infosprovider = new HistoryInfoProviderMaker("I:\\cryptotrading\\bitstampUSD_1-min_data_2012-01-01_to_2017-10-20.csv");
        FakeHistoryInfoProviderMaker infosprovider = new FakeHistoryInfoProviderMaker();
        
        //define the mdp
        TrainingEnv mdp = new TrainingEnv(infosprovider);
        
        //define the training
        A3CDiscrete<ModelStateObservation> a3c = new A3CDiscreteDense<ModelStateObservation>(mdp, MODEL_NET, MODEL_A3C, manager); //= new A3CDiscrete<ModelStateObservation>(mdp, new ActorCriticFactorySeparateStdDense(MODEL_NET).buildActorCritic(mdp.getObservationSpace().getShape(), mdp.getActionSpace().getSize()), MODEL_A3C,  manager);
        
        //start the training
        a3c.train();

        ACPolicy<ModelStateObservation> pol = a3c.getPolicy();

        pol.save("A3C_value", "A3C_policy");

        //close the mdp (http connection)
        mdp.close();

        //reload the policy, will be equal to "pol", but without the randomness
        //ACPolicy<Box> pol2 = ACPolicy.load("A3C_value", "A3C_policy");
    }
	    

    public static void main(String[] args) throws IOException {
    	AsyncTrainer();
    }

   

}
