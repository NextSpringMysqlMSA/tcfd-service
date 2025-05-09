package com.nsmm.esg.tcfdservice.util;

import ucar.ma2.Array;
import ucar.ma2.IndexIterator;

import java.io.File;

/**
 * NetCDF 관련 유틸리티 클래스
 * - 좌표 기반 인덱스 탐색
 * - 기후 재해별 NetCDF 파일 경로 자동 탐색
 */
public class NetCDFUtils {

    /**
     * 주어진 실수형 배열(Array)에서 target 값과 가장 가까운 값의 인덱스를 반환
     * (예: 위도 또는 경도 배열에서 가장 가까운 지점을 찾을 때 사용)
     *
     * @param array  NetCDF에서 추출한 Array (lat 또는 lon)
     * @param target 찾고자 하는 목표 위도 또는 경도
     * @return 가장 가까운 값의 인덱스
     */
    public static int findNearestIndex(Array array, double target) {
        IndexIterator iter = array.getIndexIterator();
        double minDiff = Double.MAX_VALUE;
        int idx = 0, i = 0;

        while (iter.hasNext()) {
            double value = iter.getDoubleNext();
            double diff = Math.abs(value - target);
            if (diff < minDiff) {
                minDiff = diff;
                idx = i;  // 최소 차이가 발생한 인덱스를 저장
            }
            i++;
        }
        return idx;
    }

    /**
     * 지정한 재해 유형(hazardType), 시나리오(scenario), 기준연도(baseYear)를 기반으로
     * NetCDF 기후 파일의 경로를 자동으로 탐색
     *
     * 디렉토리 구조: data/{hazardType}/{scenario}/
     * 파일명 형식: {hazardType}_{scenario}_{baseYear}.nc (와 유사한 이름을 자동 탐색)
     *
     * @param hazardType 재해 유형 (예: wind, flood, drought 등)
     * @param scenario   시나리오명 (예: ssp1-2.6)
     * @param baseYear   기준 연도 (예: 2020)
     * @return 해당 파일의 절대 경로
     */
    public static String resolveHazardPath(String hazardType, String scenario, int baseYear) {
        // 예: data/wind/ssp1-2.6
        String folder = String.format("data/%s/%s", hazardType.toLowerCase(), scenario.toLowerCase());
        File dir = new File(folder);

        // 디렉토리 존재 여부 확인
        if (!dir.exists() || !dir.isDirectory()) {
            throw new RuntimeException("❌ 디렉토리 없음: " + dir.getAbsolutePath());
        }

        // 디렉토리 내 파일 목록 불러오기 (null 처리 포함)
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            throw new RuntimeException("❌ 디렉토리 내 NetCDF 파일 없음: " + dir.getAbsolutePath());
        }

        // 소문자로 변환하여 파일명 조건 검사
        String scenarioPart = scenario.toLowerCase();
        String yearPart = String.valueOf(baseYear);

        for (File file : files) {
            String name = file.getName().toLowerCase();
            if (name.contains(hazardType.toLowerCase()) &&
                    name.contains(scenarioPart) &&
                    name.contains(yearPart) &&
                    name.endsWith(".nc")) {
                return file.getAbsolutePath();  // 조건에 맞는 파일 반환
            }
        }

        // 조건에 맞는 파일이 없는 경우 예외 발생
        throw new RuntimeException("❌ 해당 조건의 파일 없음: hazard=" + hazardType + ", scenario=" + scenario + ", year=" + baseYear);
    }
}
