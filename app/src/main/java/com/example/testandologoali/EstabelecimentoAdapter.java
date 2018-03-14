package com.example.testandologoali;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.patinho.logoali.R;
import com.example.testandologoali.db.Estabelecimentos;

import java.util.List;

public class EstabelecimentoAdapter extends ArrayAdapter<Estabelecimentos> {

    public EstabelecimentoAdapter(Context context, List<Estabelecimentos> vEstabelecimentos) {
        super(context, 0, vEstabelecimentos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Estabelecimentos mEstabelecimentoAtual = getItem(position);

        ImageView mImagemDoEstabelecimento = (ImageView) listItemView.findViewById(R.id.imagem_estabelecimento_list_item);
        mImagemDoEstabelecimento.setImageBitmap(mEstabelecimentoAtual.getmImagemEstabelecimentoThumb(this.getContext()));

        TextView mNomeDoEstabelecimento = (TextView) listItemView.findViewById(R.id.Nome);
        mNomeDoEstabelecimento.setText(mEstabelecimentoAtual.getmNomeDoEstabelecimento());

        //TextView mTelefoneDoEstabelecimento = (TextView) listItemView.findViewById(R.id.Telefone);
        //mTelefoneDoEstabelecimento.setText(mEstabelecimentoAtual.getmTelefoneDoEstabelecimento());

        TextView mBairroDoEstabelecimento = (TextView) listItemView.findViewById(R.id.Bairro);
        mBairroDoEstabelecimento.setText(mEstabelecimentoAtual.getmBairroDoEstabelecimento());

        TextView mCidadeDoEstabelecimento = (TextView) listItemView.findViewById(R.id.Cidade);
        mCidadeDoEstabelecimento.setText(mEstabelecimentoAtual.getmCidadeDoEstabelecimento());

        return listItemView;
    }
}
