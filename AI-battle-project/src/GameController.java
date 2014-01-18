import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class GameController {

    Field field;
    ArrayList<Entity> entities = new ArrayList<Entity>();
    static final int CHARACTER_SIZE = 32;
    int numRock = 20;
    Random rand;
    public GameController()
    {
        rand = new Random();
        field = new Field();
        for(int i = 0; i < numRock; i++)
        {
            entities.add(new Rock(GameController.CHARACTER_SIZE * 2 + rand.nextInt(800 - GameController.CHARACTER_SIZE * 4),rand.nextInt(480-GameController.CHARACTER_SIZE)));
        }
        createTeam(20,0,0);
        createTeam(20,0,1);
    }

    public void createTeam(int numSwordsman, int numArchers, int teamNum)
    {
        int x = CHARACTER_SIZE / 2;
        int y = CHARACTER_SIZE / 2;
        if(teamNum == 1)
        {
            x = (int)((double)CHARACTER_SIZE * 29.5);
            entities.add(new Castle(x - CHARACTER_SIZE / 2, GameController.CHARACTER_SIZE * 10));
        }
        else
        {
            entities.add(new Castle(CHARACTER_SIZE, GameController.CHARACTER_SIZE * 10));
        }
        
        for(int i = 0; i < numSwordsman; i++)
        {
            entities.add(new Swordsman(x,y, teamNum));
            y += GameController.CHARACTER_SIZE;
        }
        
              
    }

	public void update()
	{
	    for(Entity entity: entities)
        {
	            entity.update();
        }  
	}
	
	public void draw(SpriteBatch batch)
	{
	    field.draw(batch);
	    for(Entity entity: entities)
	    {
	        
	        entity.draw(batch);
	    }
	}
}