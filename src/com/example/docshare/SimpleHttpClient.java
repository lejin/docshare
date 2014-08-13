package com.example.docshare;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;


public class SimpleHttpClient {
 /** The time it takes for our client to timeout */
    public static final int HTTP_TIMEOUT = 30 * 1000; // milliseconds

    /** Single instance of our HttpClient */
    private static HttpClient mHttpClient;

    /**
     * Get our single instance of our HttpClient object.
     *
     * @return an HttpClient object with connection parameters set
     */
    private static HttpClient getHttpClient() {
        if (mHttpClient == null) {
            mHttpClient = new DefaultHttpClient();
            final HttpParams params = mHttpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(params, HTTP_TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, HTTP_TIMEOUT);
            ConnManagerParams.setTimeout(params, HTTP_TIMEOUT);
        }
        return mHttpClient;
    }

    /**
     * Performs an HTTP Post request to the specified url with the
     * specified parameters.
     *
     * @param url The web address to post the request to
     * @param postParameters The parameters to send via the request
     * @param filePath 
     * @return The result of the request
     * @throws Exception
     */
    public static HttpResponse executeHttpPost(String url, ArrayList<NameValuePair> postParameters, String filePath) throws Exception {
        try {
        	/*
            HttpClient client = getHttpClient();
            HttpPost request = new HttpPost(url);
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
            request.setEntity(formEntity);
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();

            String result = sb.toString();
            */
        	
        	 HttpClient client = new DefaultHttpClient(); 
             HttpPost post = new HttpPost(url); 
             MultipartEntityBuilder multipartEntity = MultipartEntityBuilder.create(); 
             multipartEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE); 
             
             
             //String pathi="/mnt/extsd/lejin/temp.jpg";
             
             
            // ContentType cbFile = new ContentType("im");
           //  multipartEntity.addPart("userfile", cbFile);
             //File file=new File("pathi");
            // FileBody fb=new FileBody(file);
            multipartEntity.addPart("file", new FileBody(new File(filePath)));
            // multipartEntity.addBinaryBody("file", file, ContentType.MULTIPART_FORM_DATA, file.getName());
             
            
             
             post.setEntity(multipartEntity.build()); 
            HttpResponse response = client.execute(post); 
            
             client.getConnectionManager().shutdown(); 
  
            return response;
        }
        finally {
           // if (in != null) {
               // try {
                    //in.close();
               // } catch (IOException e) {
               //     e.printStackTrace();
               // }
            //}
        }
    }

    /**
     * Performs an HTTP GET request to the specified url.
     *
     * @param url The web address to post the request to
     * @return The result of the request
     * @throws Exception
     */
    public static HttpResponse executeHttpGet(String url) throws Exception {
        BufferedReader in = null;
        try {
            HttpClient client = getHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(url));
            HttpResponse response = client.execute(request);
           // in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
/*
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();

            String result = sb.toString();
            return result;
            */
            //String res= response.toString();
            return response;
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}