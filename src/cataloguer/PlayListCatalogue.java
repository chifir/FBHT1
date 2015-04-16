package cataloguer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Generates playlist(pls format).
 *
 */
public class PlayListCatalogue {
	private ArtistsCatalogue artists;
	private StringBuffer pls;
	private String plsName;
	
	public PlayListCatalogue(ArtistsCatalogue artists, String path){
		this.artists = artists;
		this.plsName = path + File.separator +  "playlist.pls";
		this.pls = new StringBuffer();
	}
	
	public void create(){
		int trackCount = 0;
		pls.append("[playlist]\n");
		
		String[] artistNames = artists.getArtists();
		
		for(String artist: artistNames){
			AlbumsCatalogue albums = artists.getAlbumsCatalogue(artist);
			
			String[] albumNames = albums.getAlbumsName();
			
			for(String album: albumNames){
				ArrayList<Track> tracks = albums.getAlbum(album);
				
				for(Track track: tracks){
					trackCount++;
					pls.append("File"+trackCount+"="+track.getPath()+"\n");
					pls.append("Title"+trackCount+"="+track.getTitle()+"\n");
					pls.append("Length"+trackCount+"="+track.getLength()+"\n");
					
				}
			}
		}
		
		pls.append("NumberOfEntries=" + trackCount + "\n");
		pls.append("Version=2");
	}
	
	
	public void write(){
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(this.plsName));
			bw.write(pls.toString());
			bw.flush();
			bw.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public String getPath(){
		return this.plsName;
	}
	
}
