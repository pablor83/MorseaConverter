import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MorseaCode {

	private static String[] morseaCode = new String[] { "/", " ", ".-", "-...", "-.-.", "-..", ".", "..-.", "--.",
	        "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-",
	        ".--", "-..-", "-.--", "--..", ".-.-", "-.-..", "..-..", ".-..-", "--.--", "---.", "...-...", "--..-.",
	        "--..-", "-----", ".----", "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----.", ".-.-.-",
	        "--..--", ".----.", ".-..-.", "..--.-", "---...", "-.-.-.", "..--..", "-.-.--", "-....-", ".-.-.", "-..-.",
	        "-.--.", "-.--.-", "-...-", ".--.-."};

	private static String[] alphabet = new String[] { " ", "/", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
	        "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "•", "∆", " ", "£", "—", "”",
	        "å", "Ø", "è", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".", ",", "'", "\"", "_", ":", ";", "?",
	        "!", "-", "+", "/", "(", ")", "=", "@"};

	private static Map<String, String> dataMapToMorse = new HashMap<>();
	private static Map<String, String> dataMapForAlphabet = new HashMap<>();

	private String[] dataLoadedIntoTheTable = null;
	private String rawUserData;
	private String usersDataProcessed;

	private StringBuilder conversionResultSB = new StringBuilder();

	private String conversionResult;
	
	public void ustawDaneUzytkownika(String data) {
		
		rawUserData = data;
				
	}
	
	public String pobierzWynik() {
		
		String result = conversionResult;
		
		return result;
		
	}

	void handlingNewLine() {

		for (int i = 0; i < morseaCode.length; i++) {
			dataMapToMorse.put(morseaCode[i], alphabet[i]);
			dataMapForAlphabet.put(alphabet[i], morseaCode[i]);
		}

		if (rawUserData.length() == 0) {
			conversionResultSB.append("Wprowadü dane ");

		}

		else {

			String[] tableHandlingNewLine = null;

			tableHandlingNewLine = rawUserData.split("\n");
			
			for (int i = 0; i < tableHandlingNewLine.length; i++) {
				usersDataProcessed = tableHandlingNewLine[i];

				conversionOfMorseAndAlphabet();

			}

		}
		

		conversionResult = conversionResultSB.toString();
		
		conversionResultSB.delete(0, conversionResultSB.length());

	}

	private void conversionOfMorseAndAlphabet() {

		String dataFromTheUser = usersDataProcessed;
		
		if(dataFromTheUser.matches("[ ]+")) {
			conversionResultSB.append(dataFromTheUser.replace(" ", "/ ")+"\n");
		}

		else if (dataFromTheUser.matches("[\\. \\- /]+")) {

			dataLoadedIntoTheTable = dataFromTheUser.replace("/", " / ").replace("  ", " ").split(" ");

			for (int i = 0; i < dataLoadedIntoTheTable.length; i++) {

				if (dataLoadedIntoTheTable[i].length() == 0) {

					conversionResultSB.append("");
				}

				else if (dataMapToMorse.get(dataLoadedIntoTheTable[i]) == null) {
					conversionResultSB.append("(nieznany znak)");

				} else {
					
					conversionResultSB.append(dataMapToMorse.get(dataLoadedIntoTheTable[i]));
					

				}

			}

			conversionResultSB.append("\n");

		}

		else if (dataFromTheUser.matches(".+")) {
			dataLoadedIntoTheTable = dataFromTheUser.toUpperCase().split("");

			for (int i = 0; i < dataLoadedIntoTheTable.length; i++) {

				if (dataMapForAlphabet.get(dataLoadedIntoTheTable[i]) == null) {
					conversionResultSB.append("(nieznany znak) ");

				}

				else {
					conversionResultSB.append(dataMapForAlphabet.get(dataLoadedIntoTheTable[i])+" ");

				}
				
			}
			
			conversionResultSB.setLength(conversionResultSB.length() - 1);
			conversionResultSB.append("\n");
			

		}

		else if (dataFromTheUser.length() == 0) {

			conversionResultSB.append("\n");
			

		}
		
		
		
	}	

}
