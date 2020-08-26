import React, { useState } from "react";
import { Alert, Text, View, StyleSheet, ScrollView, Modal } from "react-native";
import { COLOR, TOKEN_STORAGE } from "../../utils/constants";
import CheckBox from "react-native-check-box";
import SubmitButton from "./SubmitButton";
import KakaoLoginWebView from "./KakaoLoginWebView";
import { logNav } from "../../utils/Analytics";
import AsyncStorage from "@react-native-community/async-storage";
import { MemberApi } from "../../utils/api/MemberApi";
import { navigateWithoutHistory } from "../../utils/util";
import { useSetRecoilState } from "recoil";
import {
  memberInfoState,
  memberTokenState,
} from "../../state/member/MemberState";
import { loadingState } from "../../state/loading/LoadingState";
import { useNavigation } from "@react-navigation/core";

const Agreement = () => {
  const [agreement, setAgreement] = useState(false);
  const [modalVisible, setModalVisible] = useState(false);
  const setToken = useSetRecoilState(memberTokenState);
  const setMemberInfo = useSetRecoilState(memberInfoState);
  const setIsLoading = useSetRecoilState(loadingState);
  const navigation = useNavigation();

  const toggleModal = () => {
    setModalVisible(!modalVisible);
  };

  const onSubmit = () => {
    if (!agreement) {
      Alert.alert("", "약관에 동의해주셔야 합니다.");
      return;
    }
    onLogin();
  };

  const onLogin = async () => {
    logNav("Agreement", "ApplicationNavigationRoot");
    toggleModal();
  };

  return (
    <View style={styles.container}>
      <Modal animationType={"slide"} visible={modalVisible} transparent>
        <KakaoLoginWebView toggleModal={toggleModal} />
      </Modal>
      <View style={styles.titleContainer}>
        <Text style={styles.title}>회원 약관</Text>
      </View>
      <View style={styles.body}>
        <ScrollView>
          <Text style={styles.content}>
            {"1. 개인정보의 처리 목적 <펠로톤>(‘https://peloton.ga’이하 ‘peloton’) 은(는) 다음의 목적을 위하여 개인정보를 처리하고 있으며, 다음의 목적 이외의 용도로는 이용하지 않습니다.\n" +
              "\n" +
              "- 고객 가입의사 확인, 고객에 대한 서비스 제공에 따른 본인 식별.인증, 회원자격 유지.관리, 물품 또는 서비스 공급에 따른 금액 결제, 물품 또는 서비스의 공급.배송 등\n" +
              "\n" +
              "2. 개인정보의 처리 및 보유 기간\n" +
              "\n" +
              "① <펠로톤>(‘https://peloton.ga’이하 ‘peloton’) 은(는) 정보주체로부터 개인정보를 수집할 때 동의 받은 개인정보 보유․이용기간 또는 법령에 따른 개인정보 보유․이용기간 내에서 개인정보를 처리․보유합니다.\n" +
              "\n" +
              "② 구체적인 개인정보 처리 및 보유 기간은 다음과 같습니다.\n" +
              "\n" +
              "고객 가입 및 관리 : 서비스 이용계약 또는 회원가입 해지시까지\n" +
              "\n" +
              "- 전자상거래에서의 계약․청약철회, 대금결제, 재화 등 공급기록 : 5년\n" +
              "\n" +
              "\n" +
              "\n" +
              "3. 개인정보의 제3자 제공에 관한 사항\n" +
              "\n" +
              "① <펠로톤>('https://peloton.ga'이하 'peloton') 은(는) 정보주체의 동의, 법률의 특별한 규정 등 개인정보 보호법 제17조 및 제18조에 해당하는 경우에만 개인정보를 제3자에게 제공합니다.\n" +
              "\n" +
              "② <펠로톤>('https://peloton.ga')은(는) 다음과 같이 개인정보를 제3자에게 제공하고 있습니다.\n" +
              "\n" +
              "1. <펠로톤>\n" +
              "개인정보를 제공받는 자 : 펠로톤\n" +
              "제공받는 자의 개인정보 이용목적 : 이메일, 이름, 서비스 이용 기록, 접속 로그, 위치, 쿠키, 접속 IP 정보\n" +
              "제공받는 자의 보유.이용기간: 3년\n" +
              "\n" +
              "\n" +
              "4. 개인정보처리 위탁\n" +
              "\n" +
              "① <펠로톤>('https://peloton.ga'이하 'peloton')은(는) 위탁계약 체결시 개인정보 보호법 제25조에 따라 위탁업무 수행목적 외 개인정보 처리금지, 기술적․관리적 보호조치, 재위탁 제한, 수탁자에 대한 관리․감독, 손해배상 등 책임에 관한 사항을 계약서 등 문서에 명시하고, 수탁자가 개인정보를 안전하게 처리하는지를 감독하고 있습니다.\n" +
              "\n" +
              "② 위탁업무의 내용이나 수탁자가 변경될 경우에는 지체없이 본 개인정보 처리방침을 통하여 공개하도록 하겠습니다.\n" +
              "\n" +
              "5. 정보주체와 법정대리인의 권리·의무 및 그 행사방법 이용자는 개인정보주체로써 다음과 같은 권리를 행사할 수 있습니다.\n" +
              "\n" +
              "① 정보주체는 펠로톤(‘https://peloton.ga’이하 ‘peloton) 에 대해 언제든지 다음 각 호의 개인정보 보호 관련 권리를 행사할 수 있습니다.\n" +
              "\n" +
              "1. 개인정보 열람요구\n" +
              "\n" +
              "2. 오류 등이 있을 경우 정정 요구\n" +
              "\n" +
              "3. 삭제요구\n" +
              "\n" +
              "4. 처리정지 요구\n" +
              "\n" +
              "\n" +
              "\n" +
              "6. 처리하는 개인정보의 항목 작성\n" +
              "\n" +
              "① <펠로톤>('https://peloton.ga'이하 'peloton') 은(는) 다음의 개인정보 항목을 처리하고 있습니다.\n" +
              "\n" +
              "1 <이름과 이메일 및 사용자 접속 로그를 통한 컨텐츠의 질 향상>\n" +
              "필수항목 : 이메일, 이름, 서비스 이용 기록, 접속 로그, 위치, 쿠키, 접속 IP 정보\n" +
              "\n" +
              "\n" +
              "7. 개인정보의 파기 <펠로톤>('peloton') 은(는) 원칙적으로 개인정보 처리목적이 달성된 경우에는 지체없이 해당 개인정보를 파기합니다. 파기의 절차, 기한 및 방법은 다음과 같습니다.\n" +
              "\n" +
              "-파기절차\n" +
              "이용자가 입력한 정보는 목적 달성 후 별도의 DB에 옮겨져(종이의 경우 별도의 서류) 내부 방침 및 기타 관련 법령에 따라 일정기간 저장된 후 혹은 즉시 파기됩니다. 이 때, DB로 옮겨진 개인정보는 법률에 의한 경우가 아니고서는 다른 목적으로 이용되지 않습니다.\n" +
              "\n" +
              "-파기기한\n" +
              "이용자의 개인정보는 개인정보의 보유기간이 경과된 경우에는 보유기간의 종료일로부터 5일 이내에, 개인정보의 처리 목적 달성, 해당 서비스의 폐지, 사업의 종료 등 그 개인정보가 불필요하게 되었을 때에는 개인정보의 처리가 불필요한 것으로 인정되는 날로부터 5일 이내에 그 개인정보를 파기합니다.\n" +
              "\n" +
              "\n" +
              "\n" +
              "8. 개인정보 자동 수집 장치의 설치•운영 및 거부에 관한 사항\n" +
              "\n" +
              "펠로톤 은 정보주체의 이용정보를 저장하고 수시로 불러오는 ‘쿠키’를 사용하지 않습니다.\n" +
              "\n" +
              "9. 개인정보 보호책임자 작성\n" +
              "\n" +
              "① 펠로톤(‘https://peloton.ga’이하 ‘peloton) 은(는) 개인정보 처리에 관한 업무를 총괄해서 책임지고, 개인정보 처리와 관련한 정보주체의 불만처리 및 피해구제 등을 위하여 아래와 같이 개인정보 보호책임자를 지정하고 있습니다.\n" +
              "\n" +
              "▶ 개인정보 보호책임자\n" +
              "성명 :문진주\n" +
              "직책 :사업주\n" +
              "직급 :사업주\n" +
              "연락처 :050713200930\n" +
              "이메일 :14fGuys@gmail.com\n" +
              "※ 개인정보 보호 담당부서로 연결됩니다.\n" +
              "\n" +
              "▶ 개인정보 보호 담당부서\n" +
              "부서명 :펠로톤\n" +
              "담당자 :문진주\n" +
              "연락처 :050713200930\n" +
              "② 정보주체께서는 펠로톤(‘https://peloton.ga’이하 ‘peloton) 의 서비스(또는 사업)을 이용하시면서 발생한 모든 개인정보 보호 관련 문의, 불만처리, 피해구제 등에 관한 사항을 개인정보 보호책임자 및 담당부서로 문의하실 수 있습니다. 펠로톤(‘https://peloton.ga’이하 ‘peloton) 은(는) 정보주체의 문의에 대해 지체 없이 답변 및 처리해드릴 것입니다.\n" +
              "\n" +
              "10. 개인정보 처리방침 변경\n" +
              "\n" +
              "①이 개인정보처리방침은 시행일로부터 적용되며, 법령 및 방침에 따른 변경내용의 추가, 삭제 및 정정이 있는 경우에는 변경사항의 시행 7일 전부터 공지사항을 통하여 고지할 것입니다.\n" +
              "\n" +
              "\n" +
              "\n" +
              "11. 개인정보의 안전성 확보 조치 <펠로톤>('peloton') 은(는) 개인정보보호법 제29조에 따라 다음과 같이 안전성 확보에 필요한 기술적/관리적 및 물리적 조치를 하고 있습니다.\n" +
              "\n" +
              "1. 개인정보 취급 직원의 최소화 및 교육\n" +
              "개인정보를 취급하는 직원을 지정하고 담당자에 한정시켜 최소화 하여 개인정보를 관리하는 대책을 시행하고 있습니다.\n" +
              "\n" +
              "2. 개인정보의 암호화\n" +
              "이용자의 개인정보는 비밀번호는 암호화 되어 저장 및 관리되고 있어, 본인만이 알 수 있으며 중요한 데이터는 파일 및 전송 데이터를 암호화 하거나 파일 잠금 기능을 사용하는 등의 별도 보안기능을 사용하고 있습니다.\n"}
          </Text>
        </ScrollView>
      </View>
      <CheckBox
        style={styles.checkbox}
        onClick={() => {
          setAgreement(!agreement);
        }}
        isChecked={agreement}
        leftText={"약관에 동의합니다."}
        leftTextStyle={{
          color: COLOR.WHITE,
          fontWeight: "100",
          fontSize: 16,
          textAlign: "left",
        }}
        checkBoxColor={COLOR.GRAY1}
        checkedCheckBoxColor={COLOR.BLUE1}
      />
      <View style={styles.submitButton}>
        <SubmitButton onSubmit={onSubmit} />
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: COLOR.BLUE5,
  },
  titleContainer: {
    flex: 1,
    minHeight: 35,
    marginTop: 60,
    alignItems: "center",
    justifyContent: "center",
  },
  title: {
    fontSize: 28,
    fontWeight: "100",
    fontStyle: "normal",
    lineHeight: 30,
    letterSpacing: 0.9,
    textAlign: "center",
    color: COLOR.WHITE,
  },
  body: {
    flex: 7,
    minHeight: 200,
    borderWidth: 1,
    margin: 25,
    padding: 10,
    borderColor: COLOR.WHITE,
  },
  content: {
    color: COLOR.WHITE,
    fontSize: 14,
    fontWeight: "200",
  },
  checkbox: {
    flex: 1,
    minHeight: 30,
    padding: 10,
    alignSelf: "center",
    width: 175,
  },
  submitButton: {
    alignItems: "center",
    minHeight: 50,
    marginBottom: 45,
  },
});

export default Agreement;
