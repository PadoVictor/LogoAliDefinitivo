package com.example.testandologoali;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.patinho.logoali.R;
import com.example.testandologoali.db.BancoDeDadosTeste;
import com.example.testandologoali.db.Estabelecimentos;
import com.example.testandologoali.db.Fidelidade;

import java.util.List;

/**
 * Created by dener on 01/03/2018.
 */

public class FidelidadeAdapter extends ArrayAdapter<Fidelidade> {

    public FidelidadeAdapter(Context context, List<Fidelidade> fidelidades) {
        super(context, 0, fidelidades);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.fidelidade_list_item, parent, false);
        }

        Fidelidade fidelidade = getItem(position);


        TextView mNomeDoEstabelecimento = (TextView) listItemView.findViewById(R.id.nome_estabelecimento);
        BancoDeDadosTeste.getInstance().selectEstabelecimento(fidelidade.getmIdEstabelecimento(), result -> {
            mNomeDoEstabelecimento.setText(((Estabelecimentos) result.getSingleObject()).getmNomeDoEstabelecimento());
        });

        TextView mBairroDoEstabelecimento = (TextView) listItemView.findViewById(R.id.contagem);
        int contagem = fidelidade.getmContagem();
        mBairroDoEstabelecimento.setText(contagem + " ponto" + (contagem > 1 ? "s" : ""));

        return listItemView;
    }
}
