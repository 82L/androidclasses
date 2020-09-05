package org.kohsuke.randname;

import android.content.Context;

/**
 * Generates pseudo random unique names that combines one adjective and one noun,
 * like "friendly tiger" or "good apple".
 * <p>
 * There's about 1.5 million unique combinations, and if you keep requesting a new word
 * it will start to loop (but this code will generate all unique combinations before it starts
 * to loop.)
 *
 * @author Kohsuke Kawaguchi
 */
public class RandomNameGenerator {

    private Dictionary dictionary;

    private int pos;

    private RandomNameGenerator(Dictionary dictionary, int seed) {
        this.dictionary = dictionary;
        this.pos = seed;
    }

    public RandomNameGenerator(Context context) {
        this(new Dictionary(context), (int) (System.currentTimeMillis() % Integer.MAX_VALUE));
    }

    public synchronized String next() {
        pos = Math.abs(pos + dictionary.getPrime()) % dictionary.size();
        return dictionary.word(pos);
    }
}