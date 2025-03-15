import java.util.*;

/**
 * Labirent çözme algoritmalarını içeren sınıf.
 */
public class PathFinder {

	private static final int[] dx = { -1, 1, 0, 0 }; // Yukarı, Aşağı
	private static final int[] dy = { 0, 0, -1, 1 }; // Sol, Sağ

	/**
	 * BFS ile en kısa yolu bulur (Garantili En Kısa Yol).
	 */
	public static List<int[]> bfs(int[][] maze, int[] start, int[] end) {
	    System.out.println("BFS Algoritması Başladı...");
	    System.out.println("Başlangıç: " + start[0] + "," + start[1]);
	    System.out.println("Bitiş: " + end[0] + "," + end[1]);

	    int rows = maze.length;
	    int cols = maze[0].length;
	    boolean[][] visited = new boolean[rows][cols];
	    Queue<int[]> queue = new LinkedList<>();
	    Map<String, int[]> parent = new HashMap<>();

	    queue.add(start);
	    visited[start[0]][start[1]] = true;

	    while (!queue.isEmpty()) {
	        int[] current = queue.poll();
	        int x = current[0], y = current[1];

	        System.out.println("Şu anki düğüm: (" + x + "," + y + ")");

	        if (x == end[0] && y == end[1]) {
	            System.out.println("Bitiş noktasına ulaşıldı!");
	            return reconstructPath(parent, start, end);
	        }

	        for (int i = 0; i < 4; i++) {
	            int nx = x + dx[i];
	            int ny = y + dy[i];

	            if (isValidMove(nx, ny, maze, visited)) {
	                queue.add(new int[]{nx, ny});
	                visited[nx][ny] = true;
	                parent.put(nx + "," + ny, new int[]{x, y});
	            }
	        }
	    }
	    System.out.println("BFS Algoritması Bitişe Ulaşamadı!");
	    return null;
	}


	/**
	 * DFS ile bir yol bulur (Her zaman en kısa yolu garantilemez).
	 */
	public static List<int[]> dfs(int[][] maze, int[] start, int[] end) {
		int rows = maze.length;
		int cols = maze[0].length;
		boolean[][] visited = new boolean[rows][cols];
		Stack<int[]> stack = new Stack<>();
		Map<String, int[]> parent = new HashMap<>();

		stack.push(start);
		visited[start[0]][start[1]] = true;

		while (!stack.isEmpty()) {
			int[] current = stack.pop();
			int x = current[0], y = current[1];

			if (x == end[0] && y == end[1]) {
				return reconstructPath(parent, start, end);
			}

			for (int i = 0; i < 4; i++) {
				int nx = x + dx[i];
				int ny = y + dy[i];

				if (isValidMove(nx, ny, maze, visited)) {
					stack.push(new int[] { nx, ny });
					visited[nx][ny] = true;
					parent.put(nx + "," + ny, new int[] { x, y });
				}
			}
		}
		return null;
	}

	/**
	 * Greedy Best First Search Algoritması ile en kısa yolu bulur.
	 */
	public static List<int[]> greedyBestFirstSearch(int[][] maze, int[] start, int[] end) {
	    PriorityQueue<Integer, int[]> pq = new MinHeapPriorityQueue<>(); // Sadece h(n) değerine göre sıralama yapar
	    boolean[][] visited = new boolean[maze.length][maze[0].length];
	    Map<String, int[]> parent = new HashMap<>();

	    pq.insert(heuristic(start, end), start); // Sadece h(n) kullanıyoruz

	    while (!pq.isEmpty()) {
	        Entry<Integer, int[]> entry = pq.removeMin();
	        int[] current = entry.getValue();
	        int x = current[0], y = current[1];

	        if (x == end[0] && y == end[1]) {
	            System.out.println("Bitiş noktasına ulaşıldı!");
	            return reconstructPath(parent, start, end);
	        }

	        for (int i = 0; i < 4; i++) { // Yukarı, aşağı, sol, sağ hareketleri kontrol et
	            int nx = x + dx[i], ny = y + dy[i];
	            if (!isValidMove(nx, ny, maze, visited)) continue;

	            parent.put(nx + "," + ny, new int[]{x, y});
	            visited[nx][ny] = true;
	            pq.insert(heuristic(new int[]{nx, ny}, end), new int[]{nx, ny}); // Sadece h(n) kullan
	        }
	    }
	    System.out.println("Greedy Best-First Search Bitişe Ulaşamadı!");
	    return null;
	}


	/**
	 * Manhattan mesafesi.
	 */
	private static int heuristic(int[] a, int[] b) {
		return Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]);
	}

	/**
	 * Belirtilen hareketin geçerli olup olmadığını kontrol eder.
	 */
	private static boolean isValidMove(int x, int y, int[][] maze, boolean[][] visited) {
	    boolean valid = x >= 0 && y >= 0 && x < maze.length && y < maze[0].length
	                    && (maze[x][y] == 0 || maze[x][y] == 3) // Bitiş noktasına girişi serbest bırakıyoruz
	                    && !visited[x][y];

	    System.out.println("Kontrol Edilen Hücre: (" + x + "," + y + ") -> " + (valid ? "Geçerli" : "Geçersiz"));
	    
	    return valid;
	}


	/**
	 * Bulunan yolu geri izleyerek yeniden oluşturur.
	 */
	private static List<int[]> reconstructPath(Map<String, int[]> parent, int[] start, int[] end) {
		List<int[]> path = new ArrayList<>();
		int[] step = end;

		while (!Arrays.equals(step, start)) {
			path.add(step);
			step = parent.get(step[0] + "," + step[1]);
		}
		path.add(start);
		Collections.reverse(path);
		return path;
	}

	/**
	 * Çözülen yolu labirent üzerinde gösterir.
	 */
	public static void printSolvedMaze(int[][] maze, List<int[]> path) {
		if (path == null) {
			System.out.println("Çözüm bulunamadı!");
			return;
		}

		char[][] visualMaze = new char[maze.length][maze[0].length];
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[0].length; j++) {
				visualMaze[i][j] = maze[i][j] == 1 ? '█' : ' ';
			}
		}

		for (int[] step : path) {
			visualMaze[step[0]][step[1]] = '*';
		}

		for (char[] row : visualMaze) {
			System.out.println(new String(row));
		}
	}

}
