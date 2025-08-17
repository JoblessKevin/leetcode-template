package templates.unionFind;

public class DSU {
    private final int[] p, sz;
    public DSU(int n){ p=new int[n]; sz=new int[n]; for (int i=0;i<n;i++){p[i]=i;sz[i]=1;} }
    public int find(int x){ return p[x]==x? x : (p[x]=find(p[x])); }
    public boolean unite(int a,int b){
        a=find(a); b=find(b); if (a==b) return false;
        if (sz[a] < sz[b]) {int t=a;a=b;b=t;}
        p[b]=a; sz[a]+=sz[b]; return true;
    }
}
