import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class GameController {

    Field field;
    static ArrayList<Entity> entities = new ArrayList<Entity>();
    static final int CHARACTER_SIZE = 32;
    int numRock = 0;
    Random rand;
    CollisionDetection collisionDetection;
    boolean isOver = false;
    
    Class teamZeroAI;
    Class teamOneAI;
    
    public GameController()
    {
        loadAIClasses();
        collisionDetection = new CollisionDetection(entities);
        rand = new Random();
        field = new Field();
        for(int i = 0; i < numRock; i++)
        {
            entities.add(new Rock(GameController.CHARACTER_SIZE * 2 + rand.nextInt(800 - GameController.CHARACTER_SIZE * 4),rand.nextInt(480-GameController.CHARACTER_SIZE), collisionDetection));
        }

        createTeam(10,10,0);
        createTeam(10,10,1);

    }

    public void loadAIClasses()
    {
        try {
            teamZeroAI = Class.forName("TeamZeroAI");
            teamOneAI = Class.forName("TeamOneAI");
        } catch (ClassNotFoundException e) {
            System.out.println("Team AI .class files are missing");
            System.exit(0);
        }
        
    }
    
    public void createTeam(int numSwordsman, int numArchers, int teamNum)
    {
        int x = CHARACTER_SIZE / 2;
        
        int yIncArchers = CHARACTER_SIZE * 18 / numArchers;
        int yIncSwordsman = CHARACTER_SIZE * 18 / numSwordsman;
        int newY = CHARACTER_SIZE;
        int newXSwordsman = CHARACTER_SIZE * 5;
        int newXArcher = CHARACTER_SIZE * 3;
        
        Class teamAI = null;
        
        if(teamNum == 1)
        {
            teamAI = teamOneAI;
            x = (int)((double)CHARACTER_SIZE * 29.5);
            newXSwordsman = CHARACTER_SIZE * 25;
            newXArcher = CHARACTER_SIZE * 27;
            entities.add(new Castle(x - CHARACTER_SIZE / 2, GameController.CHARACTER_SIZE * 10, collisionDetection , teamNum));
        }
        else
        {
            teamAI = teamZeroAI;
            entities.add(new Castle(CHARACTER_SIZE, GameController.CHARACTER_SIZE * 10, collisionDetection , teamNum));
        }
        
        for(int i = 0; i < numSwordsman; i++)
        {
            entities.add(new Swordsman(newXSwordsman,newY, teamNum, collisionDetection, teamAI));
            newY += yIncSwordsman;
        }
        newY = CHARACTER_SIZE;
        
        for(int i = 0; i < numArchers; i++) {
            entities.add(new Archer(newXArcher,newY, teamNum, collisionDetection, teamAI));
            newY += yIncArchers;
        }

    }

    
    
	public void update()
	{
	    for(int i = 0; i < entities.size(); i++)
        {
	            entities.get(i).update();
	            
	            if(entities.get(i).isGone())
	            {
	                System.out.println("isgone");
	                String type = entities.get(i).getType(0);
	                if(type.equals("FC") || type.equals("EC"))
	                {
	              //      System.exit(0);
	                }
	                entities.remove(entities.get(i));
	                i--;
	            }
	            
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
