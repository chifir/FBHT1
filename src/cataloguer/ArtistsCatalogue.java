package cataloguer;

import java.util.TreeMap;

public class ArtistsCatalogue {
	private TreeMap<String, AlbumsCatalogue> artistsMap;
	
	public ArtistsCatalogue(){
		this.artistsMap = new TreeMap<String, AlbumsCatalogue>();
	}
	
	public void add(Track track){
		String artistName = track.getArtist();
		
		AlbumsCatalogue albums = artistsMap.get(artistName);
		if (albums == null){
			albums = new AlbumsCatalogue();
			albums.addTrack(track);
			artistsMap.put(artistName, albums);
		}else {
			albums.addTrack(track);
		}
	}
	
	public String[] getArtists(){
		String[] keys = (String[])artistsMap.keySet().toArray(new String[0]);

		return keys;
	}
	
	public AlbumsCatalogue getAlbumsCatalogue(String artist){
		return artistsMap.get(artist);
	}
}
