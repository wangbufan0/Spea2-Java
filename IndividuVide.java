package wangbufan.algorithm;

import java.util.List;
import java.util.Random;

/**
 * @ProjectName: My Application
 * @Package: wangbufan.algorithm
 * @ClassName: IndividuVide
 * @Author: wangbufan
 * @CreateDate: 2019/12/3 16:12
 * @Description: 基因的个例
 */
public class IndividuVide implements Comparable<IndividuVide>{

    double[] value;//原值
    double[] valObjective;//目标值
    int S;//支配数
    int R;//
    double[] sigma;
    double sigmak;
    double D;
    double F;

    IndividuVide(double[] value){
        this.value=value;
    }




    boolean compare(IndividuVide b){
         /**
           * @author: wangbufan
           * @date: 2019/12/4 15:23
           * @description 比较两个点是否有匹配关系
           */
        boolean havaAny=false;
        double[] vb=b.valObjective;
        for(int i=0;i<valObjective.length;i++){
            if(valObjective[i]>vb[i]) return false;
            if(!havaAny &&valObjective[i]<vb[i])havaAny= true;
        }
        return havaAny;
    }

    double distance(IndividuVide b){
         /**
           * @author: wangbufan
           * @date: 2019/12/4 15:22
           * @description 计算两个的欧式距离
           */
        double result=0;
        double[] vb=b.valObjective;
        for(int i=0;i<valObjective.length;i++){
            result+=(valObjective[i]-vb[i])*(valObjective[i]-vb[i]);
        }
        result=Math.sqrt(result);
        return result;
    }


    @Override
    public int compareTo(IndividuVide o) {
         /**
           * @author: wangbufan
           * @date: 2019/12/4 15:21
           * @description 根据两个点的优劣比较，做排序用
           */
        Double ff=F;
        return ff.compareTo(o.F);
    }



}
