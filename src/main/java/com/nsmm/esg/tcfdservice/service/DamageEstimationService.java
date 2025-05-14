package com.nsmm.esg.tcfdservice.service;

import com.nsmm.esg.tcfdservice.util.NetCDFUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ucar.ma2.Array;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

/**
 * 기후변화로 인한 물리적 리스크(자연재해) 피해액 추정 서비스
 * TCFD 리스크 관리 영역에서 태풍, 홍수, 가뭄, 폭염 등 기후변화로 인한
 * 자연재해의 물리적 피해액을 추정하는 기능을 제공
 */
@Slf4j
@Service
public class DamageEstimationService {

    /**
     * 태풍 피해액 추정 메서드
     *
     * @param scenario 기후변화 시나리오 (SSP1-2.6, SSP2-4.5, SSP5-8.5 등)
     * @param baseYear 기준 연도
     * @param lat 위도
     * @param lon 경도
     * @param assetValue 자산 가치 (원)
     * @return 추정 피해액 (원)
     */
    public Long calculateTyphoonDamage(String scenario, int baseYear, double lat, double lon, double assetValue) {
        String path = NetCDFUtils.resolveHazardPath("wind", scenario, baseYear);
        log.info("🌪️ [태풍] 시나리오: {}, 연도: {}, 좌표: ({}, {}), 자산: {}, 경로: {}", scenario, baseYear, lat, lon, assetValue, path);

        try (NetcdfFile nc = NetcdfFile.open(path)) {
            // 풍속 데이터 추출
            float windSpeed = readValue(nc, "sfcWind", lat, lon);
            // 풍속에 따른 피해율 계산 (경험식: (풍속/70)^2 * 100%)
            double ratio = Math.pow(windSpeed / 70.0, 2) * 100;
            return logAndReturnDamage("태풍", windSpeed, "풍속(m/s)", ratio, assetValue);
        } catch (Exception e) {
            throw handleError("태풍", e);
        }
    }

    /**
     * 홍수 피해액 추정 메서드
     *
     * @param scenario 기후변화 시나리오 (SSP1-2.6, SSP2-4.5, SSP5-8.5 등)
     * @param baseYear 기준 연도
     * @param lat 위도
     * @param lon 경도
     * @param assetValue 자산 가치 (원)
     * @return 추정 피해액 (원)
     */
    public Long calculateFloodDamage(String scenario, int baseYear, double lat, double lon, double assetValue) {
        String path = NetCDFUtils.resolveHazardPath("flood", scenario, baseYear);
        log.info("🌊 [홍수] 시나리오: {}, 연도: {}, 좌표: ({}, {}), 자산: {}, 경로: {}", scenario, baseYear, lat, lon, assetValue, path);

        try (NetcdfFile nc = NetcdfFile.open(path)) {
            // 강수량 데이터 추출
            float prRaw = readValue(nc, "pr", lat, lon);
            // 강수량을 침수 깊이로 변환 (kg/m²/s → mm/day → m/day)
            float estimatedDepth = prRaw * 86400f / 1000f;
            // 침수 깊이에 따른 피해율 계산 (경험식: (침수깊이/3)^2 * 100%)
            double ratio = Math.pow(estimatedDepth / 3.0, 2) * 100;
            return logAndReturnDamage("홍수", estimatedDepth, "침수 깊이(m)", ratio, assetValue);
        } catch (Exception e) {
            throw handleError("홍수", e);
        }
    }

    /**
     * 가뭄 피해액 추정 메서드
     *
     * @param scenario 기후변화 시나리오 (SSP1-2.6, SSP2-4.5, SSP5-8.5 등)
     * @param baseYear 기준 연도
     * @param lat 위도
     * @param lon 경도
     * @param assetValue 자산 가치 (원)
     * @param normalPrecipitation 평년 강수량 (mm)
     * @return 추정 피해액 (원)
     */
    public Long calculateDroughtDamage(String scenario, int baseYear, double lat, double lon, double assetValue, double normalPrecipitation) {
        String path = NetCDFUtils.resolveHazardPath("drought", scenario, baseYear);
        log.info("🌵 [가뭄] 시나리오: {}, 연도: {}, 좌표: ({}, {}), 자산: {}, 평년 강수량: {}, 경로: {}", scenario, baseYear, lat, lon, assetValue, normalPrecipitation, path);

        try (NetcdfFile nc = NetcdfFile.open(path)) {
            // 강수량 데이터 추출
            float prRaw = readValue(nc, "pr", lat, lon);
            // 강수량 단위 변환 (kg/m²/s → mm/day)
            float actualPrecip = prRaw * 86400f;

            // 가뭄 심각도에 따른 피해율 계산 (평년 대비 부족한 강수량 비율)
            double ratio = Math.max(0, 1 - (actualPrecip / normalPrecipitation)) * 100;
            log.info("🌧️ 실제 강수량: {} mm", actualPrecip);
            log.info("📊 평년 강수량: {} mm", normalPrecipitation);
            return logAndReturnDamage("가뭄", actualPrecip, "실제 강수량(mm)", ratio, assetValue);
        } catch (Exception e) {
            throw handleError("가뭄", e);
        }
    }

    /**
     * 폭염 피해액 추정 메서드
     *
     * @param scenario 기후변화 시나리오 (SSP1-2.6, SSP2-4.5, SSP5-8.5 등)
     * @param baseYear 기준 연도
     * @param lat 위도
     * @param lon 경도
     * @param assetValue 자산 가치 (원)
     * @return 추정 피해액 (원)
     */
    public Long calculateHeatwaveDamage(String scenario, int baseYear, double lat, double lon, double assetValue) {
        String path = NetCDFUtils.resolveHazardPath("heatwave", scenario, baseYear);
        log.info("🔥 [폭염] 시나리오: {}, 연도: {}, 좌표: ({}, {}), 자산: {}, 경로: {}", scenario, baseYear, lat, lon, assetValue, path);

        try (NetcdfFile nc = NetcdfFile.open(path)) {
            // 최고기온 변수 확인
            Variable tasmaxVar = nc.findVariable("tasmax");
            if (tasmaxVar == null) {
                throw new RuntimeException("NetCDF에 'tasmax' 변수 없음");
            }

            // 단위 확인 및 섭씨 온도로 변환 (켈빈→섭씨)
            String units = tasmaxVar.findAttribute("units").getStringValue().toLowerCase();
            float tasmaxRaw = readValue(nc, "tasmax", lat, lon);
            float tmaxCelsius = units.contains("k") ? tasmaxRaw - 273.15f : tasmaxRaw;

            // 폭염 피해율 계산 (35°C 초과 시 온도에 따른 선형 피해율)
            double ratio = Math.max(0, (tmaxCelsius - 35.0) / 15.0) * 100;
            return logAndReturnDamage("폭염", tmaxCelsius, "최고기온(℃)", ratio, assetValue);
        } catch (Exception e) {
            throw handleError("폭염", e);
        }
    }

    /**
     * NetCDF 파일에서 특정 위치의 값을 추출하는 공통 메서드
     *
     * @param nc NetCDF 파일 객체
     * @param variableName 추출할 변수명
     * @param lat 위도
     * @param lon 경도
     * @return 추출된 값
     */
    private float readValue(NetcdfFile nc, String variableName, double lat, double lon) throws Exception {
        Variable var = nc.findVariable(variableName);
        if (var == null) throw new RuntimeException("변수 없음: " + variableName);

        // 위도/경도에 가장 가까운 격자점 인덱스 찾기
        int latIdx = NetCDFUtils.findNearestIndex(nc.findVariable("lat").read(), lat);
        int lonIdx = NetCDFUtils.findNearestIndex(nc.findVariable("lon").read(), lon);

        // 해당 위치의 데이터 추출 (첫 번째 시간 단계만 사용)
        Array data = var.read(new int[]{0, latIdx, lonIdx}, new int[]{1, 1, 1});
        return data.getFloat(0);
    }

    /**
     * 피해액 계산 결과를 로그로 출력하고 반환하는 공통 메서드
     *
     * @param type 재해 유형 (태풍, 홍수, 가뭄, 폭염)
     * @param value 측정값 (풍속, 침수깊이, 강수량, 온도 등)
     * @param unitLabel 측정값 단위 설명
     * @param ratio 피해율 (%)
     * @param assetValue 자산 가치 (원)
     * @return 계산된 피해액 (원)
     */
    private Long logAndReturnDamage(String type, float value, String unitLabel, double ratio, double assetValue) {
        log.info("📊 {} 값: {} {}", type, String.format("%.2f", value), unitLabel);
        log.info("📉 손실률: {}%", String.format("%.2f", ratio));
        long damage = Math.round(assetValue * ratio / 100);
        log.info("💸 예상 피해액 (원): {}", damage);
        return damage;
    }

    /**
     * 예외 처리를 위한 공통 메서드
     *
     * @param type 재해 유형 (태풍, 홍수, 가뭄, 폭염)
     * @param e 발생한 예외
     * @return 처리된 RuntimeException
     */
    private RuntimeException handleError(String type, Exception e) {
        log.error("🚨 {} 피해 계산 실패: {}", type, e.getMessage(), e);
        return new RuntimeException(type + " 피해 계산 실패", e);
    }
}