package wangbufan.algorithm;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @ProjectName: My Application
 * @Package: wangbufan.algorithm
 * @ClassName: spea2
 * @Author: wangbufan
 * @CreateDate: 2019/12/3 16:11
 * @Description: java类作用描述
 */




public class Spea2 {


    public  interface Listener{
         /**
           * @author: wangbufan
           * @date: 2019/12/4 23:27
           * @description 设置回调，用于显示
           */
        public void onListener(LinkedList<Double> x,LinkedList<Double> y);
    }


    Listener listener=null;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    Problem problem ;
    GAParameters gaParameters;

    public Spea2(Problem problem,GAParameters gaParameters){
        this.problem=problem;
        this.gaParameters=gaParameters;
    }


    public void getResult() {
        List<IndividuVide> Pop = init();
        List<IndividuVide> archive = new LinkedList<IndividuVide>();
        int G = 1;
        int Gmax=gaParameters.Gmax;
        List<IndividuVide> P;
        while (G < Gmax) {
            P = new LinkedList<>(Pop);
            P.addAll(archive);
            evaluation(P);//计算出所有数据
            archive = selection(P);//选择精英
            onPrint(archive,G);
            List<IndividuVide> MP = SelectionTournoi(archive);
            List<IndividuVide> Enfants=SimulatedBinaryCrossover(MP);
            Pop=PolynomialMutation(Enfants);
            G++;
        }
    }

    void onPrint(List<IndividuVide> archive ,int G){
         /**
           * @author: wangbufan
           * @date: 2019/12/4 23:25
           * @description 计算精英数目，和数值
           */
        LinkedList<Double> x=new LinkedList<>();
        LinkedList<Double> y=new LinkedList<>();
        int paretoFront = 0;
        for (int i = 0; i < archive.size(); i++) {
            if (archive.get(i).F < 1) {
                paretoFront++;
                x.add(archive.get(i).valObjective[0]);
                y.add(archive.get(i).valObjective[1]);
            }
        }
        System.out.println("G = " + G + " //Taille Fromt = " + paretoFront);
        if(listener!=null)listener.onListener(x,y);

    }



    List<IndividuVide> PolynomialMutation(List<IndividuVide> Enfants){
        int n=5;
        int N=Enfants.size();
        double pm=gaParameters.Pm;
        double L=problem.LowerLimit;
        double U=problem.UpperLimit;
        int nbDecVar=problem.NbVariablesDecision;//一组输入数据的个数
        Random random=new Random();

        for(int i=0;i<N;i++){
            if(random.nextDouble()<pm){
                double u=random.nextDouble();
                double deltaBar;
                if(u<0.5)
                    deltaBar=Math.pow(2*u,1/(1+n))-1;
                else
                    deltaBar=1-Math.pow(2*(1-u),1/(1+n));
                for(int j=0;j<nbDecVar;j++){
                    Enfants.get(i).value[j]=Math.abs(Enfants.get(i).value[j]+(U-L)*deltaBar);
                    if(Enfants.get(i).value[j]<L)
                        Enfants.get(i).value[j]=L;
                    else if(Enfants.get(i).value[j]>U)
                        Enfants.get(i).value[j]=U;
                }
            }
        }
        for(int i=0;i<N;i++){
            Enfants.get(i).valObjective=problem.Calculation(Enfants.get(i).value);
        }
        return Enfants;
    }




    List<IndividuVide> SimulatedBinaryCrossover(List<IndividuVide> MP) {
        /**
         * @author: wangbufan
         * @date: 2019/12/4 15:25
         * @description 模拟二进制交叉
         */
        int n=3;
        int N=MP.size();
        double pc=gaParameters.Pc;
        double L=problem.LowerLimit;
        double U=problem.UpperLimit;
        int nbDecVar=problem.NbVariablesDecision;
        List<IndividuVide> Enfants = new LinkedList<>();
        Random random=new Random();
        double Bi;
        for(int i=0;i<N;i=i+2){
            IndividuVide p1=MP.get(i);
            IndividuVide p2;
            if(i==N-1)
                p2=MP.get(i);
            else
                p2= MP.get(i+1);
            double ui=random.nextDouble();
            if(ui<=0.5)
                Bi=Math.pow(2*ui,1+1/n);
            else
                Bi=Math.pow(2*(1-ui),-(1+1/n));

            double a[]=new double[nbDecVar];
            double b[]=new double[nbDecVar];
            if(random.nextDouble()<pc){
                for(int mm=0;mm<nbDecVar;mm++){
                    a[mm]=0.5*(p1.value[mm]+p2.value[mm])+0.5*Bi*(p1.value[mm]-p2.value[mm]);
                    if(a[mm]<L)a[mm]=L;
                    else if(a[mm]>U)a[mm]=U;
                    b[mm]=a[mm];
                }
            }else{
                for(int mm=0;mm<nbDecVar;mm++){
                    a[mm]=p1.value[mm];
                    b[mm]=p2.value[mm];
                }
            }
            Enfants.add(new IndividuVide(a));
            Enfants.add(new IndividuVide(b));
        }
        if(Enfants.size()>N)Enfants.remove(N);
        return Enfants;
    }


    List<IndividuVide> SelectionTournoi(List<IndividuVide> P) {
        /**
         * @author: wangbufan
         * @date: 2019/12/4 15:05
         * @description 比赛选择要进行交配和变异的集合
         */

        int k = 2;
        int N = P.size();
        Random random = new Random();
        List<IndividuVide> MP = new LinkedList<>();
        int a, b;
        for (int i = 0; i < N; i++) {
            a = random.nextInt(N);
            b = random.nextInt(N);
            if (P.get(a).F > P.get(b).F)
                MP.add(P.get(a));
            else
                MP.add(P.get(b));
        }
        return MP;
    }

    List<IndividuVide> selection(List<IndividuVide> P) {
        /**
         * @author: wangbufan
         * @date: 2019/12/3 20:01
         * @description 选择要遗传到下一代的精英集合
         */
        int NP = P.size();
        int archiveSize = gaParameters.ArchiveSize;
        List<IndividuVide> nextGenArchive = new LinkedList<>();
        for (int i = 0; i < NP; i++) {
            if (P.get(i).F < 1) {
                nextGenArchive.add(P.get(i));
            }
        }
        if (nextGenArchive.size() <= archiveSize) {
            Collections.sort(P);
            for (int i = nextGenArchive.size(); i < archiveSize; i++)
                nextGenArchive.add(P.get(i));
        } else {
            double sigma[][] = new double[NP][nextGenArchive.size()];
            for (int i = 0; i < nextGenArchive.size(); i++) {
                for (int j = 0; j < NP; j++) {
                    sigma[j][i] = nextGenArchive.get(i).sigma[j];
                }
            }
            while (nextGenArchive.size() > archiveSize) {
                int k = 2;
                int mm = 0;
                while (k < NP) {
                    mm = 0;
                    boolean istrue = false;
                    for (int i = 1; i < nextGenArchive.size(); i++) {
                        if (!istrue && sigma[k][mm] != sigma[k][i]) {
                            istrue = true;
                        }
                        if (sigma[k][mm] > sigma[k][i]) mm = i;
                    }
                    if (istrue) break;
                    k++;
                }
                nextGenArchive.remove(mm);
                for (int i = 0; i < NP; i++) {
                    sigma[i][mm] = sigma[i][nextGenArchive.size()];
                }
            }
        }
        return nextGenArchive;
    }

    void evaluation(List<IndividuVide> P) {
         /**
           * @author: wangbufan
           * @date: 2019/12/4 15:59
           * @description 计算一个组中的数据
           */
        int N = gaParameters.PopSize;
        int NA = gaParameters.ArchiveSize;
        int NP = P.size();
        boolean dominance[][] = new boolean[NP][NP];
        int k = (int) Math.round(Math.sqrt(N + NA));//计算到第k个值的距离


        for (IndividuVide mm : P) {
            mm.S = 0;
        }
        //计算S
        for (int i = 0; i < NP; i++) {
            for (int j = i + 1; j < NP; j++) {
                IndividuVide pi = P.get(i);
                IndividuVide pj = P.get(j);
                if (pi.compare(pj)) {
                    pi.S++;
                    dominance[i][j] = true;
                } else if (pj.compare(pi)) {
                    pj.S++;
                    dominance[j][i] = true;
                }
            }
        }
        //计算R
        for (int i = 0; i < NP; i++) {
            P.get(i).R = 0;
            for (int j = 0; j < NP; j++) {
                if (dominance[j][i]) P.get(i).R++;
            }
        }

        //计算距离
        double sigmas[][] = new double[NP][NP];
        for (int i = 0; i < NP; i++) {
            for (int j = 0; j < NP; j++) {
                double mm = P.get(i).distance(P.get(j));
                sigmas[i][j] = mm;
                sigmas[j][i] = mm;
            }
        }
        for (int i = 0; i < NP; i++) {
            double sigma[] = sigmas[i];
            Arrays.sort(sigma);
            IndividuVide iv = P.get(i);
            iv.sigma = sigma;
            iv.sigmak = sigma[k];
            iv.D = 1 / (iv.sigmak + 2);
            iv.F = iv.R + iv.D;
        }
    }


    List<IndividuVide> init() {
        /**
         * @author: wangbufan
         * @date: 2019/12/3 16:55
         * @description 初始换一个组
         */
        double mmax = problem.UpperLimit;
        double mmin = problem.LowerLimit;
        int n = problem.NbVariablesDecision;
        int N = gaParameters.PopSize;
        Random random = new Random();

        IndividuVide Po[] = new IndividuVide[N];

        for (int i = 0; i < N; i++) {
            double[] mm = new double[n];
            for (int j = 0; j < n; j++)
                mm[j] = mmin + random.nextDouble() * (mmax - mmin);
            Po[i] = new IndividuVide(mm);
            Po[i].valObjective = problem.Calculation(Po[i].value);
        }

        return new LinkedList<IndividuVide>(Arrays.asList(Po));
    }


}

