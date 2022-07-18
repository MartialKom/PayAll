package cm.pfe.payall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class MainActivity extends AppCompatActivity {

    public EditText username;
    public  EditText password;
    public Button connexion;
    public TextView inscription;
    public ProgressBar progress;


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed(){
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        connexion = (Button) findViewById(R.id.loginbtn);
        inscription = (TextView) findViewById(R.id.inscription);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setVisibility(View.GONE);


        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
                finish();
            }
        });





        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = username.getText().toString();
                final String pass = password.getText().toString();


            try {
                progress.setVisibility(View.VISIBLE);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[2];
                        field[0] = "nom";
                        field[1] = "mot_de_passe";
                        //Creating array for data
                        String[] data = new String[2];
                        data[0] = "" + name;
                        data[1] = "" + pass;
                        PutData putData = new PutData("http://infoscampus.com/loginRegister/login.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                progress.setVisibility(View.GONE);
                                String result = putData.getResult();
                                if (!result.equals("Username or Password wrong")) {

                                    //Recuperer le numero de telephone et le nom

                                    Intent intent = new Intent(getApplicationContext(), Acceuil.class);
                                    //envoi des parametres à Acceuil.class
                                    intent.putExtra("telephone", result);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(), "Connexion reussie", Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "erreur: " + result, Toast.LENGTH_LONG).show();
                                    username.setError(result);
                                    password.setError(result);
                                }
                            }
                        }
                        //End Write and Read data with URL
                    }
                });

            }catch(Exception e){

                Toast.makeText(MainActivity.this, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
                return;
            }
            }
        });

    }
}
