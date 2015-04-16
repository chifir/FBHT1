package cataloguer;

import java.util.TreeMap;

/**
 *  ArtistsCatalogue is wrapper for TreeMap<Artitst_Name, Albums_Catalogue>
 *
 */
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
	/**
	 * 
	 * @return array of Artist names
	 */
	public String[] getArtists(){
		String[] keys = (String[])artistsMap.keySet().toArray(new String[0]);
		return keys;
	}
	
	/**
	 * 
	 * @return albums catalog for artist
	 */
	public AlbumsCatalogue getAlbumsCatalogue(String artist){
		return artistsMap.get(artist);
	}
}
