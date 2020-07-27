import React from "react";
import { Image, StyleSheet, TouchableOpacity, View } from "react-native";
import { useNavigation } from "@react-navigation/core";

const Profile = () => {
  const navigation = useNavigation();

  return (
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
