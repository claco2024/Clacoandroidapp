package claco.store.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import claco.store.Main.DrawerActivity;
import claco.store.R;
import claco.store.adapters.CTabAdapter;
import claco.store.customecomponent.CustomButton;
import claco.store.util.CustomLoader;
import claco.store.utils.Preferences;

import java.util.ArrayList;
import java.util.HashMap;


public class OfferFagment extends Fragment {

    View view;
    RecyclerView productRecyclerView;
    GridLayoutManager mGridLayoutManager;
   // ProductAdapter proadapter;
    Preferences pref;

    CustomLoader loader;

    LinearLayout layout_empty;

    CustomButton bAddNew;


    Spinner spinnerReason;
    EditText etComment,et_search;


    //String
    public static String OrderId;
    public static String Quantity;
    public static String orderDate;
    public static String ProductTitle;
    public static String TotalPrice;
    public static String getOrderList;
    public static String Address;
    public static String Name;
    public static String MobileNo1;
    public static String ProductCode;
    public static String status;
    public static String ProductId;
    public static String Url;
    public static String PaymentMode;
    public static String size;
    public static String timeslot;
    CTabAdapter adapter;
   public  static    TabLayout tabLayout;
    ViewPager viewPager;

 public  static  String from="";
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    ArrayList<HashMap<String, String>> Ordereditemlist = new ArrayList<>();
    ArrayList<HashMap<String, String>> reasonList = new ArrayList<>();
    ArrayList<String> reasonList1 = new ArrayList<>();

    RelativeLayout rlDelivered,rlOntheWay,rlPacked,rlProcess;
    TextView tv_filter;
    String tracking_status="";

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.offer_fragment, container, false);
        productRecyclerView = view.findViewById(R.id.recyclerView);
        mGridLayoutManager = new GridLayoutManager(getActivity(), 1);
        productRecyclerView.setLayoutManager(mGridLayoutManager);
        DrawerActivity.tvHeaderText.setText(" Offers");
        DrawerActivity.ivHome.setVisibility(View.GONE);
        pref = new Preferences(getActivity());
        layout_empty = view.findViewById(R.id.layout_empty);

        DrawerActivity.iv_menu.setImageResource(R.drawable.ic_back);
        DrawerActivity.ivHome.setVisibility(View.GONE);
        DrawerActivity.iv_menu.setVisibility(View.VISIBLE);
        DrawerActivity.rl_search.setVisibility(View.GONE);




        DrawerActivity.iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerActivity.iv_menu.setVisibility(View.GONE);
                DrawerActivity.ivHome.setVisibility(View.VISIBLE);
                Intent i = new Intent(getActivity(), DrawerActivity.class);
                i.putExtra("page", "home");
                startActivity(i);
               /// getActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
            }
        });

        //Custom loader
        loader = new CustomLoader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

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
                    ///    getActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                        return true;
                    }
                }
                return false;
            }
        });



        viewPager = view.findViewById(R.id.viewPager);
        tabLayout= view.findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Brand"));
        tabLayout.addTab(tabLayout.newTab().setText("Category"));
       tabLayout.addTab(tabLayout.newTab().setText("Product"));
       tabLayout.addTab(tabLayout.newTab().setText("Promocode"));

        adapter = new CTabAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount(),"cid","matchid","3", "reg"  );
        viewPager.setAdapter(adapter);

        viewPager.setOffscreenPageLimit(1);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
                //setView("");
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //setView("");
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // setView("");
            }
        });

        return view;
    }


    public static Fragment addfrag(Fragment frag, int val, String promocode) {
        // WicketKeepers fragment = new WicketKeepers();
        Bundle args = new Bundle();
        args.putInt("someInt", val);

        frag.setArguments(args);
        return frag;
    }

    public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
      //  transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.commit();
    }



}
