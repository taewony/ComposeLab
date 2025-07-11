import pandas as pd
import io
import matplotlib.pyplot as plt
import seaborn as sns
import numpy as np
import subprocess
import os
from konlpy.tag import Okt

# KoNLPy Okt 형태소 분석기 초기화
try:
    okt = Okt()
except Exception as e:
    print(f"Okt 형태소 분석기 초기화 중 오류 발생: {e}")
    print("Konlpy 및 Java(JDK)가 올바르게 설치되었는지 확인해주세요.")
    # 필요시 스크립트 실행 중단
    # raise e

# --- 1. 샘플 데이터 생성 ---
csv_data = """week,student_id,q1_summary,q3_difficulty,q4_attempts,q5_connection
1,student_A,"버튼 만드는 코드가 복잡해요.","코드가 너무 길어서 어려웠어요.","그냥 따라 쳤어요.","없어요"
1,student_B,"Compose는 UI를 만드는 도구인 것 같아요.","뭐가 뭔지 모르겠어서 그냥 복사했어요.","인터넷 검색을 좀 해봤어요.","잘 모르겠어요"
4,student_A,"State를 바꾸면 UI가 바뀌는 원리를 배웠어요.","State 초기화 하는 걸 깜빡해서 앱이 죽었어요. 내가 실수했어요.","Logcat을 확인하고, State 개념을 다시 찾아봤어요.","State는 Activity 생명주기와 관계가 깊은 것 같아요."
4,student_B,"데이터 구조에 맞게 UI 패턴을 만드는게 중요해요.","리스트가 안보여서 원인을 찾았는데, 어댑터를 연결 안했어요.","공식문서를 보고, 이전 실습 코드와 비교했어요.","RecyclerView의 Adapter 패턴은 데이터와 UI를 분리하는 설계 원칙과 같아요."
8,student_A,"네트워크 통신은 비동기 처리가 필수라는 구조를 이해했어요.","서버에서 데이터를 못 받아와서 내가 만든 데이터 클래스가 문제인 줄 알았어요.","디버깅으로 확인해보니 인터넷 권한을 누락했어요. Logcat도 보고, 동료에게 질문도 했어요.","네트워크 데이터 클래스는 Intent로 다른 화면에 전달할 수 있어요."
8,student_B,"상태 관리 라이브러리의 필요성을 느꼈어요. 복잡한 데이터 흐름을 제어하는 패턴이 중요해요.","상태 업데이트가 다른 화면에 반영이 안돼서 구조를 다시 봤어요.","상태 흐름도를 그려보고, ViewModel의 역할을 공식문서에서 재확인하고, 디버깅 툴을 썼어요.","상태 관리 패턴은 결국 앱 전체의 데이터 흐름을 설계하는 것과 같아요."
"""
df = pd.read_csv(io.StringIO(csv_data))

# --- 2. 정량 지표 계산 함수 정의 ---

def calculate_abstraction_score(text):
    abstraction_keywords = ['패턴', '구조', '원리', '흐름', '상태', '관계', '설계', '개념', '분리']
    score = 0
    try:
        nouns = okt.nouns(str(text))
        for keyword in abstraction_keywords:
            score += nouns.count(keyword)
    except Exception:
        return 0
    return score

def calculate_attribution_score(text):
    internal_keywords = ['내가', '제 불찰로', '실수로', '깜빡해서', '잊어서', '누락하여', '안해서']
    external_keywords = ['복잡해서', '어려워서', '원래', '때문에']
    score = 0
    try:
        morphs = okt.morphs(str(text))
        for keyword in internal_keywords:
            score += morphs.count(keyword)
        for keyword in external_keywords:
            score -= morphs.count(keyword)
    except Exception:
        return 0
    return max(0, score)

def calculate_strategy_score(text):
    strategies = ['로그캣', 'logcat', '공식문서', '검색', '질문', '비교', '디버깅', '디버거', '테스트', '출력']
    score = 0
    try:
        text_str = str(text).lower()
        unique_strategies = set()
        for strategy in strategies:
            if strategy in text_str:
                unique_strategies.add(strategy)
        score = len(unique_strategies)
    except Exception:
        return 0
    return score

def calculate_connectivity_score(text):
    tech_keywords = ['intent', 'state', 'recyclerview', 'adapter', 'viewmodel', 'coroutine', '네트워크', '데이터', 'ui', '화면']
    score = 0
    try:
        text_str = str(text).lower()
        found_keywords = set()
        for keyword in tech_keywords:
            if keyword in text_str:
                found_keywords.add(keyword)
        if len(found_keywords) > 1:
            score = len(found_keywords) * (len(found_keywords) - 1) / 2
    except Exception:
        return 0
    return score

# --- 3. 데이터프레임에 지표 적용 ---
df['abstraction_score'] = df['q1_summary'].apply(calculate_abstraction_score)
df['attribution_score'] = df['q3_difficulty'].apply(calculate_attribution_score)
df['strategy_score'] = df['q4_attempts'].apply(calculate_strategy_score)
df['connectivity_score'] = df['q5_connection'].apply(calculate_connectivity_score)
df['total_score'] = df['abstraction_score'] + df['attribution_score'] + df['strategy_score'] + df['connectivity_score']

print("--- 학습일지 분석 결과 ---")
print(df[['week', 'student_id', 'abstraction_score', 'attribution_score', 'strategy_score', 'connectivity_score', 'total_score']])

# --- 4. 결과 시각화 ---

# 그래프 스타일을 먼저 지정합니다.
plt.style.use('seaborn-v0_8-whitegrid')

# 그 다음, 한글 폰트를 설정합니다. 이 순서가 중요합니다.
try:
    plt.rc('font', family='Malgun Gothic') # Windows
    print("폰트를 'Malgun Gothic'으로 설정했습니다.")
except:
    print("경고: 'Malgun Gothic' 폰트 설정에 실패했습니다. 다른 OS 환경일 수 있습니다.")
    # Mac 사용자의 경우 'AppleGothic' 시도
    # try:
    #     plt.rc('font', family='AppleGothic')
    #     print("폰트를 'AppleGothic'으로 설정했습니다.")
    # except:
    #     print("경고: 한글 폰트 설정에 실패했습니다. 그래프에 한글이 깨져 보일 수 있습니다.")

plt.rcParams['axes.unicode_minus'] = False # 마이너스 부호 깨짐 방지

# 첫 번째 그래프: 4개의 지표
fig, axes = plt.subplots(2, 2, figsize=(16, 12))
fig.suptitle('주차별 메타인지 및 문제해결 능력 성장 추이', fontsize=20)

sns.lineplot(data=df, x='week', y='abstraction_score', hue='student_id', marker='o', ax=axes[0, 0])
axes[0, 0].set_title('개념 추상화 지수', fontsize=15)
axes[0, 0].set_ylabel('점수')
axes[0, 0].set_xlabel('주차')

sns.lineplot(data=df, x='week', y='attribution_score', hue='student_id', marker='o', ax=axes[0, 1])
axes[0, 1].set_title('문제 원인 귀인 점수', fontsize=15)
axes[0, 1].set_ylabel('점수')
axes[0, 1].set_xlabel('주차')

sns.lineplot(data=df, x='week', y='strategy_score', hue='student_id', marker='o', ax=axes[1, 0])
axes[1, 0].set_title('해결 전략 다양성 지수', fontsize=15)
axes[1, 0].set_ylabel('점수')
axes[1, 0].set_xlabel('주차')

sns.lineplot(data=df, x='week', y='connectivity_score', hue='student_id', marker='o', ax=axes[1, 1])
axes[1, 1].set_title('지식 연결성 점수', fontsize=15)
axes[1, 1].set_ylabel('점수')
axes[1, 1].set_xlabel('주차')

plt.tight_layout(rect=[0, 0.03, 1, 0.95])
plt.show()

# 두 번째 그래프: 전체 점수
plt.figure(figsize=(10, 6))
sns.lineplot(data=df, x='week', y='total_score', hue='student_id', marker='o', lw=2.5)
plt.title('종합 성장 점수 변화', fontsize=18)
plt.xlabel('주차', fontsize=12)
plt.ylabel('종합 점수', fontsize=12)
plt.legend(title='학생 ID')
plt.show()