package wangbufan.algorithm;

/**
 * @ProjectName: My Application
 * @Package: wangbufan.algorithm
 * @ClassName: GAParameters
 * @Author: wangbufan
 * @CreateDate: 2019/12/4 21:36
 * @Description: java类作用描述
 */
public class GAParameters {
    int PopSize ;
    int ArchiveSize ;
    int Gmax ;
    double Pc ;
    double Pm ;
    public GAParameters(int PopSize,int ArchiveSize,int Gmax, double Pc,double Pm){
        this.PopSize=PopSize;
        this.ArchiveSize=ArchiveSize;
        this.Gmax=Gmax;
        this.Pc=Pc;
        this.Pm=Pm;
    }
}
