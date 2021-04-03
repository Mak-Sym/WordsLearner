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
            "the"/*,
            "to",
            "was",
            "all",
            "are",
            "as",
            "at",
            "but",
            "for",
            "had",
            "have",
            "he",
            "her",
            "his",
            "not",
            "on",
            "one",
            "said",
            "so",
            "they",
            "we",
            "with",
            "you"*/);

    private static final Map<String, List<String>> EXAMPLES = Maps.map(
            entry("a", asList("A bus is big", "This is a cute dog", "I have a sister",
                    "As for me this is a good idea")),
            entry("and", asList("This is me and my sister", "I like apples and sweets", "Me and you")),
            entry("be", asList("I want to be strong", "I want to be fast")),
            entry("I", asList("I am 6 years old", "I can swim", "I have many toys", "I have a sister",
                    "I like to play with my sister", "I swim in the big race")),
            entry("in", asList("A candy is in my pocket", "A plane is in the sky",
                    "I had my birthday party in the Luna park", "I run in the big race")),
            entry("is", asList("This is my favourite movie", "My favourite dinosaur is T-rex")),
            entry("it", asList("It is my favourite game", "I forgot about it", "It is true")),
            entry("of", asList("Take care of yourself", "Best wishes to all of you")),
            entry("that", asList("That is good")),
            entry("the", asList("My school is the best", "We are the champions", "I climb in the big race"))/*,
            entry("to", asList("I like to jump", "I like to swim", "I like to play", "We are happy to go to school",
                    "Best wishes to all of you", "People want to fly, but they do not have wings")),
            entry("was", asList("I was very surprised yesterday", "I was tired", "I said it was good")),
            entry("all", asList("Best wishes to all of you")),
            entry("are", asList("We are happy to go to school", "We are the champions", "You are my best friend",
                    "Dinosaurs are very scary, but I like them")),
            entry("as", asList("As for me this is a good idea")),
            entry("at", asList("I had a wonderful day at school")),
            entry("but", asList("I wanted to watch a movie, but it was time to go to bed",
                    "Dinosaurs are very scary, but I like them", "People want to fly, but they do not have wings",
                    "I have two hands and two legs, but only one head")),
            entry("for", asList("I am ready for new adventures", "It would be good for you and me",
                    "As for me this is a good idea", "It's time for lunch")),
            entry("had", asList("I had a wonderful day at school", "I had my birthday party in the Luna park")),
            entry("have", asList("I have many toys", "I have a sister", "I have five fingers on my hand",
                    "People want to fly, but they do not have wings",
                    "I have two hands and two legs, but only one head")),
            entry("he", asList("He is my best friend", "He does everything on his own")),
            entry("her", asList("I always help my sister with her tasks",
                    "I always help my sister to find her bottle")),
            entry("his", asList("My daddy often takes me on his hands", "He does everything on his own")),
            entry("not", asList("I do not do silly things", "People want to fly, but they do not have wings")),
            entry("on", asList("I have five fingers on my hand", "My daddy often takes me on his hands",
                    "He does everything on his own")),
            entry("one", asList("I have two hands and two legs, but only one head")),
            entry("said", asList("I said it was good", "My mommy said that she missed me")),
            entry("so", asList("My teacher is so nice")),
            entry("they", asList("People want to fly, but they do not have wings")),
            entry("we", asList("We are the champions")),
            entry("with", asList("I want to play with you", "I like to play with my sister",
                    "I always help my sister with her tasks")),
            entry("you", asList("I want to play with you",
                    "Best wishes to all of you", "It would be good for you and me"))*/
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
