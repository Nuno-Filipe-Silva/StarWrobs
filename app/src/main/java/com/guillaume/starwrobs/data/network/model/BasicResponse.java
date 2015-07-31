package com.guillaume.starwrobs.data.network.model;


/**
 * Schema used to know if there are other results to fetch,
 * if (next != null)
 * do something
 */
public class BasicResponse {

    public int count;
    public String next;
    public String previous;

}
