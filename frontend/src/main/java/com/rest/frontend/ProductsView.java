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
import com.vaadin.flow.component.select.Select;
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
                        Notification.show("Edited succesfully");
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


        Button newproduct = new Button("Create new product");
        add(newproduct);
        newproduct.addClickListener(clickEvent -> {

            Dialog dialog = new Dialog();
            add(dialog);
            dialog.open();
            VerticalLayout layoutdialog = new VerticalLayout();
            layoutdialog.add(new H3("Create new product:"));
            layoutdialog.add("Stock:");
            TextField stocktf= new TextField();
            layoutdialog.add(stocktf);

            layoutdialog.add("Price:");
            TextField pricetf= new TextField();
            layoutdialog.add(pricetf);

            layoutdialog.add("Name:");
            TextField nametf= new TextField();
            layoutdialog.add(nametf);


            Button Guardar = new Button("Guardar");

            Guardar.addClickListener(clickEvent2 -> {

                try {
                    api.create(stocktf.getValue(),pricetf.getValue(),nametf.getValue());
                    Notification.show("Product added succesfully");
                    String temp = api.getall();
                    List<Product> templist = gson.fromJson(temp,new TypeToken<List<Product>>(){}.getType());
                    tabla.setItems(templist);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            });

            Button Cancel = new Button("Cancel");

            Cancel.addClickListener(clickEvent2 -> {
                dialog.close();
            });



            HorizontalLayout botones = new HorizontalLayout(Guardar, Cancel);

            dialog.add(layoutdialog,botones);


        });




        Button deleteproduct = new Button("Delete product");
        add(deleteproduct);
        deleteproduct.addClickListener(clickEvent -> {

            Dialog dialog = new Dialog();
            add(dialog);
            dialog.open();
            VerticalLayout layoutdialog = new VerticalLayout();
            layoutdialog.add(new H3("Delete product:"));

            Select<Product> select = new Select<>();
            select.setLabel("Product ID");
            select.setItemLabelGenerator(Product::getName);
            select.setItems(lista);
            layoutdialog.add(select);




            Button Delete = new Button("Delete");

            Delete.addClickListener(clickEvent2 -> {

                Dialog confirm=new Dialog();
                add(confirm);
                confirm.open();
                VerticalLayout layoutconfirm=new VerticalLayout();
                layoutconfirm.add(new H3("Are you sure you want to delete product "+select.getValue().getId()+" ("+select.getValue().getName()+")?"));


                Button yes=new Button("Yes");

                yes.addClickListener(yesevent -> {
                    try {
                        api.delete(select.getValue().getId());
                        Notification.show("Product deleted succesfully");
                        String temp = api.getall();
                        List<Product> templist = gson.fromJson(temp,new TypeToken<List<Product>>(){}.getType());
                        tabla.setItems(templist);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });

                Button no=new Button("No");

                no.addClickListener(noevent -> {
                    confirm.close();
                });
                layoutconfirm.add(new HorizontalLayout(yes,no));


                confirm.add(layoutconfirm);

            });

            Button Cancel = new Button("Cancel");

            Cancel.addClickListener(clickEvent2 -> {
                dialog.close();
            });



            HorizontalLayout botones = new HorizontalLayout(Delete, Cancel);

            dialog.add(layoutdialog,botones);


        });


    }

}
