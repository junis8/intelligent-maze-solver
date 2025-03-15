import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class MazeSolverGUI extends JFrame {
    private int[][] maze;
    private List<int[]> solutionPath;
    private int cellSize = 30; // Hücre boyutu
    private int[] start, end;  // Başlangıç ve Bitiş noktaları

    public MazeSolverGUI() {
        setTitle("Labirent Çözücü");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Labirenti çizecek panel
        MazePanel mazePanel = new MazePanel();
        add(mazePanel, BorderLayout.CENTER);

        // Butonlar ve kontrol paneli
        JPanel controlPanel = new JPanel();
        JButton loadButton = new JButton("Labirent Yükle");
        JButton bfsButton = new JButton("BFS ile Çöz");
        JButton dfsButton = new JButton("DFS ile Çöz");
        JButton aStarButton = new JButton("Greedy Best First Search ile Çöz");
        
        // Labirent yükleme butonu
        loadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();

                if (filePath.endsWith(".jpg") || filePath.endsWith(".png")) {
                    maze = MazeLoader.loadMazeFromImage(filePath);
                } else if (filePath.endsWith(".txt")) {
                    maze = MazeLoader.loadMazeFromText(filePath);
                } else {
                    JOptionPane.showMessageDialog(this, "Geçersiz dosya formatı! Sadece JPG, PNG veya TXT yükleyin.");
                    return;
                }

                if (maze == null) {
                    JOptionPane.showMessageDialog(this, "Labirent yüklenemedi.");
                    return;
                }

                // Başlangıç ve bitiş noktalarını bul
                start = findStart();
                end = findEnd();

                if (start == null || end == null) {
                    JOptionPane.showMessageDialog(this, "Başlangıç (S) veya Bitiş (E) noktası bulunamadı!");
                    return;
                }

                solutionPath = null; // Yeni labirent yüklendiğinde çözümü sıfırla
                mazePanel.repaint();
            }
        });

        bfsButton.addActionListener(e -> solveMaze("BFS", mazePanel));
        dfsButton.addActionListener(e -> solveMaze("DFS", mazePanel));
        aStarButton.addActionListener(e -> solveMaze("A*", mazePanel));

        controlPanel.add(loadButton);
        controlPanel.add(bfsButton);
        controlPanel.add(dfsButton);
        controlPanel.add(aStarButton);
        add(controlPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void solveMaze(String algorithm, MazePanel mazePanel) {
        if (maze == null) {
            JOptionPane.showMessageDialog(this, "Önce bir labirent yükleyin!");
            return;
        }

        System.out.println("Çözüm Başlatıldı: " + algorithm);
        System.out.println("Başlangıç Noktası: " + start[0] + "," + start[1]);
        System.out.println("Bitiş Noktası: " + end[0] + "," + end[1]);

        switch (algorithm) {
            case "BFS":
                solutionPath = PathFinder.bfs(maze, start, end);
                break;
            case "DFS":
                solutionPath = PathFinder.dfs(maze, start, end);
                break;
            case "Greedy Best First Search":
                solutionPath = PathFinder.greedyBestFirstSearch(maze, start, end);
                break;
        }

        if (solutionPath == null) {
            System.out.println("Çözüm Bulunamadı!");
            JOptionPane.showMessageDialog(this, "Çözüm bulunamadı!");
        } else {
            System.out.println("Çözüm Bulundu! Yol uzunluğu: " + solutionPath.size());
        }

        mazePanel.repaint();
    }



    // Labirenti çizen panel
    class MazePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (maze == null) return;

            for (int i = 0; i < maze.length; i++) {
                for (int j = 0; j < maze[0].length; j++) {
                    if (maze[i][j] == 1) {
                        g.setColor(Color.BLACK); // Duvar
                    }else if(maze[i][j] == 2){
                    	g.setColor(Color.RED); // Baslangic
                    }else if(maze[i][j] == 3){
                    	g.setColor(Color.GREEN); // Bitis
                    }else {
                        g.setColor(Color.WHITE); // Yol
                    }
                    g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                    g.setColor(Color.GRAY);
                    g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
                }
            }

            // Çözüm yolunu kırmızı olarak çiz
            if (solutionPath != null) {
                g.setColor(Color.RED);
                for (int[] step : solutionPath) {
                    g.fillRect(step[1] * cellSize, step[0] * cellSize, cellSize, cellSize);
                }
            }
        }
    }

    // Başlangıç noktasını bulur
    private int[] findStart() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j] == 2) { // 2: Başlangıç noktası (S)
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{0, 0}; // Varsayılan olarak (0,0) kullan
    }

    // Bitiş noktasını bulur
    private int[] findEnd() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j] == 3) { // 3: Bitiş noktası (E)
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{maze.length - 1, maze[0].length - 1}; // Varsayılan çıkış noktası
    }

    public static void main(String[] args) {
        new MazeSolverGUI();
    }
}
