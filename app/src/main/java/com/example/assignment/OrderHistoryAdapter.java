package com.example.assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {

    private ArrayList<Product> listProduct;
    Context context;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvProductID, tvProductName, tvProductQuantity, tvProductPrice;
        private final ImageButton imageButton;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            tvProductID = (TextView) view.findViewById(R.id.tvProductID);
            tvProductName = (TextView) view.findViewById(R.id.tvProductName);
            tvProductQuantity = (TextView) view.findViewById(R.id.tvProductQuantity);
            tvProductPrice = (TextView) view.findViewById(R.id.tvProductPrice);
            imageButton = (ImageButton) view.findViewById(R.id.imageButton);
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param listProduct String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public OrderHistoryAdapter(Context context, ArrayList<Product> listProduct) {
        this.listProduct = listProduct;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_order_history, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        double price = Double.parseDouble(listProduct.get(position).getProductPrice());
        int quantity = Integer.parseInt(listProduct.get(position).getProductQuantity());
        double totalPrice = (price * quantity) + 15000; // ship fees

        viewHolder.tvProductName.setText(listProduct.get(position).getProductName());
        viewHolder.tvProductQuantity.setText("Quantity: " + listProduct.get(position).getProductQuantity());
        viewHolder.tvProductPrice.setText(totalPrice + "đ");
        viewHolder.imageButton.setImageResource(listProduct.get(position).getImageDrawable());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listProduct.size();
    }
}
