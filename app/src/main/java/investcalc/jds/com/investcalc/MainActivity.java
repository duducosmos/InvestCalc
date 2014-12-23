package investcalc.jds.com.investcalc;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jds.investcalc.math.CompoundInterest;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        setLayout();
        addListenerOnButton();
    }

    public void addListenerOnButton() {

        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                getValuesFromView();
                calcularJuros();


            }

        });

    }

    private void calcularJuros() {
        ArrayList<Double> apliMensal = new ArrayList<Double>();
        apliMensal.add(valorAplicacaoMensal);
        CompoundInterest myInterest = new CompoundInterest(juros,
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
