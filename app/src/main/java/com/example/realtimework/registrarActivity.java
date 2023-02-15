package com.example.realtimework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.realtimework.Modelo.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class registrarActivity extends AppCompatActivity {
    private Button btnCrear,btnRegresar;
    private EditText etCorreo,etClave;
    AwesomeValidation awesomeValidation;
    FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
String correo=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        iniciarControles();
        //correo=getIntent().getStringExtra("name");
        firebaseAuth=FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.etCorreonuevo, Patterns.EMAIL_ADDRESS,R.string.invalid_mail);
        awesomeValidation.addValidation(this,R.id.etClavenuevo,".{6,}",R.string.invalid_password);
    btnCrear.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            registrarUser();
        }
    });
    btnRegresar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
    });
    }
//Conectamos el archivo xml con nuestro activity
    private void iniciarControles(){
        btnCrear=findViewById(R.id.btnRegistrarnuevo);
        btnRegresar=findViewById(R.id.btnRegresarnuevo);
        etCorreo=findViewById(R.id.etCorreonuevo);
        etClave=findViewById(R.id.etClavenuevo);
    }
    //Este metodo crea una cueta con un usuario y contraseña valido
    private void registrarUser(){
        String correo=etCorreo.getText().toString();
        String clave=etClave.getText().toString();
        if(awesomeValidation.validate()){
            firebaseAuth.createUserWithEmailAndPassword(correo,clave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        //int id=(int)(Math.random()*((10000-500)+1));
                        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                        String uid=user.getUid();
                        String correoObtain=user.getEmail();
                        writeNewUser(uid,correoObtain);
                        Toast.makeText(getApplicationContext(),"Usuario registrado con exito",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(i);
                        finish();

                    }else{
                        String errorCode=((FirebaseAuthException)task.getException()).getErrorCode();
                        dameToastdeerror(errorCode);
                    }
                }
            });
        }else{
            Toast.makeText(getApplicationContext(),"Complete los datos del usuario",Toast.LENGTH_LONG).show();
        }
    }
    //Este metodo se inicia al lograr crear una cuenta toma los datos del nuevo usuario y los guarda en la base de datos
    public void writeNewUser(String userId, String correo) {
       // String id=String.valueOf(userId);
       /* mDatabase.child("Usuarios").child("id").setValue(id);
        mDatabase.child("Usuarios").child(id).child("correo").setValue(correo);*/
        mDatabase =
                FirebaseDatabase.getInstance().getReference()
                        .child("Usuarios");

        Map<String, String> usuario = new HashMap<>();
        usuario.put("correo", correo);
        usuario.put("nombre", "");
        usuario.put("genero", "");

        mDatabase.child(userId).setValue(usuario);

    }

    private void dameToastdeerror(String error){
        switch (error) {

            case "ERROR_INVALID_CUSTOM_TOKEN":
                Toast.makeText(registrarActivity.this, "El formato del token personalizado es incorrecto. Por favor revise la documentación", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                Toast.makeText(registrarActivity.this, "El token personalizado corresponde a una audiencia diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_CREDENTIAL":
                Toast.makeText(registrarActivity.this, "La credencial de autenticación proporcionada tiene un formato incorrecto o ha caducado.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_EMAIL":
                Toast.makeText(registrarActivity.this, "La dirección de correo electrónico está mal formateada.", Toast.LENGTH_LONG).show();
                etCorreo.setError("La dirección de correo electrónico está mal formateada.");
                etCorreo.requestFocus();
                break;

            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(registrarActivity.this, "La contraseña no es válida o el usuario no tiene contraseña.", Toast.LENGTH_LONG).show();
                etClave.setError("la contraseña es incorrecta ");
                etClave.requestFocus();
                etClave.setText("");
                break;

            case "ERROR_USER_MISMATCH":
                Toast.makeText(registrarActivity.this, "Las credenciales proporcionadas no corresponden al usuario que inició sesión anteriormente..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_REQUIRES_RECENT_LOGIN":
                Toast.makeText(registrarActivity.this,"Esta operación es sensible y requiere autenticación reciente. Inicie sesión nuevamente antes de volver a intentar esta solicitud.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                Toast.makeText(registrarActivity.this, "Ya existe una cuenta con la misma dirección de correo electrónico pero diferentes credenciales de inicio de sesión. Inicie sesión con un proveedor asociado a esta dirección de correo electrónico.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(registrarActivity.this, "La dirección de correo electrónico ya está siendo utilizada por otra cuenta..   ", Toast.LENGTH_LONG).show();
                etCorreo.setError("La dirección de correo electrónico ya está siendo utilizada por otra cuenta.");
                etCorreo.requestFocus();
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                Toast.makeText(registrarActivity.this, "Esta credencial ya está asociada con una cuenta de usuario diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_DISABLED":
                Toast.makeText(registrarActivity.this, "La cuenta de usuario ha sido inhabilitada por un administrador..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                Toast.makeText(registrarActivity.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(registrarActivity.this, "No hay ningún registro de usuario que corresponda a este identificador. Es posible que se haya eliminado al usuario.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_USER_TOKEN":
                Toast.makeText(registrarActivity.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                Toast.makeText(registrarActivity.this, "Esta operación no está permitida. Debes habilitar este servicio en la consola.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(registrarActivity.this, "La contraseña proporcionada no es válida..", Toast.LENGTH_LONG).show();
                etClave.setError("La contraseña no es válida, debe tener al menos 6 caracteres");
                etClave.requestFocus();
                break;

        }

    }

}