package com.rest.backend;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class ProductController {

    private List<Product> products;

    @GetMapping("/getall")
    public List<Product> getall(){
        return products;
    }

    @GetMapping("/get")
    public Product get(@RequestParam(value = "id",defaultValue = "-1")int id){

        for (Product prod : products){
            if(prod.getId()==(id)){
                return prod;
            }

        }

        return null;

    }

    @GetMapping("/edit")
    public void edit(@RequestParam(value = "id")int id,@RequestParam( value = "stock" )int stock,@RequestParam( value = "price")float price,@RequestParam( value = "name")  String name) throws FileNotFoundException {

        for (int i = 0; i < products.size(); i++){
            if(products.get(i).getId()==(id)){
                products.set(i,new Product(id,stock,price,name));
                this.savejson();
            }

        }


    }

    @GetMapping("/create")
    public void create(@RequestParam( value = "stock" )int stock,@RequestParam( value = "price")float price,@RequestParam( value = "name")  String name) throws FileNotFoundException {
        int tempid=0;
        if(products.size()>0){
            tempid=products.get(products.size() - 1).getId()+1;
        }

        products.add(new Product(tempid,stock,price,name));
        this.savejson();


    }


    @GetMapping("/delete")
    public void delete(@RequestParam(value = "id",defaultValue = "-1")int id) throws FileNotFoundException {

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == (id)) {
                products.remove(i);
                this.savejson();
            }
        }
    }

    @GetMapping("/loadjson")
    public void loadjson(){
        Gson gson= new Gson();
        Reader reader=null;

        try {
            reader = Files.newBufferedReader(Paths.get("products.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.products=gson.fromJson(reader,new TypeToken<List<Product>>(){}.getType());


    }


    public void savejson() throws FileNotFoundException {
        Gson gson=new Gson();
        PrintWriter writer= new PrintWriter("products.json");
        writer.print(gson.toJson(this.products));
        writer.close();vn

    }





}
