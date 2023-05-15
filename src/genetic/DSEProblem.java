package genetic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.Function;

import helpers.Configs;
import helpers.FloorplanFileCreator;
import io.jenetics.engine.Codec;
import io.jenetics.engine.Problem;
import io.jenetics.ext.moea.Vec;
import io.jenetics.util.RandomRegistry;

public class DSEProblem implements Problem<GenotypeTranslation, CoreGene, Vec<double[]>> 
{
	

	private static final int OBJECTIVES = 2;
	@Override
	public Codec<GenotypeTranslation, CoreGene> codec() {
		return new FloorplanCodec();
	}

	@Override
	public Function<GenotypeTranslation, Vec<double[]>> fitness() {
		
		return this::fitnessEvaluations;
	}

	private Vec<double[]> fitnessEvaluations(GenotypeTranslation gtTrans)
	{
	
		final double[] fitnessValues = new double[OBJECTIVES];
	
		
		
		//TODO: DO SIMULATION
		simulateMTTF(gtTrans);
		
		fitnessValues[0] = Configs.MaxMTTF-gtTrans.getMTTF();
		fitnessValues[1] = gtTrans.getPower();
		
		System.out.println(gtTrans.toEvaluatedString());
		return Vec.of(fitnessValues);
		
		
	}
	
	
	private void simulateMTTF(GenotypeTranslation gtTrans)
	{
		
		
		
        
		double MTTF = RandomRegistry.getRandom().nextDouble(10.0, 25.0);
		double power = RandomRegistry.getRandom().nextDouble(1.0, 2.0);
	
		
		
		
		
        
        String[] cmd = new String[5];
        cmd[0] = Configs.Python3Path;
        cmd[1] = Configs.simulatorPath;
        cmd[2] = FloorplanFileCreator.flpFolder;
        cmd[3] = gtTrans.getName();
        cmd[4] = String.valueOf(Configs.simulationBudget);
        
        try
        {
        	 Runtime rt = Runtime.getRuntime();
             Process pr = rt.exec(cmd);

             int exitVal = pr.waitFor();
             
             //System.out.print("\nExit:" +  exitVal + "for" + gtTrans.getName());
             
             if(exitVal != 0)
             {
	             BufferedReader bfr1 = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
	             String line = "";
	             String pythonScriptError= "";
	             while((line = bfr1.readLine()) != null) {
	            	 pythonScriptError+=(line+"/n");
	             }
	             System.out.println("Error - " +pythonScriptError);
	             MTTF =  0.0;
	         	 power = 10.0;
	
             }
             
             /** retrieve output from python script */
             BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
             String line = "";
             
             while((line = bfr.readLine()) != null) {
            	 if(line.startsWith("MTTF"))
            		 MTTF =  Double.valueOf(line.split(":")[1]);
            	 else if(line.startsWith("Power"))
            		 power = Double.valueOf(line.split(":")[1]);
            	 else if(line.startsWith("Sims"))
            		 gtTrans.setSims(Integer.valueOf( (line.split(":")[1]).trim()));
            	
             }
             
        }    
        catch(Exception e)
        {
        	System.err.println(e.getMessage());
        	MTTF =  0.0;
        	power = 10.0;
        }
	

		gtTrans.setMTTF(MTTF); 
		gtTrans.setPower(power);
        
        
		
	}
}
