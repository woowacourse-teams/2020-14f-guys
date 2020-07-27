import React from "react";
import { Image, StyleSheet, TouchableOpacity, View } from "react-native";
import axios from "axios";
import { SERVER_BASE_URL, TOKEN_STORAGE } from "../../utils/constants";
import AsyncStorage from "@react-native-community/async-storage";

const Certification = () => {
  const fetchData = async () => {
    const accessToken = await AsyncStorage.getItem(TOKEN_STORAGE);
    const response = await axios.get(SERVER_BASE_URL + "/api/members/1", {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    });

    console.log(response.data);
  };

  return (
    <View style={styles.container}>
      <TouchableOpacity onPress={fetchData}>
        <Image source={require("../../assets/kakao_login_button.png")} />
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
  },
});

export default Certification;
