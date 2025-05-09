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

    public Long calculateTyphoonDamage(String scenario, int baseYear, double lat, double lon, double assetValue) {
        String path = NetCDFUtils.resolveHazardPath("wind", scenario, baseYear);
        log.info("🌪️ [태풍] 시나리오: {}, 연도: {}, 좌표: ({}, {}), 자산: {}, 경로: {}", scenario, baseYear, lat, lon, assetValue, path);

        try (NetcdfFile nc = NetcdfFile.open(path)) {
            float windSpeed = readValue(nc, "sfcWind", lat, lon);
            double ratio = Math.pow(windSpeed / 70.0, 2) * 100;

            return logAndReturnDamage("태풍", windSpeed, "풍속(m/s)", ratio, assetValue);
        } catch (Exception e) {
            throw handleError("태풍", e);
        }
    }

    public Long calculateFloodDamage(String scenario, int baseYear, double lat, double lon, double assetValue) {
        String path = NetCDFUtils.resolveHazardPath("flood", scenario, baseYear);
        log.info("🌊 [홍수] 시나리오: {}, 연도: {}, 좌표: ({}, {}), 자산: {}, 경로: {}", scenario, baseYear, lat, lon, assetValue, path);

        try (NetcdfFile nc = NetcdfFile.open(path)) {
            float depth = readValue(nc, "pr", lat, lon);
            double ratio = Math.pow(depth / 3.0, 2) * 100;

            return logAndReturnDamage("홍수", depth, "침수 깊이(m)", ratio, assetValue);
        } catch (Exception e) {
            throw handleError("홍수", e);
        }
    }


    public Long calculateDroughtDamage(String scenario, int baseYear, double lat, double lon, double assetValue, double normalPrecipitation) {
        String path = NetCDFUtils.resolveHazardPath("drought", scenario, baseYear);
        log.info("🌵 [가뭄] 시나리오: {}, 연도: {}, 좌표: ({}, {}), 자산: {}, 평년 강수량: {}, 경로: {}", scenario, baseYear, lat, lon, assetValue, normalPrecipitation, path);

        try (NetcdfFile nc = NetcdfFile.open(path)) {
            float actualPrecip = readValue(nc, "pr", lat, lon);

            double ratio = Math.max(0, 1 - (actualPrecip / normalPrecipitation)) * 100;
            log.info("🌧️ 실제 강수량: {} mm", actualPrecip);
            log.info("📊 평년 강수량: {} mm", normalPrecipitation);
            return logAndReturnDamage("가뭄", actualPrecip, "실제 강수량(mm)", ratio, assetValue);
        } catch (Exception e) {
            throw handleError("가뭄", e);
        }
    }

    public Long calculateHeatwaveDamage(String scenario, int baseYear, double lat, double lon, double assetValue) {
        String path = NetCDFUtils.resolveHazardPath("heatwave", scenario, baseYear);
        log.info("🔥 [폭염] 시나리오: {}, 연도: {}, 좌표: ({}, {}), 자산: {}, 경로: {}", scenario, baseYear, lat, lon, assetValue, path);

        try (NetcdfFile nc = NetcdfFile.open(path)) {
            log.info("📦 NetCDF 파일 변수 목록:");
            for (Variable var : nc.getVariables()) {
                log.info("  - {}", var.getFullName());
            }
            float tasmaxK = readValue(nc, "tasmax", lat, lon);
            float tmaxCelsius = tasmaxK - 273.15f;
            double ratio = Math.max(0, (tmaxCelsius - 35.0) / 15.0) * 100;
            return logAndReturnDamage("폭염", tmaxCelsius, "최고기온(℃)", ratio, assetValue);

        } catch (Exception e) {
            throw handleError("폭염", e);
        }
    }

    /**
     * NetCDF 값 추출 공통 처리
     */
    private float readValue(NetcdfFile nc, String variableName, double lat, double lon) throws Exception {
        Variable var = nc.findVariable(variableName);
        if (var == null) throw new RuntimeException("변수 없음: " + variableName);

        int latIdx = NetCDFUtils.findNearestIndex(nc.findVariable("lat").read(), lat);
        int lonIdx = NetCDFUtils.findNearestIndex(nc.findVariable("lon").read(), lon);

        Array data = var.read(new int[]{0, latIdx, lonIdx}, new int[]{1, 1, 1});
        return data.getFloat(0);
    }

    /**
     * 공통 로그 및 계산
     */
    private Long logAndReturnDamage(String type, float value, String unitLabel, double ratio, double assetValue) {
        log.info("📊 {} 값: {} {}", type, String.format("%.2f", value), unitLabel);
        log.info("📉 손실률: {}%", String.format("%.2f", ratio));
        long damage = Math.round(assetValue * ratio / 100);
        log.info("💸 예상 피해액 (원): {}", damage);
        return damage;
    }

    /**
     * 공통 예외 처리
     */
    private RuntimeException handleError(String type, Exception e) {
        log.error("🚨 {} 피해 계산 실패: {}", type, e.getMessage(), e);
        return new RuntimeException(type + " 피해 계산 실패", e);
    }
}
