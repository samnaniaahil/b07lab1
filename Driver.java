import java.io.*;

public class Driver { 
    public static void main(String [] args) throws Exception{ 
        //Polynomial p = new Polynomial(); 
        //System.out.println(p.evaluate(3));

        double [] c1 = {6, -2, 5, 3};
        int [] e1 = {0, 2, 4, 4};
        Polynomial p1 = new Polynomial(c1, e1); 
        double [] c2 = {1, 4, 7, -12};
        int [] e2 = {0, 2, 7, 4};
        Polynomial p2 = new Polynomial(c2, e2);

        /*Polynomial s = p1.add(p2); 
        for (int i = 0; i < s.coefficients.length; i++) {
            System.out.print("Coff: " + s.coefficients[i]);
            System.out.println(", Exp: " + s.exponents[i]);
        }*/

        /*Polynomial s1 = p1.multiply(p2);
        for (int i = 0; i < s1.coefficients.length; i++) {
            System.out.print("Coff: " + s1.coefficients[i]);
            System.out.println(", Exp: " + s1.exponents[i]);
        }*/


        File f = new File("poly.txt");
        Polynomial p3 = new Polynomial(f);
        for (int i = 0; i < p3.coefficients.length; i++) {
            System.out.print("Coff: " + p3.coefficients[i]);
            System.out.println(", Exp: " + p3.exponents[i]);
        }

        p3.saveToFile("output.txt");

        //System.out.println("P3 at 3:" + p3.evaluate(3));

        /*
        System.out.println("s(0.1) = " + s.evaluate(0.1)); 
        if(s.hasRoot(1)) 
            System.out.println("1 is a root of s"); 
        else 
            System.out.println("1 is not a root of s");
        */
    } 
} 