package com.openknights.app.core.navigator.api

/**
 * Module: core/navigator/api - 앱 내비게이션을 위한 인터페이스를 정의합니다。
 *
 * 이 모듈은 앱의 내비게이션 로직을 추상화하여 앱 전역에서 사용되는 서비스 역할을 합니다.
 * 다양한 내비게이션 구현체(예: Compose Navigation, Deep Link 등)를 지원하는 interrace를 제공합니다.
 * 테스트 용이성을 위해 인터페이스로 정의되어 있으며, 실제 구현체는 별도의 모듈에서 관리됩니다.
 */
interface Navigator {
    fun navigate(uri: String)
    fun navigateBack()
}

/* 참고: 모듈로 만들 기준 정리

:   기준 질문	             :                       모듈 분리 권장 여부   :
여러 Feature 모듈에서 사용하나요?	✔️ 예 → core 모듈로 분리
테스트 시 구현을 바꾸고 싶나요?	✔️ 예 → 인터페이스 분리, mock 가능
다양한 구현체 (ComposeNav, DeepLink 등)로 대체할 가능성이 있나요?	✔️ 예 → API/Impl 분리 추천
앱 규모가 커지고 있나요?	✔️ 예 → 장기적 유지보수 대비 필요
지금 단순하지만 미래에 복잡해질 것 같나요?	✔️ 예 → 구조적 분리 미리 해두는 게 좋음

*/