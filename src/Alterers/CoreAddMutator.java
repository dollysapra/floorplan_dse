package Alterers;

import java.util.Random;

import genetic.CoreGene;
import genetic.FloorplanChromosome;
import io.jenetics.Chromosome;
import io.jenetics.Mutator;
import io.jenetics.MutatorResult;

public class CoreAddMutator <T extends Comparable<? super T>>
			extends Mutator<CoreGene,T>
{

	public CoreAddMutator(double probability) 
	{
		super(probability);
	}
	
	public CoreAddMutator()
	{
		 super(0.1);
		    	
		   
	}
	
	@Override	
	protected MutatorResult<Chromosome<CoreGene>> mutate(
			final Chromosome<CoreGene> chromosome,
			final double p,
			final Random random) 
	{
		if(!(chromosome instanceof FloorplanChromosome))
			return MutatorResult.of(chromosome);
		
		FloorplanChromosome flpChromosome = (FloorplanChromosome) chromosome; 
		
		if(flpChromosome.isFull())
			return MutatorResult.of(chromosome);
		
		int emptyPos = flpChromosome.getAnyAvailablePosition();
		
		int posX = flpChromosome.getXposFromGlobalPosition(emptyPos);
		int posY = flpChromosome.getYposFromGlobalPosition(emptyPos);
		
		
		return MutatorResult.of(flpChromosome.newInstance(flpChromosome.toSeq().append(new CoreGene(posX,posY))));
	}
	

	
}


	