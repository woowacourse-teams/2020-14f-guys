import React, { useState } from "react";
import { Alert, Modal, ScrollView, StyleSheet, Text, View } from "react-native";
import { COLOR } from "../../utils/constants";
import CheckBox from "react-native-check-box";
import SubmitButton from "./SubmitButton";
import KakaoLoginWebView from "./KakaoLoginWebView";
import { logNav } from "../../utils/Analytics";
import { useSetRecoilState } from "recoil";
import {
  memberInfoState,
  memberTokenState,
} from "../../state/member/MemberState";
import { loadingState } from "../../state/loading/LoadingState";
import { useNavigation } from "@react-navigation/core";
import { agreementContent } from "../../utils/AgreementContent";

const Agreement = () => {
  const [agreement, setAgreement] = useState(false);
  const [modalVisible, setModalVisible] = useState(false);

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
          <Text style={styles.content}>{agreementContent}</Text>
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
