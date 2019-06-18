# 개요
이클립스 플러그인 기반으로 구현된 이클립스 플러그인 이며, 총 4개의 플러그 인으로 구성되어 있다. 
Autosar Standard를 구현한 Artop 플러그인과 Autosar 모델의 EMF를 제공하는 Sphinx 플러그인을 기반으로 구현되어있다.

# 개발 환경 구성
본 프로젝트를 빌드하기 위해서 Artop SDK가 필요하다. Artop SDK의 버전에 따라 다음과 같은 호환성을 가진다.
- Artop 4.10: eclipse 4.7
- Artop 4.12: eclipse 4.10

# 프로젝트 구성
- kr.co.rtst.autosar.ap4x.core: Autosar Model 처리를 위한 플러그인. Autosar Model 처리를 위해서 따라야 하는 형태를  본 플러인의 유틸리티 및 Wrapping 객체를 통해 수행할 수 있다.

- kr.co.rtst.autosar.ap4x.editor: Adaptive Autosar Model 편집기의 요구사항을 구현.

- kr.co.rtst.autosar.ap4x.ide: 본 도구의 기본적인 기능을 구현.
  
- kr.co.rtst.autosar.common.ui: 편집기 플러그인에서 반복적으로 사용하는 UI 컴포넌트 생성해주는 팩토리 클래스 제공.

# 사사
- (국문) 본 결과물은 2019년도 정부(과학기술정보통신부)의 재원으로 정보통신기획평가원의 지원을 받아 수행된 연구임 
  (2018-0-00487,지능형 차량서비스를 위한 고신뢰·적응형 전장SW플랫폼 기술 개발)
- (영문) This work was supported by Institute for Information & communications Technology Promotion(IITP) grant funded by the Korea government(MSIT) 
  (No.2018-0-00487, Reliable and adaptive automotive SW platform for intelligent vehicle services)

