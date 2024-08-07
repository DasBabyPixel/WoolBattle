package eu.darkcube.minigame.woolbattle.util;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;

public class Vote<T> {

	public final long time;
	public final T vote;

	public Vote(long time, T vote) {
		this.time = time;
		this.vote = vote;
	}

	public static final <T> T calculateWinner(Collection<Vote<T>> list, Collection<? extends T> possibles,
			T defaultObject) {
		long votes = 0;
		T object = null;
		BigInteger time = BigInteger.valueOf(0);
		for (T t : possibles) {
			long nextVotes = list.stream().filter(vote -> vote.vote.equals(t)).count();
			BigInteger nextTime = BigInteger.valueOf(0);
			list.stream().mapToLong(vote -> vote.time).forEach(i -> nextTime.add(BigInteger.valueOf(i)));
			if (nextVotes != 0) {
				if (nextVotes > votes) {
					object = t;
					votes = nextVotes;
					time = nextTime;
					continue;
				} else if (nextVotes == votes) {
					if (time.compareTo(nextTime) == 1) {
						object = t;
						votes = nextVotes;
						time = nextTime;
						continue;
					}
				}
			}
		}
		if (object == null)
			object = defaultObject;
		if (object == null) {
			Optional<? extends T> o = possibles.stream().findAny();
			object = o.orElseGet(() -> null);
		}
		return object;
	}
}
