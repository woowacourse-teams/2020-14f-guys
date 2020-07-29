import React, { useState } from "react";
import {
  Keyboard,
  StyleSheet,
  Text,
  TouchableWithoutFeedback,
  View,
} from "react-native";
import { useRecoilValue } from "recoil";
import { userInfoState, userTokenState } from "../atoms";
import { useNavigation } from "@react-navigation/core";
import { SERVER_BASE_URL } from "../../utils/constants";
import Axios from "axios";
import { useRecoilState } from "recoil/dist";
import { CommonActions } from "@react-navigation/native";
import ProfileImage from "./ProfileImage";
import SubmitButton from "./SubmitButton";
import NicknameInput from "./NicknameInput";

const ChangeProfile = () => {
  const userToken = useRecoilValue(userTokenState);
  const [userInfo, setUserInfo] = useRecoilState(userInfoState);
  const navigation = useNavigation();
  const [userInput, setUserInput] = useState("");

  const navigateHome = () => {
    navigation.dispatch({
      ...CommonActions.reset({
        index: 0,
        routes: [
          {
            name: "ApplicationNavigationRoot",
          },
        ],
      }),
    });
  };

  const onSubmit = async () => {
    Keyboard.dismiss();
    const response = await Axios({
      method: "PATCH",
      baseURL: SERVER_BASE_URL,
      url: "/api/members/name",
      headers: {
        Authorization: "Bearer " + userToken,
      },
      data: {
        name: userInput,
      },
    });
    if (response.status === 200) {
      setUserInfo({
        ...userInfo,
        name: userInput,
      });
      navigateHome();
    } else {
      alert("알 수 없는 에러가 발생했습니다.");
      navigation.goBack();
    }
  };

  return (
    <TouchableWithoutFeedback onPress={Keyboard.dismiss} accessible={false}>
      <View style={styles.container}>
        <View style={styles.titleContainer}>
          <Text style={styles.title}>프로필 설정</Text>
        </View>
        <View style={styles.body}>
          <ProfileImage />
          <NicknameInput
            userInput={userInput}
            setUserInput={setUserInput}
            onSubmit={onSubmit}
          />
          <SubmitButton onSubmit={onSubmit} />
        </View>
      </View>
    </TouchableWithoutFeedback>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#21365d",
  },
  titleContainer: {
    flex: 3,
    alignItems: "center",
    justifyContent: "center",
  },
  title: {
    width: 150,
    height: 41,
    fontSize: 28,
    fontWeight: "100",
    fontStyle: "normal",
    lineHeight: 30,
    letterSpacing: 0.9,
    textAlign: "center",
    color: "#ffffff",
  },
  body: {
    flex: 7,
    alignItems: "center",
    borderStyle: "solid",
    borderBottomWidth: 2,
    borderColor: "#21365d",
  },
  bodyContents: {
    fontSize: 22,
    fontStyle: "normal",
    lineHeight: 35,
    letterSpacing: 0,
    textAlign: "left",
    color: "#ffffff",
  },
  bodyContainer: {
    marginTop: 50,
    alignItems: "center",
  },
});

export default ChangeProfile;
