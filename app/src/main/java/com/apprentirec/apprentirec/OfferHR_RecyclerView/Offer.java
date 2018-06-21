package com.apprentirec.apprentirec.OfferHR_RecyclerView;

public class Offer {

    private String id;
    private String date;
    private String duréeContrat;
    private String lieu;
    private String profilDemande;
    private String status;
    private String titre;
    private String TypeContrat;
    private String entreprise;

    public Offer(String id, String date, String duréeContrat,String entreprise, String lieu, String profilDemande, String status, String titre, String typeContrat) {
        this.id = id;
        this.date = date;
        this.duréeContrat = duréeContrat;
        this.lieu = lieu;
        this.profilDemande = profilDemande;
        this.status = status;
        this.titre = titre;
        TypeContrat = typeContrat;
        this.entreprise = entreprise;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuréeContrat() {
        return duréeContrat;
    }

    public void setDuréeContrat(String duréeContrat) {
        this.duréeContrat = duréeContrat;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getProfilDemande() {
        return profilDemande;
    }

    public void setProfilDemande(String profilDemande) {
        this.profilDemande = profilDemande;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTypeContrat() {
        return TypeContrat;
    }

    public void setTypeContrat(String typeContrat) {
        TypeContrat = typeContrat;
    }


    public String getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }
}
