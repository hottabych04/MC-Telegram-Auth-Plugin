package auth.plugin.mc.util.trie;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashSet;

public class Trie {

    private final Node root;

    public Trie() {
        this.root = Node.rootNode();
    }

    public boolean add(@NotNull String string) {
        if (string.isEmpty())
            return false;

        return root.add(string);
    }


    public boolean remove(@NotNull String string) {
        if (string.isEmpty())
            return true;

        return root.remove(string);
    }

    public boolean find(@NotNull String string) {
        return getNodeByPrefix(string) != null;
    }

    @NotNull
    public Collection<String> findAll(@NotNull String prefix) {
        Node prefixedNode = getNodeByPrefix(prefix);
        if (prefixedNode == null || prefixedNode == root)
            return new HashSet<>();

        return prefixedNode.getAllStringsForThisBranch(new HashSet<>(), new StringBuilder(prefix.substring(0, prefix.length() - 1)));
    }


    @Nullable
    private Node getNodeByPrefix(@NotNull String prefix) {
        return root.findChildByPrefix(prefix);
    }
}