package Alterers;

import java.util.Random;

import genetic.CoreGene;
import io.jenetics.Mutator;

public class CoreTypeMutator <T extends Comparable<? super T>>
			extends Mutator<CoreGene,T>

{


	public CoreTypeMutator(double probability) 
	{
		super(probability);
	}
	
	public CoreTypeMutator()
	{
		    	super(0.3);
		    	
		   
	}
	
	@Override
	protected CoreGene mutate(final CoreGene gene, final Random random) 
	{
		if(gene.getAllele()!=null)
			return gene.mutateType();
		else return gene.mutateScheduler();
	}
	
}
