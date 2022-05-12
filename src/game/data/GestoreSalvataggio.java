package game.data;

import java.io.*;



public class GestoreSalvataggio {
	
	public static void SalvaSuFile(String nome) throws IOException {
		
		FileWriter file = new FileWriter(nome + ".txt");
		
		var buf = new BufferedWriter(file);
		
		
		buf.write(String.valueOf(Config.playerPositionX) + "\t"
				+ String.valueOf(Config.playerPositionY) + "\t"
				+ String.valueOf(Config.health) + "\t"
				);
		
		buf.flush();
	}
	
}
