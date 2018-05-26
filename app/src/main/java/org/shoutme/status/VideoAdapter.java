package org.shoutme.status;

/**
 * Created by User on 3/7/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import android.widget.Toast;
import android.widget.VideoView;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.downloader.Progress;

import org.shoutme.status.Utils.Utils;

import static android.support.v7.widget.RecyclerView.*;

/**
 * Created by User on 1/3/2018.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.Viewholder>{
    private ArrayList<Video> android;
    private Context context;
    private byte[] byteArray;
    private static String dirPath;
    int downloadId;
    ProgressBar progressBarOne;
    TextView textViewProgressOne;
    public VideoAdapter(Context context, ArrayList<Video> calendarList) {
        this.context=context;
        this.android=calendarList;
    }
    @Override
    public VideoAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_video, parent, false);
        dirPath = Utils.getRootDirPath(context);
        PRDownloader.initialize(context);
        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                .setDatabaseEnabled(true)
                .build();
        PRDownloader.initialize(context, config);

// Setting timeout globally for the download network requests:
        PRDownloaderConfig config1 = PRDownloaderConfig.newBuilder()
                .setReadTimeout(30_000)
                .setConnectTimeout(30_000)
                .build();
        PRDownloader.initialize(context, config1);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(final VideoAdapter.Viewholder holder, int position) {

        final Video post = android.get(position);
        holder.tv_android.setText(post.getName());
        holder.img.setVideoURI(Uri.parse(post.getUrl()));
//        MediaController mediaController = new
//                MediaController(context);
//        mediaController.setAnchorView(holder.img);
//        holder.img.setMediaController(mediaController);
//        holder.img.start();

        holder.img.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                /*
                 * add media controller
                 */
                        MediaController mc = new MediaController(context);
                        holder.img.setMediaController(mc);
                /*
                 * and set its position on screen
                 */
                        mc.setAnchorView(holder.img);
                    }
                });
            }
        });
        holder.img.start();
        holder.share.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.progressbar, null);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);
                 progressBarOne = (ProgressBar) promptsView.findViewById(R.id.progress);
                textViewProgressOne=(TextView)promptsView.findViewById(R.id.text);
                alertDialogBuilder.setView(promptsView);
                final AlertDialog alertDialog = alertDialogBuilder.create();
                downloadId = PRDownloader.download(post.getUrl(), dirPath, holder.tv_android.getText().toString()+".mp4")
                        .build()
                        .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                            @Override
                            public void onStartOrResume() {

                            }
                        })
                        .setOnPauseListener(new OnPauseListener() {
                            @Override
                            public void onPause() {

                            }
                        })
                        .setOnCancelListener(new OnCancelListener() {
                            @Override
                            public void onCancel() {

                            }
                        })
                        .setOnProgressListener(new OnProgressListener() {
                            @Override
                            public void onProgress(Progress progress) {
                                long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                                progressBarOne.setProgress((int) progressPercent);
                                textViewProgressOne.setText(Utils.getProgressDisplayLine(progress.currentBytes, progress.totalBytes));
                                progressBarOne.setIndeterminate(false);
                            }
                        })
                        .start(new OnDownloadListener() {
                            @Override
                            public void onDownloadComplete() {

                                Toast.makeText(context,"Completed",Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                sharingIntent.setType("video/mp4");
                                File fileToShare = new File(dirPath+"/"+holder.tv_android.getText().toString()+".mp4");
                                Uri uri = Uri.fromFile(fileToShare);
                                sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
                                context.startActivity(Intent.createChooser(sharingIntent, "Share Video!"));
                            }

                            @Override
                            public void onError(Error error) {
                            }
                        });
                alertDialog.show();
                Toast.makeText(context, "downloading", Toast.LENGTH_SHORT).show();
            }
        });


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

//        holder.share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Uri bmpUri = getLocalBitmapUri(holder.img);
//                if (bmpUri != null) {
//                    // Construct a ShareIntent with link to image
//                    Intent shareIntent = new Intent();
//                    shareIntent.setAction(Intent.ACTION_SEND);
//                    shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
//                    shareIntent.setType("image/*");
//                    // Launch sharing dialog for image
//                    context.startActivity(Intent.createChooser(shareIntent, "Share Image"));
//                } else {
//                    // ...sharing failed, handle error
//                }
//
////                Intent share = new Intent(Intent.ACTION_SEND);
////                share.setType("image/jpeg");
////                share.putExtra(Intent.EXTRA_STREAM, Uri.parse(post.getImage()));
////                context.startActivity(Intent.createChooser(share, "Share Image"));
//            }
//        });
//    }
//
//    // Returns the URI path to the Bitmap displayed in specified ImageView
//    public Uri getLocalBitmapUri(ImageView imageView) {
//        // Extract Bitmap from ImageView drawable
//        Drawable drawable = imageView.getDrawable();
//        Bitmap bmp = null;
//        if (drawable instanceof BitmapDrawable){
//            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
//        } else {
//            return null;
//        }
//        // Store image to default external storage directory
//        Uri bmpUri = null;
//        try {
//            // Use methods on Context to access package-specific directories on external storage.
//            // This way, you don't need to request external read/write permission.
//            // See https://youtu.be/5xVh-7ywKpE?t=25m25s
//            File file =  new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
//            FileOutputStream out = new FileOutputStream(file);
//            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
//            out.close();
//            // **Warning:** This will fail for API >= 24, use a FileProvider as shown below instead.
//            bmpUri = Uri.fromFile(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return bmpUri;
//    }

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
    }
    @Override
    public int getItemCount() {
        return android.size();
    }


//    @Override
//    public void onClick(View v) {
//
//    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView tv_android;
        CardView ll;
        VideoView img;
        ImageButton share;
        public Viewholder(View itemView) {
            super(itemView);
            tv_android=(TextView)itemView.findViewById(R.id.title);
            img=(VideoView) itemView.findViewById(R.id.image);
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

    public static String getRootDirPath(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File file = ContextCompat.getExternalFilesDirs(context.getApplicationContext(),
                    null)[0];
            return file.getAbsolutePath();
        } else {
            return context.getApplicationContext().getFilesDir().getAbsolutePath();
        }
    }

}
