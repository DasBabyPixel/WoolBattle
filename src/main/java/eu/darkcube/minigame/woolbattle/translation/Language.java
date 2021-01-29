package eu.darkcube.minigame.woolbattle.translation;

import java.util.Locale;
import java.util.ResourceBundle;

public enum Language {

	ENGLISH(Locale.ENGLISH), GERMAN(Locale.GERMAN),

	;

	private final Locale locale;
	private ResourceBundle bundle;

	Language(Locale locale) {
		this.locale = locale;

		try {
			this.bundle = ResourceBundle.getBundle("messages", this.locale);
//			Field Flookup = PropertyResourceBundle.class.getDeclaredField("lookup");
//			Flookup.setAccessible(true);
//			@SuppressWarnings("unchecked")
//			Map<String, Object> lookup = (Map<String, Object>) Flookup.get(this.bundle);
////			Map<String, Object> nl = new HashMap<>();
//			for (String key : bundle.keySet()) {
//				CharBuffer b = Charsets.UTF_8.decode(ByteBuffer.wrap(bundle.getString(key).getBytes()));
//				char[] b2 = new char[b.remaining()];
//				int i = 0;
//				while (b.hasRemaining()) {
//					b2[i++] = b.get();
//				}
//				String s = new String(b2);
//				if (key.contains("HEIGHT"))
//					System.out.println(locale.getLanguage() + " " + key + ": " + s);
//				lookup.put(key, s);
//			}
		} catch (Exception ex) {
			ex.printStackTrace();
			this.bundle = null;
		}
	}

	public ResourceBundle getBundle() {
		return this.bundle;
	}

	public Locale getLocale() {
		return locale;
	}
}
