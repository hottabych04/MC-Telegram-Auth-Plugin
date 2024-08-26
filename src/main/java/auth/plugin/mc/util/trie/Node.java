package auth.plugin.mc.util.trie;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

class Node {

    static final char EMPTY_KEY = '\0';

    private final char key;

    private final Map<Character, Node> children;

    private boolean isEndOfString = false;

    private Node(char key, @NotNull String string) {
        this.key = key;
        children = new HashMap<>();

        if (string.isEmpty()) {
            isEndOfString = true;
        } else {
            add(string);
        }
    }

    static Node rootNode() {
        return new Node(EMPTY_KEY, "");
    }

    boolean add(@NotNull String string) {
        if (string.isEmpty()) {
            boolean contained = isEndOfString;
            isEndOfString = true;

            return !contained;
        }

        char nextKey = string.charAt(0);

        if (children.containsKey(nextKey)) {
            return children.get(nextKey).add(string.substring(1));
        }

        children.put(nextKey, new Node(nextKey, string.substring(1)));
        return true;
    }

    boolean remove(@NotNull String string) {
        if (string.isEmpty()) {
            boolean contained = isEndOfString;

            isEndOfString = false;
            return contained;
        }

        char nextKey = string.charAt(0);
        if (!children.containsKey(nextKey))
            return false;

        return children.get(nextKey).remove(string.substring(1));
    }


    @Nullable
    Node findChildByPrefix(@NotNull String prefix) {
        if (prefix.isEmpty())
            return this;

        char nextKey = prefix.charAt(0);

        if (!children.containsKey(nextKey))
            return null;

        return children.get(nextKey).findChildByPrefix(prefix.substring(1));
    }

    @NotNull
    Collection<String> getAllStringsForThisBranch(@NotNull Collection<String> strings, @NotNull StringBuilder prefix) {
        prefix.append(key);
        if (isEndOfString) {
            strings.add(prefix.toString());
        }

        for (Node node : children.values()) {
            node.getAllStringsForThisBranch(strings, prefix);
        }
        prefix.deleteCharAt(prefix.length() - 1);

        return strings;
    }
}
