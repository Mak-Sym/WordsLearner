package com.maksym.android.words.learner.data;

import com.maksym.android.words.learner.utils.Maps;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static com.maksym.android.words.learner.utils.Maps.entry;
import static java.util.Arrays.asList;

public class Data {
    private static AtomicInteger currentWordIndex = new AtomicInteger(0);
    private static AtomicInteger currentExampleIndex = new AtomicInteger(0);

    private static final Random rnd = new Random(System.currentTimeMillis());

    private static final List<String> WORDS = asList("a",
            "and",
            "be",
            "I",
            "in",
            "is",
            "it",
            "of",
            "that",
            "the",
            "to",
            "was");

    private static final Map<String, List<String>> EXAMPLES = Maps.map(
            entry("a", asList("A bus is big", "This is a cute dog")),
            entry("and", asList("This is me and my sister", "I like apples and sweets")),
            entry("be", asList("I want to be strong")),
            entry("I", asList("I am 6 years old", "I can swim")),
            entry("in", asList("A candy is in my pocket")),
            entry("is", asList("This is my favourite movie")),
            entry("it", asList("It is my favourite game")),
            entry("of", asList("Take care of yourself")),
            entry("that", asList("That is good")),
            entry("the", asList("My school is the best")),
            entry("to", asList("I can count from one to ten", "I have never been to London")),
            entry("was", asList("I was very surprised yesterday", "I was tired"))
    );

    public static String nextWord() {
        int newIndex;
        do {
            newIndex = rnd.nextInt(WORDS.size());
        } while (currentWordIndex.get() == newIndex);
        currentWordIndex.set(newIndex);
        return WORDS.get(currentWordIndex.get());
    }

    public static String currentWord() {
        return WORDS.get(currentWordIndex.get());
    }

    public static String example() {
        List<String> examples = EXAMPLES.get(currentWord());
        String example = "";
        if(examples != null) {
            example = examples.get(rnd.nextInt(examples.size()));
        }
        return example;
    }
}
