package cm.pfe.payall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Acceuil extends AppCompatActivity {

    public TextInputEditText numRecept,montantE, codeE, numero, montantRetrait, codeRetrait, nom, prenom,date, numCni, pass, cpass ;
    public TextView number, solde;
    public EditText code;
    public String telephone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);


        final DrawerLayout drawerlayout = findViewById(R.id.drawerlayout);
        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerlayout.openDrawer(GravityCompat.START);
            }
        });


        NavigationView navigationview = findViewById(R.id.navigationView);
        NavController navcontroller = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupWithNavController(navigationview, navcontroller);



        number = findViewById(R.id.numero);

         telephone = getIntent().getStringExtra("telephone");
        number.setText(telephone);

    }







    public void submitEnvoi(View view){



        numRecept = findViewById(R.id.numR);
        montantE = findViewById(R.id.montantE);
        codeE = findViewById(R.id.codeSE);

        final String numR = String.valueOf(numRecept.getText());
        final int montant = Integer.parseInt(String.valueOf(montantE.getText()));
        final int code = Integer.parseInt(String.valueOf(codeE.getText()));


        if(numR.equals("") || montantE.getText().toString().equals("") || codeE.getText().toString().equals("")){
            numRecept.setError("Veuillez remplir tous les champs");
            montantE.setError("Veuillez remplir tous les champs");
            codeE.setError("Veuillez remplir tous les champs");

            return;
        }


        try {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {


                    //Starting Write and Read data with URL
                    //Creating array for parameters
                    String[] field = new String[4];
                    field[0] = "numExp";
                    field[1] = "numRec";
                    field[2] = "montant";
                    field[3] = "codes";

                    //Creating array for data
                    String[] data = new String[4];
                    data[0] = "" + number.getText().toString();
                    data[1] = "" + numR;
                    data[2] = "" + montant;
                    data[3] = "" + code;

                    PutData putData = new PutData("http://infoscampus.com/loginRegister/envoi.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
                            if (result.equals("Envoi reussi")) {
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), Acceuil.class);
                                intent.putExtra("telephone", data[0]);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "erreur: " + result, Toast.LENGTH_LONG).show();

                            }

                        }
                    }
                }
            });

        }catch(Exception e){
            Toast.makeText(this, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    public void submitRetrait(View view)
    {



        montantRetrait = findViewById(R.id.montant);
        codeRetrait = findViewById(R.id.codeS);


        final int montant = Integer.parseInt(String.valueOf(montantRetrait.getText()));
        final int code = Integer.parseInt(String.valueOf(codeRetrait.getText()));


        if( montantRetrait.getText().toString().equals("") || codeRetrait.getText().toString().equals("")){
            numero.setError("Veuillez remplir tous les champs");
            montantRetrait.setError("Veuillez remplir tous les champs");
            codeRetrait.setError("Veuillez remplir tous les champs");

            return;

        }

        try {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {


                    //Starting Write and Read data with URL
                    //Creating array for parameters
                    String[] field = new String[3];
                    field[0] = "numR";
                    field[1] = "montant";
                    field[2] = "codeS";


                    //Creating array for data
                    String[] data = new String[3];
                    data[0] = "" + number.getText().toString();
                    data[1] = "" + montant;
                    data[2] = "" + code;


                    PutData putData = new PutData("http://infoscampus.com/loginRegister/retrait.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
                            if (result.equals("Retrait reussi")) {
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), Acceuil.class);
                                intent.putExtra("telephone", data[0]);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "erreur: " + result, Toast.LENGTH_LONG).show();

                            }

                        }
                    }
                }
            });

        }catch (Exception e){
            Toast.makeText(this, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void consulterSolde(View view)
    {
        solde = findViewById(R.id.solde);
        code = findViewById(R.id.code);

        if(code.getText().toString().equals("")){
            code.setError("Entrez votre code");
        }

        else{

            //Appel du script qui recupere le solde à partir du code et du numero

            try {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[2];
                        field[0] = "numtel";
                        field[1] = "code";


                        //Creating array for data
                        String[] data = new String[2];
                        data[0] = "" + number.getText().toString();
                        data[1] = "" + code.getText();

                        PutData putData = new PutData("http://infoscampus.com/loginRegister/consulterSolde.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                if (!result.equals("Mauvais code"))
                                    solde.setText(result);

                                else code.setError("Mauvais code secret");
                            }
                        }
                    }
                });
            }catch(Exception e){
                Toast.makeText(this, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
                return;
            }
        }


    }

    public void modifCompte(View view)
    {

        nom = findViewById(R.id.Mnom);
        prenom = findViewById(R.id.Mprenom);
        date = findViewById(R.id.Mdate);
        numCni = findViewById(R.id.Mnumcni);
        pass = findViewById(R.id.Mpass);
        cpass = findViewById(R.id.Mcpass);

        if(nom.getText().toString().equals("") || prenom.getText().toString().equals("") || date.getText().toString().equals("") || numCni.getText().toString().equals("") || pass.getText().toString().equals("") || cpass.getText().toString().equals("")){

            nom.setError("Tous les champs sont requis");
            prenom.setError("Tous les champs sont requis");
            date.setError("Tous les champs sont requis");
            numCni.setError("Tous les champs sont requis");
            pass.setError("Tous les champs sont requis");
            cpass.setError("Tous les champs sont requis");
        }
        else if(!pass.getText().toString().equals(cpass.getText().toString()))
        {
            pass.setError("Entrez les memes valeurs pour ces champs");
            cpass.setError("Entrez les memes valeurs pour ces champs");
        }
        else
        {
            try {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {


                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[6];
                        field[0] = "numtel";
                        field[1] = "prenom";
                        field[2] = "nom";
                        field[3] = "dateNaiss";
                        field[4] = "numcni";
                        field[5] = "nmdp";


                        //Creating array for data
                        String[] data = new String[6];
                        data[0] = "" + number.getText().toString();
                        data[1] = "" + prenom.getText();
                        data[2] = "" + nom.getText();
                        data[3] = "" + date.getText();
                        data[4] = "" + numCni.getText();
                        data[5] = "" + pass.getText();


                        PutData putData = new PutData("http://infoscampus.com/loginRegister/modifCompte.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                if (result.equals("Modification reussi")) {
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), Acceuil.class);
                                    intent.putExtra("telephone", data[0]);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "erreur: " + result, Toast.LENGTH_LONG).show();

                                }

                            }
                        }
                    }
                });


            }catch (Exception e){
                Toast.makeText(this, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
                return;
            }
        }

    }



}
