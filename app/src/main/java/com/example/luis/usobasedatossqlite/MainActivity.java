package com.example.luis.usobasedatossqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ControladorBaseDeDatos controlador;
    private EditText etNombre, etApellido, etCodigo;
    private Button btnSalir,btnEliminar,btnAgregar,btnConsultar,btnActualizar,btnLimpiar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.controlador=new ControladorBaseDeDatos(this);

        // relacionar varaibles xml con java..
        this.etNombre=(EditText) findViewById(R.id.etNombre);
        this.etApellido=(EditText) findViewById(R.id.etApellido);
        this.etCodigo=(EditText) findViewById(R.id.etID);

        this.btnAgregar=(Button) findViewById(R.id.btnAdd);
        this.btnEliminar=(Button) findViewById(R.id.btnDEL);
        this.btnConsultar=(Button) findViewById(R.id.btnConsultar);
        this.btnActualizar=(Button) findViewById(R.id.btnActualizar);

        this.btnLimpiar=(Button) findViewById(R.id.btnLimpiar);
        this.btnLimpiar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                MainActivity.this.refrescarEditText();
            }
        });

        this.btnSalir=(Button) findViewById(R.id.btnSalir);
        this.btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
            }
        });
    }

    public void agregarUsuario(View view){
        try{
            HashMap datos=new HashMap();
            datos.put("id",this.etCodigo.getText().toString());
            datos.put("nombre",this.etNombre.getText().toString());
            datos.put("apellido",this.etApellido.getText().toString());
            this.controlador.addUser(datos);
            refrescarEditText();
            Toast.makeText(this,"Usuario Agregado",Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Toast.makeText(this,"Error : "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void consultarUsuario(View view){
        try{
            int id=Integer.valueOf(this.etCodigo.getText().toString());
            HashMap resultado=this.controlador.getUser(id);
            if(resultado != null){
                this.etNombre.setText( (String) resultado.get("nombre"));
                this.etApellido.setText( (String) resultado.get("apellido"));
            }else Toast.makeText(this,"NO EXISTE EL USUARIO",Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Toast.makeText(this,"ERROR: "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void eliminarUsuario(View view){
        try{
            int id=Integer.valueOf(this.etCodigo.getText().toString());
            this.controlador.deleteUser(id);
            refrescarEditText();
        }catch (Exception e){
            Toast.makeText(this,"ERROR: "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void actualizarUsuario(View view){
        try{
            HashMap datos=new HashMap();
            datos.put("id",this.etCodigo.getText().toString());
            datos.put("nombre",this.etNombre.getText().toString());
            datos.put("apellido",this.etApellido.getText().toString());
            this.controlador.updateUser(datos);
        }catch (Exception e){ Toast.makeText(this,"ERROR: "+e.getMessage(),Toast.LENGTH_SHORT).show(); }
    }

    public void refrescarEditText(){
        this.etCodigo.setText("");
        this.etNombre.setText("");
        this.etApellido.setText("");
    }
}
