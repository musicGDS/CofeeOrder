package com.example.android.justjava;

/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 *
 */


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.R.attr.checked;
import static android.R.attr.duration;
import static com.example.android.justjava.R.id.input_name;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String lastMessage = "" + createOrderSummary(calculatePrice(hasWhippedCream(), hasChocolate()));
        composeEmail("Cofee Java for " + getName(), lastMessage);
        //displayMessage(lastMessage);
    }

    private String createOrderSummary(int price){
        String priceMessage =
                getString(R.string.order_name, getName()) + "\n" + getString(R.string.quantityj)+ quantity +
                "\n"+ getString(R.string.add_whipped_cream) + hasWhippedCream() +
                "\n" + getString(R.string.add_chocolate) + hasChocolate() +
                "\n" + getString(R.string.total) + calculatePrice(hasWhippedCream(), hasChocolate()) +
                "\n" + getString(R.string.thank_you);
        return priceMessage;
    }
    /**
     * Calculates the price of the order.
     * @param hasCream checks if cream is added.
     * @param hasChocolate checks if chocolate is added.
     * @return total price.
     *
     */
    private int calculatePrice(boolean hasCream, boolean hasChocolate) {
        int cupPrice = 5;
        if (hasCream){
            cupPrice += 1;
        }
        if (hasChocolate) {
            cupPrice += 2;
        }
        int price = quantity * cupPrice;
        return price;
    }

    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
        } else {
            Toast toastOver = Toast.makeText(this, "You have reached max ammount", Toast.LENGTH_LONG);
            toastOver.show();
        }
        displayQantity(quantity);
    }

    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
        } else {
            Toast toastBelow = Toast.makeText(this, "You have to order at least 1 cup", Toast.LENGTH_LONG);
            toastBelow.show();
        }
        displayQantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }



    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    private boolean hasWhippedCream() {
        CheckBox creamBox = (CheckBox) findViewById(R.id.checkbox_cream);
        boolean isWithCream = creamBox.isChecked();
        Log.v("MainActivity", "Is Whipped cream: " + isWithCream);
        return isWithCream;
    }
    private boolean hasChocolate() {
        CheckBox creamBox = (CheckBox) findViewById(R.id.checkbox_chocolate);
        boolean isWithchocolate = creamBox.isChecked();
        Log.v("MainActivity", "Is Chocolate: " + isWithchocolate);
        return isWithchocolate;
    }

    private String getName() {
        EditText name = (EditText) findViewById(input_name);
        String nameLine = name.getText().toString();
        Log.v("Name problem", nameLine);
        return nameLine;
    }

    public void composeEmail(String subject, String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}

//    /**
//     * This method displays the given price on the screen.
//     * @param number
//     */
//    private void displayPrice(String number) {
//        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
//        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
//    }