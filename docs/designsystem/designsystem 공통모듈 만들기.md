네, 학생들에게 디자인 시스템 구축 과정을 단계별로 교육하는 것은 매우 좋은 방법입니다. 말씀하신 대로, 처음에는 독립적으로 실행 가능한 application 모듈에서 자유롭게 실험하고, 이후에 실제 프로젝트에 통합하기 위해 library 모듈로 전환하는 과정은 학생들이 개념을 이해하는 데 큰 도움이 됩니다.

아래에 학생들이 따라 할 수 있도록 단계별 가이드를 작성했습니다.

---

### **\#\#\# 👨‍🏫 디자인 시스템 모듈 구축 가이드 (학생용)**

안녕하세요, 여러분\! 오늘은 우리 앱의 '디자인 통일성'을 책임질 디자인 시스템 모듈을 만드는 과정을 배워보겠습니다. 처음에는 우리가 만든 테마가 어떻게 보이는지 바로바로 확인할 수 있도록 \*\*실행 가능한 앱(Application)\*\*으로 만들어서 실험해 볼 거예요. 그리고 실험이 끝나면 다른 팀원들이 가져다 쓸 수 있는 \*\*재사용 가능한 부품(Library)\*\*으로 변신시켜 보겠습니다.

#### **1단계: 실험실 만들기 \- :designsystem 애플리케이션 모듈 생성**

먼저 우리만의 디자인을 마음껏 실험해 볼 수 있는 독립적인 공간을 만들겠습니다.

1. **새 모듈 추가**  
   * Android Studio 메뉴에서 File \> New \> New Module...을 선택하세요.  
   * 템플릿 창이 뜨면 \*\*Application\*\*을 선택하고 Next를 누릅니다.  
   * 모듈 이름(Module name)에 **designsystem** 이라고 입력합니다. 패키지 이름이나 언어 등은 프로젝트에 맞게 설정하고 Finish를 누릅니다.  
2. **테마 파일 확인 및 수정 (Color, Type, Theme)**  
   * 방금 만든 :designsystem 모듈의 src/main/java/.../ui/theme 패키지로 이동하세요.  
   * Color.kt: 우리 앱의 브랜드 색상(Primary, Secondary 등)을 정의합니다. 학생들이 원하는 색상으로 자유롭게 바꿔보게 하세요.  
   * Type.kt: 앱에서 사용할 글꼴(Typography) 스타일을 정의합니다. (예: bodyLarge, titleMedium)  
   * Theme.kt: 위에서 만든 색상과 글꼴을 조합하여 AppTheme이라는 최종 테마를 완성하는 곳입니다.  
3. **테마 적용하고 실시간으로 확인하기**  
   * :designsystem 모듈의 MainActivity.kt 파일을 엽니다.  
   * setContent 블록 안에 우리가 만든 AppTheme이 적용되어 있을 거예요. 여기에 여러 가지 컴포저블(Button, Text, Card 등)을 추가해서 테마가 어떻게 적용되는지 확인해 보세요.  
4. Kotlin

// designsystem/src/main/java/.../MainActivity.kt  
class MainActivity : ComponentActivity() {  
    override fun onCreate(savedInstanceState: Bundle?) {  
        super.onCreate(savedInstanceState)  
        setContent {  
            // 우리가 만든 테마를 여기에 적용합니다\!  
            AppTheme {  
                // 다양한 컴포저블을 추가해서 색상과 글꼴이 잘 적용되는지 확인해봅시다.  
                Column(modifier \= Modifier.padding(16\.dp)) {  
                    Text("This is a Title", style \= MaterialTheme.typography.titleLarge)  
                    Spacer(modifier \= Modifier.height(8\.dp))  
                    Button(onClick \= { /\*TODO\*/ }) {  
                        Text("Primary Button")  
                    }  
                    // ... 등등  
                }  
            }  
        }  
    }  
}

5.   
6.   
7. **실행 및 실험**  
   * Android Studio 상단의 실행 설정(Run Configuration)을 \*\*designsystem\*\*으로 변경하고 앱을 실행하세요.  
   * 이제 에뮬레이터나 실제 기기에서 designsystem 앱이 실행됩니다. Color.kt 나 Type.kt 의 값을 바꾸고 다시 실행하면서 디자인이 어떻게 변하는지 자유롭게 실험해보세요\!

---

#### **2단계: 부품으로 변신\! \- application을 library로 전환하기**

자, 이제 실험이 끝났습니다. 우리가 만든 멋진 AppTheme을 이제 다른 모듈(메인 앱 등)에서 가져다 쓸 수 있도록 '재사용 가능한 부품(라이브러리)'으로 만들어 보겠습니다.

1. **build.gradle.kts 파일 수정 (가장 중요\!)**  
   * **:designsystem** 모듈의 build.gradle.kts 파일을 엽니다.  
   * 파일 상단의 plugins 블록을 찾아서, id("com.android.application")을 \*\*id("com.android.library")\*\*로 변경하세요.  
   * android 블록 안에 있는 defaultConfig를 찾으세요. 라이브러리는 고유 ID가 필요 없으므로 **applicationId 라인을 완전히 삭제**합니다.  
   * applicationId가 정의된 versionCode, versionName도 라이브러리에서는 필수가 아니므로 삭제해도 괜찮습니다.  
2. Kotlin

// 🎨 변경 전 (designsystem/build.gradle.kts)  
plugins {  
    id("com.android.application") // ◀️ 이 부분  
    // ...  
}

android {  
    // ...  
    defaultConfig {  
        applicationId \= "com.example.designsystem" // ◀️ 이 부분  
        // ...  
    }  
}

3.   
4. Kotlin

// 🎨 변경 후 (designsystem/build.gradle.kts)  
plugins {  
    id("com.android.library") // ◀️ 이렇게 변경\!  
    // ...  
}

android {  
    // ...  
    defaultConfig {  
        // applicationId 라인 삭제\!  
        // ...  
    }  
}

5.   
6.   
7. **불필요한 파일 정리**  
   * **AndroidManifest.xml 수정**: 라이브러리는 스스로 실행되지 않으므로, 앱 실행에 필요한 설정을 제거해야 합니다.  
     * designsystem/src/main/AndroidManifest.xml 파일을 엽니다.  
     * \<activity\> 태그 안에 있는 \<intent-filter\> 블록 전체를 삭제하세요. (앱 아이콘을 만드는 부분)  
     * \<application\> 태그에 있는 android:icon, android:label, android:theme 등 앱과 관련된 속성들도 삭제해도 좋습니다.  
   * **(선택) MainActivity.kt 삭제**: 이제 이 모듈은 직접 실행할 수 없으므로, 테스트용으로 만들었던 MainActivity.kt 파일은 더 이상 필요 없습니다. 삭제해도 무방합니다.

이제 Sync Now를 눌러 프로젝트를 동기화하면, 우리의 :designsystem 모듈은 완벽한 라이브러리 모듈로 변신했습니다\!

---

#### **3단계: 조립하기 \- 다른 모듈에서 :designsystem 라이브러리 사용하기**

마지막으로, 우리 앱의 본체인 :app 모듈에서 방금 만든 디자인 시스템 부품을 가져와 조립해 보겠습니다.

1. **의존성 추가**  
   * **:app** 모듈의 build.gradle.kts 파일을 엽니다.  
   * dependencies 블록에 아래 코드를 추가하여 :designsystem 모듈을 사용하겠다고 알려줍니다.  
2. Kotlin

// app/build.gradle.kts  
dependencies {  
    // ... 기존 의존성들  
    implementation(project(":designsystem")) // ◀️ 이 줄을 추가\!  
}

3.   
   * Sync Now를 눌러 프로젝트를 동기화합니다.  
4. **공통 테마 사용하기**  
   * **:app** 모듈의 MainActivity.kt를 엽니다.  
   * setContent 블록 안을 :designsystem 모듈에서 가져온 AppTheme으로 감싸줍니다.  
   * import 구문이 :designsystem 모듈의 경로를 잘 가리키고 있는지 꼭 확인하세요.  
5. Kotlin

// app/src/main/java/.../MainActivity.kt  
import com.example.designsystem.ui.theme.AppTheme // ◀️ import 경로 확인\!

class MainActivity : ComponentActivity() {  
    override fun onCreate(savedInstanceState: Bundle?) {  
        super.onCreate(savedInstanceState)  
        setContent {  
            // 이제 :app 모듈이 :designsystem 모듈의 테마를 사용합니다\!  
            AppTheme {  
                // 여기에 앱의 실제 화면을 구성하면 됩니다.  
            }  
        }  
    }  
}

6.   
7. 

이제 모든 준비가 끝났습니다\! 앞으로는 :designsystem 모듈의 Color.kt 파일만 수정하면, :app 모듈을 포함한 우리 프로젝트 전체의 디자인이 일관되게 변경될 것입니다.

이 과정을 통해 학생들은 모듈의 역할을 명확히 이해하고, 확장 가능성이 큰 프로젝트 구조를 설계하는 방법을 자연스럽게 익힐 수 있을 것입니다.

네, 아주 좋은 질문입니다. :designsystem 같은 공통 모듈을 만들 때 application과 library 중 어떤 타입으로 만들어야 하는지 이해하는 것은 모듈화 아키텍처에서 매우 중요합니다.

결론부터 말씀드리면, :designsystem 모듈은 **반드시 라이브러리(library) 모듈로 만들어야 합니다.**

두 타입의 근본적인 차이는 **'독립적으로 실행될 수 있는가?'** 와 **'다른 모듈의 부품으로 사용될 수 있는가?'** 에 있습니다.

---

### **\#\# 📚 라이브러리 모듈 (com.android.library)**

\*\*라이브러리 모듈은 다른 모듈이 가져다 쓸 수 있는 '재사용 가능한 부품(코드와 리소스의 묶음)'\*\*입니다.

* **목적**: 공통 기능, UI 컴포넌트, 디자인 시스템, 데이터 로직 등을 캡슐화하여 여러 모듈에서 공유하는 것이 주된 목적입니다.  
* **실행**: 스스로는 실행될 수 없습니다. 최종적으로 application 모듈에 포함되어야만 앱의 일부로서 동작합니다.  
* **빌드 결과물**: 빌드하면 **AAR (Android Archive)** 파일이 생성됩니다. 이 AAR 파일은 다른 모듈의 의존성으로 추가됩니다.  
* **build.gradle.kts 설정**: 플러그인으로 id("com.android.library")를 사용합니다. applicationId 설정이 없습니다.

AppTheme이 포함된 :designsystem 모듈은 :app 모듈이나 다른 기능 모듈(:feature\_home, :feature\_search 등)에서 가져다 써야 하는 **'공통 부품'** 이므로, 라이브러리 모듈이 되어야 합니다.

---

### **\#\# 📱 애플리케이션 모듈 (com.android.application)**

\*\*애플리케이션 모듈은 사용자가 기기에 설치하고 직접 실행할 수 있는 '완제품 앱'\*\*입니다.

* **목적**: 프로젝트의 최종 결과물이며, 앱의 시작점(Entry Point) 역할을 합니다.  
* **실행**: 독립적으로 실행 가능하며, 스마트폰에 설치되는 대상입니다.  
* **빌드 결과물**: 빌드하면 **APK (Android Package Kit)** 또는 **AAB (Android App Bundle)** 파일이 생성됩니다.  
* **build.gradle.kts 설정**: 플러그인으로 id("com.android.application")를 사용하며, 앱을 식별하는 고유한 applicationId를 반드시 지정해야 합니다.

만약 :designsystem 모듈을 application으로 만들면, 다른 모듈이 이 모듈을 의존성으로 추가하여 AppTheme을 사용할 수 없습니다. **애플리케이션 모듈은 다른 모듈의 '부품'이 될 수 없기 때문**입니다.

---

### **\#\#\# analogy 비유로 이해하기: 레고(LEGO) 블록 🧱**

* **라이브러리 모듈**: 자동차를 만들기 위한 **'바퀴', '창문', '엔진' 같은 개별 레고 부품**입니다. 이 부품들 자체만으로는 완성품이 아니지만, 자동차를 만드는 데 필수적입니다.  
  * :designsystem은 모든 레고 모델에 공통으로 필요한 색상과 모양 규칙을 담은 \*\*'디자인 가이드북' 또는 '특별 부품 상자'\*\*와 같습니다.  
* **애플리케이션 모듈**: 이 모든 레고 부품들을 조립하여 만든 **'완성된 레고 자동차'** 입니다. 이것이 바로 우리가 가지고 노는 최종 결과물입니다.  
  * :app 모듈이 바로 이 완성된 자동차에 해당합니다.

---

### **\#\# 📝 한눈에 보는 비교**

| 항목 | 라이브러리 모듈 (Library) | 애플리케이션 모듈 (Application) |
| :---- | :---- | :---- |
| **목적** | 재사용 가능한 코드/리소스 묶음 (부품) | 사용자가 설치하고 실행하는 앱 (완제품) |
| **실행 가능 여부** | ❌ 불가능 | ✅ 가능 |
| **빌드 결과물** | AAR (Android Archive) | APK 또는 AAB |
| **다른 모듈의 의존성 대상** | ✅ 가능 (주된 역할) | ❌ 불가능 |
| **build.gradle.kts 플러그인** | com.android.library | com.android.application |
| **applicationId** | ❌ 없음 | ✅ 필수 |

따라서 :designsystem과 같이 여러 모듈에서 공통으로 사용될 코드를 관리하는 모듈은 항상 **라이브러리 모듈**로 만들어야 합니다.

