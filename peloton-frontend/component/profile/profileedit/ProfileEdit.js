import React from "react";
import { ScrollView, StyleSheet } from "react-native";
import ProfileEditInfo from "./ProfileInfoEdit";
import ProfileImageEdit from "./ProfileImageEdit";
import SignOutButtonContainer from "./SignOutButtonContainer";
import UnregisterButtonContainer from "./UnregisterButtonContainer";
import { COLOR } from "../../../utils/constants";
import LoadingIndicator from "../../../utils/LoadingIndicator";

const ProfileEdit = () => {
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
