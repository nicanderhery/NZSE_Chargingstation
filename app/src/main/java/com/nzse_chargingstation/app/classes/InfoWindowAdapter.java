package com.nzse_chargingstation.app.classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.nzse_chargingstation.app.R;

public class InfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final Context mContext;

    public InfoWindowAdapter(Context context) {
        this.mContext = context.getApplicationContext();
    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View v =  inflater.inflate(R.layout.custom_info_window, null);

        ChargingStation myCS = ContainerAndGlobal.searchChargingStation(marker.getPosition());
        String address, distance, fastCharging;

        TextView tvChargingStationAddress = v.findViewById(R.id.textViewChargingStationAddress);
        TextView tvChargingStationDistance = v.findViewById(R.id.textViewChargingStationDistance);
        TextView tvChargingStationFastCharging = v.findViewById(R.id.textViewChargingStationFastCharging);
        ImageView imgViewChargingStation = v.findViewById(R.id.imageViewChargingStation);
        imgViewChargingStation.setImageResource(mContext.getResources().getIdentifier("ic_baseline_electrical_services_24", "drawable", mContext.getPackageName()));

        assert myCS != null;
        address = myCS.getStrasse() + ' ' + myCS.getHausnummer();
        tvChargingStationAddress.setText(address);
        if(ContainerAndGlobal.getCurrentLocation() != null)
            distance = "Distance: " + ContainerAndGlobal.df.format(ContainerAndGlobal.calculateLength(myCS.getLocation(), ContainerAndGlobal.getCurrentLocation())) + " KM";
        else
            distance = "Distance: Unknown distance";
        tvChargingStationDistance.setText(distance);
        if(myCS.getArtDerLadeeinrichtung().equals("Normalladeeinrichtung"))
        {
            fastCharging = "No fast charging";
            imgViewChargingStation.setColorFilter(mContext.getColor(R.color.black));
        }
        else
        {
            fastCharging = "Fast charging";
            imgViewChargingStation.setColorFilter(mContext.getColor(R.color.icon_color));
        }
        tvChargingStationFastCharging.setText(fastCharging);
        return v;
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        return null;
    }
}
