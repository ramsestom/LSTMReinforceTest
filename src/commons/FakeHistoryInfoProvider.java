package commons;

import model.PriceStateObservation;

public class FakeHistoryInfoProvider {

	int nb_steps_max = 60*24*30;
	
	double base_price;
	double amplitude;
	double frequency;
	double phase_angle_offset = 0;
		
	int cur_step_index;
	double cur_time_space_val; //valeur du déplacement le long de l'axe des X
	long cur_time;
		
	PriceStateObservation current_infos;
	
	public FakeHistoryInfoProvider(double base_price) 
	{
		this.base_price = base_price;
		//attribue une amplitude qui soit entre 10 et 90% de ce prix
		this.amplitude = (0.1+Math.random()*0.8)*base_price;
		//attribue une frequence
		this.frequency = 1;//60*24+(Math.random()*5*60*24);
		//attribue un offset angle
		this.phase_angle_offset = 0;
				
		reset();
	}
	
	public PriceStateObservation getCurrentInfos() {
		return current_infos;
	}

	public PriceStateObservation step() {
		this.cur_step_index++;
		this.cur_time+=60000; //1 minute
		updateCurrentInfos();
		return getCurrentInfos();
	}

	public PriceStateObservation reset() {
		this.cur_time_space_val = 0;
		this.cur_step_index = 0;
		this.cur_time = 0;
		updateCurrentInfos();
		return getCurrentInfos();
	}

	public boolean isDone() {
		return (this.cur_step_index>nb_steps_max);
	} 
	
	private double getPriceAt(double time_space_val) {
		return (this.base_price + this.amplitude*Math.sin(2*Math.PI*this.frequency*time_space_val + this.phase_angle_offset)); 
	}
	
	private void updateCurrentInfos() {
		double open_price = getPriceAt(this.cur_time_space_val);
		this.cur_time_space_val+=0.01; //0.01*(0.5+Math.random());
		double close_price = getPriceAt(this.cur_time_space_val);
		PriceStateObservation new_infos = new PriceStateObservation();
		new_infos.setTimestamp(this.cur_time);
		new_infos.setOpen_price(open_price);
		new_infos.setClose_price(close_price);
		new_infos.setLowest_price(Math.min(open_price, close_price));
		new_infos.setHighest_price(Math.max(open_price, close_price));
		new_infos.setVolume(1);
		current_infos = new_infos;
	}

}
