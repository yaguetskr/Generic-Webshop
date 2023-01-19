package com.rest.frontend;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Route(value = "ProductsView",layout=MainView.class)
public class ProductsView extends VerticalLayout {

    public ProductsView() throws Exception {

        API api=new API();
        String listajson= null;
        try {
            listajson = api.getall();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Gson gson = new Gson();

        List<Product> lista = gson.fromJson(listajson,new TypeToken<List<Product>>(){}.getType());

        Grid<Product> tabla=new Grid<>();
        Grid.Column<Product> namecol =tabla.addColumn(Product::getName).setHeader("Product name:");
        Grid.Column<Product> preciocol =tabla.addColumn(Product::getPrice).setHeader("Precio:");
        Grid.Column<Product> stockcol =tabla.addColumn(Product::getStock).setHeader("Stock:");



        tabla.setItems(lista);
        add(tabla);
    }

}
