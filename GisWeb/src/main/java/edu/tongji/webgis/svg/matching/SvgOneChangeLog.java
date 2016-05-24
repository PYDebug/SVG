package edu.tongji.webgis.svg.matching;

import java.util.ArrayList;

import edu.tongji.webgis.svg.ivtd.IVTDGen;

public class SvgOneChangeLog {

	private int size = 0;
	
	private IVTDGen SVG;
	
	private ArrayList<String> resultXPath = new ArrayList<String>();
	
	//Types insert and moveIn --> 1; Types delete and moveOut --> 2;
	private ArrayList<Integer> resultXPathTypes = new ArrayList<Integer>();
	
	public SvgOneChangeLog(IVTDGen SVG){
		this.SVG = SVG;
	}
	
	public void addChangeLog(int types, String XPath){
		this.resultXPath.add(XPath);
		this.resultXPathTypes.add(types);
		this.size++;
	}
	
	public String getChangedPosition(String XPath){
		
		String newXPath ="";
		//Current Processing XPath in Change Log;
		String oldXPath ="";
		
		int largestSameLength;
		
		if(size == 0){
			return XPath;
		}
		
		for(int i = 0; i < size; i++){
			oldXPath = resultXPath.get(i);
			int oldXPathIndex = getFinalLayerIndex(oldXPath);
			int oldXPathTypes = resultXPathTypes.get(i);
			if(!isInSameLayer(XPath,oldXPath) && XPath.length() > oldXPath.length()){
				//continue;
				largestSameLength = getLargestSameLength(XPath,oldXPath);
				String lastChar = oldXPath.substring(largestSameLength-1,largestSameLength);
				String largestSamePart = oldXPath.substring(0,largestSameLength);
				int parentLayerNewIndex = 0;
			    if( lastChar!= null){
			    	//It means their parent is originally in the same position
			    	if(lastChar.equals("]")){ //== "]" is wrong!
			    		oldXPathIndex = getFinalLayerIndex(largestSamePart);
                        //insert will be good
			    		if(oldXPathTypes == 1){
			    			parentLayerNewIndex = oldXPathIndex + 1;
			    			XPath = XPath.substring(0,largestSamePart.lastIndexOf('[')+1)
			    					+ parentLayerNewIndex + ']' + XPath.substring(
			    							nextIndexOfChar(XPath,"]",largestSamePart.lastIndexOf(']'))+1);
			    		}else{
			    		//but we need to consider next generation if delete(oldXPathTypes == 2);
			    			oldXPathIndex = getFinalLayerIndex(largestSamePart);
			    			String furtherXPath = XPath.substring(0,nextIndexOfChar(XPath,"]",largestSameLength)+1);
			    			int beingNewParentIndex = oldXPathIndex + getFinalLayerIndex(furtherXPath) - 1;
			    			XPath = XPath.substring(0,largestSamePart.lastIndexOf('*')+1) + '[' + 
			    					beingNewParentIndex + ']' + XPath.substring(nextIndexOfChar(XPath, 
			    							"]", largestSameLength)+1,XPath.length());
			    		}
//			    	}else if(lastChar != "["){//forget considering [12 and [13 is the same this 
			    	}else if(!lastChar.equals("]")){
			    		String strOldXPathIndex = oldXPath.substring(largestSamePart.lastIndexOf("[")+1,
			    				nextIndexOfChar(oldXPath,"]",largestSameLength));
			    		String strNewXPathIndex = XPath.substring(largestSamePart.lastIndexOf("[")+1,
			    				nextIndexOfChar(XPath,"]",largestSameLength));
			    		oldXPathIndex = Integer.valueOf(strOldXPathIndex);
			    		int newXPathIndex = Integer.valueOf(strNewXPathIndex);
                        switch(oldXPathTypes){
                        case 1:{
                        	if(oldXPathIndex < newXPathIndex){
                        		newXPathIndex++;
                        		break;
                        	}else {
                        		break;
                        	}
                        }
                        case 2:{
                        	if(oldXPathIndex < newXPathIndex){
                        		newXPathIndex--;
                        		break;
                        	}else{
                        		break;
                        	}
                        }
                        }
                        XPath = XPath.substring(0,largestSamePart.lastIndexOf('*')+1) + '[' + 
                        		newXPathIndex + ']' + XPath.substring(nextIndexOfChar(XPath, 
		    							"]", largestSameLength)+1,XPath.length());
			    	}
			    }
			    continue;
			}
			if(!isBrother(XPath,oldXPath)){
				continue;
			}
			int newXPathIndex = getFinalLayerIndex(XPath);
			switch (oldXPathTypes){
			//Types of insert and moveIn
			case 1:{
				if(newXPathIndex < oldXPathIndex){
					break;
				}else{
					newXPathIndex++;
					break;
				}
			}
			//Types of delete and moveOut
			case 2:{
				if(newXPathIndex <= oldXPathIndex){
					break;
				}else{
					newXPathIndex--;
            		int childCount = 0;
            		for(int k=0; k<SVG.getLCCount(); k++){
            			String potentialChildXPath = SVG.getPosition(k);
						if (potentialChildXPath.contains(oldXPath)
								&& potentialChildXPath.length() > oldXPath.length()) {
							childCount++;
						}
            		}
            		newXPathIndex = newXPathIndex + childCount;
					break;
				}
			}
			default:break;
			}
			XPath = XPath.substring(0,XPath.lastIndexOf('*')+1) + '[' + newXPathIndex + ']';
		}
		
//		newXPath = XPath.substring(0,XPath.lastIndexOf('*')+1) + '[' + newXPathIndex + ']';
		
//		return newXPath;
		return XPath;
	}
	
	public boolean isInSameLayer(String XPathA, String XPathB){
		int levelA = levelNumbers(XPathA);
		int levelB = levelNumbers(XPathB);
		
		return levelA == levelB ? true : false;
	}
	
	public int levelNumbers(String str){
        int cnt = 0;
        int offset = 0;
        while((offset = str.indexOf('/', offset)) != -1){
            offset = offset + 1;
            cnt++;
        }
        return cnt;
	}
	
	public boolean isBrother(String XPathA, String XPathB){
		String subXPathA = XPathA.substring(0,XPathA.lastIndexOf('/'));
		String subXPathB = XPathB.substring(0,XPathB.lastIndexOf('/'));
		
		return subXPathA.equals(subXPathB) ? true:false;
	}
	
	public int getFinalLayerIndex(String XPath){		
		String strIndex = XPath.substring(XPath.lastIndexOf('[')+1, XPath.lastIndexOf(']'));		
		int actualIndex = Integer.parseInt(strIndex);
		return actualIndex;
	}
	
	public int getLargestSameLength(String XPathA, String XPathB){
		int largestSameLength = 0;
		String charA;
		String charB;
		
		int compareLength = XPathA.length() > XPathB.length() ? XPathB.length() : XPathA.length();
		for(int i = 0; i < compareLength; i++){
			charA = XPathA.substring(i,i+1);
			charB = XPathB.substring(i,i+1);
			if(charA.equals(charB)){
				largestSameLength = largestSameLength + 1;
			}else{
				break;
			}
		}
		return largestSameLength;
	}
	
	public int nextIndexOfChar(String str, String ch, int offset){
		for(int i = offset; i < str.length() ; i++){
			if(str.substring(i,i+1).equals(ch)){ // always remember that (ch == str.substring(i,i+1)) is wrong !
				return i;
			}else{
				continue;
			}
		}
		//minus 1 represents that we could not find such that char.
		return -1;
	}
	
	public void adaptSVGTreeChanges(IVTDGen SVG1, String XPath){
		
		this.addChangeLog(1, XPath);
	}
	
}
