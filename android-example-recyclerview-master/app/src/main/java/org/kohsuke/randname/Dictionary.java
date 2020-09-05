package org.kohsuke.randname;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import fr.iut_amiens.namegenerator.R;

/**
 * Dictionary of adjectives and nouns.
 *
 * @author Kohsuke Kawaguchi
 */
public class Dictionary {

    private List<String> nouns = new ArrayList<>();

    private List<String> adjectives = new ArrayList<>();

    private final int prime;

    public Dictionary(Context context) {
        try {
            adjectives = load(context, R.raw.a);
            nouns = load(context, R.raw.n);
        } catch (IOException e) {
            throw new Error(e);
        }

        int combo = size();

        int primeCombo = 2;
        while (primeCombo <= combo) {
            int nextPrime = primeCombo + 1;
            primeCombo *= nextPrime;
        }
        prime = primeCombo + 1;
    }

    /**
     * Total size of the combined words.
     */
    int size() {
        return nouns.size() * adjectives.size();
    }

    /**
     * Sufficiently big prime that's bigger than {@link #size()}
     */
    int getPrime() {
        return prime;
    }

    String word(int i) {
        int a = i % adjectives.size();
        int n = i / adjectives.size();

        return adjectives.get(a) + " " + nouns.get(n);
    }

    private static List<String> load(Context context, int file) throws IOException {
        BufferedReader r = null;
        try {
            r = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(file), "UTF-8"));

            List<String> res = new ArrayList<>();

            String line;
            while ((line = r.readLine()) != null) {
                res.add(line);
            }

            return res;
        } finally {
            if (r != null) {
                try {
                    r.close();
                } catch (Exception ignored) {}
            }
        }
    }
}
