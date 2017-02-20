package com.maksym.android.words.learner.utils;

import android.text.Html;
import android.text.Spanned;

public class Views {
    public static Spanned highlight(String word, String sentence) {
        String preparedString = " " + sentence + " ";
        preparedString = preparedString
                .replaceAll("\\s" + word + "\\s", " <b><u>" + word + "</u></b> ")
                .replaceAll("\\s" + capitalize(word) + "\\s", " <b><u>" + capitalize(word) + "</u></b> ")
                .trim() + ".";
        return Html.fromHtml(preparedString);
    }

    private static String capitalize(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }
}
