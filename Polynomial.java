public class Polynomial {
	
    double[] coefficients;
	
    public Polynomial() {
        coefficients = new double[1];
    }

    public Polynomial(double[] arr) {
        coefficients = arr;
    }
        
    public Polynomial add(Polynomial poly) {
    	int parLen = poly.coefficients.length;
    	int callLen = this.coefficients.length;
    	int minLen = 0;
    	
    	double[] coffs;
  
    	if (parLen > callLen) {
    		coffs = poly.coefficients;
    		minLen = callLen;
    		
    		for (int i = 0; i < minLen; i++) {
        		coffs[i] += this.coefficients[i];
        	}
    	}
    	else {
    		coffs = this.coefficients;
    		minLen = parLen;
    		
    		for (int i = 0; i < minLen; i++) {
        		coffs[i] += poly.coefficients[i];
        	}
    	}

    	Polynomial newPoly = new Polynomial(coffs);
    	
    	return newPoly;
    }
    
    public double evaluate(double num) {
    	double result = 0.0;
    	
    	for (int i = 0; i < this.coefficients.length; i++) {
    		result += (Math.pow(num, i) * this.coefficients[i]); 
    	}
    	
    	return result;
    }
    
    public boolean hasRoot(double num) {
    	
    	return (evaluate(num) == 0);
    }
}