package templates.trie;

public class Trie {
    static class Node { Node[] nxt = new Node[26]; boolean end; }
    private final Node root = new Node();

    public void insert(String s){
        Node cur = root;
        for (char ch : s.toCharArray()){
            int i = ch - 'a';
            if (cur.nxt[i] == null) cur.nxt[i] = new Node();
            cur = cur.nxt[i];
        }
        cur.end = true;
    }
    public boolean search(String s){
        Node cur = root;
        for (char ch : s.toCharArray()){
            int i = ch - 'a';
            if (cur.nxt[i] == null) return false;
            cur = cur.nxt[i];
        }
        return cur.end;
    }
    public boolean startsWith(String p){
        Node cur = root;
        for (char ch : p.toCharArray()){
            int i = ch - 'a';
            if (cur.nxt[i] == null) return false;
            cur = cur.nxt[i];
        }
        return true;
    }
}
