package Assignment2;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;

public class ReferenceMonitor {
	
	private HashMap<SysSubject, SecurityLevel> subHashMap = new HashMap<SysSubject, SecurityLevel>();
	private HashMap<SysObject, SecurityLevel> objHashMap = new HashMap<SysObject,SecurityLevel>();
	
	//This integer is used to return a 0 or 1 after a read.
	public int bite;
	
	ObjectManager mgr = new ObjectManager();
	
	public void createSubject(SysSubject sub, SecurityLevel lvl){
		
		subHashMap.put(sub, lvl);
	}
	
	public void createNewObject(String name, SecurityLevel lvl){
		
		SysObject obj = new SysObject(name);
		objHashMap.put(obj, lvl);
	}
	
	// Secure semantics not necessary here
	private class ObjectManager{
		
		public void read(SysSubject sub, int temp) {
			sub.setTEMP(temp);
		}
		
		public void write(SysObject obj, int value) {
			obj.setValue(value);
		}
		
		public void destroy(SysObject obj){
			objHashMap.remove(obj);
		}
		
		
	}
	public void executeInstruction(InstructionObject instruction, boolean verbose, PrintWriter log){
		if(instruction.getOperation().equals("bad")){
			//System.out.println("Bad Instruction");
		}
		else {
			if (instruction.getOperation().equals("read")) {
				int val = 0;
				//even if the operation isn't successful, the instruction was still executed.
				if(verbose){
					log.write(instruction.getOperation() + " " + instruction.getSubName() + " " + instruction.getObjName() + "\n");
				}
				for (SysSubject Key: subHashMap.keySet()){
					if (Key.getName().equals(instruction.getSubName())){
						for (SysObject Key2: objHashMap.keySet()){
							if (Key2.getName().equals(instruction.getObjName())){
								if (subHashMap.get(Key).compareTo(objHashMap.get(Key2)) >= 0){
									val = Key2.getValue();
									//System.out.println(instruction.getSubName() + " reads " + instruction.getObjName());
									bite = 1;
									mgr.read(Key, val);
								}
								else {
									//System.out.println(instruction.getSubName() + " reads " + instruction.getObjName());
									bite = 0;
									mgr.read(Key, val);
								}
							}
						}
					}
				}
			}
			if (instruction.getOperation().equals("write")) {
				//even if the operation isn't successful, the instruction was still executed.
				if(verbose){
					log.write(instruction.getOperation() + " " + instruction.getSubName() + " " + instruction.getObjName() + " " + instruction.getValue() + "\n");
				}
				for (SysSubject Key: subHashMap.keySet()){
					if (Key.getName().equals(instruction.getSubName())){
						for (SysObject Key2: objHashMap.keySet()){
							if (Key2.getName().equals(instruction.getObjName())){
								if (subHashMap.get(Key).compareTo(objHashMap.get(Key2)) <= 0){
									//System.out.println(instruction.getSubName() + " writes value " + instruction.getValue() + " to " + instruction.getObjName());
									mgr.write(Key2, instruction.getValue());
								}
								else{
									//System.out.println(instruction.getSubName() + " writes value " + instruction.getValue() + " to " + instruction.getObjName());
								}
							}
						}
					}
				}
			}			
			if (instruction.getOperation().equals("create")) {
				//even if the operation isn't successful, the instruction was still executed.
				if(verbose){
					log.write(instruction.getOperation() + " " + instruction.getSubName() + " " + instruction.getObjName() + "\n");
				}
				for (SysObject Key: objHashMap.keySet()){
					if(Key.getName().equals(instruction.getObjName())){
						//object already exists, do nothing
						return;
					} 						
				}
				for (SysSubject Key2: subHashMap.keySet()){
					//create object with initial value 0
					if(Key2.getName().equals(instruction.getSubName())){
						SecurityLevel lvl = subHashMap.get(Key2);
						//just for debugging
						//System.out.println("New object " + instruction.getObjName() + " created with lvl: " + lvl.toString() + " (same as " + instruction.getSubName());
						createNewObject(instruction.getObjName(), lvl);
					}
				}
			}
			if (instruction.getOperation().equals("destroy")) {
				//even if the operation isn't successful, the instruction was still executed.
				if(verbose){
					log.write(instruction.getOperation() + " " + instruction.getSubName() + " " + instruction.getObjName() + "\n");
				}
				for (SysObject Key: objHashMap.keySet()){					
					if(Key.getName().equals(instruction.getObjName())){
						//object exists, try to destroy it
						for (SysSubject Key2: subHashMap.keySet()){
							// find the correct subject
							if(Key2.getName().equals(instruction.getSubName()))
								//check for "write" access
								if(subHashMap.get(Key2).compareTo(objHashMap.get(Key)) <= 0){
									mgr.destroy(Key);							
								}
								else{
									;								
								}
						
						}
					} 						
				} 
			}
		}

	}
	
	
	public void printState() {
		
		System.out.println("The current state is:");
		for (SysObject oKey: objHashMap.keySet()){
			System.out.println("\t" + oKey.getName() + " has value: " + oKey.getValue());
		}
		
		for (SysSubject sKey: subHashMap.keySet()){
			System.out.println("\t" + sKey.getName() + " has recently read: " + sKey.getTEMP());
		}	
		
		System.out.println();
	}
	
}
