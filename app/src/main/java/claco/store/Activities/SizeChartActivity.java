package claco.store.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import claco.store.R;

import com.bumptech.glide.Glide;
import com.imagezoom.ImageAttacher;

public class SizeChartActivity extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size_chart);

        imageView=findViewById(R.id.image);

        try
        {
            Glide.with(SizeChartActivity.this).load(getIntent().getStringExtra("SizeChart")).placeholder(R.drawable.placeholder11).into(imageView);

            usingSimpleImage(imageView);
        }

        catch (Exception e)
        {
            Log.e("Exception",e.toString());
        }
    }



    public void usingSimpleImage(ImageView imageView) {
        ImageAttacher mAttacher = new ImageAttacher(imageView);
        ImageAttacher.MAX_ZOOM = 4.0f; // times the current Size
        ImageAttacher.MIN_ZOOM = 1.0f; // Half the current Size
        MatrixChangeListener mMaListener = new MatrixChangeListener();
        mAttacher.setOnMatrixChangeListener(mMaListener);
        PhotoTapListener mPhotoTap = new PhotoTapListener();
        mAttacher.setOnPhotoTapListener(mPhotoTap);
    }

    private class MatrixChangeListener implements ImageAttacher.OnMatrixChangedListener {
        @Override
        public void onMatrixChanged(RectF rect) {

        }
    }

    private class PhotoTapListener implements ImageAttacher.OnPhotoTapListener {
        @Override
        public void onPhotoTap(View view, float x, float y) {

            // slide();
        }
    }
}
