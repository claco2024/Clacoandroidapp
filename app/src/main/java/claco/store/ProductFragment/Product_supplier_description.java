package claco.store.ProductFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import claco.store.Activities.MyCartActivity;
import claco.store.Main.DrawerActivity;

import claco.store.R;

public class Product_supplier_description extends Fragment implements View.OnClickListener {

    View view;
    ViewPager myviewpager;
    TabLayout tabDots;

    TextView tvAddToCart;

    int currentPage = 0;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.product_description_supplier, container, false);

        tabDots=view.findViewById(R.id.tabDots);
        tvAddToCart=view.findViewById(R.id.tvAddToCart);
        DrawerActivity.ivHome.setVisibility(View.GONE);
        myviewpager=view.findViewById(R.id.myviewpager);
        DrawerActivity.rl_search.setVisibility(View.GONE);
        DrawerActivity.tvHeaderText.setText("");
        tabDots.setupWithViewPager(myviewpager, true);
        final int NUM_PAGES = 5;
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES - 1) {
                    currentPage = 0;
                }
                myviewpager.setCurrentItem(currentPage++, true);
            }
        };


      /*  CardPagerAdapter mCardAdapter = new CardPagerAdapter(getActivity(), images) {
            @Override
            protected void onCategoryClick(View view, String str) {
                replaceFragmentWithAnimation(new GalleryFragment());

            }
        };
*/
      /*  myviewpager.setAdapter(mCardAdapter);
        myviewpager.setOffscreenPageLimit(2);
        myviewpager.setClipToPadding(false);
        myviewpager.setCurrentItem(0, true);
        myviewpager.setPageMargin(10);*/
        tvAddToCart.setOnClickListener(this);

        return view;
    }

    public void replaceFragmentWithAnimation(Fragment fragment){
        FragmentTransaction transaction=getFragmentManager().beginTransaction();
      //  transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tvAddToCart:
                Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(100);
                startActivity(new Intent(getActivity(), MyCartActivity.class));
                getActivity().overridePendingTransition(R.anim.enter_from_left,R.anim.exit_to_left);
                break;
        }
    }
}
