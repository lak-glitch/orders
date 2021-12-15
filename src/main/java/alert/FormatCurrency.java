package alert;

import java.text.NumberFormat;
import java.util.Locale;

public class FormatCurrency {
    public static Locale localeVN = new Locale("vi", "VN");
    public static NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);

    public static String formatCurrency(int currency) {
        return currencyVN.format(currency);
    }
}
