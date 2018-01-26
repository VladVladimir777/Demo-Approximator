package com.example.admin.demoapproximator;

import java.math.BigDecimal;


public class LeastSquareMethod {

    private double[][] table;
    private BigDecimal[][] matrix;
    private BigDecimal[] dataPolynomial;


    public LeastSquareMethod(double[][] table) {
        this.table = table;
        createMatrix();
        gaussMethod();
    }

    private void createMatrix() {

        //  a0 + (a1 * x) + (a2 * x^2) + (a3 * x^3)
        matrix = new BigDecimal[4][5];
        for (int i = 0; i < matrix.length; i++) {

            int pow;
            if ( i == 0 | i == 1) {
                pow = 1;
            } else {
                pow = i;
            }

            for (int j = 0; j < matrix[0].length; j++) {
                if ((i == 0) & (j == 0)) {
                    matrix[i][j] = new BigDecimal(table[0].length).setScale(3, BigDecimal.ROUND_HALF_UP);
                } else if (j == (matrix[0].length - 1)) {
                    BigDecimal y = new BigDecimal(table[1][0]).multiply(new BigDecimal(table[0][0]).pow(i))
                            .add(new BigDecimal(table[1][1]).multiply(new BigDecimal(table[0][1]).pow(i)))
                            .add(new BigDecimal(table[1][2]).multiply(new BigDecimal(table[0][2]).pow(i)))
                            .add(new BigDecimal(table[1][3]).multiply(new BigDecimal(table[0][3]).pow(i)))
                            .add(new BigDecimal(table[1][4]).multiply(new BigDecimal(table[0][4]).pow(i)));
                    matrix[i][j] = y.setScale(3, BigDecimal.ROUND_HALF_UP);
                    pow++;
                } else {
                    BigDecimal x = new BigDecimal(table[0][0]).pow(pow)
                            .add(new BigDecimal(table[0][1]).pow(pow))
                            .add(new BigDecimal(table[0][2]).pow(pow))
                            .add(new BigDecimal(table[0][3]).pow(pow))
                            .add(new BigDecimal(table[0][4]).pow(pow));
                    matrix[i][j] = x.setScale(3, BigDecimal.ROUND_HALF_UP);
                    pow++;
                }
            }
        }

    }

    private void gaussMethod() {

        for (int i = 0; i < matrix.length; i++) {
            // divide a string into an excluded element
            BigDecimal z = matrix[i][i];
            for (int k = 0; k < matrix[i].length; k++) {
                matrix[i][k] = (matrix[i][k].divide(z, 3, BigDecimal.ROUND_HALF_UP)).setScale(3, BigDecimal.ROUND_HALF_UP);
            }
            // subtract from each line the i-th line multiplied by the excluded element of the line
            for (int j = 0; j < matrix.length; j++) {
                if (j != i) {
                    BigDecimal s = matrix[j][i];
                    for (int k = 0; k < matrix[j].length; k++) {
                        matrix[j][k] = (matrix[j][k].subtract(matrix[i][k].multiply(s))).setScale(3, BigDecimal.ROUND_HALF_UP);
                    }
                }
            }
        }

        // array dataXY matrix
        dataPolynomial = new BigDecimal[matrix.length];

        // get dataXY from the matrix
        for (int i = 0; i < matrix.length; i++) {
            dataPolynomial[i] = matrix[i][matrix.length];
        }

    }

    double[] approximate(double[] dataX) {

        double[] dataApproximated = new double[dataX.length];
        for (int i = 0; i < dataApproximated.length; i++) {

            BigDecimal x = new BigDecimal(dataX[i]);

            BigDecimal f = dataPolynomial[3].multiply(x).multiply(x).multiply(x)
                    .add(dataPolynomial[2].multiply(x).multiply(x))
                    .add(dataPolynomial[1].multiply(x))
                    .add(dataPolynomial[0]);
            dataApproximated[i] = Double.valueOf(String.valueOf(f.setScale(3, BigDecimal.ROUND_HALF_UP)));
        }
        return dataApproximated;
    }


}
