package model;

import org.deeplearning4j.rl4j.space.Encodable;

public class PriceStateObservation {

	private long timestamp;
	private double mean_price;
	private double open_price;
	private double close_price;
	private double lowest_price;
	private double highest_price;
	private double volume;

	
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public double getMean_price() {
		return mean_price;
	}

	public void setMean_price(double mean_price) {
		this.mean_price = mean_price;
	}
	
	public double getOpen_price() {
		return open_price;
	}

	public void setOpen_price(double open_price) {
		this.open_price = open_price;
	}

	public double getClose_price() {
		return close_price;
	}

	public void setClose_price(double close_price) {
		this.close_price = close_price;
	}

	public double getLowest_price() {
		return lowest_price;
	}

	public void setLowest_price(double lowest_price) {
		this.lowest_price = lowest_price;
	}

	public double getHighest_price() {
		return highest_price;
	}

	public void setHighest_price(double highest_price) {
		this.highest_price = highest_price;
	}
	
	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

}
