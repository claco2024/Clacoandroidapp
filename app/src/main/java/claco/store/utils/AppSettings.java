package claco.store.utils;
import android.app.Activity;
public final class AppSettings extends OSettings {
    public static final String PREFS_MAIN_FILE = "PREFS_KALASH_FILE";
    public static String catname = "catname";
    public static String subcatname = "subcatname";
    public static final String CustomerID = "CustomerID";
    public static final String firstName = "firstName";
    public static final String Gender = "Gender";
    public static final String Email = "Email";
    public static final String Phone1 = "Phone1";
    public static final String District = "District";
    public static final String ReferralValue = "ReferralValue";
    public static final String DistrictName = "DistrictName";

    public static String status="0";
    public static String from="";
    public static String cartStatus="0";
    public static String count="0";

    public static String fromPage="0";
    public static String productFrom="0";
    public static String amount="0";
    public static String coupon="";
    public static String statusvendor="";
    public static String dashboard="";
    public static String walletpoint="walletpoint";
    public static String hasFreeItem="false";
    public static String hasFreeItemList="";



    public AppSettings(Activity mActivity) {
        super(mActivity);
    }
}
