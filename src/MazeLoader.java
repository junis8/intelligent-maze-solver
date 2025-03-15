import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class MazeLoader {
    
    /**
     * Resimden labirenti okuyup 2D grid'e çeviren fonksiyon.
     * @param filePath Görüntü dosyasının yolu (JPG, PNG)
     * @return Labirenti temsil eden 2D int dizisi (1 = duvar, 0 = yol)
     */
    public static int[][] loadMazeFromImage(String filePath) {
        try {
            // Resmi yükle
            BufferedImage img = ImageIO.read(new File(filePath));
            int width = img.getWidth();
            int height = img.getHeight();

            int[][] mazeGrid = new int[height][width];

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = img.getRGB(x, y);
                    
                    // RGB'den gri tona çevirme
                    int red = (pixel >> 16) & 0xff;
                    int green = (pixel >> 8) & 0xff;
                    int blue = pixel & 0xff;
                    int gray = (red + green + blue) / 3;

                    // Siyah (duvar) ise 1, beyaz (yol) ise 0 ata
                    mazeGrid[y][x] = (gray < 128) ? 1 : 0;
                }
            }
            return mazeGrid;
        } catch (Exception e) {
            System.err.println("Labirent resmi okunamadı: " + e.getMessage());
            return null;
        }
    }

    /**
     * Metin dosyasından labirenti okuyup 2D grid'e çeviren fonksiyon.
     * @param filePath Metin dosyasının yolu (.txt)
     * @return Labirenti temsil eden 2D int dizisi (1 = duvar, 0 = yol)
     */
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
