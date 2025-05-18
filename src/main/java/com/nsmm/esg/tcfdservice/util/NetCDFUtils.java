package com.nsmm.esg.tcfdservice.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import ucar.ma2.Array;
import ucar.ma2.IndexIterator;
import ucar.nc2.NetcdfFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

/**
 * NetCDF 관련 유틸리티 클래스
 * - S3에서 NetCDF(.nc) 파일을 임시 파일로 다운로드 후 파싱
 * - 위도/경도 배열에서 가장 가까운 인덱스를 계산하는 기능 제공
 */
@Slf4j
@RequiredArgsConstructor
public class NetCDFUtils {

    private final S3Client s3;
    private final String bucket;

    /**
     * S3에서 NetCDF 파일을 임시 파일로 저장 후 NetcdfFile 객체로 반환합니다.
     *
     * [동작 흐름]
     * 1. S3에서 .nc 파일을 UUID 기반 임시 파일로 다운로드
     * 2. 해당 파일을 NetcdfFile로 파싱
     * 3. JVM 종료 시 자동 삭제 예약
     * 4. 실패 시 수동 삭제 및 예외 전파
     *
     * @param hazardType 재해 유형 (예: drought, flood, wind 등)
     * @param scenario   시나리오명 (예: ssp1-2.6)
     * @param baseYear   기준 연도 (예: 2020)
     * @return 파싱된 NetcdfFile 객체
     * @throws IOException 다운로드 또는 파싱 실패 시 예외 발생
     */
    public NetcdfFile loadFromS3(String hazardType, String scenario, int baseYear) throws IOException {
        // S3 key 예시: data/drought/ssp1-2.6/drought_ssp1-2.6_2020.nc
        String key = String.format("data/%s/%s/%s_%s_%d.nc",
                hazardType.toLowerCase(),
                scenario.toLowerCase(),
                hazardType.toLowerCase(),
                scenario.toLowerCase(),
                baseYear);

        log.debug("📦 S3에서 NetCDF 파일 다운로드 시작: key={}", key);

        // 고유한 임시 파일 경로 생성 (예: /tmp/s3-nc-xxxxxx.nc)
        Path tempFile = Path.of(System.getProperty("java.io.tmpdir"),
                "s3-nc-" + UUID.randomUUID() + ".nc");

        log.debug("🗂️ 생성할 임시 파일 경로: {}", tempFile.toAbsolutePath());

        try {
            // S3에서 임시 파일로 다운로드
            s3.getObject(
                    GetObjectRequest.builder()
                            .bucket(bucket)
                            .key(key)
                            .build(),
                    ResponseTransformer.toFile(tempFile)
            );
            log.info("✅ S3에서 NetCDF 다운로드 성공: {}", tempFile.toAbsolutePath());

            // NetCDF 파싱
            NetcdfFile netcdfFile = NetcdfFile.open(tempFile.toFile().getAbsolutePath());
            log.debug("📈 NetCDF 파싱 완료: {}", tempFile.getFileName());

            // JVM 종료 시 임시 파일 삭제 예약
            tempFile.toFile().deleteOnExit();

            return netcdfFile;

        } catch (Exception e) {
            // 실패 시 로그 출력 및 임시 파일 삭제
            log.error("❌ NetCDF 처리 실패: key={}, 파일={}, 원인={}",
                    key, tempFile.toAbsolutePath(), e.getMessage(), e);
            Files.deleteIfExists(tempFile);
            throw new IOException("NetCDF 로딩 실패: " + key, e);
        }
    }

    /**
     * NetCDF 배열에서 target 값과 가장 가까운 인덱스를 반환합니다.
     *
     * @param array  위도 또는 경도 배열
     * @param target 사용자 요청 좌표값
     * @return 가장 가까운 인덱스
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
