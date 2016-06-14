/**
 * 
 */
package edu.tongji.webgis.svg.ivtd;

/**
 * @author Administrator
 *
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class IVTDGen {

	private File f;
	private FileInputStream fis;
	private byte b[];
	String str = "";
	int depth = 0;

	private int docLength = 0;// 整个XML中的有效字符的总长度
	private int VTDDepth = -1; // 最大深度,start from 0
	private int currentDepth = -1; // 遍历过程中当前元素深度
	private int currentTokenOffset = 0;// 当前标记在doc中的开始位置
	private int rootIndex = 0; // 根在VTD数组中的标号
	private int maxTokenLength = 0;
	private int VTDLength = 0; // VTD数组大小
	private int LCLevelCount[] = new int[50];// 每一层LC数组的个数，从第0层开始
	private int LCCount = 0;

	private int VTD[][];
	private int LC[][];
	private int VTDIndex = 0;
	private int LCIndex = 0;
	private int LCLevelNum[] = new int[100];// 每一层LC数组的当前元素的下标
	private int CO[];

	public IVTDGen() {
	}

	public void setDoc(byte b[]) {
		this.b = b;
	}

	public void printXPath(int LCIndex) {
		if (LCIndex < 0)
			return;
		printXPath(LC[LCIndex][2]);
		String str = "";
		str += new String(b, VTD[LC[LCIndex][0]][3], VTD[LC[LCIndex][0]][2]);
		for (int i = LC[LCIndex][0] + 1;; i++) {
			if (i == VTDLength || VTD[i][0] == 0) {
				break;
			} else {
				str += new String(b, VTD[i][3], VTD[i][2]);
			}
		}// for i
		System.out.println(str);
	}

	/**
	 * 
	 * @param LCIndex
	 * @return 获得元素的XPath
	 */
	public String getPosition(int LCIndex) {
		String str = "";
		// int index = LCIndex;
		int tCO[] = new int[VTDDepth + 1];
		int m = 0;
		while (LCIndex >= 0) {
			tCO[m] = LCIndex;
			LCIndex = LC[LCIndex][2];
			m++;
		}
		if (m > 0)
			str += "/*[1]";
		for (int i = m - 2; i >= 0; --i) {
			str += "/*[" + (tCO[i] - LC[tCO[i + 1]][1]+1) + "]";
		}
		return str;
	}

	/**
	 * 
	 * @param fLC
	 *            当前元素在LCs中的标号
	 * @param depth
	 *            当前元素的深度
	 * @param tab
	 *            当前元素的缩进
	 */
	public void printLevelElement(int fLC, int depth, String tab) {
		String tmptab = tab;
		String ele = tmptab + "<";
		for (int k = VTD[LC[fLC][0]][3]; k < VTD[LC[fLC][0]][3]
				+ VTD[LC[fLC][0]][2]; k++)
			ele += (char) b[k];
		ele += " ";

		for (int i = LC[fLC][0] + 1;; i++) {
			if (i == VTDLength || VTD[i][0] == 0) {
				ele += ">";
				break;
			} else if (VTD[i][0] == 5) {
				ele += ">";
				for (int k = VTD[i][3]; k < VTD[i][3] + VTD[i][2]; k++)
					ele += (char) b[k];
				break;
			} else {
				ele += " ";
				for (int k = VTD[i][3]; k < VTD[i][3] + VTD[i][2]; k++)
					ele += (char) b[k];
			}
		}
		System.out.println(ele);
		tab += "     ";
		int k = 1;
		for (int i = LC[fLC][1];;) {
			if (i == 0)
				break;
			if (fLC == LCLevelNum[depth] - 1) {// fLC为该层的最后一个节点
				if (i < LCLevelNum[depth + 1]) {
					printLevelElement(i++, depth + 1, tab);
				} else {
					break;
				}
			} else if (LC[fLC + k][1] != 0) {// 同一层的后一个节点有子节点
				if (i < LC[fLC + k][1]) {
					printLevelElement(i++, depth + 1, tab);
				} else {
					break;
				}
			} else {// 同一层的后一个节点无子节点
				if (fLC + k < LCLevelNum[depth] - 1) {
					k++;
				} else {
					if (i < LCLevelNum[depth + 1]) {
						printLevelElement(i++, depth + 1, tab);
					} else {
						break;
					}
				}
			}
		}
		System.out.println(tmptab + "</>");
	}

	public void printInfo() {
		System.out.println("VTDDepth:0~" + VTDDepth);
		System.out.println("VTDLength:" + VTDLength);
		System.out.println("LCCount:" + LCCount);
		System.out.println("maxTokenLength:" + maxTokenLength);
		for (int i = 0; i <= VTDDepth; i++) {
			System.out.println("level " + i + ":" + LCLevelCount[i]);
		}
	}

	public void parse() {
		Statistics();
		// printInfo();
		Generate();
	}

	private void startElement(int currentDepth, int qNamelen, int peri) {
		VTD[VTDIndex][0] = 0;
		VTD[VTDIndex][1] = currentDepth;
		VTD[VTDIndex][2] = qNamelen;
		VTD[VTDIndex][3] = peri;
		int currentLCLevelNum = LCLevelNum[currentDepth];
		LC[currentLCLevelNum][0] = VTDIndex;
		if (CO[0] >= 0) {
			/**
			 * CO[0] + 1 : CO中最后一个元素 CO[CO[0] + 1] ：CO中最后一个元素在LC数组中的位置
			 * LC[CO[CO[0] + 1]][0]：LC元素在VTD数组中的位置 VTD[LC[CO[CO[0] + 1]][0]][1]
			 * ： VTD元素的嵌套深度
			 */
			int lastCODepth = VTD[LC[CO[CO[0] + 1]][0]][1];
			if (currentDepth > lastCODepth) {
				/** 当前节点的嵌套深度大于上一个节点的嵌套深度，则，当前节点是上一个节点的第一个子节点 */
				LC[CO[CO[0] + 1]][1] = currentLCLevelNum;
				LC[currentLCLevelNum][2] = CO[CO[0] + 1];
				// CO[0]++;
				CO[0] = currentDepth;
				CO[CO[0] + 1] = currentLCLevelNum;
			} else if (currentDepth < lastCODepth) {//
				/** 当前节点的嵌套深度小于上一个节点的嵌套深度 */
				// CO[0]--;
				CO[0] = currentDepth;// 可能深度不是依次下降的，例如可能从深度4下降到1
				LC[currentLCLevelNum][2] = LC[CO[CO[0] + 1]][2];
				CO[CO[0] + 1] = currentLCLevelNum;
			} else {
				/** 当前节点的嵌套深度等于上一个节点的嵌套深度，则当前节点是上一节点的兄弟节点 */
				LC[currentLCLevelNum][2] = LC[CO[CO[0] + 1]][2];
				CO[CO[0] + 1] = currentLCLevelNum;
			}
		} else {// top level
			CO[0] = 0;
			CO[CO[0] + 1] = currentLCLevelNum;
			LC[currentLCLevelNum][2] = -1;
		}
		LCLevelNum[currentDepth]++;
		VTDIndex++;
	}

	private void startAttribute(int currentDepth, int attrlen, int peri) {
		VTD[VTDIndex][0] = 2; // Token Type
		VTD[VTDIndex][1] = currentDepth; // Nesting depth
		VTD[VTDIndex][2] = attrlen; // QName Length
		VTD[VTDIndex][3] = peri;// Token Starting offset
		VTDIndex++;
	}

	private void startText(int currentDepth, int textLen, int peri) {
		VTD[VTDIndex][0] = 5; // Token Type
		VTD[VTDIndex][1] = currentDepth; // Nesting depth
		VTD[VTDIndex][2] = textLen; // QName Length
		VTD[VTDIndex][3] = peri;// Token Starting offset
		VTDIndex++;
	}

	private int startElementBody(byte b[], int i) {
		currentDepth++;
		int tokenType = 0;
		int qNamelen = 0;
		int attrlen = 0;
		int peri = i;
		//Vendor Edit, Jaki 2016-06-02
		boolean setStartElement = false;
		//
		// boolean elemEnd = false;
		byte quotStack[] = new byte[11];// quotation nest eg. id='"a'b'c"dd'
		for (;;) {
			boolean bk = false;
			switch (b[i]) {
			case 13:
			case ' ':
			case '\n':
			case '\t':
				// element token is end and the attributes start
				startElement(currentDepth, qNamelen, peri);
				setStartElement = true;
				int quotStackIndex = 0;
				for (;;) {
					int m = 0;
					i++;
					switch (b[i]) {
					case 13:
						break;
					case ' ':
					case '\n':
					case '\t':
						if (quotStackIndex > 0) {
							// blank in the single or double quotation mark
							attrlen++;
							continue;
						} else {
							if (attrlen > 0) {
								startAttribute(currentDepth, attrlen, peri);
							}

							attrlen = 0;
							quotStackIndex = 0;
							quotStack[quotStackIndex] = 0;
							continue;
						}
					case '\'':
						if (quotStackIndex == 0 || quotStackIndex > 0
								&& quotStack[quotStackIndex - 1] != '\'') {
							// first single quotation
							quotStack[quotStackIndex++] = '\'';
						} else {// the second single quotation
							quotStackIndex--;
							if (quotStackIndex == 0) {
								attrlen++;
								startAttribute(currentDepth, attrlen, peri);
								attrlen = 0;
								continue;
							}
						}
						attrlen++;
						continue;
					case '\"':
						if (quotStackIndex == 0 || quotStackIndex > 0
								&& quotStack[quotStackIndex - 1] != '\"') {
							// first double quotation mark
							quotStack[quotStackIndex++] = '\"';
						} else {// second double quotation mark
							quotStackIndex--;
							if (quotStackIndex == 0) {
								attrlen++;
								startAttribute(currentDepth, attrlen, peri);
								attrlen = 0;
								continue;
							}
						}
						attrlen++;
						continue;
					case '/':
						if (quotStackIndex > 0) {
							// a part of attribute' value
							attrlen++;
						} else {
							if (attrlen > 0) {
								startAttribute(currentDepth, attrlen, peri);
							}
							// i--;
							attrlen = 0;
							m = 1;
						}
						break;
					case '>':
						if (attrlen > 0) {
							startAttribute(currentDepth, attrlen, peri);
						}
						// i--;
						attrlen = 0;
						m = 1;
						break;
					default:
						attrlen++;
						if (attrlen == 1)
							peri = i;
						if (tokenType == 0)
							tokenType = 2;
					}
					if (m == 1) {
						bk = true;
						break;
					}
				}// for
				tokenType = -1;
				continue;
			case '/':
				if (b[i + 1] == '>')
					// /> element end
					i--;
				tokenType = -1;
				bk = true;
				if(!setStartElement){
					startElement(currentDepth, qNamelen, peri);
				}
				break;
			case '>':// >a text start
				if (tokenType == 0) {
					startElement(currentDepth, qNamelen, peri);
					setStartElement = true;
				}
				tokenType = -1;
				int textLen = 0;
				for (;;) {
					i++;
					if (tokenType == -1
							&& (b[i] == ' ' || b[i] == '\n' || b[i] == '\t' || b[i] == 13)) {
						continue;
					} else if (b[i] == '<') {// a< text end
						i--;
						int ti = i;
						for (;;) {
							if (b[ti] == ' ' || b[ti] == '\n' || b[ti] == '\t'
									|| b[ti] == 13) {
								textLen--;
							} else {
								break;
							}
							ti--;
						}
						break;
					} else {
						tokenType = 5;
						textLen++;
						if (textLen == 1)
							peri = i;
					}
				}
				if (textLen > 0) {
					startText(currentDepth, textLen, peri);
				}
				tokenType = -1;
				bk = true;
				break;
			default:
				// not special char ,a part of element
				qNamelen++;
				++i;
			}
			if (bk)
				break;
		}

		return i;
	}

	private void Generate() {
		VTD = new int[VTDLength][4];
		LC = new int[LCCount][3];
		// current VTD,first child LC, father LC
		CO = new int[VTDDepth + 2];
		LCLevelNum[0] = 0;
		CO[0] = -1;
		int i = 1;
		for (i = 1; i <= VTDDepth; i++) {
			LCLevelNum[i] = LCLevelNum[i - 1] + LCLevelCount[i - 1];
		}
		LCLevelNum[i] = LCLevelNum[i - 1] + 1;
		int len = b.length;
		byte last = 0;
		for (i = 0; i < len; i++) {
			switch (b[i]) {
			case '<':
				last = '<';
				continue;
			case '/':
				if (last == '<') {// </
					for (;;) {
						++i;
						if (b[i] == '>') {// </....>
							break;
						}
						if (b[i] == ' ' || b[i] == '\n' || b[i] == '\t') {
							continue;
						}
					}
					currentDepth--;
					last = 0;
					continue;
				}
				last = '/';
				continue;
			case '>':
				if (last == '/') {
					last = 0;
					currentDepth--;
				}
				break;
			case '!':
			case '?':
				last = 0;
				break;
			default:
				if (last == '<') {// <a element start
					i = startElementBody(b, i);
					last = 0;
				} else if (last == '/') {
					last = 0;
				}
				break;
			}// if(last=='<'){//<a element start
		}// for
	}


	public void Statistics() {
		int len = b.length;
		int tokenType = -1;
		//用来记录当前标记的类型
		byte last = 0;
		//上一个字符
		byte quotStack[] = new byte[11];
		//引号嵌套 例如 id='"a'b'c"dd'
		docLength = b.length;
		for (int i = 0; i < len; i++) {
			switch (b[i]) {
			case '<':
			//一.当前字符是'<',可能是元素标签的开始
				last = '<';
				continue;
			case '/':
			//二.当前字符是'/'
				if (last == '<') {
				//如果上一个字符是‘<’，则是结束标签的开始，即， </....>
					for (;;) {
						++i;
						if (b[i] == '>') {// </....>
							break;
						}
						if (b[i] == ' ' || b[i] == '\n' || b[i] == '\t') {
							continue;
						}
					}
					currentDepth--;
					last = 0;
					continue;
				}
				last = '/';
				continue;
			case '>':
			//三.当前字符是'>'
				if (last == '/') {
				//如果上一个字符是'/',则是一个标签的结束，即，'<.../>'
					last = 0;
					currentDepth--;
				}
				break;
			case '!':
			//四.当前字符是'!'
			case '?':
			//五.当前字符是'?'
				last = 0;
				break;
			default:
			//六.当前字符是其他非特殊字符
				if (last == '<') {
				//6.1 上一个字符是'<',则元素开始，例如，当前字符是s，则是'<s'
					tokenType = 0;
					int qNamelen = 0;
					int attrlen = 0;
					for (;;) {
						if (b[i] == ' ' || b[i] == '\n' || b[i] == 13
								|| b[i] == '\t') {
						// 6.1.1 元素标记结束，该元素的属性开始
							int quotStackIndex = 0;
							int first = 0;
							for (;;) {
								int m = 0;
								i++;
								switch (b[i]) {
								case 13: // 回车
									break;
								case ' ':
								case '\n':
								case '\t':
								// blank in the single or double quotation mark
									if (quotStackIndex > 0) {
										attrlen++;
										continue;
									} else {
										if (maxTokenLength < attrlen) {
											maxTokenLength = attrlen;
										}
										if (tokenType == 2 && attrlen > 0)
											VTDLength++;
										attrlen = 0;
										quotStackIndex = 0;
										quotStack[quotStackIndex] = 0;
										continue;
									}
								case '\'':
									if (quotStackIndex == 0
											|| quotStackIndex > 0
											&& quotStack[quotStackIndex - 1] != '\'') {
								    // first single quotation
										quotStack[quotStackIndex++] = '\'';
									} else {// the second single quotation
										quotStackIndex--;
										if (quotStackIndex == 0) {
											attrlen++;
											if (maxTokenLength < attrlen) {
												maxTokenLength = attrlen;
											}
											// attrlen=0;
											continue;
										}
									}
									attrlen++;
									continue;
								case '\"':
									if (quotStackIndex == 0
											|| quotStackIndex > 0
											&& quotStack[quotStackIndex - 1] != '\"') {
									// first double quotation mark
										quotStack[quotStackIndex++] = '\"';
									} else {// second double quotation mark
										quotStackIndex--;
										if (quotStackIndex == 0) {
											attrlen++;
											if (maxTokenLength < attrlen) {
												maxTokenLength = attrlen;
											}
											// attrlen=0;
											continue;
										}
									}
									attrlen++;
									continue;
								case '/':
									if (quotStackIndex > 0) {
									// a part of attribute' value
										attrlen++;
									} else {
										if (maxTokenLength < attrlen) {
											maxTokenLength = attrlen;
										}
										if (tokenType == 2 && attrlen > 0)
											VTDLength++;
										// i--;
										attrlen = 0;
										m = 1;
									}
									break;
								case '>':
									if (maxTokenLength < attrlen) {
										maxTokenLength = attrlen;
									}
									// i--;
									attrlen = 0;
									m = 1;
									if (tokenType == 2)
										VTDLength++;
									break;
								default:
									attrlen++;
									if (tokenType == 0)
										tokenType = 2;
								}
								if (m == 1)
									break;
							}// for
							tokenType = -1;
							continue;
						} else if (b[i] == '/' && b[i + 1] == '>') { // />
						//6.1.2 元素结束，'/>'
							i--;
							tokenType = -1;
							break;
						} else if (b[i] == '>') {
						//6.1.3 该元素的文本开始 
							tokenType = -1;
							int textLen = 0;
							for (;;) {
								i++;
								if (tokenType == -1
										&& (b[i] == ' ' || b[i] == '\n'
												|| b[i] == '\t' || b[i] == 13)) {
									continue;
								} else if (b[i] == '<') {// a< text end
									i--;
									int ti = i;
									for (;;) {
										if (b[ti] == ' ' || b[ti] == '\n'
												|| b[ti] == '\t' || b[ti] == 13) {
											textLen--;
										} else {
											break;
										}
										ti--;
									}
									break;
								} else {
									tokenType = 5;
									textLen++;
								}
							}
							if (maxTokenLength < textLen) {
								maxTokenLength = textLen;
							}
							if (textLen > 0)
								VTDLength++;
							tokenType = -1;
							break;
						}// if(b[i]=='>')
						else {// not special char ,a part of element
							qNamelen++;
							++i;
						}
					}
					last = 0;
					currentDepth++;
					VTDLength++;
					if (VTDDepth < currentDepth) {
						VTDDepth = currentDepth;
					}
					LCLevelCount[currentDepth]++;
					LCCount++;
					if (maxTokenLength < qNamelen) {
						maxTokenLength = qNamelen;
					}
				} else if (last == '/') {
				//6.2 元素结束标签的开始,例如，当前字符是s，'</s..'
					last = 0;
				}
				break;
			}// if(last=='<'){//<a element start
		}// for
	}//

	public File getF() {
		return f;
	}

	public FileInputStream getFis() {
		return fis;
	}

	public byte[] getB() {
		return b;
	}

	public String getStr() {
		return str;
	}

	public int getDepth() {
		return depth;
	}

	public int getDocLength() {
		return docLength;
	}

	public int getVTDDepth() {
		return VTDDepth;
	}

	public int getCurrentDepth() {
		return currentDepth;
	}

	public int getCurrentTokenOffset() {
		return currentTokenOffset;
	}

	public int getRootIndex() {
		return rootIndex;
	}

	public int getMaxTokenLength() {
		return maxTokenLength;
	}

	public int getVTDLength() {
		return VTDLength;
	}

	public int[] getLCLevelCount() {
		return LCLevelCount;
	}

	public int getLCCount() {
		return LCCount;
	}

	public int[][] getVTD() {
		return VTD;
	}

	public int[][] getLC() {
		return LC;
	}

	public int getVTDIndex() {
		return VTDIndex;
	}

	public int getLCIndex() {
		return LCIndex;
	}

	public int[] getLCLevelNum() {
		return LCLevelNum;
	}

	public int[] getCO() {
		return CO;
	}
}
