package cm.pfe.payall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class SignUp extends AppCompatActivity {

    public TextInputEditText nom,prenom,dateNaiss,numCNI, numtel,motPasse, cmotPasse;
    public Button logbtn;
    public Button backbtn;
    public ProgressBar progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        logbtn = findViewById(R.id.loginbtn);
        backbtn = findViewById(R.id.backbtn);
        progress = findViewById(R.id.progressBar2);
        progress.setVisibility(View.GONE);
        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        dateNaiss = findViewById(R.id.date);
        numCNI = findViewById(R.id.numcni);
        numtel = findViewById(R.id.numtel);
        motPasse = findViewById(R.id.pass);
        cmotPasse = findViewById(R.id.cpass);


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });



        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name=String.valueOf(nom.getText());
                final String lastname = String.valueOf(prenom.getText());
                final String date= String.valueOf(dateNaiss.getText());
                final String numcni = String.valueOf(numCNI.getText());
                final String numero = String.valueOf(numtel.getText());
                final String mdp = String.valueOf(motPasse.getText());
                String cmdp = String.valueOf(cmotPasse.getText());

                /*
                *
                * on vérifie que les champs sont tous bien entrés
                *
                * On vérifie que les champs mdp et cmdp sont équivalentes*/

                if(!cmdp.equals(mdp))
                {
                    motPasse.setError("Les champs ne correspondent pas");
                    cmotPasse.setError("Les champs ne correspondent pas");

                    return;
                }

                if(name.equals("") || lastname.equals("") || date.equals("") || numcni.equals("") || numero.equals("") || mdp.equals("") || cmdp.equals(""))
                {
                  nom.setError("Veuillez remplir tous les champs");
                  prenom.setError("Veuillez remplir tous les champs");
                  dateNaiss.setError("Veuillez remplir tous les champs");
                  numCNI.setError("Veuillez remplir tous les champs");
                  numtel.setError("Veuillez remplir tous les champs");
                  motPasse.setError("Veuillez remplir tous les champs");
                  cmotPasse.setError("Veuillez remplir tous les champs");

                  return;
                }

                try {
                    progress.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {


                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[6];
                            field[0] = "nom";
                            field[1] = "prenom";
                            field[2] = "date_naiss";
                            field[3] = "num_cni";
                            field[4] = "tel";
                            field[5] = "mot_de_passe";
                            //Creating array for data
                            String[] data = new String[6];
                            data[0] = "" + name;
                            data[1] = "" + lastname;
                            data[2] = "" + date;
                            data[3] = "" + numcni;
                            data[4] = "" + numero;
                            data[5] = "" + mdp;
                            PutData putData = new PutData("http://infoscampus.com/loginRegister/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progress.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if (result.equals("Sign Up Success")) {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "erreur: " + result, Toast.LENGTH_LONG).show();

                                    }

                                }
                            }
                            //End Write and Read data with URL
                        }
                    });

                }catch (Exception e){
                    Toast.makeText(SignUp.this, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });



    }

}
