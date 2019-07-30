public class HighScore implements Comparable<Object>{
	private String playerName;
	private int score;
	
	public HighScore(String playerName, int score) {
		this.playerName = playerName;
		this.score = score;
	}

	public String getName() {
		return playerName;
	}

	public int getScore() {
		return score;
	}
	

	@Override
	public int compareTo(Object o) {
		HighScore currentScore = (HighScore) o;
		if (this.getScore() < currentScore.getScore()) {
			return 1;
		} else if (this.getScore() > currentScore.getScore()) {
			return -1;
		} else {
			return 0;
		}
	}
}

