import java.io.*;

public class Polynomial {
	
    double[] coefficients;
	int[] exponents;
	BufferedReader buffer;
	
    public Polynomial() {
        coefficients = new double[1];
		exponents = new int[1];
    }

    public Polynomial(double[] coffs, int[] exps) {
		coefficients = new double[coffs.length];
		exponents = new int[exps.length];

        for (int i = 0; i < coffs.length; i++) {
			coefficients[i] = coffs[i];
		}
		
		for (int i = 0; i < exps.length; i++) {
			exponents[i] = exps[i];
		}
    }

	public Polynomial(File file) throws Exception{
		String input = "";	
		buffer = new BufferedReader(new FileReader(file));

		input = buffer.readLine();
		String[] parts = input.split("\\+|(?=-)");
		
		coefficients = new double[parts.length];
		exponents = new int[parts.length];

		int coffCtr = 0;
		int expCtr = 0;
		for (int i = 0; i < parts.length; i++) {
			int varInd = parts[i].indexOf("x");
			if (varInd == -1) {
				coefficients[coffCtr] = Double.parseDouble(parts[i]);
			}
			else {
				String subCoff = parts[i].substring(0, varInd);
				if (subCoff.length() == 0) {
					coefficients[coffCtr] = 1;
				}
				else if (subCoff.length() == 1 && Character.compare(subCoff.charAt(0), '-') == 0) {
					coefficients[coffCtr] = -1;
				}
				else {
					coefficients[coffCtr] = Double.parseDouble(subCoff);
				}

				String subExp = parts[i].substring(varInd+1, parts[i].length());
				if (subExp.length() == 0) {
					exponents[expCtr] = 1;
				}
				else {
					exponents[expCtr] = Integer.parseInt(subExp);
				}
			}
			
			coffCtr++;
			expCtr++;
		}

		/*for (int x = 0; x < parts.length; x++) {
			System.out.print("Coff: " + coefficients[x]);
            System.out.println(", Exp: " + exponents[x]);
		}*/
	}

        
    public Polynomial add(Polynomial poly) {
		
		int parLen = poly.coefficients.length;
    	int callLen = this.coefficients.length;

		double[] coffs = new double[parLen+callLen];
		int[] exps = new int[parLen+callLen];

		int ctr = 0; // tracks final polynomial ind
		int indCtr = 0; // tracks number of elements used (one coff == one element and one exp == one element)
		int i = 0;
		int j = 0;

		while (indCtr < parLen+callLen) {
			if (i >= parLen) {
				exps[ctr] = this.exponents[j];
				coffs[ctr] = this.coefficients[j];
				j++;
				ctr++;
				indCtr++;
			}
			else if (j >= callLen) {
				exps[ctr] = poly.exponents[i];
				coffs[ctr] = poly.coefficients[i];
				i++;
				ctr++;
				indCtr++;
			}	
			else {
				if (poly.exponents[i] == this.exponents[j]) {
					coffs[ctr] = poly.coefficients[i] + this.coefficients[j];
					exps[ctr] = poly.exponents[i];
	
					i++;
					j++;
					ctr++;
					indCtr += 2;
				}
				else {
					if (poly.exponents[i] < this.exponents[j]) {
						exps[ctr] = poly.exponents[i];
						coffs[ctr] = poly.coefficients[i];
						i++;
					}
					else {
						exps[ctr] = this.exponents[j];
						coffs[ctr] = this.coefficients[j];
						j++;
					}
					ctr++;
					indCtr++;
				}
			}
		}

		// Filter out all terms with zero coefficients
		int newArrLen = 0;
		for (int x = 0; x < coffs.length; x++) {
			if (coffs[x] != 0.0) {
				newArrLen++;
			}
		}
		double[] finalCoffs = new double[newArrLen];
		int[] finalExps = new int[newArrLen];
		int lastInd = 0;
		for (int y = 0; y < coffs.length; y++) {
			if (coffs[y] != 0.0) {
				finalCoffs[lastInd] = coffs[y];
				finalExps[lastInd] = exps[y];

				lastInd++;
			}
		}

		Polynomial newPoly = new Polynomial(finalCoffs, finalExps);
    	return newPoly;
    }


	public Polynomial multiply(Polynomial poly) {
		int parLen = poly.coefficients.length;
    	int callLen = this.coefficients.length;

		// Get a good estimate of final array size
		int maxCallExp = 0;
		for (int a = 0; a < callLen; a++) {
			if (this.exponents[a] > maxCallExp) {
				maxCallExp = this.exponents[a];
			}
		}
		int maxParExp = 0;
		for (int b = 0; b < parLen; b++) {
			if (poly.exponents[b] > maxParExp) {
				maxParExp = poly.exponents[b];
			}
		}
		int finalArrSize = maxParExp+1 * maxCallExp+1;


		double[] coffs = new double[finalArrSize];
		int[] exps = new int[finalArrSize];

		for (int i = 0; i < poly.exponents.length; i++) {
			for (int j = 0; j < this.exponents.length; j++) {
				double prod = poly.coefficients[i] * this.coefficients[j];
				int newExp = poly.exponents[i] + this.exponents[j];

				exps[newExp] = newExp;
				coffs[newExp] += prod;
			}
		}

		// Filter out all terms with zero coefficients
		int newArrLen = 0;
		for (int x = 0; x < coffs.length; x++) {
			if (coffs[x] != 0.0) {
				newArrLen++;
			}
		}
		double[] finalCoffs = new double[newArrLen];
		int[] finalExps = new int[newArrLen];
		int lastInd = 0;
		for (int y = 0; y < coffs.length; y++) {
			if (coffs[y] != 0.0) {
				finalCoffs[lastInd] = coffs[y];
				finalExps[lastInd] = exps[y];

				lastInd++;
			}
		}

		Polynomial newPoly = new Polynomial(finalCoffs, finalExps);
		return newPoly;
	}

    
    public double evaluate(double num) {
    	double result = 0.0;
    	
    	for (int i = 0; i < this.coefficients.length; i++) {
    		result += (Math.pow(num, this.exponents[i]) * this.coefficients[i]); 
    	}
    	
    	return result;
    }

    
    public boolean hasRoot(double num) {
    	
    	return (evaluate(num) == 0);
    }


	public void saveToFile(String fileName) throws Exception{
		FileWriter writer = new FileWriter(fileName, false);
		String coff;
		String exp;

		for (int i = 0; i < this.coefficients.length; i++) {
			coff = Integer.toString((int)this.coefficients[i]) + "x";
			exp = Integer.toString(this.exponents[i]);

			if (Character.compare(exp.charAt(0), '0') == 0) {
				exp = "";
				coff = coff.substring(0, coff.length()-1);
			}

			if (i > 0 && Character.compare(coff.charAt(0), '-') != 0) {
				writer.write("+");
			}
			writer.write(coff + exp);
		}

		writer.write("\n");
		writer.close();
	}
}