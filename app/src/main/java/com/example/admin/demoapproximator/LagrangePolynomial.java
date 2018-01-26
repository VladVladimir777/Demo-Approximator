package com.example.admin.demoapproximator;


import java.math.BigDecimal;
import java.util.Arrays;


public class LagrangePolynomial {

    private double[][] table;
    private BigDecimal[] dataPolynomial;



    public LagrangePolynomial(double[][] table) {
        this.table = table;
        createPolynomial();
    }


    private void createPolynomial() {

        // the dataXY for the function (x^3, x^2, x, number) is a polynomial of degree 3
        dataPolynomial = new BigDecimal[5];
        BigDecimal n = new BigDecimal(0);
        Arrays.fill(dataPolynomial, n);


        for (int i = 0; i < table[0].length; i++ ) {

            int count = 0;

            // the base polynomial
            BigDecimal[] data = new BigDecimal[4];

            // the multiplier for the basic polynomial
            BigDecimal y = new BigDecimal(table[1][i]);

            for (int j = 0; j < table[0].length; j++) {
                if (table[0][j] != table[0][i]) {
                    data[count++] = new BigDecimal(table[0][j] * -1);
                }
            }

            //double denominator = (table[0][i] + dataXY[0]) * (table[0][i] + dataXY[1]) *(table[0][i] + dataXY[2]) *(table[0][i] + dataXY[3]);
            BigDecimal denominator = (new BigDecimal(table[0][i]).add(data[0]))
                    .multiply((new BigDecimal(table[0][i]).add(data[1])))
                    .multiply((new BigDecimal(table[0][i]).add(data[2])))
                    .multiply((new BigDecimal(table[0][i]).add(data[3])));

            // number
            //dataPolynomial[0] = y * (dataXY[0] * dataXY[1] * dataXY[2] * dataXY[3]) / denominator;
            BigDecimal z = y.multiply(data[0])
                                .multiply(data[1])
                                .multiply(data[2])
                                .multiply(data[3])
                                .divide(denominator, 4, BigDecimal.ROUND_HALF_UP);
            dataPolynomial[0] = dataPolynomial[0].add(z);

            // x
            //dataPolynomial[1] = y * (((dataXY[1] + dataXY[0]) * dataXY[2] * dataXY[3] + dataXY[0] * dataXY[1] * (dataXY[3] + dataXY[2])) / denominator);
            BigDecimal x = y.multiply(((data[1].add(data[0])).multiply(data[2]).multiply(data[3]))
                    .add((data[0].multiply(data[1]).multiply(data[3].add(data[2])))))
                    .divide(denominator, 4, BigDecimal.ROUND_HALF_UP);
            dataPolynomial[1] = dataPolynomial[1].add(x);

            // x^2
            //dataPolynomial[2] = y * ((dataXY[2] * dataXY[3] + (dataXY[1] + dataXY[0]) * (dataXY[3] + dataXY[2]) + dataXY[0] * dataXY[1]) / denominator);
            BigDecimal xx = y.multiply(data[2].multiply(data[3])
                    .add((data[1].add(data[0])).multiply(data[3].add(data[2])))
                    .add(data[0].multiply(data[1])))
                    .divide(denominator, 4, BigDecimal.ROUND_HALF_UP);

            dataPolynomial[2] = dataPolynomial[2].add(xx);

            //x^3
            //dataPolynomial[3] = y * (((dataXY[3] + dataXY[2]) + (dataXY[1] + dataXY[0])) / denominator);
            BigDecimal xxx = y.multiply(data[3].add(data[2]).add(data[1]).add(data[0]))
                    .divide(denominator, 4, BigDecimal.ROUND_HALF_UP);

            dataPolynomial[3] = dataPolynomial[3].add(xxx);

            //dataPolynomial[4] = y / denominator;
            BigDecimal xxxx = y.divide(denominator, 4, BigDecimal.ROUND_HALF_UP);
            dataPolynomial[4] = dataPolynomial[4].add(xxxx);
        }
    }


    public double[] approximate(double[] dataX) {

        double[] dataApproximated = new double[dataX.length];
        for (int i = 0; i < dataApproximated.length; i++) {

            BigDecimal x = new BigDecimal(dataX[i]);

            BigDecimal f = dataPolynomial[4].multiply(x).multiply(x).multiply(x).multiply(x)
                    .add(dataPolynomial[3].multiply(x).multiply(x).multiply(x))
                    .add(dataPolynomial[2].multiply(x).multiply(x))
                    .add(dataPolynomial[1].multiply(x))
                    .add(dataPolynomial[0]);
            dataApproximated[i] = Double.valueOf(String.valueOf(f.setScale(3, BigDecimal.ROUND_HALF_UP)));
        }
        return dataApproximated;
    }


}
