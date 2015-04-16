package cataloguer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * HtmlCatalogue generates html-catalogue
 *
 */
public class HtmlCatalogue {
	private StringBuffer page;
	private String path;
	private String fileName;
	
	public HtmlCatalogue(String path){
		page = new StringBuffer();
		this.path = path;
		this.fileName = path + File.separator +"catalogue.html";
		System.out.println("Save to: " + this.fileName);
	}
	
	public void createHead(){
		page.append("<html>	<head> <title>Mp3 Catalogue</title></head><body>");
		page.append("<h1 align = \"center\"> Scanned dir : " + this.path + "</h1><br/>");
	}
	
	public void createStat(long trackCount, long dirCount, String plsPath){
		page.append("<h3 align = \"center\">Tracks: " + trackCount + " Directories: " + dirCount + " <a href=\"file://"+plsPath+"\">Playlist</a>"+ "</h3><br/>");
	}
	
	public void createFoot(){
		page.append("</body></html>");
	}
	
	public void addArtist(String artist){
		page.append("<h2 align = \"center\">"+artist + "</h2><br/>");
	}
	
	public void addAlbum(String album, ArrayList<Track> tracks){
		page.append("<tr><h3>" + album + "</h3></tr>");
		page.append("<table border = \"1\" style = \"width:100%\">");
		page.append("<tr><td>Title</td><td>Length</td><td>Link</td></tr>");
		for (Track track : tracks){
			page.append("<tr><td>" + track.getTitle() + "</td><td>" + track.getLengthInMin() 
					+ "</td><td> <a href = \"" + Paths.get(track.getPath()).toUri().toString() + "\">Play</a></td></tr>");
		}
		
		page.append("</table><br/>");
	}
	
	public void write(){
		
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(this.fileName));
			bw.write(page.toString());
			bw.flush();
			bw.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
