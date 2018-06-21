package com.apprentirec.apprentirec.OfferHR_RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apprentirec.apprentirec.R;

import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<OfferHolder> {

    List<Offer> list;

    //ajouter un constructeur prenant en entrée une liste
    public OfferAdapter(List<Offer> list) {
        this.list = list;
    }

    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public OfferHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_cards,viewGroup,false);
        return new OfferHolder(view);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(OfferHolder offerHolder, int position) {
        Offer offer = list.get(position);
        offerHolder.bind(offer);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
