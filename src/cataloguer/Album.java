package cataloguer;

import java.util.ArrayList;

public class Album {
	private String name;
	private ArrayList<Track> tracks;
	
	public Album(){
		tracks = new ArrayList<Track>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Track> getTracks() {
		return tracks;
	}
	public void setTracks(ArrayList<Track> tracks) {
		this.tracks = tracks;
	}	
	
	
}
