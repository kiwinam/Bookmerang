package rowan.bookmerang.Global;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by CHEONMYUNG on 2016-10-12.
 */

public class TaskMethod extends AsyncTask<String, Void, String> {

    String url, sId, encodeType;

    public TaskMethod(String url, String sId, String encodeType) {
        this.url = url;
        this.sId = sId;
        this.encodeType = encodeType;
    }

    public String getUrl() {
        return url;
    }

    public String getsId() {
        return sId;
    }

    public String getEncodeType() {
        return encodeType;
    }

    String str,str2,sResult;
    @Override
    protected String doInBackground(String[] sId) {
        str2 = "";
        try {
            URL url = new URL(getUrl());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
            osw.write(getsId());
            osw.flush();
            if(conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), getEncodeType());
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                sResult = buffer.toString();

            } else {
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sResult;
    }
}
