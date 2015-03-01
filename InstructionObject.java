package Assignment2;

public class InstructionObject {
	
	private String[] instruction;
	private String operation;
	private String subName;
	private String objName;
	private int value;
	
	public InstructionObject(String[] instruction){
		
		if(instruction.length < 2){
			this.operation = instruction[0];
		}
		else if(instruction.length == 3){
			this.operation = instruction[0];
			this.subName = instruction[1];
		}
		else{
			this.instruction = instruction;
			this.operation = instruction[0];
			this.subName = instruction[1];
			this.objName = instruction[2];
			if(instruction.length == 4 && isInteger(instruction[3])){
				this.value = Integer.parseInt(instruction[3]);

			}
			else{
				this.value = 0;
			}
		}
	}
	
	public InstructionObject(String operation, String subName, String objName) {
		this.operation = operation;
		this.subName = subName;
		this.objName = objName;
	}

	public InstructionObject(String operation, String subName, String objName, int value) {
		this.operation = operation;
		this.subName = subName;
		this.objName = objName;
		this.value = value;
	}

	public String[] getInstruction(){
		return this.instruction;
	}
	
	public void setInstruction(String[] instruction){
		this.instruction = instruction;
	}
	
	public String getOperation(){
		return this.operation;
	}
	
	public void setOperation(String operation){
		this.operation = operation;
	}
	
	public String getSubName(){
		return this.subName;
	}
	
	public void setSubName(String subName){
		this.subName = subName;
	}
	
	public String getObjName(){
		return this.objName;
	}
	
	public void setObjName(String objName){
		this.objName = objName;
	}
	
	public int getValue(){
		return this.value;
	}
	
	public void setValue(int value){
		this.value = value;
	}		

	public static boolean isInteger(String s) {
	     try { 
	         Integer.parseInt(s); 
	     } catch(NumberFormatException e) { 
	         return false; 
	     }
	     return true;
	 }
	
}
