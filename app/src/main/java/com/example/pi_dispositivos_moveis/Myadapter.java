package com.example.pi_dispositivos_moveis;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pi_dispositivos_moveis.activity.MainAnuncio;
import com.example.pi_dispositivos_moveis.activity.TelaInicialActivity;

import java.util.List;

public class Myadapter extends RecyclerView.Adapter{
    TelaInicialActivity telaInicialActivity;
    List<Anuncio> anuncios;

    public Myadapter(TelaInicialActivity telaInicialActivity, List<Anuncio>anuncios) {
        this.telaInicialActivity = telaInicialActivity;
        this.anuncios = anuncios;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(telaInicialActivity);
        View v = inflater.inflate(R.layout.anuncio,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Anuncio anuncio = anuncios.get(position);

        View v = holder.itemView;

        ImageView imv = v.findViewById(R.id.imvAnuncio);
        imv.setImageBitmap(anuncio.photo);

        TextView tvTitle = v.findViewById(R.id.tvNomeAnuncio);
        tvTitle.setText(anuncio.nome);

        TextView tvEstiloAnuncio = v.findViewById(R.id.tvEstiloAnuncio);
        tvEstiloAnuncio.setText(anuncio.estilo);

        TextView tvCacheMAnunico = v.findViewById(R.id.tvCacheMAnunico);
        tvCacheMAnunico.setText(anuncio.cache);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(telaInicialActivity, MainAnuncio.class);
                i.putExtra("idanuncio", anuncio.getIdanuncio());
                telaInicialActivity.startActivity(i);
            }
        });



    }

    @Override
    public int getItemCount() {
        return this.anuncios.size();
    }
}

