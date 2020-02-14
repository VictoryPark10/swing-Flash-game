package ddong;

public class DropF {
	int x, y;

	DropF(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void drop() {
		y += 5;
	}

	public void drop1() {
		if (y < 150) {
			y += 5;
		}
	}
}