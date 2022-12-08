package com.example.philipricedealership.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.philipricedealership._models.Product;
import com.example.philipricedealership.R;
import com.example.philipricedealership._models.User;
import com.example.philipricedealership._utils.DatabaseHelper;
import com.example.philipricedealership.home.Home;
import com.example.philipricedealership.home.fragment_cart;

import java.util.ArrayList;

public class cart_adapter extends ArrayAdapter<Product> {
    User currentuser;
    fragment_cart rootParent;

    public cart_adapter(Context cont, ArrayList<Product> pList, User currentUser, fragment_cart rootParent) {
        super(cont, R.layout.cart_list, pList);
        this.currentuser = currentUser;
        this.rootParent = rootParent;
    }

    public View getView(int position, @Nullable View c, @NonNull ViewGroup parent) {
        Product rice = getItem(position);
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        if (c == null) {
            c = LayoutInflater.from(getContext()).inflate(R.layout.cart_list, parent, false);
        }
        ImageView img = c.findViewById(R.id.item_pic), increase = c.findViewById(R.id.increase), decrease = c.findViewById(R.id.decrease);
        TextView name = c.findViewById(R.id.item_name), price = c.findViewById(R.id.item_price), desc = c.findViewById(R.id.item_desc), quantity = c.findViewById(R.id.quantity);
        ImageButton delete = c.findViewById(R.id.delete);

        increase.startAnimation(AnimationUtils.loadAnimation(c.getContext(), R.anim.anim_item));

        increase.setOnClickListener(JohnySinsei -> {

            ArrayList<Product> prs = currentuser.getCartItems(dbHelper);
            for (Product p : prs)
                if (p.getUid() == rice.getUid())
                    p.setQty(p.getQty() + 1);

            currentuser.setCart(currentuser.cartStringifyer(prs));
            currentuser.saveState(getContext(), dbHelper, false);
            rootParent.rerender();
        });

        decrease.setOnClickListener(JohnySinsei -> {
            ArrayList<Product> prs = currentuser.getCartItems(dbHelper);

            for (Product p : prs)
                if (p.getUid() == rice.getUid()) {
                    if (p.getQty() == 1){
                        Toast.makeText(getContext(), "1 is the minimum quantity", Toast.LENGTH_LONG).show();
                        continue;
                    }
                    p.setQty(p.getQty() - 1);
                }

            currentuser.setCart(currentuser.cartStringifyer(prs));
            currentuser.saveState(getContext(), dbHelper, false);
            rootParent.rerender();
        });

        delete.setOnClickListener(e -> {
            currentuser.removeFromCart(rice, getContext(), dbHelper);
            currentuser.saveState(getContext(), dbHelper, false);
            rootParent.rerender();
        });

        delete.setClickable(true);
        name.setText(rice.getName());
        desc.setText(rice.getDescription());
        price.setText("$" + Integer.toString((int) rice.getPrice()) + ".00");
        img.setImageResource(rice.getImgResId(this.getContext()));
        quantity.setText(rice.getQty()+"x");

        return c;
    }
}
