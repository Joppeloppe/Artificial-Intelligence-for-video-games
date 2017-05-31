package com.assignment2.game;

public class Result {
	private String spawnString;
	private float fitnessValue;
	
	public Result(String spawnString, float fitnessValue){
		setSpawnString(spawnString);
		setFitnessValue(fitnessValue);
	}

	@Override
	public String toString(){
		return getSpawnString() + ":" + getFitnessValue();
	}
	

/*
 * Getters and setters.
 */
	public String getSpawnString() {
		return spawnString;
	}

	private void setSpawnString(String spawnString) {
		this.spawnString = spawnString;
	}

	public float getFitnessValue() {
		return fitnessValue;
	}

	private void setFitnessValue(float fitnessValue) {
		this.fitnessValue = fitnessValue;
	}
}
