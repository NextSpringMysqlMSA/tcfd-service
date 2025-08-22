package com.nsmm.esg.tcfdservice.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import ucar.ma2.Array;
import ucar.ma2.IndexIterator;
import ucar.nc2.NetcdfFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

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
     * @param hazardType 재해 유형 (예: drought, flood, wind 등)
     * @param scenario   시나리오명 (예: ssp1-2.6)
     * @param baseYear   기준 연도 (예: 2020)
     * @return 파싱된 NetcdfFile 객체
     * @throws IOException 다운로드 또는 파싱 실패 시 예외 발생
     */
    public NetcdfFile loadFromS3(String hazardType, String scenario, int baseYear) throws IOException {
        String key = String.format("data/%s/%s/%s_%s_%d.nc",
                hazardType.toLowerCase(),
                scenario.toLowerCase(),
                hazardType.toLowerCase(),
                scenario.toLowerCase(),
                baseYear);

        log.debug("S3에서 NetCDF 파일 다운로드 시작: key={}", key);

        Path tempFile = Files.createTempFile("netcdf-", ".nc");

        try (ResponseInputStream<GetObjectResponse> responseInputStream = s3.getObject(
                GetObjectRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .build())) {

            // S3 스트림을 임시 파일에 저장
            Files.copy(responseInputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
            log.debug("임시 파일 저장 완료: {}", tempFile);

            // NetCDF 파일 파싱
            NetcdfFile netcdfFile = NetcdfFile.open(tempFile.toString());
            log.debug("NetCDF 파싱 완료: {}", key);

            return netcdfFile;

        } catch (Exception e) {
            log.error("NetCDF 처리 실패: key={}, 원인={}", key, e.getMessage(), e);
            throw new IOException("NetCDF 로딩 실패: " + key, e);

        } finally {
            // NetcdfFile이 성공적으로 열렸다면 해당 파일은 외부에서 닫히기 전까지 필요하므로,
            // 여기서 바로 삭제하면 안 됩니다. 대신 실패했을 경우만 삭제.
            try {
                if (Files.exists(tempFile)) {
                    Files.delete(tempFile);
                    log.debug("임시 파일 삭제 완료: {}", tempFile);
                }
            } catch (IOException cleanupEx) {
                log.warn("임시 파일 삭제 실패: {}", tempFile, cleanupEx);
            }
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
