package helper;

import java.awt.Rectangle;
import java.util.Random;

import basic.Game;

public class Dungeongenerator implements Runnable {
	int[][] g = new int[105][105], used = new int[105][105];
	String[][] dun = new String[105][105];
	String road = "0", wall = "10";
	int[][][] draw;
	int[] dx = { 0, 0, 1, -1 }, dy = { 1, -1, 0, 0 };
	public int[] startpoint = new int[2];
	public boolean goodmap = false;
	int width, height, roomnum, trynum;
	Game game;
	// for thread
	boolean running = false;
	private Thread thread;
	//
	Rectangle[] rooms = new Rectangle[100];
	Random rand = new Random();

	public Dungeongenerator(Game g) {
		game = g;
	}

	void dfs(int x, int y) {
		if (x < 1 || y < 1 || x >= width || y >= height)
			return;
		if (dun[x][y] == wall || g[x][y] == 1)
			return;
		g[x][y] = 1;
		for (int i = 0; i < 4; i++) {
			dfs(x + dx[i], y + dy[i]);
		}
	}

	void floodfill(int x, int y) {
		if (x < 1 || y < 1 || x >= width || y >= height)
			return;
		if (used[x][y] > 0)
			return;
		int j = 0;
		for (int i = 0; i < 4; i++) {
			if (used[x + dx[i]][y + dy[i]] == 1 && (x + dx[i] != width && y + dy[i] != height))
				j++;
		}
		if (j > 1)
			return;
		used[x][y] = 1;
		dun[x][y] = road;
		int cnt = 0, dir = 0;
		if (x == width - 1 && y == height - 1)
			return;
		while (cnt < 10) {
			dir = rand.nextInt(4);
			floodfill(x + dx[dir], y + dy[dir]);
			cnt++;
		}
	}

	public boolean IsRunning() {
		return running;
	}

	public String[][] dungeon() {
		return dun;
	}

	public void set(int w, int h, int[][][] draw) {
		this.width = w;
		this.height = h;
		this.draw = draw;
	}

	public void setmap() {
		for (int i = 0; i <= 50; i++) {
			for (int j = 0; j <= 50; j++) {
				if (dun[i][j].length() == 2)
					draw[0][i][j] = dun[i][j].charAt(1) - '0' + (dun[i][j].charAt(0) - '0') * 10;
				else
					draw[0][i][j] = dun[i][j].charAt(0) - '0';
			}
		}
		game.getGameScene().getPlayer().setX(startpoint[1] * 48);// fuck I use
																	// row/col
																	// and x/y
																	// at same
																	// time
		game.getGameScene().getPlayer().setY(startpoint[0] * 48);
	}

	public int[][] getUsed() {
		return used;
	}

	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		if (!running)
			return;
		running = false;
		try {
			this.goodmap = true;
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		boolean goodmap = this.goodmap;

		while (!goodmap) {
			goodmap = true;
			long start = System.nanoTime();
			for (int i = 0; i < 105; i++)
				for (int j = 0; j < 105; j++) {
					g[i][j] = used[i][j] = 0;
					dun[i][j] = wall;
				}

			roomnum = 0;
			trynum = 500;
			boolean ok;

			for (int i = 0; i < trynum && roomnum < 100; i++) {
				ok = true;
				rooms[roomnum] = new Rectangle(rand.nextInt(width - 2) + 1, rand.nextInt(height - 2) + 1,
						rand.nextInt(width / 2) + 6, rand.nextInt(height / 2) + 8);
				if (rooms[roomnum].x + rooms[roomnum].width > width
						|| rooms[roomnum].y + rooms[roomnum].height > height)
					continue;
				for (int j = 0; j < roomnum; j++) {
					if (rooms[j].intersects(rooms[roomnum])) {
						ok = false;
						break;
					}
				}
				if (!ok)
					continue;
				for (int rw = 0, boundw = rooms[roomnum].width; rw < boundw; rw++) {
					for (int rh = 0, boundh = rooms[roomnum].height; rh < boundh; rh++) {
						if (rw == 0 || rw == boundw - 1 || rh == 0 || rh == boundh - 1) {
							dun[rooms[roomnum].x + rw][rooms[roomnum].y + rh] = wall;
							used[rooms[roomnum].x + rw][rooms[roomnum].y + rh] = 2;
							if ((rw == 0 || rw == boundw - 1) && (rh == 0 || rh == boundh - 1))
								g[rooms[roomnum].x + rw][rooms[roomnum].y + rh] = 1;
						} else {
							dun[rooms[roomnum].x + rw][rooms[roomnum].y + rh] = road;
							used[rooms[roomnum].x + rw][rooms[roomnum].y + rh] = 1;
						}
					}
				}
				roomnum++;
			}

			// road
			for (int i = 1; i < width; i++)
				for (int j = 1; j < height; j++)
					floodfill(i, j);
			// make room accessible
			for (int i = 0, step; i < roomnum && goodmap; i++) {
				step = rand.nextInt(300) % ((rooms[i].width + rooms[i].height - 4) * 2);
				for (int u = 0; u < rooms[i].width && goodmap; u++) {
					long now = System.nanoTime();
					if (now - start > 2000000000l)
						goodmap = false;
					for (int v = 0; v < rooms[i].height && goodmap; v++) {
						// corner
						if ((u == 0 || u == rooms[i].width - 1) && (v == 0 || v == rooms[i].height - 1))
							continue;

						if (u == 0 || u == rooms[i].width - 1 || v == 0 || v == rooms[i].height - 1) {
							if (step == 0) {
								// useless door
								int fine = 0;
								for (int d = 0; d < 4; d++) {
									if (used[rooms[i].x + u + dx[d]][rooms[i].y + v + dy[d]] != 0
											&& g[rooms[i].x + u + dx[d]][rooms[i].y + v + dy[d]] != 1)
										fine++;
									if (rooms[i].x + u + dx[d] == 0 || rooms[i].x + u + dx[d] == width
											|| rooms[i].y + v + dy[d] == 0 || rooms[i].y + v + dy[d] == height)
										fine--;
								}
								if (fine < 4)
									continue;
								//
								dun[rooms[i].x + u][rooms[i].y + v] = road;
								used[rooms[i].x + u][rooms[i].y + v] = 1;
								// double wall
								for (int d = 0; d < 4; d++) {
									if (used[rooms[i].x + u + dx[d]][rooms[i].y + v + dy[d]] == 2
											&& used[rooms[i].x + u + dx[d ^ 1]][rooms[i].y + v + dy[d ^ 1]] != 2) {
										used[rooms[i].x + u + dx[d]][rooms[i].y + v + dy[d]] = 1;
										dun[rooms[i].x + u + dx[d]][rooms[i].y + v + dy[d]] = road;
									}
								}

								u = rooms[i].width + 1;
								break;
							} else {
								step--;
							}
						}
					}
					if (u == rooms[i].width - 1)
						u = 0;
				}
			}
			if (!goodmap)
				continue;
			// add wall
			for (int k = 0; k < roomnum; k++)
				for (int j = 1; j < rooms[k].width;) {
					for (int i = 0; i < rooms[k].height; i++) {
						if (dun[rooms[k].x + j][rooms[k].y + i] == road) {
							if ((dun[rooms[k].x + j - 1][rooms[k].y + i] == wall
									&& !(dun[rooms[k].x + j + 1][rooms[k].y + i] == wall))
									|| (!(dun[rooms[k].x + j - 1][rooms[k].y + i] == wall)
											&& dun[rooms[k].x + j + 1][rooms[k].y + i] == wall)) {
								dun[rooms[k].x + j][rooms[k].y + i] = wall;
								used[rooms[k].y + i][rooms[k].x + j] = 0;
							}
						}
					}
					j += rooms[k].width - 3;
				}
			// start point
			do {
				int select = rand.nextInt(roomnum);
				startpoint[0] = rooms[select].x + rand.nextInt(rooms[select].width - 3) + 1;
				startpoint[1] = rooms[select].y + rand.nextInt(rooms[select].height - 3) + 1;
			} while (used[startpoint[0]][startpoint[1]] != 1);

			// some room still unaccessible
			dfs(startpoint[0], startpoint[1]);
			for (int i = 0; i < width && goodmap; i++) {
				for (int j = 0; j < height && goodmap; j++) {
					if (dun[i][j] == wall)
						g[i][j] = 1;
					if (g[i][j] == 0)
						goodmap = false;
				}
			}
			if (!goodmap)
				continue;

			// reduce dead end
			// for (int k = 0; k < 3; k++)
			// for (int i = 1; i < width; i++) {
			// for (int j = 1; j < height; j++) {
			// int c = 0;
			// for (int d = 0; d < 4; d++)
			// if (dun[i + dx[d]][j + dy[d]] == wall)
			// c++;
			// if (c > 2 && used[i][j] != 2) {
			// dun[i][j] = wall;
			// used[i][j] = 0;
			// }
			// }
			// }
			
			// reduce dead end
			for (int i = 1; i < width; i++) {
				for (int j = 1; j < height; j++) {
					int c = 0;
					for (int d = 0; d < 4; d++)
						if (dun[i + dx[d]][j + dy[d]] == wall)
							c++;
					if (c > 2) {
						dun[i][j] = wall;
						used[i][j] = 0;
					}
				}
			}
			
			// reduce wall
			for (int i = 1; i < height; i++) {
				for (int j = 1; j < width; j++) {
					if (dun[i][j] == road)
						continue;
					if (dun[i - 1][j] == dun[i + 1][j] && dun[i - 1][j] == road){
						dun[i][j] = road;
						used[i][j] = 1;
					}
				}
			}
			//set used
			for (int i = 1; i < width; i++) {
				for (int j = 1; j < height; j++) {
					if(dun[i][j] == wall){
						used[i][j] = 0;
					}else
						used[i][j] = 1;
						
				}
			}
			// paint
			for(int j = 0 ; j < width; j++){
					if(used[1][j] == 1){
							dun[0][j] = "21";
					}
			}
			for(int j = 0 ; j < width ; j++){
				if(dun[0][j] == "21" && dun[0][j-1] != "21" && dun[0][j-1] != "22" 
						&&dun[0][j+1] != "21" && dun[0][j+1] != "22"&& dun[0][j-1] != "24"&& dun[0][j+1] != "24")
					dun[0][j] = "23";
				else if(dun[0][j] == "21" && dun[0][j-1] != "21" && dun[0][j-1] != "22"&& dun[0][j-1] != "24")
					dun[0][j] = "22";
				else if (dun[0][j] == "21" && dun[0][j+1] != "21" && dun[0][j+1] != "22" && dun[0][j+1] != "24")
					dun[0][j] = "24";
			}
			for (int i = 1; i < height; i++) {
				for (int j = 1; j < width; j++) {
					//wall
					if (dun[i][j] == wall) {
						if (used[i+1][j] == 1){
							dun[i][j] = "21";
						}
						else if (used[i][j - 1] ==1) {
							if (used[i - 1][j] == 1) {
								if (used[i][j + 1] == 1)
									dun[i][j] = "19";
								else
									dun[i][j] = "13";
							} else if (used[i][j + 1] ==1)
								dun[i][j] = "18";
							else if (used[i + 1][j] == 1)
								dun[i][j] = "12";
							else
								dun[i][j] = "11";
						} else if (used[i][j + 1] ==1) {
							if (used[i - 1][j] == 1)
								dun[i][j] = "17";
							else if (used[i + 1][j] == 1)
								dun[i][j] = "16";
							else
								dun[i][j] = "15";
						} else if (used[i - 1][j] == 1){
							dun[i][j] = "14";
						}
						
					}
					//road
					if (dun[i][j] == road) {
						if (used[i][j - 1] == 0) {
							if (used[i - 1][j] ==0) {
								if (used[i][j + 1] == 0)
									dun[i][j] = "9";
								else
									dun[i][j] = "3";
							} else if (used[i][j + 1] == 0)
								dun[i][j] = "8";
							else if (used[i + 1][j] == 0)
								dun[i][j] = "2";
							else
								dun[i][j] = "1";
						} else if (used[i][j + 1] == 0) {
							if (used[i - 1][j] == 0)
								dun[i][j] = "7";
							else if (used[i + 1][j] == 0)
								dun[i][j] = "6";
							else
								dun[i][j] = "5";
						} else if (used[i - 1][j] == 0)
							dun[i][j] = "4";
					}
					
				}
			}
			//wall skin
			for (int i = 1; i < height; i++) {
				for (int j = 1; j < width; j++) {
					if(dun[i][j] == "21" && dun[i][j-1] != "21" && dun[i][j-1] != "22" 
							&&dun[i][j+1] != "21" && dun[i][j+1] != "22"&& dun[i][j-1] != "24"&& dun[i][j+1] != "24")
						dun[i][j] = "23";
					else if(dun[i][j] == "21" && dun[i][j-1] != "21" && dun[i][j-1] != "22"&& dun[i][j-1] != "24")
						dun[i][j] = "22";
					else if (dun[i][j] == "21" && dun[i][j+1] != "21" && dun[i][j+1] != "22" && dun[i][j+1] != "24")
						dun[i][j] = "24";
				}
			}
			int i, j;
			for (i = 0; i <= width; i++) {
				for (j = 0; j <= height; j++) {
					if (dun[i][j] == wall)
						System.out.print("#");
					else
						System.out.print(".");
				}
				System.out.println();
			}

			for (i = 0; i <= width; i++) {
				for (j = 0; j <= height; j++) {
					System.out.print(used[i][j]);
				}
				System.out.println();
			}
		}
		try {
			Thread.sleep(3000);
			setmap();
			stop();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
