package cataloguer;

import java.util.ArrayList;

public class Catalogue {
	private ArtistsCatalogue artists;
	
	public Catalogue(ArtistsCatalogue artists){
		this.artists = artists;
	}
	
	public void print(){
		String[] artistNames = artists.getArtists();
		
		for(String artist : artistNames){
			AlbumsCatalogue albums = artists.getAlbumsCatalogue(artist);
			String[] albumNames = albums.getAlbumsName();
			for(String albumName : albumNames){
				ArrayList<Track> tracks = albums.getAlbum(albumName);
				for(Track track : tracks){
					StringBuffer sb = new StringBuffer();
					sb.append("\ttitle: " + track.getTitle() + "\n");
					sb.append("\tlength: " + track.getLength() + "\n");
					sb.append("\tpath: " + track.getPath());
					//System.out.println(sb.toString());
				}
			}
		}
	}
	
	public void writeHtmlCatalogue(String path, long trackCount, long dirCount){
		HtmlCatalogue html = new HtmlCatalogue(path);
		html.createHead();
		html.createStat(trackCount, dirCount);
		String[] artistNames = artists.getArtists();
		
		for(String artist : artistNames){
			html.addArtist(artist);
			AlbumsCatalogue albums = artists.getAlbumsCatalogue(artist);
			String[] albumNames = albums.getAlbumsName();
			for(String albumName : albumNames){
				
				ArrayList<Track> tracks = albums.getAlbum(albumName);
				html.addAlbum(albumName, tracks);			
			}
		}
		
		html.createFoot();
		html.write();
		
		
	}
}

