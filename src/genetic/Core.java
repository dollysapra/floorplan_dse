package genetic;

import helpers.Configs;
import io.jenetics.util.RandomRegistry;

public class Core implements Cloneable
{
	private String _name;
	private int _type =0;
	private int _height =1;
	private int _width =1;
	private int _left_x;
	private int _bottom_y;
	
	@Override
	public Core clone() 
	{
		Core c = new Core();
		c.setName(_name);
		c.setType(_type);
		c.setLeftX(_left_x);
		c.setBottomY(_bottom_y);
		c.setHeight(_height);
		c.setWidth(_width);
		
		return c;
	}
	
	public void setName(String name) {
		_name = name;
	}
	
	public int getType() {
		return _type;
	}
	
	public void setType(int type) {
		_type = type;
	}
	
	public void setRandomType() {
		_type = RandomRegistry.getRandom().nextInt(0, Configs.numCoreTypes);
	}
	
	public void changeRandomNewType() 
	{
		int newType = _type;
		while(newType == _type) { newType = RandomRegistry.getRandom().nextInt(0, Configs.numCoreTypes);}
				
		_type = newType;
	}
	
	public void setWidth(int width) {
		 _width = width;
	}
	
	public int getWidth(){
		return _width;
	}
	
	
	public void setHeight(int height) {
		 _height = height;
	}
	
	public int getHeight() {
		return _height;
	}
	
	public void setLeftX(int left_x)  {
		_left_x = left_x;
	}
	public int getLeftX() {
		return _left_x;
	}
	
	
	public void setBottomY(int bottom_y)  {
		_bottom_y = bottom_y;
	}
	
	public int getBottomY() {
		return _bottom_y;
	}
	
	public String toNameString() {
		return _type+ "_"+ _name + "\t" ;
	}
}
