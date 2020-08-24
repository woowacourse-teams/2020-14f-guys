import React, { useEffect } from "react";
import { Alert, ScrollView, StyleSheet } from "react-native";
import ProfileEditInfo from "./ProfileInfoEdit";
import ProfileImageEdit from "./ProfileImageEdit";
import SignOutButtonContainer from "./SignOutButtonContainer";
import UnregisterButtonContainer from "./UnregisterButtonContainer";
import { COLOR, TOKEN_STORAGE } from "../../../utils/constants";
import LoadingIndicator from "../../../utils/LoadingIndicator";
import { useRecoilState, useRecoilValue, useSetRecoilState } from "recoil";
import {
  memberInfoState,
  memberTokenState,
} from "../../../state/member/MemberState";
import { loadingState } from "../../../state/loading/LoadingState";
import { useNavigation } from "@react-navigation/core";
import AsyncStorage from "@react-native-community/async-storage";
import { navigateWithoutHistory } from "../../../utils/util";
import { MemberApi } from "../../../utils/api/MemberApi";

const ProfileEdit = () => {
  const navigation = useNavigation();
  const setMemberInfo = useSetRecoilState(memberInfoState);
  const setIsLoading = useSetRecoilState(loadingState);
  const token = useRecoilValue(memberTokenState);

  useEffect(() => {
    setIsLoading(true);
    const fetchUser = async () => {
      if (!token) {
        Alert.alert("", "비정상적인 접근입니다.");
        navigateWithoutHistory(navigation, "Login");
      }
      try {
        const memberInfo = await MemberApi.get(token);
        setMemberInfo(memberInfo);
      } catch (e) {
        Alert.alert("", e.response.data.code);
      }
    };
    fetchUser();
    setIsLoading(false);
  }, []);

  return (
    <LoadingIndicator>
      <ScrollView style={styles.container}>
        <ProfileImageEdit />
        <ProfileEditInfo />
        <SignOutButtonContainer />
        <UnregisterButtonContainer />
      </ScrollView>
    </LoadingIndicator>
  );
};

const styles = StyleSheet.create({
  container: {
    backgroundColor: COLOR.WHITE,
  },
});

export default ProfileEdit;
