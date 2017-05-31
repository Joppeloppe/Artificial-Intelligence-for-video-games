package com.assignment.object;

import com.assignment.game.AssetsManager;
import com.assignment.game.Main;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Spawner {
	
	private AssetsManager assetsManager;
	private boolean spawnEnemy, spawnTarget;
	
	public Spawner(AssetsManager assetsManager){
		this.assetsManager = assetsManager;
		
		setSpawnEnemy(true);
		setSpawnTarget(true);
	}
	
	public void updateDecision(){
		Vector2 newPosition = new Vector2();

		decisionSpawnEnemy(newPosition);
		decisionSpawnTarget(newPosition);
	}
	
	private void decisionSpawnEnemy(Vector2 newPosition){
		int spawnedEnemy = 0;
		
		if(isSpawnEnemy() && Main.getListEnemy().size() <= 4 && Main.getListEnemy().size() > 1){
			int newEnemy = Main.random.nextInt(2);
			
			newPosition = new Vector2(Main.random.nextInt(600) + 100, Main.random.nextInt(600) + 100);
			
			if(newEnemy == 0){	
				Main.addEnemy(new EnemyFSM(assetsManager.enemyTexture, newPosition, null));	
				spawnedEnemy++;
			}else{
				Main.addEnemy(new EnemyFuSM(assetsManager.enemy1Texture, newPosition, assetsManager.projectileTexture));
				spawnedEnemy++;
			}
			
		}else if(Main.getListEnemy().size() <= 1){	
			newPosition = new Vector2(Main.random.nextInt(500) + 100, Main.random.nextInt(500) + 100);			
			Main.addEnemy(new EnemyFSM(assetsManager.enemyTexture, newPosition, null));	
			
			newPosition = new Vector2(Main.random.nextInt(500) + 100, Main.random.nextInt(500) + 100);
			Main.addEnemy(new EnemyFuSM(assetsManager.enemy1Texture, newPosition, assetsManager.projectileTexture));
			
			spawnedEnemy++;
		}
		
		if(spawnedEnemy != 0){
			
			setSpawnEnemy(false);
			Timer.schedule(new Task(){
				@Override
				public void run() {
					setSpawnEnemy(true);
				}
				
			}, 1);
		}
		
		if(!isSpawnEnemy()){			
			int numbFSM = 0;
			int numbFuSM = 0;
			
			for(int i = 0; i < Main.getListEnemy().size(); i++){
				if(((GameObject) Main.getListEnemy().get(i)).getTag() == "FSM"){
					numbFSM++;
				}else if(((GameObject) Main.getListEnemy().get(i)).getTag() == "FuSM"){
					numbFuSM++;
				}
			}
			
			if(numbFSM <= 4){
				newPosition = new Vector2(Main.random.nextInt(500) + 100, Main.random.nextInt(500) + 100);
				Main.addEnemy(new EnemyFSM(assetsManager.enemyTexture, newPosition, null));				
			}
			if(numbFuSM <= 2){
				newPosition = new Vector2(Main.random.nextInt(500) + 100, Main.random.nextInt(500) + 100);
				Main.addEnemy(new EnemyFuSM(assetsManager.enemy1Texture, newPosition, assetsManager.projectileTexture));
			}
			
		}
	}
	
	private void decisionSpawnTarget(Vector2 newPosition){
		if(isSpawnTarget() && Main.getListPowerUp().size() <= 0){
			newPosition = new Vector2(Main.random.nextInt(500) + 100, Main.random.nextInt(500) + 100);
			Main.addTarget(new Target(assetsManager.powerUpTexture, newPosition));
			
			setSpawnTarget(false);
			Timer.schedule(new Task(){

				@Override
				public void run() {
					setSpawnTarget(true);
				}
				
			}, 10);
		}else if(isSpawnTarget() && Main.getListPowerUp().size() > 0 && Main.getListEnemy().size() >= 5){
			newPosition = new Vector2(Main.random.nextInt(500) + 100, Main.random.nextInt(500) + 100);
			Main.addTarget(new Target(assetsManager.powerUpTexture, newPosition));
			newPosition = new Vector2(Main.random.nextInt(500) + 100, Main.random.nextInt(500) + 100);
			Main.addTarget(new Target(assetsManager.powerUpTexture, newPosition));
			
			setSpawnTarget(false);
			Timer.schedule(new Task(){

				@Override
				public void run() {
					setSpawnTarget(true);
				}
				
			}, 10);
		}
	}

	public boolean isSpawnEnemy() {
		return spawnEnemy;
	}

	public void setSpawnEnemy(boolean spawnEnemy) {
		this.spawnEnemy = spawnEnemy;
	}

	public boolean isSpawnTarget() {
		return spawnTarget;
	}

	public void setSpawnTarget(boolean spawnPowerUp) {
		this.spawnTarget = spawnPowerUp;
	}
}
