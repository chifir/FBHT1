package cataloguer;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 *  AlbumsCatalogue is wrapper for TreeMap<Catalogue_Name, ArrayList of tracks>
 *
 */
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

	/**
	 * 
	 * @return array of album names
	 */
	public String[] getAlbumsName(){
		return (String[])albumMap.keySet().toArray(new String[0]);
	}	
	
	
	/**
	 * 
	 * @param name
	 * @return ArrayList of tracks for album "name"
	 */
	public ArrayList<Track> getAlbum(String name){
		return albumMap.get(name);
	}
}
