package br.com.ufpb.ayty.cursos.android_basico.contatos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import br.com.ufpb.ayty.cursos.android_basico.contatos.util.Agenda;
import br.com.ufpb.ayty.cursos.android_basico.contatos.util.Contato;


public class AddContacts extends ActionBarActivity {

    private Agenda agenda;
    private Contato contato;
    private int position;
    private Toolbar mToolbar;
    private EditText mEditTextName, mEditTextNickName, mEditTextNumber, mEditTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contacts);
        intentGetExtras();

        agenda = Agenda.getInstance();

        mEditTextName = (EditText) findViewById(R.id.editName);
        mEditTextNickName = (EditText) findViewById(R.id.editNickName);
        mEditTextNumber = (EditText) findViewById(R.id.editPhone);
        mEditTextEmail = (EditText) findViewById(R.id.editEmail);

        toolbarActivate();

        if (position >= 0) {
            contato = agenda.getContatos().get(position);
            preencherContatosView();
        }
    }

    private void intentGetExtras() {
        Intent intent = getIntent();
        if (intent != null) {
            position = intent.getIntExtra("position", -1);
        }
    }

    private void toolbarActivate() {
        mToolbar = (Toolbar) findViewById(R.id.toobar);
        mToolbar.setTitleTextAppearance(getBaseContext(), R.style.TitleStyles2);
        setSupportActionBar(mToolbar);
        if (position >= 0) {
            getSupportActionBar().setTitle("Editar contato");
        } else {
            getSupportActionBar().setTitle("Adicionar novo contato");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_contacts, menu);
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
            Intent intent = new Intent(AddContacts.this, HomeScreen.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.check) {
            if (position >= 0) {
                editContact();
                Intent intent = new Intent(AddContacts.this, ViewContact.class);
                intent.putExtra("position", position);
                startActivity(intent);
                finish();
            } else {
                addContato();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void addContato() {
        Contato contato = new Contato(mEditTextName.getText().toString(),
                mEditTextNickName.getText().toString(), mEditTextNumber.getText().toString(),
                mEditTextEmail.getText().toString());
        agenda.addContato(contato);
        Intent intent = new Intent(AddContacts.this, HomeScreen.class);
        startActivity(intent);
        finish();

    }

    private void editContact() {
        this.contato.setNome(mEditTextName.getText().toString());
        this.contato.setSobrenome(mEditTextNickName.getText().toString());
        this.contato.setNumero(mEditTextNumber.getText().toString());
        this.contato.setEmail(mEditTextEmail.getText().toString());
    }

    private void preencherContatosView() {
        mEditTextName.setText(this.contato.getNome());
        mEditTextNickName.setText(this.contato.getSobrenome());
        mEditTextNumber.setText(this.contato.getNumero());
        mEditTextEmail.setText(this.contato.getEmail());
    }
}
