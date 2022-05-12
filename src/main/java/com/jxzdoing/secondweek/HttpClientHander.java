package com.jxzdoing.secondweek;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author:jack
 * @date:Create on 2022/5/12 22:35
 */
public class HttpClientHander {

    public static void main(String[] args) throws IOException {
        String url = "http://localhost:8801/";
        System.out.println(getResult(url));
    }

    private static String getResult(String url) throws IOException {
        final HttpClientBuilder builder = HttpClients.custom();
        try (CloseableHttpClient client = builder.build()) {
            HttpGet get = new HttpGet(url);
            try (CloseableHttpResponse response = client.execute(get)) {
                String ret = "";
                if (response.getEntity() != null) {
                    ret = EntityUtils.toString(response.getEntity());
                }
                final int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    throw new IOException(
                            String.format("Request failed, statusCode=%s and result=%s", statusCode, ret));
                }
                return ret;
            }
        }
    }
}
