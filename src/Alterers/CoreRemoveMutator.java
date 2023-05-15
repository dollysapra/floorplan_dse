package Alterers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import genetic.CoreGene;
import genetic.FloorplanChromosome;
import helpers.Configs;
import io.jenetics.Chromosome;
import io.jenetics.Mutator;
import io.jenetics.MutatorResult;
import io.jenetics.util.MSeq;

public class CoreRemoveMutator <T extends Comparable<? super T>>
			extends Mutator<CoreGene,T>
{

	public CoreRemoveMutator(double probability) 
	{
		super(probability);
	}
	
	public CoreRemoveMutator()
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
		
		if(chromosome.length() <= Configs.minimumCores)
			return MutatorResult.of(chromosome);
		
		List<CoreGene> newChromosome = new LinkedList<>(chromosome.toSeq().asList());
		int removeid = random.nextInt(0,chromosome.length());
		newChromosome.remove(removeid);
		
		MSeq<CoreGene> flpSeq = MSeq.of(newChromosome);
		
	
		return MutatorResult.of(chromosome.newInstance(flpSeq.asISeq()));
	}
	

	
}


	
	
	
	