package com.example.assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ConfirmOrderAdapter extends RecyclerView.Adapter<ConfirmOrderAdapter.ViewHolder> {

    private ArrayList<Product> listProduct;
    private ArrayList<Integer> listQuantity;
    DatabaseHandler DB;
    Context context;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvProductID, tvProductName, tvProductQuantity, tvProductPrice;
        private final ImageButton imageButton;
        private final Button btnAddToCart;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            tvProductID = (TextView) view.findViewById(R.id.tvProductID);
            tvProductName = (TextView) view.findViewById(R.id.tvProductName);
            tvProductQuantity = (TextView) view.findViewById(R.id.tvProductQuantity);
            tvProductPrice = (TextView) view.findViewById(R.id.tvProductPrice);
            imageButton = (ImageButton) view.findViewById(R.id.imageButton);
            btnAddToCart = (Button) view.findViewById(R.id.btnAddToCart);
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param listProduct String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public ConfirmOrderAdapter(Context context, ArrayList<Product> listProduct, ArrayList<Integer> listQuantity) {
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
                .inflate(R.layout.confirm_order_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        double price = Double.parseDouble(listProduct.get(position).getProductPrice());
        double totalPrice = (price * listQuantity.get(position));

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.tvProductID.setText("ID: " + listProduct.get(position).getProductID());
        viewHolder.tvProductName.setText(listProduct.get(position).getProductName());
        viewHolder.tvProductQuantity.setText("Quantity: " + listQuantity.get(position));
        viewHolder.tvProductPrice.setText("Price: " + totalPrice + "đ");
        viewHolder.imageButton.setImageResource(listProduct.get(position).getImageDrawable());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listProduct.size();
    }
}
