package com.gjm.webquizengine.quiz;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Quiz {
    private String title;
    private String text;
    private List<String> options;
}
