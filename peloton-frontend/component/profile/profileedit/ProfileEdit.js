import React from "react";
import { ScrollView, StyleSheet } from "react-native";
import ProfileEditInfo from "./ProfileEditInfo";
import ProfileEditImage from "./ProfileEditImage";
import SignOutButtonContainer from "./SignOutButtonContainer";
import UnregisterButtonContainer from "./UnregisterButtonContainer";

const ProfileEdit = () => {
  return (
    <ScrollView style={styles.container}>
      <ProfileEditImage />
      <ProfileEditInfo />
      <SignOutButtonContainer />
      <UnregisterButtonContainer />
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    backgroundColor: "#eceff0",
  },
});

export default ProfileEdit;
