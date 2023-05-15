package genetic;

import static io.jenetics.util.RandomRegistry.getRandom;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import helpers.Configs;
import io.jenetics.Gene;

public class CoreGene  implements Gene<Core,CoreGene>, Serializable
{

	private static final long serialVersionUID = 3500930529925447436L;
	
	protected Core _core;
	private Configs.Schedulers _scheduler  = null;
	private double scaleX= 0.0008;
	private double scaleY= 0.00075;
	
	static int counter = 1;
	
	public CoreGene(int positionX, int positionY, int type)
	{
		_core = new Core();
		_core.setName("core" + counter++);
		_core.setLeftX(positionX);
		_core.setBottomY(positionY);
		_core.setType(type);
		
	}
	
	public CoreGene(int positionX, int positionY)
	{
		_core = new Core();
		_core.setName("core" + counter++);
		_core.setLeftX(positionX);
		_core.setBottomY(positionY);
		_core.setType(getRandom().nextInt(Configs.numCoreTypes)); //random returns between 0 and num-1.	
	}
	
	public CoreGene()
	{
		_core = new Core();
		_core.setName("core" + counter++);
		_core.setLeftX(0);
		_core.setBottomY(0);
		_core.setType(getRandom().nextInt(Configs.numCoreTypes)); //random returns between 0 and num-1. 	
	}
	
	public CoreGene(Configs.Schedulers sch)
	{
		_core = null;
		_scheduler = sch;
		
	}
	
	public CoreGene(Core _C)
	{
		_core = _C.clone();
	}

	
	public String toString()
	{
		
		DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
		df.setMaximumFractionDigits(34);
		
		
		if(_core!=null)
		{
		return _core.toNameString() + df.format(getScaledWidth()) + 
				"\t" + df.format(getScaledHeight())  + "\t" + df.format(getScaledLeftX())
				+ "\t" + df.format(getScaledBottomY());
	
		}
		else
		{
			return "Scheduling Policy: " + _scheduler;
		}
	}
	
	public CoreGene clone()
	{
		if(_core!=null)
			return new CoreGene(_core);
		else return new CoreGene(_scheduler);
	}
	
	public CoreGene mutateType()
	{
		
		CoreGene cg = this.clone();
	
		if(_core == null)
			return cg;
					
		cg._core.changeRandomNewType();
		return cg;
		
	}
	
	public CoreGene mutateScheduler()
	{
		
		CoreGene cg = this.clone();
	
		if(_core != null)
			return cg;
					
		cg._scheduler = Configs.getRandomScheduler();
		return cg;
		
	}
	
	
	public double getScaledWidth(){
		return _core.getWidth()*scaleX;
	}
	
	public double getScaledHeight(){
		return _core.getHeight()*scaleY;
	}
	
	public double getScaledLeftX(){
		return _core.getLeftX()*scaleX;
	}
	
	public double getScaledBottomY(){
		return _core.getBottomY()*scaleY;
	}
	
	
	public int getWidth(){
		return _core.getWidth();
	}
	
	public int getHeight(){
		return _core.getHeight();
	}
	
	public int getLeftX(){
		return _core.getLeftX();
	}
	public int getBottomY() {
		return _core.getBottomY();
	}
	
	public void setLeftX(int leftX){
		 _core.setLeftX(leftX);
	}
	
	public void setBottomY(int bottomY) {
		_core.setBottomY(bottomY);
	}
	
	
	@Override
	public boolean isValid() 
	{
		return ((_core!=null) || (_scheduler!=null));
	}
	
	@Override
	public Core getAllele() 
	{
		return _core;
	}
	
	public Configs.Schedulers getScheduler() 
	{
		return _scheduler;
	}
	
	@Override
	public CoreGene newInstance() 
	{
		return new CoreGene(); 
	}
	
	@Override
	public CoreGene newInstance(Core core) 
	{
		return new CoreGene(_core);
	}
}
	