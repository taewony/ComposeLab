Compose로 화면을 구성하는 방법
컴포즈는 '컨테이너', 'UI 요소', 그리고 '변경자'라는
세 가지 핵심 요소의 조합으로 화면을 만들어냅니다.

화면 구성의 3가지 핵심 요소
컨테이너 (Containers)

다른 UI 요소들을 담는 그릇입니다.

자식들을 어떻게 배치할지 결정합니다. (예: Row, Column, Box)

개별 UI 요소 (Elements)

화면에 실제로 보이는 내용물입니다.

(예: Text, Button, Image)

변경자 (Modifiers)

UI 요소의 외형(크기, 색, 여백)과 동작(클릭 등)을 꾸미는 수식어입니다.

구조 시각화 (Mermaid Diagram)
컴포즈 UI는 '상자(컨테이너) 안에 작은 상자(UI 요소)를 넣고, 각 상자를 꾸미는(변경자)' 레고 블록과 같은 계층 구조를 가집니다.

```mermaid
graph TD
    A[Scaffold: 전체 화면 구조] --> B[Column: 세로 배치 컨테이너];
    B --> C{Text: 제목};
    B --> D[Row: 가로 배치 컨테이너];
    B --> E[Button: 버튼 요소];

    subgraph "변경자 (Modifier) 적용"
        C -- ".padding(16.dp)" --> C;
        D -- ".fillMaxWidth()" --> D;
        E -- ".clickable{}" --> E;
    end

    D --> F{Image: 이미지 요소};
    D --> G[Column: 내부 세로 배치];

    G --> H{Text: 부제목};
    G --> I{Text: 내용};

    style A fill:#B3E5FC,stroke:#01579B,stroke-width:2px
    style B fill:#C8E6C9,stroke:#1B5E20,stroke-width:2px
    style D fill:#C8E6C9,stroke:#1B5E20,stroke-width:2px
    style G fill:#E1F5FE,stroke:#0277BD,stroke-width:1px,stroke-dasharray: 5 5

    style C fill:#FFF9C4,stroke:#F57F17
    style E fill:#FFCCBC,stroke:#BF360C
    style F fill:#FFF9C4,stroke:#F57F17
    style H fill:#FFF9C4,stroke:#F57F17
    style I fill:#FFF9C4,stroke:#F57F17
```

요약
컨테이너로 구조를 잡고,

개별 UI 요소로 내용을 채운 뒤,

Modifier로 세밀하게 꾸미고 동작을 추가합니다.

이 세 가지 요소의 관계만 이해하면 어떤 복잡한 화면이라도 체계적으로 구성할 수 있습니다.