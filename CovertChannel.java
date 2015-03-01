package Assignment2;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;



public class CovertChannel {
	
	//FileOutputStream for various instructions' output

	static ReferenceMonitor rm = new ReferenceMonitor();
	
	public ReferenceMonitor getReferenceMonitory(){
		return this.rm;
	}
	
	public void createSubject(String name, SecurityLevel lvl){
		SysSubject subject = new SysSubject(name);
		rm.createSubject(subject, lvl);
	}
	
	public static void main(String [] args) throws IOException {
		CovertChannel csys = new CovertChannel();
		FileInputStream fstream;
		DataInputStream dstream;
		BufferedReader br;
		File file;
		String line;
		String outfilename = "";
		boolean verbose;
		
		//String used to keep track of read return values
		String binString = "";
		// Various needed instructions
		InstructionObject createHal = new InstructionObject("create", "Hal", "obj");
		InstructionObject createLyle = new InstructionObject("create", "Lyle", "obj");
		InstructionObject writeLyle = new InstructionObject("write", "Lyle", "obj", 1);
		InstructionObject readLyle = new InstructionObject("read", "Lyle", "obj");
		InstructionObject destroyLyle = new InstructionObject("destroy", "Lyle", "obj");
		
		if(args.length == 1){
			outfilename = args[0];
			file = new File(args[0]);
			verbose = false;
		} else {
			outfilename = args[1];
			file = new File(args[1]);
			verbose = true;
		}
		//Log file
		PrintWriter log = new PrintWriter("log");
		
		//Output file
		fstream = new FileInputStream(file);
		dstream = new DataInputStream(fstream);
		br = new BufferedReader(new InputStreamReader(dstream));
		FileOutputStream fout = new FileOutputStream(outfilename + ".out");
		
		// LOW and HIGH are constants defined in the SecurityLevel 
        // class, such that HIGH dominates LOW.
		SecurityLevel low = SecurityLevel.LOW;
		SecurityLevel high = SecurityLevel.HIGH;
		
		// We add two subjects, one high and one low.
		csys.createSubject("Lyle", low);
		csys.createSubject("Hal", high);
		boolean addNewline = false;
		
		Stopwatch sw = new Stopwatch();
		while((line = br.readLine()) != null){
			if(!addNewline){
				//First line of text, don't add \n
				addNewline = true;
			}else{
				//not first line, add \n
				fout.write('\n');
			}
			byte[] buf = line.getBytes();
			InputStream istream = new ByteArrayInputStream(buf);
			int i;
			while((i = istream.read()) != -1){
				String string = Integer.toString(i, 2);
				//iterate through bits
				for(int j = 0; j < string.length(); j++){
					//Lyle sees 1, Hal doesn't previously create object
					if(string.charAt(j) == '1'){						
						rm.executeInstruction(createLyle, verbose, log);
						rm.executeInstruction(writeLyle, verbose, log);
						rm.executeInstruction(readLyle, verbose, log);
						rm.executeInstruction(destroyLyle, verbose, log);
					}else{
						//Lyle sees a 0 after the read, Hal created.
						rm.executeInstruction(createHal, verbose, log);
						rm.executeInstruction(createLyle, verbose, log);
						rm.executeInstruction(writeLyle, verbose, log);
						rm.executeInstruction(readLyle, verbose, log);
						rm.executeInstruction(destroyLyle, verbose, log);
					}
					if(rm.bite == 1){
						binString = binString + "1";
					}else{
						binString = binString + "0";
					}
				}
				int dec = Integer.parseInt(binString, 2);
				fout.write((char)dec);
				binString = "";				
			}
		}
		double time = sw.elapsedTime();
		//Size in bits
		long fileSize = file.length();
		System.out.println("File name: " + file.getName());
		System.out.println("File size: " + fileSize + " bytes");
		System.out.println("Bandwidth " + (fileSize*8)/time + " bits/ms");
		fout.close();
		br.close();
	}

}
