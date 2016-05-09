package com.luowei.opentools.module.asynchttp;

/**
 * Created by 骆巍 on 2016/5/9.
 */
public class GzipFragment extends JsonFragment {

    @Override
    public String getDefaultURL() {
        return PROTOCOL + "httpbin.org/gzip";
    }
}
