import React from "react";
import { Image, StyleSheet, Text, TouchableOpacity, View } from "react-native";
import { useRecoilValue } from "recoil";
import { userInfoState } from "../atoms";
import { COLOR, TOKEN_STORAGE } from "../../utils/constants";
import AsyncStorage from "@react-native-community/async-storage";
import { navigateWithoutHistory } from "../../utils/util";
import { useNavigation } from "@react-navigation/core";

const Profile = () => {
  const userInfo = useRecoilValue(userInfoState);
  const navigation = useNavigation();

  const onLogout = async () => {
    await AsyncStorage.removeItem(TOKEN_STORAGE);
    navigateWithoutHistory(navigation, "Login");
  };

  return (
    <View style={styles.container}>
      <Text>닉네임 : {userInfo.name}</Text>
      <Text>이메일 : {userInfo.email}</Text>
      <Image
        style={{ width: 150, height: 150, resizeMode: "contain" }}
        source={{ uri: userInfo.profile.baseImageUrl }}
      />
      <Text>{userInfo.cash}</Text>
      <Text>{userInfo.role}</Text>
      <TouchableOpacity onPress={onLogout}>
        <View
          style={{
            justifyContent: "center",
            alignItems: "center",
            width: 150,
            height: 50,
            backgroundColor: COLOR.LOGIN_BLUE,
          }}
        >
          <Text style={{ color: COLOR.WHITE }}>로그아웃</Text>
        </View>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});

export default Profile;
