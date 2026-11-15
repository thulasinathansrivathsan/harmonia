package io.github.srivathsanthulasinathan.harmonia.search;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TrieTest {

    @Test
    void insertAndSearch() {
        Trie t = new Trie();
        t.insert("car");
        t.insert("cart");
        t.insert("cat");

        assertTrue(t.search("car"));
        assertTrue(t.search("cat"));
        assertFalse(t.search("ca"));
        assertTrue(t.search("cart"));
        assertFalse(t.search("dog"));
    }
}
