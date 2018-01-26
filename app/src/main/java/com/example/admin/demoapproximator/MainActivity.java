package com.example.admin.demoapproximator;


import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements OnChartGestureListener {

    double[][] dataXY = {{1, 2, 3, 4, 5}, {1, 2, 2.5, 1.8, 3}};
    double[] dataX = {0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9,
                        1, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8, 1.9,
                        2, 2.1, 2.2, 2.3, 2.4, 2.5, 2.6, 2.7, 2.8, 2.9,
                        3, 3.1, 3.2, 3.3, 3.4, 3.5, 3.6, 3.7, 3.8, 3.9,
                        4, 4.1, 4.2, 4.3, 4.4, 4.5, 4.6, 4.7, 4.8, 4.9,
                        5, 5.1, 5.2, 5.3, 5.4, 5.5, 5.6, 5.7, 5.8, 5.9, 6};
    double[] arrayApproximatedLeastSquareMethod;
    double[] arrayApproximatedLagrangePolynomial;

    LineChart chart;
    ArrayList<Entry> xValues1;
    ArrayList<Entry> xValues2;
    ArrayList<Entry> xValues3;
    ArrayList<Entry> xValues4;
    ArrayList<Entry> xValues5;
    ArrayList<Entry> squareValues;
    ArrayList<Entry> lagrangeValues;
    LineDataSet setX1;
    LineDataSet setX2;
    LineDataSet setX3;
    LineDataSet setX4;
    LineDataSet setX5;
    LineDataSet setSquare;
    LineDataSet setLagrange;
    LineData lineData;

    int x;
    double y;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chart = (LineChart) findViewById(R.id.chart);
        chart.setOnChartGestureListener(MainActivity.this);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(false);

        MainActivity mainActivity = (MainActivity) getLastCustomNonConfigurationInstance();
        if (mainActivity != null) {
            dataXY = mainActivity.dataXY;
        }

        // X 1
        xValues1 = new ArrayList<>();
        xValues1.add(new Entry((float) dataXY[0][0], (float) dataXY[1][0]));
        setX1 = new LineDataSet(xValues1, "x = 1");
        setX1.setColor(Color.parseColor("#8B4513"));
        setX1.setValueTextColor(Color.parseColor("#8B4513"));
        setX1.setCircleColor(Color.parseColor("#8B4513"));
        setX1.setCircleRadius(7);

        // X 2
        xValues2 = new ArrayList<>();
        xValues2.add(new Entry((float) dataXY[0][1], (float) dataXY[1][1]));
        setX2 = new LineDataSet(xValues2, "x = 2");
        setX2.setColor(Color.parseColor("#32CD32"));
        setX2.setValueTextColor(Color.parseColor("#32CD32"));
        setX2.setCircleColor(Color.parseColor("#32CD32"));
        setX2.setCircleRadius(7);

        // X 3
        xValues3 = new ArrayList<>();
        xValues3.add(new Entry((float) dataXY[0][2], (float) dataXY[1][2]));
        setX3 = new LineDataSet(xValues3, "x = 3");
        setX3.setColor(Color.parseColor("#FF7F50"));
        setX3.setValueTextColor(Color.parseColor("#FF7F50"));
        setX3.setCircleColor(Color.parseColor("#FF7F50"));
        setX3.setCircleRadius(7);

        // X 4
        xValues4 = new ArrayList<>();
        xValues4.add(new Entry((float) dataXY[0][3], (float) dataXY[1][3]));
        setX4 = new LineDataSet(xValues4, "x = 4");
        setX4.setColor(Color.parseColor("#008B8B"));
        setX4.setValueTextColor(Color.parseColor("#008B8B"));
        setX4.setCircleColor(Color.parseColor("#008B8B"));
        setX4.setCircleRadius(7);

        // X 5
        xValues5 = new ArrayList<>();
        xValues5.add(new Entry((float) dataXY[0][4], (float) dataXY[1][4]));
        setX5 = new LineDataSet(xValues5, "x = 5");
        setX5.setColor(Color.parseColor("#9400D3"));
        setX5.setValueTextColor(Color.parseColor("#9400D3"));
        setX5.setCircleColor(Color.parseColor("#9400D3"));
        setX5.setCircleRadius(7);

        //LeastSquareMethod
        LeastSquareMethod leastSquareMethod = new LeastSquareMethod(dataXY);
        arrayApproximatedLeastSquareMethod = leastSquareMethod.approximate(dataX);
        squareValues = new ArrayList<>();
        fillSquareValues();
        setSquare = new LineDataSet(squareValues, "Square");
        setSquare.setColor(Color.parseColor("#DC143C"));
        setSquare.setDrawCircles(false);
        setSquare.setDrawValues(false);
        setSquare.setLineWidth(3);

        //lagrangePolynomial
        LagrangePolynomial lagrangePolynomial = new LagrangePolynomial(dataXY);
        arrayApproximatedLagrangePolynomial = lagrangePolynomial.approximate(dataX);
        lagrangeValues = new ArrayList<>();
        fillLagrangeValues();
        setLagrange = new LineDataSet(lagrangeValues, "Lagrange");
        setLagrange.setColor(Color.parseColor("#0000FF"));
        setLagrange.setDrawCircles(false);
        setLagrange.setDrawValues(false);
        setLagrange.setLineWidth(3);

        // Chart
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(setX1);
        dataSets.add(setX2);
        dataSets.add(setX3);
        dataSets.add(setX4);
        dataSets.add(setX5);
        dataSets.add(setSquare);
        dataSets.add(setLagrange);
        lineData = new LineData(dataSets);

        Description description = new Description();
        description.setText("");
        chart.setDescription(description);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setAxisLineWidth(5);
        xAxis.setAxisMaximum(6);

        chart.setData(lineData);
    }


    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        x = (int) (chart.getValuesByTouchPoint(me.getX(), me.getY(), YAxis.AxisDependency.LEFT)).x;
        switch (x) {
            case 1:
                setX1.setVisible(false);
                break;
            case 2:
                setX2.setVisible(false);
                break;
            case 3:
                setX3.setVisible(false);
                break;
            case 4:
                setX4.setVisible(false);
                break;
            case 5:
                setX5.setVisible(false);
                break;
            default:
                break;
        }
    }


    private void approximateLeastSquareMethod() {
        LeastSquareMethod leastSquareMethod = new LeastSquareMethod(dataXY);
        arrayApproximatedLeastSquareMethod = leastSquareMethod.approximate(dataX);
        int countSquare = squareValues.size();
        for (int i = 0; i < countSquare; i++) {
            squareValues.remove(0);
        }
        fillSquareValues();
    }


    private void fillSquareValues() {
        for (int i = 0; i < dataX.length; i++) {
            squareValues.add(new Entry((float) dataX[i], (float) arrayApproximatedLeastSquareMethod[i]));
        }

    }


    private void approximateLagrangePolynomial() {
        LagrangePolynomial lagrangePolynomial = new LagrangePolynomial(dataXY);
        arrayApproximatedLagrangePolynomial = lagrangePolynomial.approximate(dataX);
        int countLagrange = lagrangeValues.size();
        for (int i = 0; i < countLagrange; i++) {
            lagrangeValues.remove(0);
        }
        fillLagrangeValues();
    }


    private void fillLagrangeValues() {
        for (int i = 0; i < dataX.length; i++) {
            lagrangeValues.add(new Entry((float) dataX[i], (float) arrayApproximatedLagrangePolynomial[i]));
        }
    }


    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        if (me.getAction() == MotionEvent.ACTION_UP) {
            y = (chart.getValuesByTouchPoint(me.getX(), me.getY(), YAxis.AxisDependency.LEFT)).y;
            switch (x) {
                case 1:
                    dataXY[1][x - 1] = y;
                    xValues1.remove(0);
                    xValues1.add(new Entry(1, (float) y));
                    approximateLeastSquareMethod();
                    approximateLagrangePolynomial();
                    setX1.setVisible(true);
                    break;
                case 2:
                    dataXY[1][x - 1] = y;
                    xValues2.remove(0);
                    xValues2.add(new Entry(2, (float) y));
                    approximateLeastSquareMethod();
                    approximateLagrangePolynomial();
                    setX2.setVisible(true);
                    break;
                case 3:
                    dataXY[1][x - 1] = y;
                    xValues3.remove(0);
                    xValues3.add(new Entry(3, (float) y));
                    approximateLeastSquareMethod();
                    approximateLagrangePolynomial();
                    setX3.setVisible(true);
                    break;
                case 4:
                    dataXY[1][x - 1] = y;
                    xValues4.remove(0);
                    xValues4.add(new Entry(4, (float) y));
                    approximateLeastSquareMethod();
                    approximateLagrangePolynomial();
                    setX4.setVisible(true);
                    break;
                case 5:
                    dataXY[1][x - 1] = y;
                    xValues5.remove(0);
                    xValues5.add(new Entry(5, (float) y));
                    approximateLeastSquareMethod();
                    approximateLagrangePolynomial();
                    setX5.setVisible(true);
                    break;
                default:
                    break;
            }
            chart.notifyDataSetChanged();
        }

    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return this;
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

}
