package cataloguer;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

public class Mp3Scanner {
	private ArtistsCatalogue artists;
	private HashSet<String> scannedDirs;
	private String rootPath;
	private long trackCount;
	final private String ext;
	
	public Mp3Scanner(String path){
		this.ext = ".mp3";
		this.scannedDirs = new HashSet<String>();
		this.artists = new ArtistsCatalogue();
		this.rootPath = path;
		//this.scanDir(this.rootPath);
		//this.saveToHtml();		
	}
	
	public void scan(){
		this.scanDir(this.rootPath);
	}
	
	private void scanDir(String path){

		File dir = new File(path);
		File[] items = dir.listFiles();
		String fileName;
		scannedDirs.add(path);
		System.out.println("__ path: " + path);
		for(File item : items){
			if (item.isFile()){
				fileName = item.getAbsolutePath();
				if (fileName.endsWith(this.ext)){
					Track track;
					try{
						Mp3File file;
				
						file = new Mp3File(fileName);
						if (file.hasId3v1Tag()){
							ID3v1 tags = file.getId3v1Tag();
							track = this.createTrack(file, tags);
							track.setPath(fileName);
							this.artists.add(track);
							trackCount++;
						}else if (file.hasId3v2Tag()){
							ID3v2 tags = file.getId3v2Tag();
							track = this.createTrack(file, tags); 
							track.setPath(fileName);
							this.artists.add(track);
							trackCount++;
						}else if(file.hasCustomTag()){
							//System.out.println("custom");
						}
					
					} catch (UnsupportedTagException | InvalidDataException	| IOException e) {
						System.err.println("Something wrong in getting tags");
						e.printStackTrace();
					}
				}
			//}else if(item.isDirectory() && (!this.scannedDirs.add(item.getAbsolutePath()))){
			}else if(item.isDirectory()){
				System.out.println("path : " + item.getAbsolutePath() + "\nis.dir = " + item.isDirectory() + "\tscannedDirs = " + this.scannedDirs.add(item.getAbsolutePath()));
				this.scanDir(item.getAbsolutePath());
			}
		}
		
	}
	
	private Track createTrack(Mp3File file, ID3v1 tags){
		Track track = new Track();
		track.setArtist(unknown(tags.getArtist(), "Unknown Artist"));
		track.setAlbum(unknown(tags.getAlbum(), "Unknown Album"));
		track.setTitle(unknown(tags.getTitle(), "Unknown Title"));
		track.setLength(file.getLengthInSeconds());

/*		StringBuffer sb = new StringBuffer();
		sb.append(" artist: "+tags.getArtist());
		sb.append(" album: "+tags.getAlbum());
		sb.append(" title: "+tags.getTitle());
		sb.append(" length: " +file.getLengthInSeconds());
		
		System.out.println(sb.toString());
*/		return track;
	}
	
	
	
	private Track createTrack(Mp3File file, ID3v2 tags){
		Track track = new Track();
		track.setArtist(unknown(tags.getArtist(), "Unknown Artist"));
		track.setAlbum(unknown(tags.getAlbum(), "Unknown Album"));
		track.setTitle(unknown(tags.getTitle(), "Unknown Title"));
		track.setLength(file.getLengthInSeconds());
/*
		StringBuffer sb = new StringBuffer();
		sb.append(" artist: "+tags.getArtist());
		sb.append(" album: "+tags.getAlbum());
		sb.append(" title: "+tags.getTitle());
		sb.append(" length: " +file.getLengthInSeconds());
		
		System.out.println(sb.toString());
*/		
		return track;
	}

	private String unknown(String tag, String template){
		if ((tag == null) || (tag.length() == 0 )){
			return template;
		}
		
		return tag;
	}
	
	public void saveToHtml(){
		Catalogue cat = new Catalogue(artists);
		cat.print();
		cat.writeHtmlCatalogue(this.rootPath, trackCount, scannedDirs.size());
	}
}
