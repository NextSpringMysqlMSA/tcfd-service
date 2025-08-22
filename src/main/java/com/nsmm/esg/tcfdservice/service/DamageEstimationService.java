package com.nsmm.esg.tcfdservice.service;

import com.nsmm.esg.tcfdservice.util.NetCDFUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ucar.ma2.Array;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

@Slf4j
@Service
@RequiredArgsConstructor
public class DamageEstimationService {

    private final NetCDFUtils netCDFUtils;

    public Long calculateTyphoonDamage(String scenario, int baseYear, double lat, double lon, double assetValue) {
        try (NetcdfFile nc = netCDFUtils.loadFromS3("wind", scenario, baseYear)) {
            float windSpeed = readValue(nc, "sfcWind", lat, lon);
            double ratio = Math.pow(windSpeed / 70.0, 2) * 100;
            return logAndReturnDamage("태풍", windSpeed, "풍속(m/s)", ratio, assetValue);
        } catch (Exception e) {
            throw handleError("태풍", e);
        }
    }

    public Long calculateFloodDamage(String scenario, int baseYear, double lat, double lon, double assetValue) {
        try (NetcdfFile nc = netCDFUtils.loadFromS3("flood", scenario, baseYear)) {
            float prRaw = readValue(nc, "pr", lat, lon);
            float estimatedDepth = prRaw * 86400f / 1000f;
            double ratio = Math.pow(estimatedDepth / 3.0, 2) * 100;
            return logAndReturnDamage("홍수", estimatedDepth, "침수 깊이(m)", ratio, assetValue);
        } catch (Exception e) {
            throw handleError("홍수", e);
        }
    }

    public Long calculateDroughtDamage(String scenario, int baseYear, double lat, double lon, double assetValue, double normalPrecipitation) {
        try (NetcdfFile nc = netCDFUtils.loadFromS3("drought", scenario, baseYear)) {
            float prRaw = readValue(nc, "pr", lat, lon);
            float actualPrecip = prRaw * 86400f;
            double ratio = Math.max(0, 1 - (actualPrecip / normalPrecipitation)) * 100;
            log.info("실제 강수량: {} mm", actualPrecip);
            log.info("평년 강수량: {} mm", normalPrecipitation);
            return logAndReturnDamage("가뭄", actualPrecip, "실제 강수량(mm)", ratio, assetValue);
        } catch (Exception e) {
            throw handleError("가뭄", e);
        }
    }

    public Long calculateHeatwaveDamage(String scenario, int baseYear, double lat, double lon, double assetValue) {
        try (NetcdfFile nc = netCDFUtils.loadFromS3("heatwave", scenario, baseYear)) {
            Variable tasmaxVar = nc.findVariable("tasmax");
            if (tasmaxVar == null) {
                throw new RuntimeException("NetCDF에 'tasmax' 변수 없음");
            }

            String units = tasmaxVar.findAttribute("units").getStringValue().toLowerCase();
            float tasmaxRaw = readValue(nc, "tasmax", lat, lon);
            float tmaxCelsius = units.contains("k") ? tasmaxRaw - 273.15f : tasmaxRaw;
            double ratio = Math.max(0, (tmaxCelsius - 35.0) / 15.0) * 100;
            return logAndReturnDamage("폭염", tmaxCelsius, "최고기온(℃)", ratio, assetValue);
        } catch (Exception e) {
            throw handleError("폭염", e);
        }
    }

    private float readValue(NetcdfFile nc, String variableName, double lat, double lon) throws Exception {
        Variable var = nc.findVariable(variableName);
        if (var == null) throw new RuntimeException("변수 없음: " + variableName);
        int latIdx = NetCDFUtils.findNearestIndex(nc.findVariable("lat").read(), lat);
        int lonIdx = NetCDFUtils.findNearestIndex(nc.findVariable("lon").read(), lon);
        Array data = var.read(new int[]{0, latIdx, lonIdx}, new int[]{1, 1, 1});
        return data.getFloat(0);
    }

    private Long logAndReturnDamage(String type, float value, String unitLabel, double ratio, double assetValue) {
        log.info("{} 값: {} {}", type, String.format("%.2f", value), unitLabel);
        log.info("손실률: {}%", String.format("%.2f", ratio));
        long damage = Math.round(assetValue * ratio / 100);
        log.info("예상 피해액 (원): {}", damage);
        return damage;
    }

    private RuntimeException handleError(String type, Exception e) {
        log.error("{} 피해 계산 실패: {}", type, e.getMessage(), e);
        return new RuntimeException(type + " 피해 계산 실패", e);
    }
}