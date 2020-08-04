import React from "react";
import ProfileImage from "../ProfileImage";
import { Alert, Image, Linking, StyleSheet, TouchableOpacity, View, } from "react-native";
import { useRecoilState, useRecoilValue } from "recoil";
import { userInfoState, userTokenState } from "../../atoms";
import * as ImagePicker from "expo-image-picker";
import Axios from "axios";
import { SERVER_BASE_URL } from "../../../utils/constants";

const ProfileEditImage = () => {
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
        { cancelable: false }
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
    <View style={styles.imageContainer}>
      <ProfileImage image={userInfo.profile.baseImageUrl} />
      <TouchableOpacity onPress={openImagePickerAsync}>
        <Image
          style={styles.profileEditButton}
          source={require("../../../assets/icn_edit.png")}
        />
      </TouchableOpacity>
    </View>
  );
};
const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#eceff0",
  },
  imageContainer: {
    alignItems: "center",
    justifyContent: "center",
    marginTop: 25,
  },
  profileEditButton: {
    width: 40,
    height: 40,
    left: 30,
    bottom: 30,
  },
});

export default ProfileEditImage;
