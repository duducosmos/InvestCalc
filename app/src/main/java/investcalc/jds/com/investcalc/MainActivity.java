package investcalc.jds.com.investcalc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jds.investcalc.math.CompoundInterest;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    CompoundInterest myInterest;
    private EditText jurosET;
    private EditText inflacaoET;
    private EditText aplicacaoInicialET;
    private EditText periodoMesesET;
    private EditText valorAplicacaoMensalET;
    private TextView valorNominalET;
    private TextView valorRealET;
    private TextView valorNominalTV;
    private TextView valorRealTV;
    private TextView textViewInvestido;
    private CheckBox aplicarTodoMesCK;
    private Button button;
    private float juros;
    private float inflacao;
    private float aplicacaoInicial;
    private Integer periodoMeses;
    private Double valorAplicacaoMensal;
    private float valorNominal;
    private float valorReal;
    private boolean aplicarTodoMes;
    private AlertDialog alerta;
    private GraphicalView mChart;
    private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
    private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
    private XYSeries mCurrentSeries;
    private XYSeries realSeries;
    private XYSeriesRenderer realRenderer;
    private XYSeriesRenderer mCurrentRender;
    private DisplayMetrics displayMetrics;
    private boolean isPortrait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        displayMetrics = (this.getResources()).getDisplayMetrics();
        setLayout();
        addListenerOnButton();
        initChart();
    }

    int _dp(float pixels) {
        return (int) (pixels * displayMetrics.density);
    }

    float _sp(float pixels) {
        return (pixels * displayMetrics.scaledDensity);
    }

    private void initChart() {
        mCurrentSeries = new XYSeries("Nominal Value");
        realSeries = new XYSeries("Real Value");
        mDataset.addSeries(mCurrentSeries);
        mDataset.addSeries(realSeries);
        mCurrentRender = new XYSeriesRenderer();
        realRenderer = new XYSeriesRenderer();
        mCurrentRender.setColor(Color.BLUE);
        realRenderer.setColor(Color.RED);

        mRenderer.addSeriesRenderer(mCurrentRender);
        mRenderer.addSeriesRenderer(realRenderer);
        mRenderer.setXTitle("Mês");
        //mRenderer.setYTitle("Acumulado");
        mRenderer.setYLabelsAlign(Paint.Align.RIGHT);
        mRenderer.setAxisTitleTextSize(_sp(25));
        mRenderer.setChartTitleTextSize(_sp(25));
        mRenderer.setYLabels(15);
        mRenderer.setXLabels(15);


    }

    private void addSampleData() {
        ArrayList<Double> yValues = myInterest.getNominalEvolution();
        ArrayList<Double> yRealV = myInterest.getRealEvolution();
        int ySize = yValues.size();
        mRenderer.setXAxisMax((double) ySize);
        mRenderer.setYAxisMax(yValues.get(ySize - 1));
        for (int i = 1; i <= ySize; i++) {
            mCurrentSeries.add(i, yValues.get(i - 1));
            realSeries.add(i, yRealV.get(i - 1));
        }

    }

    private void generateChart() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
        if (this.aplicarTodoMes) {

            if (mChart == null) {

                mCurrentSeries.clear();
                realSeries.clear();

                addSampleData();
                mChart = ChartFactory.getLineChartView(this, mDataset, mRenderer);
                //getCubeLineChartView(this, mDataset, mRenderer, 0.3f);

                layout.removeAllViews();
                layout.addView(mChart);
            } else {
                mChart.repaint();
            }
        } else {
            layout.removeAllViews();
        }


    }



    private void informacoes() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("About");
        builder.setMessage("Calculadora simples de " +
                "Juros compostos\n" +
                "by: jabuticabads@gmail.com");
        builder.setNeutralButton("O.K.", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                }
        );

        alerta = builder.create();
        alerta.show();
    }

    public void addListenerOnButton() {

        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                mChart = null;

                getValuesFromView();
                calcularJuros();
                generateChart();


            }

        });

    }

    private void calcularJuros() {
        ArrayList<Double> apliMensal = new ArrayList<Double>();
        apliMensal.add(valorAplicacaoMensal);
        myInterest = new CompoundInterest(juros,
                inflacao,
                aplicacaoInicial,
                periodoMeses,
                aplicarTodoMes,
                apliMensal
        );


        valorNominalTV.setText(myInterest.accumulatedNominal().toString());
        valorRealTV.setText(myInterest.accumulatedReal().toString());
        textViewInvestido.setText(myInterest.vested().toString());
    }


    private void getValuesFromView() {


        juros = Float.parseFloat(jurosET.getText().toString()) / 100f;
        inflacao = Float.parseFloat(inflacaoET.getText().toString()) / 100f;
        aplicacaoInicial = Float.parseFloat(aplicacaoInicialET.getText().toString());
        periodoMeses = Integer.parseInt(periodoMesesET.getText().toString());
        valorAplicacaoMensal = Double.parseDouble(valorAplicacaoMensalET.getText().toString());
        aplicarTodoMes = aplicarTodoMesCK.isChecked();

    }


    private void setLayout() {
        jurosET = (EditText) findViewById(R.id.editText);
        inflacaoET = (EditText) findViewById(R.id.editText2);
        aplicacaoInicialET = (EditText) findViewById(R.id.editText3);
        periodoMesesET = (EditText) findViewById(R.id.editText4);
        valorAplicacaoMensalET = (EditText) findViewById(R.id.editText5);
        aplicarTodoMesCK = (CheckBox) findViewById(R.id.checkBox);
        valorNominalTV = (TextView) findViewById(R.id.textViewNominal);
        valorRealTV = (TextView) findViewById(R.id.textViewReal);
        textViewInvestido = (TextView) findViewById(R.id.textViewInvestido);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action_about) {
            informacoes();

        }

        return super.onOptionsItemSelected(item);
    }
}
