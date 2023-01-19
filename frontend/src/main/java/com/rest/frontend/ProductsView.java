package com.rest.frontend;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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



        tabla.addSelectionListener(selection -> {
            Optional<Product> optional = selection.getFirstSelectedItem();
            if (optional.isPresent()) {

                Dialog dialog = new Dialog();
                add(dialog);
                dialog.open();

                int i=lista.indexOf(optional.get());

                VerticalLayout layoutdialog= new VerticalLayout();
                layoutdialog.add(new H3("Editar the product "+(optional.get().getId() ) +":"));

                layoutdialog.add("Stock:");
                TextField stocktf= new TextField();
                stocktf.setValue(Integer.toString(optional.get().getStock()));
                layoutdialog.add(stocktf);

                layoutdialog.add("Price:");
                TextField pricetf= new TextField();
                pricetf.setValue(Float.toString(optional.get().getPrice()));
                layoutdialog.add(pricetf);

                layoutdialog.add("Name:");
                TextField nametf= new TextField();
                nametf.setValue(optional.get().getName());
                layoutdialog.add(nametf);



                Button Guardar = new Button("Guardar");
                Guardar.addClickListener(clickEvent -> {

                    String temp;
                    try {
                        api.edit(Integer.toString(optional.get().getId()),stocktf.getValue(),pricetf.getValue(),nametf.getValue());
                        temp = api.getall();
                        Notification notification = Notification.show("Edited succesfully");
                        List<Product> templist = gson.fromJson(temp,new TypeToken<List<Product>>(){}.getType());
                        tabla.setItems(templist);



                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    dialog.close();

                });

                Button Cancelar = new Button("Cancelar");
                Cancelar.addClickListener(clickEvent -> {
                    dialog.close();

                });

                HorizontalLayout botones = new HorizontalLayout(Guardar, Cancelar);

                dialog.add(layoutdialog,botones);



            }
        });
    }

}
