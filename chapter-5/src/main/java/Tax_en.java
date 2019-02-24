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
        ResourceBundle rb = ResourceBundle.getBundle("Tax", Locale.US);
        System.out.println(rb.getObject("vat"));
        System.out.println(rb.getObject("corporation"));
        System.out.println(rb.getObject("message"));
    }

}
