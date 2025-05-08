package com.nsmm.esg.tcfdservice.service;

import com.nsmm.esg.tcfdservice.util.NetCDFUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ucar.ma2.Array;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

@Slf4j
@Service
public class DamageEstimationService {

    public double calculateTyphoonDamage(String scenario, int baseYear, double lat, double lon, double assetValue) {
        String path = NetCDFUtils.resolveWindPath(scenario, baseYear);

        try (NetcdfFile nc = NetcdfFile.open(path)) {
            Variable wind = nc.findVariable("sfcWind");
            Variable latVar = nc.findVariable("lat");
            Variable lonVar = nc.findVariable("lon");

            int latIdx = NetCDFUtils.findNearestIndex(latVar.read(), lat);
            int lonIdx = NetCDFUtils.findNearestIndex(lonVar.read(), lon);

            Array windData = wind.read(new int[]{0, latIdx, lonIdx}, new int[]{1, 1, 1});
            float windSpeed = windData.getFloat(0);

            double ratio = Math.pow(windSpeed / 70.0, 2);
            return assetValue * ratio;

        } catch (Exception e) {
            throw new RuntimeException("태풍 피해 계산 실패", e);
        }
    }
}
