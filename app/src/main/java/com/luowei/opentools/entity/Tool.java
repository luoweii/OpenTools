package com.luowei.opentools.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 骆巍 on 2016/4/20.
 */
public class Tool implements Serializable {
    public String name;
    public String desc;
    public String intent;
    public String imgId;
    public String extra;
    public List<Tool> children;
    public String url;
}
