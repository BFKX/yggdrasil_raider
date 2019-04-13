package mapping;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

class OpenRoom extends Room {
	private final int[][] mapRoom;

	public OpenRoom(int width, int height, Random pseudoRandomList) {
		super(width, height, pseudoRandomList);
		mapRoom = new int[this.getWidth()][this.getHeight()];
	}

	public void addGround() {
		for (int column = 0; column < this.getWidth(); column++) {
			mapRoom[column][this.getWidth()] = 1;
		}
	}

	public void addRandomGround() {
		int[][] grounds = new int[this.getWidth()][this.getHeight() / 10];
		for (int i = 0; i < grounds.length; i++) {
			for (int j = 0; j < grounds[0].length; j++) {
				System.out.println("{" + i + ";" + j + "}");
				grounds[i][j] = pseudoRandomList.nextInt(this.getHeight() / 10);
			}
		}
		System.out.println("end1");
		for (int i = 0; i < this.getWidth(); i++) {
			for (int j = this.getHeight(); j < this.getHeight() / 10; j--) {
				this.mapRoom[i][j] = grounds[i][j];
			}
		}
		System.out.println("end 2");
	}

	public int[][] horizontalFiltering(@NotNull int[][] tab) {
		int[][] filtered = new int[tab.length][tab[0].length];
		for (int i = 0; i < tab.length; i++) {
			for (int j = 1; j < this.getHeight() - 1; j++) {
				filtered[j][i] = tab[j - 1][i] * tab[j + 1][i]
						+ (tab[j][i] * (1 - tab[j - 1][i]) + (1 - tab[j + 1][i])) * (tab[j - 1][i] + tab[j + 1][i]);
			}
		}
		System.out.println("end2");
		return filtered;
	}
}
