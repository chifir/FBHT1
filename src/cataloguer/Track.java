package cataloguer;

/**
 * Just Track object with tag properties.
 *
 */
public class Track {
	private String artist;
	private String album;
	private String title;
	private long length;
	private String path;
	
	public String getArtist() {
		return artist;
	}
	
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	public String getAlbum() {
		return album;
	}
	
	public void setAlbum(String album) {
		this.album = album;
	}
	
	public long getLength() {
		return length;
	}
	
	public void setLength(long length) {
		this.length = length;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getLengthInMin(){
		long sec;
		long min;
		String hrl;
		sec = this.length % 60;
		min = (this.length - sec)/60;
		hrl = min + " min " + sec + " sec";
		return hrl;
	}
	
}
