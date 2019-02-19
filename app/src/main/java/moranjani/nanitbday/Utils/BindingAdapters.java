package moranjani.nanitbday.Utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class BindingAdapters {

    @BindingAdapter({"imageViewRes"})
    public static void setImageViewResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }
}
