import React, { useEffect, useState } from "react";
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
import { COLOR, TOKEN_STORAGE } from "../../utils/constants";
import AsyncStorage from "@react-native-community/async-storage";
import ProfileImageSelect from "./ProfileImageSelect";
import SubmitButton from "./SubmitButton";
import NicknameInput from "./NicknameInput";
import { navigateWithoutHistory } from "../../utils/util";
import LoadingIndicator from "../../utils/LoadingIndicator";
import { loadingState } from "../../state/loading/LoadingState";
import {
  memberInfoState,
  memberTokenState,
} from "../../state/member/MemberState";
import { MemberApi } from "../../utils/api/MemberApi";

const ChangeProfile = () => {
  const navigation = useNavigation();
  const token = useRecoilValue(memberTokenState);
  const [memberInfo, setMemberInfo] = useRecoilState(memberInfoState);
  const [userInput, setUserInput] = useState("");
  const setIsLoading = useSetRecoilState(loadingState);

  useEffect(() => {
    const fetchMemberInfo = async () => {
      setIsLoading(true);
      const newMemberInfo = await MemberApi.get(token);
      setMemberInfo(newMemberInfo);
      setIsLoading(false);
    };
    fetchMemberInfo();
  }, []);

  const onSubmit = async () => {
    Keyboard.dismiss();
    setIsLoading(true);
    await AsyncStorage.setItem(TOKEN_STORAGE, token);
    try {
      await MemberApi.patchName(token, userInput);
      setMemberInfo({
        ...memberInfo,
        name: userInput,
      });
      const formData = new FormData();
      formData.append("profile_image", {
        uri: memberInfo.profile,
        type: "image/jpeg",
        name: memberInfo.profile.substring(9),
      });
      await MemberApi.postProfile(token, formData);
      navigateWithoutHistory(navigation, "ApplicationNavigationRoot");
    } catch (error) {
      console.log(error);
    }
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
  },
});

export default ChangeProfile;
