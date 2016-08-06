/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Proses.SVM;

/**
 *
 * @author Administrator
 */
public class SVM {
    /** Trained/loaded model */
    private double C = 10000;
<<<<<<< HEAD
    /* kernel yang digunakan */
=======
    /** kernel yang digunakan */
>>>>>>> origin/master
    private int kernel;
    /** Tolerance */
    private double tol = 10e-3;
    /** Tolerance */
    private double tol2 = 10e-5;
<<<<<<< HEAD
    /* variabel training */
=======
    /** variabel training */
>>>>>>> origin/master
    private int maxpass = 100;
    private double Ei, Ej;
    private double ai_old, aj_old, b_old;
    private double L, H;
    /* mapping data dan hasil training */
    private Kernel mapData;
    
    public SVM (Kernel map, int kernel){
        mapData = map;
        this.kernel = kernel;
        SMO_simple();
    }
    public double svmTestOne(Data x) {
        double f = 0;
	for (int i=0; i<mapData.x.length; i++) {
            f += mapData.x[i].alpha*mapData.x[i].target*hitungNilaiKernel(x, mapData.x[i]);
	}
	return f+mapData.b;
    }
    private void SMO_simple() {
	int pass = 0;
	int alpha_change = 0;
	int i, j;
	double eta;
	//Main iteration:
	while (pass < maxpass) {
            alpha_change = 0;
            for (i=0; i<mapData.x.length; i++) {
                Ei = svmTestOne(mapData.x[i]) - mapData.x[i].target;
                if ((mapData.x[i].target*Ei<-tol && mapData.x[i].alpha<C) || (mapData.x[i].target*Ei>tol && mapData.x[i].alpha>0)) {
                    j = (int)Math.floor(Math.random()*(mapData.x.length-1));
                    j = (j<i)?j:(j+1);
                    Ej = svmTestOne(mapData.x[j]) - mapData.x[j].target;
                    ai_old = mapData.x[i].alpha;
                    aj_old = mapData.x[j].alpha;
                    L = computeL(mapData.x[i].target, mapData.x[j].target);
                    H = computeH(mapData.x[i].target, mapData.x[j].target);
                    if (L == H) //next i
                        continue;
                    double kij = hitungNilaiKernel(mapData.x[i],mapData.x[j]); 
                    double kii = hitungNilaiKernel(mapData.x[i],mapData.x[i]); 
                    double kjj = hitungNilaiKernel(mapData.x[j],mapData.x[j]); 
                    eta = 2*kij-kii-kjj;
                    if (eta >= 0) //next i
                        continue;
                    mapData.x[j].alpha = aj_old - (mapData.x[j].target*(Ei-Ej))/eta;
                    if (mapData.x[j].alpha > H)
                        mapData.x[j].alpha = H;
                    else if (mapData.x[j].alpha < L)
                        mapData.x[j].alpha = L;
                    if (Math.abs(mapData.x[j].alpha-aj_old) < tol2) //next i
			continue;
                    mapData.x[i].alpha = ai_old + mapData.x[i].target*mapData.x[j].target*(aj_old-mapData.x[j].alpha);
                    computeBias(mapData.x[i].alpha, mapData.x[j].alpha, mapData.x[i].target, mapData.x[j].target, kii, kjj, kij);
                    alpha_change++;
		}
            }
            if (alpha_change == 0)
                pass++;
            else
                pass = 0;
        }
    }
    
    private void computeBias(double ai, double aj, int yi, int yj, double kii, double kjj, double kij) {
	double b1 = mapData.b - Ei - yi*(ai-ai_old)*kii - yj*(aj-aj_old)*kij;
	double b2 = mapData.b - Ej - yi*(ai-ai_old)*kij - yj*(aj-aj_old)*kjj;
	if (0 < ai && ai<C)
            mapData.b = b1;
	else if (0 < aj && aj < C)
            mapData.b = b2;
	else
            mapData.b = (b1+b2)/2;		
    }
    private double computeL(int yi, int yj) {
        double L = 0;
	if (yi != yj) {
            L = Math.max(0, -ai_old+aj_old);
	} else {
            L = Math.max(0, ai_old+aj_old-C);
	}
	return L;
    }
    private double computeH(int yi, int yj) {
        double H = 0;
	if (yi != yj) {
            H = Math.min(C, -ai_old+aj_old+C);
	} else {
            H = Math.min(C, ai_old+aj_old);
	}
	return H;
    }
    private double hitungNilaiKernel(Data a, Data b) {
        double ret = 0;
	switch (kernel) {
            case 0: //user defined
		break;
            case 1: //linear
		ret = Kernel.kLinear(a, b);
                break;
            case 3: //Gausian
		ret = Kernel.kGaussian(a, b, mapData.Sigma);
		break;
        }
	return ret;
    }
    public static void main (String []args){
        Kernel SVMLinemodel = new Kernel(4);
        SVMLinemodel.Sigma=1;
        SVMLinemodel.addData(new double[]{0,0.242535625,0,0,0,0.242535625}, 1);
        SVMLinemodel.addData(new double[]{0,0,0,0.401023948,0.271490202,0}, 1);
        SVMLinemodel.addData(new double[]{0.174077656,0,0.348155312,0,0,0}, -1);
        SVMLinemodel.addData(new double[]{0.5,0.5,0,0,0,0.5}, -1);
        Proses.SVM.SVM smoLineSimple = new Proses.SVM.SVM(SVMLinemodel,3);
        for(int i=0;i<4;i++){
            System.out.println(SVMLinemodel.x[i].alpha);
        }
        System.out.println(SVMLinemodel.b);
        System.out.println(smoLineSimple.svmTestOne(new Data(0,new double[]{4,0})));
    }
}
