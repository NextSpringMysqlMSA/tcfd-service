## 🌿 ESG 플랫폼 - Forest 테마 다이어그램 모음

> 모든 다이어그램은 `Mermaid` 문법 기반이며, `forest` 스타일 (녹색 테마)로 통일되어 있습니다. 인증 실패는 붉은 테마(`error`)로 표시됩니다.

---

## ✅ 공통 정의를 포함한 예시 다이어그램

```mermaid
flowchart TD

    %% 공통 시작 및 인증 처리
    Start((Start))
    Start --> Auth["X-MEMBER-ID 인증"]
    Auth --> AuthCheck{"인증 성공 여부"}
    AuthCheck -- "아니오" --> Error401["401 Unauthorized"] --> EndErr((End))
    AuthCheck -- "예" --> Router["요청 분기"]

    %% 목록 조회
    Router --> GetList["위원회 목록 조회"]
    GetList --> SvcList["committeeService.getCommittees()"]
    SvcList --> RespList["위원회 목록 반환"]
    RespList --> End1((End))

    %% 단건 조회
    Router --> GetOne["위원회 단건 조회"]
    GetOne --> SvcOne["committeeService.getCommitteeById()"]
    SvcOne --> RespOne["단건 반환"]
    RespOne --> End2((End))

    %% 등록
    Router --> Create["위원회 등록"]
    Create --> SvcCreate["committeeService.createCommittee()"]
    SvcCreate --> RespCreate["등록 완료 메시지"]
    RespCreate --> End3((End))

    %% 수정
    Router --> Update["위원회 수정"]
    Update --> SvcUpdate["committeeService.updateCommittee()"]
    SvcUpdate --> RespUpdate["수정 완료 메시지"]
    RespUpdate --> End4((End))

    %% 삭제
    Router --> Delete["위원회 삭제"]
    Delete --> SvcDelete["committeeService.deleteCommittee()"]
    SvcDelete --> RespDelete["삭제 완료 메시지"]
    RespDelete --> End5((End))

    %% 색상 스타일 정의
    classDef forest fill:#e6f4ea,stroke:#2e7d32,stroke-width:1.5px,color:#2e7d32;
    classDef terminal fill:#d0f0c0,stroke:#1b5e20,color:#1b5e20;
    classDef error fill:#fdecea,stroke:#c62828,color:#c62828;

    %% 클래스 적용
    class Start,End1,End2,End3,End4,End5,EndErr terminal;
    class Auth,AuthCheck,Router,GetList,GetOne,Create,Update,Delete,
          SvcList,SvcOne,SvcCreate,SvcUpdate,SvcDelete,
          RespList,RespOne,RespCreate,RespUpdate,RespDelete forest;
    class Error401 error;
```

---

## ✅ 주의사항 (GitHub Mermaid 렌더링 기준)

GitHub에서는 `classDef`와 `class`가 **정의된 다이어그램 안에서만 유효**하며, **단독 선언**으로는 작동하지 않습니다. 

> ❗ `classDef`는 반드시 `flowchart`, `sequenceDiagram` 등과 **함께 포함**되어 있어야 하며, 단독 사용 시 렌더링 오류가 발생합니다.

따라서 공통 스타일 정의는 각각의 Mermaid 다이어그램 내부에 포함되어야 하며, 아래와 같이 **다이어그램 내부에 작성**하세요.

```mermaid
flowchart TD
    ... (노드 정의)

    classDef forest fill:#e6f4ea,stroke:#2e7d32,stroke-width:1.5px,color:#2e7d32;
    classDef terminal fill:#d0f0c0,stroke:#1b5e20,color:#1b5e20;
    classDef error fill:#fdecea,stroke:#c62828,color:#c62828;
    class Start,End1,... forest;
```

필요하신 모든 다이어그램에 이 구조를 적용해드릴 수 있습니다. 원하시면 전체 ESG 흐름도 일괄 변환해드릴게요 ✅
