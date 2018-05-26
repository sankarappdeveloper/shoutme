package org.shoutme.status;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static android.support.v7.widget.RecyclerView.*;

/**
 * Created by User on 1/3/2018.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Viewholder>{
    private ArrayList<Post> android;
    private Context context;
    private byte[] byteArray;
    public CustomAdapter(Context context, ArrayList<Post> calendarList) {
        this.context=context;
        this.android=calendarList;
    }
    @Override
    public CustomAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list, parent, false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(final CustomAdapter.Viewholder holder, int position) {

        final Post post=android.get(position);
        holder.tv_android.setText(post.getMessage());

        Glide.with(context).load(post.getImage()).asBitmap().into(holder.img);

//        holder.ll.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TextView tv=(TextView)v.findViewById(R.id.title);
//                BottomSheetDialogFragment bottomSheetDialogFragment = new Comment();
//                Toast.makeText(context,tv.getText().toString(),Toast.LENGTH_SHORT).show();
//                FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
//                bottomSheetDialogFragment.show(ft,bottomSheetDialogFragment.getTag());
//            }
//        });

        holder.share.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri bmpUri = getLocalBitmapUri(holder.img);
                if (bmpUri != null) {
                    // Construct a ShareIntent with link to image
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                    shareIntent.setType("image/*");
                    // Launch sharing dialog for image
                    context.startActivity(Intent.createChooser(shareIntent, "Share Image"));
                } else {
                    // ...sharing failed, handle error
                }

//                Intent share = new Intent(Intent.ACTION_SEND);
//                share.setType("image/jpeg");
//                share.putExtra(Intent.EXTRA_STREAM, Uri.parse(post.getImage()));
//                context.startActivity(Intent.createChooser(share, "Share Image"));
            }
        });
    }

    // Returns the URI path to the Bitmap displayed in specified ImageView
    public Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            // Use methods on Context to access package-specific directories on external storage.
            // This way, you don't need to request external read/write permission.
            // See https://youtu.be/5xVh-7ywKpE?t=25m25s
            File file =  new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            // **Warning:** This will fail for API >= 24, use a FileProvider as shown below instead.
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    // Method when launching drawable within Glide
//    public Uri getBitmapFromDrawable(Bitmap bmp) {
//
//        // Store image to default external storage directory
//        Uri bmpUri = null;
//        try {
//            // Use methods on Context to access package-specific directories on external storage.
//            // This way, you don't need to request external read/write permission.
//            // See https://youtu.be/5xVh-7ywKpE?t=25m25s
//            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
//            FileOutputStream out = new FileOutputStream(file);
//            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
//            out.close();
//
//            // wrap File object into a content provider. NOTE: authority here should match authority in manifest declaration
//            bmpUri = FileProvider.getUriForFile(BookDetailActivity.this, "com.codepath.fileprovider", file);  // use this version for API >= 24
//
//            // **Note:** For API < 24, you may use bmpUri = Uri.fromFile(file);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return bmpUri;
//    }
    @Override
    public int getItemCount() {
        return android.size();
    }


//    @Override
//    public void onClick(View v) {
//
//    }

    public class Viewholder extends ViewHolder {
        private TextView tv_android;
        CardView ll;
        ImageView img;
        ImageButton share;
        public Viewholder(View itemView) {
            super(itemView);
           tv_android=(TextView)itemView.findViewById(R.id.title);
            img=(ImageView) itemView.findViewById(R.id.image);
            ll=(CardView)itemView.findViewById(R.id.card);
            share=(ImageButton)itemView.findViewById(R.id.share);

//itemView.setOnClickListener(new OnClickListener() {
//    @Override
//    public void onClick(View v) {
//
//
//    }
//});
        }
    }
}
