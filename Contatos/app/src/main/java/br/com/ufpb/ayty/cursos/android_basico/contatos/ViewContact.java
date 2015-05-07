package br.com.ufpb.ayty.cursos.android_basico.contatos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import br.com.ufpb.ayty.cursos.android_basico.contatos.util.Agenda;
import br.com.ufpb.ayty.cursos.android_basico.contatos.util.Contato;

import static br.com.ufpb.ayty.cursos.android_basico.contatos.R.color.md_white_1000;


public class ViewContact extends ActionBarActivity {

    private Agenda agenda;
    private Contato contato;
    private int position;
    private TextView mTextViewPhone;
    private TextView mTextViewMail;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_contact);

        overridePendingTransition(R.anim.push_left_in, R.anim.left_out);

        agenda = Agenda.getInstance();
        // Recupera a posição do item clicado na tela anterior
        intentGetExtras();

        // Verifica se a posição é válida, se for válida recupera o contato da Lista
        if(position >= 0){
            contato = agenda.getContatos().get(position);
        }

        toolbarView();

        mTextViewPhone = (TextView)findViewById(R.id.view_phone);
        mTextViewMail = (TextView)findViewById(R.id.view_mail);

        mTextViewPhone.setText(contato.getNumero());
        mTextViewMail.setText(contato.getEmail());

    }

    private void toolbarView(){
        mToolbar = (Toolbar)findViewById(R.id.toobarView);
        mToolbar.setTitleTextAppearance(getBaseContext(),R.style.TitleStyles);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(contato.getNome() + " " + contato.getSobrenome());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void intentGetExtras(){
        Intent intent = getIntent();
        if(intent != null){
            position = intent.getIntExtra("position", -1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            backToHome();
            return true;
        }else if(id == R.id.action_delete){
            deleteContato();
            return true;
        }else if(id == R.id.action_edit){
            editContact();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void backToHome(){
        Intent intent = new Intent(ViewContact.this, HomeScreen.class);
        startActivity(intent);
        finish();
    }

    private void editContact() {
        Intent intent = new Intent(ViewContact.this, AddContacts.class);
        intent.putExtra("position", position);
        startActivity(intent);
        finish();
    }

    private void deleteContato(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage(R.string.delete_contato).setTitle(R.string.delete).setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                agenda.removerContato(contato);
                backToHome();
            }
        });
        alertBuilder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertBuilder.create().show();
    }
}
