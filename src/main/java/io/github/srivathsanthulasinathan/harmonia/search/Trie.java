package io.github.srivathsanthulasinathan.harmonia.search;

import java.util.*;

public class Trie {
    private TrieNode root = new TrieNode();

    public void insert(String word) {
        TrieNode current = root;

        for(char c: word.toCharArray()) {
            if(!current.getChildren().containsKey(c)) {
                current.getChildren().put(c, new TrieNode());
            }
            current = current.getChildren().get(c);
        }
        current.setTerminal(true);
    }

    public boolean search(String query) {
        TrieNode current = root;

        for(char c: query.toCharArray()) {
            if(!current.getChildren().containsKey(c)){
                return false;
            }
            current = current.getChildren().get(c);
        }

        return current.isTerminal();
    }

    public List<String> autocomplete(String query) {
        TrieNode current = root;
        record TrieState(TrieNode node, String word) {}
        Deque<TrieState> stack = new ArrayDeque<>();

        List<String> result = new ArrayList<>();
        StringBuilder suggestions = new StringBuilder();
        for(char c: query.toCharArray()){
            if(!current.getChildren().containsKey(c)) {
                return result;
            }
            suggestions.append(c);
            current = current.getChildren().get(c);
        }
        stack.push(new TrieState(current, suggestions.toString()));

        while (!stack.isEmpty()) {
            TrieState state = stack.pop();
            TrieNode traverse = state.node();
            String word = state.word();

            for (Map.Entry<Character, TrieNode> entry : traverse.getChildren().entrySet()) {
                char c = entry.getKey();
                TrieNode child = entry.getValue();
                String nextWord = word + c;

                stack.push(new TrieState(child, nextWord));
            }
            if(traverse.isTerminal()) {
                result.add(word);
            }
        }
        return result;
    }

    // searchFrequency

    // ranked results with heaps

    // caching

    // thread optimization

}
