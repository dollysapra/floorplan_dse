package helpers;

import io.jenetics.util.IntRange;
import io.jenetics.util.RandomRegistry;

public class Configs 
{
	public static  int populationSize = 50;
	public static  int numOfGAIterations = 100;
	public static  int parallelism = 6; //depends on resources available - GPUs etc. 
	public static  int bestSurvivors = 2; 
	public static  IntRange paretoSetSize = IntRange.of(10, 15);
	
	public static String SimulatorPath = "...";
	public static String Python3Path = "/opt/homebrew/bin/python3";
	public static String workspacePath = "/Users/dsapra/admorph/workspace/";
	public static String FlpPath = workspacePath + "runtime/";
	//public static String simulatorPath = workspacePath + "nl.uva.admorph.simuflage/src/simulate_budget.py";
	public static String simulatorPath = workspacePath + "nl.uva.admorph.simuflage/src/simulate4dse.py";
	//public static String simulatorPath = workspacePath + "nl.uva.admorph.simuflage/src/simulate_utilization.py";
	

	public static int simulationBudget = 100;
	
	public static int HardwareFloorPlanMaxX = 3;
	public static int HardwareFloorPlanMaxY = 3;
	public static int minimumCores = 3;
	
	public static int numCoreTypes = 3;
	
	public static double MaxMTTF = 1000000.0;
	
	public static enum Schedulers {
		HEFT,
		DNDS,
		DWDS
	}

	
	public static Schedulers getRandomScheduler() 
	{
	    int pick = RandomRegistry.getRandom().nextInt(Schedulers.values().length);
	    return Schedulers.values()[pick];
	}
}
