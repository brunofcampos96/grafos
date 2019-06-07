package grafos;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		try {
			ArrayList<String[]> alunoArea = readAlunoAreaFile();
			int quantAlunos = alunoArea.size();
			String[][] dissimilaridade = readDissimilaridadeFile();
			Integer[][] grafo = new Integer[quantAlunos][quantAlunos];
			for(int i = 0; i < quantAlunos; i++) {
				int materiaLinha = Integer.parseInt(alunoArea.get(i)[1]) -1 ;
				for(int k = 0; k < quantAlunos; k ++) {
					Integer dissimilaridadeMaterias = 0;
					int materiaColuna = Integer.parseInt(alunoArea.get(k)[1]) - 1;
					if(materiaLinha < materiaColuna) {
						dissimilaridadeMaterias = Integer.parseInt(dissimilaridade[materiaLinha][materiaColuna]);
					}else {
						dissimilaridadeMaterias = Integer.parseInt(dissimilaridade[materiaColuna][materiaLinha]);
					}
					grafo[i][k] = dissimilaridadeMaterias;
					 
				}
			}
			
			String[][] grafoPrim = primMST(grafo, quantAlunos);
			
			int quantProf = 0;
			Scanner sc = new Scanner(System.in);
			System.out.print("Informe quantos professores estão disponíveis: ");
			quantProf = sc.nextInt();
			if(quantProf > quantAlunos) {
				sc.close();
				throw new Exception("Não pode haver mais professores do que alunos");
			}
				
			for(int l = 0; l < quantProf; l++) {
				Integer x = 0, y = 0, max = -1;
				for(int i = 0; i < grafoPrim.length; i++) {
					for(int k = 0; k < grafoPrim.length; k++) {
						if(grafoPrim[i][k] != null && Integer.parseInt(grafoPrim[i][k]) > max) {
							max = Integer.parseInt(grafoPrim[i][k]);
							x = i;
							y = k;
						}
					}
				}
				grafoPrim[x][y] = null;
			}
			
			
			sc.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static ArrayList<String[]> readAlunoAreaFile(){
		ArrayList<String[]> alunoArea = new ArrayList<String[]>();
		try(BufferedReader br = new BufferedReader(new FileReader("mocks\\alunoArea.txt"))) {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		    	alunoArea.add(line.split(" "));
		        line = br.readLine();
		    }
		    String everything = sb.toString();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return alunoArea;
	}
	
	private static String[][] readDissimilaridadeFile() throws IOException{
		String filePath = "mocks\\dissimilaridade.txt";
		int lines = getFileLines(filePath);
		int matrixActualLine = 0;
		String[][] matrizDiss = new String[lines][lines];
		try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		    	String[] grauDiss = line.split(" ");
		    	for(int i = 0; i < grauDiss.length; i++) {
		    		matrizDiss[matrixActualLine][i] = grauDiss[i];
		    	}
		    	matrixActualLine++;
		        line = br.readLine();
		    }
		    String everything = sb.toString();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return matrizDiss;
	}
	
	private static int getFileLines(String filePath) throws IOException {
		try {
			BufferedReader reader;
			reader = new BufferedReader(new FileReader(filePath));
			int lines = 0;
			while (reader.readLine() != null) lines++;
			reader.close();
			return lines;
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return 0;
	} 
  
    // Function to construct and 
    // print MST for a graph represented 
    // using adjacency matrix representation 
    static String[][] primMST(Integer[][] g, Integer vertices) 
    { 
    	Integer[] parent = new Integer[vertices];   
    	Integer[] k = new Integer[vertices];     
    	Integer[] mst = new Integer[vertices];    
    	Integer i, count,u,v;   
        for (i = 0; i < vertices; i++) {
        	k[i] = 9999;
        	mst[i] = 0;
        }
             
      
        k[0] = 0;       
        parent[0] = -1;   
      
        for (count = 0; count < vertices-1; count++)  
        {  
        
           u = minimum_key(k, mst, vertices);  
         mst[u] = 1;  
      
           for (v = 0; v < vertices; v++)  
      
             if (g[u][v] != null && mst[v] == 0 && g[u][v] <  k[v])  {
            	 parent[v]  = u;
            	 k[v] = g[u][v];  
             }
                
        }  

 	   String[][] grafoPrim = new String[vertices][vertices];
       for (i = 1; i < vertices; i++) {
    	   if( i < parent[i]) {
        	   grafoPrim[i][parent[i]] = g[i][parent[i]].toString();
    	   }else {
    		   grafoPrim[parent[i]][i] = g[i][parent[i]].toString();
    	   }
       } 
       return grafoPrim;
    } 
    
    static int minimum_key(Integer k[], Integer mst[], Integer vertices)  
    {  
       int minimum  = 99999, min = 0,i;  
       
       for (i = 0; i < vertices; i++)  
         if (mst[i] == 0 && k[i] < minimum ) {
        	 minimum  = k[i];
        	 min = i;  
         }
             
       
       return min;  
    }
    

}
