# app_13_todotask UI 디자인 명세

## 1. UI 요구사항

이 앱은 Jetpack Compose Navigation을 사용하여 두 개의 화면(메인, 할 일 추가)으로 구성된 간단한 "할 일(Todo)" 목록 애플리케이션을 만드는 것을 목표로 합니다.

*   **메인 화면 요구사항**:
    *   **요구사항 1-1**: 상단 바에 "Todo List"라는 제목이 표시되어야 합니다.
    *   **요구사항 1-2**: 할 일 목록이 비어있을 경우, "할 일이 없습니다."라는 안내 문구가 표시되어야 합니다.
    *   **요구사항 1-3**: 할 일 목록이 있을 경우, 각 항목이 아이콘과 텍스트로 구성된 리스트로 표시되어야 합니다.
    *   **요구사항 1-4**: 화면 우측 하단에 할 일을 추가하는 화면으로 이동하는 Floating Action Button(FAB)이 있어야 합니다.

*   **할 일 추가 화면 요구사항**:
    *   **요구사항 2-1**: 상단 바에 "Add Todo"라는 제목과 뒤로가기 버튼이 표시되어야 합니다.
    *   **요구사항 2-2**: 사용자로부터 새로운 할 일을 입력받을 수 있는 텍스트 필드가 있어야 합니다.
    *   **요구사항 2-3**: "저장" 버튼이 ��어야 하며, 이 버튼을 클릭하면 입력된 할 일이 메인 화면의 목록에 추가되고 이전 화면으로 돌아가야 합니다.

## 2. UI 구조 개요

이 앱은 `NavHost`를 사용하여 두 개의 화면, `main`과 `add`를 전환합니다.

```mermaid
graph TD
    subgraph "전체 앱 구조 (NavHost)"
        A[main: MainScreenContent]
        B[add: AddScreenContent]
        A -- "FAB 클릭" --> B
        B -- "저장 또는 뒤로가기" --> A
    end

    subgraph "MainScreenContent (Scaffold)"
        C[TopAppBar: "Todo List"]
        D[LazyColumn] --> E[TodoItem]
        F[FloatingActionButton]
    end

    subgraph "AddScreenContent (Scaffold)"
        G[TopAppBar: "Add Todo"]
        H[Column] --> I[OutlinedTextField] & J[Button: 저장]
    end

    style A fill:#BBDEFB
    style B fill:#C8E6C9
```

## 3. 주요 컴포저블 및 개념 설명

*   **`rememberNavController`**: `NavHost`를 제어하는 `NavController` 인스턴스를 생성하고 `remember`를 통해 상태를 유지합니다.

*   **`NavHost`**: 내비게이션 그래프의 컨테이너 역할을 합니다. `navController`와 시작 목적지(`startDestination`)를 정의합니다.
    *   `composable("main") { ... }`: "main"이라는 라우트(route)��� 해당하는 화면(Composable)을 정의합니다.
    *   `navController.navigate("add")`: "add" 라우트로 화면을 전환하는 명령입니다.
    *   `navController.popBackStack()`: 현재 화면을 내비게이션 스택에서 제거하고 이전 화면으로 돌아갑니다.

*   **상태 공유 (State Hoisting)**:
    *   할 일 목록 데이터(`datas`)가 `remember { mutableStateListOf<String>() }`로 `AppContent` 최상위 레벨에서 생성됩니다.
    *   이 `datas`를 `MainScreenContent`와 `AddScreenContent`에 파라미터로 전달하거나, 데이터를 수정하는 람다(`onSave`)를 전달합니다.
    *   이를 통해 여러 화면(Composable)이 동일한 데이터 소스를 공유하고 수정할 수 있습니다. `AddScreenContent`에서 `datas.add(...)`를 호출하면 `MainScreenContent`의 `LazyColumn`이 자동으로 업데이트(리컴포지션)됩니다. 이것이 **단방향 데이터 흐름(UDF)** 의 핵심 원리입니다.

*   **`Scaffold`**: 각 화면의 기본 구조를 제공하며, `topBar`와 `floatingActionButton` 같은 공통적인 UI 요소를 쉽게 배치할 수 있게 해줍니다.

*   **`LazyColumn`**: 메인 화면에서 할 일 목록을 효율적으로 표시하는 데 사용됩니다.

*   **`OutlinedTextField`**: '할 일 추가' 화면에서 사용자 입력을 받는 데 사용되는 텍스트 필드입니다.

## 4. 미리보기(Preview) 설명

*   **`MainScreenPreview`**: 메인 화면의 UI를 독립적으로 확인할 수 있습니다. 샘플 데이터를 직접 전달하여 목록이 있을 때의 화면을 보여줍니다.
*   **`AddScreenPreview`**: '할 일 추가' 화면의 UI를 독립적으로 확인할 수 있습니다.
*   **`AppContentPreview`**: `NavHost`를 포함한 전체 앱의 초기 화면(`MainScreenContent`)을 보여줍니다.
*   학생들은 각 프리뷰를 통해 개별 화면의 UI를 디자인하고, `AppContentPreview`를 통해 내비게이션과 상태 공유가 어떻게 통합되는지 전체적인 흐름을 파악할 수 있습니다.
