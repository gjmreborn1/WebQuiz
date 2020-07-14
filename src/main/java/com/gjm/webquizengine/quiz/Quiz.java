package com.gjm.webquizengine.quiz;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
public class Quiz {
    private static int counter = 0;

    private int id;

    @NotNull(message = "Title can't be null")
    @NotBlank(message = "Title can't be empty")
    private String title;

    @NotNull(message = "Text can't be null")
    @NotBlank(message = "Text can't be empty")
    private String text;

    @Size(min = 2, message = "There must be specified at least 2 options")
    private List<String> options;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Integer> answer;

    public Quiz(String title, String text, List<String> options, List<Integer> answer) {
        this.title = title;
        this.text = text;
        this.options = options;

        // Quiz can have no correct answers
        if(answer == null) {
            this.answer = new ArrayList<>();
        } else {
            this.answer = answer;
        }

        counter++;
        this.id = counter;
    }

    public boolean isCorrect(List<Integer> passedAnswer) {
        passedAnswer.retainAll(answer);

        return passedAnswer.size() > 0;
    }
}
