# OpenKnights App - 화면 설계 (Screen Design)

## 1. 개요
이 문서는 OpenKnights 앱의 주요 화면들에 대한 UI 구조 및 핵심 기능을 정의합니다.

## 2. 화면 상세 설명

### 2.1. HOME 화면 (대회 목록)
*   **목적**: 현재 진행 중이거나 예정된 오픈소스 경진대회 목록을 사용자에게 제공합니다.
*   **UI 구조 개요 (Mermaid)**:
```mermaid
graph TD
    A[HOME 화면] --> B[TopAppBar: "오픈소스 경진대회 목록"]
    B --> C[ContestListScreen]
    C --> D[ContestCard (반복)]
    D --> E[Contest 제목]
    D --> F[Contest 기간]
    D --> G[Contest 상태 (예: 접수 중, 종료)]
    A --> H[BottomNavigationBar]
    H --> I[HOME 탭]
    H --> J[프로젝트 탭]
    H --> K[사용자 탭]
```
*   **주요 컴포저블**: `TopAppBar`, `ContestListScreen`, `ContestCard`, `BottomNavigationBar`, `NavigationBarItem`
*   **기능**: 대회 목록 표시, 대회 클릭 시 해당 대회의 프로젝트 목록으로 이동, 하단 탭을 통한 화면 전환.

### 2.2. 프로젝트 화면 (프로젝트 목록)
*   **목적**: 특정 대회의 프로젝트 목록을 사용자에게 제공하고, 각 프로젝트의 상세 정보를 간략하게 보여줍니다.
*   **UI 구조 개요 (Mermaid)**:
```mermaid
graph TD
    A[프로젝트 화면] --> B[TopAppBar: "프로젝트 목록(ContestTerm)"]
    B --> C[프로젝트 개수 표시]
    C --> D[LazyColumn: 프로젝트 목록]
    D --> E[ProjectCard (반복)]
    E --> F[프로젝트 제목]
    E --> G[팀 이름]
    E --> H[프로젝트 단계 (Chip)]
    A --> I[BottomNavigationBar]
    I --> J[HOME 탭]
    I --> K[프로젝트 탭]
    I --> L[사용자 탭]
```
*   **주요 컴포저블**: `TopAppBar`, `Text` (프로젝트 개수), `LazyColumn`, `ProjectCard`, `BottomNavigationBar`, `NavigationBarItem`
*   **기능**: 선택된 대회의 프로젝트 목록 표시, 프로젝트 개수 표시, 프로젝트 카드 배경색 교차 적용, 프로젝트 단계 칩 표시.

### 2.3. 사용자 화면 (사용자 목록)
*   **목적**: 앱에 등록된 사용자 목록과 각 사용자의 간략한 정보 및 역할을 제공합니다.
*   **UI 구조 개요 (Mermaid)**:
```mermaid
graph TD
    A[사용자 화면] --> B[TopAppBar: "OpenKnights"]
    B --> C[LazyColumn: 사용자 목록]
    C --> D[UserCard (반복)]
    D --> E[사용자 이름]
    D --> F[사용자 소개]
    D --> G[사용자 역할 (Chip, 우측 정렬)]
    A --> H[BottomNavigationBar]
    H --> I[HOME 탭]
    H --> J[프로젝트 탭]
    H --> K[사용자 탭]
```
*   **주요 컴포저블**: `TopAppBar`, `LazyColumn`, `UserCard`, `TextChip`, `BottomNavigationBar`, `NavigationBarItem`
*   **기능**: 사용자 목록 표시, 각 사용자의 이름, 소개, 역할 칩 표시.
