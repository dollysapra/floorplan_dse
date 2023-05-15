package genetic;

import java.util.function.Function;

import helpers.Configs;
import io.jenetics.Genotype;
import io.jenetics.engine.Codec;
import io.jenetics.util.Factory;

public class FloorplanCodec implements Codec<GenotypeTranslation, CoreGene>
{
	
	public static final Genotype<CoreGene> ENCODING = Genotype.of(
			FloorplanChromosome.of(Configs.minimumCores, Configs.HardwareFloorPlanMaxX, Configs.HardwareFloorPlanMaxY),
			SchedulerChromosome.of()
			);
			

	@Override
	public Function<Genotype<CoreGene>, GenotypeTranslation> decoder() 
	{
		
		return gt-> new GenotypeTranslation(gt);
	}

	@Override
	public Factory<Genotype<CoreGene>> encoding() 
	{
		return ENCODING;
	}

}
