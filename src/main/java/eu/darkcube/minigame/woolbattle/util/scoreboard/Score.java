package eu.darkcube.minigame.woolbattle.util.scoreboard;

public class Score {

	private org.bukkit.scoreboard.Score score;
	private Scoreboard sb;
	private Objective obj;

	public Score(Objective obj, Team team) {
		this(team.getScoreboard(), obj, team.getTeam().getName());
	}

	public Score(Scoreboard sb, Objective obj, String name) {
		this.score = obj.getObjective().getScore(name);
		this.obj = obj;
		this.sb = sb;
	}

	public Score setScore(int score) {
		this.score.setScore(score);
		return this;
	}

	public int getScore() {
		return score.getScore();
	}

	public Scoreboard getScoreboard() {
		return sb;
	}

	public Objective getObjective() {
		return obj;
	}
}