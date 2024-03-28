package claco.store.util;


import android.app.Dialog;
import android.content.Context;

import claco.store.R;


public class CustomLoader extends Dialog {

	public CustomLoader(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CustomLoader(Context context, int theme) {
		super(context, theme);

		// TODO Auto-generated constructor stub
		setContentView(R.layout.custom_progress_view);
		//TextView tv = (TextView)findViewById(R.id.textView37);
	}

	public CustomLoader(Context context, boolean cancelable,
                        OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}

}
