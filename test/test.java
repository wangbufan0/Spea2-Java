package wangbufan.algorithm.test;

import wangbufan.algorithm.GAParameters;
import wangbufan.algorithm.Problem;
import wangbufan.algorithm.Spea2;

/**
 * @ProjectName: My Application
 * @Package: wangbufan.algorithm.test
 * @ClassName: test
 * @Author: wangbufan
 * @CreateDate: 2019/12/4 21:46
 * @Description: java类作用描述
 */
public class test {

    public static void main(String[] args) {
        Problem problem = new TestProblem();
        GAParameters gaParameters=new GAParameters(100,100,100,0.7,0.2);
        Spea2 spea2 = new Spea2(problem,gaParameters);
        spea2.getResult();
    }

}
