package wangbufan.algorithm.test;


import wangbufan.algorithm.Problem;

/**
 * @ProjectName: My Application
 * @Package: wangbufan.algorithm
 * @ClassName: TestProblem
 * @Author: wangbufan
 * @CreateDate: 2019/12/4 21:34
 * @Description: java类作用描述
 */
public class TestProblem extends Problem {


    public TestProblem() {
        super(1000, -1000, 1);
    }

    @Override
    public double[] Calculation(double[] x) {
        double[] y=new double[2];
        double xx= x[0];
        y[0]=xx*xx;
        y[1]=(xx-2)*(xx-2);
        return y;
    }
}
