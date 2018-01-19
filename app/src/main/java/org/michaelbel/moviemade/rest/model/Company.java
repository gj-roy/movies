package org.michaelbel.moviemade.rest.model;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.moviemade.rest.TmdbObject;

import java.io.Serializable;

public class Company extends TmdbObject implements Serializable {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("description")
    public String description;

    @SerializedName("headquarters")
    public String headquarters;

    @SerializedName("logo_path")
    public String logoPath;

    @SerializedName("parent_company")
    public String parentCompany;
}