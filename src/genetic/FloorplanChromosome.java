package genetic;
import static io.jenetics.util.RandomRegistry.getRandom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import helpers.Configs;
import io.jenetics.Chromosome;
import io.jenetics.VariableChromosome;
import io.jenetics.util.ISeq;
import io.jenetics.util.IntRange;
import io.jenetics.util.MSeq;

public class FloorplanChromosome extends VariableChromosome<CoreGene> 
{
	
	private static final long serialVersionUID = 5665265868876847105L;
	
	private static int _minCores;
	private static int _flpmaxX;
	private static int _flpmaxY;
	
	
	protected FloorplanChromosome(ISeq<? extends CoreGene> genes, IntRange lengthRange) 
	{
		super(genes, lengthRange);
	}
	
	protected FloorplanChromosome(ISeq<? extends CoreGene> genes) 
	{
	
		super(genes, IntRange.of(Configs.minimumCores, Configs.HardwareFloorPlanMaxX*Configs.HardwareFloorPlanMaxY +1));
	}
	

	public FloorplanChromosome(int minCores, int flpmaxX, int flpmaxY)
	{

		this(getNewRandomFloorplan(minCores,flpmaxX,flpmaxY).toISeq());
		
		_minCores = minCores;
		_flpmaxX = flpmaxX;
		_flpmaxY = flpmaxY;
		
		_valid = true;
	}
	
	
	@Override
	public Chromosome<CoreGene> newInstance(ISeq<CoreGene> geneSequence) {
		return new FloorplanChromosome(geneSequence);
	}

	@Override
	public Chromosome<CoreGene> newInstance() {
	
		return new FloorplanChromosome(_minCores, _flpmaxX, _flpmaxY);
	}

	
	
	public static MSeq<CoreGene> getNewRandomFloorplan(int minCores,int flpmaxX, int flpmaxY )
	{
		int maxCores = flpmaxX*flpmaxY;
		
		List<Integer> flpPositions =
			    IntStream.range(0, maxCores)                      // <-- creates a stream of ints
			        .boxed()                                // <-- converts them to Integers
			        .collect(Collectors.toList());          // <-- collects the values to a list

			Collections.shuffle(flpPositions);
			
			int len = getRandom().nextInt(minCores, maxCores+1);
			List<CoreGene> flp = new ArrayList<>();
			
			for(int i =0; i<len; i++)
			{
				int corePosition = flpPositions.get(i);
				
				int posX = Math.floorDiv(corePosition, flpmaxX);
				int posY = corePosition%flpmaxX;
				flp.add(new CoreGene(posX,posY));
						
			}
			return MSeq.of(flp);
	}

	
	public static FloorplanChromosome of(int minCores, int flpmaxX, int flpmaxY)
	{
		return new FloorplanChromosome(minCores,flpmaxX,flpmaxY);
	}
	

	public List<Integer> getPositions()	
	{
		List<Integer> flpPositions = new ArrayList<>();
		
		
		this.toSeq().asList().forEach(gene -> flpPositions.add(gene.getLeftX()*_flpmaxX + gene.getBottomY()));
		
		return flpPositions;
	}
	
	public List<Integer> getPositionsMap()	
	{
		List<Integer> flpPositions = new ArrayList<>();
		
		
		this.toSeq().asList().forEach(gene -> flpPositions.add(gene.getLeftX()*_flpmaxX + gene.getBottomY()));
		
		List<Integer> flpPositionMap = new ArrayList<>(Collections.nCopies(_flpmaxX*_flpmaxY, 0));
		
		flpPositions.forEach(pos -> flpPositionMap.set(pos, 1));
		
		return flpPositionMap;
	}
	
	public List<Integer> getAvailablePositions()	
	{
		List<Integer> flpPositions = new ArrayList<>();
		

		List<Integer> availablePositions =
			    IntStream.range(0, _flpmaxX*_flpmaxY).boxed().collect(Collectors.toList()); 
		
		this.toSeq().asList().forEach(gene -> flpPositions.add(gene.getLeftX()*_flpmaxX + gene.getBottomY()));
		
		
		flpPositions.forEach(pos -> availablePositions.remove(pos));
		
		return availablePositions;
	}
	
	public int getAnyAvailablePosition()
	{
		List<Integer> availablePositions = getAvailablePositions();
		
		if(availablePositions.size() == 0)
		{
			System.out.println("error");
			List<Integer> flpPositions = new ArrayList<>();
			this.toSeq().asList().forEach(gene -> flpPositions.add(gene.getLeftX()*_flpmaxX + gene.getBottomY()));
			flpPositions.forEach(pos -> availablePositions.remove(pos));
		}
		
		Collections.shuffle(availablePositions);
		return availablePositions.get(0);
	}
	
	public boolean isFull()
	{
		return (this.toSeq().length() == _flpmaxX*_flpmaxY)?true:false;
		
	}
	
	
	public int getXposFromGlobalPosition(int pos)
	{
		return Math.floorDiv(pos, _flpmaxX);
	}
	
	public int getYposFromGlobalPosition(int pos)
	{
		return pos % _flpmaxX;
	}
	
	
}
