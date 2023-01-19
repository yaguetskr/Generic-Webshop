package com.rest.frontend;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class API {

    private static int backendPort=8080;
    private static String backendUrlPrefix="http://localhost:"+ backendPort + "/products/%s";


    public String getall() throws URISyntaxException, IOException, InterruptedException {
        String fullUrl=String.format(backendUrlPrefix,"getall");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());
        return response.body();

    }

    public String get(int id) throws URISyntaxException, IOException, InterruptedException {

        String fullUrl=String.format(backendUrlPrefix,"get?id="+Integer.toString(id));
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());
        return response.body();

    }

    public void edit(int id,int stock,float price, String name) throws URISyntaxException, IOException, InterruptedException {

        String fullUrl=String.format(backendUrlPrefix,"edit?id="+Integer.toString(id)+"&stock="+Integer.toString(stock)+"&price="+Float.toString(price)+"&name="+name);
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());


    }

    public void create(int stock,float price, String name) throws URISyntaxException, IOException, InterruptedException {

        String fullUrl=String.format(backendUrlPrefix,"create?stock="+Integer.toString(stock)+"&price="+Float.toString(price)+"&name="+name);
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());


    }

    public void delete(int id) throws URISyntaxException, IOException, InterruptedException {

        String fullUrl=String.format(backendUrlPrefix,"delete?id="+Integer.toString(id));
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());


    }


}
