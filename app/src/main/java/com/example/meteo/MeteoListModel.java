package com.example.meteo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeteoListModel extends ArrayAdapter<MeteoItem> {
    private List<MeteoItem> listItems   ;
    private int resource ;
    public  static Map<String , Integer > images = new HashMap<>() ;
    static  {
        images.put("Clear",R.drawable.logo);
        images.put("Clouds",R.drawable.cloud);
        images.put("Rain", R.drawable.rain);
        images.put("thunderstormspng", R.drawable.logo);
    }

    public MeteoListModel(@NonNull Context context, int resource, List<MeteoItem> data)
    {
        super(context, resource , data);

        Log.i("meteoList","...........inside constructor............");
        Log.i("meteoList","...........here images............");
        Log.i("meteoList","...........seee images............" +images);
        Log.i("meteoList","...........seee data............" +data);
        Log.i("meteoList","...........seee ressources............" +resource);
        Log.i("meteoList","...........seee context............" +context);

        Log.i("meteoList" ,"data size" +String.valueOf(data.size())) ;
        this.listItems = data ;
        this.resource = resource;
        Log.i("meteoList" ,"list size" +listItems.toString() );



    }

   @NonNull
    @Override
    public View getView (int position , @Nullable View convertView , @NonNull ViewGroup parent )
    {
        Log.i("meteoList","...........This is the possition bee............"+position);
        Log.i("meteoList", "MeteoItem at position " + position + ": " + listItems.get(position).toString());
         View listItem = convertView ;
        if ( listItem==null)
            listItem = LayoutInflater.from(getContext()).inflate(resource, parent , false) ;
        ImageView imageView=listItem.findViewById(R.id.imageLogo);
        TextView textViewTempMax=listItem.findViewById(R.id.textViewTempMax);
        TextView textViewTempMin=listItem.findViewById(R.id.textViewTempMin);
        TextView textViewPression=listItem.findViewById(R.id.textViewPression);
        TextView textViewHumidite=listItem.findViewById(R.id.textViewHumdite);
        TextView textViewDate=listItem.findViewById(R.id.textViewDate);
        String key = listItems.get(position).image ;
        Log.i("meteoList","...........This is the key bee............"+key);
        Log.i("meteoList","...........outside if ............");
        Log.i("meteoList", "Image key: " + key);
        Log.i("meteoList", "Image resource ID: " + images.get(key));
        if(key!=null) imageView.setImageResource(images.get(key));
        textViewTempMax.setText(String.valueOf(listItems.get(position).tempMax)+" °C");
        textViewTempMin.setText(String.valueOf(listItems.get(position).tempMin)+" °C");
        textViewPression.setText(String.valueOf(listItems.get(position).pression)+" hPa");
        Log.i("meteoList","...........inside getView............");

        textViewHumidite.setText(String.valueOf(listItems.get(position).humidite)+" %");
        textViewDate.setText(String.valueOf(listItems.get(position).date));




        Log.i("meteoList", "TempMax: " + textViewTempMax.getText());
        Log.i("meteoList", "TempMin: " + textViewTempMin.getText());
        Log.i("meteoList", "Pression: " + textViewPression.getText());
        Log.i("meteoList", "Humidite: " + textViewHumidite.getText());
        Log.i("meteoList", "Date: " + textViewDate.getText());
        return listItem;
    }
}


//

