package Assignment2;


public class SysObject {

        private String name;
        private int value;
        
        public SysObject(){
        
            this.name = "";
            this.value = 0;
        
        }
        public SysObject(String name) {
        	
        	this.name = name;
        	this.value = 0;
        	
		}
        
        public SysObject(String name, int value){
        
            this.name = name;
            this.value = value;
        
        }

		public String getName(){
        
            return this.name;
        
        }
        
        public void setName(String name){
        
            this.name =  name;
        
        }
        
        public int getValue(){
        
            return this.value;
        
        }
        
        public void setValue(int value){
        
            this.value = value;
        
        }
        
        

}