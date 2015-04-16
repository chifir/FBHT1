package cataloguer;

import java.util.ArrayList;
import java.util.TreeMap;

public class AlbumsCatalogue {
	private TreeMap<String, ArrayList<Track>> albumMap;

	public AlbumsCatalogue(){
		albumMap = new TreeMap<String, ArrayList<Track>>();
	}

	public void addTrack(Track track){
		String albumName = track.getAlbum();
		ArrayList<Track> album = albumMap.get(albumName); 
		if (album == null){
			album = new ArrayList<Track>();
			album.add(track);
			albumMap.put(albumName, album);
		}else{
			album.add(track);
		}
	}
	
	public String[] getAlbumsName(){
		return (String[])albumMap.keySet().toArray(new String[0]);
	}	
	
	public ArrayList<Track> getAlbum(String name){
		return albumMap.get(name);
	}
}
