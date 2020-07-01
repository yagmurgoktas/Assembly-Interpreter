import java.util.*;


public class Program {
	
	public static int lastcmp;
	public static String memory[]; 
	public boolean ZF;
	public boolean CF;
	public boolean AF;
	public boolean SF;
	public boolean OF;
	public static Reg AX;
	public static Reg BX;
	public static Reg CX;
	public static Reg DX;
	public static Reg AH;
	public static Reg BH;
	public static Reg CH;
	public static Reg DH;
	public static Reg AL;
	public static Reg BL;
	public static Reg CL;
	public static Reg DL;
	public static Reg BP;
	public static Reg SI;
	public static Reg DI;
	public String SPhex="FFFE";
	public int SP=65534;
	public ArrayList<String> labels;
	public ArrayList<Integer> labelIndex;
	public static ArrayList<Variable> variables;
	
	static {
		memory = new String[65535];
		AX = new Reg("AX");
		BX = new Reg("BX");
		CX = new Reg("CX");
		DX = new Reg("DX");
		AH = new Reg("AH");
		BH = new Reg("BH");
		CH = new Reg("CH");
		DH = new Reg("DH");
		AL = new Reg("AL");
		BL = new Reg("BL");
		CL = new Reg("CL");
		DL = new Reg("DL");
		BP = new Reg("BP");
		SI = new Reg("SI");
		DI = new Reg("DI");
		variables = new ArrayList<Variable>();
	}
	
	public Program() {
		this.labels = new ArrayList<>();
		this.labelIndex = new ArrayList<>();
	
		
	}
	
	public void INT21H() {
		String x = AH.content;
				
		if(x.equalsIgnoreCase("01h") || x.equals("1")) {
			Scanner scan = new Scanner(System.in);
			String s = scan.nextLine();
			scan.close();
			char input = s.charAt(0);
			String inp = "";
			AL.content = inp+input;
		} else if(x.equalsIgnoreCase("02h") || x.equals("2")) {
			int y = getRegContent("DL");
			char c = (char) y;
			System.out.println(c);
		} else {
			return;
		}
	}
	
	public int getVarContent(String name) {
		int content = 0;
		for(int i=0;i<variables.size();i++) {
			if(variables.get(i).name.equals(name)) {
				content = variables.get(i).content;
				break;
			}
		}
		return content;
	}
	
	public int getIndex(String s) {
		int a;
		if(s.startsWith("w[") || s.startsWith("W[") || s.startsWith("b[") || s.startsWith("B[")) {
			s = s.substring(2,s.length()-1);
			a = Integer.parseInt(s,16);
		} else {
			s = s.substring(1,s.length()-1);
			a = Integer.parseInt(s,16);
		}	
			return a;
	}
	
	public int getSourceContent(String source, int sourceType) {
		//SOURCE IS A REG
		if(sourceType == 1) {
			return getRegContent(source);
				
		//SOURCE IS A MEMORY OFFSET
		} else if(sourceType == 2) {
			return getAddress(source);
			
		//SOURCE IS A HEX
		} else if(sourceType == 3) {
			if(source.endsWith("h") || source.endsWith("H")) {
				return Integer.parseInt(source.substring(0, source.length() - 1), 16);
			} else {
				return Integer.parseInt(source, 16);
			}
		//SOURCE IS AN INT	
		} else if(sourceType == 4) {
			if(source.endsWith("d") || source.endsWith("d")) {
				return Integer.parseInt(source.substring(0, source.length() - 1));
			} else {
				return Integer.parseInt(source);
			}
		//SOURCE IS AN OFFSETVAR
		} else if(sourceType == 5) {
			String varName = source.substring(6);
			for(int i=0;i<variables.size();i++) {
				if(variables.get(i).name.equals(varName)){
					return variables.get(i).address;
				}
			}
			
			
		//SOURCE IS A CHAR
		} else if(sourceType == 6) {
			return source.charAt(1);
		
		//SOURCE IS A VARIABLE
		} else if(sourceType == 7) {
			return getVarContent(source);
		}
		
		return 0;
	}
	
	public void MOV(String destination,String source, boolean error) {

			int sourceType = typeCheck(source);
			int sourceContent = 0;
		/*
			//SOURCE IS A REG
			if(sourceType == 1) {
				sourceContent = getRegContent(source);
					
			//SOURCE IS A MEMORY OFFSET
			} else if(sourceType == 2) {
				sourceContent = getAddress(source);
					
			//SOURCE IS A HEX
			} else if(sourceType == 3) {
				if(source.endsWith("h") || source.endsWith("H")) {
					sourceContent = Integer.parseInt(source.substring(0, source.length() - 1), 16);
				} else {
					sourceContent = Integer.parseInt(source, 16);
				}
			//SOURCE IS AN INT	
			} else if(sourceType == 4) {
				if(source.endsWith("d") || source.endsWith("d")) {
					sourceContent = Integer.parseInt(source.substring(0, source.length() - 1));
				} else {
					sourceContent = Integer.parseInt(source);
				}	
			//SOURCE IS AN OFFSETVAR
			} else if(sourceType == 5) {
				///variable adresini bulup tutmali
				sourceContent = -1;	//burada contenti stringe dondurmemek icin -1 yolladim pointer yolladigimi anlamasi icin
			
				//SOURCE IS A CHAR
			} else if(sourceType == 6) {
				sourceContent = source.charAt(1);
			
			//source is a variable
			} else if(sourceType == 7) {
				//////////source sadece var adi ise
				sourceContent = getVarContent(source);
			}
				*/
			sourceContent=getSourceContent(source,sourceType);
			int destType = typeCheck(destination);
			//destination is a reg
			
			 
			if(destType == 1) {
					
				//source bir pointersa (offset var)
				
					
					/* String pointer = "";
					String varName = source.substring(6); //// basinda offset yazdigi icin onu trimleyip sadece variable adini aldim
					int x = variables.size();
					for(int i = 0; i < x; i++) {
						if(variables.get(i).name.equals(varName))
							pointer = variables.get(i).address + ""; //variablein tuttugu int adresi hex cevirdim
					}															//sonradan reg icinde hex gorup onu adress olarak alsin diye
					*/
			
				
				
				//source 8 bitse
				  if( sourceContent >= 0) {
					
					
						
					if(destination.equalsIgnoreCase("ah")) {
						AH.content = sourceContent+"";
						this.updateBigReg(AX, AH, AL);
					} else if(destination.equalsIgnoreCase("bh")) {
						BH.content = sourceContent+"";
						this.updateBigReg(BX, BH, BL);
					} else if(destination.equalsIgnoreCase("ch")) {
						CH.content = sourceContent+"";
						this.updateBigReg(CX, CH, CL);
					} else if(destination.equalsIgnoreCase("dh")) {
						DH.content = sourceContent+"";
						this.updateBigReg(DX, DH, DL);
					} else if(destination.equalsIgnoreCase("al")) {
						AL.content = sourceContent+"";
						this.updateBigReg(AX, AH, AL);
					} else if(destination.equalsIgnoreCase("bl")) {
						BL.content = sourceContent+"";	
						this.updateBigReg(BX, BH, BL);
					} else if(destination.equalsIgnoreCase("cl")) {
						CL.content = sourceContent+"";	
						this.updateBigReg(CX, CH, CL);
					} else if(destination.equalsIgnoreCase("dl")) {
						DL.content = sourceContent+"";
						this.updateBigReg(DX, DH, DL);
					}
					
				//source 16 bitse
				
					
					
					
					if(destination.equalsIgnoreCase("ax")) {
						AX.content = sourceContent+"";
						this.updateSmallReg(AX, AH, AL);
					} else if(destination.equalsIgnoreCase("bx")) {
						BX.content = sourceContent+"";
						this.updateSmallReg(BX, BH, BL);
					} else if(destination.equalsIgnoreCase("cx")) {
						CX.content = sourceContent+"";
						this.updateSmallReg(CX, CH, CL);
					} else if(destination.equalsIgnoreCase("dx")) {
						DX.content = sourceContent+"";
						this.updateSmallReg(DX, DH, DL);
					} else if(destination.equalsIgnoreCase("bp")) {
						BP.content = sourceContent+"";
					} else if(destination.equalsIgnoreCase("si")) {
						SI.content = sourceContent+"";
					} else if(destination.equalsIgnoreCase("di")) {
						DI.content = sourceContent+"";
					} 
				
				  }	
			//destination is an address	
			} else if(destType == 2) {
				boolean word = false;
				if(destination.startsWith("w[") || destination.startsWith("W["))
					word = true;
				
				int memoryIndex = getIndex(destination);
				
				////source da adress ise hata vermeli
				if(sourceType == 2) {
					error = true;
					System.out.println("error");
					return;
						
				//source is a hex
				} 
				String s = Integer.toHexString(sourceContent);
					
					if(word) {
						if(sourceContent > 256) {
							memory[memoryIndex+1] = s.substring(0,2); //hex de ilk iki karakteri almali
							memory[memoryIndex] = s.substring(2);
						} else {
							memory[memoryIndex+1] = Integer.toHexString(0);
							memory[memoryIndex] = s;
						}
					//if byte
					} else {
						if(sourceContent > 256) {
							error = true;
							System.out.println("error");
							return;
						}
					}
				
			//destination is a variable
			} else if(destType == 7) {
				for(int i=0;i<variables.size();i++) {
					if(variables.get(i).name.equals(destination)) {
						int varSize = variables.get(i).size;
						if((varSize == 1 && sourceContent < 256) || varSize == 2 && sourceContent < Math.pow(2, 16)) {
							variables.get(i).content=sourceContent;
						} else {
							error = true;
							System.out.println("error");
							return;
						}
						break;
					}
				}
			} else {
				error = true;
				System.out.println("error");
				return;
			}
	}
		
	public void ADD(String destination,String source, boolean error) {
		SF=false;
		int sourceType = typeCheck(source);
		int sourceContent = getSourceContent(source, sourceType);
		boolean addressWord = false;
		
		if(sourceType == 2 && (source.startsWith("w") || source.startsWith("W")))
			addressWord = true;
		
		
		int destType=typeCheck(destination);
		//destination is a reg
		if(destType == 1) {
			//8 bit reg + w[] error
			if(addressWord) {	
				if(destination.equalsIgnoreCase("ah")||destination.equalsIgnoreCase("al")||destination.equalsIgnoreCase("bh")||destination.equalsIgnoreCase("bl")
					||destination.equalsIgnoreCase("ch")||destination.equalsIgnoreCase("cl")||destination.equalsIgnoreCase("dh")||destination.equalsIgnoreCase("dl")) {
					
					error = true;
					System.out.println("error");
					return;
				}
			}
			
			if(sourceType == 2 && source.startsWith("[") && source.endsWith("]")) {
				if(destination.equalsIgnoreCase("ax")||destination.equalsIgnoreCase("bx")||destination.equalsIgnoreCase("cx")||destination.equalsIgnoreCase("dx")
						||destination.equalsIgnoreCase("bp")||destination.equalsIgnoreCase("si")||destination.equalsIgnoreCase("di")) {
					
					sourceContent = getAddress("w" + source);
				}
			}
			
			if(sourceContent > 256) {	
				if(destination.equalsIgnoreCase("ah")||destination.equalsIgnoreCase("al")||destination.equalsIgnoreCase("bh")||destination.equalsIgnoreCase("bl")
						||destination.equalsIgnoreCase("ch")||destination.equalsIgnoreCase("cl")||destination.equalsIgnoreCase("dh")||destination.equalsIgnoreCase("dl")) {
						error = true;
						System.out.println("error");
						return;
					}
			}
			
				if(destination.equalsIgnoreCase("ah")) {
					int a=getRegContent("AH")+ sourceContent;
					if(a==0) {
						ZF=true;
					}else if(a>=256) {
						CF=true;
						a=a%256;
					} else {
						CF=false;
					}
					AH.content =a+"";
					this.updateBigReg(AX, AH, AL);
				} else if(destination.equalsIgnoreCase("bh")) {
					int a=getRegContent("BH")+ sourceContent;
					if(a==0) {
						ZF=true;
					}else if(a>=256) {
						CF=true;
						a=a%256;
					} else {
						CF=false;
					}
					BH.content =a+"";
					this.updateBigReg(BX, BH, BL);
				} else if(destination.equalsIgnoreCase("ch")) {
					int a=getRegContent("CH")+ sourceContent;
					if(a==0) {
						ZF=true;
					} else if(a>=256) {
						CF=true;
						a=a%256;
					} else {
						CF=false;
					}
					CH.content =a+"";
					this.updateBigReg(CX, CH, CL);
				} else if(destination.equalsIgnoreCase("dh")) {
					int a=getRegContent("DH")+ sourceContent;
					if(a==0) {
						ZF=true;
					}else if(a>=256) {
						CF=true;
						a=a%256;
					} else {
						CF=false;
					}
					DH.content = a+"";
					this.updateBigReg(DX, DH, DL);
				} else if(destination.equalsIgnoreCase("al")) {
					int a=getRegContent("AL")+ sourceContent;
					if(a==0) {
						ZF=true;
					}else if(a>=256) {
						CF=true;
						a=a%256;
					} else {
						CF=false;
					}
					AL.content = a+"";
					this.updateBigReg(AX, AH, AL);
				} else if(destination.equalsIgnoreCase("bl")) {
					int a=getRegContent("BL")+ sourceContent;
					if(a==0) {
						ZF=true;
					}else if(a>=256) {
						CF=true;
						a=a%256;
					} else {
						CF=false;
					}
					BL.content = a+"";	
					this.updateBigReg(BX, BH, BL);
				} else if(destination.equalsIgnoreCase("cl")) {
					int a=getRegContent("CL")+ sourceContent;
					if(a==0) {
						ZF=true;
					} else if(a>=256) {
						CF=true;
						a=a%256;
					} else {
						CF=false;
					}
					CL.content = a+"";
					this.updateBigReg(CX, CH, CL);
				} else if(destination.equalsIgnoreCase("dl")) {
					int a=getRegContent("DL")+ sourceContent;
					if(a==0) {
						ZF=true;
					} else if(a>=256) {
						CF=true;
						a=a%256;
					} else {
						CF=false;
					}
					DL.content = a+"";
					this.updateBigReg(DX, DH, DL);
				
			//////////////	
				} else if(destination.equalsIgnoreCase("ax")) {
					int a=getRegContent("AX")+ sourceContent;
					if(a==0) {
						ZF=true;
					}else if(a>=65536) {
						CF=true;
						a=a%65536;
					} else {
						CF=false;
					}
					AX.content = a+"";
					this.updateSmallReg(AX, AH, AL);
				} else if(destination.equalsIgnoreCase("bx")) {
					int a=getRegContent("BX")+ sourceContent;
					if(a==0) {
						ZF=true;
					} else if(a>=65536) {
						CF=true;
						a=a%65536;
					} else {
						CF=false;
					}
					BX.content =a+"";
					this.updateSmallReg(BX, BH, BL);
				} else if(destination.equalsIgnoreCase("cx")) {
					int a=getRegContent("CX")+ sourceContent;
					if(a==0) {
						ZF=true;
					} else if(a>=65536) {
						CF=true;
						a=a%65536;
					} else {
						CF=false;
					}
					CX.content = a+"";
					this.updateSmallReg(CX, CH, CL);
				} else if(destination.equalsIgnoreCase("dx")) {
					int a=getRegContent("DX")+ sourceContent;
					if(a==0) {
						ZF=true;
					}else if(a>=65536) {
						CF=true;
						a=a%65536;
					} else {
						CF=false;
					}
					DX.content = a+"";
					this.updateSmallReg(DX, DH, DL);
				}else if(destination.equalsIgnoreCase("bp")) {
					int a=getRegContent("BP")+ sourceContent;
					if(a==0) {
						ZF=true;
					}else if(a>=65536) {
						CF=true;
						a=a%65536;
					} else {
						CF=false;
					}
					BP.content = a+"";
					
				}else if(destination.equalsIgnoreCase("si")) {
					int a=getRegContent("SI")+ sourceContent;
					if(a==0) {
						ZF=true;
					}else if(a>=65536) {
						CF=true;
						a=a%65536;
					} else {
						CF=false;
					}
					SI.content = a+"";
					
				}else if(destination.equalsIgnoreCase("di")) {
					int a=getRegContent("DI")+ sourceContent;
					if(a==0) {
						ZF=true;
					}else if(a>=65536) {
						CF=true;
						a=a%65536;
					} else {
						CF=false;
					}
					DI.content = a+"";
				}
			
		
		//destination is an address	
		} else if(destType == 2) {
			
			if(sourceType == 2) {
				error = true;
				System.out.println("error");
				return;
			} else { // memory degilse islem yapmali
				int memoryIndex = getIndex(destination);
				int a=getAddress(destination)+sourceContent;
				if(a==0) {
					ZF=true;
				}
				
				memory[memoryIndex]=(a%256)+"";
				memory[memoryIndex+1]=(a/256) + "";
			}
			
		}
		
		//destination is an variable
		else if(destType==7) {
			
			for(int i=0;i<variables.size();i++) {
				if(variables.get(i).name.equals(destination)) {
					variables.get(i).content += sourceContent;
				if(variables.get(i).content==0) {
					ZF=true;
				}
				break;
				}
			}
		}
	}
	
	public void SUB(String destination,String source, boolean error) {
		CF=false; SF=false;
		int sourceType = typeCheck(source);
		int sourceContent=getSourceContent(source, sourceType);
		
		int destType = typeCheck(destination);
		//destination is a reg
		if(destType == 1) {
			
			if(sourceContent > 256) {	
				if(destination.equalsIgnoreCase("ah")||destination.equalsIgnoreCase("al")||destination.equalsIgnoreCase("bh")||destination.equalsIgnoreCase("bl")
						||destination.equalsIgnoreCase("ch")||destination.equalsIgnoreCase("cl")||destination.equalsIgnoreCase("dh")||destination.equalsIgnoreCase("dl")) {
						error = true;
						System.out.println("error");
						return;
					}
			}
			
				if(destination.equalsIgnoreCase("ah")) {
					int a=getRegContent("AH")- sourceContent;
					if(a==0) {
						ZF=true;
					}else if(a < 0) {
						a += 256;
						SF = true;
						CF=true;
					}
					AH.content =a+"";
					this.updateBigReg(AX, AH, AL);
				
				} else if(destination.equalsIgnoreCase("bh")) {
					int a=getRegContent("BH")- sourceContent;
					if(a==0) {
						ZF=true;
					}else if(a < 0) {
						a += 256;
						SF = true;
						CF=true;
					}
					BH.content =a+"";
					this.updateBigReg(BX, BH, BL);
				
				} else if(destination.equalsIgnoreCase("ch")) {
					int a=getRegContent("CH")- sourceContent;
					if(a==0) {
						ZF=true;
					} else if(a < 0) {
						a += 256;
						SF = true;
						CF=true;
					}
					CH.content =a+"";
					this.updateBigReg(CX, CH, CL);
					
				} else if(destination.equalsIgnoreCase("dh")) {
					int a=getRegContent("DH")- sourceContent;
					if(a==0) {
						ZF=true;
					} else if(a < 0) {
						a += 256;
						SF = true;
						CF=true;
					}
					DH.content = a+"";
					this.updateBigReg(DX, DH, DL);
					
				} else if(destination.equalsIgnoreCase("al")) {
					int a=getRegContent("AL")- sourceContent;
					if(a==0) {
						ZF=true;
					} else if(a < 0) {
						a += 256;
						SF = true;
						CF=true;
					}
					AL.content = a+"";
					this.updateBigReg(AX, AH, AL);
					
				} else if(destination.equalsIgnoreCase("bl")) {
					int a=getRegContent("BL")- sourceContent;
					if(a==0) {
						ZF=true;
					} else if(a < 0) {
						a += 256;
							SF = true;
						CF=true;
					}
					BL.content = a+"";	
					this.updateBigReg(BX, BH, BL);
					
				} else if(destination.equalsIgnoreCase("cl")) {
					int a=getRegContent("CL")- sourceContent;
						if(a==0) {
						ZF=true;
					}else if(a < 0) {
						a += 256;
						SF = true;
						CF=true;
					}
					CL.content = a+"";
					this.updateBigReg(CX, CH, CL);
					
				} else if(destination.equalsIgnoreCase("dl")) {
					int a=getRegContent("DL")- sourceContent;
					if(a==0) {
						ZF=true;
					}else if(a < 0) {
							a += 256;
						SF = true;
						CF=true;
					}
					DL.content = a+"";
					this.updateBigReg(DX, DH, DL);
				
			//////16bit	
				} else if(destination.equalsIgnoreCase("ax")) {
					int a=getRegContent("AX")- sourceContent;
					if(a==0) {
						ZF=true;
					}else if(a < 0) {
						a += Math.pow(2, 16);
						SF = true;
						CF=true;
					}
					AX.content = a+"";
					this.updateSmallReg(AX, AH, AL);
					
				} else if(destination.equalsIgnoreCase("bx")) {
					int a=getRegContent("BX")- sourceContent;
					if(a==0) {
						ZF=true;
					}else if(a < 0) {
						a += Math.pow(2, 16);
						SF = true;
						CF=true;
						}
					BX.content =a+"";
					this.updateSmallReg(BX, BH, BL);
					
				} else if(destination.equalsIgnoreCase("cx")) {
					int a=getRegContent("CX")- sourceContent;
						if(a==0) {
						ZF=true;
					}else if(a < 0) {
						a += Math.pow(2, 16);
						SF = true;
						CF=true;
					}
					CX.content = a+"";
					this.updateSmallReg(CX, CH, CL);
					
				} else if(destination.equalsIgnoreCase("dx")) {
					int a=getRegContent("DX")- sourceContent;
					if(a==0) {
						ZF=true;
					}else if(a < 0) {
						a += Math.pow(2, 16);
						SF = true;
						CF=true;
					}
					DX.content = a+"";
					this.updateSmallReg(DX, DH, DL);
					
				} else if(destination.equalsIgnoreCase("bp")) {
					int a=getRegContent("BP")- sourceContent;
					if(a==0) {
						ZF=true;
					}else if(a < 0) {
						a += Math.pow(2, 16);
						SF = true;
						CF=true;
					}
					BP.content = a+"";
					
				}else if(destination.equalsIgnoreCase("si")) {
					int a=getRegContent("SI")- sourceContent;
					if(a==0) {
						ZF=true;
					}else if(a < 0) {
						a += Math.pow(2, 16);
						SF = true;
						CF=true;
					}
					SI.content = a+"";
					
				} else if(destination.equalsIgnoreCase("di")) {
					int a=getRegContent("DI")- sourceContent;
					if(a==0) {
						ZF=true;
					}else if(a < 0) {
						a += Math.pow(2, 16);
						SF = true;
						CF=true;
					}
					DI.content = a+"";
				}
				
				
			//!!!!!!!!!!!!!!!!!!!!!!!!!!!!	
				//String degistigi icin sub ayri yapilacak
			//destination is an address	
		} else if(destType == 2) {
		
			if(sourceType == 2) {
				error = true;
				System.out.println("error");
				return;
			} else { // memory degilse islem yapmali
				int memoryIndex = getIndex(destination);
				int a=getAddress(destination)-sourceContent;
				if(a==0) {
					ZF=true;
				}
				memory[memoryIndex]=(a%256)+"";
				memory[memoryIndex+1]=(a/256) + "";
			}
		}
		
		//destination is a variable
		else if(destType==7) {
			
			for(int i=0;i<variables.size();i++) {
				if(variables.get(i).name.equals(destination)) {
					int a = variables.get(i).content - sourceContent;
					if(a==0) {
						ZF=true;
					}
					if(a < 0) {
						if(variables.get(i).size == 1) {
							a += 256;
						} else {
							a += Math.pow(2, 16);
						}
						SF = true;
					}
					variables.get(i).content = a;
					break;
				}	
			}
		}
	}
	
	public void MUL(String source, boolean error) {
		// uses AL by default writes into AX
		CF=true;
		int sourceType = typeCheck(source);
		int sourceContent=getSourceContent(source, sourceType);
						
		if( sourceContent >= 0 && sourceContent<256) {
			int a=getRegContent("AX");
			int i=a * sourceContent;
			if(i > Math.pow(2, 16)) {
				error = true;
				System.out.println("error");
				return;
			}
			
			if(i==0) {
				ZF=true;
			} else if(i<256) {
				CF=false;
			}
			AX.content =i+"";
			this.updateBigReg(AX, AH, AL);
				
		} else {
			int a=getRegContent("AX");
			int i=a*sourceContent;
			if(i==0) {
				ZF=true;
			}
				
			String s=Integer.toBinaryString(i);
			if(s.length()<32) {
				for(int m=0;m<32-s.length();m++) {
					s="0"+s;
				}			
			}
			String dx=s.substring(0,16);
			String ax=s.substring(16,33);
			int dxi=Integer.parseInt(dx,2);
			int axi=Integer.parseInt(ax,2);
			
			if(dxi > Math.pow(2, 16) || axi > Math.pow(2, 16)) {
				error = true;
				System.out.println("error");
				return;
			}
			
			if(dxi==0) {
				CF=false;
			}
				
			DX.content=dxi+"";
			AX.content=axi+"";
			this.updateSmallReg(AX, AH, AL);
			this.updateSmallReg(DX, DH, DL);
		}
	}
	
	public void DIV(String source, boolean error) {
		//uses AX by default as dividend and writes result into AL and remainder in AH
		
		int sourceType = typeCheck(source);
		int sourceContent=getSourceContent(source,sourceType);
		
		// divide by 0 error
		if(sourceContent == 0) {
			error = true;
			System.out.println("error");
			return;
		} else if(sourceContent > 0 && sourceContent < 256) {
			int a= getRegContent("AX")/sourceContent;
			int b= getRegContent("AX")%sourceContent;
			
			if(a > 256 || b > 256) {
				error = true;
				System.out.println("error");
				return;
			}
			
			if(a==0) 
				ZF=true;
			
			AL.content = a+"";
			AH.content = b+"";
			this.updateBigReg(AX, AH, AL);
			
		} else {
			String sdx=Integer.toBinaryString(getRegContent("DX"));
			String sax=Integer.toBinaryString(getRegContent("AX"));
					
			if(sdx.length()<16) {
				for(int m=0;m<16-sdx.length();m++) {
					sdx="0"+sdx;
				}
			}
			
			if(sax.length()<16) {
				for(int m=0;m<16-sax.length();m++) {
					sax="0"+sax;
				}
			}
			
			String dividends=sdx+sax;
			int dividend=Integer.parseInt(dividends,2);
			
			int a=dividend/sourceContent;
			int b=dividend%sourceContent;
			
			if(a > Math.pow(2, 16) || b > Math.pow(2, 16)) {
				error = true;
				System.out.println("error");
				return;
			}
			if(a==0) 
				ZF=true;
			
			AX.content=a+"";
			DX.content=b+"";
			this.updateSmallReg(AX, AH, AL);
			this.updateSmallReg(DX, DH, DL);
		}
	}
	
	public void XOR(String destination , String source) {
		///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
		//a=a ^ b;
		
		CF=false;
		int sourceType = typeCheck(source);
		int sourceContent=getSourceContent(source,sourceType);
		
		int destType = typeCheck(destination);
		//destination is a reg
		if(destType == 1) {
			
			//source 8 bitse
			if(sourceContent < 256 && sourceContent >= 0) {
				
				if(destination.equalsIgnoreCase("ah")) {
					int a=getRegContent("AH")^ sourceContent;
					if(a==0)
						ZF=true;
					
					AH.content =a+"";
					this.updateBigReg(AX, AH, AL);
				} else if(destination.equalsIgnoreCase("bh")) {
					int a=getRegContent("BH")^ sourceContent;
					if(a==0) 
						ZF=true;
					
					BH.content =a+"";
					this.updateBigReg(BX, BH, BL);
				} else if(destination.equalsIgnoreCase("ch")) {
					int a=getRegContent("CH")^ sourceContent;
					if(a==0) 
						ZF=true;
					
					CH.content =a+"";
					this.updateBigReg(CX, CH, CL);
				} else if(destination.equalsIgnoreCase("dh")) {
					int a=getRegContent("DH")^ sourceContent;
					if(a==0) 
						ZF=true;
					
					DH.content = a+"";
					this.updateBigReg(DX, DH, DL);
				} else if(destination.equalsIgnoreCase("al")) {
					int a=getRegContent("AL")^ sourceContent;
					if(a==0) 
						ZF=true;
					
					AL.content = a+"";
					this.updateBigReg(AX, AH, AL);
				} else if(destination.equalsIgnoreCase("bl")) {
					int a=getRegContent("BL")^ sourceContent;
					if(a==0) 
						ZF=true;
					
					BL.content = a+"";	
					this.updateBigReg(BX, BH, BL);
				} else if(destination.equalsIgnoreCase("cl")) {
					int a=getRegContent("CL")^ sourceContent;
					if(a==0) 
						ZF=true;
					
					CL.content = a+"";
					this.updateBigReg(CX, CH, CL);
				} else if(destination.equalsIgnoreCase("dl")) {
					int a=getRegContent("DL")^ sourceContent;
					if(a==0) 
						ZF=true;
					
					DL.content = a+"";
					this.updateBigReg(DX, DH, DL);
				}
			
			//source 16 bitse
			} else if(sourceContent < 65536 && sourceContent >= 0) {
				
				if(destination.equalsIgnoreCase("ax")) {
					int a=getRegContent("AX")^ sourceContent;
					if(a==0) 
						ZF=true;
					
					AX.content = a+"";
					this.updateSmallReg(AX, AH, AL);
				} else if(destination.equalsIgnoreCase("bx")) {
					int a=getRegContent("BX")^ sourceContent;
					if(a==0) 
						ZF=true;
					
					BX.content =a+"";
					this.updateSmallReg(BX, BH, BL);
				} else if(destination.equalsIgnoreCase("cx")) {
					int a=getRegContent("CX")^ sourceContent;
					if(a==0) 
						ZF=true;
					
					CX.content = a+"";
					this.updateSmallReg(CX, CH, CL);
				} else if(destination.equalsIgnoreCase("dx")) {
					int a=getRegContent("DX")^ sourceContent;
					if(a==0) 
						ZF=true;
					
					DX.content = a+"";
					this.updateSmallReg(DX, DH, DL);
				} else if(destination.equalsIgnoreCase("bp")) {
						int a=getRegContent("BP")^ sourceContent;
						if(a==0) 
							ZF=true;
						
						BP.content = a+"";
				} else if(destination.equalsIgnoreCase("SI")) {
						int a=getRegContent("SI")^ sourceContent;
						if(a==0) 
							ZF=true;
						
						SI.content = a+"";
						
				} else if(destination.equalsIgnoreCase("DI")) {
						int a=getRegContent("DI")^ sourceContent;
						if(a==0) 
							ZF=true;
						
						DI.content = a+"";
				}
			}
			
			///// !!!!!!!!!!!!!!!!!!!!!!!
		//destination is an address	
		} else if(destType == 2) {
			int memoryIndex = getIndex(destination);
			int a=getAddress(destination)^sourceContent;
			if(a==0) 
				ZF=true;
			
			memory[memoryIndex]=(a%256)+"";
			memory[memoryIndex+1]=(a/256) + "";
			
		//destination is a variable
		} else if(destType==7) {
			for(int i=0;i<variables.size();i++) {
				if(variables.get(i).name.equals(destination)) {
					variables.get(i).content=variables.get(i).content ^ sourceContent;
					if(variables.get(i).content==0) {
						ZF=true;
					}
					break;
				}
			}
		}
	}
	
	public void OR(String destination , String source ) {
		//!!!!!!!!!!
		// a=a | b;
		CF=false;
		int sourceType = typeCheck(source);
		int sourceContent=getSourceContent(source,sourceType);
		
		int destType = typeCheck(destination);
		//destination is a reg
		if(destType == 1) {
					
			//source 8 bitse
			if(sourceContent < 256 && sourceContent >= 0) {
					
				if(destination.equalsIgnoreCase("ah")) {
					int a=getRegContent("AH")| sourceContent;
					if(a==0) 
						ZF=true;
					
					AH.content =a+"";
					this.updateBigReg(AX, AH, AL);
				} else if(destination.equalsIgnoreCase("bh")) {
					int a=getRegContent("BH")| sourceContent;
					if(a==0) 
						ZF=true;
					
					BH.content =a+"";
					this.updateBigReg(BX, BH, BL);
				} else if(destination.equalsIgnoreCase("ch")) {
					int a=getRegContent("CH")| sourceContent;
					if(a==0) 
						ZF=true;
					
					CH.content =a+"";
					this.updateBigReg(CX, CH, CL);
				} else if(destination.equalsIgnoreCase("dh")) {
					int a=getRegContent("DH")| sourceContent;
					if(a==0) 
						ZF=true;
					
					DH.content = a+"";
						this.updateBigReg(DX, DH, DL);
				} else if(destination.equalsIgnoreCase("al")) {
					int a=getRegContent("AL")| sourceContent;
					if(a==0) 
						ZF=true;
					
					AL.content = a+"";
					this.updateBigReg(AX, AH, AL);
				} else if(destination.equalsIgnoreCase("bl")) {
					int a=getRegContent("BL")| sourceContent;
					if(a==0) 
						ZF=true;
					
					BL.content = a+"";	
					this.updateBigReg(BX, BH, BL);
				} else if(destination.equalsIgnoreCase("cl")) {
					int a=getRegContent("CL")| sourceContent;
					if(a==0) 
						ZF=true;
					
					CL.content = a+"";
					this.updateBigReg(CX, CH, CL);
				} else if(destination.equalsIgnoreCase("dl")) {
					int a=getRegContent("DL")| sourceContent;
					if(a==0) 
						ZF=true;
					
					DL.content = a+"";
					this.updateBigReg(DX, DH, DL);
				}
			//source 16 bitse
			} else if(sourceContent < 65536 && sourceContent >= 0) {
				
				if(destination.equalsIgnoreCase("ax")) {
					int a=getRegContent("AX")| sourceContent;
					if(a==0) 
						ZF=true;
					
					AX.content = a+"";
					this.updateSmallReg(AX, AH, AL);
				} else if(destination.equalsIgnoreCase("bx")) {
					int a=getRegContent("BX")| sourceContent;
					if(a==0) 
						ZF=true;
					
					BX.content =a+"";
					this.updateSmallReg(BX, BH, BL);
				} else if(destination.equalsIgnoreCase("cx")) {
					int a=getRegContent("CX")| sourceContent;
					if(a==0) 
						ZF=true;
					
					CX.content = a+"";
					this.updateSmallReg(CX, CH, CL);
				} else if(destination.equalsIgnoreCase("dx")) {
					int a=getRegContent("DX")| sourceContent;
					if(a==0) 
						ZF=true;
					
					DX.content = a+"";
					this.updateSmallReg(DX, DH, DL);
				} else if(destination.equalsIgnoreCase("BP")) {
					int a=getRegContent("BP")| sourceContent;
					if(a==0) 
						ZF=true;
					
					BP.content = a+"";
						
				} else if(destination.equalsIgnoreCase("SI")) {
					int a=getRegContent("SI")| sourceContent;
					if(a==0) 
						ZF=true;
					
					SI.content = a+"";
				} else if(destination.equalsIgnoreCase("DI")) {
					int a=getRegContent("DI")| sourceContent;
					if(a==0) 
						ZF=true;
					
					DI.content = a+"";
				}
			}
				
		//destination is an address	
		} else if(destType == 2) {
			int memoryIndex = getIndex(destination);
			int a=getAddress(destination)|sourceContent;
			if(a==0) 
				ZF=true;
			
			memory[memoryIndex]=(a%256)+"";
			memory[memoryIndex+1]=(a/256) + "";
				
		//destination is an variable
		} else if(destType==7) {
			for(int i=0;i<variables.size();i++) {
				if(variables.get(i).name.equals(destination)) {
					variables.get(i).content=variables.get(i).content | sourceContent;
					if(variables.get(i).content==0) {
						ZF=true;
					}
				break;
				}
			}
		}
	}
	
	public void AND(String destination , String source) {
		//!!!!!!!!
		//a=a & b;
		
		CF=false;
		int sourceType = typeCheck(source);
		int sourceContent=getSourceContent(source,sourceType);	
		
		int destType = typeCheck(destination);
		//destination is a reg
		if(destType == 1) {
									
			//source 8 bitse
			if(sourceContent < 256 && sourceContent >= 0) {
						
				if(destination.equalsIgnoreCase("ah")) {
					int a=getRegContent("AH")& sourceContent;
					if(a==0) {
						ZF=true;
					}
					AH.content =a+"";
					this.updateBigReg(AX, AH, AL);
				} else if(destination.equalsIgnoreCase("bh")) {
					int a=getRegContent("BH")& sourceContent;
					if(a==0) {
						ZF=true;
					}
					BH.content =a+"";
					this.updateBigReg(BX, BH, BL);
				} else if(destination.equalsIgnoreCase("ch")) {
					int a=getRegContent("CH")& sourceContent;
					if(a==0) {
						ZF=true;
					}
					CH.content =a+"";
					this.updateBigReg(CX, CH, CL);
				} else if(destination.equalsIgnoreCase("dh")) {
					int a=getRegContent("DH")& sourceContent;
					if(a==0) {
						ZF=true;
					}
					DH.content = a+"";
					this.updateBigReg(DX, DH, DL);
				} else if(destination.equalsIgnoreCase("al")) {
					int a=getRegContent("AL")& sourceContent;
					if(a==0) {
						ZF=true;
					}
					AL.content = a+"";
					this.updateBigReg(AX, AH, AL);
				} else if(destination.equalsIgnoreCase("bl")) {
					int a=getRegContent("BL")& sourceContent;
					if(a==0) {
						ZF=true;
					}
					BL.content = a+"";	
					this.updateBigReg(BX, BH, BL);
				} else if(destination.equalsIgnoreCase("cl")) {
					int a=getRegContent("CL")& sourceContent;
					if(a==0) {
						ZF=true;
					}
					CL.content = a+"";
					this.updateBigReg(CX, CH, CL);
				} else if(destination.equalsIgnoreCase("dl")) {
					int a=getRegContent("DL")& sourceContent;
					if(a==0) {
						ZF=true;
					}
					DL.content = a+"";
					this.updateBigReg(DX, DH, DL);
				}
					
			//source 16 bitse
			} else if(sourceContent < 65536 && sourceContent >= 0) {
						
				if(destination.equalsIgnoreCase("ax")) {
					int a=getRegContent("AX")& sourceContent;
					if(a==0) {
						ZF=true;
					}
					AX.content = a+"";
					this.updateSmallReg(AX, AH, AL);
				} else if(destination.equalsIgnoreCase("bx")) {
					int a=getRegContent("BX")& sourceContent;
					if(a==0) {
						ZF=true;
					}
					BX.content =a+"";
					this.updateSmallReg(BX, BH, BL);
				} else if(destination.equalsIgnoreCase("cx")) {
					int a=getRegContent("CX")& sourceContent;
					if(a==0) {
						ZF=true;
					}
					CX.content = a+"";
					this.updateSmallReg(CX, CH, CL);
				} else if(destination.equalsIgnoreCase("dx")) {
					int a=getRegContent("DX")& sourceContent;
					if(a==0) {
						ZF=true;
					}
					DX.content = a+"";
					this.updateSmallReg(DX, DH, DL);
				} else if(destination.equalsIgnoreCase("BP")) {
					int a=getRegContent("BP")& sourceContent;
					if(a==0) {
						ZF=true;
					}
					BP.content = a+"";
					
				} else if(destination.equalsIgnoreCase("SI")) {
					int a=getRegContent("SI")& sourceContent;
					if(a==0) {
						ZF=true;
					}
					SI.content = a+"";
				
				} else if(destination.equalsIgnoreCase("DI")) {
					int a=getRegContent("DI")& sourceContent;
					if(a==0) {
						ZF=true;
					}
					DI.content = a+"";
				}
			}
					
		//destination is an address	
		} else if(typeCheck(destination) == 2) {
								
			int memoryIndex = getIndex(destination);
			int a=getAddress(destination)&sourceContent;
			if(a==0) {
				ZF=true;
			}
			memory[memoryIndex]=(a%256)+"";
			memory[memoryIndex+1]=(a/256) + "";
			
		//destination is an variable
		} else if(typeCheck(destination)==7) {
			for(int i=0;i<variables.size();i++) {
				if(variables.get(i).name.equals(destination)) {
					variables.get(i).content=variables.get(i).content & sourceContent;
					if(variables.get(i).content==0) {
						ZF=true;
					}
				break;
				}
			}
		}
	}
	
	public void INC(String destination) {
		SF=false;
		int destType = typeCheck(destination);
		if(destType == 1) {
			
			if(destination.equalsIgnoreCase("ah")) {
				int a=getRegContent("AH");
				a++;
				if(a==0) {
					ZF=true;
				}
				if(a>=256) {
					a=a%256;
				}
				AH.content = a+"";
				this.updateBigReg(AX, AH, AL);
			} else if(destination.equalsIgnoreCase("bh")) {
				int a=getRegContent("BH");
				a++;
				if(a==0) {
					ZF=true;
				}
				if(a>=256) {
					a=a%256;
				}
				BH.content = a+"";
				this.updateBigReg(BX, BH, BL);
			} else if(destination.equalsIgnoreCase("ch")) {
				int a=getRegContent("CH");
				a++;
				if(a==0) {
					ZF=true;
				}
				if(a>=256) {
					a=a%256;
				}
				CH.content = a+"";
				this.updateBigReg(CX, CH, CL);
			} else if(destination.equalsIgnoreCase("dh")) {
				int a=getRegContent("DH");
				a++;
				if(a==0) {
					ZF=true;
				}
				if(a>=256) {
					a=a%256;
				}
				DH.content =a+"";
				this.updateBigReg(DX, DH, DL);
			} else if(destination.equalsIgnoreCase("al")) {
				int a=getRegContent("AL");
				a++;
				if(a==0) {
					ZF=true;
				}
				if(a>=256) {
					a=a%256;
				}
				AL.content=a+"";
				this.updateBigReg(AX, AH, AL);
			} else if(destination.equalsIgnoreCase("bl")) {
				int a=getRegContent("BL");
				a++;
				if(a==0) {
					ZF=true;
				}
				if(a>=256) {
					a=a%256;
				}
				BL.content = a+"";	
				this.updateBigReg(BX, BH, BL);
			} else if(destination.equalsIgnoreCase("cl")) {
				int a=getRegContent("CL");
				a++;
				if(a==0) {
					ZF=true;
				}
				if(a>=256) {
					a=a%256;
				}
				CL.content = a+"";	
				this.updateBigReg(CX, CH, CL);
			} else if(destination.equalsIgnoreCase("dl")) {
				int a=getRegContent("DL");
				a++;
				if(a==0) {
					ZF=true;
				}
				if(a>=256) {
					a=a%256;
				}
				DL.content = a+"";	
				this.updateBigReg(DX, DH, DL);
			}
		
		
			else if(destination.equalsIgnoreCase("ax")) {
				int a=getRegContent("AX");
				a++;
				if(a==0) {
					ZF=true;
				}
				if(a>=65536) {
					a=a%65536;
				}
				AX.content = a+"";
				this.updateSmallReg(AX, AH, AL);
			} else if(destination.equalsIgnoreCase("bx")) {
				int a=getRegContent("BX");
				a++;
				if(a==0) {
					ZF=true;
				}
				if(a>=65536) {
					a=a%65536;
				}
				BX.content = a+"";
				this.updateSmallReg(BX, BH, BL);
			} else if(destination.equalsIgnoreCase("cx")) {
				int a=getRegContent("CX");
				a++;
				if(a==0) {
					ZF=true;
				}
				if(a>=65536) {
					a=a%65536;
				}
				CX.content = a+"";
				this.updateSmallReg(CX, CH, CL);
			} else if(destination.equalsIgnoreCase("dx")) {
				int a=getRegContent("DX");
				a++;
				if(a==0) {
					ZF=true;
				}
				if(a>=65536) {
					a=a%65536;
				}
				DX.content = a+"";
				this.updateSmallReg(DX, DH, DL);
			}
			else if(destination.equalsIgnoreCase("BP")) {
				int a=getRegContent("BP");
				a++;
				if(a==0) {
					ZF=true;
				}
				if(a>=65536) {
					a=a%65536;
				}
				BP.content = a+"";
				
			}
			else if(destination.equalsIgnoreCase("SI")) {
				int a=getRegContent("SI");
				a++;
				if(a==0) {
					ZF=true;
				}
				if(a>=65536) {
					a=a%65536;
				}
				SI.content = a+"";
				
			}
			else if(destination.equalsIgnoreCase("DI")) {
				int a=getRegContent("DI");
				a++;
				if(a==0) {
					ZF=true;
				}
				if(a>=65536) {
					a=a%65536;
				}
				DI.content = a+"";
				
			}
		
		}//destination is an address	
		 else if(destType == 2) {
				
				int memoryIndex = getIndex(destination);
				int a=getAddress(destination);
				a++;
				if(a==0) {
					ZF=true;
				}
				memory[memoryIndex]=(a%256)+"";
				memory[memoryIndex+1]=(a/256) + "";
		}
	}
	
	public void DEC(String destination) {
		SF=false;
		int destType = typeCheck(destination);
		if(destType == 1) {
			
			if(destination.equalsIgnoreCase("ah")) {
				int a=getRegContent("AH");
				a--;
				if(a==0) {
					ZF=true;
				}
				if(a < 0) {
					a += 256;
					SF = true;
				}
				
				AH.content = a+"";
				this.updateBigReg(AX, AH, AL);
			} else if(destination.equalsIgnoreCase("bh")) {
				int a=getRegContent("BH");
				a--;
				if(a==0) {
					ZF=true;
				}
				if(a < 0) {
					a += 256;
					SF = true;
				}
				BH.content = a+"";
				this.updateBigReg(BX, BH, BL);
			} else if(destination.equalsIgnoreCase("ch")) {
				int a=getRegContent("CH");
				a--;
				if(a==0) {
					ZF=true;
				}
				if(a < 0) {
					a += 256;
					SF = true;
				}
				CH.content = a+"";
				this.updateBigReg(CX, CH, CL);
			} else if(destination.equalsIgnoreCase("dh")) {
				int a=getRegContent("DH");
				a--;
				if(a==0) {
					ZF=true;
				}
				if(a < 0) {
					a += 256;
					SF = true;
				}
				DH.content =a+"";
				this.updateBigReg(DX, DH, DL);
			} else if(destination.equalsIgnoreCase("al")) {
				int a=getRegContent("AL");
				a--;
				if(a==0) {
					ZF=true;
				}
				if(a < 0) {
					a += 256;
					SF = true;
				}
				AL.content=a+"";
				this.updateBigReg(AX, AH, AL);
			} else if(destination.equalsIgnoreCase("bl")) {
				int a=getRegContent("BL");
				a--;
				if(a==0) {
					ZF=true;
				}
				if(a < 0) {
					a += 256;
					SF = true;
				}
				BL.content = a+"";	
				this.updateBigReg(BX, BH, BL);
			} else if(destination.equalsIgnoreCase("cl")) {
				int a=getRegContent("CL");
				a--;
				if(a==0) {
					ZF=true;
				}
				if(a < 0) {
					a += 256;
					SF = true;
				}
				CL.content = a+"";	
				this.updateBigReg(CX, CH, CL);
			} else if(destination.equalsIgnoreCase("dl")) {
				int a=getRegContent("DL");
				a--;
				if(a==0) {
					ZF=true;
				}
				if(a < 0) {
					a += 256;
					SF = true;
				}
				DL.content = a+"";	
				this.updateBigReg(DX, DH, DL);
			}
		
		
			else if(destination.equalsIgnoreCase("ax")) {
				int a=getRegContent("AX");
				a--;
				if(a==0) {
					ZF=true;
				}
				if(a < 0) {
					a += Math.pow(2, 16);
					SF = true;
				}
				AX.content = a+"";
				this.updateSmallReg(AX, AH, AL);
			} else if(destination.equalsIgnoreCase("bx")) {
				int a=getRegContent("BX");
				a--;
				if(a==0) {
					ZF=true;
				}
				if(a < 0) {
					a += Math.pow(2, 16);
					SF = true;
				}
				BX.content = a+"";
				this.updateSmallReg(BX, BH, BL);
			} else if(destination.equalsIgnoreCase("cx")) {
				int a=getRegContent("CX");
				a--;
				if(a==0) {
					ZF=true;
				}
				if(a < 0) {
					a += Math.pow(2, 16);
					SF = true;
				}
				CX.content = a+"";
				this.updateSmallReg(CX, CH, CL);
			} else if(destination.equalsIgnoreCase("dx")) {
				int a=getRegContent("DX");
				a--;
				if(a==0) {
					ZF=true;
				}
				if(a < 0) {
					a += Math.pow(2, 16);
					SF = true;
				}
				DX.content = a+"";
				this.updateSmallReg(DX, DH, DL);
			}
			 else if(destination.equalsIgnoreCase("BP")) {
					int a=getRegContent("BP");
					a--;
					if(a==0) {
						ZF=true;
					}
					if(a < 0) {
						a += Math.pow(2, 16);
						SF = true;
					}
					BP.content = a+"";
					
				}
			 else if(destination.equalsIgnoreCase("SI")) {
					int a=getRegContent("SI");
					a--;
					if(a==0) {
						ZF=true;
					}
					if(a < 0) {
						a += Math.pow(2, 16);
						SF = true;
					}
					SI.content = a+"";
					
				}
			 else if(destination.equalsIgnoreCase("DI")) {
					int a=getRegContent("DI");
					a--;
					if(a==0) {
						ZF=true;
					}
					if(a < 0) {
						a += Math.pow(2, 16);
						SF = true;
					}
					DI.content = a+"";
					
				}
		
		}//destination is an address	
		else if(destType == 2) {
				
				int memoryIndex = getIndex(destination);
				int a=getAddress(destination);
				a--;
				if(a==0) {
					ZF=true;
				}
				memory[memoryIndex]=(a%256)+"";
				memory[memoryIndex+1]=(a/256) + "";
		}
	}
	
	public void NOT(String destination) {
		
		// a=~a;
		int destType = typeCheck(destination);
		if(destType == 1) {
			
				if(destination.equalsIgnoreCase("ah")) {
					int a=getRegContent("AH");
					a=~ a;
					if(a==0) {
						ZF=true;
					}
					AH.content = a+"";
					this.updateBigReg(AX, AH, AL);
				} else if(destination.equalsIgnoreCase("bh")) {
					int a=getRegContent("BH");
					a=~ a;
					if(a==0) {
						ZF=true;
					}
					BH.content = a+"";
					this.updateBigReg(BX, BH, BL);
				} else if(destination.equalsIgnoreCase("ch")) {
					int a=getRegContent("CH");
					a=~ a;
					if(a==0) {
						ZF=true;
					}
					CH.content = a+"";
					this.updateBigReg(CX, CH, CL);
				} else if(destination.equalsIgnoreCase("dh")) {
					int a=getRegContent("DH");
					a=~ a;
					if(a==0) {
						ZF=true;
					}
					DH.content =a+"";
					this.updateBigReg(DX, DH, DL);
				} else if(destination.equalsIgnoreCase("al")) {
					int a=getRegContent("AL");
					a=~ a;
					if(a==0) {
						ZF=true;
					}
					AL.content=a+"";
					this.updateBigReg(AX, AH, AL);
				} else if(destination.equalsIgnoreCase("bl")) {
					int a=getRegContent("BL");
					a=~ a;
					if(a==0) {
						ZF=true;
					}
					BL.content = a+"";	
					this.updateBigReg(BX, BH, BL);
				} else if(destination.equalsIgnoreCase("cl")) {
					int a=getRegContent("CL");
					a=~ a;
					if(a==0) {
						ZF=true;
					}
					CL.content = a+"";	
					this.updateBigReg(CX, CH, CL);
				} else if(destination.equalsIgnoreCase("dl")) {
					int a=getRegContent("DL");
					a=~ a;
					if(a==0) {
						ZF=true;
					}
					DL.content = a+"";	
					this.updateBigReg(DX, DH, DL);
				}
			
			
				else if(destination.equalsIgnoreCase("ax")) {
					int a=getRegContent("AX");
					a=~ a;
					if(a==0) {
						ZF=true;
					}
					AX.content = a+"";
					this.updateSmallReg(AX, AH, AL);
				} else if(destination.equalsIgnoreCase("bx")) {
					int a=getRegContent("BX");
					a=~ a;
					if(a==0) {
						ZF=true;
					}
					BX.content = a+"";
					this.updateSmallReg(BX, BH, BL);
				} else if(destination.equalsIgnoreCase("cx")) {
					int a=getRegContent("CX");
					a=~ a;
					if(a==0) {
						ZF=true;
					}
					CX.content = a+"";
					this.updateSmallReg(CX, CH, CL);
				} else if(destination.equalsIgnoreCase("dx")) {
					int a=getRegContent("DX");
					a=~ a;
					if(a==0) {
						ZF=true;
					}
					DX.content = a+"";
					this.updateSmallReg(DX, DH, DL);
				}
				else if(destination.equalsIgnoreCase("BP")) {
					int a=getRegContent("BP");
					a=~ a;
					if(a==0) {
						ZF=true;
					}
					BP.content = a+"";
					
				}
				else if(destination.equalsIgnoreCase("SI")) {
					int a=getRegContent("SI");
					a=~ a;
					if(a==0) {
						ZF=true;
					}
					SI.content = a+"";
				
				}
				else if(destination.equalsIgnoreCase("DI")) {
					int a=getRegContent("DI");
					a=~ a;
					if(a==0) {
						ZF=true;
					}
					DI.content = a+"";
					
				}
			
		//destination is an address	
		} else if(destType == 2) {
			
			int memoryIndex = getIndex(destination);
			int a=getAddress(destination);
			a=~a;
			if(a==0) {
				ZF=true;
			}
			memory[memoryIndex]=(a%256)+"";
			memory[memoryIndex+1]=(a/256) + "";
		
		//destination is a variable	
		} else if(destType==7) {
			for(int i=0;i<variables.size();i++) {
				if(variables.get(i).name.equals(destination)) {
					variables.get(i).content=~variables.get(i).content ;
				if(variables.get(i).content==0) {
					ZF=true;
				}
				break;
				}
			}
		}
	}
	
	public int rcl(int des,int i) {
		String bin=Integer.toBinaryString(des);
		if(des<256) {
			if(bin.length()<8) {
				int y=bin.length();
				for(int m=0;m<y;m++) {
					bin="0"+bin;
				}
			}
		}
		else {
			if(bin.length()<16) {
				int y=bin.length();
				for(int m=0;m<16-y;m++) {
					bin="0"+bin;
				}
			}
		}
		if(CF) {
			bin="1"+bin;
		}else {
			bin="0"+bin;
		}
		int reel=Integer.parseInt(bin,2);
		reel=Integer.rotateLeft(reel, i);
		bin=Integer.toBinaryString(reel);
		if(bin.length()<9) {
			CF=false;
		}
		else if(bin.length()==9) {
			if(bin.charAt(0)=='0') {
				CF=false;
			}
			else {
				CF=true;
			}
			bin=bin.substring(1);
		}
		else if(bin.length()>9 && bin.length()<17) {

			CF=false;
		}
		else if(bin.length()==17) {
			if(bin.charAt(0)=='0') {
				CF=false;
			}
			else {
				CF=true;
			}
			bin=bin.substring(1);
		}
		return Integer.parseInt(bin,2);
	}
	
	public void RCL(String destination,String i) {
		// carry biti sola koyup rotate 
		
		//destination =reg
		int dist=Integer.parseInt(i);
		int destType = typeCheck(destination);
		
			if(destType==1) {
				if(destination.equalsIgnoreCase("ah")) {
					int a=rcl(getRegContent("AH"),dist);
					if(a==0) {
						ZF=true;
					}
					AH.content = a + "";
					this.updateBigReg(AX, AH, AL);
				} else if(destination.equalsIgnoreCase("bh")) {
					int a=rcl(getRegContent("BH"),dist);
					if(a==0) {
						ZF=true;
					}
					BH.content = a+"";
					this.updateBigReg(BX, BH, BL);
				} else if(destination.equalsIgnoreCase("ch")) {
					int a=rcl(getRegContent("CH"),dist);
					if(a==0) {
						ZF=true;
					}
					CH.content = a+"";
					this.updateBigReg(CX, CH, CL);
				} else if(destination.equalsIgnoreCase("dh")) {
					int a=rcl(getRegContent("DH"),dist);
					if(a==0) {
						ZF=true;
					}
					DH.content = a+"";
					this.updateBigReg(DX, DH, DL);
				} else if(destination.equalsIgnoreCase("al")) {
					int a=rcl(getRegContent("AL"),dist);
					if(a==0) {
						ZF=true;
					}
					AL.content = a+"";
					this.updateBigReg(AX, AH, AL);
				} else if(destination.equalsIgnoreCase("bl")) {
					int a=rcl(getRegContent("BL"),dist);
					if(a==0) {
						ZF=true;
					}
					BL.content = a+"";
					this.updateBigReg(BX, BH, BL);
				} else if(destination.equalsIgnoreCase("cl")) {
					int a=rcl(getRegContent("CL"),dist);
					if(a==0) {
						ZF=true;
					}
					CL.content = a+"";
					this.updateBigReg(CX, CH, CL);
				} else if(destination.equalsIgnoreCase("dl")) {
					int a=rcl(getRegContent("DL"),dist);
					if(a==0) {
						ZF=true;
					}
					DL.content = a+"";
					this.updateBigReg(DX, DH, DL);
				}else if(destination.equalsIgnoreCase("ax")) {
					int a=rcl(getRegContent("AX"),dist);
					if(a==0) {
						ZF=true;
					}
					AX.content = a+"";
					this.updateSmallReg(AX, AH, AL);
				} else if(destination.equalsIgnoreCase("bx")) {
					int a=rcl(getRegContent("BX"),dist);
					if(a==0) {
						ZF=true;
					}
					BX.content =a+"";
					this.updateSmallReg(BX, BH, BL);
				} else if(destination.equalsIgnoreCase("cx")) {
					int a=rcl(getRegContent("CX"),dist);
					if(a==0) {
						ZF=true;
					}
					CX.content =a+"";
					this.updateSmallReg(CX, CH, CL);
				} else if(destination.equalsIgnoreCase("dx")) {
					int a=rcl(getRegContent("DX"),dist);
					if(a==0) {
						ZF=true;
					}
					DX.content = a+"";
					this.updateSmallReg(DX, DH, DL);
				}
				 else if(destination.equalsIgnoreCase("BP")) {
						int a=rcl(getRegContent("BP"),dist);
						if(a==0) {
							ZF=true;
						}
						BP.content = a+"";
					
					}
				 else if(destination.equalsIgnoreCase("SI")) {
						int a=rcl(getRegContent("SI"),dist);
						if(a==0) {
							ZF=true;
						}
						SI.content = a+"";
						
					}
				 else if(destination.equalsIgnoreCase("DI")) {
						int a=rcl(getRegContent("DI"),dist);
						if(a==0) {
							ZF=true;
						}
						DI.content = a+"";
						
					}
				
		}//destination is address
		else if(destType==2) {
			
					int memoryIndex = getIndex(destination);
						
					int a=getAddress(destination) ;
					a=rcl(a,dist);
					if(a==0) {
						ZF=true;
					}
					memory[memoryIndex]=(a%256)+"";
					memory[memoryIndex+1]=(a/256) + "";
		}//destination is variable
		else if(destType==7) {
			for(int m=0;m<variables.size();m++) {
				if(variables.get(m).name.equals(destination)) {
					variables.get(m).content=rcl(variables.get(m).content,dist);
					if(variables.get(m).content==0) {
					ZF=true;
					}
					break;
				}
			}
		}
	
	}
	
	public int rcr(int des,int i) {
		String bin=Integer.toBinaryString(des);
		if(des<256) {
			if(bin.length()<8) {
				int y=bin.length();
				for(int m=0;m<8-y;m++) {
					bin="0"+bin;
				}
			}
		}
		else {
			if(bin.length()<16) {
				int y=bin.length();
				for(int m=0;m<16-y;m++) {
					bin="0"+bin;
				}
			}
		}
		if(CF) {
			bin=bin+"1";
		}else {
			bin=bin+"0";
		}
		int reel=Integer.parseInt(bin,2);
		reel=Integer.rotateRight(reel, i);
		bin=Integer.toBinaryString(reel);
		
		if(bin.charAt(bin.length()-1)=='0') {
			CF=false;
		}else {
			CF=true;
		}
		bin=bin.substring(0,bin.length()-1);
		return Integer.parseInt(bin,2);
	}
	
	public void RCR(String destination,String i) {
		// carry biti saga koyup rotate
		//Integer.rotateRight(i , distance);
		
		int dist=Integer.parseInt(i);
		int destType = typeCheck(destination);
		
		if(destType==1) {
			if(destination.equalsIgnoreCase("ah")) {
				int a=rcr(getRegContent("AH"),dist);
				if(a==0) {
					ZF=true;
				}
				AH.content = a + "";
				this.updateBigReg(AX, AH, AL);
			} else if(destination.equalsIgnoreCase("bh")) {
				int a=rcr(getRegContent("BH"),dist);
				if(a==0) {
					ZF=true;
				}
				BH.content = a+"";
				this.updateBigReg(BX, BH, BL);
			} else if(destination.equalsIgnoreCase("ch")) {
				int a=rcr(getRegContent("CH"),dist);
				if(a==0) {
					ZF=true;
				}
				CH.content = a+"";
				this.updateBigReg(CX, CH, CL);
			} else if(destination.equalsIgnoreCase("dh")) {
				int a=rcr(getRegContent("DH"),dist);
				if(a==0) {
					ZF=true;
				}
				DH.content = a+"";
				this.updateBigReg(DX, DH, DL);
			} else if(destination.equalsIgnoreCase("al")) {
				int a=rcr(getRegContent("AL"),dist);
				if(a==0) {
					ZF=true;
				}
				AL.content = a+"";
				this.updateBigReg(AX, AH, AL);
			} else if(destination.equalsIgnoreCase("bl")) {
				int a=rcr(getRegContent("BL"),dist);
				if(a==0) {
					ZF=true;
				}
				BL.content = a+"";
				this.updateBigReg(BX, BH, BL);
			} else if(destination.equalsIgnoreCase("cl")) {
				int a=rcr(getRegContent("CL"),dist);
				if(a==0) {
					ZF=true;
				}
				CL.content = a+"";
				this.updateBigReg(CX, CH, CL);
			} else if(destination.equalsIgnoreCase("dl")) {
				int a=rcr(getRegContent("DL"),dist);
				if(a==0) {
					ZF=true;
				}
				DL.content = a+"";
				this.updateBigReg(DX, DH, DL);
			}else if(destination.equalsIgnoreCase("ax")) {
				int a=rcr(getRegContent("AX"),dist);
				if(a==0) {
					ZF=true;
				}
				AX.content = a+"";
				this.updateSmallReg(AX, AH, AL);
			} else if(destination.equalsIgnoreCase("bx")) {
				int a=rcr(getRegContent("BX"),dist);
				if(a==0) {
					ZF=true;
				}
				BX.content =a+"";
				this.updateSmallReg(BX, BH, BL);
			} else if(destination.equalsIgnoreCase("cx")) {
				int a=rcr(getRegContent("CX"),dist);
				if(a==0) {
					ZF=true;
				}
				CX.content =a+"";
				this.updateSmallReg(CX, CH, CL);
			} else if(destination.equalsIgnoreCase("dx")) {
				int a=rcr(getRegContent("DX"),dist);
				if(a==0) {
					ZF=true;
				}
				DX.content = a+"";
				this.updateSmallReg(DX, DH, DL);
			}
			else if(destination.equalsIgnoreCase("BP")) {
				int a=rcr(getRegContent("BP"),dist);
				if(a==0) {
					ZF=true;
				}
				BP.content = a+"";
				
			}
			else if(destination.equalsIgnoreCase("SI")) {
				int a=rcr(getRegContent("SI"),dist);
				if(a==0) {
					ZF=true;
				}
				SI.content = a+"";
				
			}
			else if(destination.equalsIgnoreCase("DI")) {
				int a=rcr(getRegContent("DI"),dist);
				if(a==0) {
					ZF=true;
				}
				DI.content = a+"";
				
			}
			
		}//destination is address
		 else if(destType == 2) {
			int memoryIndex = getIndex(destination);
				
			int a=getAddress(destination) ;
			a=rcr(a,dist);
			if(a==0) {
				ZF=true;
			}
			memory[memoryIndex]=(a%256)+"";
			memory[memoryIndex+1]=(a/256) + "";
							
		 }
		//destination is variable
		 else if(destType==7) {
			for(int m=0;m<variables.size();m++) {
				if(variables.get(m).name.equals(destination)) {
					variables.get(m).content=rcr(variables.get(m).content,dist);
					if(variables.get(m).content==0) {
						ZF=true;
					}
					break;
					}
				}
			}
	}
	
	public void SHR(String destination,String i) {
		// shift right
		//cf degisebilir !!!!!!!!!!!!!!!!!!!!!
		
		int it=Integer.parseInt(i);
		int destType = typeCheck(destination);
		
		//destination is a reg
		if(destType==1) {
			if(destination.equalsIgnoreCase("ah")) {
				int p=getRegContent("AH");
				String s=Integer.toBinaryString(p);
				int cf=s.charAt(s.length()-it)-'0';
				if(cf==1) {
					CF=true;
				}else {
					CF=false;
				}
				int a=p>>it;
				if(a==0) {
					ZF=true;
				}
				
				AH.content = a + "";
				this.updateBigReg(AX, AH, AL);
			} else if(destination.equalsIgnoreCase("bh")) {
				int p=getRegContent("BH");
				int a=p>>it;
				String s=Integer.toBinaryString(p);
				int cf=s.charAt(s.length()-it)-'0';
				if(cf==1) {
					CF=true;
				}else {
					CF=false;
				}
				if(a==0) {
					ZF=true;
				}
				BH.content = a+"";
				this.updateBigReg(BX, BH, BL);
			} else if(destination.equalsIgnoreCase("ch")) {
				int p=getRegContent("CH");
				int a=(p>>it);
				String s=Integer.toBinaryString(p);
				int cf=s.charAt(s.length()-it)-'0';
				if(cf==1) {
					CF=true;
				}else {
					CF=false;
				}
				if(a==0) {
					ZF=true;
				}
				CH.content = a+"";
				this.updateBigReg(CX, CH, CL);
			} else if(destination.equalsIgnoreCase("dh")) {
				int p=getRegContent("DH");
				int a=(int)(p>>it);
				String s=Integer.toBinaryString(p);
				int cf=s.charAt(s.length()-it)-'0';
				if(cf==1) {
					CF=true;
				}else {
					CF=false;
				}
				if(a==0) {
					ZF=true;
				}
				DH.content = a+"";
				this.updateBigReg(DX, DH, DL);
			} else if(destination.equalsIgnoreCase("al")) {
				int p=getRegContent("AL");
				int a=(int)(p>>it);
				String s=Integer.toBinaryString(p);
				int cf=s.charAt(s.length()-it)-'0';
				if(cf==1) {
					CF=true;
				}else {
					CF=false;
				}
				if(a==0) {
					ZF=true;
				}
				AL.content = a+"";
				this.updateBigReg(AX, AH, AL);
			} else if(destination.equalsIgnoreCase("bl")) {
				int p=getRegContent("BL");
				int a=(int)(p>>it);
				String s=Integer.toBinaryString(p);
				int cf=s.charAt(s.length()-it)-'0';
				if(cf==1) {
					CF=true;
				}else {
					CF=false;
				}
				if(a==0) {
					ZF=true;
				}
				BL.content = a+"";
				this.updateBigReg(BX, BH, BL);
			} else if(destination.equalsIgnoreCase("cl")) {
				int p=getRegContent("CL");
				int a=(int)(p>>it);
				String s=Integer.toBinaryString(p);
				int cf=s.charAt(s.length()-it)-'0';
				if(cf==1) {
					CF=true;
				}else {
					CF=false;
				}
				if(a==0) {
					ZF=true;
				}
				CL.content = a+"";
				this.updateBigReg(CX, CH, CL);
			} else if(destination.equalsIgnoreCase("dl")) {
				int p=getRegContent("DL");
				String s=Integer.toBinaryString(p);
				int cf=s.charAt(s.length()-it)-'0';
				if(cf==1) {
					CF=true;
				}else {
					CF=false;
				}
				int a=(int)(p>>it);
				if(a==0) {
					ZF=true;
				}
				DL.content = a+"";
				this.updateBigReg(DX, DH, DL);
			}else if(destination.equalsIgnoreCase("ax")) {
				int p=getRegContent("AX");
				int a=(int)(p>>it);
				String s=Integer.toBinaryString(p);
				int cf=s.charAt(s.length()-it)-'0';
				if(cf==1) {
					CF=true;
				}else {
					CF=false;
				}
				if(a==0) {
					ZF=true;
				}
				AX.content = a+"";
				this.updateSmallReg(AX, AH, AL);
			} else if(destination.equalsIgnoreCase("bx")) {
				int p=getRegContent("BX");
				int a=(int)(p>>it);
				String s=Integer.toBinaryString(p);
				int cf=s.charAt(s.length()-it)-'0';
				if(cf==1) {
					CF=true;
				}else {
					CF=false;
				}
				if(a==0) {
					ZF=true;
				}
				BX.content =a+"";
				this.updateSmallReg(BX, BH, BL);
			} else if(destination.equalsIgnoreCase("cx")) {
				int p=getRegContent("CX");
				int a=(int)(p>>it);
				String s=Integer.toBinaryString(p);
				int cf=s.charAt(s.length()-it)-'0';
				if(cf==1) {
					CF=true;
				}else {
					CF=false;
				}
				if(a==0) {
					ZF=true;
				}
				CX.content =a+"";
				this.updateSmallReg(CX, CH, CL);
			} else if(destination.equalsIgnoreCase("dx")) {
				int p=getRegContent("DX");
				int a=(int)(p>>it);
				String s=Integer.toBinaryString(p);
				int cf=s.charAt(s.length()-it)-'0';
				if(cf==1) {
					CF=true;
				}else {
					CF=false;
				}
				if(a==0) {
					ZF=true;
				}
				DX.content = a+"";
				this.updateSmallReg(DX, DH, DL);
			}
			 else if(destination.equalsIgnoreCase("BP")) {
					int p=getRegContent("BP");
					int a=(int)(p>>it);
					String s=Integer.toBinaryString(p);
					int cf=s.charAt(s.length()-it)-'0';
					if(cf==1) {
						CF=true;
					}else {
						CF=false;
					}
					if(a==0) {
						ZF=true;
					}
					BP.content = a+"";
					
				}
			 else if(destination.equalsIgnoreCase("SI")) {
					int p=getRegContent("SI");
					int a=(int)(p>>it);
					String s=Integer.toBinaryString(p);
					int cf=s.charAt(s.length()-it)-'0';
					if(cf==1) {
						CF=true;
					}else {
						CF=false;
					}
					if(a==0) {
						ZF=true;
					}
					SI.content = a+"";
					
				}
			 else if(destination.equalsIgnoreCase("DI")) {
					int p=getRegContent("DI");
					int a=(int)(p>>it);
					String s=Integer.toBinaryString(p);
					int cf=s.charAt(s.length()-it)-'0';
					if(cf==1) {
						CF=true;
					}else {
						CF=false;
					}
					if(a==0) {
						ZF=true;
					}
					DI.content = a+"";
					
				}
		
		//destination is an address	
		} else if(destType == 2) {
			int memoryIndex = getIndex(destination);
				
			int a=getAddress(destination) ;
			String s=Integer.toBinaryString(a);
			int cf=s.charAt(s.length()-it)-'0';
			if(cf==1) {
				CF=true;
			}else {
				CF=false;
			}
			a=(int)(a>>it);
			if(a==0) {
				ZF=true;
			}
			memory[memoryIndex]=(a%256)+"";
			memory[memoryIndex+1]=(a/256) + "";
		
		//destination is variable 
		} else if(destType==7) {
			for(int m=0;m<variables.size();m++) {
				if(variables.get(m).name.equals(destination)) {
					String s=Integer.toBinaryString(variables.get(m).content);
					int cf=s.charAt(s.length()-it)-'0';
					if(cf==1) {
						CF=true;
					} else {
						CF=false;
					}
					variables.get(m).content=(int)(variables.get(m).content>>it);
					if(variables.get(m).content==0) {
						ZF=true;
					}
					break;
				}
			}
		}
	}
	
	public void SHL(String destination,String i) {
		//shift left
		//cf degisebilir !'!!!!!!!!!!!!!!!!
		
		int it=Integer.parseInt(i);
		int destType = typeCheck(destination);
		//destination is a reg
					
		if(destType==1) {
			if(destination.equalsIgnoreCase("ah")) {
				int p=getRegContent("AH");
				String s=Integer.toBinaryString(p);
				if(s.charAt(it)-'0'==0) {
					CF=false;
				}else {
					CF=true;
				}
				int a=(int)(p*Math.pow(2.0,(double) it));
				if(a==0) {
					ZF=true;
				}else if(a>=256) {
					a=a%256;
				}
				AH.content = a + "";
				this.updateBigReg(AX, AH, AL);
			} else if(destination.equalsIgnoreCase("bh")) {
				int p=getRegContent("BH");
				String s=Integer.toBinaryString(p);
				if(s.charAt(it)-'0'==0) {
					CF=false;
				}else {
					CF=true;
				}
				int a=(int)(p*Math.pow(2.0,(double) it));
				if(a==0) {
					ZF=true;
				}else if(a>=256) {
					a=a%256;
				}
				BH.content = a+"";
				this.updateBigReg(BX, BH, BL);
			} else if(destination.equalsIgnoreCase("ch")) {
				int p=getRegContent("CH");
				String s=Integer.toBinaryString(p);
				if(s.charAt(it)-'0'==0) {
					CF=false;
				} else {
					CF=true;
				}
				int a=(int)(p*Math.pow(2.0,(double) it));
				if(a==0) {
					ZF=true;
				}else if(a>=256) {
					a=a%256;
				}
				CH.content = a+"";
				this.updateBigReg(CX, CH, CL);
			} else if(destination.equalsIgnoreCase("dh")) {
				int p=getRegContent("DH");
				String s=Integer.toBinaryString(p);
				if(s.charAt(it)-'0'==0) {
					CF=false;
				}else {
					CF=true;
				}
				int a=(int)(p*Math.pow(2.0,(double) it));
				if(a==0) {
					ZF=true;
				}else if(a>=256) {
					a=a%256;
				}
				DH.content = a+"";
				this.updateBigReg(DX, DH, DL);
			} else if(destination.equalsIgnoreCase("al")) {
				int p=getRegContent("AL");
				String s=Integer.toBinaryString(p);
				if(s.charAt(it)-'0'==0) {
					CF=false;
				}else {
					CF=true;
				}
				int a=(int)(p*Math.pow(2.0,(double) it));
				if(a==0) {
					ZF=true;
				}else if(a>=256) {
					a=a%256;
				}
				AL.content = a+"";
				this.updateBigReg(AX, AH, AL);
			} else if(destination.equalsIgnoreCase("bl")) {
				int p=getRegContent("BL");
				String s=Integer.toBinaryString(p);
				if(s.charAt(it)-'0'==0) {
					CF=false;
				}else {
					CF=true;
				}
				int a=(int)(p*Math.pow(2.0,(double) it));
				if(a==0) {
					ZF=true;
				}else if(a>=256) {
					a=a%256;
				}
				BL.content = a+"";
				this.updateBigReg(BX, BH, BL);
			} else if(destination.equalsIgnoreCase("cl")) {
				int p=getRegContent("CL");
				String s=Integer.toBinaryString(p);
				if(s.charAt(it)-'0'==0) {
					CF=false;
				}else {
					CF=true;
				}
				int a=(int)(p*Math.pow(2.0,(double) it));
				if(a==0) {
					ZF=true;
				}else if(a>=256) {
					a=a%256;
				}
				CL.content = a+"";
				this.updateBigReg(CX, CH, CL);
			} else if(destination.equalsIgnoreCase("dl")) {
				int p=getRegContent("DL");
				String s=Integer.toBinaryString(p);
				if(s.charAt(it)-'0'==0) {
				CF=false;
				}else {
					CF=true;
				}
				int a=(int)(p*Math.pow(2.0,(double) it));
				if(a==0) {
					ZF=true;
				}else if(a>=256) {
					a=a%256;
				}
				DL.content = a+"";
				this.updateBigReg(DX, DH, DL);
			}else if(destination.equalsIgnoreCase("ax")) {
				int p=getRegContent("AX");
				String s=Integer.toBinaryString(p);
				if(s.charAt(it)-'0'==0) {
					CF=false;
				}else {
					CF=true;
				}
				int a=(int)(p*Math.pow(2.0,(double) it));
				if(a==0) {
					ZF=true;
				}else if(a>=65536) {
					a=a%65536;
				}
				AX.content = a+"";
				this.updateSmallReg(AX, AH, AL);
			} else if(destination.equalsIgnoreCase("bx")) {
				int p=getRegContent("BX");
				String s=Integer.toBinaryString(p);
				if(s.charAt(it)-'0'==0) {
					CF=false;
				}else {
					CF=true;
				}
				int a=(int)(p*Math.pow(2.0,(double) it));
				if(a==0) {
					ZF=true;
				} else if(a>=65536) {
					a=a%65536;
				}
				BX.content =a+"";
				this.updateSmallReg(BX, BH, BL);
			} else if(destination.equalsIgnoreCase("cx")) {
				int p=getRegContent("CX");
				String s=Integer.toBinaryString(p);
				if(s.charAt(it)-'0'==0) {
					CF=false;
				}else {
					CF=true;
				}
				int a=(int)(p*Math.pow(2.0,(double) it));
				if(a==0) {
					ZF=true;
				}else if(a>=65536) {
					a=a%65536;
				}
				CX.content =a+"";
				this.updateSmallReg(CX, CH, CL);
			} else if(destination.equalsIgnoreCase("dx")) {
				int p=getRegContent("DX");
				String s=Integer.toBinaryString(p);
				if(s.charAt(it)-'0'==0) {
					CF=false;
				}else {
					CF=true;
				}
				int a=(int)(p*Math.pow(2.0,(double) it));
				if(a==0) {
					ZF=true;
				}else if(a>=65536) {
					a=a%65536;
				}
				DX.content = a+"";
				this.updateSmallReg(DX, DH, DL);
			
			} else if(destination.equalsIgnoreCase("BP")) {
				int p=getRegContent("BP");
				String s=Integer.toBinaryString(p);
				if(s.charAt(it)-'0'==0) {
					CF=false;
				}else {
					CF=true;
				}
				int a=(int)(p*Math.pow(2.0,(double) it));
				if(a==0) {
					ZF=true;
				}else if(a>=65536) {
					a=a%65536;
				}
				BP.content = a+"";
	
			} else if(destination.equalsIgnoreCase("SI")) {
				int p=getRegContent("SI");
				String s=Integer.toBinaryString(p);
				if(s.charAt(it)-'0'==0) {
					CF=false;
				}else {
					CF=true;
				}
				int a=(int)(p*Math.pow(2.0,(double) it));
				if(a==0) {
					ZF=true;
				}else if(a>=65536) {
					a=a%65536;
				}
				SI.content = a+"";
					
			} else if(destination.equalsIgnoreCase("DI")) {
				int p=getRegContent("DI");
				String s=Integer.toBinaryString(p);
				if(s.charAt(it)-'0'==0) {
					CF=false;
				}else {
					CF=true;
				}
				int a=(int)(p*Math.pow(2.0,(double) it));
				if(a==0) {
					ZF=true;
				}else if(a>=65536) {
					a=a%65536;
				}
				DI.content = a+"";
			}
				
		//destination is an address
		} else if(destType == 2) {
			 int memoryIndex = getIndex(destination);
					
				int num=getAddress(destination) ;
				String s=Integer.toBinaryString(num);
				if(s.charAt(it)-'0'==0) {
					CF=false;
				}else {
					CF=true;
				}
				num=(int)(num*Math.pow(2.0,(double) it));
				if(num==0) {
					ZF=true;
				}
				memory[memoryIndex]=(num%256)+"";
				memory[memoryIndex+1]=(num/256) + "";
			
		//destination is a variable
		} else if(destType==7) {
			for(int m=0;m<variables.size();m++) {
				if(variables.get(m).name.equals(destination)) {
					String s=Integer.toBinaryString(variables.get(m).content);
					if(s.charAt(it)-'0'==0) {
						CF=false;
					}else {
						CF=true;
					}
					variables.get(m).content=(int)(variables.get(m).content*Math.pow(2.0,(double) it));
					if(variables.get(m).content==0) {
					ZF=true;
					}
				break;
				}
			}
		}
	}
	
	public void PUSH(String source, boolean error) {
		//sadece word pushlanabilir bunu kontrol etmeliyiz
		int sourceType = typeCheck(source);
		int sourceContent = 0;
		
		//SOURCE IS A REG
		if(sourceType == 1) {
				
			if(!source.equalsIgnoreCase("di") || !source.equalsIgnoreCase("si")|| !source.equalsIgnoreCase("bp") || !source.equalsIgnoreCase("ax") || !source.equalsIgnoreCase("bx") || !source.equalsIgnoreCase("cx") || !source.equalsIgnoreCase("dx")) {
				error = true;
				System.out.println("error");
				return;
			} else {
				sourceContent = getRegContent(source);
			}
		//SOURCE IS A MEMORY OFFSET
		} else if(sourceType == 2) {
			sourceContent = getAddress(source);
				
		//SOURCE IS A HEX
		} else if(sourceType == 3) {
			int content = 0;
			
			if(source.endsWith("h") || source.endsWith("H")) {
				content = Integer.parseInt(source.substring(0, source.length() - 1), 16);
			} else {
				content = Integer.parseInt(source, 16);
			}
		
			if(content < 256) {
				error = true;
				System.out.println("error");
				return;
			} else {
				sourceContent = content;
			}
			
		//SOURCE IS AN INT	
		} else if(sourceType == 4) {
			int content=0;
			if(source.endsWith("d") || source.endsWith("d")) {
				content = Integer.parseInt(source.substring(0, source.length() - 1));
			} else {
				content = Integer.parseInt(source);
			}
						
			if(content < 256) {
				///////error
			} else {
				sourceContent = content;
			}
		//SOURCE IS AN OFFSETVAR
		} else if(sourceType == 5) {
			String varName = source.substring(7);
			
			for(int i=0;i<variables.size();i++) {
				if(variables.get(i).name.equals(varName)) {
					if(variables.get(i).size == 1) {
						////error
					} else {
						sourceContent = variables.get(i).content;
					}
				}
			}
		//SOURCE IS A CHAR
		} else if(sourceType == 6) {
			error = true;
			System.out.println("error");
			return;
		} else if(sourceType == 7) {
			
			for(int i=0;i<variables.size();i++) {
				if(variables.get(i).name.equals(source)) {
					if(variables.get(i).size == 1) {
						error = true;
						System.out.println("error");
						return;
					} else {
						sourceContent = variables.get(i).content;
					}
				}
				
			}
		}
		//34
		//12
		memory[SP-2] = sourceContent % 256 +"";
		memory[SP-1]=  sourceContent / 256 +"";
		SP -= 2;
		SPhex = Integer.toHexString(SP);
	}
	
	public void POP(String destination, boolean error) {
		//r ye yazicaz
		
		//hic push islemi yapilmamissa ya da poplanacak bir sey yoksa SP ilk degerindeyse
		if(SP == 65534) {
			error = true;
			System.out.println("error");
			return;
		} else {
		
			String s1=memory[SP];
			String s2=memory[SP+1];
			int s11=Integer.parseInt(s1);
			int s12=Integer.parseInt(s2);
			int source=s12*256+s11;
			String s=source+"";
			memory[SP]=null;	
			SP+=2;
			SPhex=Integer.toHexString(SP);
			
			int destType = typeCheck(destination);
			if(destType == 1) {
						
			
				if(destination.equalsIgnoreCase("ax")) {
					AX.content = s;
					this.updateSmallReg(AX, AH, AL);
				} else if(destination.equalsIgnoreCase("bx")) {
					BX.content = s;
					this.updateSmallReg(BX, BH, BL);
				} else if(destination.equalsIgnoreCase("cx")) {
					CX.content = s;
					this.updateSmallReg(CX, CH, CL);
				} else if(destination.equalsIgnoreCase("dx")) {
					DX.content = s;
					this.updateSmallReg(DX, DH, DL);
				}else if(destination.equalsIgnoreCase("bp")) {
					BP.content = s;
					
				}else if(destination.equalsIgnoreCase("si")) {
					SI.content = s;
					
				}else if(destination.equalsIgnoreCase("di")) {
					DI.content = s;
				}
			
			//address
			} else if(destType == 2) {
				int memoryIndex = getIndex(destination);
				
				memory[memoryIndex]=s1;
				memory[memoryIndex+1]=s2;
			//variable
			} else if(destType==7) {
				for(int m=0;m<variables.size();m++) {
					if(variables.get(m).name.equals(destination)) {
						variables.get(m).content=Integer.parseInt(s);
						break;
					}
				}
			}
		}
	}
	
	public static void NOP() {
	
	}
	
	public void CMP(String one, String two) {
		
		int oneType = typeCheck(one);
		int oneContent=getSourceContent(one, oneType);
		
		int twoType = typeCheck(two);
		int twoContent=getSourceContent(two,twoType);
		
		
		if(oneContent > twoContent) {
			this.lastcmp = 1;
			CF = false;
			ZF = false;
		} else if(oneContent == twoContent) {
			this.lastcmp = 0;
			ZF = true;
		} else {
			this.lastcmp = -1;
			CF = true;
			ZF = false;
		}
	}
			
	public int JZ(String s) {
		// Jump Zero zero flaga bakilcak
		// labelleri nasil tanimlarsak
		
		if(ZF) {
			for(int i=0;i<labels.size();i++) {
				if(labels.get(i).toUpperCase().equals(s.toUpperCase())){
					return this.labelIndex.get(i);
				
				}
			}
		}
		return 0;
	}
	
	public int JNZ(String s) {
		//Jump Not Zero zero flaga bakilcak
		// labelleri nasil tanimlarsak
		if(!ZF) {
			for(int i=0;i<labels.size();i++) {
				if(labels.get(i).toUpperCase().equals(s.toUpperCase())){
					return this.labelIndex.get(i);
					
				}
			}
		}
		return 0;
	}
	
	public int JE(String s) {
		// Will jump if compared things are equal
		// labelleri nasil tanimlarsak
		if(this.lastcmp==0) {
			for(int i=0;i<labels.size();i++) {
				if(labels.get(i).toUpperCase().equals(s.toUpperCase())){
					return this.labelIndex.get(i);
				}
			}
		}
		return 0;
	}
	
	public int JNE(String s) {
		// Will jump if compared things are not equal
		// labelleri nasil tanimlarsak
		if(this.lastcmp!=0) {
			for(int i=0;i<labels.size();i++) {
				if(labels.get(i).toUpperCase().equals(s.toUpperCase())){
					return this.labelIndex.get(i);
				}
			}
		}
		return 0;
	}
	
	public int JA(String s) {
		// Will jump if the first thing is greater.
		// labelleri nasil tanimlarsak
		if(this.lastcmp==1) {
			for(int i=0;i<labels.size();i++) {
				if(labels.get(i).toUpperCase().equals(s.toUpperCase())){
					return this.labelIndex.get(i);
				}
			}
		}
		return 0;
	}
	
	public  int JAE(String s) {
		// birincisi buyuk ya da esit
		// labelleri nasil tanimlarsak
		if(this.lastcmp!=-1) {
			for(int i=0;i<labels.size();i++) {
				if(labels.get(i).toUpperCase().equals(s.toUpperCase())){
					return this.labelIndex.get(i);
				}
			}
		}
		return 0;
	}
	
	public int JB(String s) {
		// Will jump if the first thing is less.
		// labelleri nasil tanimlarsak
		if(this.lastcmp==-1) {
			for(int i=0;i<labels.size();i++) {
				if(labels.get(i).toUpperCase().equals(s.toUpperCase())){
					return this.labelIndex.get(i);
				}
			}
		}
		return 0;
	}
	
	public int JNAE(String s) {
		//birincisi buyuk ve esit degilse
		// labelleri nasil tanimlarsak
		if(this.lastcmp==-1) {
			for(int i=0;i<labels.size();i++) {
				if(labels.get(i).toUpperCase().equals(s.toUpperCase())){
					return this.labelIndex.get(i);
				}
			}
		}
		return 0;
	}
	
	public int JNB(String s) {
		// ikincisi buyuk degilse
		// labelleri nasil tanimlarsak
		if(this.lastcmp!=-1) {
			for(int i=0;i<labels.size();i++) {
				if(labels.get(i).toUpperCase().equals(s.toUpperCase())){
					return this.labelIndex.get(i);
				}
			}
		}
		return 0;
	}
	
	public int JNBE(String s) {
		//ikincisi buyuk ve esit degilse
		// labelleri nasil tanimlarsak
		if(this.lastcmp==1) {
			for(int i=0;i<labels.size();i++) {
				if(labels.get(i).toUpperCase().equals(s.toUpperCase())){
					return this.labelIndex.get(i);
				}
			}
		}
		return 0;
	}
	
	public int JNC(String s) {
		//Jump If No Carry carry flaga bakilcak
		// labelleri nasil tanimlarsak
		if(!CF) {
			for(int i=0;i<labels.size();i++) {
				if(labels.get(i).toUpperCase().equals(s.toUpperCase())){
					return this.labelIndex.get(i);
				}
			}
		}
		return 0;
	}
	
	public int JC(String s) {
		//Jump If Carry carry flaga bakilcak
		// labelleri nasil tanimlarsak
		if(CF) {
			for(int i=0;i<labels.size();i++) {
				if(labels.get(i).toUpperCase().equals(s.toUpperCase())){
					return this.labelIndex.get(i);
				}
			}
		}
		return 0;
	}

public int getRegContent(String source) {
		
		int oneContent = 0;
		
		if(source.equalsIgnoreCase("di")) {
			
			int oneType = typeCheck(DI.content);
			return getSourceContent(DI.content, oneType);
			
		} else if(source.equalsIgnoreCase("si")) {
			
			int oneType = typeCheck(SI.content);
			return getSourceContent(SI.content,oneType);
			
		} else if(source.equalsIgnoreCase("BP")) {
			
			int oneType = typeCheck(BP.content);
			return getSourceContent(BP.content,oneType);
			
		} else if(source.equalsIgnoreCase("ax")) {
			
			int oneType = typeCheck(AX.content);
			return getSourceContent(AX.content, oneType);
			
		} else if(source.equalsIgnoreCase("bx")) {
			
			int oneType = typeCheck(BX.content);
			return getSourceContent(BX.content,oneType);
			
		} else if(source.equalsIgnoreCase("cx")) {
			
			int oneType = typeCheck(CX.content);
			return getSourceContent(CX.content,oneType);	
			
		} else if(source.equalsIgnoreCase("dx")) {
			
			int oneType = typeCheck(DX.content);
			return getSourceContent(DX.content, oneType);		
			
		} else if(source.equalsIgnoreCase("al")) {
			
			int oneType = typeCheck(AL.content);
			return getSourceContent(AL.content,oneType);
			
		} else if(source.equalsIgnoreCase("bl")) {
			
			int oneType = typeCheck(BL.content);
			return getSourceContent(BL.content,oneType);
			
		} else if(source.equalsIgnoreCase("cl")) {
			
			int oneType = typeCheck(CL.content);
			return getSourceContent(CL.content,oneType);
			
		} else if(source.equalsIgnoreCase("dl")) {
			
			int oneType = typeCheck(DL.content);
			return getSourceContent(DL.content,oneType);
						
		} else if(source.equalsIgnoreCase("ah")) {
			
			int oneType = typeCheck(AH.content);
			return getSourceContent(AH.content, oneType);
			
		} else if(source.equalsIgnoreCase("bh")) {
			
			int oneType = typeCheck(BH.content);
			return getSourceContent(BH.content,oneType);
			
		} else if(source.equalsIgnoreCase("ch")) {
			
			int oneType = typeCheck(CH.content);
			return getSourceContent(CH.content,oneType);
						
		} else if(source.equalsIgnoreCase("dh")) {
			
			int oneType = typeCheck(DH.content);
			return getSourceContent(DH.content,oneType);
		}
		return 0;
	}
public int getAddress(String parameter) {
	
	int oneContent=0;
	boolean b = false;
	boolean w = false;
	String address = "";
	
	if(parameter.startsWith("[")) {
		address = parameter.substring(1, parameter.length()-1);
	} else {
		if(parameter.charAt(0) == 'b' || parameter.charAt(0) == 'B') {
			b = true;
		} else {
			w = true;
		}
		address = parameter.substring(2, parameter.length()-1);
	}
	
	//bosluklu yerleri atlamis olur
	Scanner scan = new Scanner(address);
	String s = scan.next();
	address = s;
	scan.close();
	
	//[reg] ise
	if(typeCheck(address) == 1) {
		oneContent = getRegContent(address);

	//[num] ise
	} else {
		int index = Integer.parseInt(address.substring(0, address.length() - 1), 16);
		address = memory[index];
		if(w) {
			oneContent += getAddress(memory[index+1])*256;
		}
			int oneType = typeCheck(address);
			
			//MEMORY ADDRESS
			if(oneType == 2) {
				oneContent = getAddress(address);
									
			//HEX
			} else if(oneType == 3) {
				if(parameter.endsWith("h") || parameter.endsWith("H")) {
					oneContent = Integer.parseInt(parameter.substring(0, parameter.length() - 1), 16);
				} else {
					oneContent = Integer.parseInt(parameter, 16);
				}
												
			//INT	
			} else if(oneType == 4) {
				if(parameter.endsWith("d") || parameter.endsWith("d")) {
					oneContent = Integer.parseInt(parameter.substring(0, parameter.length() - 1));
				} else {
					oneContent = Integer.parseInt(parameter);
				}
								
			//OFFSETVAR
			} else if(oneType == 5) {
				String varName = address.substring(7);
				
				for(int i=0;i<variables.size();i++) {
					if(variables.get(i).name.equals(varName)) {
						oneContent = variables.get(i).content;
						break;
					}
				}
				
			//CHAR
			} else if(oneType == 6) {
				oneContent = parameter.charAt(1);
				
			//Variable
			} else if(oneType == 7) {
				
				for(int i=0;i<variables.size();i++) {
					if(variables.get(i).name.equals(address)) {
						oneContent = variables.get(i).content;
						break;
					}
				}
			}
		}
	
	if(oneContent > 256) {
		if(b) {
			oneContent /= 256;
		}
	}
	return oneContent;
}
	
	public void updateSmallReg(Reg a,Reg b,Reg c) {
		// ax ah al order
		int ac=getRegContent(a.name);
		String s=Integer.toBinaryString(ac);
		if(s.length()<16) {
			int y=s.length();
			for(int i=0;i<16-y;i++) {
				s=0 + "" + s;
			}
		}
		String be=s.substring(0,8);
		String ce=s.substring(8);
		b.content=Integer.parseInt(be,2)+"";
		c.content=Integer.parseInt(ce,2)+"";
	}
	
	public void updateBigReg(Reg a,Reg b,Reg c) {
		// ax ah al order
		int bc=getRegContent(b.name);
		int cc=getRegContent(c.name);
		String sb=Integer.toBinaryString(bc);
		String sc=Integer.toBinaryString(cc);
		if(sb.length()<8) {
			int y=sb.length();
			for(int i=0;i<8-y;i++) {
				sb= "0" + sb;
			}
		}
		if(sc.length()<8) {
			int y=sc.length();
			for(int i=0;i<8-y;i++) {
				sc="0" + sc;
			}
		}
		String sa=sb + "" + sc;
		a.content=Integer.parseInt(sa,2)+"";
	}
		
	public static int typeCheck(String parameter) {
		
		//if register
		if(parameter.equalsIgnoreCase("di") || parameter.equalsIgnoreCase("bp") || parameter.equalsIgnoreCase("sp")|| parameter.equalsIgnoreCase("si")
				|| parameter.equalsIgnoreCase("ax") || parameter.equalsIgnoreCase("ah") || parameter.equalsIgnoreCase("al")
				|| parameter.equalsIgnoreCase("bx") || parameter.equalsIgnoreCase("bh") || parameter.equalsIgnoreCase("bl")
				|| parameter.equalsIgnoreCase("cx") || parameter.equalsIgnoreCase("ch") || parameter.equalsIgnoreCase("cl")
				|| parameter.equalsIgnoreCase("dx") || parameter.equalsIgnoreCase("dh") || parameter.equalsIgnoreCase("dl")) {
			
			return 1;
			
		//if memory offset
		} else if((parameter.startsWith("[") || parameter.startsWith("w[") || parameter.startsWith("W[") || parameter.startsWith("b[") 
				|| parameter.startsWith("B[")) && parameter.endsWith("]") ) {
			return 2;
		
		//if hex number
		} else if(parameter.endsWith("h") || parameter.endsWith("H") || parameter.startsWith("0")) {
			return 3;
						
		//if decimal
		} else if(parameter.endsWith("d") || parameter.endsWith("D") || (parameter.charAt(0) <= 57 && parameter.charAt(0) > 48)) {
			return 4;
			
		//if offset variable
		} else if(parameter.startsWith("offset") || parameter.startsWith("OFFSET")) {
			return 5;	
		
		//if char 
		} else if(parameter.charAt(0) == 39 && parameter.charAt(parameter.length()-1) == 39) {
			return 6;
			
		//if variable
		} else {
			for(int i=0;i<variables.size();i++) {
				if(variables.get(i).name.equals(parameter)) {
					return 7;
				}
			}
		}
		return 0;
	}
}