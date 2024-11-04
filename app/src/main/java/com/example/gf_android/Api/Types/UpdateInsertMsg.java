package com.example.gf_android.Api.Types;

public class UpdateInsertMsg {

    public String msg;
    public int rowsAffected;

    public UpdateInsertMsg(String s, int n) {
        msg = s;
        rowsAffected = n;
    }
}
