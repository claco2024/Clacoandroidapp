package claco.store.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import claco.store.Activities.OfferFagment;
import claco.store.fragments.CategoryOfferFrag;
import claco.store.fragments.ProductFrag;
import claco.store.fragments.PromocodeFrag;

public class CTabAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    String cid,matchid,PattiId,Reg;

    public CTabAdapter(FragmentManager fm, int NumOfTabs, String cid, String matchid, String PattiId, String Reg) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.cid = cid;
        this.matchid = matchid;
        this.PattiId = PattiId;
        this.Reg = Reg;
    }

    @Override
    public Fragment getItem(int position) {

            if (position==0) {
                OfferFagment.from= "Brand";
                return OfferFagment.addfrag(new BrandFragment(), position,"Brand" );

            } else   if (position==1) {
                OfferFagment.from= "Category";
                return OfferFagment.addfrag(new CategoryOfferFrag(), position,"Category" );
            }  else   if (position==2) {
                OfferFagment.from= "Product";
                return OfferFagment.addfrag(new ProductFrag(), position ,"Product");
            }else    {
                OfferFagment.from= "Promocode";
                return OfferFagment.addfrag(new PromocodeFrag(), position,"Promocode" );
            }

    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}