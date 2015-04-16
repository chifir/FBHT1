package cataloguer;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

/**
 * Mp3Scanner - сканирует директорию, находит mp3-файлы, добавляет их в каталог.
 * Модель поведения следующая:
 * 1) находится mp3-файл.
 * 2) определяются его теги
 * 3) файл добавляется в каталог:
 * 		каталог исполнителей (ArtistsCatalogue), если исполнитель уже есть в каталоге,
 * 		то ищем в каталоге его альбомов (AlbumsCatalogue) альбом данного трека, если
 * 		находим то добавляем в него трек, если нет то создаем нового исполнителя и альбом
 * 		для данного	трека   
 *
 */
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
	}
	
	public void scan(){
		this.scanDir(this.rootPath);
	}
	/**
	 * scanning dir
	 * @param path path to dir
	 */
	private void scanDir(String path){

		File dir = new File(path);
		File[] items = dir.listFiles();
		String fileName;
		scannedDirs.add(path);
		for(File item : items){
			if (item.isFile()){
				fileName = item.getAbsolutePath();
				if (item.canRead() && item.canWrite()){
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
							System.err.println("Something wrong in getting tags file: " + fileName);
							e.printStackTrace();
						}
					}
				}else {
					System.out.println("Warning:");
					if (!item.canRead()) System.out.println("Cat read file" + fileName);
					if (!item.canWrite()) System.out.println("Cat write file" + fileName);
				}
			}else if(item.isDirectory() && (this.scannedDirs.add(item.getAbsolutePath())) && item.canRead() && item.canWrite()){
				/*check whether the item is a directory, whether it was previously cataloging, can read and read it.
				 * */
				this.scanDir(item.getAbsolutePath());
			}
		}
		
	}
	
	/**
	 * create Track object with id3v1 tags  
	 */
	private Track createTrack(Mp3File file, ID3v1 tags){
		Track track = new Track();
		track.setArtist(unknown(tags.getArtist(), "Unknown Artist"));
		track.setAlbum(unknown(tags.getAlbum(), "Unknown Album"));
		track.setTitle(unknown(tags.getTitle(), "Unknown Title"));
		track.setLength(file.getLengthInSeconds());

		return track;
	}
	
	
	/**
	 * create Track object with id3v2 tags  
	 */	
	private Track createTrack(Mp3File file, ID3v2 tags){
		Track track = new Track();
		track.setArtist(unknown(tags.getArtist(), "Unknown Artist"));
		track.setAlbum(unknown(tags.getAlbum(), "Unknown Album"));
		track.setTitle(unknown(tags.getTitle(), "Unknown Title"));
		track.setLength(file.getLengthInSeconds());
		
		return track;
	}

	/**
	 * if the length of the tag is zero, or is equal to null, it is set template.
	 */
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
