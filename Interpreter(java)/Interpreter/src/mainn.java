import java.util.*;
import java.io.*;
public class mainn {
	
	
	
	public static void main(String[] args) throws FileNotFoundException {
		
		File file=new File("src/input.txt");
		Scanner scanner=new Scanner(file);
		Program program=new Program();
		//Instructions
		
		int index=0;
		scanner.nextLine();
		while(scanner.hasNextLine()) {
			
			String s=scanner.nextLine();
			if(s.toLowerCase().startsWith("int 20h")) {
				program.memory[index]=s;
				index+=6;
				break;
			}
			
			if(s.contains(":")) {
				program.labels.add(s.substring(0, s.length()-1));
				program.labelIndex.add(index);
				
			}else {
				program.memory[index]=s;
				
				index+=6;
			}
		}
		//variable definition
		while(scanner.hasNextLine()) {
			String s=scanner.nextLine();
			if(!s.equalsIgnoreCase("code ends")) {
			Scanner scan=new Scanner(s);
			String name=scan.next();
			String type = scan.next();
									
			if(type.equalsIgnoreCase("db")) {
				program.memory[index]=name;
				String content=scan.next();
				char c=content.charAt(0);
				int con;
				if(c<'9' && c>'0') {
					if(content.endsWith("h") || content.endsWith("H")) {
						con = Integer.parseInt(content.substring(0, content.length() - 1), 16);
					} else {
						con = Integer.parseInt(content, 16);
					}
				}else {
					con=c;
				}
				Variable var=new Variable(name,1,index,con);
				Program.variables.add(var);
				index+=1;
				
			}
			else if(type.equalsIgnoreCase("dw")) {
				program.memory[index]=name;
				String content=scan.next();
				char c=content.charAt(0);
				int con;
				if(c<'9' && c>'0') {
					if(content.endsWith("h") || content.endsWith("H")) {
						con = Integer.parseInt(content.substring(0, content.length() - 1), 16);
					} else {
						con = Integer.parseInt(content, 16);
					}
				}else {
					con=c;
				}
				Variable var=new Variable(name,2,index,con);
				program.variables.add(var);
				index+=2;
			}
			}
		}
		run(program);
	}
	
	
	public static void run(Program p) {
		boolean error = false;
		
		for(int i=0;i<65535;i+=6) {
			String st=p.memory[i];
			String s="";
			for(int it=0;it<st.length();it++) {
				if(st.charAt(it)!=' ') {
					s+=st.charAt(it);
				}
			}
			
			if(s.substring(0,3).toUpperCase().equals("MOV")) {
				String s1=s.substring(3,s.indexOf(','));
				String s2=s.substring(s.indexOf(',')+1);
				p.MOV(s1, s2,error);
			}else if(s.substring(0,3).toUpperCase().equals("ADD")) {
				String s1=s.substring(3,s.indexOf(','));
				String s2=s.substring(s.indexOf(',')+1);
				p.ADD(s1, s2,error);
			}else if(s.substring(0,3).toUpperCase().equals("SUB")) {
				String s1=s.substring(3,s.indexOf(','));
				String s2=s.substring(s.indexOf(',')+1);
				p.SUB(s1, s2,error);
			}else if(s.substring(0,3).toUpperCase().equals("MUL")) {
				String s1=s.substring(3);
				p.MUL(s1, error);
			}else if(s.substring(0,3).toUpperCase().equals("DIV")) {
				String s1=s.substring(3);
				p.DIV(s1,error);
			}else if(s.substring(0,3).toUpperCase().equals("XOR")) {
				String s1=s.substring(3,s.indexOf(','));
				String s2=s.substring(s.indexOf(',')+1);
				p.XOR(s1, s2);
			}else if(s.substring(0,2).toUpperCase().equals("OR")) {
				String s1=s.substring(2,s.indexOf(','));
				String s2=s.substring(s.indexOf(',')+1);
				p.OR(s1, s2);
			}else if(s.substring(0,3).toUpperCase().equals("AND")) {
				String s1=s.substring(3,s.indexOf(','));
				String s2=s.substring(s.indexOf(',')+1);
				p.AND(s1, s2);
			}else if(s.substring(0,3).toUpperCase().equals("NOT")) {
				String s1=s.substring(3);
				p.NOT(s1);
			}else if(s.substring(0,3).toUpperCase().equals("RCL")) {
				String s1=s.substring(3,s.indexOf(','));
				String s2=s.substring(s.indexOf(',')+1);
				p.RCL(s1, s2);
			}else if(s.substring(0,3).toUpperCase().equals("RCR")) {
				String s1=s.substring(3,s.indexOf(','));
				String s2=s.substring(s.indexOf(',')+1);
				p.RCR(s1, s2);
			}else if(s.substring(0,3).toUpperCase().equals("SHL")) {
				String s1=s.substring(3,s.indexOf(','));
				String s2=s.substring(s.indexOf(',')+1);
				p.SHL(s1, s2);
			}else if(s.substring(0,3).toUpperCase().equals("SHR")) {
				String s1=s.substring(3,s.indexOf(','));
				String s2=s.substring(s.indexOf(',')+1);
				p.SHR(s1, s2);
			}else if(s.substring(0,4).toUpperCase().equals("PUSH")) {
				String s1=s.substring(4);
				p.PUSH(s1,error);
			}else if(s.substring(0,3).toUpperCase().equals("POP")) {
				String s1=s.substring(3);
				p.POP(s1,error);
			}else if(s.substring(0,3).toUpperCase().equals("NOP")) {
				p.NOP();
			}else if(s.substring(0,3).toUpperCase().equals("CMP")) {
				String s1=s.substring(3,s.indexOf(','));
				String s2=s.substring(s.indexOf(',')+1);
				p.CMP(s1, s2);
			}else if(s.substring(0,2).toUpperCase().equals("JZ")) {
				String s1=s.substring(2);
				i=p.JZ(s1);
			}else if(s.substring(0,3).toUpperCase().equals("JNZ")) {
				String s1=s.substring(3);
				i=p.JNZ(s1);
			}else if(s.substring(0,2).toUpperCase().equals("JE")) {
				String s1=s.substring(2);
				i=p.JE(s1);
			}else if(s.substring(0,3).toUpperCase().equals("JNE")) {
				String s1=s.substring(3);
				i=p.JNE(s1);
			}else if(s.substring(0,2).toUpperCase().equals("JA")) {
				String s1=s.substring(2);
				i=p.JA(s1);
			}else if(s.substring(0,3).toUpperCase().equals("JAE")) {
				String s1=s.substring(3);
				i=p.JAE(s1);
			}else if(s.substring(0,2).toUpperCase().equals("JB")) {
				String s1=s.substring(2);
				i=p.JB(s1);
			}else if(s.substring(0,4).toUpperCase().equals("JNAE")) {
				String s1=s.substring(4);
				i=p.JNAE(s1);
			}else if(s.substring(0,3).toUpperCase().equals("JNB")) {
				String s1=s.substring(3);
				i=p.JNB(s1);
			}else if(s.substring(0,4).toUpperCase().equals("JNBE")) {
				String s1=s.substring(4);
				i=p.JNBE(s1);
			}else if(s.substring(0,3).toUpperCase().equals("JNC")) {
				String s1=s.substring(3);
				i=p.JNC(s1);
			}else if(s.substring(0,2).toUpperCase().equals("JC")) {
				String s1=s.substring(2);
				i=p.JC(s1);
			}else if(s.toLowerCase().equals("int21h")) {
				p.INT21H();
			}else if(s.toLowerCase().equals("int20h")) {
				return;
			}
			
			if(error)
				break;
		}
	}
}