import React, { useEffect } from "react";
import { Alert, ScrollView, StyleSheet } from "react-native";
import ProfileEditInfo from "./ProfileInfoEdit";
import ProfileImageEdit from "./ProfileImageEdit";
import SignOutButtonContainer from "./SignOutButtonContainer";
import UnregisterButtonContainer from "./UnregisterButtonContainer";
import { COLOR } from "../../../utils/constants";
import LoadingIndicator from "../../../utils/LoadingIndicator";
import { useRecoilValue, useSetRecoilState } from "recoil";
import { memberInfoState, memberTokenState, } from "../../../state/member/MemberState";
import { loadingState } from "../../../state/loading/LoadingState";
import { MemberApi } from "../../../utils/api/MemberApi";
import { logNav } from "../../../utils/Analytics";

const ProfileEdit = () => {
  const setMemberInfo = useSetRecoilState(memberInfoState);
  const setIsLoading = useSetRecoilState(loadingState);
  const token = useRecoilValue(memberTokenState);

  useEffect(() => {
    setIsLoading(true);
    logNav("Profile", "ProfileEdit");
    const fetchUser = async () => {
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
