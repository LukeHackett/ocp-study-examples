package com.github.lukehackett.ocp.chapter5.resource;

import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;

public class Tax_en extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                { "vat", 17.5D },
                { "corporation", 28.0D }
        };
    }

    public static void main(String[] args) {
        // Obtain the resource bundle
        ResourceBundle rb = ResourceBundle.getBundle("com.github.lukehackett.ocp.chapter5.resource.Tax", Locale.US);

        // Prints out the values above
        System.out.println(rb.getObject("vat"));
        System.out.println(rb.getObject("corporation"));

        // throws a MissingResourceException as it doesn't exist
        System.out.println(rb.getObject("message"));
    }

}
