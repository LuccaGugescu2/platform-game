package game.data;


import java.io.*;


public class GestoreSalvataggio {
	
	private static File gamesDirectory = new File("src/fileData");
	
	
	/**
	 * salva su file la posizione del player e la vita
	 * @param nome
	 * @throws IOException
	 * @author montis
	 */
	public static void SalvaSuFile(String nome) throws IOException {
		
		isDirectory(gamesDirectory);
	
		File file = new File(gamesDirectory + "/" + nome + ".txt");
		
		if(!file.exists()) {
			file.createNewFile(); 
		}else {System.out.println("file already exists");}
		
		BufferedWriter buffer = null;
		
		try {
			buffer = new BufferedWriter(new FileWriter(file , false));
			
			buffer.write(String.valueOf(Config.playerPosition.getX()) + "\n"
				+ String.valueOf(Config.playerPosition.getY()) + "\n"
				+ String.valueOf(Config.health) + "\n"
				);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		
		}catch (IOException e) {
			e.printStackTrace();
		
		}finally {
			
			if(buffer != null) {
				try {
					buffer.close();
				
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * legge i valori sul file e li applica alle variabili di {@link Config}
	 * @param nome
	 * @throws IOException
	 */
	public static void LeggiDaFile (String nome) throws IOException {
		
		isDirectory(gamesDirectory);
		
		File file = new File(gamesDirectory + "/" + nome + ".txt");
		
		if(!file.exists()) {
			file.createNewFile(); 
		}else {System.out.println("file already exists");}
		
		BufferedReader buffer = null;
		
		
		try {
			buffer = new BufferedReader(new FileReader(file));
			
			String a = buffer.readLine();
		
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		
		}catch (IOException e) {
			e.printStackTrace();
		
		}finally {
			
			if(buffer != null) {
				try {
					buffer.close();
				
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * dice se la directory esiste o no e la crea in caso non esista
	 * @param f
	 * @return <b>true</b> se esiste e non crea nulla, <b>false</b> se non esiste e crea la directory
	 * @author montis
	 */
	public static boolean isDirectory (File f) {
		
		if(!f.exists() && !f.isDirectory()) {
			f.mkdir();
			return false;
		}
		else {
			System.out.println("directory already exists");
			return true;
		}
	}
}
