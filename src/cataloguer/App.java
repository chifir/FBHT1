package cataloguer;

import java.io.File;
import java.io.PrintStream;

public class App {

	public static void main(String[] args) {
		PrintStream ps = System.out;
	
		ps.println("Mp3Scanner by chifir");
		
		if (args.length == 0){
			usage(ps);
		}else{
			for (String path: args){
				File dir = new File(path);
				
				if (dir.exists() && dir.isDirectory()){
					if (dir.canRead() && dir.canWrite()){
						Mp3Scanner sc = new Mp3Scanner(path);
						ps.println("Scanned dir: " + dir.getAbsolutePath()); 
						sc.scan();
						sc.saveToHtml();
					}else {
						ps.println("Warinig:");
						if (!dir.canRead()) ps.println("Can not read dir: " + dir.getAbsolutePath());
						if (!dir.canWrite()) ps.println("Can not write dir: " + dir.getAbsolutePath());
					}
				}else{
					ps.println("Warning:");
					ps.println(path +" is not directory");
					usage(ps);
				}
			}
		}
	}
	
	public static void usage(PrintStream ps){
		StringBuffer usage = new StringBuffer();
		usage.append("usage:\n");
		usage.append("App dir_1 dir_2 ... dir_n\n");
		ps.println(usage.toString());
	}

}
