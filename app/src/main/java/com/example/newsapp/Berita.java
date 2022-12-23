package com.example.newsapp;

public class Berita {
//    public Berita(String judul, String penulis, String konten) {
//        this.judul = judul;
//        this.penulis = penulis;
//        this.konten = konten;
//    }

    private String judul;
    private String penulis;
    private String kategori;
    private String konten;
    private String key;

    public Berita(){

    }

    public Berita(String judul, String penulis, String kategori, String konten){
        this.judul = judul;
        this.penulis = penulis;
        this.kategori = kategori;
        this.konten = konten;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getKonten() {
        return konten;
    }

    public void setKonten(String konten) {
        this.konten = konten;
    }

    public void setKey(String key) {this.key = key;}

    public String getKey() {return key;}

//    String judul;
//    String penulis;
//    String konten;
}