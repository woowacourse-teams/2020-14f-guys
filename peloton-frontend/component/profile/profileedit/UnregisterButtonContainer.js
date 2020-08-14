import React from "react";
import { Alert, StyleSheet, Text, TouchableOpacity, View } from "react-native";
import { useNavigation } from "@react-navigation/native";
import Axios from "axios";
import { useRecoilValue } from "recoil";
import { COLOR, SERVER_BASE_URL } from "../../../utils/constants";
import { memberTokenState } from "../../../state/member/MemberState";

const UnregisterButtonContainer = () => {
  const navigation = useNavigation();
  const token = useRecoilValue(memberTokenState);

  const requestUnregister = () => {
    Axios.delete(`${SERVER_BASE_URL}/api/members`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    navigation.navigate("Login");
  };
  const createTwoButtonAlert = () =>
    Alert.alert(
      "Unregister",
      "정말로 탈퇴하시겠습니까?",
      [
        {
          text: "Nope",
          onPress: () =>
            console.log("Someone tried to unregister. But didn't."),
          style: "cancel",
        },
        { text: "Yes", onPress: requestUnregister },
      ],
      { cancelable: false }
    );

  return (
    <View style={styles.unregisterBtnContainer}>
      <TouchableOpacity style={styles.button} onPress={createTwoButtonAlert}>
        <Text style={styles.buttonText}>Unregister</Text>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  unregisterBtnContainer: {
    flex: 1,
    alignItems: "center",
    paddingBottom: 50,
  },
  button: {
    marginTop: 50,
    borderWidth: 1,
    width: 150,
    height: 50,
    borderColor: COLOR.WHITE3,
    borderRadius: 100,
    alignItems: "center",
    justifyContent: "center",
  },
  buttonText: {
    color: COLOR.GREEN2,
    fontSize: 14,
  },
});

export default UnregisterButtonContainer;
