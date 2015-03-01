package Assignment2;


public class SysSubject{

        private String name;
        private int TEMP;
        
        public SysSubject(){
        
            this.name = "";
            this.TEMP = 0;
        
        }
        
        public SysSubject(String name){
        	this.name = name;
        	this.TEMP = 0;
        }
        
        public SysSubject(String name, int TEMP){
        
            this.name = name;
            this.TEMP = TEMP;
        
        }
        
        public String getName(){
        	return this.name;
        }
        
        public void setName(String name){
        	this.name = name;
        }
        
        public int getTEMP(){
        	return this.TEMP;
        }
        
        public void setTEMP(int TEMP){
        	this.TEMP = TEMP;
        }
        

}