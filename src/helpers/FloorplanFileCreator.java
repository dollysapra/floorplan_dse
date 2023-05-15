package helpers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.Iterator;

import genetic.CoreGene;
import genetic.FloorplanChromosome;


public class FloorplanFileCreator 
{
	private static long idCounter = 1;
	
	public static String flpFolder = Configs.FlpPath +  new Date().getTime() + "/";
	
	public static String saveToFile(FloorplanChromosome flpChromosome)
	{
		
		String filename = "/dp" + getNextCounter() + ".flp";
		String fileLocation = flpFolder + filename;
		
		File file = new File(flpFolder);
		if (!file.exists()) 
		{
            System.out.print("No Folder:" + flpFolder);
            file.mkdir();
            file = new File(flpFolder + "results");
            file.mkdir();
            System.out.println("Folder created" + new Date().toString());
            try {
   			 	File f = new File(flpFolder+ "/console.txt");
   			 	f.createNewFile();
   			 	System.setOut(new PrintStream(new FileOutputStream(f)));
   			 	System.out.println(flpFolder +"::" +new Date().toString());
            }
            catch(Exception e)
            {
            	System.out.print("System out not set to console");
            }
        }
		
		try 
		{
		      FileWriter myWriter = new FileWriter(fileLocation);
		      myWriter.write("# Line Format: <unit-name>\\t<width>\\t<height>\\t<left-x>\\t<bottom-y>\n");
		     
		      Iterator<CoreGene> it = flpChromosome.iterator();
		      while (it.hasNext())
		      {
		    	  CoreGene cg = it.next();
		    	  myWriter.write(cg.toString());
		    	  myWriter.write("\n");
		      }
		      myWriter.close();
		      return filename;
		} 
		catch (IOException e) 
		{
		      System.out.println("File write for .flp file:  Error occurred.");
		      e.printStackTrace();
		      return null; 
		}
	}
	
	public static synchronized long getNextCounter() {
	     return idCounter++;
	 }

}
