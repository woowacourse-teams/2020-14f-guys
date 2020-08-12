import React, { useState } from "react";
import {
  Keyboard,
  KeyboardAvoidingView,
  StyleSheet,
  Text,
  TouchableWithoutFeedback,
  View,
} from "react-native";
import { useRecoilState, useRecoilValue, useSetRecoilState } from "recoil";
import { useNavigation } from "@react-navigation/core";
import { COLOR, SERVER_BASE_URL, TOKEN_STORAGE } from "../../utils/constants";
import AsyncStorage from "@react-native-community/async-storage";
import Axios from "axios";
import ProfileImageSelect from "./ProfileImageSelect";
import SubmitButton from "./SubmitButton";
import NicknameInput from "./NicknameInput";
import { navigateWithoutHistory } from "../../utils/util";
import LoadingIndicator from "../../utils/LoadingIndicator";
import { loadingState } from "../../state/loading/LoadingState";
import { memberInfoState, memberTokenState } from "../../state/member/MemberState";

const ChangeProfile = () => {
  const userToken = useRecoilValue(memberTokenState);
  const [userInfo, setUserInfo] = useRecoilState(memberInfoState);
  const navigation = useNavigation();
  const [userInput, setUserInput] = useState("");
  const setIsLoading = useSetRecoilState(loadingState);

  const onSubmit = async () => {
    Keyboard.dismiss();
    setIsLoading(true);
    await AsyncStorage.setItem(TOKEN_STORAGE, userToken);
    try {
      await Axios({
        method: "PATCH",
        baseURL: SERVER_BASE_URL,
        url: "/api/members/name",
        headers: {
          Authorization: `Bearer ${userToken}`,
        },
        data: {
          name: userInput,
        },
      });
      setUserInfo({
        ...userInfo,
        name: userInput,
      });
      navigateWithoutHistory(navigation, "ApplicationNavigationRoot");
    } catch (error) {}
    setIsLoading(false);
  };

  return (
    <LoadingIndicator>
      <TouchableWithoutFeedback onPress={Keyboard.dismiss} accessible={false}>
        <KeyboardAvoidingView behavior="height" style={styles.container}>
          <View style={styles.titleContainer}>
            <Text style={styles.title}>프로필 설정</Text>
          </View>
          <View style={styles.body}>
            <ProfileImageSelect />
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
    backgroundColor: COLOR.BLUE5,
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
    color: COLOR.WHITE,
  },
  body: {
    flex: 7,
    alignItems: "center",
    borderStyle: "solid",
    borderBottomWidth: 2,
    borderColor: COLOR.BLUE5,
  },
});

export default ChangeProfile;
