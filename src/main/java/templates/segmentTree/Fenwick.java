package templates.segmentTree;

public class Fenwick {
    private final long[] bit; private final int n;
    public Fenwick(int n){ this.n=n; this.bit=new long[n+1]; }
    public void add(int idx, long delta){ for (int i=idx+1;i<=n;i+=i&-i) bit[i]+=delta; }
    public long sumPrefix(int idx){ long s=0; for (int i=idx+1;i>0;i-=i&-i) s+=bit[i]; return s; }
    public long rangeSum(int l, int r){ return sumPrefix(r) - (l==0?0:sumPrefix(l-1)); }
}
