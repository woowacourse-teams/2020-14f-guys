import React from "react";
import {
  Alert,
  Keyboard,
  StyleSheet,
  Text,
  TextInput,
  TouchableOpacity,
  TouchableWithoutFeedback,
  View,
} from "react-native";
import { COLOR } from "../../../utils/constants";
import { useRecoilValue, useSetRecoilState } from "recoil";
import { useNavigation } from "@react-navigation/core";
import { MemberApi } from "../../../utils/api/MemberApi";
import {
  memberInfoState,
  memberTokenState,
} from "../../../state/member/MemberState";

const CashUpdate = () => {
  const [input, setInput] = React.useState(5000);
  const [cash, setCash] = React.useState(5000);
  const [isValid, setIsValid] = React.useState(true);
  const token = useRecoilValue(memberTokenState);
  const navigation = useNavigation();
  const [memberInfo, setMemberInfo] = useSetRecoilState(memberInfoState);

  const setCashWithValidate = (value) => {
    const onlyNumber = /^[0-9]+$/;
    setInput(value);
    if (!onlyNumber.test(value)) {
      setIsValid(false);
      return;
    }
    setIsValid(true);
    setCash(value);
  };

  const requestChangeCash = async () => {
    if (!isValid) {
      Alert.alert("충전 금액을 다시 입력해주세요.");
      return;
    }
    try {
      await MemberApi.patchCash(token, cash);
      const response = await MemberApi.get(token);
      setMemberInfo(response);
      navigation.navigate("ProfileEdit");
    } catch (error) {
      Alert.alert("에러가 발생했습니다.");
      console.log(error);
    }
  };

  const ErrorMessage = () => {
    return isValid ? null : (
      <View>
        <Text style={styles.errorMessage}>
          금액을 천원단위로 입력해주세요😊
        </Text>
      </View>
    );
  };

  return (
    <TouchableWithoutFeedback onPress={() => Keyboard.dismiss()}>
      <View style={styles.container}>
        <View style={styles.chargeContainer}>
          <View style={{ alignItems: "left", width: "100%" }}>
            <Text style={styles.chargeText}>충전 금액을 입력해주세요</Text>
            <TextInput
              style={styles.chargeInput}
              onChangeText={(text) => setCashWithValidate(text)}
              value={String(input)}
              keyboardType={"number-pad"}
            />
            <ErrorMessage />
          </View>
        </View>
        <View style={styles.buttonContainer}>
          <TouchableOpacity style={styles.button} onPress={requestChangeCash}>
            <Text style={styles.buttonText}>충전하기</Text>
          </TouchableOpacity>
        </View>
      </View>
    </TouchableWithoutFeedback>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: COLOR.WHITE4,
  },
  chargeContainer: {
    flex: 5,
    alignItems: "center",
    justifyContent: "center",
  },
  chargeText: {
    marginLeft: "14%",
    color: COLOR.GREEN3,
    fontWeight: "bold",
    fontSize: 20,
    lineHeight: 21,
    letterSpacing: -0.36,
    marginBottom: 20,
  },
  errorMessage: {
    position: "absolute",
    marginTop: "3%",
    marginLeft: "14%",
    color: COLOR.RED,
    fontWeight: "bold",
    fontSize: 15,
    lineHeight: 21,
  },
  chargeInput: {
    backgroundColor: COLOR.WHITE,
    fontSize: 16,
    fontWeight: "300",
    color: COLOR.GREEN2,
    paddingLeft: "10%",
    height: 60,
    width: "90%",
    shadowColor: "rgba(27, 28, 32, 0.3)",
    shadowOffset: {
      width: 0,
      height: 20,
    },
    shadowRadius: 30,
    shadowOpacity: 0.2,
    borderRadius: 10,
    alignSelf: "center",
  },
  buttonContainer: {
    flex: 2,
    alignItems: "center",
  },
  button: {
    marginTop: 50,
    width: 150,
    height: 50,
    borderRadius: 100,
    backgroundColor: COLOR.WHITE,
    color: COLOR.BLACK2,
    alignItems: "center",
    justifyContent: "center",

    shadowColor: "rgba(0, 0, 0, 0.3)",
    shadowOffset: {
      width: 0,
      height: 20,
    },
    shadowRadius: 50,
    shadowOpacity: 0.5,
  },
  buttonText: {
    color: COLOR.GREEN3,
    fontSize: 14,
  },
});

export default CashUpdate;
