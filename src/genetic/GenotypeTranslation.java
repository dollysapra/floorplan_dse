package genetic;

import helpers.Configs;
import helpers.FloorplanFileCreator;
import io.jenetics.Genotype;

public class GenotypeTranslation 
{
	private Boolean _valid = true;
	private Genotype<CoreGene> _gt;
	private String _name = "";
	
	private double _MTTF;
	private double _power;
	private int _sims =0 ;
	public Configs.Schedulers scheduler = Configs.Schedulers.HEFT;
	
	public GenotypeTranslation(Genotype<CoreGene> genotype)
	{
		_name =FloorplanFileCreator.saveToFile((FloorplanChromosome)genotype.getChromosome(0));
		scheduler = genotype.getChromosome(genotype.length() -1 ).getGene().getScheduler();
	}

	
	//TODO: THIS FUNCTION MAYBE NOT NEEDED 
	public static GenotypeTranslation decodeGenotype(Genotype<CoreGene> genotype)
	{
		return new GenotypeTranslation(genotype);
	}
		
	public Boolean isValid()
	{
		return _valid;
	}
	
	public String toEvaluatedString()
	{
	
		if(_sims == 0)
			return _name + ":" + _MTTF + ":" + _power;
		else
			return _name + ":" + _MTTF + ":" + _power + ":" + _sims;
	}
	
	
	public void setMTTF(double mttf)
	{
		_MTTF = mttf;
	}
	public double getMTTF()
	{
		return _MTTF;
	}
	
	public void setSims(int simulations)
	{
		_sims = simulations;
	}
	public double getSims()
	{
		return _sims;
	}
	
	
	public String getName()
	{
		return _name;
	}
	
	
	public void setPower(double avgPower)
	{
		_power = avgPower;
	}
	public double getPower()
	{
		return _power ;
	}
	
	
}
