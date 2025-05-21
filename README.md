```mermaid
flowchart TD

%% 공통 시작 및 인증 처리
    Start((Start))
    Start --> Auth["X-MEMBER-ID 인증"]
    Auth --> AuthCheck{"인증 성공 여부"}
    AuthCheck -- "아니오" --> Error401["401 Unauthorized"] --> EndErr((End))
    AuthCheck -- "예" --> Router["요청 분기"]

%% 목록 조회
    Router --> GetList["회의 목록 조회"]
    GetList --> SvcList["meetingService.getMeetings()"]
    SvcList --> RespList["회의 목록 반환"]
    RespList --> End1((End))

%% 단건 조회
    Router --> GetOne["회의 단건 조회"]
    GetOne --> SvcOne["meetingService.getMeetingById()"]
    SvcOne --> RespOne["회의 반환"]
    RespOne --> End2((End))

%% 등록
    Router --> Create["회의 등록"]
    Create --> SvcCreate["meetingService.createMeeting()"]
    SvcCreate --> RespCreate["등록 완료 메시지"]
    RespCreate --> End3((End))

%% 수정
    Router --> Update["회의 수정"]
    Update --> SvcUpdate["meetingService.updateMeeting()"]
    SvcUpdate --> RespUpdate["수정 완료 메시지"]
    RespUpdate --> End4((End))

%% 삭제
    Router --> Delete["회의 삭제"]
    Delete --> SvcDelete["meetingService.deleteMeeting()"]
    SvcDelete --> RespDelete["삭제 완료 메시지"]
    RespDelete --> End5((End))

%% 색상 스타일 정의
    classDef forest fill:#e6f4ea,stroke:#2e7d32,stroke-width:1.5px,color:#2e7d32;
    classDef terminal fill:#d0f0c0,stroke:#1b5e20,color:#1b5e20;
    classDef error fill:#fdecea,stroke:#c62828,color:#c62828;

%% 클래스 적용 (줄바꿈 없이 한 줄에 작성)
    class Start,End1,End2,End3,End4,End5,EndErr terminal;
    class Auth,AuthCheck,Router,GetList,GetOne,Create,Update,Delete,SvcList,SvcOne,SvcCreate,SvcUpdate,SvcDelete,RespList,RespOne,RespCreate,RespUpdate,RespDelete forest;
    class Error401 error;

---


```mermaid
flowchart TD

%% 공통 시작 및 인증 처리
    Start((Start))
    Start --> Auth["X-MEMBER-ID 인증"]
    Auth --> AuthCheck{"인증 성공 여부"}
    AuthCheck -- "아니오" --> Error401["401 Unauthorized"] --> EndErr((End))
    AuthCheck -- "예" --> Router["요청 분기"]

%% KPI 목록 조회
    Router --> GetList["KPI 목록 조회"]
    GetList --> SvcList["kpiService.getKpiGoals()"]
    SvcList --> RespList["KPI 목록 반환"]
    RespList --> End1((End))

%% KPI 단건 조회
    Router --> GetOne["KPI 단건 조회"]
    GetOne --> SvcOne["kpiService.getKpiGoalById()"]
    SvcOne --> RespOne["KPI 반환"]
    RespOne --> End2((End))

%% KPI 등록
    Router --> Create["KPI 등록"]
    Create --> SvcCreate["kpiService.createKpiGoal()"]
    SvcCreate --> RespCreate["등록 완료 메시지"]
    RespCreate --> End3((End))

%% KPI 수정
    Router --> Update["KPI 수정"]
    Update --> SvcUpdate["kpiService.updateKpiGoal()"]
    SvcUpdate --> RespUpdate["수정 완료 메시지"]
    RespUpdate --> End4((End))

%% KPI 삭제
    Router --> Delete["KPI 삭제"]
    Delete --> SvcDelete["kpiService.deleteKpiGoal()"]
    SvcDelete --> RespDelete["삭제 완료 메시지"]
    RespDelete --> End5((End))
```

```mermaid
flowchart TD

%% 공통 시작 및 인증 처리
    Start((Start))
    Start --> Auth["X-MEMBER-ID 인증"]
    Auth --> AuthCheck{"인증 성공 여부"}
    AuthCheck -- "아니오" --> Error401["401 Unauthorized"] --> EndErr((End))
    AuthCheck -- "예" --> Router["요청 분기"]

%% 교육 목록 조회
    Router --> GetList["교육 목록 조회"]
    GetList --> SvcList["educationService.getEducations()"]
    SvcList --> RespList["교육 목록 반환"]
    RespList --> End1((End))

%% 교육 단건 조회
    Router --> GetOne["교육 단건 조회"]
    GetOne --> SvcOne["educationService.getEducationById()"]
    SvcOne --> RespOne["교육 반환"]
    RespOne --> End2((End))

%% 교육 등록
    Router --> Create["교육 등록"]
    Create --> SvcCreate["educationService.createEducation()"]
    SvcCreate --> RespCreate["등록 완료 메시지"]
    RespCreate --> End3((End))

%% 교육 수정
    Router --> Update["교육 수정"]
    Update --> SvcUpdate["educationService.updateEducation()"]
    SvcUpdate --> RespUpdate["수정 완료 메시지"]
    RespUpdate --> End4((End))

%% 교육 삭제
    Router --> Delete["교육 삭제"]
    Delete --> SvcDelete["educationService.deleteEducation()"]
    SvcDelete --> RespDelete["삭제 완료 메시지"]
    RespDelete --> End5((End))
```
```mermaid
flowchart TD

%% 공통 시작 및 인증
    Start((Start))
    Start --> Auth["X-MEMBER-ID 인증"]
    Auth --> AuthCheck{"인증 성공 여부"}
    AuthCheck -- "아니오" --> Error401["401 Unauthorized"] --> EndErr((End))
    AuthCheck -- "예" --> Router["요청 분기"]

%% TCFD 진행도 조회
    Router --> TCFDGet["TCFD 진행도 조회"]
    TCFDGet --> SvcTCFD["internalProgressService.getProgress()"]
    SvcTCFD --> RespTCFD["TCFD 진행도 반환"]
    RespTCFD --> End1((End))

%% NetZero 배출량 조회
    Router --> NZGet["NetZero 배출량 진행률 조회"]
    NZGet --> SvcNZ["internalProgressService.getNetZeroEmissionProgress()"]
    SvcNZ --> RespNZ["NetZero 배출량 반환"]
    RespNZ --> End2((End))
```

```mermaid
flowchart TD

%% 공통 시작 및 인증
    Start((Start))
    Start --> Auth["X-MEMBER-ID 인증"]
    Auth --> AuthCheck{"인증 성공 여부"}
    AuthCheck -- "아니오" --> Error401["401 Unauthorized"] --> EndError((End))
    AuthCheck -- "예" --> Route["요청 분기"]

%% 전체 조회
    Route --> NetZeroGetAll["GET /netzero"]
    NetZeroGetAll --> ServiceGetAll["NetZeroService.getNetZeroGoals"]
    ServiceGetAll --> RespGetAll["목표 목록 반환"]
    RespGetAll --> End1((End))

%% 단건 조회
    Route --> NetZeroGetOne["GET /netzero/{id}"]
    NetZeroGetOne --> ServiceGetOne["NetZeroService.getNetZeroGoalById"]
    ServiceGetOne --> RespGetOne["목표 반환"]
    RespGetOne --> End2((End))

%% 생성 (계산 포함)
    Route --> NetZeroCreate["POST /netzero"]
    NetZeroCreate --> BuildGoal["요청 기반 GoalNetZero 생성"]
    BuildGoal --> CalcEF["산업별 EF / AF / Eb 계산"]
    CalcEF --> CalcReduction["평균 감축률 계산"]
    CalcReduction --> YearlyEmission["연도별 배출량 계산"]
    YearlyEmission --> SaveGoal["DB 저장"]
    SaveGoal --> RespPost["생성된 목표 반환"]
    RespPost --> End3((End))

%% 수정
    Route --> NetZeroUpdate["PUT /netzero/{id}"]
    NetZeroUpdate --> UpdateGoal["기존 목표 수정 + 재계산"]
    UpdateGoal --> RespPut["수정된 목표 반환"]
    RespPut --> End4((End))

%% 삭제
    Route --> NetZeroDelete["DELETE /netzero/{id}"]
    NetZeroDelete --> DeleteGoal["NetZero 목표 삭제"]
    DeleteGoal --> RespDelete["삭제 완료 메시지"]
    RespDelete --> End5((End))
```

```mermaid
flowchart TD

%% 공통 시작 및 인증
Start((Start))
Start --> Auth["X-MEMBER-ID 인증"]
Auth --> AuthCheck{"인증 성공 여부"}
AuthCheck -- "아니오" --> Error401["401 Unauthorized"] --> EndError((End))
AuthCheck -- "예" --> Route["요청 분기"]

%% 전체 조회
Route --> ScenarioGetAll["GET /strategy/scenario"]
ScenarioGetAll --> ServiceGetAll["StrategyService.getScenarios()"]
ServiceGetAll --> RespGetAll["시나리오 목록 반환"]
RespGetAll --> End1((End))

%% 단건 조회
Route --> ScenarioGetOne["GET /strategy/scenario/{id}"]
ScenarioGetOne --> ServiceGetOne["StrategyService.getScenarioById()"]
ServiceGetOne --> RespGetOne["시나리오 반환"]
RespGetOne --> End2((End))

%% 생성
Route --> ScenarioPost["POST /strategy/scenario"]
ScenarioPost --> EstimateDamage["피해 추정 계산"]
EstimateDamage --> ServicePost["StrategyService.createScenario()"]
ServicePost --> RespPost["생성 완료 메시지"]
RespPost --> End3((End))

%% 수정
Route --> ScenarioPut["PUT /strategy/scenario/{id}"]
ScenarioPut --> EstimateUpdate["피해 재계산"]
EstimateUpdate --> ServicePut["StrategyService.updateScenario()"]
ServicePut --> RespPut["수정 완료 메시지"]
RespPut --> End4((End))

%% 삭제
Route --> ScenarioDelete["DELETE /strategy/scenario/{id}"]
ScenarioDelete --> ServiceDelete["StrategyService.deleteScenario()"]
ServiceDelete --> RespDelete["삭제 완료 메시지"]
RespDelete --> End5((End))
```

```mermaid
flowchart TD

%% 공통 시작 및 인증
    Start((Start))
    Start --> Auth["X-MEMBER-ID 인증"]
    Auth --> AuthCheck{"인증 성공 여부"}
    AuthCheck -- "아니오" --> Error401["401 Unauthorized"] --> EndError((End))
    AuthCheck -- "예" --> Route["요청 분기"]

%% 전체 조회
    Route --> RiskGetAll["GET /strategy/risk"]
    RiskGetAll --> ServiceGetAll["StrategyService.getRisks()"]
    ServiceGetAll --> RespGetAll["리스크 목록 반환"]
    RespGetAll --> End1((End))

%% 단건 조회
    Route --> RiskGetOne["GET /strategy/risk/{id}"]
    RiskGetOne --> ServiceGetOne["StrategyService.getRiskById()"]
    ServiceGetOne --> RespGetOne["리스크 반환"]
    RespGetOne --> End2((End))

%% 생성
    Route --> RiskPost["POST /strategy/risk"]
    RiskPost --> ServicePost["StrategyService.createRisk()"]
    ServicePost --> RespPost["생성 완료 메시지"]
    RespPost --> End3((End))

%% 수정
    Route --> RiskPut["PUT /strategy/risk/{id}"]
    RiskPut --> ServicePut["StrategyService.updateRisk()"]
    ServicePut --> RespPut["수정 완료 메시지"]
    RespPut --> End4((End))

%% 삭제
    Route --> RiskDelete["DELETE /strategy/risk/{id}"]
    RiskDelete --> ServiceDelete["StrategyService.deleteRisk()"]
    ServiceDelete --> RespDelete["삭제 완료 메시지"]
    RespDelete --> End5((End))
```
