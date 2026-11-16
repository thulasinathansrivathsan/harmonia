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

        if(current.isTerminal()) {
            current.setSearchFrequency(current.getSearchFrequency() + 1);
        }

        return current.isTerminal();
    }

    // TODO: clean this up later
    // record type for search frequency
    record AutoCompleteResults(String word, Long searchFrequency){}


    public List<String> autocomplete(String query) {
        TrieNode current = root;
        record TrieState(TrieNode node, String word) {}
        Deque<TrieState> stack = new ArrayDeque<>();

        List<AutoCompleteResults> result = new ArrayList<>();
        StringBuilder suggestions = new StringBuilder();
        for(char c: query.toCharArray()){
            if(!current.getChildren().containsKey(c)) {
                return new ArrayList<>();
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
                result.add(new AutoCompleteResults(word, traverse.getSearchFrequency()));
            }
        }
        // TODO: change to env
        return topResults(result, 5);
    }

    private List<String> topResults(List<AutoCompleteResults> matches, int k ) {

        PriorityQueue<AutoCompleteResults> topSearchedQueries = new PriorityQueue<>(Comparator.comparingLong(AutoCompleteResults::searchFrequency));

        for(AutoCompleteResults match: matches) {
            topSearchedQueries.offer(match);
            if (topSearchedQueries.size() > k) {
                topSearchedQueries.poll();
            }
        }

        List<String> result = new ArrayList<>();

        while(!topSearchedQueries.isEmpty()) {
            result.add(topSearchedQueries.poll().word);
        }

        return result.reversed();
    }

    // thread optimization
}
