package com.assignment.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.assignment.object.Enemy;
import com.assignment.object.EnemyFSM;
import com.assignment.object.EnemyFuSM;
import com.assignment.object.Player;
import com.assignment.object.Projectile;
import com.assignment.object.Spawner;
import com.assignment.object.Target;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Main
extends ApplicationAdapter {
	public static final int WINDOW_HEIGHT = 720, WINDOW_WIDTH = 1080;
	public static Random random;
	private static OrthographicCamera camera;
	
	private SpriteBatch batch;
	
	private AssetsManager assetsManager;
	private static Player player;
	private Spawner spawner;
	
	private static List<Projectile> listProjectile = new ArrayList<Projectile>();
	private static List<Enemy> listEnemy = new ArrayList<Enemy>();
	private static List<Target> listTarget = new ArrayList<Target>();
	
	@Override
	public void create () {
		random = new Random();
		
		batch = new SpriteBatch();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WINDOW_WIDTH, WINDOW_HEIGHT);
		camera.update();
		
		
		assetsManager = new AssetsManager();
		assetsManager.loadContent();
		
		player = new Player(assetsManager.playerTexture,
				new Vector2(100, 100),
				assetsManager.projectileTexture);
				
		spawner = new Spawner(assetsManager);
						
		addEnemy(new EnemyFSM(assetsManager.enemyTexture,
				new Vector2(500, 500),
				assetsManager.projectileTexture));
		
		addEnemy(new EnemyFuSM(assetsManager.enemy1Texture,
				new Vector2(400, 300),
				assetsManager.projectileTexture));
		
		addTarget(new Target(assetsManager.powerUpTexture,
				new Vector2 (200, 200)));
	}

	@Override
	public void render () {
		update();
		
		Gdx.gl.glClearColor(0, 0.5f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		batch.begin();
		
		player.draw(batch);
		
		for(int i = 0; i < listProjectile.size(); i++){
			listProjectile.get(i).draw(batch);
		}
		
		for(int i = 0; i < listEnemy.size(); i++){
			listEnemy.get(i).draw(batch);
		}
		
		for(int i = 0; i < listTarget.size(); i++){
			listTarget.get(i).draw(batch);
		}
				
		batch.end();
	}
	
	private void update() {
		spawner.updateDecision();
		player.update();
		
		for(int i = 0; i < listEnemy.size(); i++){
			listEnemy.get(i).update();
			
			listEnemy.get(i).setTargetPosition(new Vector2(player.getOrigin()));

			if(listTarget.size() == 0){
				listEnemy.get(i).setDefendPosition(null);
			}else{
				int closestTarget = 0;
				float closestDistance = listTarget.get(0).getPosition().dst(listEnemy.get(i).getPosition());
				
				for(int j = 1; j < listTarget.size(); j++){
					float newDistance = listTarget.get(j).getPosition().dst(listEnemy.get(i).getPosition());
					
					if(newDistance < closestDistance){
						closestDistance = newDistance;
						closestTarget = j;
					}
				}
				
				listEnemy.get(i).setDefendPosition(new Vector2(listTarget.get(closestTarget).getPosition()));				
			}
			
			if(listEnemy.get(i).collides(player))
				listEnemy.get(i).setDead(true);
						
			for(int j = 0; j < listProjectile.size(); j++){							
				if(listProjectile.get(j).collides(listEnemy.get(i)) &&
						listProjectile.get(j).getTag() == "ProjectilePlayer" ){
					listProjectile.get(j).setDead(true);
					listEnemy.get(i).setDead(true);
				}
			}
			
			if(listEnemy.get(i).isDead()){
				listEnemy.remove(i);
				break;
			}
		}
		
		for(int i = 0; i < listProjectile.size(); i++){
			listProjectile.get(i).update();
			
			if(listProjectile.get(i).isDead()){
				listProjectile.remove(i);
				break;
			}
		}
		
		for(int i = 0; i < listTarget.size(); i++){
			listTarget.get(i).update();
			
			if(listTarget.get(i).collides(player)){
				listTarget.get(i).setDead(true);
			}
			
			if(listTarget.get(i).isDead()){
				listTarget.remove(i);
				break;
			}
		}	
	}

	public static void addProjectile(Projectile newProjectile){
		listProjectile.add(newProjectile);
	}
	
	public static void addEnemy(Enemy newEnemy){
		newEnemy.setTargetPosition(player.getPosition());
		
		listEnemy.add(newEnemy);
	}
	
	public static void addTarget(Target newPowerUp){
		listTarget.add(newPowerUp);
	}
	
	public static OrthographicCamera getCamera(){
		return camera;
	}
	
	public static List getListEnemy(){
		return listEnemy;
	}

	public static List getListPowerUp(){
		return listTarget;
	}
}
