import React, { useEffect } from "react";
import { Image, StyleSheet, Text, TouchableOpacity, View } from "react-native";
import { useNavigation } from "@react-navigation/core";
import { useRecoilValue } from "recoil/dist";
import { userTokenState } from "../atoms";
import AsyncStorage from "@react-native-community/async-storage";
import { TOKEN_STORAGE } from "../../utils/constants";

const Profile = ({ route }) => {
  const navigation = useNavigation();
  const token = useRecoilValue(userTokenState);

  useEffect(() => {
    const saveToken = async () => {
      if (!token) {
        return;
      }
      try {
        await AsyncStorage.setItem(TOKEN_STORAGE, token);
      } catch (e) {
        return;
      }
    };
    saveToken();
  });

  return token ? (
    <View style={styles.loginButton}>
      <TouchableOpacity onPress={() => navigation.navigate("WebScreen")}>
        <Image source={require("../../assets/kakao_login_button.png")} />
        <Text>{token}</Text>
      </TouchableOpacity>
    </View>
  ) : (
    <View style={styles.loginButton}>
      <TouchableOpacity onPress={() => navigation.navigate("WebScreen")}>
        <Image source={require("../../assets/kakao_login_button.png")} />
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  loginButton: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
});

export default Profile;
