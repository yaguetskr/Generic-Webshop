package com.rest.frontend;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
@Route
@PWA(name = "My Application", shortName = "My Application")
public class MainView extends AppLayout {


    private final RouterLink tab1;


    public MainView() {

        H1 title=new H1("Inicio");
        this.tab1=new RouterLink("Productos",ProductsView.class);

        final UnorderedList list = new UnorderedList(new ListItem(tab1));
        final Nav navigation = new Nav(list);
        addToDrawer(navigation);
        setPrimarySection(Section.DRAWER);
        setDrawerOpened(false);

        final Header header = new Header(new DrawerToggle(), title);
        addToNavbar(header);








    }

    private RouterLink[] getRouterLinks() {
        return new RouterLink[] {tab1};
    }


}
