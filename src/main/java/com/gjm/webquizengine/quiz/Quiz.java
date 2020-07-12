package com.gjm.webquizengine.quiz;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Quiz {
    private static int counter = 0;

    private int id;
    private String title;
    private String text;
    private List<String> options;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int answer;

    public Quiz(String title, String text, List<String> options, int answer) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;

        counter++;
        this.id = counter;
    }
}
