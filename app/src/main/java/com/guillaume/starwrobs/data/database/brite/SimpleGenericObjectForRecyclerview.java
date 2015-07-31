package com.guillaume.starwrobs.data.database.brite;

public class SimpleGenericObjectForRecyclerview {

    public String name;
    public int id;
    public int categoryId;

    public SimpleGenericObjectForRecyclerview(int id, int catId, String name) {
        this.id = id;
        this.categoryId = catId;
        this.name = name;
    }
}
