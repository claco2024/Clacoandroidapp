

package claco.store.retrofit.ui.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import claco.store.R;
import claco.store.retrofit.presenters.ZipCodesPresenter;
import claco.store.retrofit.ui.adapter.ZipCodesAdapter;
import claco.store.retrofit.ui.model.ZipCodeData;

import java.util.List;

import javax.inject.Inject;

import claco.store.utils.AppController;


public class MainActivity extends AppCompatActivity implements CityZipsView {

    @Inject
    ZipCodesPresenter presenter;

    @Inject
    ZipCodesAdapter adapter;


    private ProgressDialog progressDialog;

    private RecyclerView rvElements;

    private EditText etCityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



        etCityName = (EditText) findViewById(R.id.et_city_name);

        rvElements = (RecyclerView) findViewById(R.id.rv_elements);

        rvElements.setLayoutManager(new LinearLayoutManager(this));

        rvElements.setAdapter(adapter);

        (findViewById(R.id.bt_make_request)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                hideKeyboard();
            //    FirebaseApp.initializeApp(MainActivity.this);



                /*FirebaseDynamicLinks.getInstance()
                        .getDynamicLink(getIntent())
                        .addOnSuccessListener(MainActivity.this, new OnSuccessListener<PendingDynamicLinkData>() {
                            @Override
                            public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {

                            }
                        })
                        .addOnFailureListener(MainActivity.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(AppController.TAG, "getDynamicLink:onFailure", e);
                            }
                        });*/
            }

        });



    }

    @Override
    public void setCityZips(List<ZipCodeData> cityZips) {

        adapter.setZipCodeDataList(cityZips);

        adapter.notifyDataSetChanged();

    }

    @Override
    public void showCityNeededErrorMessage() {

        Toast.makeText(this, "Enter City name", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showErrorInRequest() {
        Toast.makeText(this, "Error Found", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showWaitingDialog() {

        if (progressDialog == null) {

            progressDialog = new ProgressDialog( this );

            progressDialog.setIndeterminate( true );

            progressDialog.setProgressStyle( ProgressDialog.STYLE_SPINNER );

         //   progressDialog.setTitle("Loading" );

            progressDialog.setMessage("Loading" );

        }

        progressDialog.show();
    }

    @Override
    public void hideWaitingDialog() {

        if (progressDialog.isShowing()) {

            progressDialog.hide();

        }

    }



    private void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }
}
