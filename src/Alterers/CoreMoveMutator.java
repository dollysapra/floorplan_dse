package Alterers;

import java.util.Random;

import genetic.CoreGene;
import genetic.FloorplanChromosome;
import io.jenetics.Chromosome;
import io.jenetics.Mutator;
import io.jenetics.MutatorResult;

public class CoreMoveMutator <T extends Comparable<? super T>>
			extends Mutator<CoreGene,T>
{

	public CoreMoveMutator(double probability) 
	{
		super(probability);
	}
	
	public CoreMoveMutator()
	{
		    	super(0.3);
		    	
		   
	}
	
	
	
	@Override	
	protected MutatorResult<Chromosome<CoreGene>> mutate(
			final Chromosome<CoreGene> chromosome,
			final double p,
			final Random random) 
	{
		
		//Probability is not used here -- only one gene is moved. No need for probabiliy of gene being selected. 
		
		if(!(chromosome instanceof FloorplanChromosome))
			return MutatorResult.of(chromosome);
		
		FloorplanChromosome flpChromosome = (FloorplanChromosome) chromosome.newInstance(chromosome.toSeq());


		if(flpChromosome.isFull())
			return MutatorResult.of(chromosome);

		CoreGene randomCG = flpChromosome.getGene(random.nextInt(0,flpChromosome.toSeq().length())).clone();
		
		int moveToPosition = flpChromosome.getAnyAvailablePosition();
		randomCG.setLeftX(flpChromosome.getXposFromGlobalPosition(moveToPosition));
		randomCG.setBottomY(flpChromosome.getYposFromGlobalPosition(moveToPosition));
		
		return MutatorResult.of(flpChromosome);
		
		
	}
	
	
}


	
	
	