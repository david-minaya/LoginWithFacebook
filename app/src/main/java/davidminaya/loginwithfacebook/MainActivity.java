package davidminaya.loginwithfacebook;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Enlaces importantes para la correcta implementacion del
 * login en Facebook y la obtencion de datos del usuarios.
 *
 * -Datos que podemos obtener del usuario:
 *  https://developers.facebook.com/docs/graph-api/reference/user/
 *
 * -Permisos necesarios para obtener los datos del usuario:
 *  https://developers.facebook.com/docs/facebook-login/permissions#reference-user_friends
 *
 * -Documentacion de la api Graph. En esta documentacion se
 *  explica como implementar el login, la obtencion de datos
 *  y muchas mas funciones:
 *  https://developers.facebook.com/docs/reference/android/current/interface/GraphRequest.GraphJSONArrayCallback/
 * */

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String a = "prueba";

        if (AccessToken.getCurrentAccessToken() == null){

            login();
        }


        IU();

    }

    //______________________________________________________________________________________________
    // MENU DE OPCIONES


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.m_actualizar:

                IU();

                break;

            case R.id.m_cerrar_sesion:

                LoginManager.getInstance().logOut();

                login();

                break;
        }

        return super.onOptionsItemSelected(item);
    }


    //______________________________________________________________________________________________
    // METODOS DE MOSTRAR DATOS EN LA IU

    public void actualizar(View view){

        IU();
    }

    public  void IU(){

        Profile perfil = Profile.getCurrentProfile();

        if (perfil != null) {

            ProfilePictureView ivFotoDePerfil = (ProfilePictureView) findViewById(R.id.iv_foto_de_perfil);
            final ProfilePictureView ivFotoDePortada = (ProfilePictureView) findViewById(R.id.iv_foto_de_portada);

            TextView tvNombre = (TextView) findViewById(R.id.tv_nombre);
            final TextView tvFechaDeNacimiento = (TextView)findViewById(R.id.tv_fecha_de_nacimiento);
            final TextView tvSexo = (TextView)findViewById(R.id.tv_sexo);
            final TextView tvLocalidad = (TextView)findViewById(R.id.tv_localidad);
            final TextView tvZonaHoraria = (TextView)findViewById(R.id.tv_zona_horaria);
            final TextView tvIdioma = (TextView)findViewById(R.id.tv_idioma);
            final TextView tvEmail = (TextView)findViewById(R.id.tv_email);

            Button bAbrirFacebook = (Button)findViewById(R.id.b_abrir_facebook);


            String fotoDePerfil = perfil.getId();
            String nombre = perfil.getName();
            final Uri link = perfil.getLinkUri();


            ivFotoDePerfil.setProfileId(fotoDePerfil);
            tvNombre.setText(nombre);


            GraphRequest request = GraphRequest.newMeRequest( AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {

                            try {

                                String fotoDePortada, fechaDeNacimiento, sexo,
                                       localidad, zonaHoraria, idioma, email;

                                fotoDePortada = object.getJSONObject("cover").getString("id");
                                fechaDeNacimiento = object.getString("birthday");
                                sexo = object.getString("gender");
                                localidad = object.getString("locale");
                                zonaHoraria = object.getString("timezone");
                                idioma = "Español";
                                email = object.getString("email");

                                ivFotoDePortada.setProfileId(fotoDePortada);
                                tvFechaDeNacimiento.setText(fechaDeNacimiento);
                                tvSexo.setText( sexo.equals("male") ? "Masculino" : "Femenino" );
                                tvLocalidad.setText(localidad);
                                tvZonaHoraria.setText("(UTC"+zonaHoraria+")");
                                tvIdioma.setText(idioma);
                                tvEmail.setText(email);

                            } catch (JSONException e) {

                                e.printStackTrace();
                                Log.i("Datos obtenidos", "ERROR :( | No se pudieron obtener los datos");

                            } catch (NullPointerException e){

                                e.printStackTrace();
                                Log.i("Error", "Error al obtener los datos");
                            }

                        }
                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "cover, birthday, locale, timezone, email, gender");
            request.setParameters(parameters);
            request.executeAsync();


            bAbrirFacebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent link_intent = new Intent(Intent.ACTION_VIEW, link);
                    startActivity(link_intent);

                }
            });

        }

    }

    public void login () {

        Intent intent_Login = new Intent(this, Login.class);
        intent_Login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent_Login);

    }
}
