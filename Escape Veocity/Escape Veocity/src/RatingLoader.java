import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class RatingLoader {
	Vector<String> lines = new Vector<String>(); 
	private Scanner s = null;
	int x = 0;
	PrintWriter p;
	public void openFile(String FileName){
		File ratingFile = new File(FileName); 
		try {
		    s = new Scanner(ratingFile);
		}
		catch(Exception noFile) { 
		    System.out.println("No file found");
		}
		while(s.hasNext()){
			lines.add(s.nextLine());
		}  
	}
	
	public void addRating(String rating){
		int played = Integer.parseInt(lines.elementAt(0))+1;
		int currRate = Integer.parseInt(rating);
		int rate = Integer.parseInt(lines.elementAt(1));
		rate *= played-1;
		rate += currRate;
		rate /=played;
		String timesPlayed = Integer.toString(played);
		rating=Integer.toString(rate);
		try{
			p = new PrintWriter("rating");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		p.println(timesPlayed);
		p.println(rating);
		p.close(); 
	}
}
