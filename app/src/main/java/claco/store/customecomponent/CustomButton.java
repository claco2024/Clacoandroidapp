package claco.store.customecomponent;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatButton;
import android.util.AttributeSet;

import claco.store.R;


public class CustomButton extends AppCompatButton {

    private Typeface fontType;


    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (isInEditMode())
            return;
        final TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        String fontName = ta.getString(R.styleable.CustomTextView_font_name);
        if (fontName != null) {
            final Typeface font = FontCache.getInstance().getFont(context, "fonts/" + fontName);
            setTypeface(font);
        }
        ta.recycle();
    }


}