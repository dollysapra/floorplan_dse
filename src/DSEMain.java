import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.Iterator;
import java.util.concurrent.Executors;

import Alterers.CoreAddMutator;
import Alterers.CoreMoveMutator;
import Alterers.CoreRemoveMutator;
import Alterers.CoreTypeMutator;
import genetic.CoreGene;
import genetic.DSEProblem;
import genetic.GenotypeTranslation;
import helpers.Configs;
import io.jenetics.Optimize;
import io.jenetics.Phenotype;
import io.jenetics.TournamentSelector;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.ext.moea.MOEA;
import io.jenetics.ext.moea.NSGA2Selector;
import io.jenetics.ext.moea.Vec;
import io.jenetics.util.ISeq;
import io.jenetics.util.Seq;

public class DSEMain {
	
	static final Engine<CoreGene, Vec<double[]>> MULTI_ENGINE = 
			Engine.builder(new DSEProblem())
			.populationSize(Configs.populationSize)
			.minimizing()
			.survivorsSelector(NSGA2Selector.ofVec())
			.offspringSelector(new TournamentSelector<>(3))
			.alterers(new CoreAddMutator<Vec<double[]>>(0.2),
					  new CoreMoveMutator<Vec<double[]>>(0.3),
					  new CoreRemoveMutator<>(0.2),
					  new CoreTypeMutator<>(0.3))
			.executor(Executors.newFixedThreadPool(Configs.parallelism))
			.build();

	
	
	public static void main(final String[] args) 
	{

		System.out.println("iterations - " + Configs.numOfGAIterations);
		
		
		final ISeq<Phenotype<CoreGene, Vec<double[]>>> paretoset  = MULTI_ENGINE.stream()//new InitialPopulationCreator(populationSize).createPopulation() )
				.limit(Configs.numOfGAIterations)
				.peek(DSEMain::peek)
				.collect(MOEA.toParetoSet(Configs.paretoSetSize));

			System.out.println("Best results are ::  "+ new Date().toString());
			
						
			Iterator<Phenotype<CoreGene, Vec<double[]>>> it = paretoset.iterator();
			while(it.hasNext())
			{
				Phenotype<CoreGene, Vec<double[]>> pt = it.next();
				
			
				GenotypeTranslation gtTrans = new GenotypeTranslation(pt.getGenotype());
				double MTTF = Configs.MaxMTTF - pt.getFitness().data()[0];
				double power = pt.getFitness().data()[1];
				
				System.out.println(pt.getGeneration() + " : " +gtTrans.getName() +":"+ MTTF + ","+ power);
			}
			 
	}
	
	private static Double bestMTTFYet = 0.0;

	private static void peek (final EvolutionResult<CoreGene, Vec<double[]>> result)
	{
		DoubleSummaryStatistics stats = result.getPopulation().stream().mapToDouble(pt->pt.getFitness().data()[0]).summaryStatistics();
		DoubleSummaryStatistics stats_second = result.getPopulation().stream().mapToDouble(pt->pt.getFitness().data()[1]).summaryStatistics();
		
		System.out.println("gen: " + result.getGeneration() + " stats: " + stats.toString() + "\n second: " + stats_second.toString() );
		
		double bestOfThisEvolution = result.getBestFitness().data()[0]; //in reverse
		
		bestMTTFYet = (bestOfThisEvolution > bestMTTFYet)? bestOfThisEvolution : bestMTTFYet;
		
		
		ISeq<Phenotype<CoreGene, Vec<double[]>>> paretoTemp =  NSGA2Selector.ofVec().select((Seq)result.getPopulation().asMSeq(), Configs.paretoSetSize.getMax(), Optimize.MINIMUM);
		
		Iterator<Phenotype<CoreGene, Vec<double[]>>> it = paretoTemp.iterator();
		
		while(it.hasNext())
		{
			Phenotype<CoreGene, Vec<double[]>> pt = it.next();
			
			double MTTF = Configs.MaxMTTF - pt.getFitness().data()[0];
			double power = pt.getFitness().data()[1];
			System.out.println(pt.getGeneration() + " : " + MTTF + ","+ power);
			
		}
		
		
	}
	
}
