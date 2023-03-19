package com.example.assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private ArrayList<Product> listProduct;
    private ArrayList<Integer> listQuantity;
    DatabaseHandler DB;
    Context context;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvProductID, tvProductName, tvQuantity, tvProductPrice;
        private final ImageButton imageButton;
        private final ImageView ivPlus, ivMinus;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            tvProductID = (TextView) view.findViewById(R.id.tvProductID);
            tvProductName = (TextView) view.findViewById(R.id.tvProductName);
            tvQuantity = (TextView) view.findViewById(R.id.tvQuantity);
            tvProductPrice = (TextView) view.findViewById(R.id.tvProductPrice);
            imageButton = (ImageButton) view.findViewById(R.id.imageButton);
            ivPlus = view.findViewById(R.id.ivPlus);
            ivMinus = view.findViewById(R.id.ivMinus);

        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param listProduct String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public CartAdapter(Context context, ArrayList<Product> listProduct, ArrayList<Integer> listQuantity) {
        this.listProduct = listProduct;
        this.context = context;
        DB = new DatabaseHandler(context);

        this.listQuantity = listQuantity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cart_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        double price = Double.parseDouble(listProduct.get(position).getProductPrice());

        viewHolder.tvProductID.setText("ID: " + listProduct.get(position).getProductID());
        viewHolder.tvProductName.setText("Name: " + listProduct.get(position).getProductName());
        viewHolder.tvProductPrice.setText("Price: " + (price * listQuantity.get(position)) + "đ");
        viewHolder.imageButton.setImageResource(listProduct.get(position).getImageDrawable());

        viewHolder.tvQuantity.setText(String.valueOf(listQuantity.get(position)));
        viewHolder.ivPlus.setOnClickListener(v -> {
            int quantity = listQuantity.get(position);
            quantity += 1;
            listQuantity.set(position, quantity);
            notifyItemChanged(position);
        });
        viewHolder.ivMinus.setOnClickListener(v -> {
            int quantity = listQuantity.get(position);
            if (quantity == 1) {
                return;
            }
            quantity -= 1;
            listQuantity.set(position, quantity);
            notifyItemChanged(position);
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listProduct.size();
    }
}
