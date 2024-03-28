package claco.store.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import claco.store.Main.DrawerActivity;
import claco.store.R;
import claco.store.util.CustomLoader;
import claco.store.utils.AppSettings;
import claco.store.utils.Preferences;
import claco.store.utils.Utils;
import claco.store.utils.WebService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;


public class MyEnaamFragment extends Fragment {

    View view;

    GridLayoutManager mGridLayoutManager;
    RecyclerView recyclerView;

    CustomLoader loader;
    TextView tv_points;
    String mainCatId;
    Preferences preferences;
    public static JSONObject jsoncategory;

    //Arraylist
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.enaam_frag, container, false);

        //custom loader
        loader = new CustomLoader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        preferences = new Preferences(getActivity());
        DrawerActivity.ivHome.setVisibility(View.GONE);
        tv_points = view.findViewById(R.id.tv_points);

        DrawerActivity.tvHeaderText.setText(getActivity().getResources().getString(R.string.enaam));
        DrawerActivity.rl_search.setVisibility(View.GONE);

        //Back
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        Intent mIntent = new Intent(getActivity(), DrawerActivity.class);
                        mIntent.putExtra("page", "home");
                        startActivity(mIntent);
                        //   getActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                        return true;
                    }
                }
                return false;
            }
        });


        if (Utils.isNetworkConnectedMainThred(getActivity())) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(true);
            new HitWalletPoint().execute();

        } else {
            Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
        }


        return view;
    }


    public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        // transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.commit();
    }

    //***********************************API****************************************************//
    public class HitWalletPoint extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.HitWalletPoint(preferences.get(AppSettings.CustomerID), "WalletPoint");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            loader.dismiss();
            Log.e("WalletPoint", displayText);

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject obj = new JSONObject(displayText);
                    if (obj.getBoolean("Status")) {
                        String WalletPoint = obj.getString("WalletPoint");
                        preferences.set(AppSettings.walletpoint, "" + /*jsonArray.length()*/WalletPoint);
                        preferences.commit();
                        DrawerActivity.tv_points.setText("\u20B9 " + preferences.get(AppSettings.walletpoint));

                        tv_points.setText("Your points \u20B9 " + preferences.get(AppSettings.walletpoint));
                    }
                } catch (Exception e) {
                    tv_points.setText("Your points \u20B9 0.0");
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }


}
