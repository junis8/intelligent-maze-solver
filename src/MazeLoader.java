import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class MazeLoader {   
   
    public static int[][] loadMazeFromText(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            List<String> lines = new ArrayList<>();
            String line;
            int maxCols = 0; 

            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {  
                    lines.add(line);
                    maxCols = Math.max(maxCols, line.length()); 
                }
            }

            int rows = lines.size();
            int[][] mazeGrid = new int[rows][maxCols];

            for (int i = 0; i < rows; i++) {
                String currentLine = lines.get(i);
                for (int j = 0; j < maxCols; j++) {
                    if (j < currentLine.length()) {
                        char c = currentLine.charAt(j);
                        if (c == '#') mazeGrid[i][j] = 1;  // Duvar
                        else if (c == '.') mazeGrid[i][j] = 0; // Yol
                        else if (c == 'S') mazeGrid[i][j] = 2; // Başlangıç
                        else if (c == 'E') mazeGrid[i][j] = 3; // Bitiş
                    } else {
                        mazeGrid[i][j] = 1; // Eksik sütunları duvar olarak kabul et
                    }
                }
            }

            // Labirenti terminalde yazdır (kontrol amaçlı)
            System.out.println("Labirent Yüklendi:");
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < maxCols; j++) {
                    System.out.print(mazeGrid[i][j] == 1 ? "█" : 
                                     (mazeGrid[i][j] == 2 ? "S" : 
                                     (mazeGrid[i][j] == 3 ? "E" : " ")));
                }
                System.out.println();
            }

            return mazeGrid;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
