package genetic;

import helpers.Configs;
import io.jenetics.Chromosome;
import io.jenetics.VariableChromosome;
import io.jenetics.util.ISeq;
import io.jenetics.util.IntRange;
import io.jenetics.util.MSeq;

public class SchedulerChromosome extends VariableChromosome<CoreGene> 
{
	private static final long serialVersionUID = 8748893030773446292L;

	public SchedulerChromosome(ISeq<? extends CoreGene> genes, IntRange lengthRange) {
		super(genes, IntRange.of(1));
	}

	public SchedulerChromosome(ISeq<? extends CoreGene> genes) {
		super(genes, IntRange.of(1));
	}

	public SchedulerChromosome(Configs.Schedulers sch)
	{
		this(MSeq.of(getScheduleGeneOf(sch)).asISeq());
	}
	
	public SchedulerChromosome()
	{
		this(MSeq.of(getRandomScheduleGene()).asISeq());
	}
	
	private static CoreGene getRandomScheduleGene()
	{
		CoreGene cg = new CoreGene(Configs.getRandomScheduler());
		return cg;
	}

	private static CoreGene getScheduleGeneOf(Configs.Schedulers sch)
	{
		CoreGene cg = new CoreGene(sch);
		return cg;
	}
	public static SchedulerChromosome of()
	{
		return new SchedulerChromosome();
	}
	
	@Override
	public Chromosome<CoreGene> newInstance(ISeq<CoreGene> arg0) 
	{
		return new SchedulerChromosome(arg0);
	}

	@Override
	public Chromosome<CoreGene> newInstance() {
		
		return new SchedulerChromosome();
	}

	

}
