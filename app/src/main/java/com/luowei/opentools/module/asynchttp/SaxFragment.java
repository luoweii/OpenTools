package com.luowei.opentools.module.asynchttp;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.SaxAsyncHttpResponseHandler;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;

/**
 * Created by 骆巍 on 2016/5/6.
 */
public class SaxFragment extends SampleParentFragment {

    private static final String LOG_TAG = "SaxSample";
    private final SaxAsyncHttpResponseHandler saxAsyncHttpResponseHandler = new SaxAsyncHttpResponseHandler<SAXTreeStructure>(new SAXTreeStructure()) {
        @Override
        public void onStart() {
            clearOutputs();
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, SAXTreeStructure saxTreeStructure) {
            debugStatusCode(LOG_TAG, statusCode);
            debugHeaders(LOG_TAG, headers);
            debugHandler(saxTreeStructure);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, SAXTreeStructure saxTreeStructure) {
            debugStatusCode(LOG_TAG, statusCode);
            debugHeaders(LOG_TAG, headers);
            debugHandler(saxTreeStructure);
        }

        private void debugHandler(SAXTreeStructure handler) {
            for (Tuple t : handler.responseViews) {
                addView(getColoredView(t.color, t.text));
            }
        }
    };

    @Override
    public ResponseHandlerInterface getResponseHandler() {
        return saxAsyncHttpResponseHandler;
    }

    @Override
    public String getDefaultURL() {
        return "http://bin-iin.com/sitemap.xml";
    }

    @Override
    public boolean isRequestHeadersAllowed() {
        return false;
    }

    @Override
    public boolean isRequestBodyAllowed() {
        return false;
    }

    @Override
    public RequestHandle executeSample(AsyncHttpClient client, String URL, Header[] headers, HttpEntity entity, ResponseHandlerInterface responseHandler) {
        return client.get(getContext(), URL, headers, null, responseHandler);
    }

    private class Tuple {
        public final Integer color;
        public final String text;

        public Tuple(int _color, String _text) {
            this.color = _color;
            this.text = _text;
        }
    }

    private class SAXTreeStructure extends DefaultHandler {

        public final List<Tuple> responseViews = new ArrayList<Tuple>();

        public void startElement(String namespaceURI, String localName,
                                 String rawName, Attributes atts) {
            responseViews.add(new Tuple(LIGHTBLUE, "Start Element: " + rawName));
        }

        public void endElement(String namespaceURI, String localName,
                               String rawName) {
            responseViews.add(new Tuple(LIGHTBLUE, "End Element  : " + rawName));
        }

        public void characters(char[] data, int off, int length) {
            if (length > 0 && data[0] != '\n') {
                responseViews.add(new Tuple(LIGHTGREEN, "Characters  :  " + new String(data,
                        off, length)));
            }
        }
    }
}
