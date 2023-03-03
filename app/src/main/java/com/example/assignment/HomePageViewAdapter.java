package com.example.assignment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomePageViewAdapter extends RecyclerView.Adapter<HomePageViewAdapter.ViewHolder> {

    ArrayList<Integer> listQuantity = new ArrayList<>();
    private ArrayList<Product> listProduct;
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
    public HomePageViewAdapter(Context context, ArrayList<Product> listProduct) {
        this.listProduct = listProduct;
        this.context = context;
        DB = new DatabaseHandler(context);
        for (int i = 0 ; i<listProduct.size(); i++){
            listQuantity.add(1);
        }

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.home_page_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.tvProductID.setText("ID: " + listProduct.get(position).getProductID());
        viewHolder.tvProductName.setText("Name: " + listProduct.get(position).getProductName());
        viewHolder.tvProductQuantity.setText("Quantity: " + listQuantity.get(position));
        viewHolder.tvProductPrice.setText("Price: " + listProduct.get(position).getProductPrice() + "đ");
        viewHolder.imageButton.setImageResource(listProduct.get(position).getImageDrawable());
        viewHolder.btnAddToCart.setOnClickListener(v -> {

            boolean isExist = DB.checkCart(listProduct.get(position).getProductID());
            if(isExist){
                int quantity = listQuantity.get(position) + 1;
                listQuantity.set(position,quantity);
                Toast.makeText(context, "Add to cart successfully", Toast.LENGTH_LONG).show();
            }else{
                boolean insert = DB.insertCartData
                        (listProduct.get(position).getProductID(),
                                listProduct.get(position).getProductName(),
                                listProduct.get(position).getProductPrice(),
                                listProduct.get(position).getImageDrawable());
                if(insert){
                    Toast.makeText(context, "Add to cart successfully", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listProduct.size();
    }
}
