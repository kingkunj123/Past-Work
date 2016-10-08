import java.io.*;
import java.util.*;
public class ScoreLoader {
	Vector<String> lines = new Vector<String>(); 
	private Scanner s = null;
	int x = 0;
	 PrintWriter p;
	public void openFile(String FileName){
		File scoreFile = new File(FileName); 
		try {
		    s = new Scanner(scoreFile);
		}
		catch(Exception noFile) { 
		    System.out.println("No file found");
		}
//		while(s.hasNext()){
//		   lines.add(s.nextLine());
//		}
	}
	
	public boolean arrangeScore(int score){
		boolean newScore = false;
		for(int i=0;i<lines.size();i++){
			int num= Integer.parseInt(lines.elementAt(i).substring(6));
			if(score>num){
				newScore=true;
				x=i;
				break;
			}
		}
		return newScore;
	}
	
	public void getName(String name, int score){
		String pair=null;
		for(int i=0;i<5;i++){
			if(name==null)
				name="xxxxx";
			else if(name.length()>5)
				name=name.substring(0,5);
			else if(name.length()<5)
				name+="X";
		}
		pair=name+" " + (Integer.toString(score));
		lines.insertElementAt(pair, x);
		lines.removeElementAt(5);
		
		try{
			p = new PrintWriter("scoreFile");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		for(int j=0;j<lines.size();j++){
			p.println(lines.elementAt(j)); 
		}
		p.close(); 
	}
}
