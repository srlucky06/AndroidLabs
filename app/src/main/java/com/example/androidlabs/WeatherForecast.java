package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class WeatherForecast extends AppCompatActivity {
    private ImageView weatherIcon;
    private TextView currentTemp, minTemp, maxTemp, uvRating;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        weatherIcon = findViewById(R.id.weatherIcon);
        currentTemp = findViewById(R.id.currentTemp);
        minTemp     = findViewById(R.id.minTemp);
        maxTemp     = findViewById(R.id.maxTemp);
        uvRating    = findViewById(R.id.uvRating);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);


        ForecastQuery request = new ForecastQuery(); //creates a background thread
        request.execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric");
    }


    private class ForecastQuery extends AsyncTask< String, Integer, String>{
        Bitmap weatherImage;
        String currTemp, minimumTemp, maximumTemp, uvRate;
        private final String weatherImageURL = "http://openweathermap.org/img/w/";

        protected String doInBackground(String... args)
        {
            try {
                //encode the string url; may need to be commented later
                // String encodeURL = URLEncoder.encode(args[0], "UTF-8");
                //create a URL object of what server to contact:
                URL url = new URL(args[0]);
                //   URL url = new URL(encodeURL);

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data:
                InputStream response = urlConnection.getInputStream();



                //From part 3: slide 19
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( response  , "UTF-8"); //response is data from the server



                //From part 3, slide 20
                String parameter = null;

                int eventType = xpp.getEventType(); //The parser is currently at START_DOCUMENT

                while(eventType != XmlPullParser.END_DOCUMENT)
                {

                    if(eventType == XmlPullParser.START_TAG)
                    {
                        //If you get here, then you are pointing at a start tag
                        if(xpp.getName().equals("temperature"))
                        {
                            //If you get here, then you are pointing to a <Weather> start tag
                            currTemp = xpp.getAttributeValue(null,    "value");
                            publishProgress(25);
                            Thread.sleep(1000);
                            minimumTemp = xpp.getAttributeValue(null, "min");
                            publishProgress(50);
                            Thread.sleep(1000);
                            maximumTemp =xpp.getAttributeValue(null,"max");
                            publishProgress(75);
                            Thread.sleep(1000);
                        }
                        else if (xpp.getName().equals("weather")){
                            String iconId =  xpp.getAttributeValue(null,    "icon");
                            String iconName = iconId+".png";

                            if (fileExistance(iconName)){
                                Log.i("ForecastQuery","Icon "+iconName+" already present in the local storage");
                                FileInputStream fis =null;
                                try{
                                    fis = openFileInput(iconName);
                                }catch (FileNotFoundException e){
                                    e.printStackTrace();
                                }
                                weatherImage = BitmapFactory.decodeStream(fis);
                            }
                            else {
                                Log.i("ForecastQuery","Icon "+iconName+" is not present in the local storage");
                                URL imageUrl = new URL(weatherImageURL+iconName);
                                HttpURLConnection imageUrlConnection = (HttpURLConnection) imageUrl.openConnection();
                                imageUrlConnection.connect();
                                int responseCode = imageUrlConnection.getResponseCode();
                                if(responseCode == 200){
                                    weatherImage= BitmapFactory.decodeStream(imageUrlConnection.getInputStream());
                                }
                                FileOutputStream fos = openFileOutput(iconName, Context.MODE_PRIVATE);
                                weatherImage.compress(Bitmap.CompressFormat.PNG,80,fos);
                                fos.flush();
                                fos.close();
                                Log.i("ForecastQuery","Icon "+iconName+" is downloaded");
                            }
                            publishProgress(100);
                        }
                    }
                    eventType = xpp.next(); //move to the next xml event and store it in a variable
                }

                // process JSON for UV code
                URL UVURL = new URL("http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");
                //open the connection
                HttpURLConnection uvUrlConnection = (HttpURLConnection) UVURL.openConnection();
                //wait for data:
                InputStream uvResponse = uvUrlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(uvResponse, "UTF-8"), 8);

                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                String result = sb.toString();
                JSONObject jsonObject = new JSONObject(result);
                uvRate = String.valueOf(jsonObject.getDouble("value"));


            }
            catch (Exception e)
            {

            }

            return "Done";
        }

        public boolean fileExistance(String fileName){
            File file = getBaseContext().getFileStreamPath(fileName);
            return file.exists();
        }

        public void onProgressUpdate(Integer... args)
        {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(args[0]);
        }

        public void onPostExecute(String fromDoInBackground)
        {
            //Log.i("HTTP", fromDoInBackground);
            weatherIcon.setImageBitmap(weatherImage);
            currentTemp.setText(currentTemp.getText()+ currTemp +"℃");
            minTemp.setText(minTemp.getText()+minimumTemp+"℃");
            maxTemp.setText(maxTemp.getText()+ maximumTemp+"℃");
            uvRating.setText( uvRating.getText()+uvRate);
            progressBar.setVisibility(View.INVISIBLE);


        }
    }
}


