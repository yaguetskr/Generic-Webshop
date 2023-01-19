package com.rest.frontend;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class API {

    private static int backendPort=8080;
    private static String backendUrlPrefix="http://localhost:"+ backendPort + "/products/%s";


    public String getall() throws URISyntaxException, IOException, InterruptedException {
        String fullUrl=String.format(backendUrlPrefix,"getall");
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());
        return response.body();

    }

    public String get(int id) throws URISyntaxException, IOException, InterruptedException {

        String fullUrl=String.format(backendUrlPrefix,"get?id="+Integer.toString(id));
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());
        return response.body();

    }

    public void edit(String id,String stock,String price, String name) throws URISyntaxException, IOException, InterruptedException {

        String fullUrl=String.format(backendUrlPrefix,"edit?id="+(id)+"&stock="+(stock)+"&price="+(price)+"&name="+name);
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());


    }

    public void create(String stock,String price, String name) throws URISyntaxException, IOException, InterruptedException {

        String fullUrl=String.format(backendUrlPrefix,"create?stock="+stock+"&price="+price+"&name="+name);
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());


    }

    public void delete(int id) throws URISyntaxException, IOException, InterruptedException {

        String fullUrl=String.format(backendUrlPrefix,"delete?id="+Integer.toString(id));
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());


    }


}
