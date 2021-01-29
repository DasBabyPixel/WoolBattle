package eu.darkcube.minigame.woolbattle.util;

import eu.darkcube.minigame.woolbattle.user.User;
import eu.darkcube.system.stats.api.Duration;
import eu.darkcube.system.stats.api.StatsAPI;
import eu.darkcube.system.stats.api.mysql.MySQL;

public class StatsLink {

	private static boolean isStats;
	public static boolean enabled = true;

	/*
	 * 
	 * 200 + 2000 ~ 33 300 + 3000 ~ 40 400 + 4000 ~ 48 2000 + 200 ~ 1.5 400 + 400 ~
	 * 5
	 * 
	 * 
	 */

	public static void main(String[] args) {

		double A = 2000;
		double B = 1000;
//		double K = 10;
//		double elo = K * (1 - 1 / (1 + Math.pow(10, ((B - A) / 400))));
		double elo = 100 * (1 - 1 / (1 + Math.pow(4, ((B - A - 1000) / (400)))));
		System.out.println(elo);

//
//		double a = 1000;
//		double b = 500;
//		double f1 = 0.5;
//		double f2 = 0.9;
//		double f3 = 3;
//
//		double elo = 0;
//
//		for (int i = 0; i < 400; i++) {
//
//			if (b / a < f1) {
//				elo = 0;
//			} else if (b / a < f2) {
//				elo = (Math.pow(((1 - f1) + (b / a)), /* Wurzel */Math.pow(43544b, 0.4)) - 1) / 440;
//			} else if (b / a < f3) {
//
//			} else {
//
//			}
//
////		elo = ((b / a) * c1) + (a / c2);
//
//			System.out.println(b + ": " + elo);
//			b += 3;
//		}
//
////		System.out.println(getAddOrRemoveElo(elo1, elo2));
//
////		double A = 599;
////		double B = 601;
////
////			double elo = 0;
////
//////			double dif = (A / B) / start;
//////			if (sA > sB) {
////			elo = A == 0 ? 0 : B / A;
//////			}
////
////			double dif = Math.abs(A - B) / 10;
////
////			elo = elo / dif * 400;
////			if (B < A) {
////				elo /= 1.4;
////			}
////
////			System.out.println(" ");
////			System.out.println(A);
////			System.out.println(B);
////			System.out.println(elo);
////
////
////			System.out.println(getAddOrRemoveElo(A, B));
////			A += elo;
////			B -= elo;
//
//		double A = 100;
//		double B = 4000;
//
//		double winrateB = A == 0 ? B : B / A;
//		elo = 0;
//
//		System.out.println(winrateB);
//		elo = winrateB * (A + B) / B * Math.pow(1.5, Double.toString(A + B).length() / 1.3);
//
////		if(winrateB > winrateA) {
////			elo = B / 5
////		}
//
////		
////		elo = A == 0 ? 1 : B / A;
////		
////		double mult = 0;
////		
////		double dif = Math.abs(A - B) / 10;
////		
////		mult += dif;
////
////		System.out.println(elo);
////		
////		elo *= start / 10;
//
//		System.out.println(elo);
////		System.out.println(mult);
//
//		System.out.println(getAddOrRemoveElo(A, B));

	}

	public static double getAddOrRemoveElo(double elo1, double elo2) {
//		double elo = 0;
//		elo = elo1 == 0 ? 0 : elo2 / elo1;
//
//		double dif = Math.abs(elo1 - elo2) / 10;
//
//		elo = dif != 0 ? elo / dif * 400 : elo / 1 * 400;
//
//		if (elo2 < elo1) {
//			elo /= 1.4;
//		}
//		return elo;
//
//		double elo = 0;
//		if (elo1 <= elo2) {
//			elo = 4 + (elo2 - elo1) / 30;
//		} else {
//			elo = 4 + (elo2 - elo1) / (elo2 / 40);
//		}
//		if (elo < 0)
//			elo = 0;
		double elo = 100 * (1 - 1 / (1 + Math.pow(4, ((elo2 - elo1 - 1000) / (400)))));
		return elo;

//		return (elo1 == 0 ? elo2 : elo2 / elo1) * (elo1 + elo2) / elo2 * 10;
	}

	static {
		try {
			Class.forName("eu.darkcube.system.stats.api.StatsAPI");
			isStats = true;
		} catch (Exception ex) {
			isStats = false;
		}
	}

	public static void updateKillElo(User killer, User dead) {
		if (isStats()) {
			for (Duration duration : Duration.values()) {
				double elo1 = StatsAPI.getUser(killer.getUniqueId()).getWoolBattleStats(duration).getElo();
				double elo2 = StatsAPI.getUser(dead.getUniqueId()).getWoolBattleStats(duration).getElo();
				double elo = getAddOrRemoveElo(elo1, elo2);
//				StatsAPI.getUser(killer.getUniqueId()).removeWoolBattleElo(0);
				elo1 += elo;
				elo2 -= elo;
				MySQL.setWoolBattleElo(StatsAPI.getUser(killer.getUniqueId()), duration, elo1);
				MySQL.setWoolBattleElo(StatsAPI.getUser(dead.getUniqueId()), duration, elo2);
			}
		}
	}

	public static void addDeath(User user) {
		if (isStats()) {
			StatsAPI.getUser(user.getUniqueId()).addWoolBattleDeath();
		}
	}

	public static void addWin(User user) {
		if (isStats()) {
			StatsAPI.getUser(user.getUniqueId()).addWoolBattleWin();
		}
	}

	public static void addLoss(User user) {
		if (isStats()) {
			StatsAPI.getUser(user.getUniqueId()).addWoolBattleLoss();
		}
	}

	public static void addKill(User user) {
		if (isStats()) {
			StatsAPI.getUser(user.getUniqueId()).addWoolBattleKill();
		}
	}

	public static boolean isStats() {
		return isStats && enabled;
	}
}
