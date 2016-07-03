package nan.galeryrecyclerview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {


    private Activity _activity;
    private ArrayList<String> _filePaths = new ArrayList<String>();
    private int imageWidth;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(View v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.imgg);

        }
    }

    public RecyclerAdapter(Activity activity, ArrayList<String> filePaths,
                           int imageWidth) {
        this._activity = activity;
        this._filePaths = filePaths;
        this.imageWidth = imageWidth;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (holder.imageView.getTag() != null) {
            ((ImageGetter) holder.imageView.getTag()).cancel(true);
        }
        ImageGetter task = new ImageGetter(holder.imageView);
        task.execute(new File(_filePaths.get(position)));
        holder.imageView.setTag(task);
        holder.imageView.setOnClickListener(new OnImageClickListener(position));


    }

    private Bitmap decodeFile(File filePath) {
        try {

            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            BitmapFactory.decodeStream(new FileInputStream(filePath), null, o);

            final int REQUIRED_WIDTH = 100;
            final int REQUIRED_HIGHT = 100;
            int scale = 2;
            while (o.outWidth / scale / 2 >= REQUIRED_WIDTH
                    && o.outHeight / scale / 2 >= REQUIRED_HIGHT)
                scale *= 2;

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;

            return BitmapFactory.decodeStream(new FileInputStream(filePath), null, o2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return _filePaths.size();
    }

    class OnImageClickListener implements View.OnClickListener {

        int _postion;

        // Конструктор
        public OnImageClickListener(int position) {
            this._postion = position;
        }

        @Override
        public void onClick(View v) {
            //Запуск активности с выбранным изображением
            Intent i = new Intent(_activity, FullScreenViewActivity.class);
            i.putExtra("position", _postion);
            _activity.startActivity(i);
        }

    }

    public class ImageGetter extends AsyncTask<File, Void, Bitmap> {
        private ImageView iv;

        public ImageGetter(ImageView v) {
            iv = v;
        }

        @Override
        protected Bitmap doInBackground(File... params) {
            return decodeFile(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            iv.setImageBitmap(result);
        }
    }

}