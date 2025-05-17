package com.nsmm.esg.tcfdservice.util;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import ucar.ma2.Array;
import ucar.ma2.IndexIterator;
import ucar.nc2.NetcdfFile;

import java.io.IOException;

/**
 * NetCDF 관련 유틸리티 클래스
 * - S3에서 .nc 파일 직접 로드
 * - 좌표 기반 인덱스 탐색
 */
@RequiredArgsConstructor
public class NetCDFUtils {

    private final S3Client s3;
    private final String bucket;

    /**
     * S3에 저장된 NetCDF 파일을 시나리오 기반 경로로부터 메모리에서 불러옴
     * 예: data/drought/ssp1-2.6/drought_ssp1-2.6_2020.nc
     *
     * @param hazardType 재해 유형 (예: drought, flood, wind)
     * @param scenario   시나리오 (예: ssp1-2.6)
     * @param baseYear   기준 연도 (예: 2020)
     * @return NetcdfFile (메모리 기반)
     */
    public NetcdfFile loadFromS3(String hazardType, String scenario, int baseYear) throws IOException {
        String key = String.format("data/%s/%s/%s_%s_%d.nc",
                hazardType.toLowerCase(),
                scenario.toLowerCase(),
                hazardType.toLowerCase(),
                scenario.toLowerCase(),
                baseYear);

        ResponseInputStream<GetObjectResponse> inputStream = s3.getObject(
                GetObjectRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .build()
        );

        byte[] bytes = IOUtils.toByteArray(inputStream);
        return NetcdfFile.openInMemory(key, bytes); // 메모리 내에서 직접 파싱
    }

    /**
     * 주어진 실수형 배열(Array)에서 target 값과 가장 가까운 인덱스 반환
     * (위도 또는 경도 좌표 일치용)
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
                idx = i;
            }
            i++;
        }
        return idx;
    }
}
