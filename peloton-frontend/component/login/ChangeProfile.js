import React, { useState } from "react";
import {
  Keyboard,
  KeyboardAvoidingView,
  StyleSheet,
  Text,
  TouchableWithoutFeedback,
  View,
} from "react-native";
import { useRecoilValue } from "recoil";
import { userInfoState, userTokenState } from "../atoms";
import { useNavigation } from "@react-navigation/core";
import { SERVER_BASE_URL, TOKEN_STORAGE } from "../../utils/constants";
import AsyncStorage from "@react-native-community/async-storage";
import Axios from "axios";
import { useRecoilState, useSetRecoilState } from "recoil/dist";
import ProfileImage from "./ProfileImage";
import SubmitButton from "./SubmitButton";
import NicknameInput from "./NicknameInput";
import { navigateWithoutHistory } from "../../utils/util";
import LoadingIndicator from "../../utils/LoadingIndicator";
import { loadingState } from "../../state/loading/LoadingState";

const ChangeProfile = () => {
  const userToken = useRecoilValue(userTokenState);
  const [userInfo, setUserInfo] = useRecoilState(userInfoState);
  const navigation = useNavigation();
  const [userInput, setUserInput] = useState("");
  const setIsLoading = useSetRecoilState(loadingState);

  const onSubmit = async () => {
    Keyboard.dismiss();
    await AsyncStorage.setItem(TOKEN_STORAGE, userToken);
    await Axios({
      method: "PATCH",
      baseURL: SERVER_BASE_URL,
      url: "/api/members/name",
      headers: {
        Authorization: "Bearer " + userToken,
      },
      data: {
        name: userInput,
      },
    })
      .then(() => {
        setUserInfo({
          ...userInfo,
          name: userInput,
        });
        navigateWithoutHistory(navigation, "ApplicationNavigationRoot");
        setIsLoading(false);
      })
      .catch((error) => {
        console.log(error);
        setIsLoading(false);
      });
  };

  return (
    <LoadingIndicator>
      <TouchableWithoutFeedback onPress={Keyboard.dismiss} accessible={false}>
        <KeyboardAvoidingView behavior="height" style={styles.container}>
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
        </KeyboardAvoidingView>
      </TouchableWithoutFeedback>
    </LoadingIndicator>
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
});

export default ChangeProfile;
