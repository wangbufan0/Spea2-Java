package wangbufan.algorithm;

/**
 * @ProjectName: My Application
 * @Package: wangbufan.algorithm
 * @ClassName: Problem
 * @Author: wangbufan
 * @CreateDate: 2019/12/3 16:27
 * @Description: 输入值数据
 */
public abstract class Problem {
    double UpperLimit;
    double LowerLimit;
    int NbVariablesDecision;

    protected Problem(double UpperLimit,double LowerLimit,  int NbVariablesDecision){
        this.UpperLimit =UpperLimit;
        this.LowerLimit =LowerLimit;
        this.NbVariablesDecision =NbVariablesDecision;
    }

    protected abstract double[] Calculation(double[] x);


}
