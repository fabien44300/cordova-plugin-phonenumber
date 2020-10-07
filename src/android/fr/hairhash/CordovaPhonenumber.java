/**
 */
package fr.hairhash;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.apache.cordova.PermissionHelper;
import android.util.Log;
import java.util.Date;
import android.telephony.TelephonyManager;
import android.content.Context;
import android.Manifest;

public class CordovaPhonenumber extends CordovaPlugin {

    private static final String TAG = "CordovaPhonenumber";
   TelephonyManager tm;
    String [] permissions = {
       Manifest.permission.READ_PHONE_STATE
     };

  public void initialize(CordovaInterface cordova, CordovaWebView webView) {

    super.initialize(cordova, webView);
    tm = (TelephonyManager) cordova.getActivity().getSystemService(Context.TELEPHONY_SERVICE);
    Log.d(TAG, "Initializing CordovaPhonenumber");
  }

  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException
  {

    if(action.equals("echo"))
    {
      String phrase = args.getString(0);
      final PluginResult result = new PluginResult(PluginResult.Status.OK, phrase);
      callbackContext.sendPluginResult(result);
      // Echo back the first argument
  //    Log.d(TAG, phrase);
    }
    else if(action.equals("getDate"))
    {
      // An example of returning data back to the web layer
      final PluginResult result = new PluginResult(PluginResult.Status.OK, (new Date()).toString());
      callbackContext.sendPluginResult(result);
    }
    else if(action.equals("getTelephonyService"))
    {
          try
          {
                String argument = args.getString(0);
              if(hasPermisssion())
                {
                    String data = this.getInformation(argument);
                      PluginResult result = new PluginResult(PluginResult.Status.OK, data);
                      callbackContext.sendPluginResult(result);
                      return true;
                }
              else
              {
                  PermissionHelper.requestPermissions(this, 0, permissions);
              }
                  return true;
          }
          catch(Exception e)
          {
            PluginResult result = new PluginResult(PluginResult.Status.OK, e.toString());
            callbackContext.sendPluginResult(result);
          }

    }
    return true;
  }

    // Vérification d'accès des droits android

    public boolean hasPermisssion() {
        for(String p : permissions)
        {
            if(!PermissionHelper.hasPermission(this, p))
            {
                return false;
            }
        }
        return true;
    }

    /*
     * We override this so that we can access the permissions variable, which no longer exists in
     * the parent class, since we can't initialize it reliably in the constructor!
     */

    public void requestPermissions(int requestCode)
    {
        PermissionHelper.requestPermissions(this, requestCode, permissions);
    }

    public String getInformation(String request)
    {
       String valueToReturn ;
        switch (request)
        {
            case "phoneNumber" : valueToReturn = tm.getLine1Number();
                break;
             case "IMEINumber" : valueToReturn = tm.getImei();
                break;
            case "networkCountryISO": valueToReturn = tm.getNetworkCountryIso();
                break;

              default: valueToReturn = "probleme";
        }
        return valueToReturn;

    }

}
