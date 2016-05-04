package edu.tongji.webgis.svg.query;
//���ݴʿ�  ������ѯ��� ����Mode
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Lexicon {
	
	//storeindex �ж��Ƿ�Ҫ��index�ļ������ڴ�
	public Lexicon(String fileIn,boolean storeindex)
	{
		filePath=fileIn;
		facttory = new JsonFactory();
		
		indexInMem=storeindex;
		if(storeindex)
		{
			storeIndex();
		}
		queryFailed=new ArrayList<String>();
	}
	
	//��index.json�����ڴ�
	private boolean storeIndex()
	{
		
		
		try {
			parser = facttory.createJsonParser(new File(filePath+"//index.json"));
			indexMap=new HashMap();
			while(parser.nextToken()!=null)
			{
				if(parser.getCurrentToken()==JsonToken.VALUE_STRING)
				{
					indexMap.put(parser.getCurrentName(),parser.getText() );
				}
			}
			return true;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
		
	}
	
	
	//ͨ������key����index�ļ����ҳ�ƥ���content���ļ�
	//��ͨ���ʿ��е�Ԫ�����ԣ��ҵ���Ԫ���ڴʿ��еĶ���λ��
	public String getIndex(String key) throws JsonParseException, IOException
	{
		parser = facttory.createJsonParser(new File(filePath+"//index.json"));
		if (indexInMem) {
			if(indexMap.get(key)!=null)
				return (String)indexMap.get(key);

			return null;

		} else {
			while (parser.nextToken() != JsonToken.END_OBJECT) {
				String fieldname = parser.getCurrentName();
				if (key.equals(fieldname)) {
					parser.nextToken();

					return parser.getText();
				}
			}
			return null;
		}
	}
	
	
	public void printAll() throws JsonParseException, IOException
	{
		parser = facttory.createJsonParser(new File(filePath+"//index.json"));
		String token="asd";//parser.getCurrentToken().toString();
		while( parser.nextToken()!= null)
		{
			token=parser.getCurrentToken().toString();
			System.out.println(token);
			System.out.println(parser.getCurrentName());
			System.out.println(parser.getText());
			System.out.println("----------------------------");
		}

	}
	
	public String getModes(String in) throws JsonParseException, IOException
	{
		String[] arr=in.split(" ");
		String out="";
		for(String s:arr)
		{
			String res=getMode(new StringBuffer(s));
			if(res!=null)
			{
				//out
			}
			
			
		}
		return null;
	}
	
	//get mode from content file using string key
	public String getMode(StringBuffer Bufkey) throws JsonParseException, IOException
	{
		int startPos=0,endPos=0;
		int length=Bufkey.length();
		LinkedList<contentAttrs> conAttrs= new LinkedList<contentAttrs>(); 
		//
		while(startPos<length)
		{
			endPos=startPos+1;
			while(endPos<=length)
			{
				String curStr=Bufkey.substring(startPos, endPos);
				String index=getIndex(curStr);
				if(index!=null)
				{
					//Bufkey=Bufkey.substring(0, startPos)+key.substring(endPos,length);
					Bufkey.replace(startPos, endPos, "");
/*					System.out.println(index);
					System.out.println(Bufkey);
					System.out.println("--------------------");*/
					
					
					//do something with contentfile
					String contentFilePath=filePath+index+"/content.json";
					contentfacttory = new JsonFactory();
					conPar=contentfacttory.createJsonParser(new File(contentFilePath));
					
					
					//LinkedList<contentAttrs> conAttrs= new LinkedList<contentAttrs>(); 
					ReadContentInMem(conPar,conAttrs);
					
					int startP=0,endP=0;
					int attrKeyLength=Bufkey.length();
					
					while(startP<=attrKeyLength)
					{
						endP=startP+1;
						while(endP<=attrKeyLength)
						{
							String attrCurStr=Bufkey.substring(startP, endP);
							contentAttrs conRes=getAttr(conAttrs,attrCurStr);
							if(conRes!=null)
							{
								//Bufkey=Bufkey.substring(0, startP)+Bufkey.substring(endP,attrKeyLength);
								Bufkey.replace(startP, endP, "");
								//StringBuffer keyBuf=new StringBuffer(key);
								String mode_op=modeFromAttr(conRes.operator,Bufkey);
								if(Bufkey.length()==0){
									return mode_op;
								}
								else
								{
									if(mode_op!=null){
										return mode_op+"+"+getMode(Bufkey);}
									else{
										return getMode(Bufkey);
									}
								}

							}
							endP++;
						}
						startP++;
					}
					
					
	/*				Bufkey.delete(0, Bufkey.length());
					return null;*/
					
					
					//�ҵ�Ԫ�� û�ҵ����� ��Ӹ�Ԫ���µ�����mode
					if(index!=null)
					{
						for(contentAttrs cA:conAttrs)
						{
							for(HashMap<String,String> op:cA.operator)
							{
								this.queryFailed.add(op.get("ModeID"));
							}
						}
					}
					return getMode(Bufkey);
					
				}
				endPos++;
			}
			startPos++;
		}
		
		Bufkey.delete(0, Bufkey.length());

		
		
		return "";
	}
	
	//��content�ļ������ڴ� �浽 contentAttrs��LinkedList��
	private void ReadContentInMem(JsonParser conPar,LinkedList<contentAttrs>  conAttrsIn) throws JsonParseException, IOException
	{
		
		conPar.nextToken();
		while(conPar.nextToken()!=JsonToken.END_ARRAY)
		{
			contentAttrs attrPut=new contentAttrs();
			conPar.nextToken();  //attr name
			conPar.nextToken();	//attr value
			attrPut.attr= conPar.getText();
			conPar.nextToken();//operator name
			conPar.nextToken();// [
			
			while(conPar.nextToken()!=JsonToken.END_ARRAY)//operator������û������
			{
				HashMap<String,String> OpVal=new HashMap<String,String>();
				while(conPar.nextToken()!=JsonToken.END_OBJECT)//ÿ��operator��Ԫ�صĸ���û������
				{
					if(conPar.getCurrentToken()==JsonToken.VALUE_STRING)
					{
						OpVal.put(conPar.getCurrentName(), conPar.getText());		
					}
				}
				attrPut.operator.add(OpVal);
			}
			conAttrsIn.add(attrPut);
			conPar.nextToken();// }
		}
	}
	
	
	//����key ����һ��attr������ ���� ���� 4 m��
	private contentAttrs getAttr(LinkedList<contentAttrs> attrs ,String key)
	{
		for(contentAttrs attr:attrs)
		{
			if(attr.attr.equals(key))
			{
				return attr;
			}
		}
		return null;
	}
	
	//����ƥ�䵽�Ĳ�����  ����һ��operation(����  4 m)   key���ڴ���ʣ�µĲ�ѯ��
	private HashMap<String,String> operationFromAttr(LinkedList<HashMap<String,String>> attrs,StringBuffer key)	
	{
		int startPos=0,endPos=0;
		int length=key.length();
		
		HashMap<String,String> curAttr = new HashMap<String,String>();//����ģ�͵Ĺ�ϣmap
		
		
		//��ö�Ӧ��ģ��
		while (startPos < length) 
		{
			endPos = startPos + 1;
			while (endPos <= length) 
			{
				String curStr = key.substring(startPos, endPos);
				for(HashMap<String,String> attr:attrs)
				{
					if(attr.get("name").equals(curStr))
					{
						Iterator<Entry<String, String>> iter = attr.entrySet().iterator();
						while (iter.hasNext()) 
						{
							Entry<String,String> entry = (Entry<String,String>) iter.next();
							curAttr.put(entry.getKey(), entry.getValue());
						}
						curAttr.remove("name");
						key.delete(startPos, endPos);
						//key=key.substring(0, startPos)+key.substring(endPos,length);

						return curAttr;
					}	
				}
				endPos++;
			}
			startPos++;
		}
		
		return null;
		
		
		
	}
		
	private String modeFromAttr(LinkedList<HashMap<String,String>> attrs,StringBuffer key)
	{
		//StringBuffer keyBuf=new StringBuffer(key);
		HashMap<String,String> attr= operationFromAttr(attrs,key);
		//String curStr= keyBuf.toString();//���ʣ�µĲ�ѯ��
		
		
		//û�ҵ������� �����в�����������
		if(attr==null)
		{
			for(HashMap<String,String> op:attrs)
			{
				this.queryFailed.add(op.get("ModeID"));
			}
			return null;
			
			
			
		}
		
		int startPos=0,endPos=1;
		int length=key.length();
		String num = "";
		String curKey;
		
		//��ȡ����
		switch (attr.get("operand")) {
		case "Num":
			
			for (int i = 0; i < length; i++) {
				if (key.charAt(i) >= 48 && key.charAt(i) <= 57) {
					num += key.charAt(i);
					if (i + 1 < length) {
						if (!(key.charAt(i+1) >= 48 && key.charAt(i+1) <= 57)) {
							break;
						}
					}
				}

			}
			if(num!=""){
				key.indexOf(num);
				key.replace(key.indexOf(num), key.indexOf(num)+num.length(), "");
				attr.remove("operand");
				}
			else{
				
				//�Ѿ��ҵ������� ֻ����ӵ�ǰһ��
				this.queryFailed.add(attr.get("ModeID"));
				return null;
			}
		}
		
		
		
		length=key.length();
		
		
		while(startPos<length)
		{
			endPos=startPos+1;
			while(endPos<=length)
			{
				curKey=key.substring(startPos,endPos);
				if(curKey.equals(attr.get("unit")))
				{
					key.replace(key.indexOf(curKey), key.indexOf(curKey)+curKey.length(), "");
					attr.remove("operand");
					return attr.get("ModeID")+" "+num;
				}
				
				endPos++;
			}
			
			startPos++;
		}
		
		
		
		//�Ѿ��ҵ������� ֻ����ӵ�ǰһ��
		this.queryFailed.add(attr.get("ModeID"));
		
		
		return null;
	}
	
	
	public ArrayList<String> queryFailedInfo(String fileIn) throws com.fasterxml.jackson.core.JsonParseException, JsonMappingException, IOException
	{
		ArrayList<String> res=new ArrayList<String>();
		
		
		ObjectMapper mapper = new ObjectMapper();
	
		File jsonfile=new File(fileIn+"queryError.json");
		LinkedHashMap<String,Object> element = mapper.readValue(jsonfile, LinkedHashMap.class);
		
		for(String id :queryFailed)
		{
			res.add((String)element.get(id));
		}
		
		
		
		
		return res;
	}
	
	
	
	public ArrayList<String> queryFailed;//��ѯʧ�ܺ󷵻�
	private String filePath;//����ʿ�index�ļ��������ļ���λ��
	private JsonParser parser;//index�ļ�
	private JsonFactory facttory;//index�ļ�
	private JsonParser conPar;//content�ļ�
	private JsonFactory contentfacttory;//content�ļ�
	private boolean indexInMem=false;//�Ƿ�index�ļ������ڴ�
	private HashMap<String,String> indexMap;//��index�����ڴ�
}
