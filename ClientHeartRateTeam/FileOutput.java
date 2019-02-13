package ClientHeartRateTeam;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class FileOutput implements Observer {

	private String fileName;
	private BufferedWriter writer;

	public FileOutput() {
		
		// System.out.println( sdf.format(cal.getTime()) );
		//name currently hardcoded but needs to change as per simulator
		fileName = "output-HR" + getTimeStamp() + ".csv";
		try {
			writer = new BufferedWriter(new FileWriter(fileName, true));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object arg) {

		ClientSubscriber csObject = (ClientSubscriber) o;
		String data = csObject.getObject().toString();
		FileOutput fileOutput = csObject.getFile();
		//check stop clicked, close the filewriter
		if (data.equals("FIN")) {
			try {
				fileOutput.writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				fileOutput.writer.write(getTimeStamp()+" ");
				fileOutput.writer.write(data);
				fileOutput.writer.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	
	String getTimeStamp() {
		return new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
	}
	//To implement next format data for csv format
	// formatDataForCsv(String )

}
