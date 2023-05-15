package Alterers;

import static io.jenetics.internal.math.random.indexes;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import genetic.CoreGene;
import genetic.FloorplanChromosome;
import io.jenetics.AbstractAlterer;
import io.jenetics.AltererResult;
import io.jenetics.Genotype;
import io.jenetics.Phenotype;
import io.jenetics.util.MSeq;
import io.jenetics.util.RandomRegistry;
import io.jenetics.util.Seq;

public class FloorplanRecombinator <T extends Comparable<? super T>>
			extends AbstractAlterer<CoreGene, T>
{

	public FloorplanRecombinator(double probability) 
	{
		super(probability);
	}
	
	@Override
	public final AltererResult<CoreGene, T> alter(
		final Seq<Phenotype<CoreGene, T>> population,
		final long generation) 
	{
		final AltererResult<CoreGene, T> result ;
		
		if (population.size() >= 2) 
		{
			final Random random = RandomRegistry.getRandom();
			
			final MSeq<Phenotype<CoreGene, T>> pop = MSeq.of(population);
			int count = 0;
			List<Integer> indexList = indexes(random, population.size(), _probability).boxed().collect(Collectors.toList());
			
			Collections.shuffle(indexList);
			int[] indexes = indexList.stream().mapToInt(i->i.intValue()).toArray();
					
			
			for(int i=0; i<indexes.length/2; i++)
			{
				count+= recombine(pop, new int[]{indexes[2*i], indexes[2*i+1]}, generation );
			}

			result = AltererResult.of(pop.toISeq(), count);
			
		}
		else 
		{
			result = AltererResult.of(population.asISeq());
		}

		return result;
	}
	
	protected int recombine(final MSeq<Phenotype<CoreGene, T>> population,
			final int[] individuals,
			final long generation) 
	{
		final Random random = RandomRegistry.getRandom();
		final Phenotype<CoreGene, T> pt1 = population.get(individuals[0]);
		final Phenotype<CoreGene, T> pt2 = population.get(individuals[1]);
		final Genotype<CoreGene> gt1 = pt1.getGenotype();
		final Genotype<CoreGene> gt2 = pt2.getGenotype();
		
		
		 MSeq<CoreGene> flp1 = gt1.toSeq().get(0).toSeq().copy();
		 MSeq<CoreGene> flp2 = gt2.toSeq().get(0).toSeq().copy();
		
		 final int chIndex = random.nextInt(Math.min(flp1.length(), flp2.length()));
				 
		//CoreGene cg1 = flp1.getGene(random.nextInt(flp1.length()));
		//CoreGene cg2 = flp2.getGene(random.nextInt(flp2.length()));
		
		 CoreGene cg1 = flp1.get(chIndex).clone();
		 CoreGene cg2 = flp2.get(chIndex).clone();
		 
		 
		 //int emptyPos1 = flp1.getAnyAvailablePosition();
			
		 //randomCG.setLeftX(flpChromosome.getXposFromGlobalPosition(moveToPosition));
		 //randomCG.setBottomY(flpChromosome.getYposFromGlobalPosition(moveToPosition));

		
		return 2; //2 elements are swapped or altered at one time.
		
	}

	
}



//protected int recombine(final MSeq<Phenotype<LayerGene, T>> population,
//							final int[] individuals,
//							final long generation) 
//	{
//		
//		
//		//Choosing the Chromosome index for crossover.
//		final int chIndex = random.nextInt(Math.min(gt1.length()-4, gt2.length()-4) + 1); //Avoid Last 3 and the first one = total 4 chromosomes
//		
//		final MSeq<Chromosome<LayerGene>> c1 = gt1.toSeq().copy();
//		final MSeq<Chromosome<LayerGene>> c2 = gt2.toSeq().copy();
//		
//		final MSeq<LayerGene> genes1 = c1.get(chIndex).toSeq().copy();
//		final MSeq<LayerGene> genes2 = c2.get(chIndex).toSeq().copy();
//		
//		try
//		{
//			//int index = RandomRegistry.getRandom().nextInt(Math.min(genes1.length(), genes2.length()));
//			
//			if(c1.get(chIndex) instanceof SkipCnxnDualLayerChromosome)
//			{
//				crossoverskip(genes1, genes2); 
//				
//				c1.set(chIndex, c1.get(chIndex).newInstance(genes2.toISeq()));
//				c2.set(chIndex, c2.get(chIndex).newInstance(genes1.toISeq()));
//				
//			}
//			else
//				c1.swap(chIndex, c2); 	//Swap only the chosen chromosome
//			
//			/**
//			 * Swap one gene in selected chromosome
//			 */
//			//c1.set(chIndex, c1.get(chIndex).newInstance(genes1.toISeq()));
//			//c2.set(chIndex, c2.get(chIndex).newInstance(genes2.toISeq()));
//			
//			
//			/**
//			 * Swap rest of genotype 
//			 */
//			//for(int swapi = chIndex+1; swapi<gt1.length(); swapi++)
//			//	c1.swap(swapi, c2);
//			//Check
//			

//			//Creating two new Phenotypes and exchanging it with the old.
//			population.set(
//				individuals[0],
//				pt1.newInstance(newGT1, generation)
//			);
//			population.set(
//				individuals[1],
//				pt2.newInstance(newGT2, generation)
//			);
//		}
//		catch(Exception e)
//		{
//			System.err.println(e.getMessage());
//		}
//				
//		return getOrder();
//	}
//

//	
//	private void crossoverskip( final MSeq<LayerGene> that, final MSeq<LayerGene> other) 
//	{
//		that.swap(that.length()- 2,that.length(), other, other.length() - 2); // len - 2 because start is inclusive and end is exclusive for index
//	}
//	
//}
