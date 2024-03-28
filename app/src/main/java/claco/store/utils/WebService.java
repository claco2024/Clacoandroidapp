package claco.store.utils;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.SocketTimeoutException;

public class WebService {
    private static String NAMESPACE = "http://tempuri.org/";
    //    private static String URL = "http://fstnew.sigmasoftwares.org/webservice.asmx";
//    private static String URL = "http://kifayatistore.com/WebService.asmx";
    private static String URL = "https://www.claco.in/webservice.asmx";
    private static String SOAP_ACTION = "http://tempuri.org/";
    public static String imageURL = "https://claco.in/";

    public static String ForgotPassword(String Mobile, String webMethName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("Mobile");
        celsiusPI.setValue(Mobile);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);


        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }

        return resTxt;
    }

    public static String GetBanner(String LocationCode, String webMethName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("LocationCode");
        celsiusPI.setValue(LocationCode);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        Log.v("fhfjgfjgfjgff", request.toString());

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);


        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }

        return resTxt;
    }

    public static String getBannerbootom(String LocationCode, String webMethName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        //  PropertyInfo celsiusPI = new PropertyInfo();
//        celsiusPI.setName("LocationCode");
//        celsiusPI.setValue(LocationCode);
//        celsiusPI.setType(String.class);
//        request.addProperty(celsiusPI);

        Log.v("fhfjgfjgfjgff", request.toString());

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);


        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }

        return resTxt;
    }

    public static String GetVersion(String webMethName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }
        return resTxt;
    }

    public static String GetCat(String webMethName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);


        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }

        return resTxt;
    }

    public static String GetDeliveryType(String webMethName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }

        return resTxt;
    }

    public static String GetDeals(String webMethName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);


        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }

        return resTxt;
    }

    public static String GetState(String webMethName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);


        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }

        return resTxt;


    }

    public static String GetCity(String stateid, String webMethName) {
        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("stateid");
        celsiusPI.setValue(stateid);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }
        return resTxt;

    }

    public static String LikeUnlike(String CustomerID, String ProductID, String webMethName) {
        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("CustomerID");
        celsiusPI.setValue(CustomerID);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("ProductID");
        celsiusPI.setValue(ProductID);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }
        return resTxt;

    }


    public static String Getcategory(String MainCategory, String webMethName) {
        String resTxt = null;


        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("MainCategory");
        celsiusPI.setValue(MainCategory);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);


        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }

        return resTxt;


    }

    public static String GetSubcat(String Category, String webMethName) {
        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("Category");
        celsiusPI.setValue(Category);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }
        return resTxt;


    }

    public static String GetProduct(String subCategory, String Action, String Districtid, String Productname, String webMethName) {
        String resTxt = null;


        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("subCategory");
        celsiusPI.setValue(subCategory);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("Action");
        celsiusPI.setValue(Action);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("AreaCode");
        celsiusPI.setValue(Districtid);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("ProductName");
        celsiusPI.setValue(Productname);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        Log.v("kjkjkjkjkjkkjj", request.toString());

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);


        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }

        return resTxt;

    }

    public static String GetProductlistByfilter(String brand, String product, String FromRate, String ToRate, String Category, String webMethName) {
        String resTxt = null;


        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("brand");
        celsiusPI.setValue(brand);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("product");
        celsiusPI.setValue(product);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("FromRate");
        celsiusPI.setValue(FromRate);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("ToRate");
        celsiusPI.setValue(ToRate);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("Category");
        celsiusPI.setValue(Category);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        Log.v("GetProductlistByfilter", request.toString());

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);


        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL, 100000);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }

        return resTxt;

    }

    public static String GetProductNew(String CategoryID, String webMethName) {
        String resTxt = null;


        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("CategoryId");
        celsiusPI.setValue(CategoryID);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        Log.v("kjkjkjkjkjkkjj", request.toString());

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);


        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }

        return resTxt;

    }

    public static String GetProductSearch(String SearchText, String webMethName) {
        String resTxt = null;


        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("SearchText");
        celsiusPI.setValue(SearchText);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        Log.v("kjkjkjkjkjkkjj", request.toString());

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);


        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }

        return resTxt;

    }

    public static String GetWishList(String customerId, String webMethName) {
        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("customerId");
        celsiusPI.setValue(customerId);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);


        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }


        return resTxt;
    }

    public static String GetSearchSubcategories(String webMethName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }
        return resTxt;
    }

    public static String GetCartList(String custCode, String action, String district_id, String webMethName) {
        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("CustomerId");
        celsiusPI.setValue(custCode);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        Log.v("displayyyyy1", request.toString());

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);


        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }


        return resTxt;
    }

    public static String GetMRPWiseDeliveryCharges(String MRP, String webMethName) {
        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("MRP");
        celsiusPI.setValue(MRP);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        Log.v(" " + webMethName, request.toString());

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);


        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }


        return resTxt;
    }

    public static String Checkout(String CustomerId, String GrossPayable, String webMethName, String deliverycharges, String iscoupenapplied,
                                  String CoupenAmount, String DiscAmt, String DeliveryTo, String PayMode,
                                  String Status, String NetTotal, String StockistId, String jsonData, String CombojsonData) {
        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("CustomerId");
        celsiusPI.setValue(CustomerId);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("GrossPayable");
        celsiusPI.setValue(GrossPayable);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("deliverycharges");
        celsiusPI.setValue(deliverycharges);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("iscoupenapplied");
        celsiusPI.setValue(iscoupenapplied);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("CoupenAmount");
        celsiusPI.setValue(CoupenAmount);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("DiscAmt");
        celsiusPI.setValue(DiscAmt);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("DeliveryTo");
        celsiusPI.setValue(DeliveryTo);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("PayMode");
        celsiusPI.setValue(PayMode);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("Status");
        celsiusPI.setValue(Status);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("NetTotal");
        celsiusPI.setValue(NetTotal);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("StockistId");
        celsiusPI.setValue(StockistId);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("jsonData");
        celsiusPI.setValue(jsonData);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("CombojsonData");
        celsiusPI.setValue(CombojsonData);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        Log.v("fdhjgjgjgjgg", request.toString());

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }

        return resTxt;
    }

    public static String GetMembershiptype(String webMethName) {
        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        Log.v("fdhjgjgjgjgg", request.toString());

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }

        return resTxt;
    }


    public static String GetPincode(String PinCode, String webMethName) {
        String resTxt = null;


        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("PinCode");
        celsiusPI.setValue(PinCode);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);


        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }

        return resTxt;


    }

    public static String GetSponsor(String Sponsorid, String webMethName) {
        String resTxt = null;


        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("SpocerId");
        celsiusPI.setValue(Sponsorid);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        Log.v("displayyyyy12345", request.toString());

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);


        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }

        return resTxt;


    }


    public static String Payment(String OrderId, String TransactionId, String TransactionDate, String RecieptNo, String PaymentStatus, String TotalAmount, String PaidAmount, String DiscountAmount, String EntryDate, String DeliveryType, String CustomerCode, String PaymentDate, String Myhpayid, String CardNum, String CardType, String ProductCode, String contact, String OrderDate, String TimeSlot, String paymentMode, String webMethName) {
        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, webMethName);


        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("OrderId");
        celsiusPI.setValue(OrderId);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("TransactionId");
        celsiusPI.setValue(TransactionId);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("TransactionDate");
        celsiusPI.setValue(TransactionDate);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("RecieptNo");
        celsiusPI.setValue(RecieptNo);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("PaymentStatus");
        celsiusPI.setValue(PaymentStatus);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("TotalAmount");
        celsiusPI.setValue(TotalAmount);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("PaidAmount");
        celsiusPI.setValue(PaidAmount);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("DiscountAmount");
        celsiusPI.setValue(DiscountAmount);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("EntryDate");
        celsiusPI.setValue(EntryDate);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("DeliveryType");
        celsiusPI.setValue(DeliveryType);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("CustomerCode");
        celsiusPI.setValue(CustomerCode);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("PaymentDate");
        celsiusPI.setValue(PaymentDate);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("Myhpayid");
        celsiusPI.setValue(Myhpayid);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("CardNum");
        celsiusPI.setValue(CardNum);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("CardType");
        celsiusPI.setValue(CardType);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("ProductCode");
        celsiusPI.setValue(ProductCode);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("contact");
        celsiusPI.setValue(contact);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("paymentMode");
        celsiusPI.setValue(paymentMode);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("OrderDate");
        celsiusPI.setValue(OrderDate);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("TimeSlot");
        celsiusPI.setValue(TimeSlot);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        Log.v("fdhjgjgjgjgg", request.toString());

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }

        return resTxt;
    }

    public static String OrderList(String customerId, String FromDate, String ToDate, String webMethName) {
        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("customerId");
        celsiusPI.setValue(customerId);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        PropertyInfo celsiusPIFromDate = new PropertyInfo();
        celsiusPIFromDate.setName("FromDate");
        celsiusPIFromDate.setValue(FromDate);
        celsiusPIFromDate.setType(String.class);
        request.addProperty(celsiusPIFromDate);

        PropertyInfo celsiusPIToDate = new PropertyInfo();
        celsiusPIToDate.setName("ToDate");
        celsiusPIToDate.setValue(ToDate);
        celsiusPIToDate.setType(String.class);
        request.addProperty(celsiusPIToDate);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        Log.v(" " + webMethName, "" + request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }

        Log.v(" " + webMethName, "" + resTxt);
        return resTxt;
    }

    public static String OfferList(String customerId, String webMethName) {
        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("UserId");
        celsiusPI.setValue("");
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        Log.e("jsonobject123", customerId);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }

        return resTxt;
    }

    public static String getRefferalCustomerWise(String CustomerID, String webMethName) {
        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("CustomerID");
        celsiusPI.setValue(CustomerID);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        Log.e("jsonobject123", "customerId");

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }

        return resTxt;
    }

    public static String getofferlist(String type, String webMethName) {
        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("type");
        celsiusPI.setValue(type);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        Log.e("jsonobject123", "customerId");

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }

        return resTxt;
    }

    public static String getManufacturerlist(String type, String webMethName) {
        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("type");
        celsiusPI.setValue(type);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        Log.e("jsonobject123", "customerId");

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }

        return resTxt;
    }

    public static String getOfferList(String type, String webMethName) {
        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("type");
        celsiusPI.setValue(type);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        Log.e("jsonobject123", "customerId");

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }

        return resTxt;
    }

    public static String GetCoupon(String UserId, String webMethName) {
        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("UserId");
        celsiusPI.setValue(UserId);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }

        return resTxt;
    }

    public static String ReviewListing(String ProductCode, String webMethName) {
        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("ProductCode");
        celsiusPI.setValue(ProductCode);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }
        return resTxt;
    }

    public static String GetProductDetail(String Productid, String ProductCategory, String title, String CatId, String webMethName) {
        String resTxt = null;
        Log.e("req", "" + Productid + ", " + ProductCategory + ", " + title + ", " + CatId);


        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("Productid");
        celsiusPI.setValue(Productid);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("ProductCategory");
        celsiusPI.setValue(ProductCategory);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("title");
        celsiusPI.setValue(title);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("CatId");
        celsiusPI.setValue(CatId);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);


        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }

        return resTxt;

    }

    public static String addTocart(String CustomerID, String ProductID, String Quantity, String varId,
                                   String attrid, String colorImage, String Rateid, String colorname, String sizename, String webMethName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("CustomerID");
        celsiusPI.setValue(CustomerID);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("ProductID");
        celsiusPI.setValue(ProductID);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("Quantity");
        celsiusPI.setValue(Quantity);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("AttrId");
        celsiusPI.setValue(attrid);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("VarId");
        celsiusPI.setValue(varId);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("colorname");
        celsiusPI.setValue(colorname);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("sizename");
        celsiusPI.setValue(sizename);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        if (webMethName.equals("DeleteCart")) {

        } else {
//         celsiusPI = new PropertyInfo();
//         celsiusPI.setName("Color");
//         celsiusPI.setValue(Color);
//         celsiusPI.setType(String.class);
//         request.addProperty(celsiusPI);
//
//         celsiusPI = new PropertyInfo();
//         celsiusPI.setName("size");
//         celsiusPI.setValue(size);
//         celsiusPI.setType(String.class);
//         request.addProperty(celsiusPI);
//
//         celsiusPI = new PropertyInfo();
//         celsiusPI.setName("colorImage");
//         celsiusPI.setValue(colorImage);
//         celsiusPI.setType(String.class);
//         request.addProperty(celsiusPI);
        }


        Log.v("fjghjfffgfg", request.toString());

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }
        return resTxt;
    }

    public static String deleteTocart(String CustomerID, String cartlistid, String Quantity, String Color, String size, String colorImage, String Rateid, String webMethName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("CustomerID");
        celsiusPI.setValue(CustomerID);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("cartlistid");
        celsiusPI.setValue(cartlistid);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        Log.v("deleteTocart", request.toString());

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }
        return resTxt;
    }

    public static String UpdateCartNew(String CustomerID, String VarId, String cartlistid, String Quantity, String webMethName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("CustomerID");
        celsiusPI.setValue(CustomerID);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("ProductID");
        celsiusPI.setValue(cartlistid);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("Quantity");
        celsiusPI.setValue(Quantity);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("VarId");
        celsiusPI.setValue(VarId);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        Log.v("UpdateCartNew", request.toString());

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }
        return resTxt;
    }

    public static String addAddress(String CustomerId, String Name, String MobileNo, String PinCode, String Locality, String Address, String StateId, String CityId, String LandMark, String Alternatemobileno, String Offertype, String latitude, String longtitude, String address2, String webMethName) {
        String resTxt = null;

//:
//:
//:
//
        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("CustomerId");
        celsiusPI.setValue(CustomerId);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("Name");
        celsiusPI.setValue(Name);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("MobileNo");
        celsiusPI.setValue(MobileNo);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("PinCode");
        celsiusPI.setValue(PinCode);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("Locality");
        celsiusPI.setValue(Locality);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("Address");
        celsiusPI.setValue(Address);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("StateId");
        celsiusPI.setValue(StateId);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("CityId");
        celsiusPI.setValue(CityId);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("LandMark");
        celsiusPI.setValue(LandMark);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("Alternatemobileno");
        celsiusPI.setValue(Alternatemobileno);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("Offertype");
        celsiusPI.setValue(Offertype);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("latitude");
        celsiusPI.setValue(latitude);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("longtitude");
        celsiusPI.setValue(longtitude);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("address2");
        celsiusPI.setValue(address2);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }
        return resTxt;
    }

    public static String GetOTP(String Mobile, String webMethName) {
        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo info = new PropertyInfo();
        info.setName("Mobile");
        info.setValue(Mobile);
        info.setType(String.class);
        request.addProperty(info);


        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            // Involve web service
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }

        return resTxt;
    }

    public static String GetAddress(String customerId, String webMethName) {
        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo info = new PropertyInfo();
        info.setName("customerId");
        info.setValue(customerId);
        info.setType(String.class);
        request.addProperty(info);


        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            // Involve web service
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }

        return resTxt;
    }

    public static String UpdateQuantity(String CustomerID, String ProductID, String Quantity, String Rateid, String webMethName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("CustomerID");
        celsiusPI.setValue(CustomerID);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("ProductID");
        celsiusPI.setValue(ProductID);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("Quantity");
        celsiusPI.setValue(Quantity);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("Rateid");
        celsiusPI.setValue(Rateid);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        Log.v("dkkddkhdhddh", request.toString());


        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }
        return resTxt;
    }

    public static String UpdateAddress(String customerid, String Id, String webMethName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("customerid");
        celsiusPI.setValue(customerid);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("Id");
        celsiusPI.setValue(Id);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }
        return resTxt;
    }

    public static String ApplyCoupon(String UserId, String CoupanCode, String OrderAmount, String webMethName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("UserId");
        celsiusPI.setValue(UserId);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("CoupanCode");
        celsiusPI.setValue(CoupanCode);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("OrderAmount");
        celsiusPI.setValue(OrderAmount);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }
        return resTxt;
    }

    //    :CustomerName
//    MobileNo:
//    EmailAddress:
//    Password:
//    MemberShip:
//    PaymentMode:
//    MemberBarCode:
    public static String AddMembershiptype(String MemberShip,
                                           String PaymentMode, String
                                                   MemberBarCode, String CustomerName, String MobileNo, String EmailAddress, String Password, String webMethName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("CustomerName");
        celsiusPI.setValue(CustomerName);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("MobileNo");
        celsiusPI.setValue(MobileNo);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("EmailAddress");
        celsiusPI.setValue(EmailAddress);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("Password");
        celsiusPI.setValue(Password);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);
        // PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("MemberShip");
        celsiusPI.setValue(MemberShip);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("PaymentMode");
        celsiusPI.setValue(PaymentMode);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI.setName("MemberShip");
        celsiusPI.setValue(MemberShip);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("MemberBarCode");
        celsiusPI.setValue(MemberBarCode);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }
        return resTxt;
    }


    public static String CancelOrder(String OrderId, String ProductCode, String webMethName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("OrderId");
        celsiusPI.setValue(OrderId);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("Reason");
        celsiusPI.setValue(ProductCode);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }
        return resTxt;
    }

    public static String TrackOrder(String OrderId, String webMethName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("OrderId");
        celsiusPI.setValue(OrderId);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }
        return resTxt;
    }


    public static String ReturnOrder(String OrderId, String OrderItemId, String Comment, String ReasonId, String CustomerId, String webMethName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("OrderId");
        celsiusPI.setValue(OrderId);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("OrderItemId");
        celsiusPI.setValue(OrderItemId);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("Comment");
        celsiusPI.setValue(Comment);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("ReasonId");
        celsiusPI.setValue(ReasonId);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("CustomerId");
        celsiusPI.setValue(CustomerId);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }
        return resTxt;
    }

    public static String AddReviews(String UserId, String Ratting, String ProductId, String ReviewMessage, String webMethName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("UserId");
        celsiusPI.setValue(UserId);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("Ratting");
        celsiusPI.setValue(Ratting);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("ProductId");
        celsiusPI.setValue(ProductId);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("ReviewMessage");
        celsiusPI.setValue(ReviewMessage);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        Log.e("rev_request", "" + request);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }
        return resTxt;
    }

    public static String UpdateProfile(String customerId, String Name, String Email, String PhoneNumber, String Gender, String webMethName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("customerId");
        celsiusPI.setValue(customerId);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("Name");
        celsiusPI.setValue(Name);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("Email");
        celsiusPI.setValue(Email);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("PhoneNumber");
        celsiusPI.setValue(PhoneNumber);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI = new PropertyInfo();
        celsiusPI.setName("Gender");
        celsiusPI.setValue(Gender);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }
        return resTxt;
    }

    public static String Login(String Userid, String password, String webMethName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("Userid");
        celsiusPI.setValue(Userid);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        PropertyInfo celsiusPI1 = new PropertyInfo();
        celsiusPI1.setName("password");
        celsiusPI1.setValue(password);
        celsiusPI1.setType(String.class);
        request.addProperty(celsiusPI1);


        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }
        return resTxt;
    }

    public static String HitGetLoginOTP(String Mobile, String webMethName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("Mobile");
        celsiusPI.setValue(Mobile);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }
        return resTxt;
    }

    public static String HitGetLoginNEW(String Mobile, String webMethName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("Mobile");
        celsiusPI.setValue(Mobile);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }
        return resTxt;
    }

    public static String HitWalletPoint(String CustomerId, String webMethName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("CustomerId");
        celsiusPI.setValue(CustomerId);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }
        return resTxt;
    }


    public static String Register(/*String Name, String Email,*/String Mobile, String refer,/*,String Password,String ConfirmPassword,*/ String webMethName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo celsiusPI = new PropertyInfo();
       /* celsiusPI.setName("Name");
        celsiusPI.setValue(Name);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("Email");
        celsiusPI.setValue(Email);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);*/

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("Mobile");
        celsiusPI.setValue(Mobile);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("RefferCode");
        celsiusPI.setValue(refer);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

      /*  celsiusPI = new PropertyInfo();
        celsiusPI.setName("Password");
        celsiusPI.setValue(Password);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("ConfirmPassword");
        celsiusPI.setValue(ConfirmPassword);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);
*/

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }

        return resTxt;
    }


    public static String Changepassword(String Mobile, String oldPassword, String Password, String webMethName) {
        String resTxt = null;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        PropertyInfo celsiusPI = new PropertyInfo();
        // Set Name
        celsiusPI.setName("Mobile");
        celsiusPI.setValue(Mobile);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        PropertyInfo celsiusPI1 = new PropertyInfo();
        celsiusPI1.setName("oldPassword");
        celsiusPI1.setValue(oldPassword);
        // Set dataType
        celsiusPI1.setType(String.class);

        request.addProperty(celsiusPI1);

        PropertyInfo celsiusPI3 = new PropertyInfo();
        celsiusPI3.setName("Password");
        celsiusPI3.setValue(Password);


        celsiusPI3.setType(String.class);

        request.addProperty(celsiusPI3);


        Log.e("passwordReq", "" + request);


        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invole web service
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();

        } catch (SocketTimeoutException e) {

            e.printStackTrace();
            resTxt = "connection fault";
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "connection fault";
        }
        return resTxt;
    }


    public static String RecentlyAddedProduct(String day, String areacode, String webMethName) {
        String resTxt = null;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        PropertyInfo celsiusPI = new PropertyInfo();
        // Set Name
        celsiusPI.setName("Days");
        celsiusPI.setValue(day);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        // Set Name
        celsiusPI.setName("areacode");
        celsiusPI.setValue(areacode);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        Log.e("passwordReq", "" + request);


        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invole web service
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();

        } catch (SocketTimeoutException e) {

            e.printStackTrace();
            resTxt = "connection fault";
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "connection fault";
        }
        return resTxt;
    }

    public static String LastWeekAddedProductNew(String day, String areacode, String webMethName) {
        String resTxt = null;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);


        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invole web service
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
            Log.e("productRecyclerView", "" + resTxt);
        } catch (SocketTimeoutException e) {

            e.printStackTrace();
            resTxt = "connection fault";
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "connection fault";
        }
        return resTxt;
    }


    public static String DealofDayProduct(String areacode, String webMethName) {
        String resTxt = null;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        PropertyInfo celsiusPI = new PropertyInfo();
        // Set Name
        celsiusPI = new PropertyInfo();
        // Set Name
        celsiusPI.setName("areacode");
        celsiusPI.setValue(areacode);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        Log.e("passwordReq", "" + request);


        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invole web service
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();

        } catch (SocketTimeoutException e) {

            e.printStackTrace();
            resTxt = "connection fault";
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "connection fault";
        }
        return resTxt;
    }

    public static String GetReturnList(String webMethName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);


        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }

        return resTxt;
    }

    public static String GetDeliverySlot(String webMethName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);


        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }

        return resTxt;
    }

    public static String GetAccessToken(String webMethName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);


        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }

        return resTxt;
    }

    public static String GetDistrict(String webMethName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);


        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }

        return resTxt;
    }

    public static String RegistrationApi(String Mobile, String Email, String Name,String Password,String RefferCode, String webMethName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("Mobile");
        celsiusPI.setValue(Mobile);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("Name");
        celsiusPI.setValue(Name);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("Password");
        celsiusPI.setValue(Password);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("Email");
        celsiusPI.setValue(Email);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("RefferCode");
        celsiusPI.setValue(RefferCode);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        Log.e("RegistrationApi", request.toString());

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();
            resTxt = "error";
        }
        return resTxt;
    }
}
