package edu.tongji.webgis.svg.query;

public class ModeParser {
	public ModeParser(String in)
	{
		String[] args=in.split(" ");
		modeId=Integer.parseInt(args[0]);
		operand=Integer.parseInt(args[1]);
		switch (modeId) {
		case 1:
		case 2:
		case 3:
			attr="length";
			element="rivers";
			break;
		case 7:
		case 8:
		case 9:
			attr="price";
			element="hotels";
			break;
		case 13:
		case 14:
		case 15:
			attr="level";
			element="restaurant";
			break;
		case 16:
		case 17:
		case 18:
			attr="praise";
			element="supermarket";
			break;
		case 19:
		case 20:
		case 21:
			attr="population";
			element="school";
			break;
		case 22:
		case 23:
		case 24:
			attr="satisfaction";
			element="hospital";
			break;
		default:
			break;
		}
		
		
		
		
		
		
		
		
	}


	public boolean operate(int in) {
		switch (modeId) {
		case 1:
		case 7:
		case 13:
		case 16:
		case 19:
		case 22:
			if (in > operand)
				return true;
			else
				return false;
		case 2:
		case 8:
		case 14:
		case 17:
		case 20:
		case 23:
			if (in < operand)
				return true;
			else
				return false;
		case 3:
		case 9:
		case 15:
		case 18:
		case 21:
		case 24:
			if (in == operand)
				return true;
			else
				return false;
/*			
		case 7:
			if (in > operand)
				return true;
			else
				return false;
		case 8:
			if (in < operand)
				return true;
			else
				return false;
		case 9:
			if (in == operand)
				return true;
			else
				return false;*/
			
			
			
		default:
			System.out.println("no that ModeID"+in);
			return false;
		}

	}
	
	
	
	
	public String getAttr() {
		return attr;
	}


	public void setAttr(String attr) {
		this.attr = attr;
	}


	public String getElement() {
		return element;
	}


	public void setElement(String element) {
		this.element = element;
	}

	private String attr;
	private String element;
	
	private int modeId;
	private int operand;
	
	
	

}
