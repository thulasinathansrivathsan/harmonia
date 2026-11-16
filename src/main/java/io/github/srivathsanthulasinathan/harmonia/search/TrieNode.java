package io.github.srivathsanthulasinathan.harmonia.search;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

public class TrieNode {
    @Getter
    @Setter
    private HashMap<Character, TrieNode> children = new HashMap<>();

    @Getter
    @Setter
    private boolean Terminal;

    @Getter
    @Setter
    private long searchFrequency;
}
