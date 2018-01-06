import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private WeightedQuickUnionUF[] u;
    private int[] b;
    private final int t;
   // private boolean[] s;
    private final int m;
    private int open;
    private final int  m_square;
    public Percolation(int n){
        if(n<=0)throw new IllegalArgumentException("n needs >0");   
        m_square =(int)Math.pow(n,2);
        //s = new boolean[m_square];
       // for ( int i = 0 ; i < m_square ; i++ ) s[i] = false; 
        
        if ( m_square % 32 != 0 ) t = m_square / 32 + 1 ;
        else t = m_square / 32 ;
        b =  new int [ t ] ;
        for ( int i = 0 ; i < t ; i++ ) b[i] = 0; 
        
        
        u = new WeightedQuickUnionUF[2];
        u[0] = new WeightedQuickUnionUF  ( m_square + 1);
        u[1] = new WeightedQuickUnionUF ( m_square + 2);
        m = n;   
        open = 0 ;
    }
    public void open(int row, int col){ 
        if(isOpen(row,col)) return;
       // s[(row-1)*m+col-1]=true;
        
        open++;
        int temp1=(row-1)*m+col-1;
        b[ temp1 / 32 ] =  b[ temp1 / 32 ]  | ( 0x01 << (temp1 % 32)) ;
        
        if(row==1){u[0].union(temp1,m_square);u[1].union(temp1,m_square);}
        if(row==m) u[1].union(temp1,m_square+1);
       // if(row-2>=0 && s[ temp1 - m ] == true){u[0].union(temp1,temp1-m);u[1].union(temp1,temp1-m);}
      //  if(row<m && s[ temp1 + m ] == true){u[0].union(temp1,temp1+m);u[1].union(temp1,temp1+m);}
       // if(col-2>=0 && s[ temp1-1 ] == true){u[0].union(temp1,temp1-1);u[1].union(temp1,temp1-1);}
       // if(col <m && s[ temp1+1 ] == true){u[0].union(temp1,temp1+1);u[1].union(temp1,temp1+1);}
        
        if(row-2>=0 && ( ( b[ ( temp1 - m ) / 32 ] >> ( ( temp1 - m ) % 32 ) )  & 0X01 ) != 0 ){u[0].union(temp1,temp1-m);u[1].union(temp1,temp1-m);}
        if(row<m && ( ( b[ ( temp1 + m ) / 32 ] >> ( ( temp1 + m ) % 32 ) )  & 0X01 ) != 0 ){u[0].union(temp1,temp1+m);u[1].union(temp1,temp1+m);}
        if(col-2>=0 && ( ( b[ ( temp1 - 1 ) / 32 ] >> ( ( temp1 - 1 ) % 32 ) )  & 0X01 ) != 0 ){u[0].union(temp1,temp1-1);u[1].union(temp1,temp1-1);}
        if(col <m && ( ( b[ ( temp1 + 1 ) / 32 ] >> ( ( temp1 + 1 ) % 32 ) )  & 0X01 ) != 0 ){u[0].union(temp1,temp1+1);u[1].union(temp1,temp1+1);}
        
        
        
        
    } 
    public boolean isOpen(int row, int col){
        if(row > m || row < 1 ) throw new IllegalArgumentException("row is not allowed"); 
        if(col > m || col < 1 ) throw new IllegalArgumentException("col is not allowed"); 
       // if(s[(row-1)*m+col-1]==true)return true;
        int temp = (b[((row-1)*m+col-1) / 32] >> ( ( ( row - 1 ) * m + col - 1 ) % 32 ));
        if((temp & 0X01)!=0X00) return true;
        else return false;
    } 
    public boolean isFull(int row, int col){
        if ( row > m || row < 1 ) throw new IllegalArgumentException ( "row is not allowed" ); 
        if ( col > m || col < 1 ) throw new IllegalArgumentException ( "col is not allowed" ); 
        return  u[0].connected ( ( row - 1 ) * m + col - 1 , m_square ) ;
    } 
    public int numberOfOpenSites()   { return open; }    // number of open sites
    public boolean percolates()    {
        return u[1].connected(m_square,m_square+1);
    }          // does the system percolate?

    public static void main(String[] args)  {

    } // test client (optional)
}