package org.lunding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * The Serializer class is used to read and write and Event-object to/from the disk
 * @author Rasmus Lunding
 * @version 1.0
 * @since 2014-08-10
 */
public class Serializer {

	/**
	 * Write an event-object to the disk.<br>
	 * Give event and filename as arguments. If you want to place the file in a different location
	 * add the path before the actual filename. (e.g. ../[name] - will place the file in the parent folder)
	 * @param event
	 * @param filename
	 * @return Boolean if success
	 */
	public static boolean serializeEvent(Event event, String filename){
		try {
			FileOutputStream fileout = new FileOutputStream(filename + ".event");
			ObjectOutputStream oos = new ObjectOutputStream(fileout);
			oos.writeObject(event);
			oos.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Read in an Event object from the disk.<br>
	 * @param file
	 * @return Event
	 */
	public static Event deserialzeEvent(File file){
		try {
			FileInputStream filein = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(filein);
			Event event = (Event) ois.readObject();
			ois.close();
			return event;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
