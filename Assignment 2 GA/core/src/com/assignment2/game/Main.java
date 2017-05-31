package com.assignment2.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.assignment2.game.enemy.Enemy;
import com.assignment2.game.enemy.EnemyBrute;
import com.assignment2.game.enemy.EnemyFlying;
import com.assignment2.game.enemy.EnemySwarmling;
import com.assignment2.game.object.GameObject;
import com.assignment2.game.tower.Tower;
import com.assignment2.game.tower.TowerStrong;
import com.assignment2.game.tower.TowerWeak;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Main
extends ApplicationAdapter {
	public static int WINDOW_HEIGHT = 256,
			WINDOW_WIDHT = 512;
	public static Random random;
	
	private int healthPoint, enemySpawnCharCounter = 0, enemySpawnCounter = 0, lastWaypoint = 0, currentGeneration = 0;
	private float enemySpawnDelay, elapsedTime, generationTime;
	private long startTime;
	private static boolean spawnEnemy;
	
	private final Vector2 spawnPoint = new Vector2(32, -32);
	
	private List<String> listEnemySpawn = new ArrayList<String>();
	private List<Float> listFitnessValue = new ArrayList<Float>();
	private List<Result> listResult = new ArrayList<Result>();
	
	private List<GameObject> listLand = new ArrayList<GameObject>();
	private List<GameObject> listRoad = new ArrayList<GameObject>();
	private List<Tower> listTower = new ArrayList<Tower>();
	private List<Enemy> listEnemy = new ArrayList<Enemy>();
	
	private GameObject castle;
	
	private static SpriteBatch batch;
	private static AssetsManager assetsManager; 
	private static OrthographicCamera camera;
	
	@Override
	public void create () {
		random = new Random();
		batch = new SpriteBatch();
		
		assetsManager = new AssetsManager();
		assetsManager.loadAssets();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WINDOW_WIDHT, WINDOW_HEIGHT);
		camera.update();
		
		loadMap();

// Castle health points.
		setHealthPoint(5);
// Spawn delay in seconds.
		setEnemySpawnDelay(1.0f);
		setStartTime(TimeUtils.millis());
		setGenerationTime(0);
		setSpawnEnemy(true);
		
		readFromFile();
				
		System.out.println("\nStarting round " + enemySpawnCounter + "!\n" +
		"Spawn string is " + listEnemySpawn.get(enemySpawnCounter));
	}

	@Override
	public void render () {
		update();

// Black background.
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();

		for(int i = 0; i < listLand.size(); i++){
			listLand.get(i).draw(batch);
		}
		for(int i = 0; i < listRoad.size(); i++){
			listRoad.get(i).draw(batch);
		}
		for(int i = 0; i < listTower.size(); i ++){
			listTower.get(i).draw(batch);
		}
		for(int i = 0; i < listEnemy.size(); i++){
			listEnemy.get(i).draw(batch);
		}
		
		castle.draw(batch);
		
/*
 * Writes the number of health points left, and the play time of the round.
 */
		assetsManager.font.draw(batch,"Generation " + currentGeneration +
				"\n" + "Round " + enemySpawnCounter +
				"\n\n" + "Health: " + getHealthPoint() + "\n" + 
				"Elapsed time: " + (getElapsedTime() / 1000), 300, 245);
		
		batch.end();
	}
	
	private void update(){
		if(isSpawnEnemy())
			spawnEnemy();
/*
 * Update the elapsed game time in milliseconds,
 * this stops when the player looses i.e. health reaches 0(zero),
 * or when the player wins i.e. there are no more enemies on the level.
 */
		if(getHealthPoint() > 0 && listEnemy.size() > 0){
			setElapsedTime(TimeUtils.timeSinceMillis(getStartTime()));
		}else{
			fitnessFunction();
			
			String result = getHealthPoint() + " : " + getElapsedTime() / 1000 + " : " + lastWaypoint +  " = " + listResult.get(enemySpawnCounter).getFitnessValue();
									
			System.out.println("\nResults are :: Health : Time in seconds : Waypoints reached = Fitness value.");
			System.out.println("Result round " + enemySpawnCounter + " :: " + result + "\n");
			
			enemySpawnCounter++;
/*
 * Starts a new round, else
 * if the generation is done, fitness is evaluated, stored,
 * and a new population is bred, and a new generation starts.
 */			
			if(enemySpawnCounter < listEnemySpawn.size()){
				System.out.println("\nStarting round " + enemySpawnCounter + "!\n");
				
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					System.out.println("Failed to sleep at start of new round.");
				}
				
				System.out.println("Spawn string: " + listEnemySpawn.get(enemySpawnCounter));
				
				setHealthPoint(5);
				setStartTime(TimeUtils.millis());
				setGenerationTime(getGenerationTime() + getElapsedTime());
				
				listEnemy.clear();
				enemySpawnCharCounter = 0;
				setSpawnEnemy(true);	
				spawnEnemy();
			}else{
				try {
					wait();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
/*
 * Evaluates the generation, writes the results and breeds a new generation.
 */
				sortByFitnessValue();
				writeResults();
				fixPopulation();
				writeNewEnemySpawn();
/*
 * Starts a new generation.
 */			
				enemySpawnCharCounter = 0;
				enemySpawnCounter = 0;
						
				if(enemySpawnCounter >= 1000){
					try {
						wait();
					} catch (InterruptedException e) {
						System.out.println("Failed to wait.");
					}
				}else{
					
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						System.out.println("Failed to sleep at start of new round.");
					}
				}
								
				setHealthPoint(5);
				setStartTime(TimeUtils.millis());
				
				listEnemy.clear();
				listEnemySpawn.clear();
				listFitnessValue.clear();;
				listResult.clear();;
				
				setGenerationTime(0);
				setSpawnEnemy(true);
				
				readFromFile();
				
				System.out.println("\nStarting round " + enemySpawnCounter + "!\n" +
						"Spawn string is " + listEnemySpawn.get(enemySpawnCounter));
			}
		}
		
/*
 * Tower update
 */
		for(int i = 0; i < listTower.size(); i++){
			if(listEnemy.size() == 0)
				break;
			
			int closestTarget = -1;
			float closestDistance = 0;

			for(int j = 0; j < listEnemy.size(); j++){
//Strong towers cannot target flying enemies.
				if(listTower.get(i).getTag() == "TowerStrong" && listEnemy.get(j).getTag() == "EnemyFlying")
					break;
				
				float distance = listTower.get(i).calculateDistanceToTarget(listEnemy.get(j));
				
				if(distance <= listTower.get(i).getRange()){
					if(closestTarget != -1){
						if(distance < closestDistance){
							closestDistance = distance;
							closestTarget = j;
						}						
					}
					else{
						closestDistance = distance;
						closestTarget = 0;
					}
				}
			}
			
			if(closestTarget != -1 && !listTower.get(i).isHasShot())
				listTower.get(i).shoot(listEnemy.get(closestTarget));
		}
		
/*
 * Enemy update
 */
		for(int i = 0; i < listEnemy.size(); i++ ){
			if(listEnemy.get(i).isDead()){
				if(listEnemy.get(i).getCurrentWaypoint() > lastWaypoint){
					lastWaypoint = listEnemy.get(i).getCurrentWaypoint();
				}
				
				listEnemy.remove(i);
				break;
			}
			
			listEnemy.get(i).update();
			
			if(listEnemy.get(i).collision(castle)){
				setHealthPoint(getHealthPoint() - 1);
				listEnemy.get(i).setDead(true);
			}
		}
	}
	
/*
 * 	Loads the map elements into their correct lists.
 */
	private void loadMap(){	
		loadLand();
		loadRoad();
		loadTowers();
		
		castle = new GameObject(assetsManager.castle00, new Vector2(256, 224));
	}

	private void loadLand(){
		listLand.add(new GameObject(assetsManager.grass, new Vector2(0, 0)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(0, 32)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(0, 64)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(0, 96)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(0, 128)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(0, 160)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(0, 192)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(0, 224)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(0, 256)));

		
		listLand.add(new GameObject(assetsManager.grass, new Vector2(32, 0)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(32, 32)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(32, 64)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(32, 96)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(32, 128)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(32, 160)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(32, 192)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(32, 224)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(32, 256)));

		
		listLand.add(new GameObject(assetsManager.grass, new Vector2(64, 0)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(64, 32)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(64, 64)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(64, 96)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(64, 128)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(64, 160)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(64, 192)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(64, 224)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(64, 256)));

		listLand.add(new GameObject(assetsManager.grass, new Vector2(96, 0)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(96, 32)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(96, 64)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(96, 96)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(96, 128)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(96, 160)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(96, 192)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(96, 224)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(96, 256)));
		
		listLand.add(new GameObject(assetsManager.grass, new Vector2(128, 0)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(128, 32)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(128, 64)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(128, 96)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(128, 128)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(128, 160)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(128, 192)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(128, 224)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(128, 256)));

		listLand.add(new GameObject(assetsManager.grass, new Vector2(160, 0)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(160, 32)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(160, 64)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(160, 96)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(160, 128)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(160, 160)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(160, 192)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(160, 224)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(160, 256)));
		
		listLand.add(new GameObject(assetsManager.grass, new Vector2(192, 0)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(192, 32)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(192, 64)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(192, 96)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(192, 128)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(192, 160)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(192, 192)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(192, 224)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(192, 256)));

		listLand.add(new GameObject(assetsManager.grass, new Vector2(224, 0)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(224, 32)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(224, 64)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(224, 96)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(224, 128)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(224, 160)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(224, 192)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(224, 224)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(224, 256)));

		listLand.add(new GameObject(assetsManager.grass, new Vector2(256, 0)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(256, 32)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(256, 64)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(256, 96)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(256, 128)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(256, 160)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(256, 192)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(256, 224)));
		listLand.add(new GameObject(assetsManager.grass, new Vector2(256, 256)));
	}
	
	private void loadRoad(){
		listRoad.add(new GameObject(assetsManager.road, new Vector2(32, 0)));
		listRoad.add(new GameObject(assetsManager.road, new Vector2(32, 32)));
		listRoad.add(new GameObject(assetsManager.road, new Vector2(32, 64)));
		listRoad.add(new GameObject(assetsManager.road, new Vector2(32, 96)));
		listRoad.add(new GameObject(assetsManager.road, new Vector2(64, 96)));
		listRoad.add(new GameObject(assetsManager.road, new Vector2(96, 96)));
		listRoad.add(new GameObject(assetsManager.road, new Vector2(128, 96)));
		listRoad.add(new GameObject(assetsManager.road, new Vector2(160, 96)));
		listRoad.add(new GameObject(assetsManager.road, new Vector2(160, 64)));
		listRoad.add(new GameObject(assetsManager.road, new Vector2(160, 32)));
		listRoad.add(new GameObject(assetsManager.road, new Vector2(192, 32)));
		listRoad.add(new GameObject(assetsManager.road, new Vector2(224, 32)));
		listRoad.add(new GameObject(assetsManager.road, new Vector2(224, 64)));
		listRoad.add(new GameObject(assetsManager.road, new Vector2(224, 96)));
		listRoad.add(new GameObject(assetsManager.road, new Vector2(224, 128)));
		listRoad.add(new GameObject(assetsManager.road, new Vector2(224, 160)));
		listRoad.add(new GameObject(assetsManager.road, new Vector2(224, 192)));
		listRoad.add(new GameObject(assetsManager.road, new Vector2(224, 224)));
		listRoad.add(new GameObject(assetsManager.road, new Vector2(256, 224)));
	}
	
	private void loadTowers(){
		listTower.add(new TowerWeak(assetsManager.towerWeak, new Vector2(0, 64)));
		listTower.add(new TowerWeak(assetsManager.towerWeak, new Vector2(64, 64)));
		listTower.add(new TowerWeak(assetsManager.towerWeak, new Vector2(128, 64)));
		listTower.add(new TowerStrong(assetsManager.towerStrong, new Vector2(64, 128)));
		listTower.add(new TowerStrong(assetsManager.towerStrong, new Vector2(192, 64)));
		listTower.add(new TowerWeak(assetsManager.towerWeak, new Vector2(192, 0)));
		listTower.add(new TowerWeak(assetsManager.towerWeak, new Vector2(256, 160)));
		listTower.add(new TowerStrong(assetsManager.towerStrong, new Vector2(192, 160)));
	}

/*
 * Reads the enemy spawn string and spawns the correct enemy type.
 */
	private void spawnEnemy(){
		for(; enemySpawnCharCounter < listEnemySpawn.get(enemySpawnCounter).length();){
			if(listEnemySpawn.get(enemySpawnCounter).charAt(enemySpawnCharCounter) == 'S'){
				//Spawns Enemy of type "Swarmling".
				Enemy newSwarmling = new EnemySwarmling(assetsManager.enemySwarmling, spawnPoint);
				newSwarmling.setWaypoints(listRoad);
				
				listEnemy.add(newSwarmling);
			}else if(listEnemySpawn.get(enemySpawnCounter).charAt(enemySpawnCharCounter) == 'B'){
				//Spawns Enemy of type "Brute".
				Enemy newBrute = new EnemyBrute(assetsManager.enemyBrute, spawnPoint);
				newBrute.setWaypoints(listRoad);
				
				listEnemy.add(newBrute);
			}else if(listEnemySpawn.get(enemySpawnCounter).charAt(enemySpawnCharCounter) == 'F'){
				//Spawns Enemy of type "Flying".
				Enemy newFlying = new EnemyFlying(assetsManager.enemyFlying, spawnPoint);
				newFlying.setWaypoints(listRoad);
				
				listEnemy.add(newFlying);
			}				
			
			setSpawnEnemy(false);
			enemySpawnCharCounter++;
			
			Timer.schedule(new Task(){
				@Override
				public void run(){
					setSpawnEnemy(true);
				}
			}, getEnemySpawnDelay());
			break;
		}
	}
/*
 * Read the enemy spawn strings from file and load into a list.
 */
	private void readFromFile(){
		System.out.println("Starting generation " + currentGeneration + ".\n");

		try {
			FileReader fileReader = new FileReader("d:/Skola/Spelutveckling/ai/Assignment 2 GA/core/assets/enemySpawn.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line = null;
			
			while((line = bufferedReader.readLine()) != null){
				listEnemySpawn.add(line);
				System.out.println(line);
			}

			bufferedReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Failed to find file.");
		} catch (IOException e) {
			System.out.println("Failed to read line.");
		}		
	}
	
/*
 * Write results to file.
 * String written is: Enemy spawn string of the round : the fitness value of that round.
 */
	private void writeResults(){
		try {
			FileWriter fileWriter0 = new FileWriter("d:/Skola/Spelutveckling/ai/Assignment 2 GA/core/assets/results.txt", true);
			BufferedWriter bufferedWriter0 = new BufferedWriter(fileWriter0);
			
			String toWrite = "\nResults for generation " + currentGeneration;
			System.out.println(toWrite);
			
			bufferedWriter0.append(toWrite);
			bufferedWriter0.newLine();
			
			toWrite = "Time: " + generationTime / 1000;
			System.out.println(toWrite);
			
			bufferedWriter0.append(toWrite);
			bufferedWriter0.newLine();
			
			for(int i = 0; i < listResult.size(); i++){
				toWrite = listResult.get(i).toString();
				
				bufferedWriter0.write(toWrite);
				bufferedWriter0.newLine();
				
				System.out.println("Wrote: " + toWrite);
			}
			
			bufferedWriter0.newLine();
			System.out.println("");
			
			bufferedWriter0.close();
		} catch (IOException e) {
			System.out.println("Failed to find file.");
		}		
	}
/*
 * Writes the new generation of strings.
 */
	private void writeNewEnemySpawn(){
		try {	
			FileWriter fileWriter1 = new FileWriter("d:/Skola/Spelutveckling/ai/Assignment 2 GA/core/assets/enemySpawn.txt");
			BufferedWriter bufferedWriter1 = new BufferedWriter(fileWriter1);		
			
			System.out.println("Generation " + ++currentGeneration);
			
			for(int i = 0; i < listResult.size(); i++){	
				String toWrite = listResult.get(i).getSpawnString();
				
				bufferedWriter1.write(toWrite);
				bufferedWriter1.newLine();
				
				System.out.println("Wrote: " + toWrite);
			}
			
			System.out.println("");
			
			bufferedWriter1.close();
		} catch (IOException e) {
			System.out.println("Failed to find file.");
		}
	}

/*
 * Calculates the fitness value of the round.
 * Healh of castle - time(in seconds) / number of waypoints reached.
 */
	private void fitnessFunction(){
		float fitness = getHealthPoint() - ((getElapsedTime() / 1000) / lastWaypoint);
		
		listFitnessValue.add(fitness);
		listResult.add(new Result(listEnemySpawn.get(enemySpawnCounter), fitness));
	}
/*
 * Removes bad individuals and breeds new ones.
 */
	private void fixPopulation(){
		sortByFitnessValue();
		
		int removed = 0;
		
		for(int i = 0; i < listResult.size(); i++){
			if(removed > 5)
				break;
			
			if(listResult.get(i).getFitnessValue() <= 0f){
				listResult.remove(i);
				removed++;
				break;
			}
			else if(listResult.get(i).getFitnessValue() >= 2f){
				listResult.remove(i);
				removed++;
				break;
			}
		}
		
		while(removed < 5){
			for(int i = 0; i < listResult.size(); i++){
				listResult.remove(i);
				removed++;
			}
		}
		
		int i = 0;
		
		while(listResult.size() < 10){
			breedPopulation(listResult.get(i++), listResult.get(i++));
		}
	}
	
/*
 * Breeds a new population.
 * Uses a uniform crossover, 2-3-2-3
 */
	private void breedPopulation(Result result0, Result result1){
		String string0 = result0.getSpawnString();
		String string1 = result1.getSpawnString();
		int randInt;
		
		String newSpawn0 = string0.substring(0, 2) + 
				string1.substring(2, 5) + 
				string0.substring(5, 7) + 
				string1.substring(7, 10);		
/*
 * Checks for mutation in first string.
 */
		for(int i = 0; i < newSpawn0.length(); i++){
			randInt = random.nextInt(10);
			
			if(randInt == 0){
//Mutates a char.
				char mutatedChar = mutate(newSpawn0.charAt(i));
				int nextI = i + 1;
				
				System.out.print("Mutated: " + newSpawn0 + " to ");
				
				newSpawn0 = newSpawn0.substring(0, i) + 
						mutatedChar +
						newSpawn0.substring(nextI, 10);
				
				System.out.println(newSpawn0);
			}
		}
		
		
		String newSpawn1 = string1.substring(0, 2) + 
				string0.substring(2, 5) + 
				string1.substring(5, 7) + 
				string0.substring(7, 10);
/*
 * Checks for mutation in second string.
 */		
		for(int i = 0; i < newSpawn1.length(); i++){
			randInt = random.nextInt(10);
			
			if(randInt == 0){
				char mutatedChar = mutate(newSpawn0.charAt(i));
				int nextI = i + 1;
				
				newSpawn1 = newSpawn1.substring(0, i) + 
						mutatedChar +
						newSpawn0.substring(nextI, 10);
				
				System.out.println(newSpawn0);
			}
		}
		
		listResult.add(new Result(newSpawn0, 0));
		listResult.add(new Result(newSpawn1, 0));
	}

/*
 * Sorts the results in ascending fitness value order.
 */
	private void sortByFitnessValue(){
		Collections.sort(listFitnessValue);
		
		for(int i = 0; i < listResult.size(); i++){
			for(int j = 0; j < listFitnessValue.size(); j++){
				if(listResult.get(i).getFitnessValue() == listFitnessValue.get(j)){
					listResult.set(j, listResult.get(i));
				}
			}
		}
	}

/*
 * Mutates a char to whatever it is not.
 * @param oldChar, the old char that is being mutated.
 * @return new, mutated char.
 */
	private char mutate(char oldChar){
		int randInt = random.nextInt(2);
		
		switch(oldChar){
			case 'S':
				switch(randInt){
				case 0:
					return 'F';
				case 1:
					return 'B';
			}
			case 'B':
				switch(randInt){
				case 0:
					return 'S';
				case 1:
					return 'F';
			}
			case 'F':
				switch(randInt){
				case 0:
					return 'S';
				case 1:
					return 'B';
				}
		}
		
		return ' ';
	}
	
	public void dispose(){
		assetsManager.dispose();
	}
	
/*
 * Getters and setters.
 */
	public static OrthographicCamera getCamera(){
		return camera;
	}

	public int getHealthPoint() {
		return healthPoint;
	}

	public void setHealthPoint(int healthPoint) {
		this.healthPoint = healthPoint;
	}

	public float getEnemySpawnDelay() {
		return enemySpawnDelay;
	}

	public void setEnemySpawnDelay(float enemySpawnDelay) {
		this.enemySpawnDelay = enemySpawnDelay;
	}

	public float getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(float elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public static boolean isSpawnEnemy() {
		return spawnEnemy;
	}

	public static void setSpawnEnemy(boolean spawnEnemy) {
		Main.spawnEnemy = spawnEnemy;
	}

	public float getGenerationTime() {
		return generationTime;
	}

	public void setGenerationTime(float generationTime) {
		this.generationTime = generationTime;
	}
}
