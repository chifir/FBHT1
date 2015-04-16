package cataloguer;

import java.io.File;
import java.io.PrintStream;

/**
 * App - is entry point.
 *
 */
public class App {

	public static void main(String[] args) {
		PrintStream ps = System.out;
	
		ps.println("Mp3Scanner. FBHT1. ");
		ps.println("mp3agic(https://github.com/mpatric/mp3agic) library(Id3 tag parsing) is used");
		
		if (args.length == 0){
			usage(ps);
		}else{
			for (String path: args){
				File dir = new File(path);
				
				if (dir.exists() && dir.isDirectory()){
					if (dir.canRead() && dir.canWrite()){
						Mp3Scanner sc = new Mp3Scanner(path);
						ps.println("Scanning dir: " + dir.getAbsolutePath()); 
						sc.scan();
						sc.saveToHtml();
					}else {
						ps.println("Warning:");
						if (!dir.canRead()) ps.println("Can not read dir: " + dir.getAbsolutePath());
						if (!dir.canWrite()) ps.println("Can not write dir: " + dir.getAbsolutePath());
						usage(ps);
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
		usage.append("Output:\n");
		usage.append("1) App generates html representation of the catalogue and stores it in dir/catalogue.html\n");
		usage.append("2) App generates pls playlist of the catalogue and stores it in dir/playlist.pls\n");
		ps.println(usage.toString());
	}

}
