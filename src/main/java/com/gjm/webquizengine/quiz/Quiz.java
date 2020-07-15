package com.gjm.webquizengine.quiz;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull(message = "Title can't be null")
    @NotBlank(message = "Title can't be empty")
    private String title;

    @NotNull(message = "Text can't be null")
    @NotBlank(message = "Text can't be empty")
    private String text;

    @Size(min = 2, message = "There must be specified at least 2 options")
    private ArrayList<String> options;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ArrayList<Integer> answer;

    public Quiz(String title, String text, ArrayList<String> options, ArrayList<Integer> answer) {
        this.title = title;
        this.text = text;
        this.options = options;

        // Quiz can have no correct answers
        if(answer == null) {
            this.answer = new ArrayList<>();
        } else {
            this.answer = answer;
        }
    }

    public Quiz() {
        this("", "", new ArrayList<>(), new ArrayList<>());
    }

    public boolean isCorrect(List<Integer> passedAnswer) {
        passedAnswer.retainAll(answer);

        return passedAnswer.size() > 0;
    }
}
