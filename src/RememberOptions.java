import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

public class RememberOptions {

	private Properties property = new Properties();
	private static File file = new File("settings.properties");

	public void setDefaultOptions() {

		property.setProperty("sCO", "true");
		property.setProperty("sWO", "true");
		property.setProperty("sEO", "false");

	}

	public void setOptionsClose(String sCOValue) {

		property.setProperty("sCO", sCOValue);
	}

	public void setOptionsWrap(String sWOValue) {
		property.setProperty("sWO", sWOValue);
	}

	public void setOptionsEnter(String sEOValue) {
		property.setProperty("sEO", sEOValue);
	}

	public void saveOptions() {

		try {
			FileOutputStream saveFile = new FileOutputStream(file);
			property.store(saveFile, "Ustawienia check box");

			saveFile.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,
					"B³¹d Wejœcia/Wyjœcia\nNie mogê zapisaæ ustawieñ\nSprawdŸ prawa do folderu/pliku", "B³¹d zapisz",
					JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "B³¹d Wejœcia/Wyjœcia", "B³¹d zapisz", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void loadOptions() {

		try {
			FileInputStream loadFile = new FileInputStream(file);
			property.load(loadFile);

			loadFile.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,
					"B³¹d Wejœcia/Wyjœcia\nNie mogê odczytaæ ustawieñ.\nSprawdŸ prawa do folderu/pliku", "B³¹d odczytu",
					JOptionPane.ERROR_MESSAGE);
			setDefaultOptions();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "B³¹d Wejœcia/Wyjœcia", "B³¹d za³aduj", JOptionPane.ERROR_MESSAGE);
			setDefaultOptions();
		}
	}

	public void checkFile() {

		if (file.exists() == true)
			loadOptions();

		else if (file.exists() == false)
			setDefaultOptions();
	}

	public boolean getClosingSettings() {

		boolean statusClosingOption = Boolean.parseBoolean(property.getProperty("sCO"));

		return statusClosingOption;
	}

	public boolean getWrapingSettings() {

		boolean statusWrapingOption = Boolean.parseBoolean(property.getProperty("sWO"));

		return statusWrapingOption;
	}

	public boolean getEnterSettings() {

		boolean statusEnterOption = Boolean.parseBoolean(property.getProperty("sEO"));

		return statusEnterOption;
	}

	public static void main(String[] args) {

	}

}
