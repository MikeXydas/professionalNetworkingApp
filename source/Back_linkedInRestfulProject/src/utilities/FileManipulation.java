package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.FileUtils;

public class FileManipulation {

	//Gets the picture from the JSON, saves it locally (predetermined path) and returns the savePath
	public String ReceiveFile(String pathToSave, byte[] fileBytes) {
			try {
				FileUtils.writeByteArrayToFile((new File(pathToSave)), fileBytes);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		return pathToSave;
	}
	
	//Returns a BASE64 encoded String
	public String SendFile (String fileUrl) throws IOException {
		InputStream in = new FileInputStream(new File(fileUrl));
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder out = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			out.append(line);
		}
		reader.close();
		return out.toString();
	}
}
