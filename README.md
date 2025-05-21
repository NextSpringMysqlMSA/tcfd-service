## 🌿 ESG 플랫폼 - Forest 테마 다이어그램 모음

> 모든 다이어그램은 `Mermaid` 문법 기반이며, `forest` 스타일 (녹색 테마)로 통일되어 있습니다. 인증 실패는 붉은 테마(`error`)로 표시됩니다.

---

### ✅ 공통 스타일 정의 (아래 모든 다이어그램에 포함됨)

```mermaid
%% 스타일 정의
classDef forest fill:#e6f4ea,stroke:#2e7d32,stroke-width:1.5px,color:#2e7d32;
classDef terminal fill:#d0f0c0,stroke:#1b5e20,color:#1b5e20;
classDef error fill:#fdecea,stroke:#c62828,color:#c62828;
```

---

## 1️⃣ 위원회 (Committee)

```mermaid
flowchart TD
    Start((Start)) --> Auth["X-MEMBER-ID 인증"] --> AuthCheck{"인증 성공 여부"}
    AuthCheck -- "아니오" --> Error401["401 Unauthorized"] --> EndErr((End))
    AuthCheck -- "예" --> Router["요청 분기"]
    Router --> GetList["위원회 목록 조회"] --> SvcList["committeeService.getCommittees()"] --> RespList["위원회 목록 반환"] --> End1((End))
    Router --> GetOne["위원회 단건 조회"] --> SvcOne["committeeService.getCommitteeById()"] --> RespOne["단건 반환"] --> End2((End))
    Router --> Create["위원회 등록"] --> SvcCreate["committeeService.createCommittee()"] --> RespCreate["등록 완료 메시지"] --> End3((End))
    Router --> Update["위원회 수정"] --> SvcUpdate["committeeService.updateCommittee()"] --> RespUpdate["수정 완료 메시지"] --> End4((End))
    Router --> Delete["위원회 삭제"] --> SvcDelete["committeeService.deleteCommittee()"] --> RespDelete["삭제 완료 메시지"] --> End5((End))

    class Start,End1,End2,End3,End4,End5,EndErr terminal;
    class Auth,AuthCheck,Router,GetList,GetOne,Create,Update,Delete,SvcList,SvcOne,SvcCreate,SvcUpdate,SvcDelete,RespList,RespOne,RespCreate,RespUpdate,RespDelete forest;
    class Error401 error;
```

---

## 2️⃣ 회의 (Meeting)

```mermaid
flowchart TD
    Start((Start)) --> Auth["X-MEMBER-ID 인증"] --> AuthCheck{"인증 성공 여부"}
    AuthCheck -- "아니오" --> Error401["401 Unauthorized"] --> EndErr((End))
    AuthCheck -- "예" --> Router["요청 분기"]
    Router --> GetList["회의 목록 조회"] --> SvcList["meetingService.getMeetings()"] --> RespList["회의 목록 반환"] --> End1((End))
    Router --> GetOne["회의 단건 조회"] --> SvcOne["meetingService.getMeetingById()"] --> RespOne["회의 반환"] --> End2((End))
    Router --> Create["회의 등록"] --> SvcCreate["meetingService.createMeeting()"] --> RespCreate["등록 완료 메시지"] --> End3((End))
    Router --> Update["회의 수정"] --> SvcUpdate["meetingService.updateMeeting()"] --> RespUpdate["수정 완료 메시지"] --> End4((End))
    Router --> Delete["회의 삭제"] --> SvcDelete["meetingService.deleteMeeting()"] --> RespDelete["삭제 완료 메시지"] --> End5((End))

    class Start,End1,End2,End3,End4,End5,EndErr terminal;
    class Auth,AuthCheck,Router,GetList,GetOne,Create,Update,Delete,SvcList,SvcOne,SvcCreate,SvcUpdate,SvcDelete,RespList,RespOne,RespCreate,RespUpdate,RespDelete forest;
    class Error401 error;
```

---

## 3️⃣ KPI

```mermaid
flowchart TD
    Start((Start)) --> Auth["X-MEMBER-ID 인증"] --> AuthCheck{"인증 성공 여부"}
    AuthCheck -- "아니오" --> Error401["401 Unauthorized"] --> EndErr((End))
    AuthCheck -- "예" --> Router["요청 분기"]
    Router --> GetList["KPI 목록 조회"] --> SvcList["kpiService.getKpiGoals()"] --> RespList["KPI 목록 반환"] --> End1((End))
    Router --> GetOne["KPI 단건 조회"] --> SvcOne["kpiService.getKpiGoalById()"] --> RespOne["KPI 반환"] --> End2((End))
    Router --> Create["KPI 등록"] --> SvcCreate["kpiService.createKpiGoal()"] --> RespCreate["등록 완료 메시지"] --> End3((End))
    Router --> Update["KPI 수정"] --> SvcUpdate["kpiService.updateKpiGoal()"] --> RespUpdate["수정 완료 메시지"] --> End4((End))
    Router --> Delete["KPI 삭제"] --> SvcDelete["kpiService.deleteKpiGoal()"] --> RespDelete["삭제 완료 메시지"] --> End5((End))

    class Start,End1,End2,End3,End4,End5,EndErr terminal;
    class Auth,AuthCheck,Router,GetList,GetOne,Create,Update,Delete,SvcList,SvcOne,SvcCreate,SvcUpdate,SvcDelete,RespList,RespOne,RespCreate,RespUpdate,RespDelete forest;
    class Error401 error;
```

---

[📄 나머지 다이어그램도 같은 구조로 이어서 추가됨 - 필요 시 계속 이어서 작성해드립니다.]
