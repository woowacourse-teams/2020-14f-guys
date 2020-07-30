import React from "react";
import {
  Keyboard,
  StyleSheet,
  Text,
  TextInput,
  TouchableOpacity,
  TouchableWithoutFeedback,
  View,
} from "react-native";
import Axios from "axios";
import { SERVER_BASE_URL } from "../../../utils/constants";
import { useRecoilValue, useSetRecoilState } from "recoil/dist";
import { userInfoState, userTokenState } from "../../atoms";
import { useNavigation } from "@react-navigation/core";

const CashUpdate = () => {
  const [cash, setCash] = React.useState(5000);
  const token = useRecoilValue(userTokenState);
  const navigation = useNavigation();
  const setUserInfo = useSetRecoilState(userInfoState);
  const userInfo = useRecoilValue(userInfoState);

  const requestChangeCash = () => {
    Axios.patch(
      `${SERVER_BASE_URL}/api/members/cash`,
      {
        cash: `${Number(userInfo.cash) + Number(cash)}`,
      },
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )
      .then(async () => {
        const response = await Axios.get(`${SERVER_BASE_URL}/api/members`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        await setUserInfo(response.data);
        navigation.navigate("ProfileEdit");
      })
      .catch((error) => console.log(error));
  };

  return (
    <TouchableWithoutFeedback onPress={() => Keyboard.dismiss()}>
      <View style={styles.container}>
        <View style={styles.chargeContainer}>
          <View style={{ alignItems: "left", width: "100%" }}>
            <Text style={styles.chargeText}>충전 금액을 입력해주세요</Text>
            <TextInput
              style={styles.chargeInput}
              onChangeText={(text) => setCash(text)}
              value={cash}
            />
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
    backgroundColor: "#eceff0",
  },
  chargeContainer: {
    flex: 5,
    alignItems: "center",
    justifyContent: "center",
  },
  chargeText: {
    marginLeft: "14%",
    color: "#334856",
    fontWeight: "bold",
    fontSize: 20,
    lineHeight: 21,
    letterSpacing: -0.36,
    marginBottom: 20,
  },
  chargeInput: {
    backgroundColor: "white",
    fontSize: 16,
    fontWeight: "300",
    color: "#628ca0",
    paddingLeft: "10%",
    height: 60,
    width: "90%",
    shadowColor: "rgba(27, 28, 32, 0.3)",
    shadowOffset: {
      width: 0,
      height: 20,
    },
    shadowRadius: 30,
    shadowOpacity: 1,
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
    backgroundColor: "white",
    color: "#1b1c20",
    alignItems: "center",
    justifyContent: "center",

    shadowColor: "rgba(0, 0, 0, 0.3)",
    shadowOffset: {
      width: 0,
      height: 20,
    },
    shadowRadius: 50,
    shadowOpacity: 1,
  },
  buttonText: {
    color: "#334856",
    fontSize: 14,
  },
});

export default CashUpdate;
