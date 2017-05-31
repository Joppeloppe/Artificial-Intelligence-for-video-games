package com.assignment3.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.assignment3.boid.Boid;
import com.assignment3.gameObject.GameObjectMoving;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Main
extends ApplicationAdapter {
	
	public final static int WINDOW_HEIGHT = 720, WINDOW_WIDTH = 1080;
	public static Random random;

	private int select = 0;
	
	private static SpriteBatch batch;
	private static OrthographicCamera camera;
	private static AssetsManager assetsManager;
	private Boid ship;
	private Boid boid;

	private List<Boid> listBoid = new ArrayList<Boid>();
	
	@Override
	public void create () {
		random = new Random();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		assetsManager = new AssetsManager();
		
		assetsManager.loadAssets();
		
		camera.setToOrtho(false, WINDOW_WIDTH, WINDOW_HEIGHT);
		camera.update();
		
		ship = new Boid(assetsManager.shipTexture, new Vector2(0, 0));
		ship.setSpeed(2f);
		ship.setDirection(new Vector2(1, 1));
		
		boid = new Boid(assetsManager.boidsTexture, new Vector2(1080, 720));
		
		for(int i = 0; i < 15; i++){
			int x = random.nextInt(1080);
			int y = random.nextInt(720);
			
			listBoid.add(new Boid(assetsManager.boidsTexture, new Vector2(x, y)));
		}
	}
	
	public void update(){
		Vector3 touchPosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(touchPosition);
					
		if(Gdx.input.isKeyPressed(Keys.NUM_0))
			select = 0;
		else if(Gdx.input.isKeyPressed(Keys.NUM_1))
			select = 1;
		else if(Gdx.input.isKeyPressed(Keys.NUM_2))
			select = 2;
		else if(Gdx.input.isKeyPressed(Keys.NUM_3))
			select = 3;
		else if(Gdx.input.isKeyPressed(Keys.NUM_4))
			select = 4;
		
		switch(select){
		case 0:
//Flock with steering behaviour.
			for(int i = 0; i < listBoid.size(); i++){
				Boid agent = listBoid.get(i);
				
				agent.setTargetPosition(new Vector2(touchPosition.x, touchPosition.y));
				
				Vector2 alignment = agent.alignment(listBoid);
				Vector2 cohesion = agent.cohesion(listBoid);
				Vector2 separation = agent.separation(listBoid);
				
				agent.setDirection(new Vector2(agent.getDirection().x + alignment.x * 0.1f + cohesion.x * 0.05f + separation.x * 0.15f,
						agent.getDirection().y + alignment.y * 0.1f + cohesion.y * 0.05f + separation.y * 0.15f));
							
				agent.update();
			}
			break;
		case 1:
// Flock wander.
			for(int i = 0; i < listBoid.size(); i++){
				Boid agent = listBoid.get(i);
				
				agent.wander();
				
				agent.update();
			}
			break;
		case 2:
//Single ship pursuit.
			ship.setTargetPosition(new Vector2(touchPosition.x, touchPosition.y));
			ship.update();
			
			boid.setTargetPosition(new Vector2(ship.getPosition()));
			boid.pursuit(ship);
			boid.update();
//Single ship arrive.
			break;
		case 3:
			ship.setTargetPosition(new Vector2(touchPosition.x, touchPosition.y));
			ship.update();

			boid.setTargetPosition(new Vector2(ship.getPosition()));
			boid.arrive();
			
			boid.update();

			break;
//Single ship approach.
		case 4:
			ship.setTargetPosition(new Vector2(touchPosition.x, touchPosition.y));
			ship.update();
		
			boid.setTargetPosition(new Vector2(ship.getPosition()));
		
			boid.update();
			break;
		}

	}

	@Override
	public void render () {
		update();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
				
		switch(select){
		case 0:
			for(int i = 0; i < listBoid.size(); i++){
				listBoid.get(i).draw(batch);
			}
			
			assetsManager.font.draw(batch, "Steering behaviour", 20, 700);
			break;
		case 1:
			for(int i = 0; i < listBoid.size(); i++){
				listBoid.get(i).draw(batch);
			}
			
			assetsManager.font.draw(batch, "Wander", 20, 700);
			break;
		case 2:
			boid.draw(batch);
			ship.draw(batch);
			
			assetsManager.font.draw(batch, "Pursuit", 20, 700);

			break;
		case 3:
			boid.draw(batch);
			ship.draw(batch);
			
			assetsManager.font.draw(batch, "Arrive", 20, 700);

			break;
		case 4:
			boid.draw(batch);
			ship.draw(batch);
			
			assetsManager.font.draw(batch, "Approach", 20, 700);
		}
		
		batch.end();
	}
	
	@Override
	public void dispose(){
		assetsManager.dispose();
	}
}
