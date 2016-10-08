import java.util.*;
public class LevelLoader {
	private Vector<String> lines = new Vector<String>(); 
	private Scanner s = null;
	int x = 0;
	public void openLevelFile(String FileName){
		
		try {
		    s = new Scanner(ResourceLoader.load(FileName));
		}
		catch(Exception noFile) { 
		    System.out.println("No file found");
		}
		while(s.hasNext()){
		   lines.add(s.nextLine());
		}
	}

	public void createLevelGrid(Vector<Bomb> bombVec, Vector<Wall> wallVec){
		int space = 68;
		String lineOfObstacles=lines.elementAt(x);
		for(int y=0;y<10;y++){
			if (lineOfObstacles.charAt(y)=='O')
				space+=70;
			else if (lineOfObstacles.charAt(y)=='B'){
					bombVec.add(new Bomb(1023,space, 70,70));
					space+=70;
			}
			else if (lineOfObstacles.charAt(y)=='W' ){
					wallVec.add(new Wall(1023,space, 70, 30));
					space+=70;
			}
		}
		x++;
		if(x == 6)
			x=0;
		
	}
}
