import React from "react";
import { Image, StyleSheet, TouchableOpacity, View } from "react-native";
import * as ImagePicker from "expo-image-picker";
import { Entypo } from "@expo/vector-icons";
import { useRecoilState, useRecoilValue } from "recoil";
import { profileImageState, userInfoState } from "../atoms";

const ProfileImage = () => {
  const userInfo = useRecoilValue(userInfoState);
  const [profileImage, setProfileImage] = useRecoilState(profileImageState);

  const openImagePickerAsync = async () => {
    const permissionResult = await ImagePicker.requestCameraRollPermissionsAsync();

    if (permissionResult.granted === false) {
      alert("Permission to access camera roll is required!");
      return;
    }
    const pickerResult = await ImagePicker.launchImageLibraryAsync({
      allowsEditing: true,
      aspect: [4, 3],
      quality: 0.1,
      base64: true,
    });
    if (pickerResult.cancelled === true) {
      alert("Cancelled!");
      return;
    }
    setProfileImage(pickerResult);
  };

  return (
    <View style={styles.container}>
      <TouchableOpacity onPress={openImagePickerAsync}>
        <Image
          style={styles.profileImage}
          source={
            profileImage
              ? profileImage
              : userInfo.profile.baseImageUrl
              ? { uri: userInfo.profile.baseImageUrl }
              : require("../../assets/default-profile.jpg")
          }
        />
        <View style={styles.cameraIcon}>
          <Entypo name="camera" size={24} color="black" />
        </View>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    height: 250,
  },
  profileImage: {
    height: 120,
    width: 120,
    borderRadius: 60,
  },
  cameraIcon: {
    opacity: 0.7,
    alignItems: "center",
    justifyContent: "center",
    width: 30,
    height: 30,
    borderRadius: 15,
    backgroundColor: "white",
    position: "absolute",
    top: 85,
    left: 85,
  },
});

export default ProfileImage;
