package kl.tools.load.config.lib;

public class ddd{
	public static void main(){
		System.load("/storage/emulated/0/config/lib/libddd_sub.so");
		int a=5,b=4;
		int result=add(a,b);
		System.out.println("java result:"+result);
		System.out.println("native result:"+sub(8,3));
	}
	public static int add(int a ,int b){
		
		return a+b;
	}
	 
	 public static native int sub(int a,int b);
	
	
}