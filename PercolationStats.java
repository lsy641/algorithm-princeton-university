import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats { 
    private final double mean_1;
    private final double stddev_1;
    private final double confidence;
    private static final double CONFIDENCE_96 = 1.96;
    public PercolationStats(int n, int trials){
       if (trials<=0)throw new IllegalArgumentException("trials is not allowed");
        int a,b,i=0;
        Percolation p;
        double[] s;
        s=new double[trials];
        double m_s=Math.sqrt(trials);   
        for(i=0;i<trials;i++){
            p=new Percolation(n);
            while(!p.percolates()){
                a=StdRandom.uniform(1,n+1);
                b=StdRandom.uniform(1,n+1);
                p.open(a,b);
            }
            s[i]=p.numberOfOpenSites()/(double)n/(double)n;
        }
        mean_1=StdStats.mean(s);
        stddev_1=StdStats.stddev(s);
        confidence=CONFIDENCE_96*stddev_1/m_s;
    }   
    public double mean(){
        return mean_1;
    } 
    public double stddev()  {

        return stddev_1;
    }                  
    public double confidenceLo(){
        return mean_1-confidence;
    }
    public double confidenceHi(){
        return mean_1+confidence;
    }

    public static void main(String[] args){
        //PercolationStats a = new PercolationStats (2 , 100000);
       // System.out.println("mean: "+a.mean()+" stddev: "+a.stddev());
    }
}