package com.nsmm.esg.tcfdservice.util;

import ucar.ma2.Array;
import ucar.ma2.IndexIterator;

import java.io.File;
import java.util.Map;

public class NetCDFUtils {

    private static final Map<String, String> scenarioToFileKeyword = Map.of(
            "ssp1-2.6", "ssp126",
            "ssp2-4.5", "ssp245",
            "ssp3-7.0", "ssp370",
            "ssp5-8.5", "ssp585"
    );

    // 위도/경도와 가장 가까운 index 찾기
    public static int findNearestIndex(Array array, double target) {
        IndexIterator iter = array.getIndexIterator();
        double minDiff = Double.MAX_VALUE;
        int idx = 0, i = 0;

        while (iter.hasNext()) {
            double value = iter.getDoubleNext();
            double diff = Math.abs(value - target);
            if (diff < minDiff) {
                minDiff = diff;
                idx = i;
            }
            i++;
        }
        return idx;
    }

    // 기후 시나리오 + 연도로 파일 경로 찾기
    public static String resolveWindPath(String scenario, int baseYear) {
        String folderName = scenario.toLowerCase(); // ex) ssp1-2.6
        String fileKeyword = scenarioToFileKeyword.get(folderName);

        if (fileKeyword == null) {
            throw new RuntimeException("지원하지 않는 시나리오: " + scenario);
        }

        String folder = String.format("data/wind/%s", folderName);
        File dir = new File(folder);

        if (!dir.exists() || !dir.isDirectory()) {
            throw new RuntimeException("디렉토리 없음: " + folder);
        }

        String year = String.valueOf(baseYear);

        for (File file : dir.listFiles()) {
            if (file.getName().contains(fileKeyword) && file.getName().contains(year)) {
                return file.getAbsolutePath();
            }
        }

        throw new RuntimeException("파일 없음: 시나리오=" + scenario + ", 연도=" + baseYear);
    }
}
