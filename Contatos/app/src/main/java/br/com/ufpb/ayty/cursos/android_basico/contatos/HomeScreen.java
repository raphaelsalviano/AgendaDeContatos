package br.com.ufpb.ayty.cursos.android_basico.contatos;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import br.com.ufpb.ayty.cursos.android_basico.contatos.util.Agenda;
import br.com.ufpb.ayty.cursos.android_basico.contatos.util.Contato;


public class HomeScreen extends ActionBarActivity {

    private Agenda agenda;
    private ImageButton mImageButton;
    private TextView mTextView;
    private View mView;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        agenda = Agenda.getInstance();

        mImageButton = (ImageButton) findViewById(R.id.addContact);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, AddContacts.class);
                startActivity(intent);
                finish();
            }
        });

        mTextView = (TextView) findViewById(R.id.nothingHere);
        mView = findViewById(R.id.fragmentListContacts);
        listView = (ListView) findViewById(R.id.listContacts);

        populateListView();
    }

    private void populateListView() {
        if (agenda.getContatos().size() > 0) {
            mTextView.setVisibility(View.GONE);
            mView.setVisibility(View.VISIBLE);

            String[] cont = new String[agenda.getContatos().size()];
            for (int i = 0; i < agenda.getContatos().size(); i++) {
                cont[i] = agenda.getContatos().get(i).getNome() + " " + agenda.getContatos().get(i).getSobrenome();
            }
            final String[] contatos = cont;
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contatos);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    for (int i = 0; i < agenda.getContatos().size(); i++) {
                        if (position == i) {
                            Intent intent = new Intent(HomeScreen.this, ViewContact.class);
                            intent.putExtra("position", i);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            });
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);

        SearchView searchView = new SearchView(this);
        searchView.setOnQueryTextListener(new SearchList());
        MenuItem searchMenu = menu.add(0, 0, 0, "");
        searchMenu.setIcon(R.drawable.ic_magnify_white);
        searchMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        searchMenu.setActionView(searchView);

        return true;
    }

    private class SearchList implements SearchView.OnQueryTextListener {

        @Override
        public boolean onQueryTextSubmit(String s) {
            searchContatosView(s);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            searchContatosView(s);
            return false;
        }
    }

    private void searchContatosView(String s){
        final Contato[] contatos = agenda.searchContact(s);
        String[] nomes = new String[contatos.length];
        for (int i = 0; i < contatos.length; i++){
            nomes[i] = contatos[i].getNome() + " " + contatos[i].getSobrenome();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nomes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < contatos.length; i++) {
                    if (position == i) {
                        Intent intent = new Intent(HomeScreen.this, ViewContact.class);
                        intent.putExtra("position", searchContatoGeral(contatos[i].getNome() + " " + contatos[i].getSobrenome()));
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

    }

    private int searchContatoGeral(String nome){
        for(int i = 0; i < agenda.getContatos().size(); i++){
            if((agenda.getContatos().get(i).getNome() + " " + agenda.getContatos().get(i).getSobrenome()).equalsIgnoreCase(nome)){
                return i;
            }
        }
        return -1;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setIcon(R.mipmap.ic_launcher).setMessage(R.string.about_text)
                    .setTitle(R.string.action_about).setPositiveButton("OK",new CaixaDeDialogo()).create().show();
        }

        return super.onOptionsItemSelected(item);
    }

    private class CaixaDeDialogo implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }
}
