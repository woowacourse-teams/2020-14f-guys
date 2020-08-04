import React from "react";
import { Alert, Image, Linking, StyleSheet, TouchableOpacity, View, } from "react-native";
import * as ImagePicker from "expo-image-picker";
import { Entypo } from "@expo/vector-icons";
import { useRecoilState, useRecoilValue } from "recoil";
import { userInfoState, userTokenState } from "../atoms";
import Axios from "axios";
import { SERVER_BASE_URL } from "../../utils/constants";

const ProfileImage = () => {
  const [userInfo, setUserInfo] = useRecoilState(userInfoState);
  const token = useRecoilValue(userTokenState);

  const requestChangeImage = (selectedImage) => {
    const formData = new FormData();
    formData.append("profile_image", {
      uri: selectedImage,
      type: "image/jpeg",
      name: `${userInfo.name}-${selectedImage.substring(0, 10)}`,
    });
    Axios.post(`${SERVER_BASE_URL}/api/members/profile`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        setUserInfo({
          ...userInfo,
          profile: {
            baseImageUrl: selectedImage,
          },
        });
      })
      .catch((err) => console.log(err));
  };

  const openImagePickerAsync = async () => {
    const permissionResult = await ImagePicker.requestCameraRollPermissionsAsync();
    if (permissionResult.granted === false) {
      Alert.alert(
        "카메라 롤 권한이 없습니다.",
        "권한 설정 페이지로 이동하시겠습니까?",
        [
          {
            text: "Cancel",
            style: "cancel",
          },
          { text: "OK", onPress: async () => await Linking.openSettings() },
        ],
        { cancelable: false },
      );
    } else {
      const pickerResult = await ImagePicker.launchImageLibraryAsync({
        allowsEditing: true,
        aspect: [4, 3],
        quality: 0.1,
        base64: true,
      });
      if (pickerResult.cancelled === true) {
        console.log("cameraroll picker cancelled");
        return;
      }
      const selectedImage = pickerResult.uri;
      await requestChangeImage(selectedImage);
    }
  };

  return (
    <View style={styles.container}>
      <TouchableOpacity onPress={openImagePickerAsync}>
        <Image
          style={styles.profileImage}
          source={
            userInfo.profile.baseImageUrl
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
