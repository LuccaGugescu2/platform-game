package game.data;


import java.io.*;


public class GestoreSalvataggio {
	
	public static void SalvaSuFile(String nome) throws IOException {
		
		
		File applicationDir = new File("src/fileData");
		
		if(!applicationDir.exists() && !applicationDir.isDirectory()) {
			applicationDir.mkdir();
		}else {System.out.println("directory already exists");}
		
		
		File file = new File(applicationDir + "/" + nome + ".txt");
		
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
	
	
	
	
	
}
